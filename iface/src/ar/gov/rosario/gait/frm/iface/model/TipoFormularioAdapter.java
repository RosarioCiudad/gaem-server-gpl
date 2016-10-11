package ar.gov.rosario.gait.frm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;

public class TipoFormularioAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "tipoFormularioAdapterVO";

	private TipoFormularioVO tipoFormulario = new TipoFormularioVO();



	// Constructores
	public TipoFormularioAdapter() {
		super(FrmSecurityConstants.ABM_TIPOFORMULARIO);
	}

	// Getters y Setters


	public String getName() {
		return NAME;
	}

	public TipoFormularioVO getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormularioVO tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}


}