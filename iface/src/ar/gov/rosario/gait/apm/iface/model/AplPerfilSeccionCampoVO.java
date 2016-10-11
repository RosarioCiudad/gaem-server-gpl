package ar.gov.rosario.gait.apm.iface.model;

import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import coop.tecso.demoda.iface.model.SiNo;

public class AplPerfilSeccionCampoVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplPerfilSeccionCampoVO";

	private AplicacionPerfilSeccionVO aplicacionPerfilSeccion = new AplicacionPerfilSeccionVO();

	private CampoVO campo = new CampoVO();

	private SiNo obligatorio = SiNo.OpcionTodo;
	private SiNo soloLectura = SiNo.OpcionTodo;
	
	private List<AplPerfilSeccionCampoValorVO> listAplPerfilSeccionCampoValor;

	private Integer orden;

	// Constructores
	public AplPerfilSeccionCampoVO() {
		super();
	}


	// Getters y Setters
	public AplicacionPerfilSeccionVO getAplicacionPerfilSeccion() {
		return aplicacionPerfilSeccion;
	}

	public void setAplicacionPerfilSeccion(AplicacionPerfilSeccionVO aplicacionPerfilSeccion) {
		this.aplicacionPerfilSeccion = aplicacionPerfilSeccion;
	}

	public CampoVO getCampo() {
		return campo;
	}

	public void setCampo(CampoVO campo) {
		this.campo = campo;
	}


	public List<AplPerfilSeccionCampoValorVO> getListAplPerfilSeccionCampoValor() {
		return listAplPerfilSeccionCampoValor;
	}

	public void setListAplPerfilSeccionCampoValor(List<AplPerfilSeccionCampoValorVO> listAplPerfilSeccionCampoValor) {
		this.listAplPerfilSeccionCampoValor = listAplPerfilSeccionCampoValor;
	}


	/**
	 * @return the obligatorio
	 */
	public SiNo getObligatorio() {
		return obligatorio;
	}


	/**
	 * @param obligatorio the obligatorio to set
	 */
	public void setObligatorio(SiNo obligatorio) {
		this.obligatorio = obligatorio;
	}


	/**
	 * @return the soloLectura
	 */
	public SiNo getSoloLectura() {
		return soloLectura;
	}


	/**
	 * @param soloLectura the soloLectura to set
	 */
	public void setSoloLectura(SiNo soloLectura) {
		this.soloLectura = soloLectura;
	}


	public Integer getOrden() {
		return orden;
	}


	public void setOrden(Integer orden) {
		this.orden = orden;
	}


}