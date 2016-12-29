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
import coop.tecso.demoda.buss.bean.VersionableBO;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "apm_perfilAccesoUsuario")
public class PerfilAccesoUsuario extends VersionableBO {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
    @JoinColumn(name = "idPerfilAcceso")
	private PerfilAcceso perfilAcceso;

	@ManyToOne(optional = false)  
    @JoinColumn(name = "idUsuario")
	private UsuarioApm usuarioAPM;
	
	// Constructores
	public PerfilAccesoUsuario() {
		super();
	}

	public PerfilAcceso getPerfilAcceso() {
		return perfilAcceso;
	}

	public UsuarioApm getUsuarioAPM() {
		return usuarioAPM;
	}

	public void setPerfilAcceso(PerfilAcceso perfilAcceso) {
		this.perfilAcceso = perfilAcceso;
	}

	public void setUsuarioAPM(UsuarioApm usuarioAPM) {
		this.usuarioAPM = usuarioAPM;
	}
	
	
	
	// Metodos de Clase
	public static PerfilAccesoUsuario getById(Long id) {
		return (PerfilAccesoUsuario) ApmDAOFactory.getPerfilAccesoUsuarioDAO().getById(id);
	}
	
	public static PerfilAccesoUsuario getByIdNull(Long id) {
		return (PerfilAccesoUsuario) ApmDAOFactory.getPerfilAccesoUsuarioDAO().getByIdNull(id);
	}
	
	public static List<PerfilAccesoUsuario> getList() {
		return (ArrayList<PerfilAccesoUsuario>) ApmDAOFactory.getPerfilAccesoUsuarioDAO().getList();
	}
	
	public static List<PerfilAccesoUsuario> getListActivos() {			
		return ApmDAOFactory.getPerfilAccesoUsuarioDAO().getListActiva();
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
		//limpiamos la lista de errores
		clearError();
	
		
		if (hasError()) {
			return false;
		}

		return true;
	}
	
	private boolean validate() throws Exception {
		
		
		
		if (hasError()) {
			return false;
		}
		
	
		
		
		return true;
	}

}