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

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import ar.gov.rosario.gait.base.iface.model.GaitPageModel;


/**
 * SearchPage del Aplicacion PErfil
 * 
 * @author Tecso
 * 
 */
public class AplicacionPerfilSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionPerfilSearchPageVO";
										
	private AplicacionPerfilVO aplicacionPerfil = new AplicacionPerfilVO();
	
	private List<AplicacionVO> listAplicacion = new ArrayList<AplicacionVO>();
	
    private Boolean clonarBussEnabled 		     = true; 

	// Constructores
	public AplicacionPerfilSearchPage() {
		super(ApmSecurityConstants.ABM_APLICACIONPERFIL);
	}

	// Getters y Setters

	public String getName() {
		return NAME;
	}

	/**
	 * @return the aplicacionPerfil
	 */
	public AplicacionPerfilVO getAplicacionPerfil() {
		return aplicacionPerfil;
	}

	/**
	 * @param aplicacionPerfil the aplicacionPerfil to set
	 */
	public void setAplicacionPerfil(AplicacionPerfilVO aplicacionPerfil) {
		this.aplicacionPerfil = aplicacionPerfil;
	}

	public List<AplicacionVO> getListAplicacion() {
		return listAplicacion;
	}

	public void setListAplicacion(List<AplicacionVO> listAplicacion) {
		this.listAplicacion = listAplicacion;
	}

	
	public Boolean getClonarBussEnabled() {
		return clonarBussEnabled;
	}

	public void setClonarBussEnabled(Boolean clonarBussEnabled) {
		this.clonarBussEnabled = clonarBussEnabled;
	}

	public String getClonarEnabled() {
		return GaitBussImageModel.hasEnabledFlag(this.getClonarBussEnabled(), ACCION_CLONAR, METODO_CLONAR);
	}

}
