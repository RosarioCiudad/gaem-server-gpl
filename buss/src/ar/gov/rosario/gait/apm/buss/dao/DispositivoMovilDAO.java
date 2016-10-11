package ar.gov.rosario.gait.apm.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilSearchPage;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;


public class DispositivoMovilDAO extends GenericDAO {

	private Logger log = Logger.getLogger(DispositivoMovilDAO.class);

	public DispositivoMovilDAO() {
		super(DispositivoMovil.class);
	}
	
	/**
	 * Obtiene un DispositivoMovil por su emailAddress
	 */
	public DispositivoMovil getByEmailAddress(String deviceID) {
		if (log.isDebugEnabled()) log.debug("getByEmailAddress: enter");
		
		DispositivoMovil dispositivoMovil;
		String queryString = "from DispositivoMovil t where t.emailAddress = :deviceID and t.estado = 1";
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setString("deviceID", deviceID);
		dispositivoMovil = (DispositivoMovil) query.uniqueResult();	

		if (log.isDebugEnabled()) log.debug("getByEmailAddress: exit");
		return dispositivoMovil; 
	}

	/**
	 * Obtiene un DispositivoMovil por su numero de IMEI
	 */
	public DispositivoMovil getByNumeroIMEI(String deviceID) {
		if (log.isDebugEnabled()) log.debug("getByNumeroIMEI: enter");
		
		DispositivoMovil dispositivoMovil;
		String queryString = "from DispositivoMovil t where t.numeroIMEI = :deviceID and t.estado = 1";
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setString("deviceID", deviceID);
		dispositivoMovil = (DispositivoMovil) query.uniqueResult();	

		if (log.isDebugEnabled()) log.debug("getByNumeroIMEI: exit");
		return dispositivoMovil; 
	}
	
	
	@SuppressWarnings("unchecked")
	public List<DispositivoMovil> getBySearchPage(DispositivoMovilSearchPage dispositivoMovilSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM DispositivoMovil t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// descripcion
		query.addIfNotNull(" AND UPPER(TRIM(t.descripcion)) LIKE ?", StringUtil.toUpperLike(dispositivoMovilSearchPage.getDispositivoMovil().getDescripcion()));
		//numero de Serie
		query.addIfNotNull(" AND UPPER(TRIM(t.numeroSerie)) LIKE ?", StringUtil.toUpperLike(dispositivoMovilSearchPage.getDispositivoMovil().getNumeroSerie()));
		//numero de Linea
		query.addIfNotNull(" AND UPPER(TRIM(t.numeroLinea)) LIKE ?", StringUtil.toUpperLike(dispositivoMovilSearchPage.getDispositivoMovil().getNumeroLinea()));
		//forzar actualizacion
		query.addIfNotNull(" AND t.forzarActualizacion= ?", dispositivoMovilSearchPage.getDispositivoMovil().getForzarActualizacion().getBussId());
		//emailaddress
		query.addIfNotNull(" AND UPPER(TRIM(t.emailAddress)) LIKE ?", StringUtil.toUpperLike(dispositivoMovilSearchPage.getDispositivoMovil().getEmailAddress()));
		//numeroIMEI
		query.addIfNotNull(" AND UPPER(TRIM(t.numeroIMEI)) LIKE ?", StringUtil.toUpperLike(dispositivoMovilSearchPage.getDispositivoMovil().getNumeroIMEI()));
		//area
		query.addIfNotNull(" AND t.area.id = ?", ModelUtil.bussId(dispositivoMovilSearchPage.getDispositivoMovil().getArea()));
		
		// Order by
		query.add(" ORDER BY t.descripcion");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);

		
		List<DispositivoMovil> listDispositivoMovil = (ArrayList<DispositivoMovil>) executeCountedSearch(query, dispositivoMovilSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listDispositivoMovil;
	}
	
	/**
	 * 
	 * @param formulario
	 * @param codigo
	 * @return
	 */
	public List<DispositivoMovil> getListActivaByArea(Long areaId){
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		query.add("FROM DispositivoMovil t WHERE 1=1 ");
		// Formulario
		query.add("AND t.area.id = ? ", areaId);
		// exclude
		query.add("AND t NOT IN (SELECT u.dispositivoMovil FROM UsuarioApmDM u WHERE u.estado = ?)", Estado.ACTIVO.getId());
		// Estado
		query.add("AND t.estado = ? ", Estado.ACTIVO.getId());
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		List<DispositivoMovil> result = HibernateQueryMaker.query(query).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return result; 
	}
}