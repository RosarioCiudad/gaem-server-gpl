package ar.gov.rosario.gait.frm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;


/**
 * SearchPage del Tipo Formulario
 * 
 * @author Tecso
 * 
 */
public class TipoFormularioSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "tipoFormularioSearchPageVO";
										
	private TipoFormularioVO tipoFormulario = new TipoFormularioVO();
	


	public TipoFormularioVO getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormularioVO tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	// Constructores
	public TipoFormularioSearchPage() {
		super(FrmSecurityConstants.ABM_TIPOFORMULARIO);
	}

	// Getters y Setters

	public String getName() {
		return NAME;
	}


	

}
