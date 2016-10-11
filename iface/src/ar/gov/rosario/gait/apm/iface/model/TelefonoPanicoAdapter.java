package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import coop.tecso.demoda.iface.model.ReportVO;

/**
 * Adapter del TelefonoPanico
 * 
 * @author tecso
 */
public class TelefonoPanicoAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "telefonoPanicoAdapterVO";
	
    private TelefonoPanicoVO telefonoPanico = new TelefonoPanicoVO();
    
    // Constructores
    public TelefonoPanicoAdapter(){
    	super(ApmSecurityConstants.ABM_TELEFONOPANICO);
    }
    
    //  Getters y Setters
	public TelefonoPanicoVO getTelefonoPanico() {
		return telefonoPanico;
	}

	public void setTelefonoPanico(TelefonoPanicoVO telefonoPanicoVO) {
		this.telefonoPanico = telefonoPanicoVO;
	}

	public String getName(){
		return NAME;
	}
			
	public void prepareReport(Long format) {
		 
		 ReportVO report = this.getReport(); // no instanciar una nueva
		 report.setReportFormat(format);	
		 report.setReportTitle("Reporte de TelefonoPanico");     
		 report.setReportBeanName("TelefonoPanico");
		 report.setReportFileName(this.getClass().getName());
		 
		// carga de filtros: ninguno
		// Order by: no 
		 
		 ReportVO reportTelefonoPanico = new ReportVO();
		 reportTelefonoPanico.setReportTitle("Datos del TelefonoPanico");
		 // carga de datos
	     
	     //C�digo
		 reportTelefonoPanico.addReportDato("C�digo", "codTelefonoPanico");
		 //Descripci�n
		 reportTelefonoPanico.addReportDato("Descripci�n", "desTelefonoPanico");
	     
		 report.getListReport().add(reportTelefonoPanico);
	
	}
	// View getters
}