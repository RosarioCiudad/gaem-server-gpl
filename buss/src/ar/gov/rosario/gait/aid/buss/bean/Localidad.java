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
package ar.gov.rosario.gait.aid.buss.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import coop.tecso.demoda.buss.bean.VersionableBO;

@Entity
@Table(name="aid_localidad")
public class Localidad extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="codigoPostal")
	private Integer codigoPostal;

	@Column(name="codigoSubPostal")
	private Integer codigoSubPostal;

	@Column(name="provPostal")
	private String provPostal;


	// Constructores
	public Localidad() {
		super();
	}


	// Getters y Setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Integer getCodigoSubPostal() {
		return codigoSubPostal;
	}

	public void setCodigoSubPostal(Integer codigoSubPostal) {
		this.codigoSubPostal = codigoSubPostal;
	}

	public String getProvPostal() {
		return provPostal;
	}

	public void setProvPostal(String provPostal) {
		this.provPostal = provPostal;
	}


}