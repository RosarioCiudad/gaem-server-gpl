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
package ar.gov.rosario.gait.base.buss.dao;

import java.sql.Connection;

import org.apache.log4j.Logger;

import coop.tecso.demoda.buss.dao.JDBCConnManager;

public class GaitJDBCDAO {

	public static final String DS_NAME = "java:comp/env/ds/gait";
       
    private Logger log = Logger.getLogger(GaitJDBCDAO.class);
	/**
	 * Constructor
	 * Pasa nombre del archivo de propiedades a la super de Demoda
	 */
    public GaitJDBCDAO() {
	}
    
    public static Connection getConnection() throws Exception {
    	return JDBCConnManager.getConnection(DS_NAME);
    }
}
