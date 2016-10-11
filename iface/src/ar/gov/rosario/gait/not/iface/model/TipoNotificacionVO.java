package ar.gov.rosario.gait.not.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class TipoNotificacionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "tipoNotificacionVO";

	private String descripcion;
	private String ubicacionSonido;
	private String ubicacionIcono;

	// Constructores
	public TipoNotificacionVO() {
		super();
	}

	// Constructor para utilizar este VO en un combo como valor SELECCIONAR,
	// TODOS, etc.
	public TipoNotificacionVO(int id, String desc) {
		super();
		setId(new Long(id));
		setDescripcion(desc);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getUbicacionIcono() {
		return ubicacionIcono;
	}

	public String getUbicacionSonido() {
		return ubicacionSonido;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setUbicacionIcono(String ubicacionIcono) {
		this.ubicacionIcono = ubicacionIcono;
	}

	public void setUbicacionSonido(String ubicacionSonido) {
		this.ubicacionSonido = ubicacionSonido;
	}

}
