package ar.gov.rosario.gait.apm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import coop.tecso.demoda.iface.model.SiNo;

/**
 * SearchPage del DispositivoMovil
 * 
 * @author Tecso
 *
 */
public class DispositivoMovilSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "dispositivoMovilSearchPageVO";
	
	private DispositivoMovilVO dispositivoMovil= new DispositivoMovilVO();
	
	private List<SiNo> listSiNo  = new ArrayList<>();
    private List<AreaVO> listArea = new ArrayList<>();
    private boolean editarAreaEnabled = false;
	
	// Constructores
	public DispositivoMovilSearchPage() {       
       super(ApmSecurityConstants.ABM_DISPOSITIVOMOVIL);        
    }
	
	// Getters y Setters
    public String getName(){    
		return NAME;
	}

	public DispositivoMovilVO getDispositivoMovil() {
		return dispositivoMovil;
	}

	public void setDispositivoMovil(DispositivoMovilVO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}

	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}

	public List<AreaVO> getListArea() {
		return listArea;
	}

	public void setListArea(List<AreaVO> listArea) {
		this.listArea = listArea;
	}

	public boolean isEditarAreaEnabled() {
		return editarAreaEnabled;
	}

	public void setEditarAreaEnabled(boolean editarAreaEnabled) {
		this.editarAreaEnabled = editarAreaEnabled;
	}
}
