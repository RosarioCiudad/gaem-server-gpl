/*******************************************************************************
 * Copyright (c) 2016 Municipalidad de Rosario, Coop. de Trabajo Tecso Ltda.
 *
 * This file is part of GAEM.
 *
 * GAEM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * GAEM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAEM.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package ar.gov.rosario.gait.not.iface.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.model.AplicacionVO;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilVO;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.not.iface.util.NotSecurityConstants;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.model.ReportVO;
import coop.tecso.demoda.iface.model.SiNo;

public class NotificacionAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "notificacionAdapterVO";

	private NotificacionVO notificacion = new NotificacionVO();

	private List<SiNo> listSiNo = new ArrayList<SiNo>();

	private List<AplicacionVO> listAplicacion = new ArrayList<AplicacionVO>();
	private List<TipoNotificacionVO> listTipoNotificacion = new ArrayList<TipoNotificacionVO>();
	private List<EstadoNotificacionVO> listEstadoNotificacion = new ArrayList<EstadoNotificacionVO>();
	private List<DispositivoMovilVO> listDispositivoMovil = new ArrayList<DispositivoMovilVO>();

	private Date fechaNotifiacionDesde;
	private Date fechaNotifiacionHasta;

	// Constructores
	public NotificacionAdapter() {
		super(NotSecurityConstants.ABM_NOTIFICACION);
	}

	// Getters y Setters
	public NotificacionVO getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(NotificacionVO notificacionVO) {
		this.notificacion = notificacionVO;
	}

	public List<SiNo> getListSiNo() {
		return listSiNo;
	}

	public void setListSiNo(List<SiNo> listSiNo) {
		this.listSiNo = listSiNo;
	}

	public String getName() {
		return NAME;
	}

	/**
	 * @param notificacion
	 *            the notificacion to set
	 */

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
	 * @param fechaNotifiacionDesde
	 *            the fechaNotifiacionDesde to set
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
		return DateUtil.formatDate(fechaNotifiacionHasta,
				DateUtil.ddSMMSYYYY_MASK);
	}

	/**
	 * @param fechaNotifiacionHasta
	 *            the fechaNotifiacionHasta to set
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
	 * @param listTipoNotificacion
	 *            the listTipoNotificacion to set
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
	 * @param listEstadoNotificacion
	 *            the listEstadoNotificacion to set
	 */
	public void setListEstadoNotificacion(
			List<EstadoNotificacionVO> listEstadoNotificacion) {
		this.listEstadoNotificacion = listEstadoNotificacion;
	}

	public List<DispositivoMovilVO> getListDispositivoMovil() {
		return listDispositivoMovil;
	}

	public void setListDispositivoMovil(
			List<DispositivoMovilVO> listDispositivoMovil) {
		this.listDispositivoMovil = listDispositivoMovil;
	}

	public void prepareReport(Long format) {

		ReportVO report = this.getReport(); // no instanciar una nueva
		report.setReportFormat(format);
		report.setReportTitle("Reporte de Notificaciones");
		report.setReportBeanName("Notificacion");
		report.setReportFileName(this.getClass().getName());

		// carga de filtros: ninguno
		// Order by: no

		ReportVO reportNotificacion = new ReportVO();
		reportNotificacion.setReportTitle("Datos de la Notificacion");
		// carga de datos

		// C�digo
		reportNotificacion.addReportDato("C�digo", "codNotificacion");
		// Descripci�n
		reportNotificacion.addReportDato("Descripci�n", "desNotificacion");

		report.getListReport().add(reportNotificacion);

	}

	// View getters
}