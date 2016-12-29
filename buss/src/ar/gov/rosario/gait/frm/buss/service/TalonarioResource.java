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

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.frm.iface.model.TalonarioVO;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.util.To;

/**
 * Gestion de Talonario de usuario
 * 
 *     GET /gait/api/talonario.json
 * 
 * Input:
 * 
 *     username: Nombre de usuario
 *     password: Password
 *     deviceid: Device Id. (string que identifica al dispositivo)
 * 	   tipoformularioid:  Tipo de Formulario Id. (string que identifica al talonario)
 * 
 * Response:
 * 	{	
 *     code: 200, 
 *     message: "ok", 
 *     result: (string)
 *  } 
 */
public class TalonarioResource {

	private Logger log = Logger.getLogger(TalonarioResource.class);
	private To to = new To();

	public Route[] routes() {
		return new Route[] {
				Route.create("GET", "/gait/api/list/talonario.json", this.getClass(), "getListTalonario")
		};
	}

	/**
	 * 
	 */
	public Reply<List<TalonarioVO>> getListTalonario(RestRequest<String> req) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		String deviceid = to.String(req.parameters.get("deviceid"));
		String tipoformularioid = to.String(req.parameters.get("tipoformularioid"));
		int cantidad = 100; 
		try {
			// Gestion de Talonario
			List<TalonarioVO> talonarioList = 
					FrmFormularioManager.getListTalonario(tipoformularioid, deviceid, cantidad);

			if(talonarioList == null){
				String msg = "Error al gestionar numeración: Asignación inválida";
				return new Reply<List<TalonarioVO>>(500, msg, null);
			}
			// Talonario
			return new Reply<List<TalonarioVO>>(talonarioList);
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
}