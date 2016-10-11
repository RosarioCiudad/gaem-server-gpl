package ar.gov.rosario.gait.apm.iface.model;



import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;


/**
 * Adapter del Usuario Apm DM
 * 
 * @author tecso
 */

public class UsuarioApmDMAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "usuarioApmDMAdapterVO";


	private UsuarioApmDMVO usuarioApmDM = new UsuarioApmDMVO();
	private List<UsuarioApmVO> listUsuarioApm = new ArrayList<UsuarioApmVO>();
	private List<DispositivoMovilVO> listDispositivoMovil = new ArrayList<DispositivoMovilVO>();
	
	// Constructores
	public UsuarioApmDMAdapter() {
		super(ApmSecurityConstants.ABM_USUARIOAPMDM);
	}

	// Getters y Setters

	

	public String getName() {
		return NAME;
	}



	public UsuarioApmDMVO getUsuarioApmDM() {
		return usuarioApmDM;
	}

	public void setUsuarioApmDM(UsuarioApmDMVO usuarioApmDM) {
		this.usuarioApmDM = usuarioApmDM;
	}

	/**
	 * @return the listUsuarioApm
	 */
	public List<UsuarioApmVO> getListUsuarioApm() {
		return listUsuarioApm;
	}

	/**
	 * @param listUsuarioApm the listUsuarioApm to set
	 */
	public void setListUsuarioApm(List<UsuarioApmVO> listUsuarioApm) {
		this.listUsuarioApm = listUsuarioApm;
	}

	/**
	 * @return the listDispositivoMovil
	 */
	public List<DispositivoMovilVO> getListDispositivoMovil() {
		return listDispositivoMovil;
	}

	/**
	 * @param listDispositivoMovil the listDispositivoMovil to set
	 */
	public void setListDispositivoMovil(
			List<DispositivoMovilVO> listDispositivoMovil) {
		this.listDispositivoMovil = listDispositivoMovil;
	}




}