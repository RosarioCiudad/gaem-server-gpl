package ar.gov.rosario.gait.apm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import coop.tecso.demoda.iface.model.SiNo;

/**
 * Adapter del Dispositivo movil
 * 
 * @author tecso
 */
public class ImpresoraAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "impresoraAdapterVO";
	
    private ImpresoraVO impresora = new ImpresoraVO();
    
    private List<SiNo> listSiNo = new ArrayList<SiNo>();
    private List<AreaVO> listArea = new ArrayList<>();
    private boolean editarAreaEnabled = false;
    
    // Constructores
    public ImpresoraAdapter(){
    	super(ApmSecurityConstants.ABM_IMPRESORA);
    }
    
    //  Getters y Setters
	public String getName(){
		return NAME;
	}

	public ImpresoraVO getImpresora() {
		return impresora;
	}
	public void setImpresora(ImpresoraVO impresora) {
		this.impresora = impresora;
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
	
	// View getters
}