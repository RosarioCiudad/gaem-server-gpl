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
 * Adapter del Aplicacion PErfil Campo
 * 
 * @author tecso
 */

public class AplPerfilSeccionCampoValorAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplPerfilSeccionCampoValorAdapterVO";
	public static final String ENC_NAME = "encAplPerfilSeccionCampoValorAdapterVO";

	private AplPerfilSeccionCampoValorVO aplPerfilSeccionCampoValor = new AplPerfilSeccionCampoValorVO();
	

	private List<CampoValorVO> listCampoValor = new ArrayList<CampoValorVO>();
	
	private List<SiNo> listSiNo = new ArrayList<SiNo>();
	// Constructores
	public AplPerfilSeccionCampoValorAdapter() {
		super(ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR);
	}

	// Getters y Setters

	

	public String getName() {
		return NAME;
	}

	


	public AplPerfilSeccionCampoValorVO getAplPerfilSeccionCampoValor() {
		return aplPerfilSeccionCampoValor;
	}

	public void setAplPerfilSeccionCampoValor(AplPerfilSeccionCampoValorVO aplPerfilSeccionCampoValor) {
		this.aplPerfilSeccionCampoValor = aplPerfilSeccionCampoValor;
	}

	
	
	// View getters
	// Permisos para ABM Perfil Seccion Campo Valor Opcion
	public String getVerAplPerfilSeccionCampoValorOpcionEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR_OPCION, BaseSecurityConstants.VER);
	}
	public String getModificarAplPerfilSeccionCampoValorOpcionEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR_OPCION, BaseSecurityConstants.MODIFICAR);
	}
	public String getEliminarAplPerfilSeccionCampoValorOpcionEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR_OPCION, BaseSecurityConstants.ELIMINAR);
	}
	public String getAgregarAplPerfilSeccionCampoValorOpcionEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR_OPCION, BaseSecurityConstants.AGREGAR);
	}

	public List<CampoValorVO> getListCampoValor() {
		return listCampoValor;
	}

	public void setListCampoValor(List<CampoValorVO> listCampoValor) {
		this.listCampoValor = listCampoValor;
	}

	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}



  
}