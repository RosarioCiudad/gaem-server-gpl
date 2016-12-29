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

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
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
public class AreaAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "areaAdapterVO";	
	public static final String ENC_NAME = "encAreaAdapterVO";

	private List<SiNo> listSiNo = new ArrayList<>();	
	private AreaVO area = new AreaVO();
	private List<AreaAplicacionPerfilVO> listAreaAplicacionPerfil = new ArrayList<>();
	
    // Constructores
    public AreaAdapter(){
    	super(DefSecurityConstants.ABM_AREA);
    }

	//Getters and Setters
	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}

	public AreaVO getArea() {
		return area;
	}

	public void setArea(AreaVO area) {
		this.area = area;
	}
	
	public List<AreaAplicacionPerfilVO> getListAreaAplicacionPerfil() {
		return listAreaAplicacionPerfil;
	}
	
	public void setListAreaAplicacionPerfil(List<AreaAplicacionPerfilVO> listAreaAplicacionPerfil) {
		this.listAreaAplicacionPerfil = listAreaAplicacionPerfil;
	}
	
	// Permisos para ABM Perfil Acceso
	public String getVerPerfilAccesoEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_PERFILACCESO, BaseSecurityConstants.VER);
	}
	public String getModificarPerfilAccesoEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_PERFILACCESO, BaseSecurityConstants.MODIFICAR);
	}
	public String getEliminarPerfilAccesoEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_PERFILACCESO, BaseSecurityConstants.ELIMINAR);
	}
	public String getAgregarPerfilAccesoEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_PERFILACCESO, BaseSecurityConstants.AGREGAR);
	}
	
	// Permisos para ABM TelefonoPanico
	public String getVerTelefonoPanicoEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_TELEFONOPANICO, BaseSecurityConstants.VER);
	}
	public String getModificarTelefonoPanicoEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_TELEFONOPANICO, BaseSecurityConstants.MODIFICAR);
	}
	public String getEliminarTelefonoPanicoEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_TELEFONOPANICO, BaseSecurityConstants.ELIMINAR);
	}
	public String getAgregarTelefonoPanicoEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_TELEFONOPANICO, BaseSecurityConstants.AGREGAR);
	}
}