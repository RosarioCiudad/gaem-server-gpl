package ar.gov.rosario.gait.apm.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.apm.buss.bean.AplicacionTabla;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTablaSearchPage;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.model.Estado;

public class AplicacionTablaDAO extends GenericDAO {

	private Logger log = Logger.getLogger(AplicacionTablaDAO.class);

	public AplicacionTablaDAO() {
		super(AplicacionTabla.class);
	}

	@SuppressWarnings("unchecked")
	public List<AplicacionTabla> getBySearchPage(AplicacionTablaSearchPage aplicacionTablaSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM AplicacionTabla t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		//aplicacion
		query.addIfNotNull(" AND t.aplicacion.id = ?", ModelUtil.bussId(aplicacionTablaSearchPage.getAplicacionTabla().getAplicacion()));
		//tablaVersion
		query.addIfNotNull(" AND t.tablaVersion.id = ?", ModelUtil.bussId(aplicacionTablaSearchPage.getAplicacionTabla().getTablaVersion()));
		// Order by
		query.add(" ORDER BY t.aplicacion");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);

		
		List<AplicacionTabla> listAplicacionTabla = executeCountedSearch(query, aplicacionTablaSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listAplicacionTabla;
	}

	@SuppressWarnings("unchecked")
	public List<AplicacionTabla> getListByApp(String codApp) {
		log.debug("getListByApp : enter");
		
		String queryStr = "SELECT p.aplicacionTabla FROM AplicacionTabla p WHERE p.aplicacion.codigo = :codigo ";
		
    	log.debug(" query: " + queryStr);
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryStr).setString("codigo", codApp);
		
		return (ArrayList<AplicacionTabla>)query.list();
	}

	public Integer updateVersionable(String sName) {
		String queryStr = "UPDATE AplicacionTabla SET tablaVersion=(tablaVersion+1) WHERE aplicacion = :aplicacion";
		
    	log.debug(" query: " + queryStr);
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryStr).setString("aplicacion", sName);
		query.executeUpdate();
		
		queryStr = "SELECT t.tablaVersion FROM AplicacionTabla t WHERE t.aplicacion = :aplicacion";
		query = session.createQuery(queryStr).setString("aplicacion", sName);
		
		return (Integer) query.uniqueResult();
	}

}