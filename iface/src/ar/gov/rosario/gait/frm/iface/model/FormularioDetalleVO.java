package ar.gov.rosario.gait.frm.iface.model;

import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoVO;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorOpcionVO;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorVO;
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class FormularioDetalleVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "formularioDetalleVO";
	
	private FormularioVO formulario = new FormularioVO();

	private AplPerfilSeccionCampoVO tipoFormularioDefSeccionCampo = new AplPerfilSeccionCampoVO();

	private AplPerfilSeccionCampoValorVO tipoFormularioDefSeccionCampoValor = new AplPerfilSeccionCampoValorVO();

	private AplPerfilSeccionCampoValorOpcionVO tipoFormularioDefSeccionCampoValorOpcion = new AplPerfilSeccionCampoValorOpcionVO();

	private String imagen = "";

	private String valor = "";

	// Getters y setters
	public FormularioVO getFormulario() {
		return formulario;
	}

	public void setFormulario(FormularioVO formulario) {
		this.formulario = formulario;
	}

	public AplPerfilSeccionCampoVO getTipoFormularioDefSeccionCampo() {
		return tipoFormularioDefSeccionCampo;
	}

	public void setTipoFormularioDefSeccionCampo(
			AplPerfilSeccionCampoVO tipoFormularioDefSeccionCampo) {
		this.tipoFormularioDefSeccionCampo = tipoFormularioDefSeccionCampo;
	}

	public AplPerfilSeccionCampoValorVO getTipoFormularioDefSeccionCampoValor() {
		return tipoFormularioDefSeccionCampoValor;
	}

	public void setTipoFormularioDefSeccionCampoValor(
			AplPerfilSeccionCampoValorVO tipoFormularioDefSeccionCampoValor) {
		this.tipoFormularioDefSeccionCampoValor = tipoFormularioDefSeccionCampoValor;
	}

	public AplPerfilSeccionCampoValorOpcionVO getTipoFormularioDefSeccionCampoValorOpcion() {
		return tipoFormularioDefSeccionCampoValorOpcion;
	}

	public void setTipoFormularioDefSeccionCampoValorOpcion(
			AplPerfilSeccionCampoValorOpcionVO tipoFormularioDefSeccionCampoValorOpcion) {
		this.tipoFormularioDefSeccionCampoValorOpcion = tipoFormularioDefSeccionCampoValorOpcion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}