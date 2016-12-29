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

import javax.servlet.http.HttpServletRequest;

import ar.gov.rosario.gait.seg.iface.service.SegServiceLocator;
import coop.tecso.demoda.http.ControlServlet;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.UserContext;

public class GaitControlServlet extends ControlServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void preInvoke(HttpServletRequest req, Class<?> klass, String methodname) {
		if (!klass.getName().equals("ar.gov.rosario.gait.seg.buss.service.LoginResource")) {
			// Determinar UserContext
			String username = req.getParameter("username");
			String usertoken = req.getParameter("usertoken");
			UserContext userContext = SegServiceLocator.getSeguridadService().initUserApm(username, usertoken);
			DemodaUtil.setCurrentUserContext(userContext);
		}
	}
	
}
