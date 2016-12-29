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

import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.pro.buss.bean.Corrida;
import ar.gov.rosario.gait.pro.buss.bean.PasoCorrida;

public class PasoCorridaDAO extends GenericDAO {

	//private Logger log = Logger.getLogger((PasoCorridaDAO.class));	
	
	public PasoCorridaDAO() {
		super(PasoCorrida.class);
	}
	
	/**
	 * Obtiene el PasoCorrida a partir de la Corrida y del Paso
	 * @param  corrida
	 * @param  paso
	 * @return PasoCorrida
	 */
	public PasoCorrida getByCorridaPaso(Corrida corrida, Integer paso){
		
		Session session = GaitHibernateUtil.currentSession();
		String sQuery = "FROM PasoCorrida pc WHERE pc.corrida = :corrida " +
				"AND paso = :paso";

		Query query = session.createQuery(sQuery)
			.setEntity("corrida", corrida)
			.setInteger("paso", paso);
		return (PasoCorrida) query.uniqueResult();		

		
	}
	
	/**
	 * Elimina los registros de PasoCorrida que corresponden a la corrida de id pasado.
	 * 
	 * @param idCorrida
	 * @return int
	 */
	public int deleteAllByIdCorrida (Long idCorrida){

		String queryString = "delete from PasoCorrida t ";
			   queryString += " where t.corrida.id = "+idCorrida; 
		
		Session session = GaitHibernateUtil.currentSession();
		Query query = session.createQuery(queryString);
	    
		return query.executeUpdate();
	}
	
}
