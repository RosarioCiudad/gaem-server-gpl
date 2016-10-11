package ar.gov.rosario.gait.def.iface.model;



import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;

public class AreaAplicacionPerfilAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "areaAplicacionPerfilAdapterVO";


	private AreaAplicacionPerfilVO areaAplicacionPerfil = new AreaAplicacionPerfilVO();
	private List<AplicacionPerfilVO> listAplicacionPerfil = new ArrayList<AplicacionPerfilVO>();
	
	// Constructores
	public AreaAplicacionPerfilAdapter() {
		super(DefSecurityConstants.ABM_AREAAPLICACIONPERFIL);
	}

	// Getters y Setters
	public String getName() {
		return NAME;
	}

	public List<AplicacionPerfilVO> getListAplicacionPerfil() {
		return listAplicacionPerfil;
	}

	public void setListAplicacionPerfil(
			List<AplicacionPerfilVO> listAplicacionPerfil) {
		this.listAplicacionPerfil = listAplicacionPerfil;
	}

	public AreaAplicacionPerfilVO getAreaAplicacionPerfil() {
		return areaAplicacionPerfil;
	}

	public void setAreaAplicacionPerfil(AreaAplicacionPerfilVO areaAplicacionPerfil) {
		this.areaAplicacionPerfil = areaAplicacionPerfil;
	}
  
}