package ar.gov.rosario.gait.apm.iface.model;

import java.util.Date;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import coop.tecso.demoda.iface.helper.DateUtil;

public class HisEstPanVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "hisEstPanVO";
	
	private PanicoVO panico = new PanicoVO();
	
	private EstadoPanicoVO estadoPanico = new EstadoPanicoVO();
	
	private Date fecha = new Date();
	
	private String logCambios;

	private String observaciones;

	// Getters y setters
	public PanicoVO getPanico() {
		return panico;
	}

	public void setPanico(PanicoVO panico) {
		this.panico = panico;
	}

	public EstadoPanicoVO getEstadoPanico() {
		return estadoPanico;
	}

	public void setEstadoPanico(EstadoPanicoVO estadoPanico) {
		this.estadoPanico = estadoPanico;
	}

	public String getLogCambios() {
		return logCambios;
	}

	public void setLogCambios(String logCambios) {
		this.logCambios = logCambios;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getFechaView() {
		return DateUtil.formatDate(fecha, DateUtil.ddSMMSYYYY_MASK);
	}
	
	public String getHoraView() {
		return DateUtil.formatDate(fecha, DateUtil.HOUR_MINUTE_MASK);
	}
}