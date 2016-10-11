package ar.gov.rosario.gait.base.buss.dao;

import org.apache.log4j.Logger;

import coop.tecso.demoda.iface.model.Common;

/**
 * Singleton de Caches de Gait
 * @author Coop. Tecso Ltda.
 *
 */
public class GaitBussCache extends Common {
	
	private static Logger log = Logger.getLogger((GaitBussCache.class));
	private static final GaitBussCache INSTANCE = new GaitBussCache();	
	
}	
