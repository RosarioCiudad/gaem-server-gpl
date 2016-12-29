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

import ar.gov.rosario.gait.apm.iface.model.UsuarioApmAdapter;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmVO;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarEncUsuarioApmDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarEncUsuarioApmDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);		
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPM_ENC, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		UsuarioApmAdapter usuarioApmAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (navModel.getAct().equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getUsuarioApmAdapterForUpdate(userSession, commonKey)";
				usuarioApmAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPM_ENC_EDIT_ADAPTER);
			}
			if (navModel.getAct().equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getUsuarioApmAdapterForCreate(userSession)";
				usuarioApmAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPM_ENC_EDIT_ADAPTER);
				//Copiar los filtros de busqueda al adapter 
				if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) DemodaUtil.populateVO(usuarioApmAdapterVO, request);				
			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (usuarioApmAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + usuarioApmAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmAdapter.ENC_NAME, usuarioApmAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			usuarioApmAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + UsuarioApmAdapter.ENC_NAME + ": "+ usuarioApmAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(UsuarioApmAdapter.ENC_NAME, usuarioApmAdapterVO);
			// Subo el apdater al userSession
			userSession.put(UsuarioApmAdapter.ENC_NAME, usuarioApmAdapterVO);

			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmAdapter.ENC_NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, 
			ApmSecurityConstants.ABM_USUARIOAPM_ENC, BaseSecurityConstants.AGREGAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			UsuarioApmAdapter usuarioApmAdapterVO = (UsuarioApmAdapter) userSession.get(UsuarioApmAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (usuarioApmAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioApmAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioApmAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(usuarioApmAdapterVO, request);
			
            // Tiene errores recuperables
			if (usuarioApmAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmAdapterVO.infoString()); 
				saveDemodaErrors(request, usuarioApmAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmAdapter.ENC_NAME, usuarioApmAdapterVO);
			}
			
			// llamada al servicio
			UsuarioApmVO usuarioApmVO = ApmServiceLocator.getAplicacionService().createUsuarioApm(userSession, usuarioApmAdapterVO.getUsuarioApm());
			
            // Tiene errores recuperables
			if (usuarioApmVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmVO.infoString()); 
				saveDemodaErrors(request, usuarioApmVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmAdapter.ENC_NAME, usuarioApmAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (usuarioApmVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioApmVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmAdapter.ENC_NAME, usuarioApmAdapterVO);
			}

			// Si tiene permiso lo dirijo al adapter de modificacion, 
			// sino vuelve al searchPage
			if (hasAccess(userSession, ApmSecurityConstants.ABM_USUARIOAPM, 
				BaseSecurityConstants.MODIFICAR)) {
				
				// seteo el id para que lo use el siguiente action 
				userSession.getNavModel().setSelectedId(usuarioApmVO.getId().toString());

				// lo dirijo al adapter de modificacion
				return forwardConfirmarOk(mapping, request, funcName, UsuarioApmAdapter.ENC_NAME, 
					ApmConstants.PATH_ADMINISTRAR_USUARIOAPM, BaseConstants.METHOD_INICIALIZAR, 
					BaseConstants.ACT_MODIFICAR);
			} else {
				
				// lo dirijo al searchPage				
				return forwardConfirmarOk(mapping, request, funcName, UsuarioApmAdapter.ENC_NAME);
				
			}
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmAdapter.ENC_NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPM_ENC, BaseSecurityConstants.MODIFICAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			UsuarioApmAdapter usuarioApmAdapterVO = (UsuarioApmAdapter) userSession.get(UsuarioApmAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (usuarioApmAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioApmAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioApmAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(usuarioApmAdapterVO, request);
			
            // Tiene errores recuperables
			if (usuarioApmAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmAdapterVO.infoString()); 
				saveDemodaErrors(request, usuarioApmAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmAdapter.ENC_NAME, usuarioApmAdapterVO);
			}
			
			// llamada al servicio
			UsuarioApmVO usuarioApmVO = ApmServiceLocator.getAplicacionService().updateUsuarioApm(userSession, usuarioApmAdapterVO.getUsuarioApm());
			
            // Tiene errores recuperables
			if (usuarioApmVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmAdapterVO.infoString()); 
				saveDemodaErrors(request, usuarioApmVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmAdapter.ENC_NAME, usuarioApmAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (usuarioApmVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioApmAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmAdapter.ENC_NAME, usuarioApmAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioApmAdapter.ENC_NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmAdapter.ENC_NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, UsuarioApmAdapter.ENC_NAME);
	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, UsuarioApmAdapter.ENC_NAME);		
	}
	
}
	
