package ar.gov.rosario.gait.apm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import coop.tecso.demoda.iface.model.SiNo;
import coop.tecso.demoda.iface.model.Tratamiento;

/**
 * Adapter del Campo Valor
 * 
 * @author tecso
 */

public class CampoValorOpcionAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "campoValorOpcionAdapterVO";
	public static final String ENC_NAME = "encCampoValorOpcionAdapterVO";
	
	private CampoValorOpcionVO campoValorOpcion = new CampoValorOpcionVO();
	private List<SiNo> listSiNo = new ArrayList<SiNo>();
	private List<Tratamiento> listTratamiento = new ArrayList<Tratamiento>();

	// Constructores
	public CampoValorOpcionAdapter() {
		super(ApmSecurityConstants.ABM_CAMPOVALOR_OPCION);
	}

	// Getters y Setters

	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}

	public String getName() {
		return NAME;
	}

	/**
	 * @return the campo
	 */
	public CampoValorOpcionVO getCampoValorOpcion() {
		return campoValorOpcion;
	}

	/**
	 * @param campo the campo to set
	 */
	public void setCampoValorOpcion(CampoValorOpcionVO campoValorOpcion) {
		this.campoValorOpcion = campoValorOpcion;
	}

	/**
	 * @return the listTratamiento
	 */
	public List<Tratamiento> getListTratamiento() {
		return listTratamiento;
	}

	/**
	 * @param listTratamiento the listTratamiento to set
	 */
	public void setListTratamiento(List<Tratamiento> listTratamiento) {
		this.listTratamiento = listTratamiento;
	}


   
}