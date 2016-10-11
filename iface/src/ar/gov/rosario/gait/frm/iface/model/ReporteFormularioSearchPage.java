package ar.gov.rosario.gait.frm.iface.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.model.ReportTableVO;
import coop.tecso.demoda.iface.model.ReportVO;
import coop.tecso.demoda.iface.model.SiNo;
import coop.tecso.demoda.iface.model.ValueVO;

/**
 * SearchPage del Formulario
 * 
 * @author Tecso
 *
 */
public class ReporteFormularioSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "reporteFormularioSearchPageVO";
	
	private FormularioVO formulario= new FormularioVO();
	private List<ValueVO> listEstado = new ArrayList<>();
	private List<ValueVO> listTipoReporte = new ArrayList<>();
	private List<AplicacionPerfilVO> listAplicacionPerfil = new ArrayList<>();
	private Integer tipoReporte = -1;
	
	private Date fechaCierreDesde;
	private Date fechaCierreHasta;
	
	private Date fechaImpresionDesde;
	private Date fechaImpresionHasta;
	
	private List<AreaVO> listArea = new ArrayList<>();
    private List<SiNo> listSiNo = new ArrayList<>();
	
	// Constructores
	public ReporteFormularioSearchPage() {       
       super(FrmSecurityConstants.ABM_REPORTEFORMULARIO);        
    }
	
	// Getters y Setters
	public FormularioVO getFormulario() {
		return formulario;
	}
	public void setFormulario(FormularioVO formulario) {
		this.formulario = formulario;
	}           

    public String getName(){    
		return NAME;
	}
	
	public List<ValueVO> getListEstado() {
		return listEstado;
	}

	public void setListEstado(List<ValueVO> listEstado) {
		this.listEstado = listEstado;
	}

	public List<AreaVO> getListArea() {
		return listArea;
	}

	public void setListArea(List<AreaVO> listArea) {
		this.listArea = listArea;
	}

	public Date getFechaImpresionHasta() {
		return fechaImpresionHasta;
	}

	public void setFechaImpresionHasta(Date fechaImpresionHasta) {
		this.fechaImpresionHasta = fechaImpresionHasta;
	}

	public void setFechaImpresionDesde(Date fechaImpresionDesde) {
		this.fechaImpresionDesde = fechaImpresionDesde;
	}

	public List<ValueVO> getListTipoReporte() {
		return listTipoReporte;
	}

	public void setListTipoReporte(List<ValueVO> listTipoReporte) {
		this.listTipoReporte = listTipoReporte;
	}
	
	public Integer getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(Integer tipoReporte) {
		this.tipoReporte = tipoReporte;
	}

	public Date getFechaCierreDesde() {
		return fechaCierreDesde;
	}

	public void setFechaCierreDesde(Date fechaCierreDesde) {
		this.fechaCierreDesde = fechaCierreDesde;
	}

	public Date getFechaCierreHasta() {
		return fechaCierreHasta;
	}

	public void setFechaCierreHasta(Date fechaCierreHasta) {
		this.fechaCierreHasta = fechaCierreHasta;
	}

	public Date getFechaImpresionDesde() {
		return fechaImpresionDesde;
	}

	public List<AplicacionPerfilVO> getListAplicacionPerfil() {
		return listAplicacionPerfil;
	}

	public void setListAplicacionPerfil(
			List<AplicacionPerfilVO> listAplicacionPerfil) {
		this.listAplicacionPerfil = listAplicacionPerfil;
	}

	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}

	public void prepareReport(Long format) {

		ReportVO report = this.getReport(); // no instanciar una nueva
		report.setReportFormat(format);	
		report.setReportTitle("Listados de Formulario");
		report.setReportBeanName("Formulario");
		report.setReportFileName(this.getClass().getName());

        /* Codigo de ejemplo para mostrar filtros de Combos en los imprimir
		String desRecurso = "";

		RecursoVO recursoVO = (RecursoVO) ModelUtil.getBussImageModelByIdForList(
				this.getReclamo().getRecurso().getId(),
				this.getListRecurso());
		if (recursoVO != null){
			desRecurso = recursoVO.getDesRecurso();
		}
		report.addReportFiltro("Recurso", desRecurso);*/

		//C�digo
//		report.addReportFiltro("Codigo", this.getFormulario().getCodFormulario());
       //Descripci�n
//		report.addReportFiltro("Descripcion", this.getFormulario().getDesFormulario());
		

		ReportTableVO rtFormulario = new ReportTableVO("rtFormulario");
		rtFormulario.setTitulo("B\u00FAsqueda de Formulario");

		// carga de columnas
		rtFormulario.addReportColumn("C�digo","codFormulario");
		rtFormulario.addReportColumn("Descripci�n", "desFormulario");
		
		 
	    report.getReportListTable().add(rtFormulario);
	}
	
	// View getters
	public String getFechaCierreDesdeView() {
		return DateUtil.formatDate(fechaCierreDesde, DateUtil.ddSMMSYYYY_MASK);
	}
	
	public String getFechaCierreHastaView() {
		return DateUtil.formatDate(fechaCierreHasta, DateUtil.ddSMMSYYYY_MASK);
	}
	
	public String getFechaImpresionDesdeView() {
		return DateUtil.formatDate(fechaImpresionDesde, DateUtil.ddSMMSYYYY_MASK);
	}
	
	public String getFechaImpresionHastaView() {
		return DateUtil.formatDate(fechaImpresionHasta, DateUtil.ddSMMSYYYY_MASK);
	}
}