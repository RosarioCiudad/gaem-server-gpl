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

import ar.gov.rosario.gait.apm.iface.model.AplicacionTipoBinarioAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTipoBinarioVO;
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

public final class AdministrarAplicacionTipoBinarioDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarAplicacionTipoBinarioDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONTIPOBINARIO, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		AplicacionTipoBinarioAdapter aplicacionTipoBinarioAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getAplicacionTipoBinarioAdapterForView(userSession, commonKey)";
				aplicacionTipoBinarioAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionTipoBinarioAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONTIPOBINARIO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplicacionTipoBinarioAdapterForUpdate(userSession, commonKey)";
				aplicacionTipoBinarioAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionTipoBinarioAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONTIPOBINARIO_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getAplicacionTipoBinarioAdapterForView(userSession, commonKey)";
				aplicacionTipoBinarioAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionTipoBinarioAdapterForView(userSession, commonKey);				
				aplicacionTipoBinarioAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.APLICACIONTIPOBINARIO_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONTIPOBINARIO_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplicacionTipoBinarioAdapterForCreate(userSession)";
				aplicacionTipoBinarioAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionTipoBinarioAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONTIPOBINARIO_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (aplicacionTipoBinarioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplicacionTipoBinarioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionTipoBinarioAdapter.NAME, aplicacionTipoBinarioAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			aplicacionTipoBinarioAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplicacionTipoBinarioAdapter.NAME + ": "+ aplicacionTipoBinarioAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(AplicacionTipoBinarioAdapter.NAME, aplicacionTipoBinarioAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionTipoBinarioAdapter.NAME, aplicacionTipoBinarioAdapterVO);
			 
			saveDemodaMessages(request, aplicacionTipoBinarioAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionTipoBinarioAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONTIPOBINARIO, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionTipoBinarioAdapter aplicacionTipoBinarioAdapterVO = (AplicacionTipoBinarioAdapter) userSession.get(AplicacionTipoBinarioAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionTipoBinarioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionTipoBinarioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionTipoBinarioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionTipoBinarioAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionTipoBinarioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTipoBinarioAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionTipoBinarioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTipoBinarioAdapter.NAME, aplicacionTipoBinarioAdapterVO);
			}
			
			// llamada al servicio
			AplicacionTipoBinarioVO aplicacionTipoBinarioVO = ApmServiceLocator.getAplicacionService().createAplicacionTipoBinario(userSession, aplicacionTipoBinarioAdapterVO.getAplicacionTipoBinario());
			
            // Tiene errores recuperables
			if (aplicacionTipoBinarioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTipoBinarioVO.infoString()); 
				saveDemodaErrors(request, aplicacionTipoBinarioVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTipoBinarioAdapter.NAME, aplicacionTipoBinarioAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionTipoBinarioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionTipoBinarioVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionTipoBinarioAdapter.NAME, aplicacionTipoBinarioAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionTipoBinarioAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionTipoBinarioAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONTIPOBINARIO, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionTipoBinarioAdapter aplicacionTipoBinarioAdapterVO = (AplicacionTipoBinarioAdapter) userSession.get(AplicacionTipoBinarioAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionTipoBinarioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionTipoBinarioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionTipoBinarioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionTipoBinarioAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionTipoBinarioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTipoBinarioAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionTipoBinarioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTipoBinarioAdapter.NAME, aplicacionTipoBinarioAdapterVO);
			}
			
			// llamada al servicio
			AplicacionTipoBinarioVO aplicacionTipoBinarioVO = ApmServiceLocator.getAplicacionService().updateAplicacionTipoBinario(userSession, aplicacionTipoBinarioAdapterVO.getAplicacionTipoBinario());
			
            // Tiene errores recuperables
			if (aplicacionTipoBinarioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTipoBinarioAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionTipoBinarioVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTipoBinarioAdapter.NAME, aplicacionTipoBinarioAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionTipoBinarioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionTipoBinarioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionTipoBinarioAdapter.NAME, aplicacionTipoBinarioAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionTipoBinarioAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionTipoBinarioAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONTIPOBINARIO, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionTipoBinarioAdapter aplicacionTipoBinarioAdapterVO = (AplicacionTipoBinarioAdapter) userSession.get(AplicacionTipoBinarioAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionTipoBinarioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionTipoBinarioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionTipoBinarioAdapter.NAME); 
			}

			// llamada al servicio
			AplicacionTipoBinarioVO aplicacionTipoBinarioVO = ApmServiceLocator.getAplicacionService().deleteAplicacionTipoBinario
				(userSession, aplicacionTipoBinarioAdapterVO.getAplicacionTipoBinario());
			
            // Tiene errores recuperables
			if (aplicacionTipoBinarioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTipoBinarioAdapterVO.infoString());
				saveDemodaErrors(request, aplicacionTipoBinarioVO);				
				request.setAttribute(AplicacionTipoBinarioAdapter.NAME, aplicacionTipoBinarioAdapterVO);
				return mapping.findForward(ApmConstants.FWD_APLICACIONTIPOBINARIO_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (aplicacionTipoBinarioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionTipoBinarioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionTipoBinarioAdapter.NAME, aplicacionTipoBinarioAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionTipoBinarioAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionTipoBinarioAdapter.NAME);
		}
	}
		
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AplicacionTipoBinarioAdapter.NAME);
		
	}
	
} 
