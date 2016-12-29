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
package ar.gov.rosario.gait.apm.buss.bean;

import java.util.Date;

import org.apache.log4j.Logger;

import coop.tecso.demoda.iface.helper.DateUtil;
import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.iface.util.BaseError;

/**
 * Manejador del modulo Apm y submodulo Panico
 * 
 * @author tecso
 *
 */
public class ApmPanicoManager {
		
	private static Logger log = Logger.getLogger(ApmPanicoManager.class);
	
	private static final ApmPanicoManager INSTANCE = new ApmPanicoManager();
	
	/**
	 * Constructor privado
	 */
	private ApmPanicoManager() {
		
	}
	
	/**
	 * Devuelve unica instancia
	 */
	public static ApmPanicoManager getInstance() {
		return INSTANCE;
	}

	// ---> ABM Panico
	public Panico createPanico(Panico panico) throws Exception {

		// Validaciones de negocio
		if (!panico.validateCreate()) {
			return panico;
		}

		ApmDAOFactory.getPanicoDAO().update(panico);

		return panico;
	}
	
	public Panico updatePanico(Panico panico) throws Exception {
		
		// Validaciones de negocio
		if (!panico.validateUpdate()) {
			return panico;
		}

		ApmDAOFactory.getPanicoDAO().update(panico);
		
		return panico;
	}
	
	public Panico deletePanico(Panico panico) throws Exception {
	
		// Validaciones de negocio
		if (!panico.validateDelete()) {
			return panico;
		}
		
		ApmDAOFactory.getPanicoDAO().delete(panico);
		
		return panico;
	}
	// <--- ABM Panico
	
	
	// ---> ABM HisEstPan
	public HisEstPan createHisEstPan(HisEstPan hisEstPan) throws Exception {

		// Validaciones de negocio
		if (!hisEstPan.validateCreate()) {
			return hisEstPan;
		}

		ApmDAOFactory.getHisEstPanDAO().update(hisEstPan);

		return hisEstPan;
	}
	
	public HisEstPan updateHisEstPan(HisEstPan hisEstPan) throws Exception {
		
		// Validaciones de negocio
		if (!hisEstPan.validateUpdate()) {
			return hisEstPan;
		}

		ApmDAOFactory.getHisEstPanDAO().update(hisEstPan);
		
		return hisEstPan;
	}
	
	public HisEstPan deleteHisEstPan(HisEstPan hisEstPan) throws Exception {
	
		// Validaciones de negocio
		if (!hisEstPan.validateDelete()) {
			return hisEstPan;
		}
		
		ApmDAOFactory.getHisEstPanDAO().delete(hisEstPan);
		
		return hisEstPan;
	}
	// <--- ABM HisEstPan
	
	
	/**
	 *  Cambia el Estado del Panico al pasado como parametro. 
	 *  Crea un registro historico de cambio con la observacion indicada.
	 *
	 * @param idNuevoEstado
	 * @param observacion
	 * @param fecha
	 * @throws Exception
	 */
	public void cambiarEstado(Panico panico, Long idNuevoEstado, String observacion, Date fecha) throws Exception{
		
		// Validaciones para el cambio de estado
		if(fecha == null){
			panico.addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.HISESTPAN_FECHA);
		}
		
		EstadoPanico estadoPanico = EstadoPanico.getByIdNull(idNuevoEstado);
		if(estadoPanico == null){
			panico.addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.ESTADOPANICO_LABEL);
		}
		
		if(panico.hasError()){
			return;
		}
		
		String logCambios = "";
		if (null != estadoPanico) 		
			logCambios += "Estado Anterior: " + panico.getEstadoPanico().getDescripcion()
				+" , Estado Nuevo: " + estadoPanico.getDescripcion()
				+" , Fecha de Cambio de Estado: "+ DateUtil.formatDate(new Date(), DateUtil.ddSMMSYYYY_MASK);			
		
		HisEstPan hisEstPan = new HisEstPan();
		
		hisEstPan.setObservaciones(observacion);
		hisEstPan.setFecha(fecha);		
		hisEstPan.setEstadoPanico(estadoPanico);
		hisEstPan.setPanico(panico);
		hisEstPan.setLogCambios(logCambios);
		
		panico.setEstadoPanico(estadoPanico);
		
		hisEstPan = createHisEstPan(hisEstPan);		
		ApmDAOFactory.getPanicoDAO().update(panico);
		
		hisEstPan.passErrorMessages(panico);
	}
}