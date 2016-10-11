package ar.gov.rosario.gait.base.iface.util;

import java.util.List;

import org.apache.log4j.Logger;

import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.model.Common;
import coop.tecso.swe.iface.model.SweContext;

/**
 * Singleton de Caches de Gait
 * @author Coop. Tecso Ltda.
 *
 */
public class GaitCache extends Common {
	
	private static Logger log = Logger.getLogger((GaitCache.class));
	private static final GaitCache INSTANCE = new GaitCache();
	private SweContext sweContext;
	
	/**
	 * Constructor privado
	 */
	private GaitCache() {
	}
	
	/**
	 * Devuelve unica instancia
	 */
	public static GaitCache getInstance() {
		return INSTANCE;
	}

	/**
	 * Obtiene el SweContext en cache
	 */
	synchronized public SweContext getSweContext() throws DemodaServiceException {
		return this.sweContext;
	}

	/**
	 * Actualiza el SweContext en cache
	 */
	synchronized public void setSweContext(SweContext sweContext) {
		this.sweContext = sweContext;

		List listItemMenu = this.sweContext.getListItemMenuApp();
	}
}	
