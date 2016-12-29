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
package ar.gov.rosario.gait.def.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;


/**
 * Value Object del Parametro
 * @author tecso
 *
 */
public class ParametroVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "parametroVO";
	
	private String codParam;
	
	private String desParam;

	private String valor;
	
		
	// Constructores
	public ParametroVO() {
		super();
	}
	
	// Constructor para utilizar este VO en un combo como valor SELECCIONAR, TODOS, etc.
	public ParametroVO(int id, String desc) {
		super();
		setId(new Long(id));
		setDesParam(desc);
	}
	
	public String getCodParam() {
		return codParam;
	}

	public void setCodParam(String codParametro) {
		this.codParam = codParametro;
	}

	public String getDesParam() {
		return desParam;
	}

	public void setDesParam(String desParametro) {
		this.desParam = desParametro;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
