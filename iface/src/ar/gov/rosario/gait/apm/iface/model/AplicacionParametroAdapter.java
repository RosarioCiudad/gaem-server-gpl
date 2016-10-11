package ar.gov.rosario.gait.apm.iface.model;



import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;


/**
 * Adapter del Aplicacion PErfil
 * 
 * @author tecso
 */

public class AplicacionParametroAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionParametroAdapterVO";


	private AplicacionParametroVO aplicacionParametro = new AplicacionParametroVO();
	private List<AplicacionVO> listAplicacion = new ArrayList<AplicacionVO>();
	
	// Constructores
	public AplicacionParametroAdapter() {
		super(ApmSecurityConstants.ABM_APLICACIONPARAMETRO);
	}

	// Getters y Setters

	

	public String getName() {
		return NAME;
	}




	public List<AplicacionVO> getListAplicacion() {
		return listAplicacion;
	}

	public void setListAplicacion(List<AplicacionVO> listAplicacion) {
		this.listAplicacion = listAplicacion;
	}

	public AplicacionParametroVO getAplicacionParametro() {
		return aplicacionParametro;
	}

	public void setAplicacionParametro(AplicacionParametroVO aplicacionParametro) {
		this.aplicacionParametro = aplicacionParametro;
	}







  
}