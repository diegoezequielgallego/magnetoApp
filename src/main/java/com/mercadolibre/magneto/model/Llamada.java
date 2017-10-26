package com.mercadolibre.magneto.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="LLAMADA")
public class Llamada {

	
	public Llamada() {

	}
	
	public Llamada(Long id) {
		this.id = id;
	}
	
	public Llamada(Long id, int duracion, Personal operador) {
		this.id = id;
		this.duracion = duracion;
		this.operador = operador;
	}
	
	@Id
	private Long id;
	
	@Column(name="DURACION", nullable=false)
	private int duracion;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Personal operador;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public Personal getOperador() {
		return operador;
	}
	public void setOperador(Personal operador) {
		this.operador = operador;
	}
	
	
}
