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
import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import coop.tecso.demoda.iface.model.SiNo;

/**
 * SearchPage del Impresora
 * 
 * @author Tecso
 *
 */
public class ImpresoraSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "impresoraSearchPageVO";
	
	private ImpresoraVO impresora= new ImpresoraVO();
	
	private List<SiNo> listSiNo  = new ArrayList<>();
    private List<AreaVO> listArea = new ArrayList<>();
    private boolean editarAreaEnabled = false;
	
	// Constructores
	public ImpresoraSearchPage() {       
       super(ApmSecurityConstants.ABM_IMPRESORA);        
    }
	
	// Getters y Setters
    public String getName(){    
		return NAME;
	}

	public ImpresoraVO getImpresora() {
		return impresora;
	}

	public void setImpresora(ImpresoraVO impresora) {
		this.impresora = impresora;
	}

	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}

	public List<AreaVO> getListArea() {
		return listArea;
	}

	public void setListArea(List<AreaVO> listArea) {
		this.listArea = listArea;
	}

	public boolean isEditarAreaEnabled() {
		return editarAreaEnabled;
	}

	public void setEditarAreaEnabled(boolean editarAreaEnabled) {
		this.editarAreaEnabled = editarAreaEnabled;
	}
}
