package com.almundo.examen.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.mercadolibre.magneto.dto.ConfigDTO;
import com.mercadolibre.magneto.model.Llamada;
import com.mercadolibre.magneto.model.Personal;
import com.mercadolibre.magneto.thread.Distpacher;
import com.mercadolibre.magneto.thread.TaskCallable;

public class AppTest {

	@Test
	public void test10Calls() {
		
		List<ConfigDTO> configList= createConfig();
		
		try {
			List<Llamada> result = runcalls(configList, 10, 50);
			assertNotNull(result.get(0).getOperador());
			assertNotNull(result.get(9).getOperador());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	
	@Test
	public void testOneCalls() {
		
		List<ConfigDTO> configList= createConfig();
		
		try {
			List<Llamada> result = runcalls(configList, 1 , 50);
			assertEquals(result.get(0).getOperador().getPuesto(), 1);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		
	}
	
	
	@Test
	public void testDistributionCalls() {
		List<ConfigDTO> configList= createConfig();
		boolean findOperador = false;
		boolean findSupervisor = false;
		boolean findDirector = false;
		try {
			List<Llamada> llamadaList = runcalls(configList, 5 , 10);
			
			for (Llamada llamada : llamadaList) {
				if (llamada.getOperador().getPuesto() == 1) {
					findOperador = true;
				}
				if (llamada.getOperador().getPuesto() == 2) {
					findSupervisor = true;
				}
				if (llamada.getOperador().getPuesto() == 3) {
					findDirector = true;
				}
			}
			
			assertEquals(true, findOperador && findSupervisor && findDirector);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		
	}

	private List<ConfigDTO> createConfig(){
		List<ConfigDTO> configList= new ArrayList<>();
		ConfigDTO config;

		config = new ConfigDTO();
		config.setTipoOperador(1);
		config.setCantidad(3);
		config.setDescripcion("operador");
		configList.add(config);
		
		config = new ConfigDTO();
		config.setTipoOperador(2);
		config.setCantidad(1);
		config.setDescripcion("supervisor");
		configList.add(config);
		
		config = new ConfigDTO();
		config.setTipoOperador(3);
		config.setCantidad(1);
		config.setDescripcion("director");
		configList.add(config);
		
		return configList;

	}
	
	private List<Llamada> runcalls(List<ConfigDTO> configList, int calls, int thread) throws Exception{

		Map<Integer, List<Personal>> personMap = setOperadores(configList);
		Distpacher.setPersonal(personMap);
		
		List<Llamada> llamadaList = new ArrayList<>();
		ExecutorService executorService = Executors.newFixedThreadPool(thread);
		List<Callable<Llamada>> lstOfCalls = new ArrayList<Callable<Llamada>>();

		// por cada llamada indicada en el frontEnd se dispara un thread
		// simulando asi la entrada de varias llamadas simultaneas
		for (int i = 1; i <= calls; i++) {
			lstOfCalls.add(new TaskCallable(new Llamada(Long.valueOf(i))));
		}

		// invoca todos los thread que se crearon por llamadas
		List<Future<Llamada>> tasks = executorService.invokeAll(lstOfCalls);

		System.out.println("\n" + tasks.size() + " Llamadas fueron procesadas.\n");
		
		// itera sobre el result de cada thread
		for (Future<Llamada> task : tasks) {
			llamadaList.add(new Llamada(task.get().getId(), task.get().getDuracion(), task.get().getOperador()));
		}
		
		executorService.shutdown();
		
		return llamadaList;
		
	}
	
	private Map<Integer, List<Personal>> setOperadores(List<ConfigDTO> confgList) {
		Personal p;
		List<Personal> plist;
		Map<Integer, List<Personal>> personalMap = new TreeMap<>();

		// Se recorre la lista de configs en la cual se crea un operador
		// por la cantidad indicada y del tipo indicado, los cuales se agregan
		// a un tree map el cual se ordena automaticamente por key, y la key
		// de este va a ser el rol y en el valor va a tener una lista de personas con
		// ese rol
		for (ConfigDTO confg : confgList) {

			for (int i = 1; i <= confg.getCantidad(); i++) {
				//El rol de la persona se podria haber normalizo mas en la BBDD
				//y ponerlo en otra tabla pero decidi que este en la misma
				//tabla persona asi es mas rapido la carga
				p = new Personal(confg.getDescripcion() + " " + i, confg.getTipoOperador());

				if (personalMap.get(confg.getTipoOperador()) != null) {
					personalMap.get(confg.getTipoOperador()).add(p);
				} else {
					plist = new ArrayList<>();
					plist.add(p);
					personalMap.put(confg.getTipoOperador(), plist);
				}

			}

		}

		return personalMap;
	}
	
	
}
