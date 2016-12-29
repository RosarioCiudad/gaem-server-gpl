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
package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.UsuarioApmImpresoraAdapter;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmImpresoraVO;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarUsuarioApmImpresoraDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarUsuarioApmImpresoraDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPMIMPRESORA, act); 
		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		UsuarioApmImpresoraAdapter usuarioApmImpresoraAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getUsuarioApmImpresoraAdapterForView(userSession, commonKey)";
				usuarioApmImpresoraAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmImpresoraAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPMIMPRESORA_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getUsuarioApmImpresoraAdapterForUpdate(userSession, commonKey)";
				usuarioApmImpresoraAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmImpresoraAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPMIMPRESORA_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getUsuarioApmImpresoraAdapterForView(userSession, commonKey)";
				usuarioApmImpresoraAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmImpresoraAdapterForView(userSession, commonKey);				
				usuarioApmImpresoraAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.USUARIOAPMIMPRESORA_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPMIMPRESORA_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getUsuarioApmImpresoraAdapterForCreate(userSession)";
				usuarioApmImpresoraAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmImpresoraAdapterForCreate(userSession,commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPMIMPRESORA_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (usuarioApmImpresoraAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + usuarioApmImpresoraAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmImpresoraAdapter.NAME, usuarioApmImpresoraAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			usuarioApmImpresoraAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + UsuarioApmImpresoraAdapter.NAME + ": "+ usuarioApmImpresoraAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(UsuarioApmImpresoraAdapter.NAME, usuarioApmImpresoraAdapterVO);
			// Subo el apdater al userSession
			userSession.put(UsuarioApmImpresoraAdapter.NAME, usuarioApmImpresoraAdapterVO);

			saveDemodaMessages(request, usuarioApmImpresoraAdapterVO);

			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmImpresoraAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPMIMPRESORA, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			UsuarioApmImpresoraAdapter usuarioApmImpresoraAdapterVO = (UsuarioApmImpresoraAdapter) userSession.get(UsuarioApmImpresoraAdapter.NAME);

			// Si es nulo no se puede continuar
			if (usuarioApmImpresoraAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioApmImpresoraAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioApmImpresoraAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(usuarioApmImpresoraAdapterVO, request);

			// Tiene errores recuperables
			if (usuarioApmImpresoraAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmImpresoraAdapterVO.infoString()); 
				saveDemodaErrors(request, usuarioApmImpresoraAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmImpresoraAdapter.NAME, usuarioApmImpresoraAdapterVO);
			}

			// llamada al servicio
			UsuarioApmImpresoraVO aplicacionUsuarioApmImpresoraVO = ApmServiceLocator.getAplicacionService().createUsuarioApmImpresora(userSession, usuarioApmImpresoraAdapterVO.getUsuarioApmImpresora());

			// Tiene errores recuperables
			if (aplicacionUsuarioApmImpresoraVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionUsuarioApmImpresoraVO.infoString()); 
				saveDemodaErrors(request, aplicacionUsuarioApmImpresoraVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmImpresoraAdapter.NAME, usuarioApmImpresoraAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplicacionUsuarioApmImpresoraVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionUsuarioApmImpresoraVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmImpresoraAdapter.NAME, usuarioApmImpresoraAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioApmImpresoraAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmImpresoraAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPMIMPRESORA, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			UsuarioApmImpresoraAdapter usuarioApmImpresoraAdapterVO = (UsuarioApmImpresoraAdapter) userSession.get(UsuarioApmImpresoraAdapter.NAME);

			// Si es nulo no se puede continuar
			if (usuarioApmImpresoraAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioApmImpresoraAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioApmImpresoraAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(usuarioApmImpresoraAdapterVO, request);

			// Tiene errores recuperables
			if (usuarioApmImpresoraAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmImpresoraAdapterVO.infoString()); 
				saveDemodaErrors(request, usuarioApmImpresoraAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmImpresoraAdapter.NAME, usuarioApmImpresoraAdapterVO);
			}

			// llamada al servicio
			UsuarioApmImpresoraVO aplicacionUsuarioApmImpresoraVO = ApmServiceLocator.getAplicacionService().updateUsuarioApmImpresora(userSession, usuarioApmImpresoraAdapterVO.getUsuarioApmImpresora());

			// Tiene errores recuperables
			if (aplicacionUsuarioApmImpresoraVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmImpresoraAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionUsuarioApmImpresoraVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmImpresoraAdapter.NAME, usuarioApmImpresoraAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplicacionUsuarioApmImpresoraVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioApmImpresoraAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmImpresoraAdapter.NAME, usuarioApmImpresoraAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioApmImpresoraAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmImpresoraAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPMIMPRESORA, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			UsuarioApmImpresoraAdapter usuarioApmImpresoraAdapterVO = (UsuarioApmImpresoraAdapter) userSession.get(UsuarioApmImpresoraAdapter.NAME);

			// Si es nulo no se puede continuar
			if (usuarioApmImpresoraAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioApmImpresoraAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioApmImpresoraAdapter.NAME); 
			}

			// llamada al servicio
			UsuarioApmImpresoraVO aplicacionUsuarioApmImpresoraVO = ApmServiceLocator.getAplicacionService().deleteUsuarioApmImpresora
					(userSession, usuarioApmImpresoraAdapterVO.getUsuarioApmImpresora());

			// Tiene errores recuperables
			if (aplicacionUsuarioApmImpresoraVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmImpresoraAdapterVO.infoString());
				saveDemodaErrors(request, aplicacionUsuarioApmImpresoraVO);				
				request.setAttribute(UsuarioApmImpresoraAdapter.NAME, usuarioApmImpresoraAdapterVO);
				return mapping.findForward(ApmConstants.FWD_USUARIOAPMIMPRESORA_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (aplicacionUsuarioApmImpresoraVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioApmImpresoraAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmImpresoraAdapter.NAME, usuarioApmImpresoraAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioApmImpresoraAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmImpresoraAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {

		return baseVolver(mapping, form, request, response, UsuarioApmImpresoraAdapter.NAME);
	}
} 
