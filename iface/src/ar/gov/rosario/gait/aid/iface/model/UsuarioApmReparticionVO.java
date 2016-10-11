package ar.gov.rosario.gait.aid.iface.model;

import ar.gov.rosario.gait.apm.iface.model.UsuarioApmVO;
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import ar.gov.rosario.gait.frm.iface.model.TipoFormularioVO;

public class UsuarioApmReparticionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "usuarioApmReparticionVO";
	
	private UsuarioApmVO usuarioApm = new UsuarioApmVO();
	private ReparticionVO reparticion = new ReparticionVO();
	private TipoFormularioVO tipoFormulario = new TipoFormularioVO();
	private Integer numeroInspector;
	
	// Getters y setters
	public UsuarioApmVO getUsuarioApm() {
		return usuarioApm;
	}
	public void setUsuarioApm(UsuarioApmVO usuarioApm) {
		this.usuarioApm = usuarioApm;
	}
	public ReparticionVO getReparticion() {
		return reparticion;
	}
	public void setReparticion(ReparticionVO reparticion) {
		this.reparticion = reparticion;
	}
	public TipoFormularioVO getTipoFormulario() {
		return tipoFormulario;
	}
	public void setTipoFormulario(TipoFormularioVO tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}
	public Integer getNumeroInspector() {
		return numeroInspector;
	}
	public void setNumeroInspector(Integer numeroInspector) {
		this.numeroInspector = numeroInspector;
	}
}