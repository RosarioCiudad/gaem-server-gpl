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
package ar.gov.rosario.gait.not.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.not.buss.bean.TipoNotificacion;
import ar.gov.rosario.gait.not.iface.model.TipoNotificacionSearchPage;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class TipoNotificacionDAO extends GenericDAO {

	private Logger log = Logger.getLogger((TipoNotificacionDAO.class));	

	public TipoNotificacionDAO() {
		super(TipoNotificacion.class);
	}

	@SuppressWarnings("unchecked")
	public List<TipoNotificacion> getBySearchPage(TipoNotificacionSearchPage tipoNotificacionSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM TipoNotificacion t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// descripcion
		query.addIfNotNull(" AND UPPER(TRIM(t.descripcion)) LIKE ?", StringUtil.toUpperLike(tipoNotificacionSearchPage.getTipoNotificacion().getDescripcion()));
		// ubicacionSonido
		query.addIfNotNull(" AND UPPER(TRIM(t.ubicacionSonido)) LIKE ?", StringUtil.toUpperLike(tipoNotificacionSearchPage.getTipoNotificacion().getUbicacionSonido()));
		// ubicacionSonido
		query.addIfNotNull(" AND UPPER(TRIM(t.ubicacionIcono)) LIKE ?", StringUtil.toUpperLike(tipoNotificacionSearchPage.getTipoNotificacion().getUbicacionIcono()));		
		// Order by
		query.add(" ORDER BY t.descripcion");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);

		List<TipoNotificacion> listTipoNotificacion = executeCountedSearch(query, tipoNotificacionSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listTipoNotificacion;
	}

	@SuppressWarnings("unchecked")
	public List<TipoNotificacion> getListByApp(String codApp) {
		log.debug("getListByApp : enter");
		
		String queryStr = "SELECT p.tipoNotificacion FROM NotificacionTipoNotificacion p WHERE p.notificacion.codigo = :codigo ";
		
    	log.debug(" query: " + queryStr);
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryStr).setString("codigo", codApp);
		
		return (ArrayList<TipoNotificacion>)query.list();
	}

	public Integer updateVersionable(String sName) {
		String queryStr = "UPDATE TipoNotificacion SET ubicacionSonido=(ubicacionSonido+1) WHERE descripcion = :descripcion";
		
    	log.debug(" query: " + queryStr);
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryStr).setString("descripcion", sName);
		query.executeUpdate();
		
		queryStr = "SELECT t.ubicacionSonido FROM TipoNotificacion t WHERE t.descripcion = :descripcion";
		query = session.createQuery(queryStr).setString("descripcion", sName);
		
		return (Integer) query.uniqueResult();
	}

}
