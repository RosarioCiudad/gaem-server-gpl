package ar.gov.rosario.gait.def.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;
import coop.tecso.demoda.iface.model.SiNo;

/**
 * SearchPage del Direccion
 * 
 * @author Tecso
 *
 */
public class DireccionSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "direccionSearchPageVO";
	
	private DireccionVO direccion= new DireccionVO();
	private List<SiNo> listSiNo = new ArrayList<SiNo>();
	// Constructores
	public DireccionSearchPage() {       
       super(DefSecurityConstants.ABM_DIRECCION);        
    }
	
	// Getters y Setters
	public DireccionVO getDireccion() {
		return direccion;
	}
	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}           

    public String getName(){    
		return NAME;
	}

	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}
	
	// View getters
}
