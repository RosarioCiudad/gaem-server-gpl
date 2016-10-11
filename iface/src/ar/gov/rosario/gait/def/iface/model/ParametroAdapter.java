package ar.gov.rosario.gait.def.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;

/**
 * Adapter del Parametro
 * 
 * @author tecso
 */
public class ParametroAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "parametroAdapterVO";
	
    private ParametroVO parametro = new ParametroVO();
    
    // Constructores
    public ParametroAdapter(){
    	super(DefSecurityConstants.ABM_PARAMETRO);
    }
    
    //  Getters y Setters
	public ParametroVO getParametro() {
		return parametro;
	}

	public void setParametro(ParametroVO parametroVO) {
		this.parametro = parametroVO;
	}

	// View getters
}
