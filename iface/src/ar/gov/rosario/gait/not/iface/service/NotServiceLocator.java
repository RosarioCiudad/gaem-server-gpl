package ar.gov.rosario.gait.not.iface.service;

import org.apache.log4j.Logger;

public class NotServiceLocator {

	static Logger log = Logger.getLogger(NotServiceLocator.class);

	// Implementaciones de servicio	
	private static String MODULO = "ar.gov.rosario.gait.not.buss.service.";
	private static String NOTIFICACION_SERVICE_IMPL = MODULO + "NotNotificacionServiceHbmImpl";
														  
	
	// Instancia
	public static final NotServiceLocator INSTANCE = new NotServiceLocator();

	private INotNotificacionService   notificacionServiceHbmImpl;
	
	
	
	// Constructor de instancia
	public NotServiceLocator() {
		try {

			this.notificacionServiceHbmImpl = (INotNotificacionService)   Class.forName(NOTIFICACION_SERVICE_IMPL).newInstance();			
			
		} catch (Exception e) {
			log.error("No se pudo crear la clase" + e);
		}
	}

	public static INotNotificacionService getNotificacionService(){
		return INSTANCE.notificacionServiceHbmImpl;	
	}
	
	
}
