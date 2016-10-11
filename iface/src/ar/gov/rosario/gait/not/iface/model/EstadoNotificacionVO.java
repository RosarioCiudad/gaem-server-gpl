package ar.gov.rosario.gait.not.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class EstadoNotificacionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "estadoNotificacionVO";

	private String descripcion;

	// Constructores
	public EstadoNotificacionVO() {
		super();
	}
	
	// Constructor para utilizar este VO en un combo como valor SELECCIONAR, TODOS, etc.
	public EstadoNotificacionVO(int id, String desc) {
		super();
		setId(new Long(id));
		setDescripcion(desc);
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
