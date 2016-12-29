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
package ar.gov.rosario.gait.seg.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.def.iface.model.DireccionVO;
import ar.gov.rosario.gait.seg.iface.util.SegSecurityConstants;

/**
 * Adapter del UsuarioGait
 * 
 * @author tecso
 */
public class UsuarioGaitAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "usuarioGaitAdapterVO";
	
    private UsuarioGaitVO usuarioGait = new UsuarioGaitVO();
    private List<DireccionVO> listDireccion = new ArrayList<>();
    private List<AreaVO> listArea = new ArrayList<>();
    
    // Constructores
    public UsuarioGaitAdapter(){
    	super(SegSecurityConstants.ABM_USUARIOGAIT);
    }
    
	//  Getters y Setters
	public UsuarioGaitVO getUsuarioGait() {
		return usuarioGait;
	}

	public void setUsuarioGait(UsuarioGaitVO usuarioGaitVO) {
		this.usuarioGait = usuarioGaitVO;
	}

	public List<DireccionVO> getListDireccion() {
		return listDireccion;
	}

	public void setListDireccion(List<DireccionVO> listDireccion) {
		this.listDireccion = listDireccion;
	}

	public List<AreaVO> getListArea() {
		return listArea;
	}

	public void setListArea(List<AreaVO> listArea) {
		this.listArea = listArea;
	}

	// View getters
}