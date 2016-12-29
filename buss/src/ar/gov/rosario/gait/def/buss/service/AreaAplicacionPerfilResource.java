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
package ar.gov.rosario.gait.def.buss.service;

import java.util.List;

import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.def.buss.bean.AreaAplicacionPerfil;
import ar.gov.rosario.gait.def.iface.model.AreaAplicacionPerfilVO;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;

public class AreaAplicacionPerfilResource {
	To to = new To();

	public Route[] routes() {
		return new Route[] {
				Route.create("GET", "/gaem/api/areaaplicacionperfil/list.json", this.getClass(), "list")
		};
	}
		
	public Reply<List<AreaAplicacionPerfilVO>> list(RestRequest<String> req) throws Exception {				
		Session session = null;
		try {
			session = GaitHibernateUtil.currentSession();

			List<AreaAplicacionPerfil> listAreaAplicacionPerfil = AreaAplicacionPerfil.getList();
			List<AreaAplicacionPerfilVO> listVO = ListUtilBean.toVO(listAreaAplicacionPerfil, 0);
			
			return new Reply<List<AreaAplicacionPerfilVO>>(listVO);
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}
	
}
