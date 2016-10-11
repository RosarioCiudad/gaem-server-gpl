package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class PerfilAccesoAplicacionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "perfilAccesoAplicacionVO";

	private PerfilAccesoVO perfilAcceso = new PerfilAccesoVO();
	
	private AplicacionVO aplicacion = new AplicacionVO();
	
	// Constructores
	public PerfilAccesoAplicacionVO() {
		super();
	}

	public PerfilAccesoVO getPerfilAcceso() {
		return perfilAcceso;
	}

	public void setPerfilAcceso(PerfilAccesoVO perfilAcceso) {
		this.perfilAcceso = perfilAcceso;
	}

	public AplicacionVO getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionVO aplicacion) {
		this.aplicacion = aplicacion;
	}
}