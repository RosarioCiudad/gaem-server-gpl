package ar.gov.rosario.gait.pro.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

/**
 * Representa los tipos de ejecucion. 
 * @author tecso
 *
 */
public class TipoEjecucionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "tipoEjecucionVO";

	private String desTipoEjecucion;
	
	// Constructores
	public TipoEjecucionVO(){
		super();
	}
	public TipoEjecucionVO(int id, String desTipoEjecucion) {
		super(id);
		setDesTipoEjecucion(desTipoEjecucion);
	}
	// Getters y Setters
	public String getDesTipoEjecucion(){
		return desTipoEjecucion;
	}
	public void setDesTipoEjecucion(String desTipoEjecucion){
		this.desTipoEjecucion = desTipoEjecucion;
	}
}
