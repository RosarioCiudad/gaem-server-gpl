package ar.gov.rosario.gait.frm.buss.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.frm.buss.bean.Formulario;
import ar.gov.rosario.gait.frm.buss.bean.FormularioDetalle;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.Estado;

public class FormularioDetalleDAO extends GenericDAO {

	private Logger log = Logger.getLogger(FormularioDetalleDAO.class);

	public FormularioDetalleDAO() {
		super(FormularioDetalle.class);
	}

	/**
	 * 
	 * @param formulario
	 * @param codigo
	 * @return
	 */
	public List<FormularioDetalle> getListByFormularioAndCodigo(Formulario formulario, String codigo){
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		query.add("FROM FormularioDetalle t WHERE 1=1 ");
		// Formulario
		query.add("AND t.formulario.id = ? ", formulario.getId());
		// Codigo
		query.add("AND t.tipoFormularioDefSeccionCampo.campo.codigo LIKE ? ", codigo);
		// Estado
		query.add("AND t.estado = ? ", Estado.ACTIVO.getId());
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		List<FormularioDetalle> result = HibernateQueryMaker.query(query).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return result; 
	}
	
	
	/**
	 * 
	 * @param formulario
	 * @param codigo
	 * @return
	 */
	public List<FormularioDetalle> getByFormularioAndCodigo(Formulario formulario, String codigo){
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		query.add("FROM FormularioDetalle t WHERE 1=1 ");
		// Formulario
		query.add("AND t.formulario.id = ? ", formulario.getId());
		// Codigo
		query.add("AND t.tipoFormularioDefSeccionCampo.campo.codigo LIKE ? ", codigo);
		// Estado
		query.add("AND t.estado = ? ", Estado.ACTIVO.getId());
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		Session session = GaitHibernateUtil.currentSession();
		List<FormularioDetalle> result =  session.createQuery(query.toString()).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return result; 
	}
}