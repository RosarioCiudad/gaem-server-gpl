package ar.gov.rosario.gait.not.iface.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.model.AplicacionVO;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilVO;
import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.not.iface.util.NotSecurityConstants;
import coop.tecso.demoda.iface.helper.DateUtil;




/**
 * SearchPage del Notificacion
 * 
 * @author Tecso
 *
 */
public class NotificacionSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "notificacionSearchPageVO";

	private NotificacionVO notificacion= new NotificacionVO();
	
	private List<AplicacionVO> listAplicacion = new ArrayList<AplicacionVO>();
	private List<TipoNotificacionVO> listTipoNotificacion = new ArrayList<TipoNotificacionVO>();
	private List<EstadoNotificacionVO> listEstadoNotificacion = new ArrayList<EstadoNotificacionVO>();
	private List<DispositivoMovilVO> listDispositivoMovil = new ArrayList<DispositivoMovilVO>();
	

	private Date fechaNotifiacionDesde;
	private Date fechaNotifiacionHasta;

	// Constructores
	public NotificacionSearchPage() {       
		super(NotSecurityConstants.ABM_NOTIFICACION);        
	}

	// Getters y Setters


	public String getName(){    
		return NAME;
	}

	/**
	 * @return the notificacion
	 */
	public NotificacionVO getNotificacion() {
		return notificacion;
	}

	/**
	 * @param notificacion the notificacion to set
	 */
	public void setNotificacion(NotificacionVO notificacion) {
		this.notificacion = notificacion;
	}

	public List<AplicacionVO> getListAplicacion() {
		return listAplicacion;
	}

	public void setListAplicacion(List<AplicacionVO> listAplicacion) {
		this.listAplicacion = listAplicacion;
	}

	/**
	 * @return the fechaNotifiacionDesde
	 */
	public Date getFechaNotifiacionDesde() {
		return fechaNotifiacionDesde;
	}

	/**
	 * @param fechaNotifiacionDesde the fechaNotifiacionDesde to set
	 */
	public void setFechaNotifiacionDesde(Date fechaNotifiacionDesde) {
		this.fechaNotifiacionDesde = fechaNotifiacionDesde;
	}

	/**
	 * @return the fechaNotifiacionHasta
	 */
	public Date getFechaNotifiacionHasta() {
		return fechaNotifiacionHasta;
	}
	
	/**
	 * @return the fechaNotifiacionHastaView
	 */
	public String getFechaNotifiacionHastaView() {
		return  DateUtil.formatDate(fechaNotifiacionHasta, DateUtil.ddSMMSYYYY_MASK);
	}

	/**
	 * @param fechaNotifiacionHasta the fechaNotifiacionHasta to set
	 */
	public void setFechaNotifiacionHasta(Date fechaNotifiacionHasta) {
		this.fechaNotifiacionHasta = fechaNotifiacionHasta;
	}

	/**
	 * @return the listTipoNotificacion
	 */
	public List<TipoNotificacionVO> getListTipoNotificacion() {
		return listTipoNotificacion;
	}

	/**
	 * @param listTipoNotificacion the listTipoNotificacion to set
	 */
	public void setListTipoNotificacion(
			List<TipoNotificacionVO> listTipoNotificacion) {
		this.listTipoNotificacion = listTipoNotificacion;
	}

	/**
	 * @return the listEstadoNotificacion
	 */
	public List<EstadoNotificacionVO> getListEstadoNotificacion() {
		return listEstadoNotificacion;
	}

	/**
	 * @param listEstadoNotificacion the listEstadoNotificacion to set
	 */
	public void setListEstadoNotificacion(
			List<EstadoNotificacionVO> listEstadoNotificacion) {
		this.listEstadoNotificacion = listEstadoNotificacion;
	}

	public List<DispositivoMovilVO> getListDispositivoMovil() {
		return listDispositivoMovil;
	}

	public void setListDispositivoMovil(List<DispositivoMovilVO> listDispositivoMovil) {
		this.listDispositivoMovil = listDispositivoMovil;
	}


	/*public void prepareReport(Long format) {

		ReportVO report = this.getReport(); // no instanciar una nueva
		report.setReportFormat(format);	
		report.setReportTitle("Listados de Notificaciones");
		report.setReportBeanName("Notificacion");
		report.setReportFileName(this.getClass().getName());

		 Codigo de ejemplo para mostrar filtros de Combos en los imprimir
		String desRecurso = "";

		RecursoVO recursoVO = (RecursoVO) ModelUtil.getBussImageModelByIdForList(
				this.getReclamo().getRecurso().getId(),
				this.getListRecurso());
		if (recursoVO != null){
			desRecurso = recursoVO.getDesRecurso();
		}
		report.addReportFiltro("Recurso", desRecurso);

		//Descripcion Ampliada
		report.addReportFiltro("Descripcion_Ampliada", this.getNotificacion().getDescripcionAmpliada());
		//Descripcion Reducida
		report.addReportFiltro("Descripcion_Reducida", this.getNotificacion().getDescripcionReducida());


		ReportTableVO rtNotificacion = new ReportTableVO("rtNotificacion");
		rtNotificacion.setTitulo("B\u00FAsqueda de Notificacion");

		// carga de columnas
		rtNotificacion.addReportColumn("C�digo","desANotificacion");
		rtNotificacion.addReportColumn("Descripci�n", "desRNotificacion");


		report.getReportListTable().add(rtNotificacion);

	}*/
	// View getters


}
