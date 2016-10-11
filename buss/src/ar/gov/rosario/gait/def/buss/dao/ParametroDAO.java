package ar.gov.rosario.gait.def.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.def.buss.bean.Parametro;
import ar.gov.rosario.gait.def.iface.model.ParametroSearchPage;
import ar.gov.rosario.gait.def.iface.model.ParametroVO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ListUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class ParametroDAO extends GenericDAO {

	private Logger log = Logger.getLogger((ParametroDAO.class));	
	
	public ParametroDAO() {
		super(Parametro.class);
	}
	
	public List<Parametro> getBySearchPage2(ParametroSearchPage parametroSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		String queryString = "from Parametro t ";
	    boolean flagAnd = false;

		if (log.isDebugEnabled()) { 
			log.debug("log de filtros del ParametroSearchPage: " + parametroSearchPage.infoString()); 
		}
	
		// Armamos filtros del HQL
		if (parametroSearchPage.getModoSeleccionar()) {
		  queryString += flagAnd ? " and " : " where ";
	      queryString += " t.estado = "+ Estado.ACTIVO.getId();
	      flagAnd = true;
		}
		
		// Filtros aqui
		
		
		// filtro parametro excluidos
 		List<ParametroVO> listParametroExcluidos = (ArrayList<ParametroVO>) parametroSearchPage.getListVOExcluidos();
 		if (!ListUtil.isNullOrEmpty(listParametroExcluidos)) {
 			queryString += flagAnd ? " and " : " where ";

 			String listIdExcluidos = ListUtil.getStringIdsFromListModel(listParametroExcluidos);
			queryString += " t.id NOT IN ("+ listIdExcluidos + ") "; 
			flagAnd = true;
		}
		
		// filtro por codParam
 		if (!StringUtil.isNullOrEmpty(parametroSearchPage.getParametro().getCodParam())) {
            queryString += flagAnd ? " and " : " where ";
			queryString += " UPPER(TRIM(t.codParam)) like '%" + 
				StringUtil.escaparUpper(parametroSearchPage.getParametro().getCodParam()) + "%'";
			flagAnd = true;
		}

		// filtro por desParam
 		if (!StringUtil.isNullOrEmpty(parametroSearchPage.getParametro().getDesParam())) {
            queryString += flagAnd ? " and " : " where ";
			queryString += " UPPER(TRIM(t.desParam)) like '%" + 
				StringUtil.escaparUpper(parametroSearchPage.getParametro().getDesParam()) + "%'";
			flagAnd = true;
		}
 		
 		// Order By
		queryString += " order by t.codParam ";
		
		
	    if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + queryString);

		List<Parametro> listParametro = (ArrayList<Parametro>) executeCountedSearch(queryString, parametroSearchPage);
		
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listParametro;
	}

	public List<Parametro> getBySearchPage(ParametroSearchPage parametroSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("from Parametro t where 1=1");
		// estado
		query.addIf(parametroSearchPage.getModoSeleccionar(), " and t.estado = ?", Estado.ACTIVO.getId()); 		

		// excluidos
 		String strIdExcluidos = ListUtil.getStringIdsFromListModel(parametroSearchPage.getListVOExcluidos());
 		query.addIf(!StringUtil.isNullOrEmpty(strIdExcluidos), "and t.id NOT IN ("+ strIdExcluidos + ")");
 		// codigo
 		query.addIfNotNull("and UPPER(TRIM(t.codParam)) like ?", StringUtil.toUpperLike(parametroSearchPage.getParametro().getCodParam()));
 		// descripcion
 		query.addIfNotNull("and UPPER(TRIM(t.desParam)) like ?", StringUtil.toUpperLike(parametroSearchPage.getParametro().getDesParam()));
 		// orden
 		query.add(" order by t.codParam");

		List<Parametro> listParametro = (ArrayList<Parametro>) executeCountedSearch(query, parametroSearchPage);
		
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listParametro;
	}

	/**
	 * Obtiene un Parametro por su codigo
	 */
	public Parametro getByCodigo(String codigo) throws Exception {
		Parametro parametro;
		String queryString = "from Parametro t where t.codParam = :codigo";
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setString("codigo", codigo);
		parametro = (Parametro) query.uniqueResult();	

		return parametro; 
	}
	
}
