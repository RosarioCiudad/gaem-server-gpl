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
package ar.gov.rosario.gait.aid.buss.service;

import java.util.List;

import ar.gov.rosario.gait.aid.iface.model.UsuarioApmReparticionVO;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.tmf.TmfWebServiceManager;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;

/**
 * Obtiene las versiones de cada tabla de una aplicacion.
 * 
 *     GET /gait/api/reparticion/list.json
 * 
 * Input:
 * 
 *     user: Nombre de usuario
 *     utok: Token de session
 *     app:  Codigo de la aplicacion
 * 
 * Response:
 * 
 *     code: 200, 
 *     message: "ok", 
 *     result: {
 *         result: (UsuarioApmReparticionVO[])
 *     } 
 */
public class ReparticionResource {
	To to = new To();

	public Route[] routes() {
		return new Route[] {
				Route.create("GET", "gaem/api/reparticion/list.json", this.getClass(), "list")
		};
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public Reply<List<UsuarioApmReparticionVO>> list(RestRequest<String> req) throws Exception {
		try {
			String username = to.String(req.parameters.get("username"));

			List<UsuarioApmReparticionVO> listUsuarioApmReparticion = 
					TmfWebServiceManager.getInstance().obtenerInspectorReparticion(username);

			return new Reply<List<UsuarioApmReparticionVO>>(listUsuarioApmReparticion);
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
}