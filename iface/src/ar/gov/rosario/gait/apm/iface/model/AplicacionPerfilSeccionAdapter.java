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
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import coop.tecso.demoda.iface.model.SiNo;


/**
 * Adapter del Aplicacion PErfil Seccion
 * 
 * @author tecso
 */

public class AplicacionPerfilSeccionAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionPerfilSeccionAdapterVO";
	public static final String ENC_NAME = "encAplicacionPerfilSeccionAdapterVO";

	private AplicacionPerfilSeccionVO aplicacionPerfilSeccion = new AplicacionPerfilSeccionVO();
	
	private List<SeccionVO> listSeccion = new ArrayList<SeccionVO>();
	
	private List<SiNo> listSiNo = new ArrayList<SiNo>();
	// Constructores
	public AplicacionPerfilSeccionAdapter() {
		super(ApmSecurityConstants.ABM_APLICACIONPERFILSECCION);
	}

	// Getters y Setters

	

	public String getName() {
		return NAME;
	}

	public AplicacionPerfilSeccionVO getAplicacionPerfilSeccion() {
		return aplicacionPerfilSeccion;
	}

	public void setAplicacionPerfilSeccion(AplicacionPerfilSeccionVO aplicacionPerfilSeccion) {
		this.aplicacionPerfilSeccion = aplicacionPerfilSeccion;
	}  


	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}


	public List<SeccionVO> getListSeccion() {
		return listSeccion;
	}

	public void setListSeccion(List<SeccionVO> listSeccion) {
		this.listSeccion = listSeccion;
	}
	
	// View getters
	// Permisos para ABM Perfil Seccion Campo
	public String getVerAplPerfilSeccionCampoEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO, BaseSecurityConstants.VER);
	}
	public String getModificarAplPerfilSeccionCampoEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO, BaseSecurityConstants.MODIFICAR);
	}
	public String getEliminarAplPerfilSeccionCampoEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO, BaseSecurityConstants.ELIMINAR);
	}
	public String getAgregarAplPerfilSeccionCampoEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO, BaseSecurityConstants.AGREGAR);
	}



  
}