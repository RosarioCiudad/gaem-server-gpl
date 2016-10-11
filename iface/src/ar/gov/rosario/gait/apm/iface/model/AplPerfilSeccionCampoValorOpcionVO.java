package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class AplPerfilSeccionCampoValorOpcionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplPerfilSeccionCampoValorOpcionVO";

	private AplPerfilSeccionCampoValorVO aplPerfilSeccionCampoValor = new AplPerfilSeccionCampoValorVO();

	private CampoValorOpcionVO  campoValorOpcion = new CampoValorOpcionVO();

	private Integer orden;


	// Constructores
	public AplPerfilSeccionCampoValorOpcionVO() {
		super();
	}


	// Getters y Setters
	public AplPerfilSeccionCampoValorVO getAplPerfilSeccionCampoValor() {
		return aplPerfilSeccionCampoValor;
	}

	public void setAplPerfilSeccionCampoValor(AplPerfilSeccionCampoValorVO aplPerfilSeccionCampoValor) {
		this.aplPerfilSeccionCampoValor = aplPerfilSeccionCampoValor;
	}

	public CampoValorOpcionVO getCampoValorOpcion() {
		return campoValorOpcion;
	}

	public void setCampoValorOpcion(CampoValorOpcionVO campoValorOpcion) {
		this.campoValorOpcion = campoValorOpcion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}


}