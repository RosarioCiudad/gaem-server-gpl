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
