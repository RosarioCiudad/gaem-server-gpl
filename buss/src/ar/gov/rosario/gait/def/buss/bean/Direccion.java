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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.def.buss.dao.DefDAOFactory;
import ar.gov.rosario.gait.def.iface.util.DefError;
import coop.tecso.demoda.buss.bean.BaseBO;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

/**
 * Bean correspondiente a Direccion
 * 
 * @author tecso
 */
@Entity //
@Table(name = "def_direccion")
public class Direccion extends BaseBO {

	private static final long serialVersionUID = 1L;

	@Column
	private String descripcion;

	@Column
	private int esDGI; //DGI: direccion general de informatica
	
	@OneToMany()
	@JoinColumn(name="idDireccion")
	@Where(clause = "estado = 1")
	private List<Area> listArea;
	
	@OneToMany()
	@JoinColumn(name="idDireccion")
	@Where(clause = "estado = 1")
	private List<DireccionAplicacionPerfil> listDireccionAplicacionPerfil;

	// Constructores
	public Direccion() {
		super();
	}

	// Metodos de Clase
	public static Direccion getById(Long id) {
		return (Direccion) DefDAOFactory.getDireccionDAO().getById(id);
	}

	public static Direccion getByIdNull(Long id) {
		return (Direccion) DefDAOFactory.getDireccionDAO().getByIdNull(id);
	}

	public static List<Direccion> getList() {
		return DefDAOFactory.getDireccionDAO().getList();
	}

	public static List<Direccion> getListActivos() {
		return DefDAOFactory.getDireccionDAO().getListActiva();
	}

	// Getters y setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEsDGI() {
		return esDGI;
	}

	public void setEsDGI(int esDGI) {
		this.esDGI = esDGI;
	}
	
	public List<Area> getListArea() {
		return listArea;
	}

	public void setListArea(List<Area> listArea) {
		this.listArea = listArea;
	}

	public List<DireccionAplicacionPerfil> getListDireccionAplicacionPerfil() {
		return listDireccionAplicacionPerfil;
	}

	public void setListDireccionAplicacionPerfil(
			List<DireccionAplicacionPerfil> listDireccionAplicacionPerfil) {
		this.listDireccionAplicacionPerfil = listDireccionAplicacionPerfil;
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
					DefError.DIRECCION_DESCRIPCION);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}

	// Metodos de negocio
	/**
	 * Activa el Direccion. Previamente valida la activacion.
	 * 
	 */
	public void activar() {
		if (!this.validateActivar()) {
			return;
		}
		this.setEstado(Estado.ACTIVO.getId());
		DefDAOFactory.getDireccionDAO().update(this);
	}

	/**
	 * Desactiva el Direccion. Previamente valida la desactivacion.
	 * 
	 */
	public void desactivar() {
		if (!this.validateDesactivar()) {
			return;
		}
		this.setEstado(Estado.INACTIVO.getId());
		DefDAOFactory.getDireccionDAO().update(this);
	}

	/**
	 * Valida la activacion del Direccion
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
	 * Valida la desactivacion del Direccion
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