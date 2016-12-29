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
package ar.gov.rosario.gait.frm.iface.service;

import org.apache.log4j.Logger;

/**
 * Formulario - Service locator
 * @author tecso
 */
public class FrmServiceLocator {

	static Logger log = Logger.getLogger(FrmServiceLocator.class);

	// Implementaciones de servicio	
	private static String MODULO = "ar.gov.rosario.gait.frm.buss.service.";
	private static String FORMULARIO_SERVICE_IMPL = MODULO + "FrmFormularioServiceHbmImpl";
	private static String NUMERACION_SERVICE_IMPL = MODULO + "FrmNumeracionServiceHbmImpl";
	private static String REPORTE_SERVICE_IMPL = MODULO + "FrmReporteServiceHbmImpl";

	// Instancia
	public static final FrmServiceLocator INSTANCE = new FrmServiceLocator();

	private IFrmFormularioService formularioServiceHbmImpl;
	private IFrmNumeracionService numeracionServiceHbmImpl;
	private IFrmReporteService 	  reporteServiceHbmImpl;

	// Constructor de instancia
	public FrmServiceLocator() {
		try {
			this.formularioServiceHbmImpl = (IFrmFormularioService) Class.forName(FORMULARIO_SERVICE_IMPL).newInstance();
			this.numeracionServiceHbmImpl = (IFrmNumeracionService) Class.forName(NUMERACION_SERVICE_IMPL).newInstance();
			this.reporteServiceHbmImpl 	  = (IFrmReporteService) Class.forName(REPORTE_SERVICE_IMPL).newInstance();
		} catch (Exception e) {
			log.error("No se pudo crear la clase", e);
		}
	}

	public static IFrmFormularioService getFormularioService(){
		return INSTANCE.formularioServiceHbmImpl;		
	}
	
	public static IFrmNumeracionService getNumeracionService(){
		return INSTANCE.numeracionServiceHbmImpl;		
	}
	
	public static IFrmReporteService getReporteService(){
		return INSTANCE.reporteServiceHbmImpl;		
	}
}