package ar.gov.rosario.gait.apm.iface.model;



import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;


/**
 * Adapter del Aplicacion PErfil Parametro
 * 
 * @author tecso
 */

public class AplicacionPerfilParametroAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionPerfilParametroAdapterVO";
	
	private AplicacionPerfilParametroVO aplicacionPerfilParametro = new AplicacionPerfilParametroVO();
	

	
	// Constructores
	public AplicacionPerfilParametroAdapter() {
		super(ApmSecurityConstants.ABM_APLICACIONPERFILPARAMETRO);
	}

	// Getters y Setters

	

	public String getName() {
		return NAME;
	}

	public AplicacionPerfilParametroVO getAplicacionPerfilParametro() {
		return aplicacionPerfilParametro;
	}

	public void setAplicacionPerfilParametro(AplicacionPerfilParametroVO aplicacionPerfilParametro) {
		this.aplicacionPerfilParametro = aplicacionPerfilParametro;
	}  



  
}