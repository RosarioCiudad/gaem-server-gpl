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

import ar.gov.rosario.gait.apm.iface.model.ImpresoraAdapter;
import ar.gov.rosario.gait.apm.iface.model.ImpresoraVO;
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

public final class AdministrarImpresoraDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarImpresoraDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_IMPRESORA, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		ImpresoraAdapter impresoraAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getImpresoraAdapterForView(userSession, commonKey)";
				impresoraAdapterVO = ApmServiceLocator.getAplicacionService().getImpresoraAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_IMPRESORA_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getImpresoraAdapterForUpdate(userSession, commonKey)";
				impresoraAdapterVO = ApmServiceLocator.getAplicacionService().getImpresoraAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_IMPRESORA_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getImpresoraAdapterForView(userSession, commonKey)";
				impresoraAdapterVO = ApmServiceLocator.getAplicacionService().getImpresoraAdapterForView(userSession, commonKey);				
				impresoraAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.IMPRESORA_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_IMPRESORA_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getImpresoraAdapterForCreate(userSession)";
				impresoraAdapterVO = ApmServiceLocator.getAplicacionService().getImpresoraAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_IMPRESORA_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (impresoraAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + impresoraAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, ImpresoraAdapter.NAME, impresoraAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			impresoraAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + ImpresoraAdapter.NAME + ": "+ impresoraAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(ImpresoraAdapter.NAME, impresoraAdapterVO);
			// Subo el apdater al userSession
			userSession.put(ImpresoraAdapter.NAME, impresoraAdapterVO);
			 
			saveDemodaMessages(request, impresoraAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, ImpresoraAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_IMPRESORA, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			ImpresoraAdapter impresoraAdapterVO = (ImpresoraAdapter) userSession.get(ImpresoraAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (impresoraAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + ImpresoraAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, ImpresoraAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(impresoraAdapterVO, request);
			
            // Tiene errores recuperables
			if (impresoraAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + impresoraAdapterVO.infoString()); 
				saveDemodaErrors(request, impresoraAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, ImpresoraAdapter.NAME, impresoraAdapterVO);
			}
			
			// llamada al servicio
			ImpresoraVO impresoraVO = ApmServiceLocator.getAplicacionService().createImpresora(userSession, impresoraAdapterVO.getImpresora());
			
            // Tiene errores recuperables
			if (impresoraVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + impresoraVO.infoString()); 
				saveDemodaErrors(request, impresoraVO);
				return forwardErrorRecoverable(mapping, request, userSession, ImpresoraAdapter.NAME, impresoraAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (impresoraVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + impresoraVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, ImpresoraAdapter.NAME, impresoraAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, ImpresoraAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, ImpresoraAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_IMPRESORA, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			ImpresoraAdapter impresoraAdapterVO = (ImpresoraAdapter) userSession.get(ImpresoraAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (impresoraAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + ImpresoraAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, ImpresoraAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(impresoraAdapterVO, request);
			
            // Tiene errores recuperables
			if (impresoraAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + impresoraAdapterVO.infoString()); 
				saveDemodaErrors(request, impresoraAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, ImpresoraAdapter.NAME, impresoraAdapterVO);
			}
			
			// llamada al servicio
			ImpresoraVO impresoraVO = ApmServiceLocator.getAplicacionService().updateImpresora(userSession, impresoraAdapterVO.getImpresora());
			
            // Tiene errores recuperables
			if (impresoraVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + impresoraAdapterVO.infoString()); 
				saveDemodaErrors(request, impresoraVO);
				return forwardErrorRecoverable(mapping, request, userSession, ImpresoraAdapter.NAME, impresoraAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (impresoraVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + impresoraAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, ImpresoraAdapter.NAME, impresoraAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, ImpresoraAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, ImpresoraAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_IMPRESORA, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			ImpresoraAdapter impresoraAdapterVO = (ImpresoraAdapter) userSession.get(ImpresoraAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (impresoraAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + ImpresoraAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, ImpresoraAdapter.NAME); 
			}

			// llamada al servicio
			ImpresoraVO impresoraVO = ApmServiceLocator.getAplicacionService().deleteImpresora
				(userSession, impresoraAdapterVO.getImpresora());
			
            // Tiene errores recuperables
			if (impresoraVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + impresoraAdapterVO.infoString());
				saveDemodaErrors(request, impresoraVO);				
				request.setAttribute(ImpresoraAdapter.NAME, impresoraAdapterVO);
				return mapping.findForward(ApmConstants.FWD_IMPRESORA_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (impresoraVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + impresoraAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, ImpresoraAdapter.NAME, impresoraAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, ImpresoraAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, ImpresoraAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, ImpresoraAdapter.NAME);
		
	}
	
	public ActionForward param (ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = getCurrentUserSession(request, mapping); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			ImpresoraAdapter impresoraAdapterVO = (ImpresoraAdapter) userSession.get(ImpresoraAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (impresoraAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + ImpresoraAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, ImpresoraAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(impresoraAdapterVO, request);
			
            // Tiene errores recuperables
			if (impresoraAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + impresoraAdapterVO.infoString()); 
				saveDemodaErrors(request, impresoraAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, ImpresoraAdapter.NAME, impresoraAdapterVO);
			}
			
			// llamada al servicio
			//impresoraAdapterVO = ApmServiceLocator.getImpresoraService().getImpresoraAdapterParam(userSession, impresoraAdapterVO);
			
            // Tiene errores recuperables
			if (impresoraAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + impresoraAdapterVO.infoString()); 
				saveDemodaErrors(request, impresoraAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, ImpresoraAdapter.NAME, impresoraAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (impresoraAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + impresoraAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, ImpresoraAdapter.NAME, impresoraAdapterVO);
			}
			
			// Envio el VO al request
			request.setAttribute(ImpresoraAdapter.NAME, impresoraAdapterVO);
			// Subo el apdater al userSession
			userSession.put(ImpresoraAdapter.NAME, impresoraAdapterVO);
			
			return mapping.findForward(ApmConstants.FWD_IMPRESORA_EDIT_ADAPTER);
		
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, ImpresoraAdapter.NAME);
		}
	}
		
		
	
} 
