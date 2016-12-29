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



import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;


/**
 * Adapter del Usuario Apm DM
 * 
 * @author tecso
 */

public class UsuarioApmDMAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "usuarioApmDMAdapterVO";


	private UsuarioApmDMVO usuarioApmDM = new UsuarioApmDMVO();
	private List<UsuarioApmVO> listUsuarioApm = new ArrayList<UsuarioApmVO>();
	private List<DispositivoMovilVO> listDispositivoMovil = new ArrayList<DispositivoMovilVO>();
	
	// Constructores
	public UsuarioApmDMAdapter() {
		super(ApmSecurityConstants.ABM_USUARIOAPMDM);
	}

	// Getters y Setters

	

	public String getName() {
		return NAME;
	}



	public UsuarioApmDMVO getUsuarioApmDM() {
		return usuarioApmDM;
	}

	public void setUsuarioApmDM(UsuarioApmDMVO usuarioApmDM) {
		this.usuarioApmDM = usuarioApmDM;
	}

	/**
	 * @return the listUsuarioApm
	 */
	public List<UsuarioApmVO> getListUsuarioApm() {
		return listUsuarioApm;
	}

	/**
	 * @param listUsuarioApm the listUsuarioApm to set
	 */
	public void setListUsuarioApm(List<UsuarioApmVO> listUsuarioApm) {
		this.listUsuarioApm = listUsuarioApm;
	}

	/**
	 * @return the listDispositivoMovil
	 */
	public List<DispositivoMovilVO> getListDispositivoMovil() {
		return listDispositivoMovil;
	}

	/**
	 * @param listDispositivoMovil the listDispositivoMovil to set
	 */
	public void setListDispositivoMovil(
			List<DispositivoMovilVO> listDispositivoMovil) {
		this.listDispositivoMovil = listDispositivoMovil;
	}




}