package ar.gov.rosario.gait.pro.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.pro.buss.bean.Corrida;
import ar.gov.rosario.gait.pro.buss.bean.FileCorrida;

public class FileCorridaDAO extends GenericDAO {

	private Logger log = Logger.getLogger((FileCorridaDAO.class));	
	
	public FileCorridaDAO() {
		super(FileCorrida.class);
	}
	
	/**
	 * Obtiene una lista de archivos de Salida para una Corrida y un Paso determinado.
	 * 
	 */
	public List<FileCorrida> getListByCorridaYPaso(Corrida corrida, Integer paso) throws Exception {
		List<FileCorrida> listFileCorrida;
		String queryString = "from FileCorrida t where t.corrida.id = :idCorrida and t.paso = :paso"+
		 " ORDER BY t.orden, t.id";
		log.debug("getListByCorridaYPaso - paso:"+paso +"      query:"+queryString);
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setLong("idCorrida", corrida.getId());
		query.setInteger("paso", paso);
		listFileCorrida = (ArrayList<FileCorrida>) query.list();	

		return listFileCorrida; 
	}

	/**
	 * Obtiene una lista de archivos de Salida para una Corrida determinada.
	 * 
	 */
	public List<FileCorrida> getListByCorrida(Corrida corrida) throws Exception {
		List<FileCorrida> listFileCorrida;
		String queryString = "from FileCorrida t where t.corrida.id = :idCorrida ORDER BY t.orden, t.id";
		
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setLong("idCorrida", corrida.getId());
		
		listFileCorrida = (ArrayList<FileCorrida>) query.list();	

		return listFileCorrida; 
	}

	
	public int deleteAllByCorridaNombre (Corrida corrida, String nombre){

		String queryString = "DELETE FROM FileCorrida fc " +
			   " WHERE fc.corrida = :corrida AND fc.nombre = :nombre"; 
		
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryString)
			.setEntity("corrida", corrida)
			.setString("nombre", nombre);
	    
		return query.executeUpdate();
	}

	public List<FileCorrida> getListByCorridaNombre (Corrida corrida, String nombre){

		String queryString = "FROM FileCorrida fc " +
			   " WHERE fc.corrida = :corrida AND fc.nombre = :nombre"; 
		
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryString)
			.setEntity("corrida", corrida)
			.setString("nombre", nombre);
	    
		return (ArrayList<FileCorrida>) query.list();
	}
		
	public int deleteByCorridaYPaso(Corrida corrida, Integer paso) throws Exception {

		String queryString = "DELETE FROM FileCorrida fc WHERE fc.corrida = :corrida AND fc.paso = :paso";
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString)
			.setEntity("corrida", corrida)
			.setInteger("paso", paso);

		return query.executeUpdate();	
	}

	/**
	 * Elimina los registros de FileCorrida que corresponden a la corrida de id pasado
	 * 
	 * @param idCorrida
	 * @return int
	 */
	public int deleteAllByIdCorrida (Long idCorrida){

		String queryString = "delete from FileCorrida t ";
			   queryString += " where t.corrida.id = "+idCorrida; 
		
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryString);
	    
		return query.executeUpdate();
	}
}
