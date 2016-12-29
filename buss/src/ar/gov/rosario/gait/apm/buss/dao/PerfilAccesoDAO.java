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

import ar.gov.rosario.gait.apm.buss.bean.PerfilAcceso;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoSearchPage;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class PerfilAccesoDAO extends GenericDAO {

	private Logger log = Logger.getLogger(PerfilAccesoDAO.class);
	
	public PerfilAccesoDAO() {
		super(PerfilAcceso.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<PerfilAcceso> getBySearchPage(PerfilAccesoSearchPage perfilAccesoSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM PerfilAcceso t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		//descripcion
		query.addIfNotNull(" AND UPPER(TRIM(t.descripcion)) LIKE ?", StringUtil.toUpperLike(perfilAccesoSearchPage.getPerfilAcceso().getDescripcion()));
		//area
		query.addIfNotNull(" AND t.area.id = ?", ModelUtil.bussId(perfilAccesoSearchPage.getPerfilAcceso().getArea()));
		// Order by
		query.add(" ORDER BY t.descripcion");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);

		
		List<PerfilAcceso> listPerfilAcceso = executeCountedSearch(query, perfilAccesoSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listPerfilAcceso;
	}

	@SuppressWarnings("unchecked")
	public List<PerfilAcceso> getListByApp(String codApp) {
		log.debug("getListByApp : enter");
		
		String queryStr = "SELECT p.perfilAcceso FROM PerfilAcceso p WHERE p.perfilAcceso.codigo = :codigo ";
		
    	log.debug(" query: " + queryStr);
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryStr).setString("codigo", codApp);
		
		return (ArrayList<PerfilAcceso>)query.list();
	}

	
}
