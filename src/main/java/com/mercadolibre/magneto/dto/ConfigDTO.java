package com.mercadolibre.magneto.dto;

public class ConfigDTO {

	private int tipoOperador;
	private int cantidad;
	private String descripcion;
	
	public int getTipoOperador() {
		return tipoOperador;
	}
	public void setTipoOperador(int tipoOperador) {
		this.tipoOperador = tipoOperador;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
