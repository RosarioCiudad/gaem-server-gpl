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

import ar.gov.rosario.gait.pro.iface.model.AdpCorridaAdapter;
import ar.gov.rosario.gait.pro.iface.model.CorridaVO;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.UserContext;

public interface IProAdpProcesoService {

	// ---> ABM_CORRIDA
	public AdpCorridaAdapter getAdpCorridaAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;

	public CorridaVO activarProceso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException;
	public CorridaVO reprogramarProceso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException;
	public CorridaVO cancelarProceso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException;
	public CorridaVO reiniciarProceso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException;
	public CorridaVO siguientePaso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException;

	public String obtenerFileCorridaName(Long idFileCorrida) throws DemodaServiceException;
	// <--- ABM_CORRIDA

	public AdpCorridaAdapter getEstadoPaso(UserContext userSession, CommonKey id, Integer paso) throws DemodaServiceException;
	public String getLogFile(UserContext userSession, CommonKey id) throws DemodaServiceException;

}
