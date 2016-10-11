package ar.gov.rosario.gait.pro.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.pro.iface.util.ProSecurityConstants;
import coop.tecso.demoda.iface.model.SiNo;

/**
 * SearchPage del Proceso
 * 
 * @author Tecso
 *
 */
public class ProcesoSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "procesoSearchPageVO";
	
	private ProcesoVO proceso= new ProcesoVO();
	
	private List<String> listCodigo = new ArrayList<String>();
	
	private List<SiNo> listSiNo = new ArrayList<SiNo>();
	
	// Constructores
	public ProcesoSearchPage() {       
       super(ProSecurityConstants.ABM_PROCESO);        
    }
	
	// Getters y Setters
	public ProcesoVO getProceso() {
		return proceso;
	}
	public void setProceso(ProcesoVO proceso) {
		this.proceso = proceso;
	}

	public List<String> getListCodigo() {
		return listCodigo;
	}

	public void setListCodigo(List<String> listCodigo) {
		this.listCodigo = listCodigo;
	}

	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}

	// View getters
}
