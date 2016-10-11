package ar.gov.rosario.gait.apm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class AplPerfilSeccionCampoValorVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplPerfilSeccionCampoValorVO";

	private AplPerfilSeccionCampoVO aplPerfilSeccionCampo = new AplPerfilSeccionCampoVO() ;

	private CampoValorVO campoValor = new CampoValorVO();

	private Integer orden;
	
	private List<AplPerfilSeccionCampoValorOpcionVO> listAplPerfilSeccionCampoValorOpcion = new ArrayList<AplPerfilSeccionCampoValorOpcionVO>();


	// Constructores
	public AplPerfilSeccionCampoValorVO() {
		super();
	}


	// Getters y Setters
	public AplPerfilSeccionCampoVO getAplPerfilSeccionCampo() {
		return aplPerfilSeccionCampo;
	}

	public void setAplPerfilSeccionCampo(AplPerfilSeccionCampoVO aplPerfilSeccionCampo) {
		this.aplPerfilSeccionCampo = aplPerfilSeccionCampo;
	}

	public CampoValorVO getCampoValor() {
		return campoValor;
	}

	public void setCampoValor(CampoValorVO campoValor) {
		this.campoValor = campoValor;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public List<AplPerfilSeccionCampoValorOpcionVO> getListAplPerfilSeccionCampoValorOpcion() {
		return listAplPerfilSeccionCampoValorOpcion;
	}

	public void setListAplPerfilSeccionCampoValorOpcion(List<AplPerfilSeccionCampoValorOpcionVO> listAplPerfilSeccionCampoValorOpcion) {
		this.listAplPerfilSeccionCampoValorOpcion = listAplPerfilSeccionCampoValorOpcion;
	}

}