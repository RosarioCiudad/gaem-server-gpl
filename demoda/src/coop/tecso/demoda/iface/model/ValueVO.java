package coop.tecso.demoda.iface.model;

import java.io.Serializable;


public class ValueVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "valueVO";

	private Integer id;
	private String descripcion = "";

	public ValueVO(Integer id, String descripcion) {
		this.id = id;
		this.descripcion=descripcion;
	}

	// Getters y setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}