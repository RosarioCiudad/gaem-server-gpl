package ar.gov.rosario.gait.frm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class EstadoTipoFormularioVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "estadoTipoFormularioVO";
	
	private String descripcion = "";
	
	private TipoFormularioVO tipoFormulario = new TipoFormularioVO();

	//construtor
	public EstadoTipoFormularioVO() {
		super();
	}

	public EstadoTipoFormularioVO(int id, String descripcion ) {
		super();
		setId(new Long(id));
		this.descripcion = descripcion;
	}
	
	// Getters y setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoFormularioVO getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormularioVO tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}
}