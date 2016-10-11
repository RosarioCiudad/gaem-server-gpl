package ar.gov.rosario.gait.frm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;

public class MotivoCierreTipoFormularioAdapter extends GaitAdapterModel {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "motivoCierreTipoFormularioAdapterVO";
										
	private MotivoCierreTipoFormularioVO motivoCierreTipoFormulario = new MotivoCierreTipoFormularioVO();
	
	private List<TipoFormularioVO> listTipoFormulario = new ArrayList<>();
	


	// Constructores
	public MotivoCierreTipoFormularioAdapter() {
		super(FrmSecurityConstants.ABM_MOTIVOCIERRETIPOFORMULARIO);
	}

	// Getters y Setters

	public String getName() {
		return NAME;
	}
	
	public MotivoCierreTipoFormularioVO getMotivoCierreTipoFormulario() {
		return motivoCierreTipoFormulario;
	}

	public void setMotivoCierreTipoFormulario(MotivoCierreTipoFormularioVO motivoCierreTipoFormulario) {
		this.motivoCierreTipoFormulario = motivoCierreTipoFormulario;
	}
	

	public List<TipoFormularioVO> getListTipoFormulario() {
		return listTipoFormulario;
	}

	public void setListTipoFormulario(List<TipoFormularioVO> listTipoFormulario) {
		this.listTipoFormulario = listTipoFormulario;
	}

}
