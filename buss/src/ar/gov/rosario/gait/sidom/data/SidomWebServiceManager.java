package ar.gov.rosario.gait.sidom.data;

import static ar.gov.rosario.gait.base.iface.model.GaitParam.PARAM_URI_SIDOM;
import static ar.gov.rosario.gait.base.iface.model.GaitParam.PARAM_URI_SIDOM_IMAGE;
import static ar.gov.rosario.gait.base.iface.model.GaitParam.PARAM_USER_PASS_SIMDOM;
import static ar.gov.rosario.gait.base.iface.model.GaitParam.getString;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;

/**
 * 
 * @author tecso.coop
 *
 */
public class SidomWebServiceManager {

	private static Logger log = Logger.getLogger(SidomWebServiceManager.class);

	private static final SidomWebServiceManager INSTANCE = new SidomWebServiceManager();


	public InfoUsuario login() throws Exception {
		final String userpass = getString(PARAM_USER_PASS_SIMDOM, "$|$");
		return login(userpass.split("\\|")[0], userpass.split("\\|")[1]);
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public InfoUsuario login(final String username, final String password) throws Exception {
		//
		Map<String, String> params = new HashMap<String, String>();
		params.put("usuario", username);
		params.put("clave", password);

		String response = webGet("Login", params);
		//
		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();  
		InfoUsuarioHandler handler = new InfoUsuarioHandler();
		saxParser.parse(new ByteArrayInputStream(response.getBytes()), handler);

		return handler.getInfoUsuario();
	}

	/**
	 * 
	 * @param hash
	 * @param iddocumento
	 * @return
	 * @throws Exception
	 */
	public DocumentoResult obtenerDocumento(final String hash, final Integer iddocumento) throws Exception {
		//
		Map<String, String> params = new HashMap<String, String>();
		params.put("hash", hash);
		params.put("iddocumento", iddocumento.toString());

		String response = webGet("ObtenerDocumento", params);
		//
		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();  
		DocumentoResultHandler handler = new DocumentoResultHandler();
		saxParser.parse(new ByteArrayInputStream(response.getBytes()), handler);

		return handler.getDocumentoResult();
	}


	/**
	 * 
	 * @param hash
	 * @param iddocumento
	 * @return
	 * @throws Exception
	 */
	public byte[] obtenerArchivoRestPorPID(final String pid) throws Exception {
		//
		Map<String, String> params = new HashMap<String, String>();
		params.put("pid", pid);

		return webGetImage("ObtenerArchivoRestPorPID", params);
	}


	/**
	 * Makes a HttpGet/WebGet on a Web service.
	 */
	public String webGet(String methodName, Map<String, String> params) throws Exception {
		// Build URL
		StringBuffer getUrl = new StringBuffer(getString(PARAM_URI_SIDOM));
		getUrl.append("/");
		getUrl.append(methodName);
		int i = 0;
		for (Map.Entry<String, String> param : params.entrySet()) {
			if(i++ == 0) {
				getUrl.append("?");
			} else {
				getUrl.append("&");
			}
			try {
				getUrl.append(param.getKey()+"="+URLEncoder.encode(param.getValue(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("**ERROR**", e);
			} 
		}
		log.debug(String.format("URL: %s", getUrl.toString()));

		URL url = new URL(getUrl.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

		InputStream stream = connection.getInputStream();
		//put output stream into a string
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
		StringBuffer result = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			result.append(line);
		}
		bufferedReader.close();
		connection.disconnect();

		return result.toString();
	}

	/**
	 * Makes a HttpGet/WebGet on a Web service.
	 */
	public byte[] webGetImage(String methodName, Map<String, String> params) throws Exception {
		// Build URL
		StringBuffer getUrl = new StringBuffer(getString(PARAM_URI_SIDOM_IMAGE));
		getUrl.append("/");
		getUrl.append(methodName);
		int i = 0;
		for (Map.Entry<String, String> param : params.entrySet()) {
			if(i++ == 0) {
				getUrl.append("?");
			} else {
				getUrl.append("&");
			}
			try {
				getUrl.append(param.getKey()+"="+URLEncoder.encode(param.getValue(),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				log.error("**ERROR**", e);
			} 
		}
		log.debug(String.format("URL: %s", getUrl.toString()));
		
		URL url = new URL(getUrl.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

		InputStream is = connection.getInputStream();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		buffer.close();
		connection.disconnect();

		return buffer.toByteArray();
	}

	/**
	 * Constructor privado
	 */
	private SidomWebServiceManager() {
		try {
			// WSDL web-service location
		} catch (Exception e) {
			log.error("**ERROR**", e);
		}
	}

	/**
	 * Devuelve unica instancia
	 */
	public static SidomWebServiceManager getInstance() {
		return INSTANCE;
	}
}