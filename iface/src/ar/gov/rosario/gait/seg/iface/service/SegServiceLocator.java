package ar.gov.rosario.gait.seg.iface.service;

import org.apache.log4j.Logger;

/**
 * Gait - Service locator
 * @author tecso
 */
public class SegServiceLocator {

	static Logger log = Logger.getLogger(SegServiceLocator.class);
	
	// Implementaciones de servicio
	private static String MODULO = "ar.gov.rosario.gait.seg.buss.service.";
	private static String SEGURIDAD_SERVICE_IMPL = MODULO + "SegSeguridadServiceHbmImpl";
	private static String AUTH_SERVICE_IMPL = MODULO + "SegAuthServiceHbmImpl";
	
	// instancia
	public static final SegServiceLocator INSTANCE = new SegServiceLocator();

	private ISegSeguridadService seguridadServiceHbmImpl;
	private ISegAuthService authServiceHbmImpl;

	// constructor de instancia
	public SegServiceLocator() {
		
		try {

			this.seguridadServiceHbmImpl = (ISegSeguridadService) 
				Class.forName(SEGURIDAD_SERVICE_IMPL).newInstance();

			this.authServiceHbmImpl = (ISegAuthService) 
					Class.forName(AUTH_SERVICE_IMPL).newInstance();

		} catch (Exception e) {
			log.error("No se pudo crear la clase" + e);
		}

	}

	public static ISegSeguridadService getSeguridadService() {
		return INSTANCE.seguridadServiceHbmImpl;
	}
	
	public static ISegAuthService getAuthService() {
		return INSTANCE.authServiceHbmImpl;
	}
}
