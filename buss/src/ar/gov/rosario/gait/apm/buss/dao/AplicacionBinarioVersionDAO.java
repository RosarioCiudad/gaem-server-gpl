package ar.gov.rosario.gait.apm.buss.dao;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.AplicacionBinarioVersion;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;

public class AplicacionBinarioVersionDAO extends GenericDAO {

	private Logger log = Logger.getLogger(AplicacionBinarioVersionDAO.class);

	public AplicacionBinarioVersionDAO() {
		super(AplicacionBinarioVersion.class);
	}

}