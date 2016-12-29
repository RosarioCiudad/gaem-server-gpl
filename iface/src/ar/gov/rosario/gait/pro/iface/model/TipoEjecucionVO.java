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

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

/**
 * Representa los tipos de ejecucion. 
 * @author tecso
 *
 */
public class TipoEjecucionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "tipoEjecucionVO";

	private String desTipoEjecucion;
	
	// Constructores
	public TipoEjecucionVO(){
		super();
	}
	public TipoEjecucionVO(int id, String desTipoEjecucion) {
		super(id);
		setDesTipoEjecucion(desTipoEjecucion);
	}
	// Getters y Setters
	public String getDesTipoEjecucion(){
		return desTipoEjecucion;
	}
	public void setDesTipoEjecucion(String desTipoEjecucion){
		this.desTipoEjecucion = desTipoEjecucion;
	}
}
