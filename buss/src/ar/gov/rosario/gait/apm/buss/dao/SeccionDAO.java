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
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.apm.buss.bean.Seccion;
import ar.gov.rosario.gait.apm.iface.model.SeccionSearchPage;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class SeccionDAO extends GenericDAO {

	private Logger log = Logger.getLogger(SeccionDAO.class);
	
	public SeccionDAO() {
		super(Seccion.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Seccion> getBySearchPage(SeccionSearchPage seccionSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM Seccion t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// descripcion
		query.addIfNotNull(" AND UPPER(TRIM(t.descripcion)) LIKE ?", StringUtil.toUpperLike(seccionSearchPage.getSeccion().getDescripcion()));
		// Order by
		query.add(" ORDER BY t.descripcion");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);

		List<Seccion> listSeccion = (ArrayList<Seccion>) executeCountedSearch(query, seccionSearchPage);
		
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listSeccion;
	}


	/**
	 * Obtiene un Seccion por su codigo
	 */
	public Seccion getByCodigo(String codigo) throws Exception {
		Seccion seccion;
		String queryString = "from Seccion t where t.codSeccion = :codigo";
		Session session = GaitHibernateUtil.currentSession();
		
		Query query = session.createQuery(queryString).setString("codigo", codigo);
		seccion = (Seccion) query.uniqueResult();
		
		return seccion;
	}
	
	/**
	 * 
	 * @param idAplicacionPerfil
	 * @return
	 * @throws Exception
	 */
	public List<Seccion> getListExcluded(Long idAplicacionPerfil) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		
		query.add("FROM Seccion t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ? ", Estado.ACTIVO.getId());
		// exclude
		query.add(" AND t NOT IN (SELECT p.seccion FROM AplicacionPerfilSeccion p WHERE p.aplicacionPerfil.id = ? AND p.estado = ?) ", 
				idAplicacionPerfil, Estado.ACTIVO.getId());
		// Order by
		query.add(" ORDER BY t.descripcion");
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		
		List<Seccion> result = HibernateQueryMaker.query(query).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		
		return result;
	}
}