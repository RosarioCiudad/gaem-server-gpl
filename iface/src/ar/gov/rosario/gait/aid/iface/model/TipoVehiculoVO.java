package ar.gov.rosario.gait.aid.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class TipoVehiculoVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "tipoVehiculoVO";
	
	private String codigo = "";
	private String descripcion = "";

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
}