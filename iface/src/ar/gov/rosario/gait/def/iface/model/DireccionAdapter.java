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

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;
import coop.tecso.demoda.iface.model.SiNo;

/**
 * Adapter de la direccion
 * 
 * @author tecso
 */
public class DireccionAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "direccionAdapterVO";	
	public static final String ENC_NAME = "encDireccionAdapterVO";

	private List<SiNo> listSiNo = new ArrayList<SiNo>();
	
	private DireccionVO direccion = new DireccionVO();

    // Constructores
    public DireccionAdapter(){
    	super(DefSecurityConstants.ABM_DIRECCION);
    }
    
 
	
	// View getters
		// Permisos para ABM Area
	public String getVerAreaEnabled(){
		return GaitBussImageModel.hasEnabledFlag(DefSecurityConstants.ABM_AREA, BaseSecurityConstants.VER);
	}
	public String getModificarAreaEnabled(){
		return GaitBussImageModel.hasEnabledFlag(DefSecurityConstants.ABM_AREA, BaseSecurityConstants.MODIFICAR);
	}
	public String getEliminarAreaEnabled(){
		return GaitBussImageModel.hasEnabledFlag(DefSecurityConstants.ABM_AREA, BaseSecurityConstants.ELIMINAR);
	}
	public String getAgregarAreaEnabled(){
		return GaitBussImageModel.hasEnabledFlag(DefSecurityConstants.ABM_AREA, BaseSecurityConstants.AGREGAR);
	}

	// Permisos para ABM Direccion Aplicacion Perfil
	public String getVerDireccionAplicacionPerfilEnabled(){
		return GaitBussImageModel.hasEnabledFlag(DefSecurityConstants.ABM_DIRECCIONAPLICACIONPERFIL, BaseSecurityConstants.VER);
	}
	public String getModificarDireccionAplicacionPerfilEnabled(){
		return GaitBussImageModel.hasEnabledFlag(DefSecurityConstants.ABM_DIRECCIONAPLICACIONPERFIL, BaseSecurityConstants.MODIFICAR);
	}
	public String getEliminarDireccionAplicacionPerfilEnabled(){
		return GaitBussImageModel.hasEnabledFlag(DefSecurityConstants.ABM_DIRECCIONAPLICACIONPERFIL, BaseSecurityConstants.ELIMINAR);
	}
	public String getAgregarDireccionAplicacionPerfilEnabled(){
		return GaitBussImageModel.hasEnabledFlag(DefSecurityConstants.ABM_DIRECCIONAPLICACIONPERFIL, BaseSecurityConstants.AGREGAR);
	}

	
	
	//Getters and Setters

	public List<SiNo> getListSiNo() {
		return listSiNo;
	}



	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}



	public DireccionVO getDireccion() {
		return direccion;
	}



	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	

		
	 	
	
	// View getters
}
