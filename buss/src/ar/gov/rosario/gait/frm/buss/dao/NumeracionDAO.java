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
package ar.gov.rosario.gait.frm.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.frm.buss.bean.Numeracion;
import ar.gov.rosario.gait.frm.iface.model.NumeracionSearchPage;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.model.Estado;

public class NumeracionDAO extends GenericDAO {

	private Logger log = Logger.getLogger(NumeracionDAO.class);
	
	public NumeracionDAO() {
		super(Numeracion.class);
	}
	
	public Numeracion getByCodigo(String codigo) {
		Numeracion numeracion;
		String queryString = "from Numeracion t where t.codigo = :codigo";
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setString("codigo", codigo);
		numeracion = (Numeracion) query.uniqueResult();	

		return numeracion; 
	}
	
	@SuppressWarnings("unchecked")
	public List<Numeracion> getBySearchPage(NumeracionSearchPage numeracionSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM Numeracion t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		//tratamiento
		query.addIfNotNull(" AND t.serie.id = ?", ModelUtil.bussId(numeracionSearchPage.getNumeracion().getSerie()));
		// tipoFormulario
		query.addIfNotNull(" AND t.tipoFormulario.id = ?", ModelUtil.bussId(numeracionSearchPage.getNumeracion().getTipoFormulario()));
		
		// Order by
		query.add(" ORDER BY t.id");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		
		List<Numeracion> listNumeracion = (ArrayList<Numeracion>) executeCountedSearch(query, numeracionSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listNumeracion;
	}


}

