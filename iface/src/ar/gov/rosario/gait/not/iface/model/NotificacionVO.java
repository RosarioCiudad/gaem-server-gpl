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

import java.util.Date;

import ar.gov.rosario.gait.apm.iface.model.AplicacionVO;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilVO;
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class NotificacionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "notificacionVO";
	
	private TipoNotificacionVO tipoNotificacion  = new TipoNotificacionVO();
	private EstadoNotificacionVO estadoNotificacion = new EstadoNotificacionVO();
	private DispositivoMovilVO dispositivoMovil = new DispositivoMovilVO();
	private AplicacionVO aplicacion = new AplicacionVO();
	
	private int numeroAplicacion;
	private Date fecha = new Date();
	private String descripcionReducida;
	private String descripcionAmpliada;
	private Date fechaGeneracion = new Date();

	private Date fechaEnvio;
	private Date fechaRecepcion; 

	public NotificacionVO() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @return the tipoNotificacion
	 */
	public TipoNotificacionVO getTipoNotificacion() {
		return tipoNotificacion;
	}


	/**
	 * @param tipoNotificacion the tipoNotificacion to set
	 */
	public void setTipoNotificacion(TipoNotificacionVO tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}


	/**
	 * @return the estadoNotificacion
	 */
	public EstadoNotificacionVO getEstadoNotificacion() {
		return estadoNotificacion;
	}


	/**
	 * @param estadoNotificacion the estadoNotificacion to set
	 */
	public void setEstadoNotificacion(EstadoNotificacionVO estadoNotificacion) {
		this.estadoNotificacion = estadoNotificacion;
	}


	/**
	 * @return the aplicacion
	 */
	public AplicacionVO getAplicacion() {
		return aplicacion;
	}


	/**
	 * @param aplicacion the aplicacion to set
	 */
	public void setAplicacion(AplicacionVO aplicacion) {
		this.aplicacion = aplicacion;
	}


	/**
	 * @return the numeroAplicacion
	 */
	public int getNumeroAplicacion() {
		return numeroAplicacion;
	}


	/**
	 * @param numeroAplicacion the numeroAplicacion to set
	 */
	public void setNumeroAplicacion(int numeroAplicacion) {
		this.numeroAplicacion = numeroAplicacion;
	}


	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}


	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	/**
	 * @return the descripcionReducida
	 */
	public String getDescripcionReducida() {
		return descripcionReducida;
	}


	/**
	 * @param descripcionReducida the descripcionReducida to set
	 */
	public void setDescripcionReducida(String descripcionReducida) {
		this.descripcionReducida = descripcionReducida;
	}


	/**
	 * @return the descripcionAmpliada
	 */
	public String getDescripcionAmpliada() {
		return descripcionAmpliada;
	}


	/**
	 * @param descripcionAmpliada the descripcionAmpliada to set
	 */
	public void setDescripcionAmpliada(String descripcionAmpliada) {
		this.descripcionAmpliada = descripcionAmpliada;
	}




	/**
	 * @return the fechaEnvio
	 */
	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	/**
	 * @param fechaEnvio the fechaEnvio to set
	 */
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}


	/**
	 * @return the fechaRecepcion
	 */
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	/**
	 * @param fechaRecepcion the fechaRecepcion to set
	 */
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	public DispositivoMovilVO getDispositivoMovil() {
		return dispositivoMovil;
	}

	public void setDispositivoMovil(DispositivoMovilVO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}
	
	

	/**
	 * @return the fechaGeneracion
	 */
	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}


	/**
	 * @param fechaGeneracion the fechaGeneracion to set
	 */
	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}
}