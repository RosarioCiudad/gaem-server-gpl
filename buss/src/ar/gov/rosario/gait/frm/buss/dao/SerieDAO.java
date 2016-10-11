package ar.gov.rosario.gait.frm.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.frm.buss.bean.Serie;
import ar.gov.rosario.gait.frm.iface.model.SerieSearchPage;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class SerieDAO extends GenericDAO{
	
	private Logger log = Logger.getLogger(TipoFormularioDAO.class);
	
	public SerieDAO() {
		super(Serie.class);
	}
	
	public Serie getByCodigo(String codigo) {
		Serie serie;
		String queryString = "from Serie t where t.codigo = :codigo";
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setString("codigo", codigo);
		serie = (Serie) query.uniqueResult();	

		return serie; 
	}
	
	@SuppressWarnings("unchecked")
	public List<Serie> getBySearchPage(SerieSearchPage serieSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM Serie t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// etiqueta
		query.addIfNotNull(" AND UPPER(TRIM(t.codigo	)) LIKE ?", StringUtil.toUpperLike(serieSearchPage.getSerie().getCodigo()));
		// Order by
		query.add(" ORDER BY t.id");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		
		List<Serie> listSerie = (ArrayList<Serie>) executeCountedSearch(query, serieSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listSerie;
	}

}
