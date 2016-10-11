package ar.gov.rosario.gait.apm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;

public class PerfilAccesoSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "perfilAccesoSearchPageVO";

	private PerfilAccesoVO perfilAcceso = new PerfilAccesoVO();
	private List<AreaVO> listArea = new ArrayList<>();

	// Constructores
	public PerfilAccesoSearchPage() {
		super(ApmSecurityConstants.ABM_PERFILACCESO);
	}

	// Getters y Setters
	public String getName() {
		return NAME;
	}

	public PerfilAccesoVO getPerfilAcceso() {
		return perfilAcceso;
	}

	public void setPerfilAcceso(PerfilAccesoVO perfilAcceso) {
		this.perfilAcceso = perfilAcceso;
	}

	public List<AreaVO> getListArea() {
		return listArea;
	}

	public void setListArea(List<AreaVO> listArea) {
		this.listArea = listArea;
	}

}
