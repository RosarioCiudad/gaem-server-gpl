package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitPageModel;

public class AplicacionSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionSearchPageVO";
										
	private AplicacionVO aplicacion = new AplicacionVO();
	
	// Constructores
	public AplicacionSearchPage() {
		super(ApmSecurityConstants.ABM_APLICACION);
	}

	// Getters y Setters
	public String getName() {
		return NAME;
	}

	public AplicacionVO getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionVO aplicacion) {
		this.aplicacion = aplicacion;
	}

}
