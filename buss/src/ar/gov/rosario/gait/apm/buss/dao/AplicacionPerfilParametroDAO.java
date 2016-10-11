package ar.gov.rosario.gait.apm.buss.dao;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.AplicacionPerfilParametro;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;

public class AplicacionPerfilParametroDAO extends GenericDAO {

	private Logger log = Logger.getLogger(AplicacionPerfilParametroDAO.class);

	public AplicacionPerfilParametroDAO() {
		super(AplicacionPerfilParametro.class);
	}

}