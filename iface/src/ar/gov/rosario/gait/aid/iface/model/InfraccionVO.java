package ar.gov.rosario.gait.aid.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class InfraccionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "infraccionVO";
	
	private String codigo = "";
	private String descripcion = "";
	private String descripcionLarga = "";
	private String tipo = "";

	// Getters y setters
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionLarga() {
		return descripcionLarga;
	}

	public void setDescripcionLarga(String descripcionLarga) {
		this.descripcionLarga = descripcionLarga;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}