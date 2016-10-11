package ar.gov.rosario.gait.frm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;

/**
 * SearchPage del  EstadoTipoFormulario
 * 
 * @author Tecso
 * 
 */
public class EstadoTipoFormularioSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "estadoTipoFormularioSearchPageVO";

	private  EstadoTipoFormularioVO  estadoTipoFormulario = new  EstadoTipoFormularioVO();
    private List<TipoFormularioVO> listTipoFormulario = new ArrayList<>();
	
	// Constructores
	public EstadoTipoFormularioSearchPage() {
		super(FrmSecurityConstants.ABM_ESTADOTIPOFORMULARIO);
	}

	// Getters y Setters
	public String getName() {
		return NAME;
	}

	public EstadoTipoFormularioVO getEstadoTipoFormulario() {
		return estadoTipoFormulario;
	}

	public void setEstadoTipoFormulario(EstadoTipoFormularioVO estadoTipoFormulario) {
		this.estadoTipoFormulario = estadoTipoFormulario;
	}

	public List<TipoFormularioVO> getListTipoFormulario() {
		return listTipoFormulario;
	}

	public void setListTipoFormulario(List<TipoFormularioVO> listTipoFormulario) {
		this.listTipoFormulario = listTipoFormulario;
	}	
	
}