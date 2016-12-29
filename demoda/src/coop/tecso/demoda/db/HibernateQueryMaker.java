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

package coop.tecso.demoda.db;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class HibernateQueryMaker  {

	public HibernateQueryMaker() {
	}
	
	public static QueryMaker make(Session session) {
		return new QueryMakerHbm(session);
	}

	public static QueryMaker make() {
		return new QueryMakerHbm();
	}

	public static Query query(QueryMaker queryMaker) {
		QueryMakerHbm hbm = (QueryMakerHbm) queryMaker;		
		return query(hbm.session, hbm.queryString(), hbm.parameters());
	}

	public static Query sqlQuery(QueryMaker queryMaker) {
		QueryMakerHbm hbm = (QueryMakerHbm) queryMaker;		
		return sqlQuery(hbm.session, hbm.queryString(), hbm.parameters());
	}

	public static Query query(Session session, String queryString, List<Object> parameters) {
		Query query = session.createQuery(queryString);
		return setParameters(query, parameters);
	}

	public static Query sqlQuery(Session session, String sqlQueryString, List<Object> parameters) {
		Query query = session.createSQLQuery(sqlQueryString);
		return setParameters(query, parameters);
	}

	private static Query setParameters(Query query, List<Object> params) {		
		if (params == null)
			return query;
		
		int i = 0;
		for(Object param : params) {
			query.setParameter(i++, param);
		}
		return query;
	}
}