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

import ar.gov.rosario.gait.frm.iface.model.ReporteFormularioSearchPage;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.model.UserContext;

public interface IFrmReporteService {
	
	// ---> ABM ReporteFormulario
	public ReporteFormularioSearchPage getReporteFormularioSearchPageInit(UserContext usercontext) throws DemodaServiceException;
	public ReporteFormularioSearchPage getReporteFormularioSearchPageResult(UserContext usercontext, ReporteFormularioSearchPage reporteFormularioSearchPage) throws DemodaServiceException;
	public ReporteFormularioSearchPage imprimirReporteFormulario(UserContext usercontext, ReporteFormularioSearchPage reporteFormularioSearchPage) throws DemodaServiceException;
	
	/*
	public ReporteFormularioAdapter getReporteFormularioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public ReporteFormularioAdapter getReporteFormularioAdapterForCreate(UserContext userContext) throws DemodaServiceException;
	public ReporteFormularioAdapter getReporteFormularioAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	
	public ReporteFormularioVO createReporteFormulario(UserContext userContext, ReporteFormularioVO reporteFormularioVO ) throws DemodaServiceException;
	public ReporteFormularioVO updateReporteFormulario(UserContext userContext, ReporteFormularioVO reporteFormularioVO ) throws DemodaServiceException;
	public ReporteFormularioVO deleteReporteFormulario(UserContext userContext, ReporteFormularioVO reporteFormularioVO ) throws DemodaServiceException;
	public ReporteFormularioVO activarReporteFormulario(UserContext userContext, ReporteFormularioVO reporteFormularioVO ) throws DemodaServiceException;
	public ReporteFormularioVO desactivarReporteFormulario(UserContext userContext, ReporteFormularioVO reporteFormularioVO ) throws DemodaServiceException;	
	public ReporteFormularioAdapter imprimirReporteFormulario(UserContext userContext, ReporteFormularioAdapter reporteFormularioAdapterVO ) throws DemodaServiceException;
	*/
	// <--- ABM ReporteFormulario
	
}