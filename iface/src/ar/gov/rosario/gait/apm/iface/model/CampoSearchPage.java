package ar.gov.rosario.gait.apm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import coop.tecso.demoda.iface.model.SiNo;
import coop.tecso.demoda.iface.model.Tratamiento;

/**
 * SearchPage del Campo
 * 
 * @author Tecso
 * 
 */
public class CampoSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "campoSearchPageVO";
										
	private CampoVO campo = new CampoVO();
	private List<SiNo> listSiNo = new ArrayList<SiNo>();
	private List<Tratamiento> listTratamiento = new ArrayList<Tratamiento>();

	// Constructores
	public CampoSearchPage() {
		super(ApmSecurityConstants.ABM_CAMPO);
	}

	// Getters y Setters

	public String getName() {
		return NAME;
	}

	/**
	 * @return the campo
	 */
	public CampoVO getCampo() {
		return campo;
	}

	/**
	 * @param campo
	 *            the campo to set
	 */
	public void setCampo(CampoVO campo) {
		this.campo = campo;
	}

	/**
	 * @return the listSiNo
	 */
	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	/**
	 * @param listSiNo
	 *            the listSiNo to set
	 */
	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}

	/**
	 * @return the listTratamiento
	 */
	public List<Tratamiento> getListTratamiento() {
		return listTratamiento;
	}

	/**
	 * @param listTratamiento
	 *            the listTratamiento to set
	 */
	public void setListTratamiento(List<Tratamiento> listTratamiento) {
		this.listTratamiento = listTratamiento;
	}

}
