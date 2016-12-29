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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.helper.StringUtil;


/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "apm_aplicacionParametro")
public class AplicacionParametro extends VersionableBO {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
    @JoinColumn(name = "idAplicacion")
	private Aplicacion aplicacion;
	
	@Column(nullable = false)
	private String codigo;
	
	@Column
	private String descripcion;
	
	@Column(nullable = false)
	private String valor;

	public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getValor() {
		return valor;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	// Metodos de Clase
	public static AplicacionParametro getById(Long id) {
		return (AplicacionParametro) ApmDAOFactory.getAplicacionParametroDAO().getById(id);
	}
	
	public static AplicacionParametro getByIdNull(Long id) {
		return (AplicacionParametro) ApmDAOFactory.getAplicacionParametroDAO().getByIdNull(id);
	}
	
	public static List<AplicacionParametro> getList() {
		return (ArrayList<AplicacionParametro>) ApmDAOFactory.getAplicacionParametroDAO().getList();
	}
	
	public static List<AplicacionParametro> getListActivos() {			
		return ApmDAOFactory.getAplicacionParametroDAO().getListActiva();
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

	public boolean validateDelete() throws Exception{
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
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					ApmError.APLICACIONPARAMETRO_DESCRIPCION);
		}
		// Validaciones
		if (StringUtil.isNullOrEmpty(getCodigo())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					ApmError.APLICACIONPARAMETRO_CODIGO);
		}
		// Validaciones
		if (StringUtil.isNullOrEmpty(getValor())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					ApmError.APLICACIONPARAMETRO_VALOR);
		}


		if (hasError()) {
			return false;
		}



		return true;
	}
}