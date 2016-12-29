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
package ar.gov.rosario.gait.gps.buss.dao;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.def.buss.bean.Area;
import ar.gov.rosario.gait.gps.buss.bean.HistorialUbicacion;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.Estado;

public class HistorialUbicacionDAO extends GenericDAO {

	private Logger log = Logger.getLogger(HistorialUbicacionDAO.class);
	
	public HistorialUbicacionDAO() {
		super(HistorialUbicacion.class);
	}
	
	/**
	 * 
	 * @param area
	 * @return
	 */
	public List<HistorialUbicacion> getLastByArea(Area area){
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		//--
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -5);
		//--
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		query.add("FROM HistorialUbicacion t WHERE 1=1 ");
		// 
		query.add("AND t.fechaUbicacion = ("
				+ " SELECT MAX(u.fechaUbicacion) "
				+ " FROM HistorialUbicacion u "
				+ " WHERE u.usuarioApm.id = t.usuarioApm.id "
				+ " AND u.area.id = ? "
				+ " AND u.fechaUbicacion > ?)", area.getId(), calendar.getTime());
		// Area
		query.add("AND t.area.id = ? ", area.getId());
		// Estado
		query.add("AND t.estado = ? ", Estado.ACTIVO.getId());
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		List<HistorialUbicacion> result = HibernateQueryMaker.query(query).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return result; 
	}
}
