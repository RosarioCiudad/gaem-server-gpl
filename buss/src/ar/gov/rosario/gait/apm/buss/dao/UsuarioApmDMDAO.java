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


import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import ar.gov.rosario.gait.apm.buss.bean.UsuarioApmDM;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import coop.tecso.demoda.iface.model.Estado;

public class UsuarioApmDMDAO extends GenericDAO {

	private Logger log = Logger.getLogger(UsuarioApmDMDAO.class);

	public UsuarioApmDMDAO() {
		super(UsuarioApmDM.class);
	}

	/**
	 * Determina si existe UsuarioApmDM por username y deviceID
	 */
	public boolean hasAccess(String username, String deviceID) {
		if (log.isDebugEnabled()) log.debug("hasAccess: enter");
		Session session = GaitHibernateUtil.currentSession();
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT usudm.id ");
		queryBuilder.append(" FROM apm_usuarioapmdm usudm, apm_usuarioapm usu, apm_dispositivoMovil dm ");
		queryBuilder.append(" WHERE usudm.idUsuario = usu.id ");
		queryBuilder.append(" AND usudm.estado = "+ Estado.ACTIVO.getId());
		queryBuilder.append(" AND usudm.idDispositivoMovil = dm.id ");
		queryBuilder.append(" AND usu.username = :username ");
		queryBuilder.append(" AND usu.estado = "+ Estado.ACTIVO.getId());
		queryBuilder.append(" AND dm.numeroIMEI = :deviceID ");
		queryBuilder.append(" AND dm.estado = "+ Estado.ACTIVO.getId());

		Query query = session.createSQLQuery(queryBuilder.toString());
		query.setMaxResults(1);
		query.setString("username", username);
		query.setString("deviceID", deviceID);

		boolean result = query.uniqueResult() != null;
		if (log.isDebugEnabled()) log.debug("hasAccess: exit");
		return  result;	
	}
}