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

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.service.ApmAplicacionServiceHbmImpl;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.def.buss.bean.DefConfiguracionManager;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.util.To;

public class EntityDeltaResource {
	
	private Logger log = Logger.getLogger(ApmAplicacionServiceHbmImpl.class);
	private To to = new To();

	/* ROUTE  : startWith(/gaem/api/delta) -> EntityDelta.delta
	 * EJEMPLO: /gaem/api/delta?entidad=UsuarioApm&version=1  -> EntityDelta.delta
	 */
	public Route[] routes() {
		return new Route[] {
				Route.create("GET", "/gaem/api/delta/list.json", this.getClass(), "getDelta"),
				Route.create("GET", "/gaem/api/delta/entity.json", this.getClass(), "getEntity"),
				Route.create("GET", "/gaem/api/delta/listByAplicacion.json", this.getClass(), "getDeltaByAplicacion"),
				Route.create("GET", "/gaem/api/delta/listByUsuario.json", this.getClass(), "getDeltaByUsuario"),
				Route.create("GET", "/gaem/api/delta/listByArea.json", this.getClass(), "getDeltaByArea")
		};
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Reply<List<Object>> getDelta(RestRequest<String> req) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			String entidad  = to.String(req.parameters.get("entidad"));
			Integer version = to.Integer(req.parameters.get("version"));

			List<VersionableBO> objs = DefConfiguracionManager.getEntities(entidad, version);
			return new Reply<List<Object>>(ListUtilBean.toVO(objs, 1, false));
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Reply<List<Object>> getDeltaByUsuario(RestRequest<String> req) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			String entidad  = to.String(req.parameters.get("entidad"));
			Integer version = to.Integer(req.parameters.get("version"));
			String username = to.String(req.parameters.get("username"));

			List<VersionableBO> objs = DefConfiguracionManager.getEntitiesByUsuario(entidad, version, username);
			return new Reply<List<Object>>(ListUtilBean.toVO(objs, 1, false));
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public Reply<Object> getEntity(RestRequest<String> req) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			String entidad = to.String(req.parameters.get("entidad"));
			String id = to.String(req.parameters.get("id"));

			VersionableBO obj = DefConfiguracionManager.getEntity(entidad, Integer.valueOf(id));
			if (obj!=null)
				return new Reply<Object>(obj.toVO());
			else
				return new Reply<Object>(new Object());
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Reply<List<Object>> getDeltaByAplicacion(RestRequest<String> req) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			String entidad 	 = to.String(req.parameters.get("entidad"));
			Integer version  = to.Integer(req.parameters.get("version"));
			String codigoApp = to.String(req.parameters.get("codigoApp"));

			List<VersionableBO> objs = DefConfiguracionManager.getEntities(entidad, version, codigoApp);

			return new Reply<List<Object>>(ListUtilBean.toVO(objs,1,false));
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Reply<List<Object>> getDeltaByArea(RestRequest<String> req) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			String entidad  = to.String(req.parameters.get("entidad"));
			Integer version = to.Integer(req.parameters.get("version"));
			Long areaId = to.Long(req.parameters.get("areaId"));

			List<VersionableBO> objs = DefConfiguracionManager.getEntitiesByArea(entidad, version, areaId);
			return new Reply<List<Object>>(ListUtilBean.toVO(objs, 1, false));
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
}