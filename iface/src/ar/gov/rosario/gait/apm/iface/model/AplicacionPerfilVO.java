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
import ar.gov.rosario.gait.def.iface.model.DireccionAplicacionPerfilVO;
import ar.gov.rosario.gait.frm.iface.model.TipoFormularioVO;

public class AplicacionPerfilVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionPerfilVO";

	private AplicacionVO aplicacion = new AplicacionVO();
	private TipoFormularioVO tipoFormulario = new TipoFormularioVO();
	private String descripcion; 
	private List<AplicacionPerfilSeccionVO> listAplicacionPerfilSeccion = new ArrayList<>();
	private List<AplicacionPerfilParametroVO> listAplicacionPerfilParametro = new ArrayList<>();
	private List<DireccionAplicacionPerfilVO> listDireccionAplicacionPerfil= new ArrayList<>();
	
    private Boolean clonarBussEnabled = true; 

	// Constructores
	public AplicacionPerfilVO() {
		super();
	}

	public AplicacionPerfilVO(int id) {
		super();
		setId(new Long(id));
	}
	
	public AplicacionPerfilVO(int id, String descripcion) {
		super();
		setId(new Long(id));
		setDescripcion(descripcion);
	}

	// Getters y Setters
	public AplicacionVO getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionVO aplicacion) {
		this.aplicacion = aplicacion;
	}
	
	public TipoFormularioVO getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormularioVO tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public List<AplicacionPerfilSeccionVO> getListAplicacionPerfilSeccion() {
		return listAplicacionPerfilSeccion;
	}

	public void setListApPerfilSeccion(List<AplicacionPerfilSeccionVO> listAplicacionPerfilSeccion) {
		this.listAplicacionPerfilSeccion = listAplicacionPerfilSeccion;
	}

	public List<AplicacionPerfilParametroVO> getListAplicacionPerfilParametro() {
		return listAplicacionPerfilParametro;
	}

	public void setListAplicacionPerfilParametro(
			List<AplicacionPerfilParametroVO> listAplicacionPerfilParametro) {
		this.listAplicacionPerfilParametro = listAplicacionPerfilParametro;
	}

	public List<DireccionAplicacionPerfilVO> getListDireccionAplicacionPerfil() {
		return listDireccionAplicacionPerfil;
	}

	public void setListDireccionAplicacionPerfil(
			List<DireccionAplicacionPerfilVO> listDireccionAplicacionPerfil) {
		this.listDireccionAplicacionPerfil = listDireccionAplicacionPerfil;
	}
	
	public Boolean getClonarBussEnabled() {
		return clonarBussEnabled;
	}

	public void setClonarBussEnabled(Boolean clonarBussEnabled) {
		this.clonarBussEnabled = clonarBussEnabled;
	}

	public String getClonarEnabled() {
		return getClonarBussEnabled()?ENABLED:DISABLED;
	}
}