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
package ar.gov.rosario.gait.pro.buss.bean;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.pro.buss.dao.ProDAOFactory;

/**
 * Manejador del modulo Pro
 * 
 * @author tecso
 *
 */
public class ProManager {
		
	private static Logger log = Logger.getLogger(ProManager.class);
	
	public static final ProManager INSTANCE = new ProManager();
	
	/**
	 * Constructor privado
	 */
	private ProManager() {
		
	}
	
	/**
	 * Devuelve unica instancia
	 */
	public static ProManager getInstance() {
		return INSTANCE;
	}

	// ---> ABM 	
		
	public Corrida deleteCorrida(Corrida corrida) throws Exception {

		// Validaciones de negocio
		if (!corrida.validateDelete()) {
			return corrida;
		}
		
		ProDAOFactory.getCorridaDAO().delete(corrida);
		
		return corrida;
	}
	// <--- ABM Corrida	

	// ---> ABM proceso
	public Proceso createProceso(Proceso proceso) throws Exception {

		// Validaciones de negocio
		if (!proceso.validateCreate()) {
			return proceso;
		}
		
		ProDAOFactory.getProcesoDAO().update(proceso);
				
		return proceso;
	}

	
	public Proceso updateProceso(Proceso proceso) throws Exception {
		
		// Validaciones de negocio
		if (!proceso.validateUpdate()) {
			return proceso;
		}

		ProDAOFactory.getProcesoDAO().update(proceso);
		
		return proceso;
	}
	
	public Proceso deleteProceso(Proceso proceso) throws Exception {
	
		// Validaciones de negocio
		if (!proceso.validateDelete()) {
			return proceso;
		}
		
		ProDAOFactory.getProcesoDAO().delete(proceso);
		
		return proceso;
	}
	
	// <--- ABM Proceso
}

