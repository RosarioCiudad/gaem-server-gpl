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
package ar.gov.rosario.gait.not.buss.bean;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.not.buss.dao.NotDAOFactory;
import coop.tecso.demoda.iface.DemodaServiceException;

/**
 * Manejador del m&oacute;dulo NOt y submodulo Notificacion
 * 
 * @author tecso
 *
 */

public class NotNotificacionManager {

	private static Logger log = Logger.getLogger(NotNotificacionManager.class);

	private static final NotNotificacionManager INSTANCE = new NotNotificacionManager();

	/**
	 * Constructor privado
	 */
	private NotNotificacionManager() {

	}

	/**
	 * Devuelve unica instancia
	 */
	public static NotNotificacionManager getInstance() {
		return INSTANCE;
	}

	// ---> ABM Notificacion
	public Notificacion createNotificacion(Notificacion notificacion) throws Exception {

		// Validaciones de negocio
		if (!notificacion.validateCreate()) {
			return notificacion;
		}

		NotDAOFactory.getNotificacionDAO().update(notificacion);

		return notificacion;
	}


	public static List<Notificacion> getPendingNotifications(Integer dID) {
		return NotDAOFactory.getNotificacionDAO().getNotifications(dID);
	}

	public static void receiveNotification(Long nID) {
		Notificacion n = Notificacion.getById(nID);
		EstadoNotificacion en = EstadoNotificacion.getById(EstadoNotificacion.ID_ESTADO_ENVIADA);
		n.setFechaRecepcion(new Date());
		n.setEstadoNotificacion(en);

		NotDAOFactory.getNotificacionDAO().update(n);
	}

	// ---> FIN ABM Notificacion
	
	// ---> ABM TipoNotificacion
	public TipoNotificacion createTipoNotificacion(TipoNotificacion tipoNotificacion) throws Exception {

		// Validaciones de negocio
		if (!tipoNotificacion.validateCreate()) {
			return tipoNotificacion;
		}

		NotDAOFactory.getTipoNotificacionDAO().update(tipoNotificacion);

		return tipoNotificacion;
	}

	public TipoNotificacion updateTipoNotificacion(TipoNotificacion tipoNotificacion) throws Exception {

		// Validaciones de negocio
		if (!tipoNotificacion.validateUpdate()) {
			return tipoNotificacion;
		}

		NotDAOFactory.getTipoNotificacionDAO().update(tipoNotificacion);

		return tipoNotificacion;
	}

	public TipoNotificacion deleteTipoNotificacion(TipoNotificacion tipoNotificacion) throws Exception {

		// Validaciones de negocio
		if (!tipoNotificacion.validateDelete()) {
			return tipoNotificacion;
		}

		NotDAOFactory.getTipoNotificacionDAO().delete(tipoNotificacion);

		return tipoNotificacion;
	}

	public static List<TipoNotificacion> getNotNotificacion(String codApp)
			throws Exception {
		try {
			List<TipoNotificacion> listTipoNotificacion = NotDAOFactory
					.getTipoNotificacionDAO().getListByApp(codApp);

			return listTipoNotificacion;
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		}
	}

	// <--- ABM TipoNotificacion
	
	// ---> ABM EstadoNotificacion
	public EstadoNotificacion createEstadoNotificacion(EstadoNotificacion estadoNotificacion) throws Exception {

		// Validaciones de negocio
		if (!estadoNotificacion.validateCreate()) {
			return estadoNotificacion;
		}

		NotDAOFactory.getEstadoNotificacionDAO().update(estadoNotificacion);

		return estadoNotificacion;
	}

	public EstadoNotificacion updateEstadoNotificacion(EstadoNotificacion estadoNotificacion) throws Exception {

		// Validaciones de negocio
		if (!estadoNotificacion.validateUpdate()) {
			return estadoNotificacion;
		}

		NotDAOFactory.getEstadoNotificacionDAO().update(estadoNotificacion);

		return estadoNotificacion;
	}

	public EstadoNotificacion deleteEstadoNotificacion(EstadoNotificacion estadoNotificacion) throws Exception {

		// Validaciones de negocio
		if (!estadoNotificacion.validateDelete()) {
			return estadoNotificacion;
		}

		NotDAOFactory.getEstadoNotificacionDAO().delete(estadoNotificacion);

		return estadoNotificacion;
	}

	public static List<EstadoNotificacion> getNotEstadoNotificacion(String codApp)
			throws Exception {
		try {
			List<EstadoNotificacion> listEstadoNotificacion = NotDAOFactory
					.getEstadoNotificacionDAO().getListByApp(codApp);

			return listEstadoNotificacion;
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		}
	}

	// <--- ABM EstadoNotificacion
	
}