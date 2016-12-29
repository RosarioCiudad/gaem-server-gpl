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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.bean.AplicacionPerfil;
import ar.gov.rosario.gait.apm.buss.bean.PerfilAcceso;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.def.buss.dao.DefDAOFactory;
import ar.gov.rosario.gait.def.iface.util.DefError;
import coop.tecso.demoda.buss.bean.VersionableBO;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "def_areaAplicacionPerfil")
public class AreaAplicacionPerfil extends VersionableBO  {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idArea")
	private Area area;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idAplicacionPerfil")
	private AplicacionPerfil aplicacionPerfil;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idPerfilAcceso")
	private PerfilAcceso perfilAcceso;

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public AplicacionPerfil getAplicacionPerfil() {
		return aplicacionPerfil;
	}

	public void setAplicacionPerfil(AplicacionPerfil aplicacionPerfil) {
		this.aplicacionPerfil = aplicacionPerfil;
	}

	public PerfilAcceso getPerfilAcceso() {
		return perfilAcceso;
	}

	public void setPerfilAcceso(PerfilAcceso perfilAcceso) {
		this.perfilAcceso = perfilAcceso;
	}

	public static List<AreaAplicacionPerfil> getListByArea(long areaID) {
		return  DefDAOFactory.getAreaAplicacionPerfilDAO().getListByArea(areaID);
	}

	public static AreaAplicacionPerfil getById(Long id) {
		return (AreaAplicacionPerfil) DefDAOFactory.getAreaAplicacionPerfilDAO().getById(
				id);
	}

	public static AreaAplicacionPerfil getByIdNull(Long id) {
		return (AreaAplicacionPerfil) DefDAOFactory.getAreaAplicacionPerfilDAO()
				.getByIdNull(id);
	}
	
	public static List<AreaAplicacionPerfil> getList() {
		return  DefDAOFactory.getAreaAplicacionPerfilDAO().getList();
	}
	
	public static List<AreaAplicacionPerfil> getListActivos() {			
		return DefDAOFactory.getAreaAplicacionPerfilDAO().getListActiva();
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

		if (aplicacionPerfil == null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					DefError.AREAAPLICACIONPERFIL_LABEL);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}
}