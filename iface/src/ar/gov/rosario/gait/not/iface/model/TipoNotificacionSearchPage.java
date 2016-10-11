package ar.gov.rosario.gait.not.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.not.iface.util.NotSecurityConstants;

/**
 * SearchPage del TipoNotificacion
 * 
 * @author Tecso
 *
 */
public class TipoNotificacionSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "tipoNotificacionSearchPageVO";
	
	private TipoNotificacionVO tipoNotificacion= new TipoNotificacionVO();
	
	// Constructores
	public TipoNotificacionSearchPage() {       
       super(NotSecurityConstants.ABM_TIPONOTIFICACION);        
    }
	
	// Getters y Setters
	public TipoNotificacionVO getTipoNotificacion() {
		return tipoNotificacion;
	}
	public void setTipoNotificacion(TipoNotificacionVO tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}           

    public String getName(){    
		return NAME;
	}

	// View getters
}
