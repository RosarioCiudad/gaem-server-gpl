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
package ar.gov.rosario.gait.seg.iface.model;

import java.util.Date;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.def.iface.model.DireccionVO;
import coop.tecso.demoda.iface.helper.DateUtil;


/**
 * Bean correspondiente a UsuarioGait
 * 
 * @author tecso
 */
public class UsuarioGaitVO extends GaitBussImageModel {
	
	private static final long serialVersionUID = 1L;
	
	private String usuarioGAIT;
	private Date fechaUltLogin;
	private DireccionVO direccion = new DireccionVO();
	private AreaVO area = new AreaVO();

	// Constructores
	public UsuarioGaitVO(){
		super();
	}

	public UsuarioGaitVO(Long id){
		super();
		setId(id);
	}

	// Getters y setters
		
	public UsuarioGaitVO(int id, String user) {
		super();
		setId(new Long(id));
		setUsuario(user);
		
	}
	
	public String getUsuarioGAIT() {
		return usuarioGAIT;
	}

	public void setUsuarioGAIT(String usuarioGAIT) {
		this.usuarioGAIT = usuarioGAIT;
	}

	public Date getFechaUltLogin() {
		return fechaUltLogin;
	}

	public void setFechaUltLogin(Date fechaUltLogin) {
		this.fechaUltLogin = fechaUltLogin;
	}

	public String getFechaUltLoginView() {
		return DateUtil.formatDate(fechaUltLogin, DateUtil.ddSMMSYYYY_MASK);
	}

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public AreaVO getArea() {
		return area;
	}

	public void setArea(AreaVO area) {
		this.area = area;
	}
}