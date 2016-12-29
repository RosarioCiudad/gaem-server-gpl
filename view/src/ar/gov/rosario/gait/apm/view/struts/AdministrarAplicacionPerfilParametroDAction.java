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

import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilParametroAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilParametroVO;
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

public final class AdministrarAplicacionPerfilParametroDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarAplicacionPerfilParametroDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFILPARAMETRO, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		AplicacionPerfilParametroAdapter aplicacionPerfilParametroAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getAplicacionPerfilParametroAdapterForView(userSession, commonKey)";
				aplicacionPerfilParametroAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilParametroAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFILPARAMETRO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplicacionPerfilParametroAdapterForUpdate(userSession, commonKey)";
				aplicacionPerfilParametroAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilParametroAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFILPARAMETRO_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getAplicacionPerfilParametroAdapterForView(userSession, commonKey)";
				aplicacionPerfilParametroAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilParametroAdapterForView(userSession, commonKey);				
				aplicacionPerfilParametroAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.APLICACIONPERFILPARAMETRO_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFILPARAMETRO_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplicacionPerfilParametroAdapterForCreate(userSession)";
				aplicacionPerfilParametroAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilParametroAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFILPARAMETRO_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (aplicacionPerfilParametroAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplicacionPerfilParametroAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			aplicacionPerfilParametroAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplicacionPerfilParametroAdapter.NAME + ": "+ aplicacionPerfilParametroAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			 
			saveDemodaMessages(request, aplicacionPerfilParametroAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilParametroAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFILPARAMETRO, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionPerfilParametroAdapter aplicacionPerfilParametroAdapterVO = (AplicacionPerfilParametroAdapter) userSession.get(AplicacionPerfilParametroAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionPerfilParametroAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilParametroAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilParametroAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionPerfilParametroAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionPerfilParametroAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilParametroAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilParametroAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			}
			
			// llamada al servicio
			AplicacionPerfilParametroVO aplicacionPerfilParametroVO = ApmServiceLocator.getAplicacionService().createAplicacionPerfilParametro(userSession, aplicacionPerfilParametroAdapterVO.getAplicacionPerfilParametro());
			
            // Tiene errores recuperables
			if (aplicacionPerfilParametroVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilParametroVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilParametroVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionPerfilParametroVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilParametroVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilParametroAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilParametroAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFILPARAMETRO, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionPerfilParametroAdapter aplicacionPerfilParametroAdapterVO = (AplicacionPerfilParametroAdapter) userSession.get(AplicacionPerfilParametroAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionPerfilParametroAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilParametroAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilParametroAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionPerfilParametroAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionPerfilParametroAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilParametroAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilParametroAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			}
			
			// llamada al servicio
			AplicacionPerfilParametroVO aplicacionPerfilParametroVO = ApmServiceLocator.getAplicacionService().updateAplicacionPerfilParametro(userSession, aplicacionPerfilParametroAdapterVO.getAplicacionPerfilParametro());
			
            // Tiene errores recuperables
			if (aplicacionPerfilParametroVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilParametroAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilParametroVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionPerfilParametroVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilParametroAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilParametroAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilParametroAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFILPARAMETRO, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionPerfilParametroAdapter aplicacionPerfilParametroAdapterVO = (AplicacionPerfilParametroAdapter) userSession.get(AplicacionPerfilParametroAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionPerfilParametroAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilParametroAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilParametroAdapter.NAME); 
			}

			// llamada al servicio
			AplicacionPerfilParametroVO aplicacionPerfilParametroVO = ApmServiceLocator.getAplicacionService().deleteAplicacionPerfilParametro
				(userSession, aplicacionPerfilParametroAdapterVO.getAplicacionPerfilParametro());
			
            // Tiene errores recuperables
			if (aplicacionPerfilParametroVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilParametroAdapterVO.infoString());
				saveDemodaErrors(request, aplicacionPerfilParametroVO);				
				request.setAttribute(AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
				return mapping.findForward(ApmConstants.FWD_APLICACIONPERFILPARAMETRO_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (aplicacionPerfilParametroVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilParametroAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilParametroAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilParametroAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AplicacionPerfilParametroAdapter.NAME);
		
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
			AplicacionPerfilParametroAdapter aplicacionPerfilParametroAdapterVO = (AplicacionPerfilParametroAdapter) userSession.get(AplicacionPerfilParametroAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionPerfilParametroAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilParametroAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilParametroAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionPerfilParametroAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionPerfilParametroAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilParametroAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilParametroAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			}
			
			// llamada al servicio
			//aplicacionPerfilParametroAdapterVO = ApmServiceLocator.getAplicacionPerfilParametroService().getAplicacionPerfilParametroAdapterParam(userSession, aplicacionPerfilParametroAdapterVO);
			
            // Tiene errores recuperables
			if (aplicacionPerfilParametroAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilParametroAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilParametroAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionPerfilParametroAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilParametroAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			}
			
			// Envio el VO al request
			request.setAttribute(AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionPerfilParametroAdapter.NAME, aplicacionPerfilParametroAdapterVO);
			
			return mapping.findForward(ApmConstants.FWD_APLICACIONPERFILPARAMETRO_EDIT_ADAPTER);
		
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilParametroAdapter.NAME);
		}
	}
		
		
	
} 
