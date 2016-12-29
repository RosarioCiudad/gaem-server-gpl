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

import ar.gov.rosario.gait.apm.buss.bean.AplicacionPerfil;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.def.buss.bean.Direccion;
import ar.gov.rosario.gait.def.buss.bean.DireccionAplicacionPerfil;

public class DireccionAplicacionPerfilDAO extends GenericDAO {

	private Logger log = Logger.getLogger(DireccionAplicacionPerfilDAO.class);
	
	public DireccionAplicacionPerfilDAO() {
		super(DireccionAplicacionPerfil.class);
	}
	
	
	/**
	 * Obtiene un AplicacionPerfil por su direccion
	 */
	public List<AplicacionPerfil> getListAplicacionPerfilByDireccion(Direccion direccion) {
		if (log.isDebugEnabled()) log.debug("getListAplicacionPerfilByDireccion: enter");
		
		List<AplicacionPerfil> listResult;
		String queryString = "select t.aplicacionPerfil from DireccionAplicacionPerfil t " +
				" where t.direccion.id = :direccionID and t.estado = 1";
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setLong("direccionID", direccion.getId());
		listResult = query.list();

		if (log.isDebugEnabled()) log.debug("getListAplicacionPerfilByDireccion: exit");
		return listResult; 
	}
	
	
	
	
}
