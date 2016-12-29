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
package ar.gov.rosario.gait.seg.iface.util;

import coop.tecso.demoda.iface.error.DemodaError;


/**
 * En esta clase se definen las descripciones de los errores que estas asociaos a los VO.
 * 
 * @author Tecso Coop. Ltda.
 * 
 */
public class SegError extends DemodaError {
	// Use Codigos desde 17000 hasta 17999
    // static public String XXXXXX_XXXX_XXX   = addError(17000, "seg.xxxxxx");

	// ---> UsuarioVO (de swe)
	public static final String USUARIO_NEWPASS_LABEL       = addError(0, "seg.changePasswordForm.newPassword.label");
	public static final String USUARIO_NEWPASSREENTRY_LABEL  = addError(0, "seg.changePasswordForm.newPasswordReentry.label");
	public static final String MSG_NO_COINICEN_ERROR  = addError(0, "seg.changePasswordForm.msgNoCoinciden.error");
	public static final String MSG_SERVICE_ERROR  = addError(0, "seg.changePasswordForm.service.error");
	// <--- UsuarioVO (de swe)

	
	// --> UsuarioGAIT
	public static final String USUARIOGAIT_LABEL       = addError(17000, "seg.usuarioGait.label");
	public static final String USUARIOGAIT_USUARIOGAIT = addError(17001, "seg.usuarioGait.usuarioGAIT.label");
	public static final String NO_EXISTE_USUARIOGAIT = addError(17002, "seg.usuarioGait.noExisteUsuario");

	public static final String NO_LOGIN_INSTANCIA_WEB = addError(17003, "seg.loginGait.noLoginInstanciaWeb");
	public static final String NO_LOGIN_INSTANCIA_INTRA = addError(17004, "seg.loginGait.noLoginInstanciaIntra");
	public static final String NO_PERMITEWEB = addError(17005, "seg.loginGait.noPermiteWeb");

	// <-- UsuarioGAIT

	// --> Oficina
	public static final String OFICINA_LABEL       = addError(17100, "seg.oficina.label");
	public static final String OFICINA_DESOFICINA  	 = addError(17101,"seg.oficina.desOficina.label");
	// <-- Oficina

}

