package ar.gov.rosario.gait.apm.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.Impresora;
import ar.gov.rosario.gait.apm.iface.model.ImpresoraSearchPage;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;


public class ImpresoraDAO extends GenericDAO {

	private Logger log = Logger.getLogger(ImpresoraDAO.class);

	public ImpresoraDAO() {
		super(Impresora.class);
	}

	@SuppressWarnings("unchecked")
	public List<Impresora> getBySearchPage(ImpresoraSearchPage impresoraSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		QueryMaker query = HibernateQueryMaker.make();

		query.add("FROM Impresora t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// descripcion
		query.addIfNotNull(" AND UPPER(TRIM(t.descripcion)) LIKE ?", StringUtil.toUpperLike(impresoraSearchPage.getImpresora().getDescripcion()));
		//numero de Serie
		query.addIfNotNull(" AND UPPER(TRIM(t.numeroSerie)) LIKE ?", StringUtil.toUpperLike(impresoraSearchPage.getImpresora().getNumeroSerie()));
		//numero de UUID
		query.addIfNotNull(" AND UPPER(TRIM(t.numeroUUID)) LIKE ?", StringUtil.toUpperLike(impresoraSearchPage.getImpresora().getNumeroUUID()));
		//area
		query.addIfNotNull(" AND t.area.id = ?", ModelUtil.bussId(impresoraSearchPage.getImpresora().getArea()));
		// Order by
		query.add(" ORDER BY t.id");

		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		List<Impresora> listImpresora = (ArrayList<Impresora>) executeCountedSearch(query, impresoraSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listImpresora;
	}


	/**
	 * 
	 * @param formulario
	 * @param codigo
	 * @return
	 */
	public List<Impresora> getDeltaByUsuario(String username, Integer version){
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		//TODO: V1
//		query.add("FROM Impresora t WHERE 1=1 ");
//		query.add(" AND t IN (SELECT u.impresora FROM UsuarioApmImpresora u WHERE u.usuarioApm.username = ?) ", username);
//		query.add(" AND t.version > ? ORDER BY t.version ", version);
		//TODO: V2
		query.add(" SELECT u.impresora FROM UsuarioApmImpresora u WHERE 1=1 ");
		query.add(" u.usuarioApm.username = ? ", username);
		query.add(" AND u.impresora.version > ? ORDER BY u.impresora.version ", version);

		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		List<Impresora> result = HibernateQueryMaker.query(query).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return result; 
	}

	/**
	 * 
	 * @param formulario
	 * @param codigo
	 * @return
	 */
	public List<Impresora> getListActivaByArea(Long areaId){
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		query.add("FROM Impresora t WHERE 1=1 ");
		// Formulario
		query.add("AND t.area.id = ? ", areaId);
		// exclude
		query.add("AND t NOT IN (SELECT u.impresora FROM UsuarioApmImpresora u WHERE u.estado = ?)", Estado.ACTIVO.getId());
		// Estado
		query.add("AND t.estado = ? ", Estado.ACTIVO.getId());

		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		List<Impresora> result = HibernateQueryMaker.query(query).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return result; 
	}
}