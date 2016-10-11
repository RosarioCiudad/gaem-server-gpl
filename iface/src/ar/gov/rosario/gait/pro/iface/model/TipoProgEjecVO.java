package ar.gov.rosario.gait.pro.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

/**
 * Representa los tipos de programas que se pueden utilizar para delegar la ejecucion de un proceso.
 * @author tecso
 *
 */
public class TipoProgEjecVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "tipoProEjecVO";

	private String desTipoProgEjec;
	
	// Constructores
	
	public TipoProgEjecVO(){
		super();
	}
	public TipoProgEjecVO(int id, String desTipoProgEjec){
		super(id);
		setDesTipoProgEjec(desTipoProgEjec);
	}
	// Getters y Setters
	public String getDesTipoProgEjec(){
		return desTipoProgEjec;
	}
	public void setDesTipoProgEjec(String desTipoProgEjec){
		this.desTipoProgEjec = desTipoProgEjec;
	}

}
