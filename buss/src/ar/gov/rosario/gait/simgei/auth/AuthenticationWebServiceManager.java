package ar.gov.rosario.gait.simgei.auth;

import static ar.gov.rosario.gait.base.iface.model.GaitParam.PARAM_URI_SIMGEI;
import static ar.gov.rosario.gait.base.iface.model.GaitParam.PARAM_USER_PASS_SIMGEI;
import static ar.gov.rosario.gait.base.iface.model.GaitParam.getString;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * 
 * @author tecso.coop
 *
 */
public class AuthenticationWebServiceManager {

	private static Logger log = Logger.getLogger(AuthenticationWebServiceManager.class);

	private static final AuthenticationWebServiceManager INSTANCE = new AuthenticationWebServiceManager();

	private static final String SERVICE_NAME	 = "AuthenticationWebServiceBean?wsdl";
	private URL wsdlLocation;

	/**
	 * Constructor privado
	 */
	private AuthenticationWebServiceManager() {
		try {
			// WSDL web-service location
			wsdlLocation = new URL(getString(PARAM_URI_SIMGEI)+ "/" +SERVICE_NAME);
			//
			final String userpass = getString(PARAM_USER_PASS_SIMGEI, "$|$");
			Authenticator authenticator = new Authenticator() {
			    @Override
			    protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication(userpass.split("\\|")[0], userpass.split("\\|")[1].toCharArray());
			    }
			};
			Authenticator.setDefault(authenticator);
		} catch (Exception e) {
			log.error("SimgeiWebServiceManager: **ERROR**", e);
		}
	}

	/**
	 * Devuelve unica instancia
	 */
	public static AuthenticationWebServiceManager getInstance() {
		return INSTANCE;
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean authenticate(final String username, final String password) throws Exception {
		
		if ("off".equals(System.getenv().get("GaitAuth"))){
			return true;
		}
		
		AuthenticationWebService authenticationService = getAuthenticationService();
		return authenticationService.authenticate(username, password);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private AuthenticationWebService getAuthenticationService() throws Exception{
		AuthenticationWebServiceBeanService service = new AuthenticationWebServiceBeanService(wsdlLocation);
		return service.getAuthenticationWebServiceBeanPort();
	}
}