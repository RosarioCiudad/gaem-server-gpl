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
package ar.gov.rosario.gait.gps.iface.model;

import java.util.Date;

import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilVO;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmVO;
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;

/**
 * Value Object del HistorialUbicacion
 * @author tecso.coop
 *
 */
public class HistorialUbicacionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "historialUbicacionVO";
	
	private DispositivoMovilVO dispositivoMovil = new DispositivoMovilVO();

	private UsuarioApmVO usuarioApm = new UsuarioApmVO();

	private AreaVO area = new AreaVO();

	private Date fechaPosicion;

	private Date fechaUbicacion;

	private double latitud;

	private double longitud;

	private double precision;

	private String origen;

	private double velocidad;

	private double radio;

	private double altitud;
	
	private double nivelBateria;

	private double nivelSenial;
	
	// Constructores
	public HistorialUbicacionVO() {
		super();
	}
	// Getters y Setters
	public DispositivoMovilVO getDispositivoMovil() {
		return dispositivoMovil;
	}

	public void setDispositivoMovil(DispositivoMovilVO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}

	public UsuarioApmVO getUsuarioApm() {
		return usuarioApm;
	}

	public void setUsuarioApm(UsuarioApmVO usuarioApm) {
		this.usuarioApm = usuarioApm;
	}

	public AreaVO getArea() {
		return area;
	}

	public void setArea(AreaVO area) {
		this.area = area;
	}

	public Date getFechaPosicion() {
		return fechaPosicion;
	}

	public void setFechaPosicion(Date fechaPosicion) {
		this.fechaPosicion = fechaPosicion;
	}

	public Date getFechaUbicacion() {
		return fechaUbicacion;
	}

	public void setFechaUbicacion(Date fechaUbicacion) {
		this.fechaUbicacion = fechaUbicacion;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getPrecision() {
		return precision;
	}

	public void setPrecision(double precision) {
		this.precision = precision;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}

	public double getAltitud() {
		return altitud;
	}

	public void setAltitud(double altitud) {
		this.altitud = altitud;
	}
	
	public double getNivelBateria() {
		return nivelBateria;
	}
	
	public void setNivelBateria(double nivelBateria) {
		this.nivelBateria = nivelBateria;
	}
	
	public double getNivelSenial() {
		return nivelSenial;
	}
	
	public void setNivelSenial(double nivelSenial) {
		this.nivelSenial = nivelSenial;
	}

	// Buss flags getters y setters
	
	// View flags getters
	
	// View getters
}