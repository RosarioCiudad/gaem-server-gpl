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

import ar.gov.rosario.gait.apm.buss.bean.Aplicacion;
import ar.gov.rosario.gait.apm.iface.model.AplicacionSearchPage;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class AplicacionDAO extends GenericDAO {

	private Logger log = Logger.getLogger(AplicacionDAO.class);

	public AplicacionDAO() {
		super(Aplicacion.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Aplicacion> getBySearchPage(AplicacionSearchPage aplicacionSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM Aplicacion t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// descripcion
		query.addIfNotNull(" AND UPPER(TRIM(t.descripcion)) LIKE ?", StringUtil.toUpperLike(aplicacionSearchPage.getAplicacion().getDescripcion()));
		//codigo
		query.addIfNotNull(" AND UPPER(TRIM(t.codigo)) LIKE ?", StringUtil.toUpperLike(aplicacionSearchPage.getAplicacion().getCodigo()));
		
		// Order by
		query.add(" ORDER BY t.id");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);

		List<Aplicacion> listAplicacion = (ArrayList<Aplicacion>) executeCountedSearch(query, aplicacionSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listAplicacion;
	}
}