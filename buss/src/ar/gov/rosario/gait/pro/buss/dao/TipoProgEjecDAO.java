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
package ar.gov.rosario.gait.pro.buss.dao;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.pro.buss.bean.TipoProgEjec;

public class TipoProgEjecDAO extends GenericDAO {

	private Logger log = Logger.getLogger((TipoProgEjecDAO.class));	
	
	public TipoProgEjecDAO() {
		super(TipoProgEjec.class);
	}
/*
	public List<TipoProgEjec> getListBySearchPage(TipoProgEjecSearchPage tipoProgEjecSearchPage) throws Exception {
		return null;
	}*/
}
