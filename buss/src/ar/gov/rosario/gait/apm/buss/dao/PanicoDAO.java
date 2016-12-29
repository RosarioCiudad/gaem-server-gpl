/*******************************************************************************
 * Copyright (c) 2016 Municipalidad de Rosario, Coop. de Trabajo Tecso Ltda.
 *
 * This file is part of GAEM.
 *
 * GAEM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * GAEM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAEM.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package ar.gov.rosario.gait.apm.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.EstadoPanico;
import ar.gov.rosario.gait.apm.buss.bean.Panico;
import ar.gov.rosario.gait.apm.iface.model.PanicoSearchPage;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.def.buss.bean.Area;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class PanicoDAO extends GenericDAO {

	private Logger log = Logger.getLogger(PanicoDAO.class);

	public PanicoDAO() {
		super(Panico.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Panico> getBySearchPage(PanicoSearchPage panicoSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		QueryMaker query = HibernateQueryMaker.make();
		
		query.add("FROM Panico t WHERE 1=1 ");
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// area
		query.addIfNotNull(" AND t.area.id = ?", ModelUtil.bussId(panicoSearchPage.getDispositivoMovil().getArea()));
		// inspector
		query.addIfNotNull(" AND t.estadoPanico.id = ?", ModelUtil.bussId(panicoSearchPage.getPanico().getEstadoPanico()));
		// estado
		query.addIfNotNull(" AND t.usuarioPanico.id = ?", ModelUtil.bussId(panicoSearchPage.getUsuarioPanico()));
		// fecha desde
		query.addIf(!StringUtil.isNullOrEmpty(panicoSearchPage.getPanico().getFechaPanicoDesdeView()),
				" AND t.fechaPanico >=" +sqlDate(panicoSearchPage.getPanico().getFechaPanicoDesde()));
		// fecha hasta
		//por ser un timestamp, se agrega un día a la fechaHasta y se saca el = de la comparación
		if (!StringUtil.isNullOrEmpty(panicoSearchPage.getPanico().getFechaPanicoHastaView()))
			query.add(" AND t.fechaPanico < " +sqlDate(DateUtil.addDaysToDate(panicoSearchPage.getPanico().getFechaPanicoHasta(), 1)));
		
		query.add(" ORDER BY t.fechaRecepcion DESC");
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		
		List<Panico> listPanico = (ArrayList<Panico>) executeCountedSearch(query, panicoSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listPanico;
	}
	
	/**
	 * 
	 * @param formulario
	 * @param codigo
	 * @return
	 */
	public List<Panico> getListPendienteByArea(Area area){
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		query.add("FROM Panico t WHERE 1=1 ");
		// Area
		query.add("AND t.area.id = ? ", area.getId());
		// Estado
		query.add("AND t.estadoPanico.id = ? ", EstadoPanico.ID_PENDIENTE);
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		List<Panico> result = HibernateQueryMaker.query(query).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return result; 
	}
	
	public List<Panico> getListAnuladosByArea(Area area){
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		query.add("FROM Panico t WHERE 1=1 ");
		// Area
		query.add("AND t.area.id = ? ", area.getId());
		// Estado
		query.add("AND t.estadoPanico.id = ? ", EstadoPanico.ID_ANULADO);
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		List<Panico> result = HibernateQueryMaker.query(query).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return result; 
	}
	
	public List<Panico> getListAtendidoByArea(Area area){
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		query.add("FROM Panico t WHERE 1=1 ");
		// Area
		query.add("AND t.area.id = ? ", area.getId());
		// Estado
		query.add("AND t.estadoPanico.id = ? ", EstadoPanico.ID_ATENDIDO);
		
		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + query);
		List<Panico> result = HibernateQueryMaker.query(query).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return result; 
	}
}