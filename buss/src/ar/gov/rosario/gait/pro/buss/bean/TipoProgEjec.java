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
package ar.gov.rosario.gait.pro.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.pro.buss.dao.ProDAOFactory;
import ar.gov.rosario.gait.pro.iface.util.ProError;
import coop.tecso.demoda.buss.bean.BaseBO;
import coop.tecso.demoda.iface.helper.StringUtil;

/**
 * Bean correspondiente a TipoProgEjec
 * Representa los tipos de programas que se pueden utilizar para delegar la ejecucion de un proceso.
 * 
 * @author tecso
 */

@Entity
@Table(name = "pro_tipoProgEjec")
public class TipoProgEjec extends BaseBO {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "tipoProgEjec";

	@Column(name = "desTipoProgEjec")
	private String desTipoProgEjec;
	
	// Constructores
	public TipoProgEjec(){
		super();
	}
	
	// Getters y Setters
	public String getDesTipoProgEjec(){
		return desTipoProgEjec;
	}
	public void setDesTipoProgEjec(String desTipoProgEjec){
		this.desTipoProgEjec = desTipoProgEjec;
	}
	
	// Metodos de Clase
	public static TipoProgEjec getById(Long id) {
		return (TipoProgEjec) ProDAOFactory.getTipoProgEjecDAO().getById(id);  
	}
	
	public static TipoProgEjec getByIdNull(Long id) {
		return (TipoProgEjec) ProDAOFactory.getTipoProgEjecDAO().getByIdNull(id);
	}
	
	public static List<TipoProgEjec> getList() {
		return (List<TipoProgEjec>) ProDAOFactory.getTipoProgEjecDAO().getList();
	}
	
	public static List<TipoProgEjec> getListActivos() {			
		return ProDAOFactory.getTipoProgEjecDAO().getListActiva();
	}


	// Metodos de Instancia
	// Validaciones
	/**
	 * Valida la creacion
	 * @author
	 */
	public boolean validateCreate() throws Exception{
		//limpiamos la lista de errores
		clearError();
		
		this.validate();
		
		if (hasError()) {
			return false;
		}

				
		return !hasError();
	}
	/**
	 * Valida la actualizacion
	 * @author
	 */
	public boolean validateUpdate() throws Exception{
		//limpiamos la lista de errores
		clearError();
		
		this.validate();
	
		if (hasError()) {
			return false;
		}

			
		return !hasError();
	}

	private boolean validate() throws Exception{
		
		//limpiamos la lista de errores
		clearError();
		
		//UniqueMap uniqueMap = new UniqueMap();

		//Validaciones de Requeridos
		
		if (StringUtil.isNullOrEmpty(getDesTipoProgEjec())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ProError.TIPOPROGEJEC_DESTIPOPROGEJEC);
		}
		
		if (hasError()) {
			return false;
		}
		// Validaciones de Unicidad
		
		// Otras Validaciones
		
		// Valida que la Fecha Desde no sea mayor que la fecha Hasta
		
		
		return !hasError();
	}

	
	/**
	 * Valida la eliminacion
	 * @author
	 */
	public boolean validateDelete() {
		//limpiamos la lista de errores
		clearError();
		
		//Validaciones de VO

		if (hasError()) {
			return false;
		}

		// Validaciones de Negocio
		
		return true;
	}

	// Metodos de negocio
	

	

}
