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
package ar.gov.rosario.gait.pro.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import coop.tecso.demoda.iface.helper.StringUtil;

public class FileCorridaVO extends GaitBussImageModel {

	private static final long serialVersionUID = 0;

	private CorridaVO corrida = new CorridaVO();
	private Integer paso;  
	private String fileName;   
	private String nombre;  
	private String observacion;
	private Long ctdRegistros;
	
	private String pasoView = "";

	// Constructores
	public FileCorridaVO(){
		super();
	}

	// Getters Y Setters
	public CorridaVO getCorrida() {
		return corrida;
	}
	public void setCorrida(CorridaVO corrida) {
		this.corrida = corrida;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public Integer getPaso() {
		return paso;
	}
	public void setPaso(Integer paso) {
		this.paso = paso;
		this.pasoView = paso.toString();
	}

	public String getPasoView() {
		return pasoView;
	}
	public void setPasoView(String pasoView) {
		this.pasoView = pasoView;
	}

	public Long getCtdRegistros() {
		return ctdRegistros;
	}
	
	public void setCtdRegistros(Long ctdRegistros) {
		this.ctdRegistros = ctdRegistros;
	}
	
	public String getCtdRegistrosView() {
		return StringUtil.formatLong(ctdRegistros);
	}


	public boolean getEsPdf(){
		int index = this.getFileName().lastIndexOf(".");
		if(index >= 0 && ".PDF".equals(this.getFileName().substring(index).toUpperCase()))
			return true;
		return false;
	}

	public boolean getEsPlanilla(){
		int index = this.getFileName().lastIndexOf(".");
		if(index >= 0 && ".CSV".equals(this.getFileName().substring(index).toUpperCase()))
			return true;
		return false;
	}
	
	public boolean getEsTxt(){
		int index = this.getFileName().lastIndexOf(".");
		if(index >= 0 && ".TXT".equals(this.getFileName().substring(index).toUpperCase()))
			return true;
		return false;
	}
	
	public boolean getNoTieneExtension(){
		int index = this.getFileName().lastIndexOf(".");
		if (index < 0){
			return true;
		}
		
		String extension = this.getFileName().substring(index).toUpperCase();
		if (extension.length() == 0){
			return true;
		}
		return false;
	}

}
