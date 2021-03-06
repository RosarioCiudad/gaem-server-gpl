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
package ar.gov.rosario.gait.pro.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.pro.iface.util.ProSecurityConstants;
import coop.tecso.demoda.iface.model.SiNo;

/**
 * Adapter del Proceso
 * 
 * @author tecso
 */
public class ProcesoAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;

	public static final String NAME = "procesoAdapterVO";
	
    private ProcesoVO proceso = new ProcesoVO();
    
    private List<SiNo> listSiNo = new ArrayList<SiNo>();
    
    private List<TipoProgEjecVO> listTipoProgEjec = new ArrayList<TipoProgEjecVO>();
    
    private List<TipoEjecucionVO> listTipoEjecucion = new ArrayList<TipoEjecucionVO>();
        
    private Boolean paramPeriodic = false;
    
    // Constructores
    public ProcesoAdapter(){
    	super(ProSecurityConstants.ABM_PROCESO);
    }
    
    //  Getters y Setters
	public ProcesoVO getProceso() {
		return proceso;
	}

	public void setProceso(ProcesoVO procesoVO) {
		this.proceso = procesoVO;
	}

	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}

	public List<TipoProgEjecVO> getListTipoProgEjec() {
		return listTipoProgEjec;
	}

	public void setListTipoProgEjec(List<TipoProgEjecVO> listTipoProgEjecVO) {
		this.listTipoProgEjec = listTipoProgEjecVO;
	}

	public List<TipoEjecucionVO> getListTipoEjecucion() {
		return listTipoEjecucion;
	}
	public void setListTipoEjecucion(List<TipoEjecucionVO> listTipoEjecucionVO) {
		this.listTipoEjecucion = listTipoEjecucionVO;
	}
	public Boolean getParamPeriodic() {
		return paramPeriodic;
	}
	public void setParamPeriodic(Boolean paramPeriodic) {
		this.paramPeriodic = paramPeriodic;
	}	
	// View getters
}
