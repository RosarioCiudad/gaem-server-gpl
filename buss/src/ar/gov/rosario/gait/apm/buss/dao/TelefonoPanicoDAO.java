package ar.gov.rosario.gait.apm.buss.dao;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.TelefonoPanico;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;


public class TelefonoPanicoDAO extends GenericDAO {

	private Logger log = Logger.getLogger(TelefonoPanicoDAO.class);

	public TelefonoPanicoDAO() {
		super(TelefonoPanico.class);
	}
}