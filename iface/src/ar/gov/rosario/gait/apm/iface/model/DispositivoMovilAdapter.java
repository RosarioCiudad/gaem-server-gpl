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
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import coop.tecso.demoda.iface.model.SiNo;

/**
 * Adapter del Dispositivo movil
 * 
 * @author tecso
 */
public class DispositivoMovilAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "dispositivoMovilAdapterVO";
	
    private DispositivoMovilVO dispositivoMovil = new DispositivoMovilVO();
    
    private List<SiNo> listSiNo = new ArrayList<SiNo>();
    private List<AreaVO> listArea = new ArrayList<>();
    private boolean editarAreaEnabled = false;
    
    // Constructores
    public DispositivoMovilAdapter(){
    	super(ApmSecurityConstants.ABM_DISPOSITIVOMOVIL);
    }
    
    //  Getters y Setters
	public String getName(){
		return NAME;
	}

	public DispositivoMovilVO getDispositivoMovil() {
		return dispositivoMovil;
	}
	public void setDispositivoMovil(DispositivoMovilVO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
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
	
	// View getters
}