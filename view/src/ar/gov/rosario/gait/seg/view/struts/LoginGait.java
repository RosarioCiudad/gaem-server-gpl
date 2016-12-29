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
package ar.gov.rosario.gait.seg.view.struts;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.iface.model.GaitParam;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.seg.iface.model.UsuarioGaitVO;
import ar.gov.rosario.gait.seg.iface.service.SegServiceLocator;
import ar.gov.rosario.gait.seg.iface.util.SegError;
import ar.gov.rosario.gait.seg.view.util.SegConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Common;
import coop.tecso.demoda.iface.model.NavModel;
import coop.tecso.swe.SweServiceLocator;
import coop.tecso.swe.iface.model.SweUserSession;
import coop.tecso.swe.iface.model.UsuarioVO;

/**
 * Clase que implementa los metodos protegidos de login/logut del GAIT.
 * IMPORTANTE: TODA la logica de login esta concetrada en esta clase!!!. Y asi debe seguir siendo.
 * Esta clase es capaz de manjear los login anonimos, y no Anonimos
 * ya sea desde la Intranet o desde la Web
 * Las clases LoginActoin, y LoginSslAction exponen los metodos protegidos de esta
 * clase, segun sean metodos que quedan expuestos para ser usados via SSL o no
 * @author Tecso Coop. Ltda.
 *
 */
 public class LoginGait extends BaseDispatchAction {

	static private Logger log = Logger.getLogger((LoginGait.class));
	static public final int FROM_WEB_LOGIN = 1;
	static public final int FROM_INTRANET_LOGIN = 2;

	static public final String APP_CODE = "GAEM"; // Codigo de la app en SWE
	/**
	 * Loguea a un tipo en el gait.
	 * Crea todo lo correspondiente al user session y lo mente en el HttpSession
	 * @return common contenedor de errores. Es una instancia de SweUserSession
	*/
	protected Common sessionLogin(HttpServletRequest request, UsuarioVO userVO, int loginFrom) throws Exception {
		HttpSession session = request.getSession(true); 
		UserSession userSession = null;
		SweUserSession sweUserSession = null;
		Common ret = new Common();
		boolean esAnonimo = false;
		
		esAnonimo = BaseSecurityConstants.USR_ANONIMO.equals(userVO.getUsername());

		//Si se intenta un login NO Anonimo, Verificamos si el loginFrom es coherente con la instancia.
		if (!esAnonimo) {
			if (loginFrom == LoginGait.FROM_WEB_LOGIN && !GaitParam.isWebGait()) {
				ret.addRecoverableError(SegError.NO_LOGIN_INSTANCIA_WEB);
				return ret;
			} else if (loginFrom == LoginGait.FROM_INTRANET_LOGIN && !GaitParam.isIntranetGait()) {
				ret.addRecoverableError(SegError.NO_LOGIN_INSTANCIA_INTRA);
				return ret;
			}
		}
		
		//seteamos el locale a es para struts 
		//(es necesario para los formatKey sobretodo el de currency)
		session.setAttribute(Globals.LOCALE_KEY, new Locale("es"));

		// Autenticamos a la persona y obtenemos sus permisos
		boolean auth = SegServiceLocator.getAuthService().auth(userVO.getUsername(), userVO.getPassword());
		if (!auth) {
			log.info("Login: Falla autenticacion: " + userVO.getUsername());
			ret.addRecoverableValueError("El nombre de usuario o la contraseña no son correctos.");
			return ret;
		}

		// Autenticamos al tipo y obtenemos sus permisos
		sweUserSession = SweServiceLocator.getSweService().login(APP_CODE, userVO, true);
		if (sweUserSession.hasError()) {
			log.info("Login: Falla autenticacion SWE usuario: " + userVO.getUsername());
			ret.setListError(sweUserSession.getListError());
			ret.setListMessage(sweUserSession.getListMessage());
			ret.setErrorType(sweUserSession.getErrorType());
			return ret;
		}

		userSession = new UserSession();
		userSession.setUserName(userVO.getUsername());
		userSession.setLongUserName(sweUserSession.getLongUserName());
		userSession.setPermiteWeb(sweUserSession.getPermiteWeb());
		
		// Pasamos los datos del sweUserSession al UserSessino de Gait
		userSession.setIdsAccionesModuloUsuario(sweUserSession.getIdsAccionesModuloUsuario());
		userSession.setCodsRolUsuario(sweUserSession.getCodsRoles());
		
    	// Analisis de PermiteWeb para Logins no anonimos
    	// verificamos que el usario tenga la bandera permiteWeb si no es anonimo.
    	if (!esAnonimo && loginFrom == LoginGait.FROM_WEB_LOGIN && !userSession.getPermiteWeb()) {
			log.info("Login: Falla login en web. Usuario OK pero no permiteWeb: " + userVO.getUsername());
			ret.addRecoverableError(SegError.NO_PERMITEWEB);
    		return ret;
    	}
		
    	// Si es el usuario anonimo seteamos el flag de anonimo
    	// actualizamos el tiempo de timeout
    	if (esAnonimo) {
    		userSession.setIsAnonimo(true);
    		session.setMaxInactiveInterval(GaitParam.getInteger(GaitParam.TIMEOUT_ANONIMO, 15) * 60);
    	} else {
    		userSession.setIsAnonimo(false);
    		session.setMaxInactiveInterval(GaitParam.getInteger(GaitParam.TIMEOUT_AUTENTICADO, 60) * 60);			
    	}    	
    	
		// Cargamos en userContext datos de UsuarioGait
		UsuarioGaitVO usuarioGait = SegServiceLocator.getSeguridadService().getUsuarioGaitForLogin(userSession);
    	if (usuarioGait.hasError()) {
			log.info("Login: Falla busqueda de usuario GAIT: " + userVO.getUsername());
    		usuarioGait.passErrorMessages(ret);
    		return ret;
    	}
    	
    	// Direccion
    	userSession.setIdDireccion(usuarioGait.getDireccion().getId());
    	userSession.setDesDireccion(usuarioGait.getDireccion().getDescripcion());
    	userSession.setEsDGI(usuarioGait.getDireccion().getEsDGI().getEsSI());
    	
    	// Area
    	userSession.setIdArea(usuarioGait.getArea().getId());
    	userSession.setDesArea(usuarioGait.getArea().getDescripcion());
    	    	    	
    	//algo de logs una vez que se loguearon
    	log.info(String.format("Login: OK: Usuario: %s [%s] Direccion: %s - Area: %s Session Timeout: %s Info: %s", 
    			userSession.getUserName(),userSession.getLongUserName(),userSession.getDesDireccion(),userSession.getDesArea(),session.getMaxInactiveInterval(),userSession.infoString()));
    	
    	// cosas de la navegacion
		NavModel navModel = new NavModel();
		navModel.setPrevAction("/GaitIndex");

		// establecemos cadena de usuario logueado
		userSession.setNavModel(navModel);	

		// subo el userSession a la session
		session.setAttribute("userSession", userSession);
		
		// Guardar fecha de login en usuario
		SegServiceLocator.getSeguridadService().grabarUltimoLoginUsuarioGait(userSession);
		
		return ret;
	}

	/**	
	 * Maneja el request de login enviado desde los formularios de login (Web o Intranet)
	 */
	protected ActionForward login(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response, int fromLogin) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		HttpSession session = request.getSession(true); 
		UserSession userSession = null;
		Common control = null; 
		try {			
			UsuarioVO userVO = new UsuarioVO("","",session.getId());

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(userVO, request);

			// Tiene errores recuperables
			if (userVO.hasErrorRecoverable()) {
				saveDemodaErrors(request, userVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioVO.NAME, userVO);
			}

			// puede arrajoar una excecion porque la db este baja o no se pueda conectar.
			// en ese caso enviamos enviamos mensaje de off.
			try {
				control = sessionLogin(request, userVO, fromLogin);
			} catch (Exception e) {
				System.out.println(e);
				if (GaitParam.isWebGait()) { // && "0".equals(GaitParam.getString("webGaitOn"))){
					return mapping.findForward(BaseConstants.FWD_GAIT_OFFLINE);
				}
				if (GaitParam.isIntranetGait()) { // && "0".equals(GaitParam.getString("intraGaitOn"))){
					return mapping.findForward(BaseConstants.FWD_GAIT_OFFLINE);
				}
			}
			
			// Tiene errores recuperables
			if (control.hasErrorRecoverable()) {
				saveDemodaErrors(request, control);
				request.setAttribute( UsuarioVO.NAME, userVO);
				if (fromLogin == LoginGait.FROM_WEB_LOGIN) {
					return mapping.findForward(SegConstants.FWD_WEB_LOGIN_FORM);
				} else {
					return mapping.findForward(SegConstants.FWD_INTRANET_LOGIN_FORM);
				}
			}

			// Tiene errores no recuperables
			if (control.hasErrorNonRecoverable()) {
				saveDemodaErrors(request, control);
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioVO.NAME, userVO);
			}

			if (fromLogin == LoginGait.FROM_INTRANET_LOGIN) {
				userSession = (UserSession) session.getAttribute("userSession");
			}
			
			return mapping.findForward(SegConstants.ACTION_GAITMENU);			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioVO.NAME);
		}
	}

	/**
	 * Invalida la session de un usuario gait. 
	 */
	protected ActionForward logout(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		HttpSession session = canAccess(request); 
		if (session == null) return forwardErrorSession(mapping, request);

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			session.invalidate();
			return mapping.findForward(BaseConstants.FWD_GAITINDEX);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioVO.NAME);
		}
	}

	/**
	 * Loguea a un usuario anonimo y luego forwardea a la url pasada en los
	 * parametros.
	 * Esta funcion es el punto de entrada a las funcionalidades de gait anonimas.
	 * Luego de pasa por este method llegaremos al gait con una session anonima
	 * recien creada.
	 * Un usuario Anonimo solo puede acceder desde la Web
	 */
	protected ActionForward anonimo(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response)
	throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
		// creamos http session
		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
		}
		session = request.getSession(true);
		
		try {
			// logueamo al anonimo
			UsuarioVO userVO = new UsuarioVO(BaseSecurityConstants.USR_ANONIMO, BaseSecurityConstants.PWD_ANONIMO, session.getId());
			Common control = null;

			try {
				control = sessionLogin(request, userVO, FROM_WEB_LOGIN);
			} catch (Exception e) {
				if (GaitParam.isWebGait()) { // && "0".equals(GaitParam.getString("webGaitOn"))){
					return mapping.findForward(BaseConstants.FWD_GAIT_OFFLINE);
				}
				if (GaitParam.isIntranetGait()) { // && "0".equals(GaitParam.getString("intraGaitOn"))){
					return mapping.findForward(BaseConstants.FWD_GAIT_OFFLINE);
				}
			}

			// Tiene errores no recuperables
			if (control.hasError()) {
				log.error("No se pudo loguear a un usuario anonimo. Esto se debe a una inconsistencia de datos entre los usuarios de segweb y los SWE. " +
						"Verfique que exista el usuario/password en swe:" 
						+ BaseSecurityConstants.USR_ANONIMO + "/" + BaseSecurityConstants.PWD_ANONIMO);
				saveDemodaErrors(request, control);
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioVO.NAME, control);
			}

			// forwadeamos a donde quiere ir.
			String url = request.getParameter("url");
			UserSession userSession = this.getCurrentUserSession(request, mapping);
			userSession.setUrlReComenzar(url);
			return new ActionForward(url);
		} catch (Exception e) {
			return baseException(mapping, request, funcName, e, UsuarioVO.NAME);			
		}
	}

	/**
	 * Muestra la p�gina para ingresar datos de usuario, contrase�a y puesto trabajo en el caso de que sea por intranet.
	 * TODO: hacer que segnu fromLogin muestro o no la parte de puesto de trabajo
	 */
	protected ActionForward init(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response, int fromLogin)
	throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		
/*/ Permitimos "apagar" la aplicacion por instancia
if (GaitParam.isWebGait() && "0".equals(GaitParam.getString("webGaitOn"))){
	return mapping.findForward(BaseConstants.FWD_GAIT_OFFLINE);
}
if (GaitParam.isIntranetGait() && "0".equals(GaitParam.getString("intraGaitOn"))){
	return mapping.findForward(BaseConstants.FWD_GAIT_OFFLINE);
}*/
		
		try {
			UsuarioVO userVO = new UsuarioVO();
			request.setAttribute(UsuarioVO.NAME, userVO);
			if (fromLogin == FROM_WEB_LOGIN) {
				return mapping.findForward(SegConstants.FWD_WEB_LOGIN_FORM);
			} else {
				return mapping.findForward(SegConstants.FWD_INTRANET_LOGIN_FORM);
			}
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioVO.NAME);
		}
	}
		
	/**
	 * Inicializa el formulario de cambio de password
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	protected ActionForward changePassInit(ActionMapping mapping,
			 ActionForm form,
			 HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
	
		UsuarioVO usuarioVO = new UsuarioVO();
		request.setAttribute(UsuarioVO.NAME, usuarioVO);
		
		return mapping.findForward(SegConstants.FWD_CHANGE_PASS_FORM);

	}
	
	
	/**
	 * Realiza el cambio de password
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward changePass(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = getCurrentUserSession(request, mapping);
		HttpSession session = request.getSession(true);
		
		try {
			UsuarioVO userVO = new UsuarioVO("","", session.getId());

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(userVO, request);

			
			if (StringUtil.isNullOrEmpty(userVO.getNewPassword())){
				userVO.addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, SegError.USUARIO_NEWPASS_LABEL);
			}
			
			if (StringUtil.isNullOrEmpty(userVO.getNewPasswordReentry())){
				userVO.addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, SegError.USUARIO_NEWPASSREENTRY_LABEL);
			}
			
			if (!StringUtil.isNullOrEmpty(userVO.getNewPassword()) && 
					!StringUtil.isNullOrEmpty(userVO.getNewPasswordReentry()) &&
					!userVO.getNewPassword().equals(userVO.getNewPasswordReentry())) {
				userVO.addRecoverableError(SegError.MSG_NO_COINICEN_ERROR);
			}
				
			// Tiene errores recuperables
			if (userVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + userVO.infoString()); 
				saveDemodaErrors(request, userVO);
				request.setAttribute(UsuarioVO.NAME, userVO);
				return mapping.findForward(SegConstants.FWD_CHANGE_PASS_FORM);
			}
			
			// llamada al servicio
			UsuarioVO usuarioVO = SegServiceLocator.getSeguridadService().changePass(userSession, userVO);
			
            // Tiene errores recuperables
			if (usuarioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioVO.infoString()); 
				saveDemodaErrors(request, usuarioVO);
				request.setAttribute(UsuarioVO.NAME, usuarioVO);
				return mapping.findForward(SegConstants.FWD_CHANGE_PASS_FORM);
			}
			
			// Tiene errores no recuperables
			if (usuarioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioVO.NAME, usuarioVO);
			}
			
			// Fue Exitoso
			NavModel navModel = userSession.getNavModel();
				
			//le seteo la accion a donde ir en la pantalla de confirmacion al navModel
			navModel.setConfAction("/seg/GaitMenu");
			navModel.setConfActionParameter("build");
					
			return this.forwardMessage(mapping, navModel, NavModel.NAVMODEL_MESSAGE_TYPE_CONFIRMATION, BaseConstants.SUCCESS_MESSAGE_DESCRIPTION);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioVO.NAME);
		}
	}

	/**
	 * Volver desde el formulario de cambio de password
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward volver(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward(SegConstants.ACTION_GAITMENU);
	}
	
}
