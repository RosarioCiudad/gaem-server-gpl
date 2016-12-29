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
package ar.gov.rosario.gait.frm.view.util;

/**
 * 
 * @author tecso.coop
 *
 */
public interface FrmConstants {
	
	// ---> Formulario
	public static final String ACTION_BUSCAR_FORMULARIO     	 = "buscarFormulario";
	public static final String ACTION_ADMINISTRAR_FORMULARIO 	 = "administrarFormulario";
	public static final String ACTION_ADMINISTRAR_ENC_FORMULARIO = "administrarEncFormulario";
	
	public static final String FWD_FORMULARIO_SEARCHPAGE 	    = "formularioSearchPage";
	public static final String FWD_FORMULARIO_VIEW_ADAPTER 		= "formularioViewAdapter";
	public static final String FWD_FORMULARIO_ADAPTER 			= "formularioAdapter";
	public static final String PATH_ADMINISTRAR_FORMULARIO 		= "/frm/AdministrarFormulario";
	
	public static final String FWD_FORMULARIO_ENC_EDIT_ADAPTER 	= "formularioEncEditAdapter";
	// <--- Formulario
	
	// ---> FormularioDetalle
	public static final String ACTION_ADMINISTRAR_FORMULARIODETALLE	 = "administrarFormularioDetalle";
	public static final String FWD_FORMULARIODETALLE_VIEW_ADAPTER 	 = "formularioDetalleViewAdapter";
	public static final String FWD_FORMULARIODETALLE_EDIT_ADAPTER    = "formularioDetalleEditAdapter";
	// <--- FormularioDetalle
    
	// ---> TipoFormulario
	public static final String ACTION_BUSCAR_TIPOFORMULARIO     	= "buscarTipoFormulario";
	public static final String ACTION_ADMINISTRAR_TIPOFORMULARIO 	= "administrarTipoFormulario";
	
	public static final String FWD_TIPOFORMULARIO_SEARCHPAGE 	    = "tipoFormularioSearchPage";
	public static final String FWD_TIPOFORMULARIO_VIEW_ADAPTER 	= "tipoFormularioViewAdapter";
	public static final String FWD_TIPOFORMULARIO_EDIT_ADAPTER 	= "tipoFormularioEditAdapter";
	// <--- TipoFormulario
	
	// ---> Serie
	public static final String ACTION_BUSCAR_SERIE     	= "buscarSerie";
	public static final String ACTION_ADMINISTRAR_SERIE 	= "administrarSerie";
	
	public static final String FWD_SERIE_SEARCHPAGE 	    = "serieSearchPage";
	public static final String FWD_SERIE_VIEW_ADAPTER 	= "serieViewAdapter";
	public static final String FWD_SERIE_EDIT_ADAPTER 	= "serieEditAdapter";
	// <--- Serie
	
	// ---> MotivoCierreTipoFormulario
	public static final String ACTION_BUSCAR_MOTIVOCIERRETIPOFORMULARIO     	= "buscarMotivoCierreTipoFormulario";
	public static final String ACTION_ADMINISTRAR_MOTIVOCIERRETIPOFORMULARIO 	= "administrarMotivoCierreTipoFormulario";
	
	public static final String FWD_MOTIVOCIERRETIPOFORMULARIO_SEARCHPAGE 	    = "motivoCierreTipoFormularioSearchPage";
	public static final String FWD_MOTIVOCIERRETIPOFORMULARIO_VIEW_ADAPTER 	= "motivoCierreTipoFormularioViewAdapter";
	public static final String FWD_MOTIVOCIERRETIPOFORMULARIO_EDIT_ADAPTER 	= "motivoCierreTipoFormularioEditAdapter";
	// <--- MotivoCierreTipoFormulario
	
	// ---> MotivoAnulacionTipoFormulario
	public static final String ACTION_BUSCAR_MOTIVOANULACIONTIPOFORMULARIO     	= "buscarMotivoAnulacionTipoFormulario";
	public static final String ACTION_ADMINISTRAR_MOTIVOANULACIONTIPOFORMULARIO 	= "administrarMotivoAnulacionTipoFormulario";
	
	public static final String FWD_MOTIVOANULACIONTIPOFORMULARIO_SEARCHPAGE 	    = "motivoAnulacionTipoFormularioSearchPage";
	public static final String FWD_MOTIVOANULACIONTIPOFORMULARIO_VIEW_ADAPTER 	= "motivoAnulacionTipoFormularioViewAdapter";
	public static final String FWD_MOTIVOANULACIONTIPOFORMULARIO_EDIT_ADAPTER 	= "motivoAnulacionTipoFormularioEditAdapter";
	// <--- MotivoAnulacionTipoFormulario

	// ---> Numeracion
	public static final String ACTION_BUSCAR_NUMERACION     	= "buscarNumeracion";
	public static final String ACTION_ADMINISTRAR_NUMERACION 	= "administrarNumeracion";

	public static final String FWD_NUMERACION_SEARCHPAGE 	    = "numeracionSearchPage";
	public static final String FWD_NUMERACION_VIEW_ADAPTER 	= "numeracionViewAdapter";
	public static final String FWD_NUMERACION_EDIT_ADAPTER 	= "numeracionEditAdapter";
	// <--- Numeracion
	
	// ---> EstadoTipoFormulario
	public static final String ACTION_BUSCAR_ESTADOTIPOFORMULARIO     	= "buscarEstadoTipoFormulario";
	public static final String ACTION_ADMINISTRAR_ESTADOTIPOFORMULARIO 	= "administrarEstadoTipoFormulario";
	
	public static final String FWD_ESTADOTIPOFORMULARIO_SEARCHPAGE 	    = "estadoTipoFormularioSearchPage";
	public static final String FWD_ESTADOTIPOFORMULARIO_VIEW_ADAPTER 	= "estadoTipoFormularioViewAdapter";
	public static final String FWD_ESTADOTIPOFORMULARIO_EDIT_ADAPTER 	= "estadoTipoFormularioEditAdapter";
	// <--- EstadoTipoFormulario
	
	// ---> ReporteFormulario
	public static final String FWD_REPORTEFORMULARIO_SEARCHPAGE	= "reporteFormularioSearchPage";
	public static final String ACTION_ADMINISTRAR_REPORTEFORMULARIO	= "administrarFormulario";
	// <--- ReporteFormulario
}