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
