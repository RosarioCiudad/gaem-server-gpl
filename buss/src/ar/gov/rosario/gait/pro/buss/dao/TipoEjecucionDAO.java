package ar.gov.rosario.gait.pro.buss.dao;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.pro.buss.bean.TipoEjecucion;

public class TipoEjecucionDAO extends GenericDAO {

	private Logger log = Logger.getLogger((TipoEjecucionDAO.class));	
	
	public TipoEjecucionDAO() {
		super(TipoEjecucion.class);
	}
/*
	public List<TipoEjecucion> getListBySearchPage(TipoEjecucionSearchPage tipoEjecucionSearchPage) throws Exception {
		return null;
	}*/
}
