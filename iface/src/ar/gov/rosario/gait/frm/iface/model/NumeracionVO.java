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

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import coop.tecso.demoda.iface.helper.StringUtil;

public class NumeracionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "numeracionVO";
	
	private TipoFormularioVO tipoFormulario = new TipoFormularioVO();

	private SerieVO serie = new SerieVO();

	private Integer valorDesde;

	private Integer valorHasta;

	private Integer valorRestante;

	// Getters y setters
	public TipoFormularioVO getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormularioVO tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	public SerieVO getSerie() {
		return serie;
	}

	public void setSerie(SerieVO serie) {
		this.serie = serie;
	}

	public Integer getValorDesde() {
		return valorDesde;
	}

	public void setValorDesde(Integer valorDesde) {
		this.valorDesde = valorDesde;
	}

	public Integer getValorHasta() {
		return valorHasta;
	}

	public void setValorHasta(Integer valorHasta) {
		this.valorHasta = valorHasta;
	}

	public Integer getValorRestante() {
		return valorRestante;
	}

	public void setValorRestante(Integer valorRestante) {
		this.valorRestante = valorRestante;
	}
	
	public String getValorDesdeView() {
		return StringUtil.formatInteger(valorDesde);
	}
	
	public String getValorHastaView() {
		return StringUtil.formatInteger(valorHasta);
	}
}