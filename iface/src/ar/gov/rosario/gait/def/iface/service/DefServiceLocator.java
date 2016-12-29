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
package ar.gov.rosario.gait.def.iface.service;

import org.apache.log4j.Logger;

/**
 * Def - Service locator
 * @author tecso
 */
public class DefServiceLocator {

	static Logger log = Logger.getLogger(DefServiceLocator.class);
	
	private static String MODULO = "ar.gov.rosario.gait.def.buss.service.";
	
	private static String CONFIGURACION_IMPL = MODULO + "DefConfiguracionServiceHbmImpl";
	
	// instancia
	public static final DefServiceLocator INSTANCE = new DefServiceLocator();

	private IDefConfiguracionService configuracionServiceHbmImpl;
	
	// Constructor de instancia
	public DefServiceLocator() {
		try {
			
			this.configuracionServiceHbmImpl = (IDefConfiguracionService) Class.forName(CONFIGURACION_IMPL).newInstance();
			
		} catch (Exception e) {
			log.error("No se pudo crear la clase" + e);
		}
	}

	public static IDefConfiguracionService getConfiguracionService() {
		return INSTANCE.configuracionServiceHbmImpl;
	}
}
