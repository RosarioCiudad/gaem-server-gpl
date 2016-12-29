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