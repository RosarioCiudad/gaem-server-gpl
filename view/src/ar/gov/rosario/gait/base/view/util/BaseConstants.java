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
package ar.gov.rosario.gait.base.view.util;



public class BaseConstants {

	// TODO: analizar si conviene subier estas constantes a demoda
	public static final String FWD_SESSION_ERROR = "sessionError";
	public static final String FWD_SESSION_ANONIMO_ERROR = "sessionAnonimoError";
	public static final String FWD_ERROR_NAVEGACION = "gaitErrorNavegacion";
	public static final String FWD_ERROR_SERVER = "gaitErrorServer";
	public static final String FWD_VIEW_IMPRIMIR = ".base.view.imprimir";
	

	public static final String FWD_GAITINDEX = "gaitIndex";
	public static final String SESSION_ERROR_DESCRIPTION = "gaitIndex";	
	public static final String SUCCESS_MESSAGE_DESCRIPTION = "La operaci\u00F3n ha sido realizada con \u00E9xito";	
	public static final String EXCEPTION_MESSAGE_DESCRIPTION = "La aplicaci\u00F3n no pudo concretar la transacci\u00F3n";
	
	/*El action Base utiliza esta constante para decidir si activa el modo ABM o seleccion */
	public static final String ACT_GAIT_MENU         = "/seg/GaitMenu"; 
	public static final String FWD_GAIT_BUILD_MENU   = "/seg/GaitMenu.do?method=build"; 
	
	//	ACTs en General --------------------------------------------------------
	public static final String ACT_INICIALIZAR = "inicializar";
	public static final String ACT_BUSCAR      = "buscar";
	public static final String ACT_VER         = "ver";
	public static final String ACT_AGREGAR     = "agregar";
	public static final String ACT_MODIFICAR   = "modificar";
	public static final String ACT_ELIMINAR    = "eliminar";
	public static final String ACT_ACTIVAR     = "activar";
	public static final String ACT_DESACTIVAR  = "desactivar";
	public static final String ACT_VOLVER      = "volver";
	public static final String ACT_PARAM       = "param";
	public static final String ACT_SELECCIONAR = "seleccionar";
	public static final String ACT_REFILL      = "refill";
	public static final String ACT_BUSCAR_AVANZADO  = "buscarAva";
	public static final String ACT_IMPRIMIR    = "imprimir";
	public static final String ACT_BAJA        = "baja";
	public static final String ACT_ALTA        = "alta";
	public static final String ACT_ANULAR      = "anular";
	public static final String ACT_ACEPTAR     = "aceptar";
	public static final String ACT_ENVIAR      = "enviar";
	public static final String ACT_DEVOLVER    = "devolver";
	public static final String ACT_ASIGNAR	   = "asignar";
	public static final String ACT_VUELTA_ATRAS = "vueltaAtras";
	public static final String ACT_RELACIONAR = "relacionar";
	public static final String ACT_APLICAR = "aplicar";
	public static final String ACT_SIMULAR = "simular";
	public static final String ACT_CONCILIAR = "conciliar";
	public static final String ACT_PROCESAR = "procesar";
	public static final String ACT_CLONAR = "clonar";
    //	Fin ACTs en General ----------------------------------------------------
	
	//	Metodos en General -----------------------------------------------------
	public static final String METHOD_INICIALIZAR = "inicializar";
    //	Fin ACTs en General ----------------------------------------------------	
	
	public static final String FWD_MSG         = "gaitMessage";
	public static final String FWD_ERROR_PRINT = "gaitErrorPrint";
	public static final String FWD_GAIT_OFFLINE  = "gaitOffLine";
	
	public static final String SELECTED_ID ="selectedId";
}
