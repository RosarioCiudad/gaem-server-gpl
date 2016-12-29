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
package ar.gov.rosario.gait.base.buss;

import coop.tecso.demoda.sys.ServiceCaller;
import coop.tecso.demoda.util.To;

public class ResourceCaller implements ServiceCaller {
	static final To to = new To(); 
	@Override
	public Object call(Class<?> klass, String methodName, Object params) {
		//Map<String, Object> reqparms = Work.attr(WorkAttr)
		
//		if (!klass.getName().equals("ar.gov.rosario.gait.seg.buss.service.LoginResource")) {
//			// Determinar UserContext
//			String user = Work.get().  req.getParameter("user");
//			String stoken = req.getParameter("utok");
//			UserContext userContext = SegServiceLocator.getSeguridadService().initUserApm(user, stoken);
//			DemodaUtil.setCurrentUserContext(userContext);
//		}
		return null;
	}
	
}
