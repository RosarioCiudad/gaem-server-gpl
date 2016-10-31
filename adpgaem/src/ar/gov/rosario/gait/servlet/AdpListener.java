

package ar.gov.rosario.gait.servlet; 

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.def.iface.service.DefServiceLocator;
import coop.tecso.adpcore.AdpClientHandler;
import coop.tecso.adpcore.AdpEngine;
import coop.tecso.adpcore.AdpRun;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.UserContext;

public class AdpListener implements ServletContextListener {
	private static AdpEngine engine = null;

    public void contextInitialized(ServletContextEvent event) {
		try {
	    	System.out.println("INICIANDO ADPgaem...");
	    	System.out.println("  INICIANDO Gaem");
			DefServiceLocator.getConfiguracionService().initializeGait();
			
			if (engine == null) {
		    	System.out.println("  INICIANDO Adp Engine");
    			AdpEngine.setclientHandler(new AdpGaitHandler());
    			engine = new AdpEngine();
    			engine.start();		
			}
	    	System.out.println("INICIANDO ADPgaem... OK");
			
		} catch (Exception e) {
			System.out.println("**************************************");
			System.out.println("ADPgaem ERROR:");
			System.out.println("No se pudo Inicializar Gaem.");
			System.out.println("El comportamiento de la aplicacion es inesperado.");
			System.out.println("El error fue: " + e);
			e.printStackTrace();
			System.out.println("**************************************");
		}
    }

    public void contextDestroyed(ServletContextEvent event) {
		try {
	    	System.out.println("DESTRUYENDO ADPgaem...");
			if (engine != null) {
		    	System.out.println("  DESTRUYENDO Adp Engine");
				engine.stop();
			}
	    	System.out.println("  DESTRUYENDO Gaem");
	    	DefServiceLocator.getConfiguracionService().destroyGait();
	    	System.out.println("DESTRUYENDO ADPgaem... OK");
		} catch (Exception e) {
			System.out.println("**************************************");
			System.out.println("ADPgaem ERROR:");
			System.out.println("No se pudo Destruir gait.");
			System.out.println("El comportamiento de la aplicacion es inesperado.");
			System.out.println("El error fue: " + e);
			e.printStackTrace();
			System.out.println("**************************************");
		}
   }
    
    class AdpGaitHandler implements AdpClientHandler {
    	Logger log = Logger.getLogger(AdpGaitHandler.class);
    	
    	@Override
    	public void startExecute(AdpRun run) {
    		UserContext userContext = new UserContext();
    		userContext.setUserName(run.getUsuario() == null ? "" : run.getUsuario());
    		DemodaUtil.setCurrentUserContext(userContext);
    	}

    	@Override
    	public String currentUserName() {
    		try {
    			return DemodaUtil.currentUserContext().getUserName();
    		} catch (DemodaServiceException e) {
    			log.info("No se puede obtener informacion del usuario para Adp. Usamos valor por defecto", e);
    			return "gaem";
    		}
    	}
    }

}
