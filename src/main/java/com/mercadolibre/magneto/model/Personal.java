package com.mercadolibre.magneto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="PERSONAL")
public class Personal {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@Column(name="NOMBRE", nullable=false)
	private String nombre;

	@Column(name="PUESTO", nullable=false)
	private Integer puesto;
	
	@Transient
	private boolean libre;
	
	
	public Personal() {

	}
	
	public Personal(String nombre, int puesto) {
		this.nombre = nombre;
		this.puesto = puesto;
		this.libre = true;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getPuesto() {
		return puesto;
	}
	public void setPuesto(int puesto) {
		this.puesto = puesto;
	}
	public boolean isLibre() {
		return libre;
	}
	public void setLibre(boolean libre) {
		this.libre = libre;
	}

}
