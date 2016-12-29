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
package ar.gov.rosario.gait.seg.iface.service;

import org.apache.log4j.Logger;

/**
 * Gait - Service locator
 * @author tecso
 */
public class SegServiceLocator {

	static Logger log = Logger.getLogger(SegServiceLocator.class);
	
	// Implementaciones de servicio
	private static String MODULO = "ar.gov.rosario.gait.seg.buss.service.";
	private static String SEGURIDAD_SERVICE_IMPL = MODULO + "SegSeguridadServiceHbmImpl";
	private static String AUTH_SERVICE_IMPL = MODULO + "SegAuthServiceHbmImpl";
	
	// instancia
	public static final SegServiceLocator INSTANCE = new SegServiceLocator();

	private ISegSeguridadService seguridadServiceHbmImpl;
	private ISegAuthService authServiceHbmImpl;

	// constructor de instancia
	public SegServiceLocator() {
		
		try {

			this.seguridadServiceHbmImpl = (ISegSeguridadService) 
				Class.forName(SEGURIDAD_SERVICE_IMPL).newInstance();

			this.authServiceHbmImpl = (ISegAuthService) 
					Class.forName(AUTH_SERVICE_IMPL).newInstance();

		} catch (Exception e) {
			log.error("No se pudo crear la clase" + e);
		}

	}

	public static ISegSeguridadService getSeguridadService() {
		return INSTANCE.seguridadServiceHbmImpl;
	}
	
	public static ISegAuthService getAuthService() {
		return INSTANCE.authServiceHbmImpl;
	}
}
