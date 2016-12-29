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
package ar.gov.rosario.gait.base.iface.util;

import coop.tecso.demoda.iface.model.CommonView;


/**
 * En esta clase se definen las constantes asociadas con la seguridad comunes a todos los modulos.
 * 
 * @author Tecso Coop. Ltda.
 * 
 */
public class BaseSecurityConstants {

	// Constantes utilizadas como parametros del canAccess de los AdministrarXXDAction
	public static final String BUSCAR                 = "buscar";
	public static final String VER                    = CommonView.METODO_VER;
	public static final String MODIFICAR              = CommonView.METODO_MODIFICAR;
	public static final String ELIMINAR               = CommonView.METODO_ELIMINAR;
	public static final String CLONAR                 = CommonView.METODO_CLONAR;
	public static final String AGREGAR                = CommonView.METODO_AGREGAR;
	public static final String ACTIVAR                = CommonView.METODO_ACTIVAR;
	public static final String DESACTIVAR             = CommonView.METODO_DESACTIVAR;
	public static final String ANULAR          	      = "anular";
	public static final String ACEPTAR          	  = "aceptar";
	public static final String INCLUIR          	  = "incluir";
	public static final String EXCLUIR          	  = "excluir";
	public static final String ENVIAR          	 	  = "enviar";
	public static final String DEVOLVER          	  = "devolver";
	public static final String EMITIR				  = "emitir";
	public static final String GUARDAR          	  = "guardar";
	public static final String VUELTA_ATRAS        	  = "vueltaAtras";
	public static final String RELACIONAR        	  = "relacionar";
	public static final String CONCILIAR        	  = "conciliar";
	public static final String PROCESAR        	  	  = "procesar";
	// usuario y passwros anonimo
	public static final String	USR_ANONIMO           = "internet";
	public static final String	PWD_ANONIMO           = "internet";
}