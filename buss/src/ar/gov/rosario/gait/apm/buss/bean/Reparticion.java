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
package ar.gov.rosario.gait.apm.buss.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import coop.tecso.demoda.buss.bean.VersionableBO;

@Entity
@Table(name="apm_reparticion")
public class Reparticion extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@Column(name="idUsuarioApm")
	private UsuarioApm usuarioApm;

	@Column(name="tipoActa")
	private String tipoActa;

	@Column(name="numeroInspector")
	private Integer numeroInspector;

	@Column(name="codigoReparticion")
	private Integer codigoReparticion;

	// Constructores
	public Reparticion() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
	public UsuarioApm getUsuarioApm() {
		return usuarioApm;
	}

	public void setUsuarioApm(UsuarioApm usuarioApm) {
		this.usuarioApm = usuarioApm;
	}

	public String getTipoActa() {
		return tipoActa;
	}

	public void setTipoActa(String tipoActa) {
		this.tipoActa = tipoActa;
	}

	public Integer getNumeroInspector() {
		return numeroInspector;
	}

	public void setNumeroInspector(Integer numeroInspector) {
		this.numeroInspector = numeroInspector;
	}

	public Integer getCodigoReparticion() {
		return codigoReparticion;
	}

	public void setCodigoReparticion(Integer codigoReparticion) {
		this.codigoReparticion = codigoReparticion;
	}


}