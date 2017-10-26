package com.mercadolibre.magneto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MUTANTES")
public class Mutant {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;

	@Column(name="NOMBRE", nullable=false)
	private String nombre;

	@Column(name="MUTANTE", nullable=false)
	private Integer isMutante;
	
	@Column(name="DNA", nullable=false)
	private String dna;
	
	public Mutant() {

	}
	
	public Mutant(String nombre, int isMutante, String dna) {
		this.nombre = nombre;
		this.isMutante = isMutante;
		this.dna = dna;
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

	public Integer getIsMutante() {
		return isMutante;
	}

	public void setIsMutante(Integer isMutante) {
		this.isMutante = isMutante;
	}

	public String getDna() {
		return dna;
	}

	public void setDna(String dna) {
		this.dna = dna;
	}
	
	

}
