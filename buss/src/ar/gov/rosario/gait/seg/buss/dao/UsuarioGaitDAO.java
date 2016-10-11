package ar.gov.rosario.gait.seg.buss.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.seg.buss.bean.UsuarioGait;
import ar.gov.rosario.gait.seg.iface.model.UsuarioGaitSearchPage;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ListUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class UsuarioGaitDAO extends GenericDAO {

	private Logger log = Logger.getLogger(UsuarioGaitDAO.class);	
	
	
	public UsuarioGaitDAO() {
		super(UsuarioGait.class);
	}
	
	public List<UsuarioGait> getBySearchPage(UsuarioGaitSearchPage usuarioGaitSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM UsuarioGait t WHERE 1=1 ");
 		String strIdExcluidos = ListUtil.getStringIdsFromListModel(usuarioGaitSearchPage.getListVOExcluidos());
 		query.addIf(!StringUtil.isNullOrEmpty(strIdExcluidos), "and t.id NOT IN ("+ strIdExcluidos + ")");
		// estado
		query.addIf(usuarioGaitSearchPage.getModoSeleccionar(), " AND t.estado = ?", Estado.ACTIVO.getId());
		// usuarioGAIT
		query.addIfNotNull(" AND UPPER(TRIM(t.usuarioGAIT)) LIKE ?", StringUtil.toUpperLike(usuarioGaitSearchPage.getUsuarioGait().getUsuarioGAIT()));
		// direccion
		query.addIfNotNull(" AND t.direccion.id = ?", ModelUtil.bussId(usuarioGaitSearchPage.getUsuarioGait().getDireccion()));
		// area
		query.addIfNotNull(" AND t.area.id = ?", ModelUtil.bussId(usuarioGaitSearchPage.getUsuarioGait().getArea()));

		// Order by
		query.add(" ORDER BY t.direccion.id, t.area.id, t.usuarioGAIT");
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		
		List<UsuarioGait> listUsuarioGait = executeCountedSearch(query, usuarioGaitSearchPage);
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");

		return listUsuarioGait;

	}
	
	/**
	 * Obtiene un UsuarioGait por su nombre
	 */
	public UsuarioGait getByUserName(String userName) throws Exception {
		UsuarioGait usuarioGait;
		String queryString = "from UsuarioGait t where t.usuarioGAIT = :userName";
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setString("userName", userName);
		usuarioGait = (UsuarioGait) query.uniqueResult();	

		return usuarioGait; 
	}
}