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
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.def.buss.bean.Area;
import ar.gov.rosario.gait.def.iface.util.DefError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.buss.dao.UniqueMap;
import coop.tecso.demoda.iface.helper.StringUtil;

@Entity
@Table(name="apm_impresora")
public class Impresora extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@Column
	private String descripcion;

	@Column
	private String numeroSerie;

	@Column
	private String numeroUUID;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idArea")
	private Area area;

	// Constructores
	public Impresora() {
		super();
	}

	// Getters y Setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getNumeroUUID() {
		return numeroUUID;
	}

	public void setNumeroUUID(String numeroUUID) {
		this.numeroUUID = numeroUUID;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	// Metodos de Clase
	public static Impresora getById(Long id) {
		return (Impresora) ApmDAOFactory.getImpresoraDAO().getById(id);
	}

	public static Impresora getByIdNull(Long id) {
		return (Impresora) ApmDAOFactory.getImpresoraDAO().getByIdNull(id);
	}

	public static List<Impresora> getList() {
		return ApmDAOFactory.getImpresoraDAO().getList();
	}

	public static List<Impresora> getListActivos() {			
		return ApmDAOFactory.getImpresoraDAO().getListActiva();
	}

	public static List<Impresora> getDeltaByUsuario(String username, Integer version) {			
		return ApmDAOFactory.getImpresoraDAO().getDeltaByUsuario(username, version);
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
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.IMPRESORA_DESCRIPCION);
		}

		if (StringUtil.isNullOrEmpty(getNumeroSerie())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.IMPRESORA_SERIE);
		}

		if (StringUtil.isNullOrEmpty(getNumeroUUID())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.IMPRESORA_NUMEROUUID);
		}

		if (getArea() == null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, DefError.AREA_LABEL);
		}

		if (hasError()) {
			return false;
		}

		UniqueMap uniqueMap = new UniqueMap();
		uniqueMap.addString("numeroUUID");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.IMPRESORA_NUMEROUUID);			
		}
		
		uniqueMap = new UniqueMap();
		uniqueMap.addString("numeroSerie");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.IMPRESORA_SERIE);			
		}

		if (hasError()) {
			return false;
		}

		return true;
	}
}