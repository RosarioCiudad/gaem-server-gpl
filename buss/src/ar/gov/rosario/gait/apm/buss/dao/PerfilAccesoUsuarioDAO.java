package ar.gov.rosario.gait.apm.buss.dao;


import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.PerfilAccesoUsuario;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;

public class PerfilAccesoUsuarioDAO extends GenericDAO {

	private Logger log = Logger.getLogger(PerfilAccesoUsuarioDAO.class);

	public PerfilAccesoUsuarioDAO() {
		super(PerfilAccesoUsuario.class);
	}

	
}