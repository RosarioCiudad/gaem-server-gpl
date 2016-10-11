package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class AplicacionTablaVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionTablaVO";

	private AplicacionVO aplicacion = new AplicacionVO();
	private TablaVersionVO tablaVersion = new TablaVersionVO();
	
	// Constructores
	public AplicacionTablaVO() {
		super();
	}
	
	// Constructor para utilizar este VO en un combo como valor SELECCIONAR, TODOS, etc.
	public AplicacionTablaVO(int id) {
		super();
		setId(new Long(id));
		
	}

	public AplicacionVO getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionVO aplicacion) {
		this.aplicacion = aplicacion;
	}

	public TablaVersionVO getTablaVersion() {
		return tablaVersion;
	}

	public void setTablaVersion(TablaVersionVO tablaVersion) {
		this.tablaVersion = tablaVersion;
	}
	
}