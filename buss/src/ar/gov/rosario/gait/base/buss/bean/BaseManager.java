package ar.gov.rosario.gait.base.buss.bean;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;

/**
 * Manejador del m&oacute;dulo Administraci&oacute;n
 * 
 * @author tecso
 *
 */
public class BaseManager {
	
	private static Logger log = Logger.getLogger(BaseManager.class);
	
	public static final BaseManager INSTANCE = new BaseManager();
	
	/**
	 * Constructor privado
	 */
	private BaseManager() {
		
	}
	
	/**
	 * Devuelve unica instancia
	 */
	public static BaseManager getInstance() {
		return INSTANCE;
	}

	public Integer updateTablaVersion(String sName) {
		log.debug(" updateTablaVersion : enter - sName: " + sName);
		return ApmDAOFactory.getTablaVersionDAO().updateVersionable(sName);
	}
	
	
}