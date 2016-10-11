package ar.gov.rosario.gait.pro.buss.dao;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.pro.buss.bean.Corrida;
import ar.gov.rosario.gait.pro.buss.bean.PasoCorrida;

public class PasoCorridaDAO extends GenericDAO {

	//private Logger log = Logger.getLogger((PasoCorridaDAO.class));	
	
	public PasoCorridaDAO() {
		super(PasoCorrida.class);
	}
	
	/**
	 * Obtiene el PasoCorrida a partir de la Corrida y del Paso
	 * @param  corrida
	 * @param  paso
	 * @return PasoCorrida
	 */
	public PasoCorrida getByCorridaPaso(Corrida corrida, Integer paso){
		
		Session session = GaitHibernateUtil.currentSession();
		String sQuery = "FROM PasoCorrida pc WHERE pc.corrida = :corrida " +
				"AND paso = :paso";

		Query query = session.createQuery(sQuery)
			.setEntity("corrida", corrida)
			.setInteger("paso", paso);
		return (PasoCorrida) query.uniqueResult();		

		
	}
	
	/**
	 * Elimina los registros de PasoCorrida que corresponden a la corrida de id pasado.
	 * 
	 * @param idCorrida
	 * @return int
	 */
	public int deleteAllByIdCorrida (Long idCorrida){

		String queryString = "delete from PasoCorrida t ";
			   queryString += " where t.corrida.id = "+idCorrida; 
		
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryString);
	    
		return query.executeUpdate();
	}
	
}
