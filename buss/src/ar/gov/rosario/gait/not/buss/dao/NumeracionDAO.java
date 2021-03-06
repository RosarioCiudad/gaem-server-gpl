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

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.frm.buss.bean.Numeracion;
import ar.gov.rosario.gait.frm.buss.bean.TipoFormulario;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.Estado;

public class NumeracionDAO extends GenericDAO {

	private Logger log = Logger.getLogger(NumeracionDAO.class);
	
	public NumeracionDAO() {
		super(Numeracion.class);
	}

	/**
	 * Obtiene un Numeracion por su codigo
	 */
	public Numeracion getByCodigo(String codigo) throws Exception {
		Numeracion numeracion;
		String queryString = "from Numeracion t where t.codNumeracion = :codigo";
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setString("codigo", codigo);
		numeracion = (Numeracion) query.uniqueResult();	

		return numeracion; 
	}
	
	
	/**
	 * Obtiene Numeracion disponible por TipoFormulario
	 */
	public Numeracion getDisponibleByTipoFormulario(TipoFormulario tipoFormulario) {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		Numeracion numeracion;
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		
		query.add("FROM Numeracion t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// TipoFormulario
		query.add(" AND t.tipoFormulario.id = ?", tipoFormulario.getId());
		// ValorRestante
		query.add(" AND t.valorRestante > 0");
		// Order by
		query.add(" ORDER BY t.serie.codigo ");
		
		
		numeracion = (Numeracion) HibernateQueryMaker.query(query).setMaxResults(1).uniqueResult();
		
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return numeracion; 
	}
	
}
