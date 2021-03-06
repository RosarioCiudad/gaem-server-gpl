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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import coop.tecso.demoda.buss.bean.VersionableBO;

@Entity
@Table(name="apm_usuarioapmimpresora")
public class UsuarioApmImpresora extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idUsuario")
	private UsuarioApm usuarioApm;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idImpresora")
	private Impresora impresora;

	// Constructores
	public UsuarioApmImpresora() {
		super();
	}

	// Getters y Setters
	public UsuarioApm getUsuarioApm() {
		return usuarioApm;
	}

	public void setUsuarioApm(UsuarioApm usuarioApm) {
		this.usuarioApm = usuarioApm;
	}

	public Impresora getImpresora() {
		return impresora;
	}

	public void setImpresora(Impresora impresora) {
		this.impresora = impresora;
	}

	public static UsuarioApmImpresora getById(Long id) {
		return (UsuarioApmImpresora) ApmDAOFactory.getUsuarioApmImpresoraDAO().getById(id);
	}

	public static UsuarioApmImpresora getByIdNull(Long id) {
		return (UsuarioApmImpresora) ApmDAOFactory.getUsuarioApmImpresoraDAO().getByIdNull(id);
	}

	public static List<UsuarioApmImpresora> getList() {
		return ApmDAOFactory.getUsuarioApmImpresoraDAO().getList();
	}

	public static List<UsuarioApmImpresora> getListActivos() {			
		return ApmDAOFactory.getUsuarioApmImpresoraDAO().getListActiva();
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
		//limpiamos la lista de errores
		clearError();

		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {

		if (null == getImpresora()) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.IMPRESORA_LABEL);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}
}