package ar.gov.rosario.gait.frm.iface.service;

import org.apache.log4j.Logger;

/**
 * Formulario - Service locator
 * @author tecso
 */
public class FrmServiceLocator {

	static Logger log = Logger.getLogger(FrmServiceLocator.class);

	// Implementaciones de servicio	
	private static String MODULO = "ar.gov.rosario.gait.frm.buss.service.";
	private static String FORMULARIO_SERVICE_IMPL = MODULO + "FrmFormularioServiceHbmImpl";
	private static String NUMERACION_SERVICE_IMPL = MODULO + "FrmNumeracionServiceHbmImpl";
	private static String REPORTE_SERVICE_IMPL = MODULO + "FrmReporteServiceHbmImpl";

	// Instancia
	public static final FrmServiceLocator INSTANCE = new FrmServiceLocator();

	private IFrmFormularioService formularioServiceHbmImpl;
	private IFrmNumeracionService numeracionServiceHbmImpl;
	private IFrmReporteService 	  reporteServiceHbmImpl;

	// Constructor de instancia
	public FrmServiceLocator() {
		try {
			this.formularioServiceHbmImpl = (IFrmFormularioService) Class.forName(FORMULARIO_SERVICE_IMPL).newInstance();
			this.numeracionServiceHbmImpl = (IFrmNumeracionService) Class.forName(NUMERACION_SERVICE_IMPL).newInstance();
			this.reporteServiceHbmImpl 	  = (IFrmReporteService) Class.forName(REPORTE_SERVICE_IMPL).newInstance();
		} catch (Exception e) {
			log.error("No se pudo crear la clase", e);
		}
	}

	public static IFrmFormularioService getFormularioService(){
		return INSTANCE.formularioServiceHbmImpl;		
	}
	
	public static IFrmNumeracionService getNumeracionService(){
		return INSTANCE.numeracionServiceHbmImpl;		
	}
	
	public static IFrmReporteService getReporteService(){
		return INSTANCE.reporteServiceHbmImpl;		
	}
}