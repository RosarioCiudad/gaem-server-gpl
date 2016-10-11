package ar.gov.rosario.gait.base.iface.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class GaitParam {

	private static Logger log = Logger.getLogger(GaitParam.class);
	private static GaitParam INSTANCE = null;
	private Map<String, String> values = new HashMap<String, String>();
	private Context context;

	public static String TIMEOUT_ANONIMO = "TimeoutAnonimo";
	public static String TIMEOUT_AUTENTICADO = "TimeoutAutenticado";
	public static String FILE_SHARE_PATH = "FileSharePath";
	public static String GENERAL_DB_NAME = "GeneralDbName";
	
	public static String MAIL_SERVER ="MailServer";
	
	public static final String CERTIFICATE_PATH = "CertificatePath";
	public static final String PARAM_URI_SIMGEI = "UriSimgei";
	public static final String PARAM_USER_PASS_SIMGEI = "UserPassSimgei";
	
	public static final String PARAM_URI_SIDOM 		 = "UriSidom";
	public static final String PARAM_USER_PASS_SIMDOM = "UserPassSidom";
	
	public static final String PARAM_URI_SIDOM_IMAGE = "UriSidomImage";
	
	// Decimales a mostrar en la vista
	public static int DEC_IMPORTE_VIEW = 2;
	
	
	// Decimales utilizados en los calculos 
	public static int DEC_IMPORTE_CALC = 6;
	public static int DEC_PORCENTAJE_CALC = 6;
	public static int DEC_INDICE_CALC = 7;
	
	//  Decimales utilizados para guardar en la db
	public static int DEC_IMPORTE_DB = 2;
	public static int DEC_PORCENTAJE_DB = 6;
	public static int DEC_INDICE_DB = 7;
	
	public static String BASE_URL ="BaseURL";
	
	private GaitParam() {
	}
	
	/**
	 * Devuelve unica instancia
	 */
	public synchronized static GaitParam getInstance()  throws Exception {
		if (INSTANCE == null) {
			INSTANCE = new GaitParam();
		}
		return INSTANCE;
	}
	
	/**
	 * Busca key en los valores cargados en GaitParam
	 * Si no lo encuetra los busca en context.xml agregando prefijo: /gait/conf/
	 * Si no lo encuentra retorna null
	 * Si encuetra en alguno de los dos, retorna el valor que primero encuentra.
	 * @param key
	 * @return
	 */
	protected synchronized String getValue(String key) {
		String value = null;
		
		value = values.get(key);
		if (value != null)
			return value;
		
		// si es null, lo buscamos en los paramtros de arquitectura del context.xml
		try {
			String prefix = "java:comp/env/gait/conf/";			
			if (context == null) {
				context = new InitialContext();
			}
			value = (String) context.lookup(prefix + key);
			return value;
		} catch (Exception e) {}
		
		return null;
	}

	public synchronized void updateValues(Map<String, String> m) {		
		this.values.clear();
		this.values.putAll(m); 
	} 
	
	static public String getString(String key) throws Exception {
		String ret = GaitParam.getInstance().getValue(key);
		if (ret == null) throw new IllegalArgumentException("No se encontro el parametro: '" + key + "' en GaitParam y tampoco en context.xml");
		return ret;
	}

	static public String getString(String key, String def) {
		String ret = null;
		try { ret = getString(key); } catch (Exception e) {}
		return ret == null ? def : ret;
	}
	
	static public int getInteger(String key) throws Exception {
		String ret = GaitParam.getInstance().getValue(key);
		if (ret == null) throw new IllegalArgumentException("No se encontro el parametro: '" + key + "' en GaitParam y tampoco en context.xml");
		return Integer.parseInt(ret);
	}

	static public int getInteger(String key, int def) {
		String ret = null;
		try { ret = getString(key); } catch (Exception e) {}
		return ret == null ? def : Integer.parseInt(ret);
	}

	static public double getDouble(String key) throws Exception {
		String ret = GaitParam.getInstance().getValue(key);
		if (ret == null) throw new IllegalArgumentException("No se encontro el parametro: '" + key + "' en GaitParam y tampoco en context.xml");
		return Double.parseDouble(ret);
	}

	static public double getDouble(String key, double def) {
		String ret = null;
		try { ret = getString(key); } catch (Exception e) {}
		return ret == null ? def : Double.parseDouble(ret);
	}

	/**
	 * Obtiene el path formado por: fileSharePath + key 
	 * @param  key
	 * @return String
	 * @throws Exception
	 */
	static public String getPath(String key) throws Exception {
		String fileSharePath = GaitParam.getInstance().getValue(GaitParam.FILE_SHARE_PATH);
		if (fileSharePath == null) throw new IllegalArgumentException("No se encontro el parametro: '" + "fileSharePath" + "' en GaitParam");

		String ret = GaitParam.getInstance().getValue(key);
		if (ret == null) throw new IllegalArgumentException("No se encontro el parametro: '" + key + "' en GaitParam");
		
		return fileSharePath + "/" + ret;
	}

	/**
	 * Verifica si se trata de intranerGait.
	 * @return
	 */
	private static Boolean isIntraGait = null;
	public static boolean isIntranetGait() {
		if (isIntraGait != null) {
			return isIntraGait;
		} else {			
			InputStream stream = new GaitParam().getClass().getClassLoader().getResourceAsStream("/intragait-key");
			isIntraGait = (stream == null) ? false : true; 
		}
		return isIntraGait;
	}

	/**
	 * Verifica si se trata de webGait.
	 * @return
	 */
	private static Boolean isWebGait = null;
	public static boolean isWebGait() {
		if (isWebGait != null) {
			return isWebGait;
		} else { 
			InputStream stream = new GaitParam().getClass().getClassLoader().getResourceAsStream("/webgait-key");
			isWebGait = (stream == null) ? false : true; 
		}
		return isWebGait;
	}

	/**
	 * Lanza una exception si NO se trata de intranetGait
	 * @throws Exception
	 */
	public static void onlyIntranetGait() throws Exception {
		if (!isIntraGait) throw new Exception("Funcion solo valida para Intranet Gait.");
	}

	/**
	 * Lanza una exception si NO se trata de webGait
	 * @throws Exception
	 */
	public static void onlyWebGait() throws Exception {
		if (!isIntraGait) throw new Exception("Funcion solo valida para Web Gait.");
	}

	/** Path al directorio de archivos compartidos */
	public static String getFileSharePath() throws Exception {
		return getString(FILE_SHARE_PATH);
	}

	/** Nombre de la base de datos general */
	public static String getGeneralDbName() throws Exception {
		return getString(GENERAL_DB_NAME);
	}

	public static String version() {
		try {
			String version="";
			
			InputStream stream = new GaitParam().getClass().getClassLoader().getResourceAsStream("/version");
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			version = reader.readLine();
			stream.close();
			
			return version;
		} catch (Exception e) {
			return "?";
		}
	}
}
