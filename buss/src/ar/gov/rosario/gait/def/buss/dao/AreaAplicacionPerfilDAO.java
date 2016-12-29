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
package ar.gov.rosario.gait.def.buss.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.def.buss.bean.AreaAplicacionPerfil;

public class AreaAplicacionPerfilDAO extends GenericDAO {

	private Logger log = Logger.getLogger(AreaAplicacionPerfilDAO.class);
	
	public AreaAplicacionPerfilDAO() {
		super(AreaAplicacionPerfil.class);
	}
	
	/**
	 * Obtiene Area Aplciacion PErfil
	 */
	public List<AreaAplicacionPerfil> getListByArea(long areaID){
		if (log.isDebugEnabled()) log.debug("getListByArea: enter");
		List<AreaAplicacionPerfil> listAreaAplicacionPerfil;
		String queryString = "from AreaAplicacionPerfil t where t.area.id = :areaID";
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setLong("areaID", areaID);
		listAreaAplicacionPerfil = query.list();
		if (log.isDebugEnabled()) log.debug("getListByArea: exit");
		return listAreaAplicacionPerfil; 
	}
}