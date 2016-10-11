package ar.gov.rosario.gait.pro.buss.dao;

import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.pro.buss.bean.EstadoCorrida;

public class EstadoCorridaDAO extends GenericDAO {

	//private Logger log = Logger.getLogger((EstadoCorridaDAO.class));	
	
	public EstadoCorridaDAO() {
		super(EstadoCorrida.class);
	}
}
