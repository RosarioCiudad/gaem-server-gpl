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
package ar.gov.rosario.gait.apsv;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import com.sun.xml.xwss.SecurityConfigurationFactory;
import com.sun.xml.xwss.XWSSecurityConfiguration;

import ar.gov.rosario.gait.base.iface.model.GaitParam;
import coop.tecso.demoda.util.To;

public class ApsvfWebServiceManager {

	private static Logger log = Logger.getLogger(ApsvfWebServiceManager.class);

	private static final ApsvfWebServiceManager INSTANCE = new ApsvfWebServiceManager();
	private static final String paramUriConsultaAPSV = "uriConsultaAPSV";

	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";

	private URL wsdlLocation;
	
	private String configFilePath;

	private To to = new To();

	/**
	 * Constructor privado
	 */
	private ApsvfWebServiceManager() {
		try {
			// Security configuration file
			configFilePath = GaitParam.getFileSharePath() + File.separator 
							+ "conf" + File.separator + "gait_security_config.xml";
			
			// WSDL web-service location
			wsdlLocation = new URL(GaitParam.getString(paramUriConsultaAPSV));
		} catch (Exception e) {
			log.error("TmfWebServiceManager: **ERROR**", e);
		}
	}

	/**
	 * Devuelve unica instancia
	 */
	public static ApsvfWebServiceManager getInstance() {
		return INSTANCE;
	}

	/**
	 * 
	 * @param codDoc: segun tabla tipo_doc
	 * @param nroDoc:
	 * @param sexo: M o F
	 * @return
	 * @throws Exception
	 */
	public ObtenerPersonaResponse obtenerPersona(Integer codDoc, Integer nroDoc, String sexo) throws Exception{
		// 
		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setLogin(LOGIN);
		requestInfo.setPassword(PASSWORD);

		ObtenerPersonaRequest request = new ObtenerPersonaRequest();
		request.setRequestInfo(requestInfo);
		if(null != codDoc) request.setCodDoc(codDoc);
		if(null != sexo) request.setSexo(sexo);
		request.setNroDoc(nroDoc);

		Apsv apsv = getApsvService();
		return apsv.obtenerPersona(request);
	}

	/**
	 * 
	 * @param codDoc: segun tabla tipo_doc
	 * @param nroDoc:
	 * @return
	 * @throws Exception
	 */
	public ObtenerPersonaResponse obtenerPersona(Integer codDoc, Integer nroDoc) throws Exception{
		return this.obtenerPersona(codDoc, nroDoc, null);
	}
	/**
	 * 
	 * @param idPersona
	 * @param claseLicencia
	 * @return
	 * @throws Exception
	 */
	public ObtenerLicenciaResponse obtenerLicencia(Integer idPersona, String claseLicencia) throws Exception {
		// 
		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setLogin(LOGIN);
		requestInfo.setPassword(PASSWORD);

		ObtenerLicenciaRequest request = new ObtenerLicenciaRequest();
		request.setRequestInfo(requestInfo);
		request.setIdPersona(idPersona);
		if(null != claseLicencia)
			request.setClaseLicencia(claseLicencia);

		Apsv apsv = getApsvService();
		return apsv.obtenerLicencia(request);
	}

	/**
	 * 
	 * @param idPersona
	 * @param claseLicencia
	 * @return
	 * @throws Exception
	 */
	public ObtenerNoAptitudResponse obtenerNoAptitud(Integer idPersona) throws Exception{
		// 
		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setLogin(LOGIN);
		requestInfo.setPassword(PASSWORD);

		ObtenerNoAptitudRequest request = new ObtenerNoAptitudRequest();
		request.setRequestInfo(requestInfo);
		request.setIdPersona(idPersona);

		Apsv apsv = getApsvService();
		return apsv.obtenerNoAptitud(request);
	}

	/**
	 * 
	 * @param idPersona
	 * @param claseLicencia
	 * @return
	 * @throws Exception
	 */
	public ObtenerInhabilitacionResponse obtenerInhabilitacion(Integer idPersona, String claseLicencia) throws Exception{
		// 
		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setLogin(LOGIN);
		requestInfo.setPassword(PASSWORD);

		ObtenerInhabilitacionRequest request = new ObtenerInhabilitacionRequest();
		request.setRequestInfo(requestInfo);
		request.setIdPersona(idPersona);
		if(null != claseLicencia)
			request.setClaseLicencia(claseLicencia);

		Apsv apsv = getApsvService();
		return apsv.obtenerInhabilitacion(request);
	}


	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Apsv getApsvService() throws Exception{
		ApsvService service = new ApsvService(wsdlLocation);
		Apsv apsv = service.getApsvSoap11();
		// XWebService Configuration
        XWSSecurityConfiguration config = 
        		SecurityConfigurationFactory.newXWSSecurityConfiguration(new FileInputStream(configFilePath));

         // Put the security configuration info
        ((BindingProvider)apsv).getRequestContext().
            put(XWSSecurityConfiguration.MESSAGE_SECURITY_CONFIGURATION, config);

		return apsv;
	}
}