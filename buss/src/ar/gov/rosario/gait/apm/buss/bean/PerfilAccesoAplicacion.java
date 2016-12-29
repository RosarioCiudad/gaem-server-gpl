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

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.model.Estado;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "apm_perfilAccesoAplicacion")
public class PerfilAccesoAplicacion extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idPerfilAcceso")
	private PerfilAcceso perfilAcceso;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idAplicacion")
	private Aplicacion aplicacion;

	// Constructores
	public PerfilAccesoAplicacion() {
		super();
	}

	public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public PerfilAcceso getPerfilAcceso() {
		return perfilAcceso;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public void setPerfilAcceso(PerfilAcceso perfilAcceso) {
		this.perfilAcceso = perfilAcceso;
	}	


	// Metodos de Clase
	public static PerfilAccesoAplicacion getById(Long id) {
		return (PerfilAccesoAplicacion) ApmDAOFactory.getPerfilAccesoAplicacionDAO().getById(id);
	}

	public static PerfilAccesoAplicacion getByIdNull(Long id) {
		return (PerfilAccesoAplicacion) ApmDAOFactory.getPerfilAccesoAplicacionDAO().getByIdNull(id);
	}

	public static List<PerfilAccesoAplicacion> getList() {
		return (ArrayList<PerfilAccesoAplicacion>) ApmDAOFactory.getPerfilAccesoAplicacionDAO().getList();
	}

	public static List<PerfilAccesoAplicacion> getListActivos() {			
		return (ArrayList<PerfilAccesoAplicacion>) ApmDAOFactory.getPerfilAccesoAplicacionDAO().getListActiva();
	}


	// Getters y setters

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
		//limpiamos la lista de errores
		clearError();

		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {

		//	Validaciones        
		if (getAplicacion() == null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.APLICACION_LABEL);
		}

		if (getPerfilAcceso() == null){
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.PERFILACCESO_LABEL);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}

	// Metodos de negocio

	/**
	 * Activa el PerfilAccesoAplicacion. Previamente valida la activacion. 
	 *
	 */
	public void activar(){
		if(!this.validateActivar()){
			return;
		}
		this.setEstado(Estado.ACTIVO.getId());
		ApmDAOFactory.getPerfilAccesoAplicacionDAO().update(this);
	}

	/**
	 * Desactiva el PerfilAccesoAplicacion. Previamente valida la desactivacion. 
	 *
	 */
	public void desactivar(){
		if(!this.validateDesactivar()){
			return;
		}
		this.setEstado(Estado.INACTIVO.getId());
		ApmDAOFactory.getPerfilAccesoAplicacionDAO().update(this);
	}

	/**
	 * Valida la activacion del PerfilAccesoAplicacion
	 * @return boolean
	 */
	private boolean validateActivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}

	/**
	 * Valida la desactivacion del PerfilAccesoAplicacion
	 * @return boolean
	 */
	private boolean validateDesactivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}
}