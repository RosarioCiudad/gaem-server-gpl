package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

/**
 * Value Object del Seccion
 * @author tecso
 *
 */
public class SeccionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "seccionVO";
	
	private String descripcion;   // VARCHAR(255) NOT NULL
	
	// Buss Flags
	
	
	// View Constants
	
	
	// Constructores
	public SeccionVO() {
		super();
	}
	
	// Constructor para utilizar este VO en un combo como valor SELECCIONAR, TODOS, etc.
	public SeccionVO(int id, String desc) {
		super();
		setId(new Long(id));
		setDescripcion(desc);
	}
	
	// Getters y Setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	// Buss flags getters y setters
	
	
	// View flags getters
	
	
	// View getters
}
