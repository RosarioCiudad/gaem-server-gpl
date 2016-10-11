package ar.gov.rosario.gait.pro.buss.dao;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.pro.buss.bean.TipoProgEjec;

public class TipoProgEjecDAO extends GenericDAO {

	private Logger log = Logger.getLogger((TipoProgEjecDAO.class));	
	
	public TipoProgEjecDAO() {
		super(TipoProgEjec.class);
	}
/*
	public List<TipoProgEjec> getListBySearchPage(TipoProgEjecSearchPage tipoProgEjecSearchPage) throws Exception {
		return null;
	}*/
}
