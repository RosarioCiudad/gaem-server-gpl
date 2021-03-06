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

package coop.tecso.demoda.buss.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;


/**
 * Permite realizar una conexion JDBC en base a la configuracion tomada
 * de un archivo de propiedades
 * 
 * @author tecso
 *
 */

public class JDBCConnManager {

	static private Logger log = Logger.getLogger(JDBCConnManager.class);

	/** 
	 * Usa un Datasource via JNDI.
	 * @param datasource nombre del datasource. ej: java:comp/env/ds/gait
	 */
	static public Connection getConnection(String dsName) throws Exception {
		Connection result = null;
		try {
			Context initialContext = new InitialContext();
			DataSource datasource = (DataSource)initialContext.lookup(dsName);
			if (datasource != null) {
				//Throwable e = new Exception().fillInStackTrace();
				log.info(dataSourceInfo(dsName));
				result = datasource.getConnection();
				result.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			} else {
				log.error("Failed to lookup datasource:" + dsName);
			}
		} catch (Exception e) {
			if (result != null) result.close();
			throw e;
		}
		return result;
	}
	
	/** 
	 * Uses native jdbc.
	 * Usado solo para ejetuar los test y desarrollar.
	 */
	static public Connection getConnectionNative(String url,String user, String pwd ) throws Exception {
		Connection result = null;

		Class.forName("com.informix.jdbc.IfxDriver");
		result = DriverManager.getConnection(url, user, pwd);
		   result.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		
		return result;
	}
	
	static public String dataSourceInfo(String dsName) {
		String ret = "";
		try {
			Context initialContext = new InitialContext();
			
			Object o = initialContext.lookup(dsName);
			if (o == null){
				ret = "[DS=warn:No initial context. No se puede logear info de datasource.]";
				return ret;
			}
			Object datasource = initialContext.lookup(dsName);
			if (datasource != null) {
				ret  = "[DS=" + dsName; 
				ret += " numActive=" + invokeGetter("getNumActive", datasource);
				ret += " numIdle=" + invokeGetter("getNumIdle", datasource);
				ret += " maxActive=" + invokeGetter("getMaxActive", datasource);
				ret += "]";
			}
		} catch (Exception e) {
			ret = "[DS=warn:No se puede logear info de datasoruce.]";
		}
		return ret;
	}

	static private Object invokeGetter(String propName, Object obj) throws Exception {
		try {
			String getterName = propName;
			return obj.getClass().getMethod(getterName).invoke(obj, (Object[]) null);
		} catch (Exception ex) {
			return "Error: invoke getter:" + ex;
		}
	}

}
