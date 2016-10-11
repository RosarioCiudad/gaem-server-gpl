package ar.gov.rosario.gait.apm.buss.dao;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampo;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;

public class AplPerfilSeccionCampoDAO extends GenericDAO {

	private Logger log = Logger.getLogger(AplPerfilSeccionCampoDAO.class);

	public AplPerfilSeccionCampoDAO() {
		super(AplPerfilSeccionCampo.class);
	}

}