package ar.gov.rosario.gait.frm.iface.util;

import ar.gov.rosario.gait.base.iface.util.BaseError;

public class FrmError extends BaseError {
	
	// ---> ABM Formulario
	public static final String FORMULARIO_LABEL = addError(4000, "frm.formulario.label");
	public static final String FORMULARIO_FECHAINICIO = addError(4000, "frm.formulario.fechaInicio.label");
	public static final String FORMULARIO_FECHACIERRE = addError(4000, "frm.formulario.fechaCierre.label");
	// <--- ABM Formulario
	
	// ---> ABM Formulario
	public static final String ESTADOTIPOFORMULARIO_LABEL = addError(4000, "frm.estadoTipoFormulario.label");
	public static final String ESTADOTIPOFORMULARIO_DESCRIPCION_LABEL = addError(4000, "frm.estadoTipoFormulario.descripcion.label");
	
	// ---> ABM FormularioDetalle
	public static final String FORMULARIODETALLE_LABEL = addError(4000, "frm.formularioDetalle.label");
	// <--- ABM FormularioDetalle
	
	//ABM Administracion de Tipo Formularios
	public static final String TIPOFORMULARIO_DESCRIPCION         = addError(4000,"frm.tipoFormulario.descripcion.label");
	public static final String TIPOFORMULARIO_CODIGO    = addError(4001,"frm.tipoFormulario.codigo.label");
	public static final String SERIE_CODIGO    = addError(4001,"frm.serie.codigo.label");
	public static final String MOTIVOCIERRETIPOFORMULARIO_DESCRIPCION    = addError(4001,"frm.motivoCierreTipoFormulario.descripcion.label");
	public static final String MOTIVOCIERRETIPOFORMULARIO_TIPOFORMULARIO    = addError(4001,"frm.motivoCierreTipoFormulario.tipoFormulario.label");
	public static final String MOTIVOANULACIONTIPOFORMULARIO_DESCRIPCION    = addError(4001,"frm.motivoAnulacionTipoFormulario.descripcion.label");
	public static final String MOTIVOANULACIONTIPOFORMULARIO_TIPOFORMULARIO    = addError(4001,"frm.motivoAnulacionTipoFormulario.tipoFormulario.label");
	public static final String NUMERACION_TIPOFORMULARIO    = addError(4001,"frm.numeracion.tipoFormulario.label");
	public static final String NUMERACION_SERIE    = addError(4001,"frm.numeracion.serie.label");
	public static final String NUMERACION_VALORDESDE = addError(4001,"frm.numeracion.valorDesde.label");
	public static final String NUMERACION_VALORHASTA = addError(4001,"frm.numeracion.valorHasta.label");
	
}
