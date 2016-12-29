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
@Table(name = "apm_aplicacionPerfilParametro")
public class AplicacionPerfilParametro extends VersionableBO {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
    @JoinColumn(name = "idAplicacionPerfil")
	private AplicacionPerfil aplicacionPerfil;
	
	@Column(nullable = false)
	private String codigo;
	
	@Column
	private String descripcion;
	
	@Column(nullable = false)
	private String valor;

	
	
	// Metodos de Clase
		public static AplicacionPerfilParametro getById(Long id) {
			return (AplicacionPerfilParametro) ApmDAOFactory.getAplicacionPerfilParametroDAO().getById(id);
		}

		public static AplicacionPerfilParametro getByIdNull(Long id) {
			return (AplicacionPerfilParametro) ApmDAOFactory.getAplicacionPerfilParametroDAO().getByIdNull(id);
		}

		public static List<AplicacionPerfilParametro> getList() {
			return (ArrayList<AplicacionPerfilParametro>) ApmDAOFactory.getAplicacionPerfilParametroDAO().getList();
		}

		public static List<AplicacionPerfilParametro> getListActivos() {
			return (ArrayList<AplicacionPerfilParametro>) ApmDAOFactory.getAplicacionPerfilParametroDAO().getListActiva();
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
			if (StringUtil.isNullOrEmpty(getCodigo().toString())) {
				addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
						ApmError.APLICACIONPERFILPARAMETRO_CODIGO);
			}
			if (StringUtil.isNullOrEmpty(getDescripcion().toString())) {
				addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
						ApmError.APLICACIONPERFILPARAMETRO_DESCRIPCION);
			}
			if (StringUtil.isNullOrEmpty(getValor().toString())) {
				addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
						ApmError.APLICACIONPERFILPARAMETRO_VALOR);
			}

			if (hasError()) {
				return false;
			}



			return true;
		}
	
	
	
	
	
	
	
	
	
	
	/**
	 * @return the aplicacionPerfil
	 */
	public AplicacionPerfil getAplicacionPerfil() {
		return aplicacionPerfil;
	}

	/**
	 * @param aplicacionPerfil the aplicacionPerfil to set
	 */
	public void setAplicacionPerfil(AplicacionPerfil aplicacionPerfil) {
		this.aplicacionPerfil = aplicacionPerfil;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}


	
	
	

}