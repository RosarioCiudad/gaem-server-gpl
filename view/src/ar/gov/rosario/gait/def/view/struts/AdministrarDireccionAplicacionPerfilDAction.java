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
import ar.gov.rosario.gait.def.iface.model.DireccionAplicacionPerfilAdapter;
import ar.gov.rosario.gait.def.iface.model.DireccionAplicacionPerfilVO;
import ar.gov.rosario.gait.def.iface.service.DefServiceLocator;
import ar.gov.rosario.gait.def.iface.util.DefError;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;
import ar.gov.rosario.gait.def.view.util.DefConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarDireccionAplicacionPerfilDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger((AdministrarDireccionAplicacionPerfilDAction.class));

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_DIRECCIONAPLICACIONPERFIL, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		DireccionAplicacionPerfilAdapter direccionAplicacionPerfilAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			log.debug(" ########  commonKey: " + commonKey.getId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getDireccionAplicacionPerfilAdapterForView(userSession, commonKey)";
				direccionAplicacionPerfilAdapterVO = DefServiceLocator.getConfiguracionService().getDireccionAplicacionPerfilAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_DIRECCIONAPLICACIONPERFIL_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getDireccionAplicacionPerfilAdapterForUpdate(userSession, commonKey)";
				direccionAplicacionPerfilAdapterVO = DefServiceLocator.getConfiguracionService().getDireccionAplicacionPerfilAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_DIRECCIONAPLICACIONPERFIL_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getDireccionAplicacionPerfilAdapterForView(userSession, commonKey)";
				direccionAplicacionPerfilAdapterVO = DefServiceLocator.getConfiguracionService().getDireccionAplicacionPerfilAdapterForView(userSession, commonKey);				
				direccionAplicacionPerfilAdapterVO.addMessage(BaseError.MSG_ELIMINAR, DefError.DIRECCIONAPLICACIONPERFIL_LABEL);
				actionForward = mapping.findForward(DefConstants.FWD_DIRECCIONAPLICACIONPERFIL_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getDireccionAplicacionPerfilAdapterForCreate(userSession)";
				direccionAplicacionPerfilAdapterVO = DefServiceLocator.getConfiguracionService().getDireccionAplicacionPerfilAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_DIRECCIONAPLICACIONPERFIL_EDIT_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_ACTIVAR)) {
				stringServicio = "getDireccionAplicacionPerfilAdapterForView(userSession)";
				direccionAplicacionPerfilAdapterVO = DefServiceLocator.getConfiguracionService().getDireccionAplicacionPerfilAdapterForView(userSession, commonKey);
				direccionAplicacionPerfilAdapterVO.addMessage(BaseError.MSG_ACTIVAR, DefError.DIRECCIONAPLICACIONPERFIL_LABEL);
				actionForward = mapping.findForward(DefConstants.FWD_DIRECCIONAPLICACIONPERFIL_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_DESACTIVAR)) {
				stringServicio = "getDireccionAplicacionPerfilAdapterForView(userSession)";
				direccionAplicacionPerfilAdapterVO = DefServiceLocator.getConfiguracionService().getDireccionAplicacionPerfilAdapterForView(userSession, commonKey);
				direccionAplicacionPerfilAdapterVO.addMessage(BaseError.MSG_DESACTIVAR, DefError.DIRECCIONAPLICACIONPERFIL_LABEL);
				actionForward = mapping.findForward(DefConstants.FWD_DIRECCIONAPLICACIONPERFIL_VIEW_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (direccionAplicacionPerfilAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + direccionAplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionAplicacionPerfilAdapter.NAME, direccionAplicacionPerfilAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			direccionAplicacionPerfilAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + DireccionAplicacionPerfilAdapter.NAME + ": "+ direccionAplicacionPerfilAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(DireccionAplicacionPerfilAdapter.NAME, direccionAplicacionPerfilAdapterVO);
			// Subo el apdater al userSession
			userSession.put(DireccionAplicacionPerfilAdapter.NAME, direccionAplicacionPerfilAdapterVO);
			 
			saveDemodaMessages(request, direccionAplicacionPerfilAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionAplicacionPerfilAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_DIRECCIONAPLICACIONPERFIL, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			DireccionAplicacionPerfilAdapter direccionAplicacionPerfilAdapterVO = (DireccionAplicacionPerfilAdapter) userSession.get(DireccionAplicacionPerfilAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (direccionAplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + DireccionAplicacionPerfilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DireccionAplicacionPerfilAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(direccionAplicacionPerfilAdapterVO, request);
			
            // Tiene errores recuperables
			if (direccionAplicacionPerfilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, direccionAplicacionPerfilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAplicacionPerfilAdapter.NAME, direccionAplicacionPerfilAdapterVO);
			}
			
			// llamada al servicio
			DireccionAplicacionPerfilVO direccionAplicacionPerfilVO = DefServiceLocator.getConfiguracionService().createDireccionAplicacionPerfil(userSession, direccionAplicacionPerfilAdapterVO.getDireccionAplicacionPerfil());
			
            // Tiene errores recuperables
			if (direccionAplicacionPerfilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAplicacionPerfilVO.infoString()); 
				saveDemodaErrors(request, direccionAplicacionPerfilVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAplicacionPerfilAdapter.NAME, direccionAplicacionPerfilAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (direccionAplicacionPerfilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + direccionAplicacionPerfilVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionAplicacionPerfilAdapter.NAME, direccionAplicacionPerfilAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, DireccionAplicacionPerfilAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionAplicacionPerfilAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_DIRECCIONAPLICACIONPERFIL, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			DireccionAplicacionPerfilAdapter direccionAplicacionPerfilAdapterVO = (DireccionAplicacionPerfilAdapter) userSession.get(DireccionAplicacionPerfilAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (direccionAplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + DireccionAplicacionPerfilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DireccionAplicacionPerfilAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(direccionAplicacionPerfilAdapterVO, request);
			
            // Tiene errores recuperables
			if (direccionAplicacionPerfilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, direccionAplicacionPerfilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAplicacionPerfilAdapter.NAME, direccionAplicacionPerfilAdapterVO);
			}
			
			// llamada al servicio
			DireccionAplicacionPerfilVO direccionAplicacionPerfilVO = DefServiceLocator.getConfiguracionService().updateDireccionAplicacionPerfil(userSession, direccionAplicacionPerfilAdapterVO.getDireccionAplicacionPerfil());
			
            // Tiene errores recuperables
			if (direccionAplicacionPerfilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, direccionAplicacionPerfilVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAplicacionPerfilAdapter.NAME, direccionAplicacionPerfilAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (direccionAplicacionPerfilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + direccionAplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionAplicacionPerfilAdapter.NAME, direccionAplicacionPerfilAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, DireccionAplicacionPerfilAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionAplicacionPerfilAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_DIRECCIONAPLICACIONPERFIL, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			DireccionAplicacionPerfilAdapter direccionAplicacionPerfilAdapterVO = (DireccionAplicacionPerfilAdapter) userSession.get(DireccionAplicacionPerfilAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (direccionAplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + DireccionAplicacionPerfilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DireccionAplicacionPerfilAdapter.NAME); 
			}

			// llamada al servicio
			DireccionAplicacionPerfilVO direccionAplicacionPerfilVO = DefServiceLocator.getConfiguracionService().deleteDireccionAplicacionPerfil
				(userSession, direccionAplicacionPerfilAdapterVO.getDireccionAplicacionPerfil());
			
            // Tiene errores recuperables
			if (direccionAplicacionPerfilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAplicacionPerfilAdapterVO.infoString());
				saveDemodaErrors(request, direccionAplicacionPerfilVO);				
				request.setAttribute(DireccionAplicacionPerfilAdapter.NAME, direccionAplicacionPerfilAdapterVO);
				return mapping.findForward(DefConstants.FWD_DIRECCIONAPLICACIONPERFIL_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (direccionAplicacionPerfilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + direccionAplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionAplicacionPerfilAdapter.NAME, direccionAplicacionPerfilAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, DireccionAplicacionPerfilAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionAplicacionPerfilAdapter.NAME);
		}
	}
	
	
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, DireccionAplicacionPerfilAdapter.NAME);
		
	}
	
}