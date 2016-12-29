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
package ar.gov.rosario.gait.not.iface.util;

import ar.gov.rosario.gait.base.iface.util.BaseError;

public class NotError extends BaseError {
	
	//ABM Administracion de Notificaciones
	
	public static final String NOTIFICACION_DESCRIPCIONREDUCIDA         = addError(4000,"not.notificacion.descripcionReducida.label");
	public static final String NOTIFICACION_DESCRIPCIONAMPLIADA     = addError(4001,"not.notificacion.descripcionAmpliada.label");
	public static final String NOTIFICACION_TIPONOTIFICACION    = addError(4002,"not.notificacion.tipoNotificacion.label");
	public static final String NOTIFICACION_APLICACION  = addError(4003,"not.notificacion.aplicacion.label");
	public static final String NOTIFICACION_DISPOSITIVOMOVIL  = addError(4004,"not.notificacion.dispositivoMovil.label");
	
	// ---> ABM TipoNotificacion
	public static final String TIPONOTIFICACION_LABEL        = addError(4000,"not.tipoNotificacion.label");
		
	// ---> ABM EstadoNotificacion
	public static final String ESTADONOTIFICACION_LABEL        = addError(4000,"not.estadoNotificacion.label");
}
