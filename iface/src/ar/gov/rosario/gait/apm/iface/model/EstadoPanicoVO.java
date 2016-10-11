package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import coop.tecso.demoda.iface.model.SiNo;

public class EstadoPanicoVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "estadoPanicoVO";
	
	private String descripcion;
	
	private String transiciones;

	private SiNo esInicial;
	
	public EstadoPanicoVO() {
		super();
	}
	
	public EstadoPanicoVO(int id, String descripcion) {
		super();
		setId(Integer.valueOf(id).longValue());
		setDescripcion(descripcion);
	}

	// Getters y setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTransiciones() {
		return transiciones;
	}

	public void setTransiciones(String transiciones) {
		this.transiciones = transiciones;
	}

	public SiNo getEsInicial() {
		return esInicial;
	}

	public void setEsInicial(SiNo esInicial) {
		this.esInicial = esInicial;
	}
}