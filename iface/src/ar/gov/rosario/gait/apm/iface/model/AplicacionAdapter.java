package ar.gov.rosario.gait.apm.iface.model;



import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;


/**
 * Adapter del Aplicacion PErfil
 * 
 * @author tecso
 */

public class AplicacionAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionAdapterVO";
	public static final String ENC_NAME = "encAplicacionAdapterVO";

	private AplicacionVO aplicacion = new AplicacionVO();


	// Constructores
	public AplicacionAdapter() {
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


	// View getters
	// Permisos para ABM  Aplicacion Binario Version
	public String getVerAplicacionBinarioVersionEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLICACIONBINARIOVERSION, BaseSecurityConstants.VER);
	}
	public String getModificarAplicacionBinarioVersionEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLICACIONBINARIOVERSION, BaseSecurityConstants.MODIFICAR);
	}
	public String getEliminarAplicacionBinarioVersionEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLICACIONBINARIOVERSION, BaseSecurityConstants.ELIMINAR);
	}
	public String getAgregarAplicacionBinarioVersionEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLICACIONBINARIOVERSION, BaseSecurityConstants.AGREGAR);
	}
	
	// View getters
	// Permisos para ABM  PARAMETRO
	public String getVerAplicacionParametroEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLICACIONPARAMETRO, BaseSecurityConstants.VER);
	}
	public String getModificarAplicacionParametroEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLICACIONPARAMETRO, BaseSecurityConstants.MODIFICAR);
	}
	public String getEliminarAplicacionParametroEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLICACIONPARAMETRO, BaseSecurityConstants.ELIMINAR);
	}
	public String getAgregarAplicacionParametroEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLICACIONPARAMETRO, BaseSecurityConstants.AGREGAR);
	}




  
}