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
package ar.gov.rosario.gait.not.buss.dao;

import java.util.List;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.frm.buss.bean.Talonario;
import ar.gov.rosario.gait.frm.buss.bean.TipoFormulario;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.Estado;

public class TalonarioDAO extends GenericDAO {

	private Logger log = Logger.getLogger(TalonarioDAO.class);
	
	public TalonarioDAO() {
		super(Talonario.class);
	}
	
	/**
	 * Obtiene Talonario ACTIVO para TipoFormulario y DispositivoMovil
	 */
	public Talonario getByTipoFormularioAndDispositivoMovil(
			TipoFormulario tipoFormulario, DispositivoMovil dispositivoMovil) {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		Talonario talonario;
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		
		query.add("FROM Talonario t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// TipoFormulario
		query.add(" AND t.tipoFormulario.id = ?", tipoFormulario.getId());
		// ValorRestante
		query.add(" AND t.dispositivoMovil.id = ?", dispositivoMovil.getId());
		
		talonario = (Talonario) HibernateQueryMaker.query(query);
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return talonario; 
	}
	
	
	/**
	 * Obtiene Talonario ACTIVO para TipoFormulario y DispositivoMovil
	 */
	public List<Talonario> getListBy(
			TipoFormulario tipoFormulario, DispositivoMovil dispositivoMovil) {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		List<Talonario> listTalonario;
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		
		query.add("FROM Talonario t WHERE 1=1 ");
		// estado
		query.add(" AND t.estado = ?", Estado.ACTIVO.getId());
		// TipoFormulario
		query.add(" AND t.tipoFormulario.id = ?", tipoFormulario.getId());
		// ValorRestante
		query.add(" AND t.dispositivoMovil.id = ?", dispositivoMovil.getId());
		// Order
		query.add(" ORDER BY t.valor ");
		
		listTalonario = HibernateQueryMaker.query(query).list();
		if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		return listTalonario; 
	}
}