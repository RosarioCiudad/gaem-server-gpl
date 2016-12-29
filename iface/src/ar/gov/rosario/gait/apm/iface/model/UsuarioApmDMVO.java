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

/**
 * 
 * @author tecso.coop
 *
 */
public class UsuarioApmDMVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "usuarioApmDMVO";

	private UsuarioApmVO usuarioApm = new UsuarioApmVO();
	private DispositivoMovilVO dispositivoMovil = new DispositivoMovilVO();
	
	
	
	// Constructores
	public UsuarioApmDMVO() {
		super();
	}



	/**
	 * @return the usuarioApm
	 */
	public UsuarioApmVO getUsuarioApm() {
		return usuarioApm;
	}



	/**
	 * @param usuarioApm the usuarioApm to set
	 */
	public void setUsuarioApm(UsuarioApmVO usuarioApm) {
		this.usuarioApm = usuarioApm;
	}



	/**
	 * @return the dispositivoMovil
	 */
	public DispositivoMovilVO getDispositivoMovil() {
		return dispositivoMovil;
	}



	/**
	 * @param dispositivoMovil the dispositivoMovil to set
	 */
	public void setDispositivoMovil(DispositivoMovilVO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}

	// Getters & Setters
	
}