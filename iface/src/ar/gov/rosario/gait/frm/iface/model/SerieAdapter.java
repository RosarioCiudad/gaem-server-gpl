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
package ar.gov.rosario.gait.frm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitAdapterModel;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;

public class SerieAdapter extends GaitAdapterModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "serieAdapterVO";

	private SerieVO serie = new SerieVO();
	

	// Constructores
	public SerieAdapter() {
		super(FrmSecurityConstants.ABM_SERIE);
	}

	// Getters y Setters
	public SerieVO getSerie() {
		return serie;
	}

	public void setSerie(SerieVO serie) {
		this.serie = serie;
	}

	public String getName() {
		return NAME;
	}


}