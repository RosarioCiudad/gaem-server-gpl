package ar.gov.rosario.gait.not.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.not.buss.bean.EstadoNotificacion;
import ar.gov.rosario.gait.not.iface.model.EstadoNotificacionSearchPage;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class EstadoNotificacionDAO extends GenericDAO {

	private Logger log = Logger.getLogger((EstadoNotificacionDAO.class));	

	public EstadoNotificacionDAO() {
		super(EstadoNotificacion.class);
	}

	@SuppressWarnings("unchecked")
	public List<EstadoNotificacion> getBySearchPage(EstadoNotificacionSearchPage estadoNotificacionSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM EstadoNotificacion t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// descripcion
		query.addIfNotNull(" AND UPPER(TRIM(t.descripcion)) LIKE ?", StringUtil.toUpperLike(estadoNotificacionSearchPage.getEstadoNotificacion().getDescripcion()));
		// Order by
		query.add(" ORDER BY t.descripcion");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);

		List<EstadoNotificacion> listEstadoNotificacion = executeCountedSearch(query, estadoNotificacionSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listEstadoNotificacion;
	}

	@SuppressWarnings("unchecked")
	public List<EstadoNotificacion> getListByApp(String codApp) {
		log.debug("getListByApp : enter");
		
		String queryStr = "SELECT p.estadoNotificacion FROM NotificacionEstadoNotificacion p WHERE p.notificacion.codigo = :codigo ";
		
    	log.debug(" query: " + queryStr);
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryStr).setString("codigo", codApp);
		
		return (ArrayList<EstadoNotificacion>)query.list();
	}

	public Integer updateVersionable(String sName) {
		String queryStr = "UPDATE EstadoNotificacion SET descripcion=(descripcion+1) WHERE descripcion = :descripcion";
		
    	log.debug(" query: " + queryStr);
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryStr).setString("descripcion", sName);
		query.executeUpdate();
		
		queryStr = "SELECT t.descripcion FROM EstadoNotificacion t WHERE t.descripcion = :descripcion";
		query = session.createQuery(queryStr).setString("descripcion", sName);
		
		return (Integer) query.uniqueResult();
	}
	
}
