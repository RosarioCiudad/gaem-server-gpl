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