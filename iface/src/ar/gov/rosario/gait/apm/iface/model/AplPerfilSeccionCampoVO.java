/*******************************************************************************
 * Copyright (c) 2016 Municipalidad de Rosario, Coop. de Trabajo Tecso Ltda.
 *
 * This file is part of GAEM.
 *
 * GAEM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * GAEM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAEM.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
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