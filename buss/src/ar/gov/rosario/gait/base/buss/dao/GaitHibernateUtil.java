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

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;

public class GaitHibernateUtil {

    private static final SessionFactory sessionFactory;
    private static ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static String sqlDialect;
        
    private static StatelessSession sSession;
	private static String HIBERNATE_CFG_RESOURCE = "/gait.hibernate.cfg.xml";
	static private Logger log = Logger.getLogger(GaitHibernateUtil.class);

    static {
        try {
            // Create the SessionFactory
        	AnnotationConfiguration cfg = new AnnotationConfiguration();
        	cfg.configure(HIBERNATE_CFG_RESOURCE);
        	sessionFactory = cfg.buildSessionFactory();
        	sqlDialect = cfg.getProperty("dialect");
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.out.println("Initial SessionFactory creation failed." + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session currentSession() throws HibernateException {    	
    	Session s = session.get();
    	if (s == null) {
			//Throwable e = new Exception().fillInStackTrace();
			StringBuffer sb = new StringBuffer();
			sb.append(coop.tecso.demoda.buss.dao.JDBCConnManager.dataSourceInfo("java:comp/env/ds/gait")).append("-");
			sb.append(coop.tecso.demoda.buss.dao.JDBCConnManager.dataSourceInfo("java:comp/env/ds/swe")).append("-");
			sb.append(coop.tecso.demoda.buss.dao.JDBCConnManager.dataSourceInfo("java:comp/env/ds/generaldb"));
			log.info(sb.toString());
        	s = sessionFactory.openSession();
        	session.set(s);
        }
        return s;  
    }
    
    /**
     * 	Obtiene la session de Hibernate y se setea el modo AutoFlush cada "maxTransactionToFlush" transacciones.
     *  Donde "maxTransactionToFlush" es pasado como parametro.
     * 
     * @param maxTransactionToFlush
     * @return
     * @throws HibernateException
     */
    public static Session currentSession(Long maxTransactionToFlush) throws HibernateException {    	
    	Session s = session.get();
    	if (s == null) {
        	s = sessionFactory.openSession();
        	session.set(s);
        }
        return s;  
    }

    
    public static StatelessSession currentStatelessSession() throws HibernateException {    	
    	sSession = sessionFactory.openStatelessSession();
        return sSession;  
    }

    
    public static void closeSession() {
    	try {
	    	Session s = session.get();
	        if (s != null) {
				try {
					s.close();
				} catch (Exception e) {
					s = null;
				}
			}
	    	session.set(null);
    	} catch (Exception e) {
    		System.out.println("ATENCION: Si ve esto, significa que pueden existir sesiones sin cerrar.");
    	}
    }
    
    public static void closeSessionFactory() {
    	closeSession();
    	sessionFactory.close(); // Free all resources
    }	

	public static String getSqlDialect() {
		return sqlDialect;
	}

}
