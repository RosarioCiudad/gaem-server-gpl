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

import ar.gov.rosario.gait.apm.buss.bean.EstadoPanico;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;

public class EstadoPanicoDAO extends GenericDAO {

	private Logger log = Logger.getLogger(EstadoPanicoDAO.class);

	public EstadoPanicoDAO() {
		super(EstadoPanico.class);
	}

	public List<EstadoPanico> getListTransicionesForEstado(EstadoPanico estadoPanico) {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		String transiciones = estadoPanico.getTransiciones();
		if(StringUtil.isNullOrEmpty(transiciones)) return new ArrayList<>();

		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		query.add("FROM EstadoPanico t WHERE 1=1 ");
		if (transiciones.equals("*")){
			query.add("AND esInicial = 0 AND t.id <> ? ", estadoPanico.getId());
		}else {
			query.add("AND t.id IN ("+transiciones+")");
		}

		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		List<EstadoPanico> result = HibernateQueryMaker.query(query).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return result;
	}
}