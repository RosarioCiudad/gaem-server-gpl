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
 * Adapter del Usuario Impresora
 * 
 * @author tecso
 */

public class UsuarioApmImpresoraAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "usuarioApmImpresoraAdapterVO";

	private UsuarioApmImpresoraVO usuarioApmImpresora = new UsuarioApmImpresoraVO();
	private List<ImpresoraVO> listImpresora = new ArrayList<>();
	
	// Constructores
	public UsuarioApmImpresoraAdapter() {
		super(ApmSecurityConstants.ABM_USUARIOAPMIMPRESORA);
	}

	// Getters y Setters
	public String getName() {
		return NAME;
	}

	
	public UsuarioApmImpresoraVO getUsuarioApmImpresora() {
		return usuarioApmImpresora;
	}

	public void setUsuarioApmImpresora(UsuarioApmImpresoraVO usuarioApmImpresora) {
		this.usuarioApmImpresora = usuarioApmImpresora;
	}

	public List<ImpresoraVO> getListImpresora() {
		return listImpresora;
	}

	public void setListImpresora(List<ImpresoraVO> listImpresora) {
		this.listImpresora = listImpresora;
	}
}