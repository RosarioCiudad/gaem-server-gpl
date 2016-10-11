package ar.gov.rosario.gait.not.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.not.iface.util.NotSecurityConstants;

/**
 * Adapter del EstadoNotificacion
 * 
 * @author tecso
 */
public class EstadoNotificacionAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "estadoNotificacionAdapterVO";
	
    private EstadoNotificacionVO estadoNotificacion = new EstadoNotificacionVO();
    
    // Constructores
    public EstadoNotificacionAdapter(){
    	super(NotSecurityConstants.ABM_ESTADONOTIFICACION);
    }
    
    //  Getters y Setters
	public EstadoNotificacionVO getEstadoNotificacion() {
		return estadoNotificacion;
	}

	public void setEstadoNotificacion(EstadoNotificacionVO estadoNotificacionVO) {
		this.estadoNotificacion = estadoNotificacionVO;
	}

	public String getName(){
		return NAME;
	}

}