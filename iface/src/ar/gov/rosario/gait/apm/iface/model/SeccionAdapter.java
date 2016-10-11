package ar.gov.rosario.gait.apm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import coop.tecso.demoda.iface.model.ReportVO;
import coop.tecso.demoda.iface.model.SiNo;

/**
 * Adapter del Seccion
 * 
 * @author tecso
 */
public class SeccionAdapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "seccionAdapterVO";
	
    private SeccionVO seccion = new SeccionVO();
    
    private List<SiNo>           listSiNo = new ArrayList<SiNo>();
    
    // Constructores
    public SeccionAdapter(){
    	super(ApmSecurityConstants.ABM_SECCION);
    }
    
    //  Getters y Setters
	public SeccionVO getSeccion() {
		return seccion;
	}

	public void setSeccion(SeccionVO seccionVO) {
		this.seccion = seccionVO;
	}

	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}
	
	public String getName(){
		return NAME;
	}
			
	public void prepareReport(Long format) {
		 
		 ReportVO report = this.getReport(); // no instanciar una nueva
		 report.setReportFormat(format);	
		 report.setReportTitle("Reporte de Seccion");     
		 report.setReportBeanName("Seccion");
		 report.setReportFileName(this.getClass().getName());
		 
		// carga de filtros: ninguno
		// Order by: no 
		 
		 ReportVO reportSeccion = new ReportVO();
		 reportSeccion.setReportTitle("Datos del Seccion");
		 // carga de datos
	     
	     //C�digo
		 reportSeccion.addReportDato("C�digo", "codSeccion");
		 //Descripci�n
		 reportSeccion.addReportDato("Descripci�n", "desSeccion");
	     
		 report.getListReport().add(reportSeccion);
	
	}
	
	// View getters
}