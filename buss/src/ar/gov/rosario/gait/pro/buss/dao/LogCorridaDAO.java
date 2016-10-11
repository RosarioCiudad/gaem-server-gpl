package ar.gov.rosario.gait.pro.buss.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.pro.buss.bean.LogCorrida;

public class LogCorridaDAO extends GenericDAO {

	private Logger log = Logger.getLogger((LogCorridaDAO.class));	
	
	public LogCorridaDAO() {
		super(LogCorrida.class);
	}
	
	/**
	 * Elimina los registros de LogCorrida que corresponden a la corrida de id pasado.
	 * 
	 * @param idCorrida
	 * @return int
	 */
	public int deleteAllByIdCorrida (Long idCorrida){

		String queryString = "delete from LogCorrida t ";
			   queryString += " where t.corrida.id = "+idCorrida; 
		
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryString);
	    
		return query.executeUpdate();
	}
}
