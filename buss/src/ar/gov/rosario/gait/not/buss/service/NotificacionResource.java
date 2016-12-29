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
package ar.gov.rosario.gait.not.buss.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.not.buss.bean.NotNotificacionManager;
import ar.gov.rosario.gait.not.buss.bean.Notificacion;
import ar.gov.rosario.gait.not.iface.model.NotificacionVO;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;

public class NotificacionResource {

	To to = new To();

	public Route[] routes() {
		return new Route[] {
				Route.create("GET", "/gait/api/pendingNotifications.json", this.getClass(), "getPendingNotifications"),
				Route.create("GET", "/gait/api/receiveNotification.json", this.getClass(), "receiveNotification")
		};
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Reply<List<NotificacionVO>> getPendingNotifications(RestRequest<String> req) throws Exception {
		System.out.println("getPendingNotifications : enter");
		try {
			Integer deviceID = to.Integer(req.parameters.get("deviceID"));
			List<Notificacion> objs = NotNotificacionManager.getPendingNotifications(deviceID);
			
			return new Reply<List<NotificacionVO>>(ListUtilBean.toVO(objs,2));
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	/**
	 * 
	 */
	public Reply<Object> receiveNotification(RestRequest<String> req) throws Exception {
		System.out.println(" receiveNotification : enter");
		Session session = null;
		Transaction tx = null;
		try {
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			
			Long nID = to.Long(req.parameters.get("notificacionID"));
			NotNotificacionManager.receiveNotification(nID);
			
			tx.commit();
			
			return new Reply<Object>("OK");
		} catch (Exception e) {
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}


}