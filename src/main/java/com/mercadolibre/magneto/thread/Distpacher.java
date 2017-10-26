package com.mercadolibre.magneto.thread;

import java.util.List;
import java.util.Map;

import com.mercadolibre.magneto.model.Llamada;
import com.mercadolibre.magneto.model.Personal;

public class Distpacher {

	// sea crea un mapa y un contador de llamadas statico
	// y volatile, as� tiene una unica instancia en memoria para toda la app
	// y nos aseguramos de trabajar siempre con la misma lista y volatile
	// asi nos aseguramos que java nunca consulte a la cache sino el valor real
	private static volatile Map<Integer, List<Personal>> personal;
	private static volatile int activeCalls = 0;

	// Se crea un constructor privado para asegurar que sea un Singleton
	// y que tenga una unica instancia el Dispaches en toda la app
	// puede ser o no un Singleton o un objeto inyectable ya que
	// lo importante es que sea static volatile la lista de empleados
	// y el synchronized en el metodo distpacherCall
	private Distpacher() {

	}

	// el metodo que se encarga de buscar el operador es synchronized ya que
	// nos tenemos que asegurar que procese las llamadas de a una para que
	// no genere iconsistencias en la lista de personal y el contador de llamdas
	public static synchronized Personal distpacherCall() {

		// si el contador de llamdas activas esta en 10 se retorna null
		// para dejar la llamda en espera
		if (activeCalls == 10) {
			System.out.println("Se llego al limete de llamadas");
			return null;
		}

		// se recorre el mapa el cual se encuentra ordenado de menor a mayor puesto
		// si no encuentra personal en los puestos mas bajo pasa al siguiente y asi
		// susesivamente, si encuentra a alguien libre lo retorna para atender la llamada
		for (Integer key : personal.keySet()) {
			for (Personal p : personal.get(key)) {
				if (p.isLibre()) {
					p.setLibre(false);
					activeCalls++;
					System.out.println("llamadas activas: " + activeCalls);
					return p;
				}
			}
		}

		// si no hay personas libres retorna null para dejar la llamda en espera
		return null;
	}
	
	
	public static Llamada proceseCall(Llamada llamada) {
		Personal p = null;

		// Mientas que la llamada no sea atendida por ningun operador va a quedar en un
		// loop
		while (p == null) {
			// Se le busca un operador libre a la llamda
			p = Distpacher.distpacherCall();
			if (p != null) {

				// La llamada pudo ser atendida por un operador
				System.out.println("llamada " + llamada.getId() + " atendida por: " + p.getNombre());

				try {

					// Se crea un numero aleatorio entre 5 y 10 segundos que puede durar la llamada
					int randomNum = (int) (5 + (Math.random() * (10 - 5)));
					// Esto simula los 5 a 10 segundos que puede durar la llamada
					// y mantiene al operador ocupado
					Thread.sleep(randomNum * 1000);

					llamada.setOperador(p);
					llamada.setDuracion(randomNum);

					// se libera al operador
					Distpacher.liberateOperador(p);
					// se resta uno al contador de llamadas activas
					Distpacher.liberateCall();
					
					System.out.println("la llamada id " + llamada.getId() + " duro: " + randomNum + " seg.");
					return llamada;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} else {
				// En caso que no se encuentre ningún operador se congela el
				// thread durante 5 seg asi le da tiempo a un operador a que se libere
				try {
					System.out.println("la llamada id " + llamada.getId() + " en espera");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		
		
		return null;
	}

	public static void setPersonal(Map<Integer, List<Personal>> personalMap) {
		Distpacher.personal = personalMap;
	}

	public static synchronized void liberateCall() {
		activeCalls--;
		System.out.println("llamadas activas: " + activeCalls);
	}

	public static synchronized void liberateOperador(Personal p) {
		p.setLibre(true);
	}
	
	public static void clear() {
		personal = null;
		activeCalls = 0;
	}

}
