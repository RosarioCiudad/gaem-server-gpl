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
import coop.tecso.demoda.iface.model.SiNo;

public class EstadoPanicoVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "estadoPanicoVO";
	
	private String descripcion;
	
	private String transiciones;

	private SiNo esInicial;
	
	public EstadoPanicoVO() {
		super();
	}
	
	public EstadoPanicoVO(int id, String descripcion) {
		super();
		setId(Integer.valueOf(id).longValue());
		setDescripcion(descripcion);
	}

	// Getters y setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTransiciones() {
		return transiciones;
	}

	public void setTransiciones(String transiciones) {
		this.transiciones = transiciones;
	}

	public SiNo getEsInicial() {
		return esInicial;
	}

	public void setEsInicial(SiNo esInicial) {
		this.esInicial = esInicial;
	}
}