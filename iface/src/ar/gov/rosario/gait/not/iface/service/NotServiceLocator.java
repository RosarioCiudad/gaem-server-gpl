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
package ar.gov.rosario.gait.not.iface.service;

import org.apache.log4j.Logger;

public class NotServiceLocator {

	static Logger log = Logger.getLogger(NotServiceLocator.class);

	// Implementaciones de servicio	
	private static String MODULO = "ar.gov.rosario.gait.not.buss.service.";
	private static String NOTIFICACION_SERVICE_IMPL = MODULO + "NotNotificacionServiceHbmImpl";
														  
	
	// Instancia
	public static final NotServiceLocator INSTANCE = new NotServiceLocator();

	private INotNotificacionService   notificacionServiceHbmImpl;
	
	
	
	// Constructor de instancia
	public NotServiceLocator() {
		try {

			this.notificacionServiceHbmImpl = (INotNotificacionService)   Class.forName(NOTIFICACION_SERVICE_IMPL).newInstance();			
			
		} catch (Exception e) {
			log.error("No se pudo crear la clase" + e);
		}
	}

	public static INotNotificacionService getNotificacionService(){
		return INSTANCE.notificacionServiceHbmImpl;	
	}
	
	
}
