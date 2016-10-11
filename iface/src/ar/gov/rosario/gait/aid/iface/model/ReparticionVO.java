package ar.gov.rosario.gait.aid.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class ReparticionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "reparticionVO";
	
	private String descripcion = "";
	
	public ReparticionVO() {
		super();
	}
	
	public ReparticionVO(int id, String descripcion) {
		super();
		setId(new Long(id));
		setDescripcion(descripcion);
	}
	
	// Getters y setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}