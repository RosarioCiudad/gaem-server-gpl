package ar.gov.rosario.gait.simgei.user;


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
public class UsuarioWebServiceManager {

	private static Logger log = Logger.getLogger(UsuarioWebServiceManager.class);

	private static final UsuarioWebServiceManager INSTANCE = new UsuarioWebServiceManager();

	private static final String SERVICE_NAME	 = "UsuarioWebServiceBean?wsdl";
	private URL wsdlLocation;


	/**
	 * Constructor privado
	 */
	private UsuarioWebServiceManager() {
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
	public static UsuarioWebServiceManager getInstance() {
		return INSTANCE;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */

	public UsuarioDto getUsuarioById(final String username) throws Exception {
		UsuarioWebService usuarioService = getUsuarioService();

		return usuarioService.getUsuarioById(username);
	}


	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private UsuarioWebService getUsuarioService() throws Exception{
		UsuarioWebServiceBeanService service = new UsuarioWebServiceBeanService(wsdlLocation);
		return service.getUsuarioWebServiceBeanPort();
	}
}