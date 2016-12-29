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
package ar.gov.rosario.gait.pro.iface.service;

import ar.gov.rosario.gait.pro.iface.model.CorridaAdapter;
import ar.gov.rosario.gait.pro.iface.model.CorridaSearchPage;
import ar.gov.rosario.gait.pro.iface.model.ProcesoAdapter;
import ar.gov.rosario.gait.pro.iface.model.ProcesoSearchPage;
import ar.gov.rosario.gait.pro.iface.model.ProcesoVO;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.UserContext;

public interface IProConsultaService {
	
	// ---> ABM_CORRIDA
	public CorridaSearchPage getCorridaSearchPageInit(UserContext usercontext) throws DemodaServiceException;
	public CorridaSearchPage getCorridaSearchPageResult(UserContext usercontext, CorridaSearchPage corridaSearchPage) throws DemodaServiceException;
	public CorridaAdapter getCorridaAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	// <--- ABM_CORRIDA
	
	// ---> ABM Proceso
	public ProcesoSearchPage getProcesoSearchPageInit(UserContext userContext) throws DemodaServiceException;
	public ProcesoSearchPage getProcesoSearchPageResult(UserContext userContext, ProcesoSearchPage procesoSearchPage) throws DemodaServiceException;
	public ProcesoAdapter getProcesoAdapterForCreate(UserContext userContext) throws DemodaServiceException;
	public ProcesoVO createProceso(UserContext userContext, ProcesoVO procesoVO) throws DemodaServiceException;
	public ProcesoVO updateProceso(UserContext userContext, ProcesoVO procesoVO) throws DemodaServiceException;
	public ProcesoVO deleteProceso(UserContext userContext, ProcesoVO procesoVO) throws DemodaServiceException;
	public ProcesoAdapter getProcesoAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException ;
	public ProcesoAdapter getProcesoAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException ;	
	
	public ProcesoAdapter getProcesoAdapterParamTipoEjecucion(UserContext userContext, ProcesoAdapter procesoAdapter) throws DemodaServiceException;
	// <--- ABM Proceso	
	
}