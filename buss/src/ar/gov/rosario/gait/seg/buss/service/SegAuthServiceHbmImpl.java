package ar.gov.rosario.gait.seg.buss.service;

import java.util.Map;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.seg.iface.service.ISegAuthService;
import ar.gov.rosario.gait.simgei.auth.AuthenticationWebServiceManager;

import com.sun.xml.ws.client.ClientTransportException;
/**
 * 
 * Implementacion de servicios de seguridad
 * @author tecso
 */


public class SegAuthServiceHbmImpl implements ISegAuthService {
	private Logger log = Logger.getLogger(SegAuthServiceHbmImpl.class);

	@Override
	public boolean auth(String username, String password) throws Exception {
		boolean auth = false;
		try {
			auth = AuthenticationWebServiceManager.getInstance().authenticate(username, password);
		} catch (ClientTransportException e) {
			log.error("**ERROR**", e);
		}
		return auth;
	}
	
	@Override
	public boolean auth(String username, String password, Map<String, Object> attributes) throws Exception {

		return false;
	}

}
