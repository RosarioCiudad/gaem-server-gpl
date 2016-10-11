package ar.gov.rosario.gait.frm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class TipoFormularioVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "tipoFormularioVO";

	private String codigo="";

	private String descripcion="";


	public TipoFormularioVO() {
		super();
	}

	public TipoFormularioVO(int id, String descripcion ) {
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}