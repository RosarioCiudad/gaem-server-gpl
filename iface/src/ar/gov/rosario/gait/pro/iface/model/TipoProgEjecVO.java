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
 * Representa los tipos de programas que se pueden utilizar para delegar la ejecucion de un proceso.
 * @author tecso
 *
 */
public class TipoProgEjecVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "tipoProEjecVO";

	private String desTipoProgEjec;
	
	// Constructores
	
	public TipoProgEjecVO(){
		super();
	}
	public TipoProgEjecVO(int id, String desTipoProgEjec){
		super(id);
		setDesTipoProgEjec(desTipoProgEjec);
	}
	// Getters y Setters
	public String getDesTipoProgEjec(){
		return desTipoProgEjec;
	}
	public void setDesTipoProgEjec(String desTipoProgEjec){
		this.desTipoProgEjec = desTipoProgEjec;
	}

}
