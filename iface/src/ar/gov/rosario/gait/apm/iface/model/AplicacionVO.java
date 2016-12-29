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

public class AplicacionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionVO";

	private String codigo;
	private String descripcion;
	private String packageName;
	private String className;
	
	private List<AplicacionParametroVO> listAplicacionParametro = new ArrayList<AplicacionParametroVO>(); 
	private List<AplicacionTablaVO> listAplicacionTabla = new ArrayList<AplicacionTablaVO>(); 
	private List<AplicacionBinarioVersionVO> listAplicacionBinarioVersion = new ArrayList<AplicacionBinarioVersionVO>(); 
	private List<AplicacionPerfilVO> listAplicacionPerfil = new ArrayList<AplicacionPerfilVO>(); 
	
	// Constructores
	public AplicacionVO() {
		super();
	}
	
	// Constructor para utilizar este VO en un combo como valor SELECCIONAR, TODOS, etc.
	public AplicacionVO(int id, String desc) {
		super();
		setId(new Long(id));
		setDescripcion(desc);
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<AplicacionParametroVO> getListAplicacionParametro() {
		return listAplicacionParametro;
	}

	public void setListAplicacionParametro(
			List<AplicacionParametroVO> listAplicacionParametro) {
		this.listAplicacionParametro = listAplicacionParametro;
	}

	public List<AplicacionTablaVO> getListAplicacionTabla() {
		return listAplicacionTabla;
	}

	public void setListAplicacionTabla(List<AplicacionTablaVO> listAplicacionTabla) {
		this.listAplicacionTabla = listAplicacionTabla;
	}

	public List<AplicacionBinarioVersionVO> getListAplicacionBinarioVersion() {
		return listAplicacionBinarioVersion;
	}

	public void setListAplicacionBinarioVersion(
			List<AplicacionBinarioVersionVO> listAplicacionBinarioVersion) {
		this.listAplicacionBinarioVersion = listAplicacionBinarioVersion;
	}

	public List<AplicacionPerfilVO> getListAplicacionPerfil() {
		return listAplicacionPerfil;
	}

	public void setListAplicacionPerfil(
			List<AplicacionPerfilVO> listAplicacionPerfil) {
		this.listAplicacionPerfil = listAplicacionPerfil;
	}

}