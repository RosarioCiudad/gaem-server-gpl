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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.not.buss.dao.NotDAOFactory;
import ar.gov.rosario.gait.not.iface.util.NotError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.helper.StringUtil;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "not_estadoNotificacion")
public class EstadoNotificacion extends VersionableBO {
	
	private static final long serialVersionUID = 1L;
	
	public static final long ID_ESTADO_PENDIENTE = 1L;
	public static final long ID_ESTADO_ENVIADA = 2L;
	
	@Column(nullable = false)
	private String descripcion;
	

	// Metodos de Clase
	public static EstadoNotificacion getById(Long id) {
		return (EstadoNotificacion) NotDAOFactory.getEstadoNotificacionDAO().getById(id);
	}
	
	public static EstadoNotificacion getByIdNull(Long id) {
		return (EstadoNotificacion) NotDAOFactory.getEstadoNotificacionDAO().getByIdNull(id);
	}
	
	@SuppressWarnings("unchecked")
	public static List<EstadoNotificacion> getList() {
		return (ArrayList<EstadoNotificacion>) NotDAOFactory.getEstadoNotificacionDAO().getList();
	}
	
	public static List<EstadoNotificacion> getListActivos() {			
		return NotDAOFactory.getEstadoNotificacionDAO().getListActiva();
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	// Validaciones
		public boolean validateCreate() throws Exception {
			// limpiamos la lista de errores
			clearError();

			if (!this.validate()) {
				return false;
			}

			// Validaciones de Negocio

			return true;
		}

		public boolean validateUpdate() throws Exception {
			// limpiamos la lista de errores
			clearError();

			if (!this.validate()) {
				return false;
			}

			// Validaciones de Negocio

			return true;
		}

		public boolean validateDelete() throws Exception {
			// limpiamos la lista de errores
			clearError();

			if (!this.validate()) {
				return false;
			}

			if (hasError()) {
				return false;
			}

			return true;
		}

		private boolean validate() throws Exception {

			// Validaciones
			if (StringUtil.isNullOrEmpty(getDescripcion())) {
				addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
						NotError.NOTIFICACION_DESCRIPCIONREDUCIDA);
			}

			if (hasError()) {
				return false;
			}

			return true;
		}

}