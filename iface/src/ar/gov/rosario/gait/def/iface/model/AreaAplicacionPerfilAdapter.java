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



import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;

public class AreaAplicacionPerfilAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "areaAplicacionPerfilAdapterVO";


	private AreaAplicacionPerfilVO areaAplicacionPerfil = new AreaAplicacionPerfilVO();
	private List<AplicacionPerfilVO> listAplicacionPerfil = new ArrayList<AplicacionPerfilVO>();
	
	// Constructores
	public AreaAplicacionPerfilAdapter() {
		super(DefSecurityConstants.ABM_AREAAPLICACIONPERFIL);
	}

	// Getters y Setters
	public String getName() {
		return NAME;
	}

	public List<AplicacionPerfilVO> getListAplicacionPerfil() {
		return listAplicacionPerfil;
	}

	public void setListAplicacionPerfil(
			List<AplicacionPerfilVO> listAplicacionPerfil) {
		this.listAplicacionPerfil = listAplicacionPerfil;
	}

	public AreaAplicacionPerfilVO getAreaAplicacionPerfil() {
		return areaAplicacionPerfil;
	}

	public void setAreaAplicacionPerfil(AreaAplicacionPerfilVO areaAplicacionPerfil) {
		this.areaAplicacionPerfil = areaAplicacionPerfil;
	}
  
}