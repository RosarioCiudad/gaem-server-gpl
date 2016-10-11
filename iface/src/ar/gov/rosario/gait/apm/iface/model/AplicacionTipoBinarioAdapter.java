package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;

public class AplicacionTipoBinarioAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionTipoBinarioAdapterVO";

	private AplicacionTipoBinarioVO aplicacionTipoBinario = new AplicacionTipoBinarioVO();

	// Constructores
	public AplicacionTipoBinarioAdapter() {
		super(ApmSecurityConstants.ABM_APLICACIONTIPOBINARIO);
	}

	// Getters y Setters
	public String getName() {
		return NAME;
	}

	public AplicacionTipoBinarioVO getAplicacionTipoBinario() {
		return aplicacionTipoBinario;
	}

	public void setAplicacionTipoBinario(AplicacionTipoBinarioVO aplicacionTipoBinario) {
		this.aplicacionTipoBinario = aplicacionTipoBinario;
	}

}
