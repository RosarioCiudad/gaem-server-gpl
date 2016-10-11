package ar.gov.rosario.gait.frm.buss.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.DateType;
import org.hibernate.type.StandardBasicTypes;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.frm.buss.bean.Formulario;
import ar.gov.rosario.gait.frm.iface.model.FormularioSearchPage;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class FormularioDAO extends GenericDAO {

	private Logger log = Logger.getLogger(FormularioDAO.class);

	public FormularioDAO() {
		super(Formulario.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Formulario> getBySearchPage(FormularioSearchPage formularioSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM Formulario t WHERE 1=1 ");
		// estado
		query.addIfNotNull(" AND t.estado = ?", formularioSearchPage.getFormulario().getEstado().getBussId());
		//numero	
		query.addIfNotNull(" AND UPPER(TRIM(t.numero)) LIKE ?",
				StringUtil.toUpperLike(formularioSearchPage.getFormulario().getNumero()));
		//fecha cierre desde
		query.addIf(!StringUtil.isNullOrEmpty(formularioSearchPage.getFechaCierreDesdeView()),
				" AND t.fechaCierre >=" +sqlDate(formularioSearchPage.getFechaCierreDesde()));
		//fecha cierre hasta
		//por ser un timestamp, se agrega un día a la fechaCierre y se saca el = de la comparación
		if (!StringUtil.isNullOrEmpty(formularioSearchPage.getFechaCierreHastaView()))
			query.add(" AND t.fechaCierre < " +sqlDate(DateUtil.addDaysToDate(formularioSearchPage.getFechaCierreHasta(), 1)));

		// usuario
		query.addIfNotNull(" AND UPPER(TRIM(t.usuarioCierre.username)) LIKE ?",
				StringUtil.toUpperLike(formularioSearchPage.getFormulario().getUsuarioCierre().getUsername()));
		// area
		query.addIfNotNull(" AND t.area.id = ?", 
				ModelUtil.bussId(formularioSearchPage.getFormulario().getArea()));

		// TipoFormluario
		query.addIfNotNull(" AND t.tipoFormulario.id = ?",
				ModelUtil.bussId(formularioSearchPage.getFormulario().getTipoFormulario()));
		
		// AplicacionPerfil
		query.addIfNotNull(" AND t.tipoFormularioDef.id = ?",
				ModelUtil.bussId(formularioSearchPage.getFormulario().getTipoFormularioDef()));
		// Order by
		query.add(" ORDER BY t.fechaCierre DESC");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		
		List<Formulario> list = executeCountedSearch(query, formularioSearchPage);
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return list;
	}
	
	
	public List<Long> getListIdActivos(){
		if (log.isDebugEnabled()) log.debug("getListIdActivos: enter");
		
		List<Long> result;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT id FROM for_formulario ");
		queryBuilder.append(" WHERE 1=1 ");
		queryBuilder.append(" AND estado = :estado ");
		
		Session session = GaitHibernateUtil.currentSession();
		
		Query query = session.createSQLQuery(queryBuilder.toString())
				.addScalar("id", StandardBasicTypes.LONG);
		query.setInteger("estado", Estado.ACTIVO.getId());
		
		result = query.list();
		if (log.isDebugEnabled()) log.debug("getListIdActivos: exit");
		return result; 
	}
	
	/**
	 * Devuelve los id de las actas con estado PROCESADO_ERROR
	 * @return Lista de id
	 */
	public List<Long> getListIdProcesadoError(){
		if (log.isDebugEnabled()) log.debug("getListIdProcesadoError: enter");
		
		List<Long> result;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT id FROM for_formulario ");
		queryBuilder.append(" WHERE 1=1 ");
		queryBuilder.append(" AND estado = :estado ");
		
		Session session = GaitHibernateUtil.currentSession();
		
		Query query = session.createSQLQuery(queryBuilder.toString())
				.addScalar("id", StandardBasicTypes.LONG);
		query.setInteger("estado", Estado.PROCESADO_ERROR.getId());
		
		result = query.list();
		log.debug("getListIdProcesadoError query: "+query.toString());
		if (log.isDebugEnabled()) log.debug("getListIdProcesadoError: exit");
		return result; 
	}
	
	/**
	 * Devuelve los id de las actas con estado PROCESADO
	 * @return Lista de id
	 */
	public List<Long> getListIdProcesado(){
		if (log.isDebugEnabled()) log.debug("getListIdProcesado: enter");
		
		List<Long> result;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT id FROM for_formulario ");
		queryBuilder.append(" WHERE 1=1 ");
		queryBuilder.append(" AND estado = :estado ");
		
		Session session = GaitHibernateUtil.currentSession();
		
		Query query = session.createSQLQuery(queryBuilder.toString())
				.addScalar("id", StandardBasicTypes.LONG);
		query.setInteger("estado", Estado.PROCESADO.getId());
		
		result = query.list();
		log.debug("getListIdProcesadoError query: "+query.toString());
		if (log.isDebugEnabled()) log.debug("getListIdProcesado: exit");
		return result; 
	}
	
	/**
	 * Devuelve los id de las actas con estado ACTIVO que correspondan al tipoFormulario con codigo cod
	 * @return Lista de id
	 */
	public List<Long> getListIdProcesadoByCodigo(String codigo){
		if (log.isDebugEnabled()) log.debug("getListIdProcesadoByCodigo: enter");
		
		List<Long> result;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT for_formulario.id FROM for_formulario, for_tipoformulario ");
		queryBuilder.append(" WHERE 1=1 ");
		queryBuilder.append(" AND idtipoformulario =  for_tipoformulario.id");
		queryBuilder.append(" AND codigo = :codigo ");
		//queryBuilder.append(" AND idestadotipoformulario = 3 "); //el estadoTipoFormulario es "cerrada definitiva"
		queryBuilder.append(" AND for_formulario.estado = :estado ");
		
		Session session = GaitHibernateUtil.currentSession();
		
		Query query = session.createSQLQuery(queryBuilder.toString())
				.addScalar("id", StandardBasicTypes.LONG);
		//query.setInteger("estado", Estado.PROCESADO.getId()); 
		query.setInteger("estado", Estado.ACTIVO.getId());
		query.setString("codigo", codigo);
		
		result = query.list();
		log.debug("getListIdProcesadoError query: "+query.toString());
		if (log.isDebugEnabled()) log.debug("getListIdProcesado: exit");
		return result; 
	}
	

	/**
	 * Devuelve el ultimo formulario procesado OK
	 * @return Lista de id
	 */
	public Formulario getUltimoProcesadoOk(){
		if (log.isDebugEnabled()) log.debug("getUltimoProcesadoOk: enter");
		
		Formulario result;
		QueryMaker queryMaker = HibernateQueryMaker.make();
		queryMaker.add("FROM Formulario t");
		queryMaker.add(" WHERE 1=1 ");
		queryMaker.add(" AND estado = :estado ");
		queryMaker.add(" ORDER BY fechaCierre desc ");
		
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryMaker.toString());
		query.setInteger("estado", Estado.PROCESADO.getId());
		query.setMaxResults(1);
		result = (Formulario)query.uniqueResult();
		log.debug("getListIdProcesadoError query: "+query.toString());
		if (log.isDebugEnabled()) log.debug("getUltimoProcesadoOk: exit");
		return result; 
	}
	
	/**
	 * Devuelve el ultimo formulario procesado Error
	 * @return Formulario
	 */
	public Formulario getUltimoProcesadoError(){
		if (log.isDebugEnabled()) log.debug("getUltimoProcesadoOk: enter");
		
		Formulario result;
		QueryMaker queryMaker = HibernateQueryMaker.make();
		queryMaker.add("FROM Formulario t");
		queryMaker.add(" WHERE 1=1 ");
		queryMaker.add(" AND estado = :estado ");
		queryMaker.add(" ORDER BY fechaCierre desc ");
		
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryMaker.toString());
		query.setInteger("estado", Estado.PROCESADO_ERROR.getId());
		query.setMaxResults(1);
		result = (Formulario)query.uniqueResult();
		log.debug("getListIdProcesadoError query: "+query.toString());
		if (log.isDebugEnabled()) log.debug("getUltimoProcesadoOk: exit");
		return result; 
	}
	
	
	public List<Object[]> getListErrorCantidad() {
		if (log.isDebugEnabled()) log.debug("getListErrorCantidad: enter");
		List<Long> result;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT observacion, COUNT(id) FROM for_formulario ");
		queryBuilder.append(" WHERE 1=1 ");
		queryBuilder.append(" AND estado = :estado ");
		queryBuilder.append(" GROUP BY observacion ");
		
		Session session = GaitHibernateUtil.currentSession();
		
		Query query = session.createSQLQuery(queryBuilder.toString());
		query.setInteger("estado", Estado.PROCESADO_ERROR.getId());
		
		List<Object[]> listResult = (ArrayList<Object[]>) query.list();
		
		log.debug("getListErrorCantidad query: "+query.toString());
		if (log.isDebugEnabled()) log.debug("getListErrorCantidad: exit");
		return listResult; 
		
	}
	
	
	/**
	 * 
	 * @param fechaImpresionDesde
	 * @param fechaImpresionHasta
	 * @return
	 */
	public List<Formulario> getListImpreso(Date fechaImpresionDesde, Date fechaImpresionHasta){
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		query.add("FROM Formulario t WHERE 1=1 ");
		// fecha impresion desde
		query.addIf(fechaImpresionDesde != null," AND t.fechaImpresion >=" +sqlDate(fechaImpresionDesde));
		// fecha impresion hasta
		query.addIf(fechaImpresionHasta != null," AND t.fechaImpresion <=" +sqlDate(fechaImpresionHasta));
		
		query.add(" AND t.fechaImpresion IS NOT NULL ");
		// Order by
		query.add(" ORDER BY t.numeroInspector, t.fechaCierre");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		
		Session session = GaitHibernateUtil.currentSession();
		List<Formulario> result = session.createQuery(query.toString()).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return result; 
	}
	
	
	/**
	 * 
	 * @param fechaCierreDesde
	 * @param fechaCierreHasta
	 * @return
	 */
	public List<Formulario> getListNoImpreso(Date fechaCierreDesde, Date fechaCierreHasta){
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		query.add("FROM Formulario t WHERE 1=1 ");
		// fecha impresion desde
		query.addIf(fechaCierreDesde != null," AND t.fechaCierre >=" +sqlDate(fechaCierreDesde));
		// fecha impresion hasta
		query.addIf(fechaCierreHasta != null," AND t.fechaCierre <=" +sqlDate(fechaCierreHasta));
		
		query.add(" AND t.fechaImpresion IS NULL ");
		// Order by
		query.add(" ORDER BY t.numeroInspector, t.fechaCierre");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		
		Session session = GaitHibernateUtil.currentSession();
		List<Formulario> result = session.createQuery(query.toString()).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return result; 
	}
	
	
	/**
	 * 
	 */
	public Date getFechaCierreByNumero(String numero) {
		if (log.isDebugEnabled()) log.debug("getByNumero: enter");
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("SELECT fechaCierre FROM for_formulario ");
		queryBuilder.append(" WHERE numero = :numero ");
		
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createSQLQuery(queryBuilder.toString())
							 .addScalar("fechaCierre", DateType.INSTANCE);
		query.setString("numero", numero);
		query.setMaxResults(1);
		
		Date result = (Date) query.uniqueResult();

		if (log.isDebugEnabled()) log.debug("getByNumero: exit");
		return result; 
	}
	
}
