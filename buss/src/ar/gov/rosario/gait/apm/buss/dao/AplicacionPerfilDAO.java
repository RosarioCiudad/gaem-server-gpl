package ar.gov.rosario.gait.apm.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.AplicacionPerfil;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilSearchPage;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class AplicacionPerfilDAO extends GenericDAO {

	private Logger log = Logger.getLogger(AplicacionPerfilDAO.class);

	public AplicacionPerfilDAO() {
		super(AplicacionPerfil.class);
	}
	
	
    @SuppressWarnings("unchecked")
	public List<AplicacionPerfil> getBySearchPage(AplicacionPerfilSearchPage aplicacionPerfilSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM AplicacionPerfil t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		//aplicacion
		query.addIfNotNull(" AND t.aplicacion.id = ?", ModelUtil.bussId(aplicacionPerfilSearchPage.getAplicacionPerfil().getAplicacion()));

		query.addIfNotNull(" AND UPPER(TRIM(t.descripcion)) LIKE ?", StringUtil.toUpperLike(aplicacionPerfilSearchPage.getAplicacionPerfil().getDescripcion()));			
		// Order by
		query.add(" ORDER BY t.id");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);

		List<AplicacionPerfil> listAplicacionPerfil = (ArrayList<AplicacionPerfil>) executeCountedSearch(query, aplicacionPerfilSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listAplicacionPerfil;
	}
    
}