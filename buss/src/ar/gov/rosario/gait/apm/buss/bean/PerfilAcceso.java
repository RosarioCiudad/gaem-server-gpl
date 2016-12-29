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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.def.buss.bean.Area;
import ar.gov.rosario.gait.def.buss.bean.AreaAplicacionPerfil;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.helper.StringUtil;


/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "apm_perfilAcceso")
public class PerfilAcceso extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idArea")
	private Area area;

	@Column(nullable = false)
	private String descripcion;
	
	@OneToMany()
	@JoinColumn(name="idPerfilAcceso")
	@Where(clause = "estado = 1")
	private List<AreaAplicacionPerfil> listAreaAplicacionPerfil;
	
	@OneToMany()
	@JoinColumn(name="idPerfilAcceso")
	@Where(clause = "estado = 1")
	private List<PerfilAccesoAplicacion> listPerfilAccesoAplicacion;

	// Constructores
	public PerfilAcceso() {
		super();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}

	public List<AreaAplicacionPerfil> getListAreaAplicacionPerfil() {
		return listAreaAplicacionPerfil;
	}

	public void setListAreaAplicacionPerfil(
			List<AreaAplicacionPerfil> listAreaAplicacionPerfil) {
		this.listAreaAplicacionPerfil = listAreaAplicacionPerfil;
	}

	public List<PerfilAccesoAplicacion> getListPerfilAccesoAplicacion() {
		return listPerfilAccesoAplicacion;
	}

	public void setListPerfilAccesoAplicacion(
			List<PerfilAccesoAplicacion> listPerfilAccesoAplicacion) {
		this.listPerfilAccesoAplicacion = listPerfilAccesoAplicacion;
	}

	// Metodos de Clase
	public static PerfilAcceso getById(Long id) {
		return (PerfilAcceso) ApmDAOFactory.getPerfilAccesoDAO().getById(id);
	}

	public static PerfilAcceso getByIdNull(Long id) {
		return (PerfilAcceso) ApmDAOFactory.getPerfilAccesoDAO().getByIdNull(id);
	}

	public static List<PerfilAcceso> getList() {
		return ApmDAOFactory.getPerfilAccesoDAO().getList();
	}

	public static List<PerfilAcceso> getListActivos() {
		return ApmDAOFactory.getPerfilAccesoDAO().getListActiva();
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
		if (StringUtil.isNullOrEmpty(descripcion)) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.PERFILACCESO_DESCRIPCION);
		}

		if (null == area) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.PERFILACCESO_AREA);
		}
		
		if (hasError()) {
			return false;
		}

		return true;
	}
}