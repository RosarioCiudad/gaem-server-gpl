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
package ar.gov.rosario.gait.def.iface.model;

import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

/**
 * Value Object de Direccion Apl Perfil
 * @author tecso
 *
 */
public class DireccionAplicacionPerfilVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "direccionAplicacionPerfilVO";
	
	private DireccionVO direccion = new DireccionVO();
	
	
	private AplicacionPerfilVO aplicacionPerfil = new AplicacionPerfilVO(); 
	
	// Buss Flags
	
	
	// View Constants
	
	
	// Constructores
	public DireccionAplicacionPerfilVO() {
		super();
	}
	
	// Constructor para utilizar este VO en un combo como valor SELECCIONAR, TODOS, etc.
	public DireccionAplicacionPerfilVO(int id, String desc) {
		super();
		setId(new Long(id));
		
	}

	
	
	// Getters y Setters

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public AplicacionPerfilVO getAplicacionPerfil() {
		return aplicacionPerfil;
	}

	public void setAplicacionPerfil(AplicacionPerfilVO aplicacionPerfil) {
		this.aplicacionPerfil = aplicacionPerfil;
	}
	

	
	// Buss flags getters y setters
	
	
	// View flags getters
	
	
	// View getters
}
