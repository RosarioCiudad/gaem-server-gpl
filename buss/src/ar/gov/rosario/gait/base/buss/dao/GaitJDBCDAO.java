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
