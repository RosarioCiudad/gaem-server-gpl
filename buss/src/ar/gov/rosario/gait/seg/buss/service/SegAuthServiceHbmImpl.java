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
package ar.gov.rosario.gait.seg.buss.service;

import java.util.Map;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.seg.iface.service.ISegAuthService;
/**
 * 
 * Implementacion de servicios de seguridad
 * @author tecso
 */


public class SegAuthServiceHbmImpl implements ISegAuthService {
	private Logger log = Logger.getLogger(SegAuthServiceHbmImpl.class);

	@Override
	public boolean auth(String username, String password) throws Exception {
		return auth(username, password, null);
	}
	
	@Override
	public boolean auth(String username, String password, Map<String, Object> attributes) throws Exception {
		
		//En este método se implementan las políticas de seguridad extra para la autenticación. Puede ser una aconsulta
		//a un servicio de autentificación (webService, Ldap). Por ejemplo:
		//return new LdapGait().login(username, password, attributes);
		
		return true;
	}

}
