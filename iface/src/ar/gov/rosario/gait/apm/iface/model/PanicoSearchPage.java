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
import ar.gov.rosario.gait.base.iface.model.GaitPageModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;

/**
 * SearchPage de Panico
 * 
 * @author Tecso
 *
 */
public class PanicoSearchPage extends GaitPageModel {

	private static final long serialVersionUID = 1L;
	public static final String NAME = "panicoSearchPageVO";
    
	private List<AreaVO> listArea = new ArrayList<>();
	private List<UsuarioApmVO> listUsuarioPanico = new ArrayList<>();
	private List<EstadoPanicoVO> listEstadoPanico = new ArrayList<>();
	private DispositivoMovilVO dispositivoMovil= new DispositivoMovilVO();
	private UsuarioApmVO usuarioPanico = new UsuarioApmVO();
	private PanicoVO panico = new PanicoVO();

	// Constructores
	public PanicoSearchPage() {       
       super(ApmSecurityConstants.ABM_SECCION);        
    }
	
	// Getters y Setters
	public String getName(){    
		return NAME;
	}
	public PanicoVO getPanico() {
		return panico;
	}
	public void setPanico(PanicoVO panico) {
		this.panico = panico;
	}
	public List<AreaVO> getListArea() {
		return listArea;
	}
	public void setListArea(List<AreaVO> listArea) {
		this.listArea = listArea;
	}
	public DispositivoMovilVO getDispositivoMovil() {
		return dispositivoMovil;
	}
	public void setDispositivoMovil(DispositivoMovilVO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}
	public List<UsuarioApmVO> getListUsuarioPanico() {
		return listUsuarioPanico;
	}
	public void setListUsuarioPanico(List<UsuarioApmVO> listUsuarioPanico) {
		this.listUsuarioPanico = listUsuarioPanico;
	}
	public UsuarioApmVO getUsuarioPanico() {
		return usuarioPanico;
	}
	public void setUsuarioPanico(UsuarioApmVO usuarioPanico) {
		this.usuarioPanico = usuarioPanico;
	}
	public List<EstadoPanicoVO> getListEstadoPanico() {
		return listEstadoPanico;
	}
	public void setListEstadoPanico(List<EstadoPanicoVO> listEstadoPanico) {
		this.listEstadoPanico = listEstadoPanico;
	}
	// View getters
}