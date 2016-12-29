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