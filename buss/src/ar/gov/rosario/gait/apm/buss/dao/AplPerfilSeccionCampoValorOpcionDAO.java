package ar.gov.rosario.gait.apm.buss.dao;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampoValorOpcion;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;

public class AplPerfilSeccionCampoValorOpcionDAO extends GenericDAO {

	private Logger log = Logger.getLogger(AplPerfilSeccionCampoValorOpcionDAO.class);

	public AplPerfilSeccionCampoValorOpcionDAO() {
		super(AplPerfilSeccionCampoValorOpcion.class);
	}

}