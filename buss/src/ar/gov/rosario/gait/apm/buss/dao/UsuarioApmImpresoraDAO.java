package ar.gov.rosario.gait.apm.buss.dao;


import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.UsuarioApmImpresora;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;

public class UsuarioApmImpresoraDAO extends GenericDAO {

	private Logger log = Logger.getLogger(UsuarioApmImpresoraDAO.class);

	public UsuarioApmImpresoraDAO() {
		super(UsuarioApmImpresora.class);
	}
}