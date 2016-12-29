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
package ar.gov.rosario.gait.apm.buss.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import ar.gov.rosario.gait.apm.buss.bean.UsuarioApm;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmSearchPage;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class UsuarioApmDAO extends GenericDAO {

	private Logger log = Logger.getLogger(UsuarioApmDAO.class);

	public UsuarioApmDAO() {
		super(UsuarioApm.class);
	}
	
	/**
	 * Obtiene un UsuarioApm por su username
	 */
	public UsuarioApm getByUserName(String username) {
		if (log.isDebugEnabled()) log.debug("getByUserName: enter");
		
		UsuarioApm usuarioApm;
		String queryString = "from UsuarioApm t where t.username = :username and t.estado = 1";
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setString("username", username);
		usuarioApm = (UsuarioApm) query.uniqueResult();	

		if (log.isDebugEnabled()) log.debug("getByUserName: exit");
		return usuarioApm; 
	}
	
	
	@SuppressWarnings("unchecked")
	public List<UsuarioApm> getBySearchPage(UsuarioApmSearchPage usuarioApmSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM UsuarioApm t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// nombre
		query.addIfNotNull(" AND UPPER(TRIM(t.nombre)) LIKE ?", StringUtil.toUpperLike(usuarioApmSearchPage.getUsuarioApm().getNombre()));
		//userName
		query.addIfNotNull(" AND UPPER(TRIM(t.username)) LIKE ?", StringUtil.toUpperLike(usuarioApmSearchPage.getUsuarioApm().getUsername()));
		//numeroInspector
		query.addIfNotNull(" AND UPPER(TRIM(t.numeroInspector)) LIKE ?", StringUtil.toUpperLike(usuarioApmSearchPage.getUsuarioApm().getNumeroInspector()));
		// Order by
		query.add(" ORDER BY t.id");
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		
		List<UsuarioApm> listUsuarioApm = (ArrayList<UsuarioApm>) executeCountedSearch(query, usuarioApmSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listUsuarioApm;
	}
	
	
	
	/**
	 * 
	 */
	public boolean canAccess(String username, String usertoken) {
		if (log.isDebugEnabled()) log.debug("canAccess: enter");
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT id FROM apm_usuarioapm ");
		queryBuilder.append(" WHERE username = :username ");
		queryBuilder.append(" AND usertoken  = :usertoken ");
		queryBuilder.append(" AND estado = :estado ");
		
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createSQLQuery(queryBuilder.toString());
		query.setMaxResults(1);
		query.setString("username", username);
		query.setString("usertoken", usertoken);
		query.setInteger("estado", Estado.ACTIVO.getId());
		
		boolean result = query.uniqueResult() != null;

		if (log.isDebugEnabled()) log.debug("canAccess: exit");
		return result; 
	}
}