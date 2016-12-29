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

import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoUsuarioAdapter;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoUsuarioVO;
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

public final class AdministrarPerfilAccesoUsuarioDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarPerfilAccesoUsuarioDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESOUSUARIO, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		PerfilAccesoUsuarioAdapter perfilAccesoUsuarioAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getPerfilAccesoUsuarioAdapterForView(userSession, commonKey)";
				perfilAccesoUsuarioAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoUsuarioAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESOUSUARIO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getPerfilAccesoUsuarioAdapterForUpdate(userSession, commonKey)";
				perfilAccesoUsuarioAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoUsuarioAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESOUSUARIO_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getPerfilAccesoUsuarioAdapterForView(userSession, commonKey)";
				perfilAccesoUsuarioAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoUsuarioAdapterForView(userSession, commonKey);				
				perfilAccesoUsuarioAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.PERFILACCESOUSUARIO_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESOUSUARIO_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getPerfilAccesoUsuarioAdapterForCreate(userSession)";
				perfilAccesoUsuarioAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoUsuarioAdapterForCreate(userSession,commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESOUSUARIO_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (perfilAccesoUsuarioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + perfilAccesoUsuarioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			perfilAccesoUsuarioAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + PerfilAccesoUsuarioAdapter.NAME + ": "+ perfilAccesoUsuarioAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			// Subo el apdater al userSession
			userSession.put(PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			 
			saveDemodaMessages(request, perfilAccesoUsuarioAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoUsuarioAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESOUSUARIO, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			PerfilAccesoUsuarioAdapter perfilAccesoUsuarioAdapterVO = (PerfilAccesoUsuarioAdapter) userSession.get(PerfilAccesoUsuarioAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (perfilAccesoUsuarioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + PerfilAccesoUsuarioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PerfilAccesoUsuarioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(perfilAccesoUsuarioAdapterVO, request);
			
            // Tiene errores recuperables
			if (perfilAccesoUsuarioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoUsuarioAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoUsuarioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			}
			
			// llamada al servicio
			PerfilAccesoUsuarioVO perfilAccesoUsuarioVO = ApmServiceLocator.getAplicacionService().createPerfilAccesoUsuario(userSession, perfilAccesoUsuarioAdapterVO.getPerfilAccesoUsuario());
			
            // Tiene errores recuperables
			if (perfilAccesoUsuarioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoUsuarioVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoUsuarioVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (perfilAccesoUsuarioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoUsuarioVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, PerfilAccesoUsuarioAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoUsuarioAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESOUSUARIO, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			PerfilAccesoUsuarioAdapter perfilAccesoUsuarioAdapterVO = (PerfilAccesoUsuarioAdapter) userSession.get(PerfilAccesoUsuarioAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (perfilAccesoUsuarioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + PerfilAccesoUsuarioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PerfilAccesoUsuarioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(perfilAccesoUsuarioAdapterVO, request);
			
            // Tiene errores recuperables
			if (perfilAccesoUsuarioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoUsuarioAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoUsuarioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			}
			
			// llamada al servicio
			PerfilAccesoUsuarioVO perfilAccesoUsuarioVO = ApmServiceLocator.getAplicacionService().updatePerfilAccesoUsuario(userSession, perfilAccesoUsuarioAdapterVO.getPerfilAccesoUsuario());
			
            // Tiene errores recuperables
			if (perfilAccesoUsuarioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoUsuarioAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoUsuarioVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (perfilAccesoUsuarioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoUsuarioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, PerfilAccesoUsuarioAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoUsuarioAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESOUSUARIO, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			PerfilAccesoUsuarioAdapter perfilAccesoUsuarioAdapterVO = (PerfilAccesoUsuarioAdapter) userSession.get(PerfilAccesoUsuarioAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (perfilAccesoUsuarioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + PerfilAccesoUsuarioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PerfilAccesoUsuarioAdapter.NAME); 
			}

			// llamada al servicio
			PerfilAccesoUsuarioVO perfilAccesoUsuarioVO = ApmServiceLocator.getAplicacionService().deletePerfilAccesoUsuario
				(userSession, perfilAccesoUsuarioAdapterVO.getPerfilAccesoUsuario());
			
            // Tiene errores recuperables
			if (perfilAccesoUsuarioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoUsuarioAdapterVO.infoString());
				saveDemodaErrors(request, perfilAccesoUsuarioVO);				
				request.setAttribute(PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
				return mapping.findForward(ApmConstants.FWD_PERFILACCESOUSUARIO_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (perfilAccesoUsuarioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoUsuarioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, PerfilAccesoUsuarioAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoUsuarioAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, PerfilAccesoUsuarioAdapter.NAME);
		
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
			PerfilAccesoUsuarioAdapter perfilAccesoUsuarioAdapterVO = (PerfilAccesoUsuarioAdapter) userSession.get(PerfilAccesoUsuarioAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (perfilAccesoUsuarioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + PerfilAccesoUsuarioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PerfilAccesoUsuarioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(perfilAccesoUsuarioAdapterVO, request);
			
            // Tiene errores recuperables
			if (perfilAccesoUsuarioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoUsuarioAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoUsuarioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			}
			
			// llamada al servicio
			//perfilAccesoUsuarioAdapterVO = ApmServiceLocator.getPerfilAccesoUsuarioService().getPerfilAccesoUsuarioAdapterParam(userSession, perfilAccesoUsuarioAdapterVO);
			
            // Tiene errores recuperables
			if (perfilAccesoUsuarioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoUsuarioAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoUsuarioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (perfilAccesoUsuarioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoUsuarioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			}
			
			// Envio el VO al request
			request.setAttribute(PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			// Subo el apdater al userSession
			userSession.put(PerfilAccesoUsuarioAdapter.NAME, perfilAccesoUsuarioAdapterVO);
			
			return mapping.findForward(ApmConstants.FWD_PERFILACCESOUSUARIO_EDIT_ADAPTER);
		
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoUsuarioAdapter.NAME);
		}
	}
		
		
	
} 
