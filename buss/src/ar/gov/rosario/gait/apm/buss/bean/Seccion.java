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
package ar.gov.rosario.gait.apm.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

/**
 * Bean correspondiente a Seccion
 * 
 * @author tecso
 */
@Entity
@Table(name = "apm_seccion")
public class Seccion extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@Column
	private String descripcion;

	// Constructores
	public Seccion() {
		super();
	}

	// Metodos de Clase
	public static Seccion getById(Long id) {
		return (Seccion) ApmDAOFactory.getSeccionDAO().getById(id);
	}

	public static Seccion getByIdNull(Long id) {
		return (Seccion) ApmDAOFactory.getSeccionDAO().getByIdNull(id);
	}

	public static List<Seccion> getList() {
		return ApmDAOFactory.getSeccionDAO().getList();
	}

	public static List<Seccion> getListActivos() {
		return ApmDAOFactory.getSeccionDAO().getListActiva();
	}

	// Getters y setters

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

	public boolean validateDelete() {
		// limpiamos la lista de errores
		clearError();

		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {

		// Validaciones

		if (StringUtil.isNullOrEmpty(getDescripcion())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					ApmError.SECCION_DESCRIPCION);
		}
		
		if (hasError()) {
			return false;
		}

		return true;
	}

	// Metodos de negocio
	
	public static Seccion getByCodigo(String codigo) throws Exception{
		return (Seccion) ApmDAOFactory.getSeccionDAO().getByCodigo(codigo);
	}

	/**
	 * Activa el Seccion. Previamente valida la activacion.
	 * 
	 */
	public void activar() {
		if (!this.validateActivar()) {
			return;
		}
		this.setEstado(Estado.ACTIVO.getId());
		ApmDAOFactory.getSeccionDAO().update(this);
	}

	/**
	 * Desactiva el Seccion. Previamente valida la desactivacion.
	 * 
	 */
	public void desactivar() {
		if (!this.validateDesactivar()) {
			return;
		}
		this.setEstado(Estado.INACTIVO.getId());
		ApmDAOFactory.getSeccionDAO().update(this);
	}

	/**
	 * Valida la activacion del Seccion
	 * 
	 * @return boolean
	 */
	private boolean validateActivar() {
		// limpiamos la lista de errores
		clearError();

		// Validaciones
		return true;
	}

	/**
	 * Valida la desactivacion del Seccion
	 * 
	 * @return boolean
	 */
	private boolean validateDesactivar() {
		// limpiamos la lista de errores
		clearError();

		// Validaciones
		return true;
	}
}
