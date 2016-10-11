package ar.gov.rosario.gait.def.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;

/**
 * Adapter del DireccionAplicacionPerfil
 * 
 * @author tecso
 */
public class DireccionAplicacionPerfilAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "direccionAplicacionPerfilAdapterVO";
	
    private DireccionAplicacionPerfilVO direccionAplicacionPerfil = new DireccionAplicacionPerfilVO();
    
	private List<DireccionVO> listDireccion = new ArrayList<DireccionVO>();
	
	private List<AplicacionPerfilVO> listAplicacionPerfil = new ArrayList<AplicacionPerfilVO>();
	
    // Constructores
    public DireccionAplicacionPerfilAdapter(){
    	super(DefSecurityConstants.ABM_DIRECCIONAPLICACIONPERFIL);
    }
    
    //  Getters y Setters
	public DireccionAplicacionPerfilVO getDireccionAplicacionPerfil() {
		return direccionAplicacionPerfil;
	}

	public void setDireccionAplicacionPerfil(DireccionAplicacionPerfilVO direccionAplicacionPerfilVO) {
		this.direccionAplicacionPerfil = direccionAplicacionPerfilVO;
	}

	public List<DireccionVO> getListDireccion() {
		return listDireccion;
	}

	public void setListDireccion(List<DireccionVO> listDireccion) {
		this.listDireccion = listDireccion;
	}

	public List<AplicacionPerfilVO> getListAplicacionPerfil() {
		return listAplicacionPerfil;
	}

	public void setListAplicacionPerfil(List<AplicacionPerfilVO> listAplicacionPerfil) {
		this.listAplicacionPerfil = listAplicacionPerfil;
	}

	// View getters
}
