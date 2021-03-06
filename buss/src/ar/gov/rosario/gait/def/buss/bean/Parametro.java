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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.def.buss.dao.DefDAOFactory;
import ar.gov.rosario.gait.def.iface.util.DefError;
import coop.tecso.demoda.buss.bean.BaseBO;
import coop.tecso.demoda.buss.dao.UniqueMap;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

/**
 * Bean correspondiente a Parametro
 * 
 * @author tecso
 */
@Entity
@Table(name = "def_parametro")
public class Parametro extends BaseBO {

	private static final long serialVersionUID = 1L;

	@Column
	private String codParam;

	@Column
	private String desParam;

	@Column
	private String valor;

	// Constructores
	public Parametro() {
		super();
	}

	// Metodos de Clase
	public static Parametro getById(Long id) {
		return (Parametro) DefDAOFactory.getParametroDAO().getById(id);
	}

	public static Parametro getByIdNull(Long id) {
		return (Parametro) DefDAOFactory.getParametroDAO().getByIdNull(id);
	}

	public static List<Parametro> getList() {
		return (ArrayList<Parametro>) DefDAOFactory.getParametroDAO().getList();
	}

	public static List<Parametro> getListActivos() {
		return DefDAOFactory.getParametroDAO().getListActiva();
	}

	// Getters y setters
	public String getCodParam() {
		return codParam;
	}

	public void setCodParam(String codParam) {
		this.codParam = codParam;
	}

	public String getDesParam() {
		return desParam;
	}

	public void setDesParam(String desParam) {
		this.desParam = desParam;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
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
		if (StringUtil.isNullOrEmpty(getCodParam())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					DefError.PARAMETRO_CODPARAM);
		}

		if (StringUtil.isNullOrEmpty(getDesParam())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					DefError.PARAMETRO_DESPARAM);
		}

		if (StringUtil.isNullOrEmpty(getValor())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					DefError.PARAMETRO_VALOR);
		}
		
		if (hasError()) {
			return false;
		}

		// Validaciones de unique
		UniqueMap uniqueMap = new UniqueMap();
		uniqueMap.addString("codParam");
		if (!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO,
					DefError.PARAMETRO_CODPARAM);
		}
		
		if (hasError()) {
			return false;
		}

		return true;
	}

	// Metodos de negocio
	
	public static Parametro getByCodigo(String codigo) throws Exception{
		return (Parametro) DefDAOFactory.getParametroDAO().getByCodigo(codigo);
	}

	/**
	 * Activa el Parametro. Previamente valida la activacion.
	 * 
	 */
	public void activar() {
		if (!this.validateActivar()) {
			return;
		}
		this.setEstado(Estado.ACTIVO.getId());
		DefDAOFactory.getParametroDAO().update(this);
	}

	/**
	 * Desactiva el Parametro. Previamente valida la desactivacion.
	 * 
	 */
	public void desactivar() {
		if (!this.validateDesactivar()) {
			return;
		}
		this.setEstado(Estado.INACTIVO.getId());
		DefDAOFactory.getParametroDAO().update(this);
	}

	/**
	 * Valida la activacion del Parametro
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
	 * Valida la desactivacion del Parametro
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
