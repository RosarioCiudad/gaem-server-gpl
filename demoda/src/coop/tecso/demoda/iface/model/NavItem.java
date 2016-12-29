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



public class NavItem  {
	
	private static final long serialVersionUID = -1L;
	private Long selectedId = 0L;
	private Long auxId = 0L;
	private String accion = "";
	private String metodo = "";

	public NavItem(String accion, String metodo, Long selectedId) {
		this.selectedId = selectedId;
		this.accion = accion;
		this.metodo = metodo;
	}

	public Long getSelectedId() {
		return this.selectedId;
	}
	public void setSelectedId(Long selectedId) {
		this.selectedId = selectedId;
	}

	public Long getAuxId() {
		return this.auxId;
	}
	public void setAuxId(Long auxId) {
		this.auxId = auxId;
	}

	public String getAccion() {
		return this.accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getMetodo() {
		return this.metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

}
