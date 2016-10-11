package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;

public class TablaVersionAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "tablaVersionAdapterVO";

	private TablaVersionVO tablaVersion = new TablaVersionVO();

	// Constructores
	public TablaVersionAdapter() {
		super(ApmSecurityConstants.ABM_TABLAVERSION);
	}

	// Getters y Setters
	public String getName() {
		return NAME;
	}

	public TablaVersionVO getTablaVersion() {
		return tablaVersion;
	}

	public void setTablaVersion(TablaVersionVO tablaVersion) {
		this.tablaVersion = tablaVersion;
	}

	// View getters
}
