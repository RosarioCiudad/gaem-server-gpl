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
import java.util.Date;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;


/**
 * Adapter del Aplicacion PErfil
 * 
 * @author tecso
 */

public class AplicacionBinarioVersionAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionBinarioVersionAdapterVO";

	private Date fecha; 
	private AplicacionBinarioVersionVO aplicacionBinarioVersion = new AplicacionBinarioVersionVO();
	private List<AplicacionVO> listAplicacion = new ArrayList<>();
	private List<AplicacionTipoBinarioVO> listAplicacionTipoBinario = new ArrayList<>();
	
	// Constructores
	public AplicacionBinarioVersionAdapter() {
		super(ApmSecurityConstants.ABM_APLICACIONBINARIOVERSION);
	}

	// Getters y Setters

	

	public String getName() {
		return NAME;
	}



	public AplicacionBinarioVersionVO getAplicacionBinarioVersion() {
		return aplicacionBinarioVersion;
	}

	public void setAplicacionBinarioVersion(AplicacionBinarioVersionVO aplicacionBinarioVersion) {
		this.aplicacionBinarioVersion = aplicacionBinarioVersion;
	}

	/**
	 * @return the listAplicacionTipoBinario
	 */
	public List<AplicacionTipoBinarioVO> getListAplicacionTipoBinario() {
		return listAplicacionTipoBinario;
	}

	/**
	 * @param listAplicacionTipoBinario the listAplicacionTipoBinario to set
	 */
	public void setListAplicacionTipoBinario(
			List<AplicacionTipoBinarioVO> listAplicacionTipoBinario) {
		this.listAplicacionTipoBinario = listAplicacionTipoBinario;
	}

	public List<AplicacionVO> getListAplicacion() {
		return listAplicacion;
	}

	public void setListAplicacion(List<AplicacionVO> listAplicacion) {
		this.listAplicacion = listAplicacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}