package ar.gov.rosario.gait.${modulo}.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.${modulo}.iface.util.${Modulo}SecurityConstants;
import coop.tecso.demoda.iface.model.ReportVO;
import coop.tecso.demoda.iface.model.SiNo;

/**
 * Adapter del ${Bean}
 * 
 * @author tecso
 */
public class ${Bean}Adapter extends GaitAdapterModel{
	
	private static final long serialVersionUID = 1L;
	
	public static final String NAME = "${bean}AdapterVO";
	
    private ${Bean}VO ${bean} = new ${Bean}VO();
    
    private List<SiNo>           listSiNo = new ArrayList<SiNo>();
    
    // Constructores
    public ${Bean}Adapter(){
    	super(${Modulo}SecurityConstants.ABM_${BEAN});
    }
    
    //  Getters y Setters
	public ${Bean}VO get${Bean}() {
		return ${bean};
	}

	public void set${Bean}(${Bean}VO ${bean}VO) {
		this.${bean} = ${bean}VO;
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
		 report.setReportTitle("Reporte de ${Bean}");     
		 report.setReportBeanName("${Bean}");
		 report.setReportFileName(this.getClass().getName());
		 
		// carga de filtros: ninguno
		// Order by: no 
		 
		 ReportVO report${Bean} = new ReportVO();
		 report${Bean}.setReportTitle("Datos del ${Bean}");
		 // carga de datos
	     
	     //C�digo
		 report${Bean}.addReportDato("C�digo", "cod${Bean}");
		 //Descripci�n
		 report${Bean}.addReportDato("Descripci�n", "des${Bean}");
	     
		 report.getListReport().add(report${Bean});
	
	}
	
	// View getters
}