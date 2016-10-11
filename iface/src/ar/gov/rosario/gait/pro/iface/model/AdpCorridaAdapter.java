package ar.gov.rosario.gait.pro.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.pro.iface.util.ProSecurityConstants;

/**
 * Adapter de Corridas usando Adp
 * 
 * @author tecso
 */
public class AdpCorridaAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "adpCorridaAdapterVO";
	
	// Parametro para pasar el id de la corrida seleccionada (esto se utiliza para no cambiar el valor de selectedId
	// del navModel, ya que tendra informacion necesaria para el retorno. Ej: idAsentamiento)
	public static final String PARAM_ID_CORRIDA_SELECTED = "paramIdCorridaSelected";
	public static final String PARAM_ID_TIPO_PROCMAS = "paramIdTipoProcMas";

	private CorridaVO corrida = new CorridaVO(); 

	// Constructores
	public AdpCorridaAdapter() {       
		super(ProSecurityConstants.ABM_CORRIDA);        
    }

	// Getters y Setters
	public CorridaVO getCorrida() {
		return corrida;
	}
	public void setCorrida(CorridaVO corrida) {
		this.corrida = corrida;
	}
	
}
