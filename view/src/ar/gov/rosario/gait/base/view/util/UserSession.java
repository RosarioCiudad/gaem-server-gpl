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
package ar.gov.rosario.gait.base.view.util;

import coop.tecso.demoda.iface.model.DemodaUserSession;

public class UserSession extends DemodaUserSession {
	private static final long serialVersionUID = -1597116720963043531L;
	
	private String idMenuSession="1";
	
	private String idRecurso;

	//private boolean canAccessSolicitudes = false;
	private boolean canAccessSolEmitidasMenu = false;
	private boolean canAccessSolPendMenu = false;
	
	public String getIdMenuSession() {
		return idMenuSession;
	}

	public void setIdMenuSession(String idMenuSession) {
		this.idMenuSession = idMenuSession;
	}


	public boolean getCanAccessSolEmitidasMenu() {
		return canAccessSolEmitidasMenu;
	}

	public void setCanAccessSolEmitidasMenu(boolean canAccessSolEmitidasMenu) {
		this.canAccessSolEmitidasMenu = canAccessSolEmitidasMenu;
	}

	public boolean getCanAccessSolPendMenu() {
		return canAccessSolPendMenu;
	}

	public void setCanAccessSolPendMenu(boolean canAccessSolPendMenu) {
		this.canAccessSolPendMenu = canAccessSolPendMenu;
	}

	/**
	 * @return the idRecurso
	 */
	public String getIdRecurso() {
		return idRecurso;
	}

	/**
	 * @param idRecurso the idRecurso to set
	 */
	public void setIdRecurso(String idRecurso) {
		this.idRecurso = idRecurso;
	}

	
	
}