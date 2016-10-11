package ar.gov.rosario.gait.apm.iface.util;

import ar.gov.rosario.gait.base.iface.util.BaseError;

/**
 * En esta clase se definen las descripciones de los errores que estas asociaos a los VO.
 * 
 * @author Tecso Coop. Ltda.
 * 
 */
public class ApmError extends BaseError {
	
	// ---> ABM UsuarioApm
	public static final String USUARIOAPM_LABEL        = addError(4000,"apm.usuarioApm.label");
	public static final String USUARIOAPM_USERNAME     = addError(4001,"apm.usuarioApm.userName.label");
	
	// ---> ABM Seccion
	public static final String SECCION_DESCRIPCION = addError(11000, "apm.seccion.descripcion.label");
	public static final String SECCION_LABEL = addError(11004, "apm.seccion.label");	
	
	// ---> ABM DispositivoMovil
	public static final String DISPOSITIVOMOVIL_DESCRIPCION = addError(4002, "apm.dispositivoMovil.descripcion.label");
	public static final String DISPOSITIVOMOVIL_LABEL = addError(4003, "apm.dispositivoMovil.label");	
	public static final String DISPOSITIVOMOVIL_EMAIL = addError(4004, "apm.dispositivoMovil.email.label");
	public static final String DISPOSITIVOMOVIL_IMEI = addError(4005, "apm.dispositivoMovil.numeroIMEI.label");
	public static final String DISPOSITIVOMOVIL_NUMEROLINEA = addError(4006, "apm.dispositivoMovil.numeroLinea.label");

	// ---> ABM TablaVersion
	public static final String TABLAVERSION_LABEL        = addError(4000,"apm.tablaVersion.label");
	public static final String TABLAVERSION_DESCRIPCION	 = addError(4020, "apm.aplicacion.descripcion.label");
	
	// ---> ABM AplicacionTabla
	public static final String APLICACIONTABLA_LABEL        = addError(4000,"apm.aplicacionTabla.label");
	
	// ---> APM Campo
	public static final String CAMPO_ETIQUETA = addError(4007, "apm.campo.etiqueta.label");
	public static final String CAMPO_TRATAMIENTO = addError(4008, "apm.campo.tratamiento.label");
	public static final String CAMPO_LABEL = addError(4009, "apm.campo.label");	

	// ---> APM CampoValorOpcion
	public static final String CAMPOVALOROPCION_LABEL = addError(4010, "apm.campoValorOpcion.label");	
	public static final String CAMPOVALOR_LABEL = addError(4010, "apm.campoValor.label");	
	
	// ---> Aplicacion Perfil 
	public static final String APLICACIONPERFIL_LABEL = addError(4011, "apm.aplicacionPerfil.label");	
	public static final String APLICACIONPERFIL_DESCRIPCION = addError(4012, "apm.aplicacionperfil.descripcion.label");	
	public static final String APLICACIONPERFIL_ORDEN = addError(4014, "apm.aplicacionperfil.orden.label");	
	public static final String APLICACIONPERFIL_CLONAR = addError(4012, "apm.aplicacionperfil.clonar.label");
	public static final String APLICACIONPERFIL_TIPOFORMULARIO = addError(4012, "apm.aplicacionPerfil.tipoformulario.label");
	
	

	// ---> Aplicacion Perfil Seccion 
	public static final String APLICACIONPERFILSECCION_LABEL = addError(4015, "apm.aplicacionPerfilSeccion.label");	
		
	// ---> Apl Perfil Seccion Campo
	public static final String APLPERFILSECCIONCAMPO_LABEL = addError(4016, "apm.aplPerfilSeccionCampo.label");	
	
	// ---> Apl Perfil Seccion Campo Valor
	public static final String APLPERFILSECCIONCAMPOVALOR_LABEL = addError(4017, "apm.aplPerfilSeccionCampoValor.label");	

	// ---> Apl Perfil Seccion Campo Valor Opcion
	public static final String APLPERFILSECCIONCAMPOVALOROPCION_LABEL = addError(4018, "apm.aplPerfilSeccionCampoValorOpcion.label");	

	// ---> Apl Perfil Parametro
	public static final String APLICACIONPERFILPARAMETRO_CODIGO	 = addError(4019, "apm.aplicacionPerfilParametro.codigo.label");	
	public static final String APLICACIONPERFILPARAMETRO_DESCRIPCION	 = addError(4020, "apm.aplicacionPerfilParametro.descripcion.label");	
	public static final String APLICACIONPERFILPARAMETRO_VALOR = addError(4021, "apm.aplicacionPerfilParametro.valor.label");	
	public static final String APLICACIONPERFILPARAMETRO_LABEL = addError(4022, "apm.aplicacionPerfilParametro.label");	
	
	// ---> Perfil Acceso
	public static final String PERFILACCESO_LABEL = addError(4023, "apm.perfilAcceso.label");	
	public static final String PERFILACCESO_DESCRIPCION = addError(4012, "apm.perfilAcceso.descripcion.label");	
	public static final String PERFILACCESO_AREA = addError(4012, "apm.perfilAcceso.area.label");
	
	//  ---> Impresora 
	public static final String IMPRESORA_DESCRIPCION = addError(4002, "apm.impresora.descripcion.label");
	public static final String IMPRESORA_LABEL = addError(4003, "apm.impresora.label");	
	public static final String IMPRESORA_SERIE= addError(4004, "apm.impresora.serie.label");
	public static final String IMPRESORA_NUMEROUUID= addError(4005, "apm.impresora.numeroUUID.label");

	// ---> Apl  Aplicacion
	public static final String APLICACION_CODIGO	 = addError(4019, "apm.aplicacion.codigo.label");	
	public static final String APLICACION_DESCRIPCION	 = addError(4020, "apm.aplicacion.descripcion.label");	
	public static final String APLICACION_VALOR = addError(4021, "apm.aplicacion.valor.label");	
	public static final String APLICACION_LABEL = addError(4022, "apm.aplicacion.label");	
	
	
	// ---> Apl  Parametro
	public static final String APLICACIONPARAMETRO_CODIGO	 = addError(4019, "apm.aplicacionParametro.codigo.label");	
	public static final String APLICACIONPARAMETRO_DESCRIPCION	 = addError(4020, "apm.aplicacionParametro.descripcion.label");	
	public static final String APLICACIONPARAMETRO_VALOR = addError(4021, "apm.aplicacionParametro.valor.label");	
	public static final String APLICACIONPARAMETRO_LABEL = addError(4022, "apm.aplicacionParametro.label");	
	
	// ---> Apl  Binario Version
	public static final String APLICACIONBINARIOVERSION_CODIGO	 = addError(4019, "apm.aplicacionBinarioVersion.codigo.label");	
	public static final String APLICACIONBINARIOVERSION_DESCRIPCION	 = addError(4020, "apm.aplicacionBinarioVersion.descripcion.label");	
	public static final String APLICACIONBINARIOVERSION_VALOR = addError(4021, "apm.aplicacionBinarioVersion.valor.label");	
	public static final String APLICACIONBINARIOVERSION_LABEL = addError(4022, "apm.aplicacionBinarioVersion.label");	
	public static final String ARCHIVO_UPLOAD_FILE_LABEL = addError(4024, "apm.aplicacionBinarioVersion.file.label");
	
	// ---> Apl  UsuarioApm
	public static final String USUARIOAPM_NOMBRE	 = addError(4020, "apm.usuarioApm.nombre.label");	

	// ---> Apl  UsuarioApmDM
	public static final String USUARIOAPMDM_CODIGO	 = addError(4019, "apm.usuarioApmDM.codigo.label");	
	public static final String USUARIOAPMDM_DESCRIPCION	 = addError(4020, "apm.usuarioApmDM.descripcion.label");	
	public static final String USUARIOAPMDM_LABEL = addError(4022, "apm.usuarioApmDM.label");	
	
	// ---> Apl  UsuarioApmDM
	public static final String USUARIOAPMIMPRESORA_LABEL = addError(4022, "apm.usuarioApmImpresora.label");
	
	// ---> Apl  Perfil Acceso Usuario
	public static final String PERFILACCESOUSUARIO_CODIGO	 = addError(4019, "apm.perfilAccesoUsuario.codigo.label");	
	public static final String PERFILACCESOUSUARIO_DESCRIPCION	 = addError(4020, "apm.perfilAccesoUsuario.descripcion.label");	
	public static final String PERFILACCESOUSUARIO_VALOR = addError(4021, "apm.perfilAccesoUsuario.valor.label");	
	public static final String PERFILACCESOUSUARIO_LABEL = addError(4022, "apm.perfilAccesoUsuario.label");	
				
	// ---> ABM HistoriaGPS
	public static final String HISTORIAGPS_LABEL               = addError(4000,"apm.historiaGPS.label");
	public static final String HISTORIAGPS_IDDISPOSITIVOMOVIL  = addError(4020, "apm.historiaGPS.idDispositivoMovil.label");
	public static final String HISTORIAGPS_FECHACAPTURA	       = addError(4020, "apm.historiaGPS.fechaCaptura.label");
	public static final String HISTORIAGPS_TIPOCAPTURA	       = addError(4020, "apm.historiaGPS.tipoCaptura.label");
	public static final String HISTORIAGPS_DATOS			   = addError(4020, "apm.historiaGPS.datos.label");
	
	//Aplicacion Tipo Binario
	public static final String APLICACIONTIPOBINARIO_CODIGO	 = addError(4019, "apm.aplicacionTipoBinario.codigo.label");	
	public static final String APLICACIONTIPOBINARIO_DESCRIPCION	 = addError(4020, "apm.aplicacionTipoBinario.descripcion.label");	
	public static final String APLICACIONTIPOBINARIO_VALOR = addError(4021, "apm.aplicacionTipoBinario.valor.label");	
	public static final String APLICACIONTIPOBINARIO_LABEL = addError(4022, "apm.aplicacionTipoBinario.label");	
	
	// ---> Apl  TelefonoPanico
	public static final String TELEFONOPANICO_LABEL  = addError(4022, "apm.telefonoPanico.label");
	public static final String TELEFONOPANICO_NUMERO = addError(4022, "apm.telefonoPanico.numero.label");
	
	// -->> Panico
	public static final String HISESTPAN_FECHA  	= addError(4023, "apm.hisEstPan.fecha");
	public static final String ESTADOPANICO_LABEL   = addError(4024, "apm.estadoPanico.label");
	
	public static final String PERFILACCESOAPLICACION_LABEL   = addError(4024, "apm.perfilAccesoAplicacion.label");
}
