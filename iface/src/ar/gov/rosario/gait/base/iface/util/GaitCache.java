/*******************************************************************************
 * Copyright (c) 2016 Municipalidad de Rosario, Coop. de Trabajo Tecso Ltda.
 *
 * This file is part of GAEM.
 *
 * GAEM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * GAEM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAEM.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
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
