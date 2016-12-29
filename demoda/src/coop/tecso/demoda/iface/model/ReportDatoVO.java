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

package coop.tecso.demoda.iface.model;





/**
 *  Representa un dato de un reporte.
 *   
 * @author Tecso
 *
 */
public class ReportDatoVO {
	
    // nombre que lo identifica
	private String nombre = "";  
	
	private String metodoEjecutar = null;// soporta notacion con puntos;

	private Integer width = new Integer(0);
	
	private Class claseEnumeracion = null ;
	
	public ReportDatoVO(){
	}
	
	public ReportDatoVO(String nombre, String metodoEjecutar) {
		this.nombre = nombre;
		this.metodoEjecutar = metodoEjecutar;
	}
	
	public ReportDatoVO(String nombre, String metodoEjecutar, Integer width) {
		this.nombre = nombre;
		this.metodoEjecutar = metodoEjecutar;
		this.width = width;
	}

	
	// Getters Y Setters
	public String getMetodoEjecutar() {
		return metodoEjecutar;
	}
	public void setMetodoEjecutar(String metodoEjecutar) {
		this.metodoEjecutar = metodoEjecutar;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}

	public Class getClaseEnumeracion() {
		return claseEnumeracion;
	}

	public void setClaseEnumeracion(Class claseEnumeracion) {
		this.claseEnumeracion = claseEnumeracion;
	}
	
	
}
