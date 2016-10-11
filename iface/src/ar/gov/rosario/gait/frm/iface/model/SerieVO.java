package ar.gov.rosario.gait.frm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class SerieVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "serieVO";
	
	private String codigo = "";
	
	public SerieVO() {
		super();
	}

	public SerieVO(int id, String codigo ) {
		super();
		setId(new Long(id));
		this.codigo = codigo;
	}

	// Getters y setters
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}