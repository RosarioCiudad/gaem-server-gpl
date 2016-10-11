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