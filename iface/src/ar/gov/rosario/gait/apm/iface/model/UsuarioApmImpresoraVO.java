package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

/**
 * 
 * @author tecso.coop
 *
 */
public class UsuarioApmImpresoraVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "usuarioApmDMVO";

	private UsuarioApmVO usuarioApm = new UsuarioApmVO();
	private ImpresoraVO impresora = new ImpresoraVO();
	
	// Constructores
	public UsuarioApmImpresoraVO() {
		super();
	}

	public UsuarioApmVO getUsuarioApm() {
		return usuarioApm;
	}

	public void setUsuarioApm(UsuarioApmVO usuarioApm) {
		this.usuarioApm = usuarioApm;
	}

	public ImpresoraVO getImpresora() {
		return impresora;
	}

	public void setImpresora(ImpresoraVO impresora) {
		this.impresora = impresora;
	}
}