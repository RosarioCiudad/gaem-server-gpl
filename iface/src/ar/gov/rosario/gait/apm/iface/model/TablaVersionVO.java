package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import coop.tecso.demoda.iface.helper.StringUtil;

public class TablaVersionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "tablaVersionVO";

	private String tabla;

	private Integer lastVersion;

	// Constructores
	public TablaVersionVO() {
		super();
	}

	public TablaVersionVO(int id, String desc) {
		super();
		setId(new Long(id));
		setTabla(desc);
	}

	// Getters y Setters
	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public Integer getLastVersion() {
		return lastVersion;
	}

	public void setLastVersion(Integer lastVersion) {
		this.lastVersion = lastVersion;
	}
	
	public String getLastVersionView() {
		return StringUtil.formatInteger(lastVersion);
	}

}