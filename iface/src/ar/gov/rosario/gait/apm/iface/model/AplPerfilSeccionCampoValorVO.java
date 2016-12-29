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