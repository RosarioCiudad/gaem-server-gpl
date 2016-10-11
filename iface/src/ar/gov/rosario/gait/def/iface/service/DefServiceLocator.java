package ar.gov.rosario.gait.def.iface.service;

import org.apache.log4j.Logger;

/**
 * Def - Service locator
 * @author tecso
 */
public class DefServiceLocator {

	static Logger log = Logger.getLogger(DefServiceLocator.class);
	
	private static String MODULO = "ar.gov.rosario.gait.def.buss.service.";
	
	private static String CONFIGURACION_IMPL = MODULO + "DefConfiguracionServiceHbmImpl";
	
	// instancia
	public static final DefServiceLocator INSTANCE = new DefServiceLocator();

	private IDefConfiguracionService configuracionServiceHbmImpl;
	
	// Constructor de instancia
	public DefServiceLocator() {
		try {
			
			this.configuracionServiceHbmImpl = (IDefConfiguracionService) Class.forName(CONFIGURACION_IMPL).newInstance();
			
		} catch (Exception e) {
			log.error("No se pudo crear la clase" + e);
		}
	}

	public static IDefConfiguracionService getConfiguracionService() {
		return INSTANCE.configuracionServiceHbmImpl;
	}
}
