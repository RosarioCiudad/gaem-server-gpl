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

import java.util.ArrayList;
import java.util.List;

import coop.tecso.demoda.iface.model.AdapterModel;
import coop.tecso.swe.iface.model.AccModAplVO;
import coop.tecso.swe.iface.model.ItemMenuVO;

public class MenuAdapter extends AdapterModel {
	static public String NAME = "gaitMenuAdapter";
	
	// Se utilizan para establecer el menu activo de la barra del menu, el valor se guarda en la session 	
	// Estos valores se utilizan en la JSP.
	public static String ID_MENU_PPAL = "1";
	public static String ID_MENU_SOLPENDIENTES = "2";
	public static String ID_MENU_SOLEMITIDAS = "3";		
	
	private List<ItemMenuVO> listItemMenuNivel1 = new ArrayList<ItemMenuVO>();
	private List<ItemMenuVO> listItemMenuNivel2 = new ArrayList<ItemMenuVO>();
	private List<ItemMenuVO> listItemMenuNivel3 = new ArrayList<ItemMenuVO>();
	
	private Long idItemMenuNivel1 = new Long(0); // id del ItemMenu seleccionado en nivel 1
	private Long idItemMenuNivel2 = new Long(0); // id del ItemMenu seleccionado en nivel 2
	private Long idItemMenuNivel3 = new Long(0); // id del ItemMenu seleccionado en nivel 3
	private Long idAccionModulo = new Long(0);
	private String tituloNivel2 = ""; // titulo del Menu de Nivel 2: equivale a Titulo de item seleccionado en nivel 1
	private String tituloNivel3 = ""; // titulo del Menu de Nivel 3: equivale a Titulo de item seleccionado en nivel 2
	
	public MenuAdapter(){
		super("");	
	}

	public Long getIdItemMenuNivel1() {
		return idItemMenuNivel1;
	}
	public void setIdItemMenuNivel1(Long idItemMenuNivel1) {
		this.idItemMenuNivel1 = idItemMenuNivel1;
	}
	public Long getIdItemMenuNivel2() {
		return idItemMenuNivel2;
	}
	public void setIdItemMenuNivel2(Long idItemMenuNivel2) {
		this.idItemMenuNivel2 = idItemMenuNivel2;
	}
	public Long getIdItemMenuNivel3() {
		return idItemMenuNivel3;
	}
	public void setIdItemMenuNivel3(Long idItemMenuNivel3) {
		this.idItemMenuNivel3 = idItemMenuNivel3;
	}
	public List<ItemMenuVO> getListItemMenuNivel1() {
		return listItemMenuNivel1;
	}
	public void setListItemMenuNivel1(List<ItemMenuVO> listItemMenuNivel1) {
		this.listItemMenuNivel1 = listItemMenuNivel1;
	}
	public List<ItemMenuVO> getListItemMenuNivel2() {
		return listItemMenuNivel2;
	}
	public void setListItemMenuNivel2(List<ItemMenuVO> listItemMenuNivel2) {
		this.listItemMenuNivel2 = listItemMenuNivel2;
	}
	public List<ItemMenuVO> getListItemMenuNivel3() {
		return listItemMenuNivel3;
	}
	public void setListItemMenuNivel3(List<ItemMenuVO> listItemMenuNivel3) {
		this.listItemMenuNivel3 = listItemMenuNivel3;
	}

	public Long getIdAccionModulo() {
		return idAccionModulo;
	}

	public void setIdAccionModulo(Long idAccionModulo) {
		this.idAccionModulo = idAccionModulo;
	}
		
	public String getTituloNivel2() {
		return this.tituloNivel2;
	}

	public void setTituloNivel2(String tituloNivel2) {
		this.tituloNivel2 = tituloNivel2;
	}

	public String getTituloNivel3() {
		return this.tituloNivel3;
	}

	public void setTituloNivel3(String tituloNivel3) {
		this.tituloNivel3 = tituloNivel3;
	}

	public AccModAplVO findAccionModulo(Long idAccionModulo) {
		// buscamos idAccionModulo en nivel3
		for (ItemMenuVO item : this.listItemMenuNivel3) {
			AccModAplVO accionModulo = item.getAccModApl();
			if (accionModulo != null && accionModulo.getId().equals(idAccionModulo)) {
				return accionModulo;
			}
		}

		// buscamos idAccionModulo en nivel2
		for (ItemMenuVO item : this.listItemMenuNivel2) {
			AccModAplVO accionModulo = item.getAccModApl();
			if (accionModulo != null && accionModulo.getId().equals(idAccionModulo)) {
				return accionModulo;
			}
		}

		// buscamos idAccionModulo en nivel1
		for (ItemMenuVO item : this.listItemMenuNivel1) {
			AccModAplVO accionModulo = item.getAccModApl();
			if (accionModulo != null && accionModulo.getId().equals(idAccionModulo)) {
				return accionModulo;
			}
		}
		
		return null;
	}

    public ItemMenuVO findItemMenu(Long id) {
		// buscamos id en nivel3
		for (ItemMenuVO item : this.listItemMenuNivel3) {
			if (item.getId().equals(id)) {
				return item;
			}
		}
		// buscamos id en nivel2
		for (ItemMenuVO item : this.listItemMenuNivel2) {
			if (item.getId().equals(id)) {
				return item;
			}
		}
		// buscamos id en nivel1
		for (ItemMenuVO item : this.listItemMenuNivel1) {
			if (item.getId().equals(id)) {
				return item;
			}
		}
		
		return null;
	}

}
