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
package ar.gov.rosario.gait.apm.buss.service;

import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilVO;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;

/**
 * Dispositivo Movil Asociado al Usuario
 * 
 *     GET /gait/api/dispositivomovil.json
 * 
 * Input:
 * 
 *     username: Nombre de usuario
 *     usertoken: Token de usuario
 *     devid: Device Id. (string que identifica al dispositivo)
 * 
 * Response:
 * 
 *     code: 200, 
 *     message: "ok", 
 *     result: (string)
 */
public class DispositivoMovilResource {
	private To to = new To();

	public Route[] routes() {
		return new Route[] {
				Route.create("GET", "gaem/api/dispositivomovil.json", this.getClass(), "syncDispositivoMovil")
		};
	}

	/**
	 * 
	 */
	public Reply<DispositivoMovilVO> syncDispositivoMovil(RestRequest<String> req) throws Exception {
		try {
			String deviceID = to.String(req.parameters.get("deviceID"));
			//
			DispositivoMovil dispositivoMovil = DispositivoMovil.getByNumeroIMEI(deviceID);
			//
			DispositivoMovilVO dispositivoMovilVO = (DispositivoMovilVO) dispositivoMovil.toVO(0,false);
			// Nuleo el VO relacionado
//			dispositivoMovilVO.setArea(null);

			return new Reply<DispositivoMovilVO>(dispositivoMovilVO);
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
}