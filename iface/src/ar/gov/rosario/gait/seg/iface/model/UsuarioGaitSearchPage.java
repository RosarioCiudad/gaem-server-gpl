package ar.gov.rosario.gait.seg.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.def.iface.model.DireccionVO;
import ar.gov.rosario.gait.seg.iface.util.SegSecurityConstants;
import coop.tecso.demoda.iface.model.ReportTableVO;
import coop.tecso.demoda.iface.model.ReportVO;

/**
 * SearchPage del UsuarioGait
 * 
 * @author Tecso
 *
 */
public class UsuarioGaitSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "usuarioGaitSearchPageVO";
	
	private UsuarioGaitVO usuarioGait= new UsuarioGaitVO();
    private List<DireccionVO> listDireccion = new ArrayList<>();
    private List<AreaVO> listArea = new ArrayList<>();

	// Constructores
	public UsuarioGaitSearchPage() {       
       super(SegSecurityConstants.ABM_USUARIOGAIT);        
    }
	
	// Getters y Setters
	public UsuarioGaitVO getUsuarioGait() {
		return usuarioGait;
	}
	public void setUsuarioGait(UsuarioGaitVO usuarioGait) {
		this.usuarioGait = usuarioGait;
	}
	public List<DireccionVO> getListDireccion() {
		return listDireccion;
	}
	public void setListDireccion(List<DireccionVO> listDireccion) {
		this.listDireccion = listDireccion;
	}
	public List<AreaVO> getListArea() {
		return listArea;
	}
	public void setListArea(List<AreaVO> listArea) {
		this.listArea = listArea;
	}
	public String getName(){
		return NAME;
	}
	
	public void prepareReport(Long format) {
		 
		ReportVO report = this.getReport(); // no instanciar una nueva
		 report.setReportFormat(format);	
		 report.setReportTitle("Consulta  de Usuario GAIT");
		 report.setReportBeanName("UsuarioGait");
		 report.setReportFileName(this.getClass().getName());
		
		
		 // Area
		 String desArea = "";		 
		 // Nombre
		 report.addReportFiltro("Nombre", this.getUsuarioGait().getUsuarioGAIT());
	     
		 // Order by
		 //report.setReportOrderBy("desTipoObra ASC");
	     
	     ReportTableVO rtUsu = new ReportTableVO("rtUsu");
	     rtUsu.setTitulo("Listado de Usuario GAIT");
	   
	     // carga de columnas
	     rtUsu.addReportColumn("Nombre", "usuarioGAIT");
	     rtUsu.addReportColumn("Area", "area.desArea");
	     rtUsu.addReportColumn("Estado", "estadoView");
	     
	     report.getReportListTable().add(rtUsu);

	    }
}
