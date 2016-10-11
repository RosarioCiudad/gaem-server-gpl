package ar.gov.rosario.gait.not.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.not.iface.util.NotSecurityConstants;

/**
 * SearchPage del EstadoNotificacion
 * 
 * @author Tecso
 *
 */
public class EstadoNotificacionSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "estadoNotificacionSearchPageVO";
	
	private EstadoNotificacionVO estadoNotificacion= new EstadoNotificacionVO();
	
	// Constructores
	public EstadoNotificacionSearchPage() {       
       super(NotSecurityConstants.ABM_ESTADONOTIFICACION);        
    }
	
	// Getters y Setters
	public EstadoNotificacionVO getEstadoNotificacion() {
		return estadoNotificacion;
	}
	public void setEstadoNotificacion(EstadoNotificacionVO estadoNotificacion) {
		this.estadoNotificacion = estadoNotificacion;
	}           

    public String getName(){    
		return NAME;
	}

}
