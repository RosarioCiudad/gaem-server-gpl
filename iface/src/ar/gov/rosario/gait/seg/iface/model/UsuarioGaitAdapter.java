package ar.gov.rosario.gait.seg.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.def.iface.model.DireccionVO;
import ar.gov.rosario.gait.seg.iface.util.SegSecurityConstants;

/**
 * Adapter del UsuarioGait
 * 
 * @author tecso
 */
public class UsuarioGaitAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "usuarioGaitAdapterVO";
	
    private UsuarioGaitVO usuarioGait = new UsuarioGaitVO();
    private List<DireccionVO> listDireccion = new ArrayList<>();
    private List<AreaVO> listArea = new ArrayList<>();
    
    // Constructores
    public UsuarioGaitAdapter(){
    	super(SegSecurityConstants.ABM_USUARIOGAIT);
    }
    
	//  Getters y Setters
	public UsuarioGaitVO getUsuarioGait() {
		return usuarioGait;
	}

	public void setUsuarioGait(UsuarioGaitVO usuarioGaitVO) {
		this.usuarioGait = usuarioGaitVO;
	}

	public List<DireccionVO> getListDireccion() {
		return listDireccion;
	}

	public void setListDireccion(List<DireccionVO> listDireccion) {
		this.listDireccion = listDireccion;
	}

	public List<AreaVO> getListArea() {
		return listArea;
	}

	public void setListArea(List<AreaVO> listArea) {
		this.listArea = listArea;
	}

	// View getters
}