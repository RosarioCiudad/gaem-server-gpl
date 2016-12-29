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

/**
 * Adapter de Panico
 * 
 * @author tecso
 */
public class PanicoAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;	
	
	public static final String NAME = "panicoAdapterVO";
	public static final String ENC_NAME = "encPanicoAdapterVO";
	public static final String CAMBIOESTADO_NAME = "cambioEstadoPanicoAdapterVO";
	
	private PanicoVO panico = new PanicoVO();
	
	private List<EstadoPanicoVO> listEstadoPanico = new ArrayList<>();
	
	public static final String ACT_CAMBIARESTADO = "cambiarEstado";
	
	private HisEstPanVO hisEstPan = new HisEstPanVO();

	// JSON data
	private String listPendiente = "";
	private String listPosicion  = "";
	private String listAnulado = "";
	private String listAtendido = "";
    
    // Constructores
    public PanicoAdapter(){
    	super(ApmSecurityConstants.ABM_PANICO);
    }

    //  Getters y Setters
	public PanicoVO getPanico() {
		return panico;
	}
	public void setPanico(PanicoVO panico) {
		this.panico = panico;
	}

	// View getters
	public String getListPendiente() {
		return listPendiente;
	}

	public void setListPendiente(String listPendiente) {
		this.listPendiente = listPendiente;
	}

	public String getListPosicion() {
		return listPosicion;
	}

	public void setListPosicion(String listPosicion) {
		this.listPosicion = listPosicion;
	}
	
	public String getListAnulado() {
		return listAnulado;
	}

	public void setListAnulado(String listAnulado) {
		this.listAnulado = listAnulado;
	}

	public String getListAtendido() {
		return listAtendido;
	}

	public void setListAtendido(String listAtendido) {
		this.listAtendido = listAtendido;
	}

	public List<EstadoPanicoVO> getListEstadoPanico() {
		return listEstadoPanico;
	}

	public void setListEstadoPanico(List<EstadoPanicoVO> listEstadoPanico) {
		this.listEstadoPanico = listEstadoPanico;
	}

	public HisEstPanVO getHisEstPan() {
		return hisEstPan;
	}

	public void setHisEstPan(HisEstPanVO hisEstPan) {
		this.hisEstPan = hisEstPan;
	}

	// Permisos para ABM HisEstPan
	public String getVerHisEstPanEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_HISESTPAN, BaseSecurityConstants.VER);
	}
	public String getModificarHisEstPanEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_HISESTPAN, BaseSecurityConstants.MODIFICAR);
	}
	public String getEliminarHisEstPanEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_HISESTPAN, BaseSecurityConstants.ELIMINAR);
	}
	public String getAgregarHisEstPanEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_HISESTPAN, BaseSecurityConstants.AGREGAR);
	}
	public String getCambiarEstadoEnabled(){
		return GaitBussImageModel.hasEnabledFlag(ApmSecurityConstants.ABM_PANICO, ACT_CAMBIARESTADO);
	}
}