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

import ar.gov.rosario.gait.apm.iface.model.AplicacionParametroAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionParametroVO;
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

public final class AdministrarAplicacionParametroDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarAplicacionParametroDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPARAMETRO, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		AplicacionParametroAdapter aplicacionParametroAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getAplicacionParametroAdapterForView(userSession, commonKey)";
				aplicacionParametroAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionParametroAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPARAMETRO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplicacionParametroAdapterForUpdate(userSession, commonKey)";
				aplicacionParametroAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionParametroAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPARAMETRO_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getAplicacionParametroAdapterForView(userSession, commonKey)";
				aplicacionParametroAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionParametroAdapterForView(userSession, commonKey);				
				aplicacionParametroAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.APLICACIONPARAMETRO_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPARAMETRO_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplicacionParametroAdapterForCreate(userSession)";
				aplicacionParametroAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionParametroAdapterForCreate(userSession,commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPARAMETRO_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (aplicacionParametroAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplicacionParametroAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			aplicacionParametroAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplicacionParametroAdapter.NAME + ": "+ aplicacionParametroAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			 
			saveDemodaMessages(request, aplicacionParametroAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionParametroAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPARAMETRO, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionParametroAdapter aplicacionParametroAdapterVO = (AplicacionParametroAdapter) userSession.get(AplicacionParametroAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionParametroAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionParametroAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionParametroAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionParametroAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionParametroAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionParametroAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionParametroAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			}
			
			// llamada al servicio
			AplicacionParametroVO aplicacionParametroVO = ApmServiceLocator.getAplicacionService().createAplicacionParametro(userSession, aplicacionParametroAdapterVO.getAplicacionParametro());
			
            // Tiene errores recuperables
			if (aplicacionParametroVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionParametroVO.infoString()); 
				saveDemodaErrors(request, aplicacionParametroVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionParametroVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionParametroVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionParametroAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionParametroAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPARAMETRO, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionParametroAdapter aplicacionParametroAdapterVO = (AplicacionParametroAdapter) userSession.get(AplicacionParametroAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionParametroAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionParametroAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionParametroAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionParametroAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionParametroAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionParametroAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionParametroAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			}
			
			// llamada al servicio
			AplicacionParametroVO aplicacionParametroVO = ApmServiceLocator.getAplicacionService().updateAplicacionParametro(userSession, aplicacionParametroAdapterVO.getAplicacionParametro());
			
            // Tiene errores recuperables
			if (aplicacionParametroVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionParametroAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionParametroVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionParametroVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionParametroAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionParametroAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionParametroAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPARAMETRO, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionParametroAdapter aplicacionParametroAdapterVO = (AplicacionParametroAdapter) userSession.get(AplicacionParametroAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionParametroAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionParametroAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionParametroAdapter.NAME); 
			}

			// llamada al servicio
			AplicacionParametroVO aplicacionParametroVO = ApmServiceLocator.getAplicacionService().deleteAplicacionParametro
				(userSession, aplicacionParametroAdapterVO.getAplicacionParametro());
			
            // Tiene errores recuperables
			if (aplicacionParametroVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionParametroAdapterVO.infoString());
				saveDemodaErrors(request, aplicacionParametroVO);				
				request.setAttribute(AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
				return mapping.findForward(ApmConstants.FWD_APLICACIONPARAMETRO_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (aplicacionParametroVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionParametroAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionParametroAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionParametroAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AplicacionParametroAdapter.NAME);
		
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
			AplicacionParametroAdapter aplicacionParametroAdapterVO = (AplicacionParametroAdapter) userSession.get(AplicacionParametroAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionParametroAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionParametroAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionParametroAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionParametroAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionParametroAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionParametroAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionParametroAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			}
			
			// llamada al servicio
			//aplicacionParametroAdapterVO = ApmServiceLocator.getAplicacionParametroService().getAplicacionParametroAdapterParam(userSession, aplicacionParametroAdapterVO);
			
            // Tiene errores recuperables
			if (aplicacionParametroAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionParametroAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionParametroAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionParametroAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionParametroAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			}
			
			// Envio el VO al request
			request.setAttribute(AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionParametroAdapter.NAME, aplicacionParametroAdapterVO);
			
			return mapping.findForward(ApmConstants.FWD_APLICACIONPARAMETRO_EDIT_ADAPTER);
		
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionParametroAdapter.NAME);
		}
	}
		
		
	
} 
