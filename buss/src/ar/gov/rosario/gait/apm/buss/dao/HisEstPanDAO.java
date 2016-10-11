package ar.gov.rosario.gait.apm.buss.dao;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.EstadoPanico;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;

public class HisEstPanDAO extends GenericDAO {

	private Logger log = Logger.getLogger(HisEstPanDAO.class);

	public HisEstPanDAO() {
		super(EstadoPanico.class);
	}
}