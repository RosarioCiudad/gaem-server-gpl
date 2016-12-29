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
package ar.gov.rosario.gait.apm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import coop.tecso.demoda.iface.model.SiNo;

public class AplicacionPerfilSeccionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionPerfilSeccionVO";

	private AplicacionPerfilVO aplicacionPerfil = new AplicacionPerfilVO();

	private SeccionVO seccion = new SeccionVO();
	
	private Integer orden;

	private SiNo noDesplegar = SiNo.NO;
	private SiNo opcional = SiNo.NO;
	private SiNo permiteImpresion = SiNo.SI;
	
	private List<AplPerfilSeccionCampoVO> listAplPerfilSeccionCampo = new ArrayList<AplPerfilSeccionCampoVO>();
  
	

	// Constructores
	public AplicacionPerfilSeccionVO() {
		super();
	}


	// Getters y Setters
	public AplicacionPerfilVO getAplicacionPerfil() {
		return aplicacionPerfil;
	}

	public void setAplicacionPerfil(AplicacionPerfilVO aplicacionPerfil) {
		this.aplicacionPerfil = aplicacionPerfil;
	}

	public SeccionVO getSeccion() {
		return seccion;
	}

	public void setSeccion(SeccionVO seccion) {
		this.seccion = seccion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public SiNo getNoDesplegar() {
		return noDesplegar;
	}

	public void setNoDesplegar(SiNo noDesplegar) {
		this.noDesplegar = noDesplegar;
	}

	public SiNo getOpcional() {
		return opcional;
	}

	public void setOpcional(SiNo opcional) {
		this.opcional = opcional;
	}

	

	public SiNo getPermiteImpresion() {
		return permiteImpresion;
	}


	public void setPermiteImpresion(SiNo permiteImpresion) {
		this.permiteImpresion = permiteImpresion;
	}


	/**
	 * @return the listAplPerfilSeccionCampo
	 */
	public List<AplPerfilSeccionCampoVO> getListAplPerfilSeccionCampo() {
		return listAplPerfilSeccionCampo;
	}


	/**
	 * @param listAplPerfilSeccionCampo the listAplPerfilSeccionCampo to set
	 */
	public void setListAplPerfilSeccionCampo(
			List<AplPerfilSeccionCampoVO> listAplPerfilSeccionCampo) {
		this.listAplPerfilSeccionCampo = listAplPerfilSeccionCampo;
	}

}