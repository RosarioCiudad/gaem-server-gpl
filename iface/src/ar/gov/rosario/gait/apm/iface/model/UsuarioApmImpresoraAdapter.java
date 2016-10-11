package ar.gov.rosario.gait.apm.iface.model;



import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;


/**
 * Adapter del Usuario Impresora
 * 
 * @author tecso
 */

public class UsuarioApmImpresoraAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "usuarioApmImpresoraAdapterVO";

	private UsuarioApmImpresoraVO usuarioApmImpresora = new UsuarioApmImpresoraVO();
	private List<ImpresoraVO> listImpresora = new ArrayList<>();
	
	// Constructores
	public UsuarioApmImpresoraAdapter() {
		super(ApmSecurityConstants.ABM_USUARIOAPMIMPRESORA);
	}

	// Getters y Setters
	public String getName() {
		return NAME;
	}

	
	public UsuarioApmImpresoraVO getUsuarioApmImpresora() {
		return usuarioApmImpresora;
	}

	public void setUsuarioApmImpresora(UsuarioApmImpresoraVO usuarioApmImpresora) {
		this.usuarioApmImpresora = usuarioApmImpresora;
	}

	public List<ImpresoraVO> getListImpresora() {
		return listImpresora;
	}

	public void setListImpresora(List<ImpresoraVO> listImpresora) {
		this.listImpresora = listImpresora;
	}
}