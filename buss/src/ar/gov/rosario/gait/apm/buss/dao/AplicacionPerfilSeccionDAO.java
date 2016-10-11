package ar.gov.rosario.gait.apm.buss.dao;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.AplicacionPerfilSeccion;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;

public class AplicacionPerfilSeccionDAO extends GenericDAO {

	private Logger log = Logger.getLogger(AplicacionPerfilSeccionDAO.class);

	public AplicacionPerfilSeccionDAO() {
		super(AplicacionPerfilSeccion.class);
	}

}