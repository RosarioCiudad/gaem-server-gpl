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
package ar.gov.rosario.gait.frm.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.frm.buss.dao.FrmDAOFactory;
import ar.gov.rosario.gait.frm.iface.util.FrmError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.helper.StringUtil;

@Entity
@Table(name = "for_estadoTipoFormulario")
public class EstadoTipoFormulario extends VersionableBO {
	
	public static final Long procesada = 5L;

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String descripcion;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idTipoFormulario")
	private TipoFormulario tipoFormulario;

	// Constructores
	public EstadoTipoFormulario() {
		super();
	}

	// Metodos de Clase
	public static EstadoTipoFormulario getById(Long id) {
		return (EstadoTipoFormulario) FrmDAOFactory
				.getEstadoTipoFormularioDAO().getById(id);
	}

	public static EstadoTipoFormulario getByIdNull(Long id) {
		return (EstadoTipoFormulario) FrmDAOFactory
				.getEstadoTipoFormularioDAO().getByIdNull(id);
	}

	public static List<EstadoTipoFormulario> getList() {
		return FrmDAOFactory.getEstadoTipoFormularioDAO().getList();
	}

	public static List<EstadoTipoFormulario> getListActivos() {
		return FrmDAOFactory.getEstadoTipoFormularioDAO().getListActiva();
	}

	// Getters y Setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoFormulario getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormulario tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
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
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, FrmError.ESTADOTIPOFORMULARIO_DESCRIPCION_LABEL );
		}

		if (null == tipoFormulario) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, FrmError.ESTADOTIPOFORMULARIO_LABEL );
		}

		if (hasError()) {
			return false;
		}

		return true;
	}
}