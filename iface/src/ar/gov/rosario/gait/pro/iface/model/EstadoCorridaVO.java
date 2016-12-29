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
package ar.gov.rosario.gait.pro.iface.model;

import coop.tecso.demoda.iface.model.BussImageModel;

public class EstadoCorridaVO extends BussImageModel {
	private static final long serialVersionUID = 0;

	private String desEstadoCorrida; // VARCHAR(100) NOT NULL,

	// Contructores	
	public EstadoCorridaVO() {
		super();
	}
	// Constructor para utilizar este VO en un combo como valor SELECCIONAR, TODOS, etc.
	public EstadoCorridaVO(int id, String desEstadoCorrida) {
		super();
		setId(new Long(id));
		setDesEstadoCorrida(desEstadoCorrida);
	}
	

	// Getters y Setters	
	public String getDesEstadoCorrida() {
		return desEstadoCorrida;
	}
	public void setDesEstadoCorrida(String desEstadoCorrida) {
		this.desEstadoCorrida = desEstadoCorrida;
	}
}
