package ar.gov.rosario.gait.apm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;

/**
 * Adapter del PerfilAccesoAplicacion
 * 
 * @author tecso
 */
public class PerfilAccesoAplicacionAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "perfilAccesoAplicacionAdapterVO";
	
    private PerfilAccesoAplicacionVO perfilAccesoAplicacion = new PerfilAccesoAplicacionVO();
	
	private List<AplicacionVO> listAplicacion = new ArrayList<>();
    
    // Constructores
    public PerfilAccesoAplicacionAdapter(){
    	super(ApmSecurityConstants.ABM_PERFILACCESOAPLICACION);
    }
    
    //  Getters y Setters
	public PerfilAccesoAplicacionVO getPerfilAccesoAplicacion() {
		return perfilAccesoAplicacion;
	}

	public void setPerfilAccesoAplicacion(PerfilAccesoAplicacionVO perfilAccesoAplicacionVO) {
		this.perfilAccesoAplicacion = perfilAccesoAplicacionVO;
	}
	
	public List<AplicacionVO> getListAplicacion() {
		return listAplicacion;
	}

	public void setListAplicacion(List<AplicacionVO> listAplicacion) {
		this.listAplicacion = listAplicacion;
	}

	public String getName(){
		return NAME;
	}
	
	// View getters
}