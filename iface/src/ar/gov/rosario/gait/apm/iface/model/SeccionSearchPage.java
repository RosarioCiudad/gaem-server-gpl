package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitPageModel;

/**
 * SearchPage del Seccion
 * 
 * @author Tecso
 *
 */
public class SeccionSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "seccionSearchPageVO";
	
	private SeccionVO seccion= new SeccionVO();
	
	// Constructores
	public SeccionSearchPage() {       
       super(ApmSecurityConstants.ABM_SECCION);        
    }
	
	// Getters y Setters
	public SeccionVO getSeccion() {
		return seccion;
	}
	public void setSeccion(SeccionVO seccion) {
		this.seccion = seccion;
	}           

    public String getName(){    
		return NAME;
	}
	
	// View getters
}
