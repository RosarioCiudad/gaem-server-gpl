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
 * Value Object del LogCorrida
 * @author tecso
 *
 */
public class LogCorridaVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "logCorridaVO";
	
	private CorridaVO corrida = new CorridaVO();
	
	private Long paso;
	
	private String log;
	
	private String nivelView;
	
	// Constructores
	public LogCorridaVO() {
		super();
	}

	//	 Getters y Setters
	
	public CorridaVO getCorrida() {
		return corrida;
	}

	public void setCorrida(CorridaVO corrida) {
		this.corrida = corrida;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Long getPaso() {
		return paso;
	}

	public void setPaso(Long paso) {
		this.paso = paso;
	}

	public String getNivelView() {
		return nivelView;
	}

	public void setNivelView(String nivelView) {
		this.nivelView = nivelView;
	}
	

	// Buss flags getters y setters
	
	
	// View flags getters
	
	
	// View getters
}
