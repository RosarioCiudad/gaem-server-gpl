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
package ar.gov.rosario.gait.def.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.def.iface.model.AreaAplicacionPerfilAdapter;
import ar.gov.rosario.gait.def.iface.model.AreaAplicacionPerfilVO;
import ar.gov.rosario.gait.def.iface.service.DefServiceLocator;
import ar.gov.rosario.gait.def.iface.util.DefError;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;
import ar.gov.rosario.gait.def.view.util.DefConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarAreaAplicacionPerfilDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarAreaAplicacionPerfilDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_AREAAPLICACIONPERFIL, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		AreaAplicacionPerfilAdapter areaAplicacionPerfilAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getAreaAplicacionPerfilAdapterForView(userSession, commonKey)";
				areaAplicacionPerfilAdapterVO = DefServiceLocator.getConfiguracionService().getAreaAplicacionPerfilAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_AREAAPLICACIONPERFIL_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAreaAplicacionPerfilAdapterForUpdate(userSession, commonKey)";
				areaAplicacionPerfilAdapterVO = DefServiceLocator.getConfiguracionService().getAreaAplicacionPerfilAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_AREAAPLICACIONPERFIL_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getAreaAplicacionPerfilAdapterForView(userSession, commonKey)";
				areaAplicacionPerfilAdapterVO = DefServiceLocator.getConfiguracionService().getAreaAplicacionPerfilAdapterForView(userSession, commonKey);				
				areaAplicacionPerfilAdapterVO.addMessage(BaseError.MSG_ELIMINAR, DefError.AREAAPLICACIONPERFIL_LABEL);
				actionForward = mapping.findForward(DefConstants.FWD_AREAAPLICACIONPERFIL_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAreaAplicacionPerfilAdapterForCreate(userSession)";
				areaAplicacionPerfilAdapterVO = DefServiceLocator.getConfiguracionService().getAreaAplicacionPerfilAdapterForCreate(userSession,commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_AREAAPLICACIONPERFIL_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (areaAplicacionPerfilAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + areaAplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			areaAplicacionPerfilAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + AreaAplicacionPerfilAdapter.NAME + ": "+ areaAplicacionPerfilAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			 
			saveDemodaMessages(request, areaAplicacionPerfilAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AreaAplicacionPerfilAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_AREAAPLICACIONPERFIL, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AreaAplicacionPerfilAdapter areaAplicacionPerfilAdapterVO = (AreaAplicacionPerfilAdapter) userSession.get(AreaAplicacionPerfilAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (areaAplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AreaAplicacionPerfilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AreaAplicacionPerfilAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(areaAplicacionPerfilAdapterVO, request);
			
            // Tiene errores recuperables
			if (areaAplicacionPerfilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, areaAplicacionPerfilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			}
			
			// llamada al servicio
			AreaAplicacionPerfilVO areaAplicacionPerfilVO = DefServiceLocator.getConfiguracionService().createAreaAplicacionPerfil(userSession, areaAplicacionPerfilAdapterVO.getAreaAplicacionPerfil());
			
            // Tiene errores recuperables
			if (areaAplicacionPerfilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAplicacionPerfilVO.infoString()); 
				saveDemodaErrors(request, areaAplicacionPerfilVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (areaAplicacionPerfilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + areaAplicacionPerfilVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AreaAplicacionPerfilAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AreaAplicacionPerfilAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_AREAAPLICACIONPERFIL, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AreaAplicacionPerfilAdapter areaAplicacionPerfilAdapterVO = (AreaAplicacionPerfilAdapter) userSession.get(AreaAplicacionPerfilAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (areaAplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AreaAplicacionPerfilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AreaAplicacionPerfilAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(areaAplicacionPerfilAdapterVO, request);
			
            // Tiene errores recuperables
			if (areaAplicacionPerfilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, areaAplicacionPerfilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			}
			
			// llamada al servicio
			AreaAplicacionPerfilVO areaAplicacionPerfilVO = DefServiceLocator.getConfiguracionService().updateAreaAplicacionPerfil(userSession, areaAplicacionPerfilAdapterVO.getAreaAplicacionPerfil());
			
            // Tiene errores recuperables
			if (areaAplicacionPerfilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, areaAplicacionPerfilVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (areaAplicacionPerfilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + areaAplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AreaAplicacionPerfilAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AreaAplicacionPerfilAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_AREAAPLICACIONPERFIL, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AreaAplicacionPerfilAdapter areaAplicacionPerfilAdapterVO = (AreaAplicacionPerfilAdapter) userSession.get(AreaAplicacionPerfilAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (areaAplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AreaAplicacionPerfilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AreaAplicacionPerfilAdapter.NAME); 
			}

			// llamada al servicio
			AreaAplicacionPerfilVO areaAplicacionPerfilVO = DefServiceLocator.getConfiguracionService().deleteAreaAplicacionPerfil
				(userSession, areaAplicacionPerfilAdapterVO.getAreaAplicacionPerfil());
			
            // Tiene errores recuperables
			if (areaAplicacionPerfilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAplicacionPerfilAdapterVO.infoString());
				saveDemodaErrors(request, areaAplicacionPerfilVO);				
				request.setAttribute(AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
				return mapping.findForward(DefConstants.FWD_AREAAPLICACIONPERFIL_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (areaAplicacionPerfilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + areaAplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AreaAplicacionPerfilAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AreaAplicacionPerfilAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AreaAplicacionPerfilAdapter.NAME);
		
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
			AreaAplicacionPerfilAdapter areaAplicacionPerfilAdapterVO = (AreaAplicacionPerfilAdapter) userSession.get(AreaAplicacionPerfilAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (areaAplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AreaAplicacionPerfilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AreaAplicacionPerfilAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(areaAplicacionPerfilAdapterVO, request);
			
            // Tiene errores recuperables
			if (areaAplicacionPerfilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, areaAplicacionPerfilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			}
			
            // Tiene errores recuperables
			if (areaAplicacionPerfilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, areaAplicacionPerfilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (areaAplicacionPerfilAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + areaAplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			}
			
			// Envio el VO al request
			request.setAttribute(AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AreaAplicacionPerfilAdapter.NAME, areaAplicacionPerfilAdapterVO);
			
			return mapping.findForward(DefConstants.FWD_AREAAPLICACIONPERFIL_EDIT_ADAPTER);
		
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AreaAplicacionPerfilAdapter.NAME);
		}
	}
		
		
	
} 
