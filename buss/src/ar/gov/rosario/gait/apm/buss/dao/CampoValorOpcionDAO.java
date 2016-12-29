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

import java.util.List;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampoValor;
import ar.gov.rosario.gait.apm.buss.bean.CampoValorOpcion;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.Estado;


public class CampoValorOpcionDAO extends GenericDAO {

	private Logger log = Logger.getLogger(CampoValorOpcionDAO.class);

	public CampoValorOpcionDAO() {
		super(CampoValorOpcion.class);
	}
	
	
	/**
	 * 
	 * @param idAplPerfilSeccionCampo
	 * @return
	 */
	public List<CampoValorOpcion> getListExcluded(AplPerfilSeccionCampoValor aplPerfilSeccionCampoValor) {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());

		query.add("FROM CampoValorOpcion t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ? ", Estado.ACTIVO.getId());
		// exclude
		query.add(" AND t NOT IN (SELECT p.campoValorOpcion " +
				" FROM AplPerfilSeccionCampoValorOpcion p WHERE p.aplPerfilSeccionCampoValor.id = ? AND p.estado = ?) ", 
				aplPerfilSeccionCampoValor.getId(), Estado.ACTIVO.getId());
		//
		query.add(" AND t.campoValor.id = ? ", aplPerfilSeccionCampoValor.getCampoValor().getId());
		// Order by
		query.add(" ORDER BY t.etiqueta");
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);

		List<CampoValorOpcion> result = HibernateQueryMaker.query(query).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");

		return result;
	}
}