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
package ar.gov.rosario.gait.frm.buss.service;

import java.util.List;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.frm.buss.bean.EstadoTipoFormulario;
import ar.gov.rosario.gait.frm.iface.model.EstadoTipoFormularioVO;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;


	public class EstadoTipoFormularioResource {
		To to = new To();

		public Route[] routes() {
			return new Route[] {
					Route.create("GET", "/gait/api/estadotipoformulario/list.json", this.getClass(), "list")
			};
		}
		
		public Reply<List<EstadoTipoFormularioVO>> list(RestRequest<String> req) throws Exception {
			
			try {
				String app = to.String(req.parameters.get("app"));
				
				List<EstadoTipoFormulario> objs = FrmFormularioManager.getFrmEstadoTipoFormulario(app);
				
				return new Reply<List<EstadoTipoFormularioVO>>(ListUtilBean.toVO(objs));
			} catch (Exception e) {
				throw new DemodaServiceException(e);
			} finally {
				GaitHibernateUtil.closeSession();
			}
		}
}