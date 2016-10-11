package ar.gov.rosario.gait.base.view.util; 

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ar.gov.rosario.gait.def.iface.service.DefServiceLocator;

public class GaitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
		try {
	    	System.out.println("INICIANDO GAIT...");
			DefServiceLocator.getConfiguracionService().initializeGait();
		} catch (Exception e) {
			System.out.println("**************************************");
			System.out.println("GAIT ERROR:");
			System.out.println("No se pudo Inicializar GAIT.");
			System.out.println("El comportamiento de la aplicacion es inesperado.");
			System.out.println("El error fue: " + e);
			e.printStackTrace();
			System.out.println("**************************************");
		}
    }

    public void contextDestroyed(ServletContextEvent event) {
		try {
	    	System.out.println("DESTRUYENDO GAIT...");
			DefServiceLocator.getConfiguracionService().destroyGait();
		} catch (Exception e) {
			System.out.println("**************************************");
			System.out.println("GAIT ERROR:");
			System.out.println("No se pudo Destruir GAIT.");
			System.out.println("El comportamiento de la aplicacion es inesperado.");
			System.out.println("El error fue: " + e);
			e.printStackTrace();
			System.out.println("**************************************");
		}
   }
}
