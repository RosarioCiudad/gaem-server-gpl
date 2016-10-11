package ar.gov.rosario.gait.aid.buss.service;

import org.apache.log4j.Logger;

public class AidReparticionManager {

	private static Logger log = Logger.getLogger(AidReparticionManager.class);

	private static final AidReparticionManager INSTANCE = new AidReparticionManager();

	/**
	 * Constructor privado
	 */
	private AidReparticionManager() {
		
	}

	/**
	 * Devuelve unica instancia
	 */
	public static AidReparticionManager getInstance() {
		return INSTANCE;
	}
}