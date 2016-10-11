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