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
package ar.gov.rosario.gait.apm.iface.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import coop.tecso.demoda.iface.helper.DateUtil;

public class PanicoVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "panicoVO";
	
	private DispositivoMovilVO dispositivoMovil = new DispositivoMovilVO();
	private EstadoPanicoVO estadoPanico = new EstadoPanicoVO();
	private UsuarioApmVO usuarioPanico = new UsuarioApmVO();
	private AreaVO area = new AreaVO();
	private Date fechaPosicion;
	private Date fechaPanico;
	private Date fechaAtencion;
	private Double latitud;
	private Double longitud;
	private String origen;
	private Double precision;
	private String observacion;
	private Date fechaRecepcion;
	
	private Date fechaPanicoDesde;
	private Date fechaPanicoHasta;
	
	private List<HisEstPanVO> listHisEstPan = new ArrayList<>();

	// Getters y setters
	public DispositivoMovilVO getDispositivoMovil() {
		return dispositivoMovil;
	}
	public void setDispositivoMovil(DispositivoMovilVO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}
	public AreaVO getArea() {
		return area;
	}
	public void setArea(AreaVO area) {
		this.area = area;
	}
	public EstadoPanicoVO getEstadoPanico() {
		return estadoPanico;
	}
	public void setEstadoPanico(EstadoPanicoVO estadoPanico) {
		this.estadoPanico = estadoPanico;
	}
	public UsuarioApmVO getUsuarioPanico() {
		return usuarioPanico;
	}
	public void setUsuarioPanico(UsuarioApmVO usuarioPanico) {
		this.usuarioPanico = usuarioPanico;
	}
	public Date getFechaPosicion() {
		return fechaPosicion;
	}
	public void setFechaPosicion(Date fechaPosicion) {
		this.fechaPosicion = fechaPosicion;
	}
	public Date getFechaPanico() {
		return fechaPanico;
	}
	public void setFechaPanico(Date fechaPanico) {
		this.fechaPanico = fechaPanico;
	}
	public Date getFechaAtencion() {
		return fechaAtencion;
	}
	public void setFechaAtencion(Date fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}
	public Double getLatitud() {
		return latitud;
	}
	public String getLatitudView() {
		return latitud.toString();
	}
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	public Double getLongitud() {
		return longitud;
	}
	public String getLongitudView() {
		return longitud.toString();
	}
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public Double getPrecision() {
		return precision;
	}
	public void setPrecision(Double precision) {
		this.precision = precision;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}	
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public Date getFechaPanicoDesde() {
		return fechaPanicoDesde;
	}
	public void setFechaPanicoDesde(Date fechaPanicoDesde) {
		this.fechaPanicoDesde = fechaPanicoDesde;
	}
	public Date getFechaPanicoHasta() {
		return fechaPanicoHasta;
	}
	public void setFechaPanicoHasta(Date fechaPanicoHasta) {
		this.fechaPanicoHasta = fechaPanicoHasta;
	}
	public List<HisEstPanVO> getListHisEstPan() {
		return listHisEstPan;
	}
	public void setListHisEstPan(List<HisEstPanVO> listHisEstPan) {
		this.listHisEstPan = listHisEstPan;
	}
	// View Getters
	public String getFechaPanicoHastaView() {
		return DateUtil.formatDate(fechaPanicoHasta, DateUtil.ddSMMSYYYY_MASK);
	}
	public String getFechaPanicoDesdeView() {
		return DateUtil.formatDate(fechaPanicoDesde, DateUtil.ddSMMSYYYY_MASK);
	}
	public String getFechaPanicoView() {
		return DateUtil.formatDate(fechaPanico, DateUtil.ddSMMSYYYY_MASK);
	}
	public String getHoraPanicoView() {
		return DateUtil.formatDate(fechaPanico, DateUtil.HOUR_MINUTE_MASK);
	}
	public String getFechaRecepcionView() {
		return DateUtil.formatDate(fechaRecepcion, DateUtil.ddSMMSYYYY_MASK);
	}
	public String getHoraRecepcionView() {
		return DateUtil.formatDate(fechaRecepcion, DateUtil.HOUR_MINUTE_MASK);
	}
	
}