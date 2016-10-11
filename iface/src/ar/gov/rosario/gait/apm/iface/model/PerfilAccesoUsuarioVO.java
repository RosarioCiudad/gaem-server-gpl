package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class PerfilAccesoUsuarioVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "perfilAccesoUsuarioVO";

	private PerfilAccesoVO perfilAcceso = new PerfilAccesoVO();
	private UsuarioApmVO usuarioAPM = new UsuarioApmVO();
	
	// Constructores
	public PerfilAccesoUsuarioVO() {
		super();
	}

	public PerfilAccesoVO getPerfilAcceso() {
		return perfilAcceso;
	}

	public void setPerfilAcceso(PerfilAccesoVO perfilAcceso) {
		this.perfilAcceso = perfilAcceso;
	}

	public UsuarioApmVO getUsuarioAPM() {
		return usuarioAPM;
	}

	public void setUsuarioAPM(UsuarioApmVO usuarioAPM) {
		this.usuarioAPM = usuarioAPM;
	}
}