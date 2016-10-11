package ar.gov.rosario.gait.def.buss.dao;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.def.buss.bean.Area;

public class AreaDAO extends GenericDAO {

	private Logger log = Logger.getLogger(AreaDAO.class);
	
	public AreaDAO() {
		super(Area.class);
	}
	
	
	
}