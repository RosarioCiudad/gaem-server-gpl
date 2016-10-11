package ar.gov.rosario.gait.apm.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.Campo;
import ar.gov.rosario.gait.apm.iface.model.CampoSearchPage;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;


public class CampoDAO extends GenericDAO {

	private Logger log = Logger.getLogger(CampoDAO.class);

	public CampoDAO() {
		super(Campo.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Campo> getBySearchPage(CampoSearchPage campoSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM Campo t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// etiqueta
		query.addIfNotNull(" AND UPPER(TRIM(t.etiqueta)) LIKE ?", StringUtil.toUpperLike(campoSearchPage.getCampo().getEtiqueta()));
		//tratamiento
		query.addIfNotNull(" AND t.tratamiento = ?", campoSearchPage.getCampo().getTratamiento().getBussId());
		//obligatorio
		query.addIfNotNull(" AND t.obligatorio = ?", campoSearchPage.getCampo().getObligatorio().getBussId());
		//sololectura
		query.addIfNotNull(" AND t.soloLectura = ?", campoSearchPage.getCampo().getSoloLectura().getBussId());
		
		// Order by
		query.add(" ORDER BY t.id");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);

		
		List<Campo> listCampo = (ArrayList<Campo>) executeCountedSearch(query, campoSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listCampo;
	}
	
	/**
	 * 
	 * @param idAplicacionPerfil
	 * @return
	 * @throws Exception
	 */
	public List<Campo> getListExcluded(Long idAplicacionPerfilSeccion) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		
		query.add("FROM Campo t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ? ", Estado.ACTIVO.getId());
		// exclude
		query.add(" AND t NOT IN (SELECT p.campo " +
				" FROM AplPerfilSeccionCampo p WHERE p.aplicacionPerfilSeccion.id = ? AND p.estado = ?) ", 
				idAplicacionPerfilSeccion, Estado.ACTIVO.getId());
		// Order by
		query.add(" ORDER BY t.etiqueta");
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		
		List<Campo> result = HibernateQueryMaker.query(query).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		
		return result;
	}
	
}