package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitPageModel;


/**
 * SearchPage del UsuarioApm 
 * 
 * @author Tecso
 * 
 */
public class UsuarioApmSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "usuarioApmSearchPageVO";
										
	private UsuarioApmVO usuarioApm = new UsuarioApmVO();
	
	

	// Constructores
	public UsuarioApmSearchPage() {
		super(ApmSecurityConstants.ABM_USUARIOAPM);
	}

	// Getters y Setters

	public String getName() {
		return NAME;
	}

	public UsuarioApmVO getUsuarioApm() {
		return usuarioApm;
	}

	public void setUsuarioApm(UsuarioApmVO usuarioApm) {
		this.usuarioApm = usuarioApm;
	}

	

	

}
