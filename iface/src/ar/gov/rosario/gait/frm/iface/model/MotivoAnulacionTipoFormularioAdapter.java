package ar.gov.rosario.gait.frm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;

public class MotivoAnulacionTipoFormularioAdapter extends GaitAdapterModel {
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "motivoAnulacionTipoFormularioAdapterVO";
										
	private MotivoAnulacionTipoFormularioVO motivoAnulacionTipoFormulario = new MotivoAnulacionTipoFormularioVO();
	
	private List<TipoFormularioVO> listTipoFormulario = new ArrayList<>();
	


	// Constructores
	public MotivoAnulacionTipoFormularioAdapter() {
		super(FrmSecurityConstants.ABM_MOTIVOANULACIONTIPOFORMULARIO);
	}

	// Getters y Setters

	public String getName() {
		return NAME;
	}
	
	public MotivoAnulacionTipoFormularioVO getMotivoAnulacionTipoFormulario() {
		return motivoAnulacionTipoFormulario;
	}

	public void setMotivoAnulacionTipoFormulario(MotivoAnulacionTipoFormularioVO motivoAnulacionTipoFormulario) {
		this.motivoAnulacionTipoFormulario = motivoAnulacionTipoFormulario;
	}
	

	public List<TipoFormularioVO> getListTipoFormulario() {
		return listTipoFormulario;
	}

	public void setListTipoFormulario(List<TipoFormularioVO> listTipoFormulario) {
		this.listTipoFormulario = listTipoFormulario;
	}

}

