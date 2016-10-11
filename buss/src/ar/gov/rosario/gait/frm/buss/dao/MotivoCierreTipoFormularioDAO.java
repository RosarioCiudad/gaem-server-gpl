package ar.gov.rosario.gait.frm.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.frm.buss.bean.MotivoCierreTipoFormulario;
import ar.gov.rosario.gait.frm.iface.model.MotivoCierreTipoFormularioSearchPage;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class MotivoCierreTipoFormularioDAO extends GenericDAO {

	private Logger log = Logger.getLogger(MotivoCierreTipoFormularioDAO.class);
	
	public MotivoCierreTipoFormularioDAO() {
		super(MotivoCierreTipoFormulario.class);
	}
	
	
	
	/**
	 * Obtiene un MotivoCierreTipoFormulario por su codigo
	 */
	public MotivoCierreTipoFormulario getByCodigo(String codigo) {
		MotivoCierreTipoFormulario motivoCierreTipoFormulario;
		String queryString = "from MotivoCierreTipoFormulario t where t.codigo = :codigo";
		Session session = GaitHibernateUtil.currentSession();

		Query query = session.createQuery(queryString).setString("codigo", codigo);
		motivoCierreTipoFormulario = (MotivoCierreTipoFormulario) query.uniqueResult();	

		return motivoCierreTipoFormulario; 
	}
	
	@SuppressWarnings("unchecked")
	public List<MotivoCierreTipoFormulario> getBySearchPage(MotivoCierreTipoFormularioSearchPage motivoCierreTipoFormularioSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM MotivoCierreTipoFormulario t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		//tratamiento
		query.addIfNotNull(" AND UPPER(TRIM(t.descripcion	)) LIKE ?", StringUtil.toUpperLike(motivoCierreTipoFormularioSearchPage.getMotivoCierreTipoFormulario().getDescripcion()));
		// tipoFormulario
		query.addIfNotNull(" AND t.tipoFormulario.id = ?", ModelUtil.bussId(motivoCierreTipoFormularioSearchPage.getMotivoCierreTipoFormulario().getTipoFormulario()));
		
		// Order by
		query.add(" ORDER BY t.id");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		
		List<MotivoCierreTipoFormulario> listTipoFormulario = (ArrayList<MotivoCierreTipoFormulario>) executeCountedSearch(query, motivoCierreTipoFormularioSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listTipoFormulario;
	}

}
