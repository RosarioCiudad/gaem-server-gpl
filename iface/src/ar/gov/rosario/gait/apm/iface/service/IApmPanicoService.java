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
package ar.gov.rosario.gait.apm.iface.service;

import ar.gov.rosario.gait.apm.iface.model.PanicoAdapter;
import ar.gov.rosario.gait.apm.iface.model.PanicoSearchPage;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.UserContext;

public interface IApmPanicoService {
	
	// ----> APM Panico				
	public PanicoSearchPage getPanicoSearchPageInit(UserContext userContext) throws DemodaServiceException;
	public PanicoSearchPage getPanicoSearchPageResult(UserContext usercontext, PanicoSearchPage panicoSearchPageVO) throws DemodaServiceException;
	
	public PanicoAdapter getPanicoAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public PanicoAdapter getPanicoAdapterForView(UserContext userContext, CommonKey commonKey, PanicoSearchPage panicoSearchPageVO) throws DemodaServiceException;
	public PanicoAdapter getPanicoAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public PanicoAdapter getPanicoAdapterForUpdate(UserContext userContext, CommonKey commonKey, PanicoSearchPage panicoSearchPageVO) throws DemodaServiceException;

	public PanicoAdapter getPanicoAdapterForCambioEstado(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public PanicoAdapter cambiarEstadoPanico(UserContext userContext, PanicoAdapter panicoAdapter) throws DemodaServiceException;
	// ----> APM Panico
	
}