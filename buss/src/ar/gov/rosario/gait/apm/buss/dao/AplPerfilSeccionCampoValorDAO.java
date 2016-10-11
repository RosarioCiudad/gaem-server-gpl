package ar.gov.rosario.gait.apm.buss.dao;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampoValor;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;

public class AplPerfilSeccionCampoValorDAO extends GenericDAO {

	private Logger log = Logger.getLogger(AplPerfilSeccionCampoValorDAO.class);

	public AplPerfilSeccionCampoValorDAO() {
		super(AplPerfilSeccionCampoValor.class);
	}

}