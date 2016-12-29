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
package ar.gov.rosario.gait.gps.buss.bean;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.gps.buss.dao.GpsDAOFactory;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DemodaUtil;

/**
 * Manejador del modulo Gps y submodulo Ubicacion
 * 
 * @author tecso
 *
 */
public class GpsUbicacionManager {
		
	private static Logger log = Logger.getLogger(GpsUbicacionManager.class);
	
	private static final GpsUbicacionManager INSTANCE = new GpsUbicacionManager();
	
	/**
	 * Constructor privado
	 */
	private GpsUbicacionManager() {
		
	}
	
	/**
	 * Devuelve unica instancia
	 */
	public static GpsUbicacionManager getInstance() {
		return INSTANCE;
	}

	
	public void saveHistorialUbicacion(HistorialUbicacion historialUbicacion) 
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		Session session = null;
		Transaction tx = null; 
		try {
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			
			createHistorialUbicacion(historialUbicacion);
			
			if (historialUbicacion.validateUpdate()) {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
			} else {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			}
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		}
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
	}
	
	
	// ---> ABM HistorialUbicacion
	public HistorialUbicacion createHistorialUbicacion(HistorialUbicacion historialUbicacion) throws Exception {

		// Validaciones de negocio
		if (!historialUbicacion.validateCreate()) {
			return historialUbicacion;
		}

		GpsDAOFactory.getHistorialUbicacionDAO().update(historialUbicacion);

		return historialUbicacion;
	}
	
	public HistorialUbicacion updateHistorialUbicacion(HistorialUbicacion historialUbicacion) throws Exception {
		
		// Validaciones de negocio
		if (!historialUbicacion.validateUpdate()) {
			return historialUbicacion;
		}

		GpsDAOFactory.getHistorialUbicacionDAO().update(historialUbicacion);
		
		return historialUbicacion;
	}
	
	public HistorialUbicacion deleteHistorialUbicacion(HistorialUbicacion historialUbicacion) throws Exception {
	
		// Validaciones de negocio
		if (!historialUbicacion.validateDelete()) {
			return historialUbicacion;
		}
		
		GpsDAOFactory.getHistorialUbicacionDAO().delete(historialUbicacion);
		
		return historialUbicacion;
	}
	// <--- ABM HistorialUbicacion
}