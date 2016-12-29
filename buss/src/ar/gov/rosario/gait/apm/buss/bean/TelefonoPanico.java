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
@Table(name="apm_telefonoPanico")
public class TelefonoPanico extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idArea")
	private Area area;

	@Column(nullable = false)
	private String numero;
	
	@Column
	private String descripcion;

	// Constructores
	public TelefonoPanico() {
		super();
	}
	public TelefonoPanico(Long id){
		super();
		setId(id);
	}

	// Metodos de Clase
	public static TelefonoPanico getById(Long id) {
		return (TelefonoPanico) ApmDAOFactory.getTelefonoPanicoDAO().getById(id);
	}

	public static TelefonoPanico getByIdNull(Long id) {
		return (TelefonoPanico) ApmDAOFactory.getTelefonoPanicoDAO().getByIdNull(id);
	}

	public static List<TelefonoPanico> getList() {
		return ApmDAOFactory.getTelefonoPanicoDAO().getList();
	}

	public static List<TelefonoPanico> getListActivos() {			
		return ApmDAOFactory.getTelefonoPanicoDAO().getListActiva();
	}

	// Getters y Setters
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
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
		if (StringUtil.isNullOrEmpty(getNumero())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.TELEFONOPANICO_NUMERO);
		}

		if (getArea() == null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, DefError.AREA_LABEL);
		}

		if (hasError()) {
			return false;
		}
		
		if (!StringUtil.isLong(getNumero())) {
			addRecoverableError(BaseError.MSG_FORMATO_CAMPO_NUMERICO_INVALIDO, ApmError.TELEFONOPANICO_NUMERO);
		}

		if (hasError()) {
			return false;
		}
		
		// Validaciones de unique
		UniqueMap uniqueMap = new UniqueMap();
		uniqueMap = new UniqueMap();
		uniqueMap.addString("numero");
		uniqueMap.addEntity("area");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.TELEFONOPANICO_NUMERO);			
		}

		if (hasError()) {
			return false;
		}

		return true;
	}
}