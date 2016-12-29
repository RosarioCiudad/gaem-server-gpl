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

/**
 * Adapter del DireccionAplicacionPerfil
 * 
 * @author tecso
 */
public class DireccionAplicacionPerfilAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "direccionAplicacionPerfilAdapterVO";
	
    private DireccionAplicacionPerfilVO direccionAplicacionPerfil = new DireccionAplicacionPerfilVO();
    
	private List<DireccionVO> listDireccion = new ArrayList<DireccionVO>();
	
	private List<AplicacionPerfilVO> listAplicacionPerfil = new ArrayList<AplicacionPerfilVO>();
	
    // Constructores
    public DireccionAplicacionPerfilAdapter(){
    	super(DefSecurityConstants.ABM_DIRECCIONAPLICACIONPERFIL);
    }
    
    //  Getters y Setters
	public DireccionAplicacionPerfilVO getDireccionAplicacionPerfil() {
		return direccionAplicacionPerfil;
	}

	public void setDireccionAplicacionPerfil(DireccionAplicacionPerfilVO direccionAplicacionPerfilVO) {
		this.direccionAplicacionPerfil = direccionAplicacionPerfilVO;
	}

	public List<DireccionVO> getListDireccion() {
		return listDireccion;
	}

	public void setListDireccion(List<DireccionVO> listDireccion) {
		this.listDireccion = listDireccion;
	}

	public List<AplicacionPerfilVO> getListAplicacionPerfil() {
		return listAplicacionPerfil;
	}

	public void setListAplicacionPerfil(List<AplicacionPerfilVO> listAplicacionPerfil) {
		this.listAplicacionPerfil = listAplicacionPerfil;
	}

	// View getters
}
