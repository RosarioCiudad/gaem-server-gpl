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
package ar.gov.rosario.gait.def.buss.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import ar.gov.rosario.gait.apm.buss.bean.AplicacionPerfil;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.def.buss.dao.DefDAOFactory;
import coop.tecso.demoda.buss.bean.VersionableBO;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "def_direccionAplicacionPerfil")
public class DireccionAplicacionPerfil extends VersionableBO  {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idDireccion")
	private Direccion direccion;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idAplicacionPerfil")
	@Where(clause="estado = 1")
	private AplicacionPerfil aplicacionPerfil; //Formulario

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public AplicacionPerfil getAplicacionPerfil() {
		return aplicacionPerfil;
	}

	public void setAplicacionPerfil(AplicacionPerfil aplicacionPerfil) {
		this.aplicacionPerfil = aplicacionPerfil;
	}


	// Metodos de Clase
	public static DireccionAplicacionPerfil getById(Long id) {
		return (DireccionAplicacionPerfil) DefDAOFactory.getDireccionAplicacionPerfilDAO().getById(id);
	}

	public static DireccionAplicacionPerfil getByIdNull(Long id) {
		return (DireccionAplicacionPerfil) DefDAOFactory.getDireccionAplicacionPerfilDAO().getByIdNull(id);
	}

	public static List<DireccionAplicacionPerfil> getList() {
		return (ArrayList<DireccionAplicacionPerfil>) DefDAOFactory.getDireccionAplicacionPerfilDAO().getList();
	}

	public static List<DireccionAplicacionPerfil> getListActivos() {
		return DefDAOFactory.getDireccionAplicacionPerfilDAO().getListActiva();
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
		if (getAplicacionPerfil() == null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					ApmError.APLICACIONPERFIL_DESCRIPCION);
		}



		if (hasError()) {
			return false;
		}

		return true;
	}

}
