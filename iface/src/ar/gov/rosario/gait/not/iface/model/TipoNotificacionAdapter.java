package ar.gov.rosario.gait.not.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.not.iface.util.NotSecurityConstants;

/**
 * Adapter del TipoNotificacion
 * 
 * @author tecso
 */
public class TipoNotificacionAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "tipoNotificacionAdapterVO";
	
    private TipoNotificacionVO tipoNotificacion = new TipoNotificacionVO();
    
    // Constructores
    public TipoNotificacionAdapter(){
    	super(NotSecurityConstants.ABM_TIPONOTIFICACION);
    }
    
    //  Getters y Setters
	public TipoNotificacionVO getTipoNotificacion() {
		return tipoNotificacion;
	}

	public void setTipoNotificacion(TipoNotificacionVO tipoNotificacionVO) {
		this.tipoNotificacion = tipoNotificacionVO;
	}

	public String getName(){
		return NAME;
	}
	
	// View getters
}