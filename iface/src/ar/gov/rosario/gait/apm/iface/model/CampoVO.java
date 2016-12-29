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
import coop.tecso.demoda.iface.model.Tratamiento;


public class CampoVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "campoVO";

	private Tratamiento tratamiento = Tratamiento.OpcionTodo;
	private String etiqueta = "";	
	private String valorDefault = "";
	private String tablaBusqueda = "";
	private SiNo obligatorio = SiNo.OpcionTodo;
	private SiNo soloLectura = SiNo.OpcionTodo;
	private String mascaraVisual = "";
	private List<CampoValorVO> listCampoValor = new ArrayList<CampoValorVO>(); 
	private String codigo;

	public CampoVO() {
		super();
	}

	public CampoVO(int id) {
		super();
		setId(new Long(id));
	}
	
	public CampoVO(int id, String descripcion) {
		super();
		setId(new Long(id));
		setEtiqueta(descripcion);
	}

	/**
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * @param etiqueta the etiqueta to set
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * @return the valorDefault
	 */
	public String getValorDefault() {
		return valorDefault;
	}

	/**
	 * @param valorDefault the valorDefault to set
	 */
	public void setValorDefault(String valorDefault) {
		this.valorDefault = valorDefault;
	}

	/**
	 * @return the tablaBusqueda
	 */
	public String getTablaBusqueda() {
		return tablaBusqueda;
	}

	/**
	 * @param tablaBusqueda the tablaBusqueda to set
	 */
	public void setTablaBusqueda(String tablaBusqueda) {
		this.tablaBusqueda = tablaBusqueda;
	}

	/**
	 * @return the obligatorio
	 */
	public SiNo getObligatorio() {
		return obligatorio;
	}

	/**
	 * @param obligatorio the obligatorio to set
	 */
	public void setObligatorio(SiNo obligatorio) {
		this.obligatorio = obligatorio;
	}

	/**
	 * @return the soloLectura
	 */
	public SiNo getSoloLectura() {
		return soloLectura;
	}

	/**
	 * @param soloLectura the soloLectura to set
	 */
	public void setSoloLectura(SiNo soloLectura) {
		this.soloLectura = soloLectura;
	}

	/**
	 * @return the tratamiento
	 */
	public Tratamiento getTratamiento() {
		return tratamiento;
	}

	/**
	 * @param tratamiento the tratamiento to set
	 */
	public void setTratamiento(Tratamiento tratamiento) {
		this.tratamiento = tratamiento;
	}

	public List<CampoValorVO> getListCampoValor() {
		return listCampoValor;
	}

	public void setListCampoValor(List<CampoValorVO> listCampoValor) {
		this.listCampoValor = listCampoValor;
	}

	public String getMascaraVisual() {
		return mascaraVisual;
	}

	public void setMascaraVisual(String mascaraVisual) {
		this.mascaraVisual = mascaraVisual;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}