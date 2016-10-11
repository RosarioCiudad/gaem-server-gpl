package ar.gov.rosario.gait.apm.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.apm.buss.bean.AplicacionTipoBinario;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTipoBinarioSearchPage;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class AplicacionTipoBinarioDAO extends GenericDAO {

	private Logger log = Logger.getLogger(AplicacionTipoBinarioDAO.class);

	public AplicacionTipoBinarioDAO() {
		super(AplicacionTipoBinario.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<AplicacionTipoBinario> getBySearchPage(AplicacionTipoBinarioSearchPage aplicacionTipoBinarioSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM AplicacionTipoBinario t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// descripcion
		query.addIfNotNull(" AND UPPER(TRIM(t.descripcion)) LIKE ?", StringUtil.toUpperLike(aplicacionTipoBinarioSearchPage.getAplicacionTipoBinario().getDescripcion()));
		// Order by
		query.add(" ORDER BY t.descripcion");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);

		
		List<AplicacionTipoBinario> listAplicacionTipoBinario = executeCountedSearch(query, aplicacionTipoBinarioSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listAplicacionTipoBinario;
	}

	@SuppressWarnings("unchecked")
	public List<AplicacionTipoBinario> getListByApp(String codApp) {
		log.debug("getListByApp : enter");
		
		String queryStr = "SELECT p.aplicacionTipoBinario FROM AplicacionTipoBinario p WHERE p.aplicacion.codigo = :codigo ";
		
    	log.debug(" query: " + queryStr);
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryStr).setString("codigo", codApp);
		
		return (ArrayList<AplicacionTipoBinario>)query.list();
	}

	public Integer updateVersionable(String sName) {
		String queryStr = "UPDATE AplicacionTipoBinario SET descripcion=(descripcion+1) WHERE descripcion = :descripcion";
		
    	log.debug(" query: " + queryStr);
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryStr).setString("descripcion", sName);
		query.executeUpdate();
		
		queryStr = "SELECT t.descripcion FROM AplicacionTipoBinario t WHERE t.descripcion = :descripcion";
		query = session.createQuery(queryStr).setString("descripcion", sName);
		
		return (Integer) query.uniqueResult();
	}


}