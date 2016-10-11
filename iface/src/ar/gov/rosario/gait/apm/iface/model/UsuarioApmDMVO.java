package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

/**
 * 
 * @author tecso.coop
 *
 */
public class UsuarioApmDMVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "usuarioApmDMVO";

	private UsuarioApmVO usuarioApm = new UsuarioApmVO();
	private DispositivoMovilVO dispositivoMovil = new DispositivoMovilVO();
	
	
	
	// Constructores
	public UsuarioApmDMVO() {
		super();
	}



	/**
	 * @return the usuarioApm
	 */
	public UsuarioApmVO getUsuarioApm() {
		return usuarioApm;
	}



	/**
	 * @param usuarioApm the usuarioApm to set
	 */
	public void setUsuarioApm(UsuarioApmVO usuarioApm) {
		this.usuarioApm = usuarioApm;
	}



	/**
	 * @return the dispositivoMovil
	 */
	public DispositivoMovilVO getDispositivoMovil() {
		return dispositivoMovil;
	}



	/**
	 * @param dispositivoMovil the dispositivoMovil to set
	 */
	public void setDispositivoMovil(DispositivoMovilVO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}

	// Getters & Setters
	
}