package ar.gov.rosario.gait.pro.buss.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.pro.buss.bean.Corrida;
import ar.gov.rosario.gait.pro.buss.bean.EstadoCorrida;
import ar.gov.rosario.gait.pro.iface.model.CorridaSearchPage;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;


public class CorridaDAO extends GenericDAO {

	private Logger log = Logger.getLogger((CorridaDAO.class));	
	
	public CorridaDAO() {
		super(Corrida.class);
	}
	
	public List<Corrida> getBySearchPage(CorridaSearchPage corridaSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		String queryString = "from Corrida t ";
	    boolean flagAnd = false;

		if (log.isDebugEnabled()) { 
			log.debug("log de filtros del CorridaSearchPage: " + corridaSearchPage.infoString()); 
		}
	
		// Armamos filtros del HQL
		if (corridaSearchPage.getModoSeleccionar()) {
		  queryString += flagAnd ? " and " : " where ";
	      queryString += " t.estado = "+ Estado.ACTIVO.getId();
	      flagAnd = true;
		}
		
		// Filtros aqui
		
		// filtro por estado de la corrida
		if(!StringUtil.isNullOrEmpty(corridaSearchPage.getCorrida().getUsuario())) {
			queryString += flagAnd ? " and " : " where ";
			queryString += " t.usuarioUltMdf = '"+corridaSearchPage.getCorrida().getUsuario() + "'";
			flagAnd = true;
		}

		// filtro por estado de la corrida
		if(!ModelUtil.isNullOrEmpty(corridaSearchPage.getCorrida().getEstadoCorrida())){
			queryString += flagAnd ? " and " : " where ";
			queryString += " t.estadoCorrida.id="+corridaSearchPage.getCorrida().getEstadoCorrida().getId();
			flagAnd = true;
		}
		
		// filtro por fecha Desde
 		if (corridaSearchPage.getFechaDesde() != null ) {
            queryString += flagAnd ? " and " : " where ";
			queryString += " t.fechaInicio >= " + sqlDate(corridaSearchPage.getFechaDesde()) + "";
			
			flagAnd = true;
		}
		
 		// 	 filtro por fecha Hasta
 		if (corridaSearchPage.getFechaHasta() != null ) {
            queryString += flagAnd ? " and " : " where ";
			queryString += " t.fechaInicio <= " + sqlDate(corridaSearchPage.getFechaHasta()) + "";
			
			flagAnd = true;
		}

 		// filtro por Descripcion de Procesos
 		if(!StringUtil.isNullOrEmpty(corridaSearchPage.getProceso().getCodProceso())){
			queryString += flagAnd ? " and " : " where ";
			queryString += " t.proceso.codProceso = '" +  corridaSearchPage.getProceso().getCodProceso() + "'";
			flagAnd = true;
		}

 		// filtro por Descripcion de Procesos
 		if(!ModelUtil.isNullOrEmpty(corridaSearchPage.getProceso())){
			queryString += flagAnd ? " and " : " where ";
			queryString += " t.proceso.id = " +  corridaSearchPage.getProceso().getId();
			flagAnd = true;
		}

 		// filtro por tipo de ejecucion del proceso
		if(!ModelUtil.isNullOrEmpty(corridaSearchPage.getCorrida().getProceso().getTipoEjecucion())){
			queryString += flagAnd ? " and " : " where ";
			queryString += " t.proceso.tipoEjecucion.id="+corridaSearchPage.getCorrida().getProceso().getTipoEjecucion().getId();
			flagAnd = true;
		}
		
 		// Order By
		queryString += " order by t.fechaInicio DESC";
		
	    if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + queryString);

		List<Corrida> listCorrida = (ArrayList<Corrida>) executeCountedSearch(queryString, corridaSearchPage);
		
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listCorrida;

	}
	
	/**
	 * Devuelve las corridas procesadas con exito con fecha inicio igual o superior 
	 * a "fecha", para el proceso pasado como parametro.
	 * 
	 * @param codProceso
	 * @param fecha
	 * @return
	 */
	public List<Corrida> getListByCodProAfterFecIni(String codProceso, Date fecha){
		String queryStr="FROM Corrida t WHERE t.proceso.codProceso = '" + codProceso + "'";			
			   queryStr+=" AND t.fechaInicio >= " + sqlDateTime(fecha);
			   queryStr+=" AND t.estadoCorrida.id = "+EstadoCorrida.ID_PROCESADO_CON_EXITO;

		Session session = currentSession();

		// Obtenemos el resultado de la consulta
		Query query = session.createQuery(queryStr);
		return query.list();
	}
	
}
