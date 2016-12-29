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
package ar.gov.rosario.gait.not.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.not.iface.model.NotificacionAdapter;
import ar.gov.rosario.gait.not.iface.model.NotificacionVO;
import ar.gov.rosario.gait.not.iface.service.NotServiceLocator;
import ar.gov.rosario.gait.not.iface.util.NotSecurityConstants;
import ar.gov.rosario.gait.not.view.util.NotConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarNotificacionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger((AdministrarNotificacionDAction.class));

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, NotSecurityConstants.ABM_NOTIFICACION, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		NotificacionAdapter notificacionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			log.debug(" ########  commonKey: " + commonKey.getId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getNotificacionAdapterForView(userSession, commonKey)";
				notificacionAdapterVO = NotServiceLocator.getNotificacionService().getNotificacionAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(NotConstants.FWD_NOTIFICACION_VIEW_ADAPTER);
			}
			
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getNotificacionAdapterForCreate(userSession)";
				notificacionAdapterVO = NotServiceLocator.getNotificacionService().getNotificacionAdapterForCreate(userSession);
				actionForward = mapping.findForward(NotConstants.FWD_NOTIFICACION_EDIT_ADAPTER);				
			}
		
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (notificacionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + notificacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, NotificacionAdapter.NAME, notificacionAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			notificacionAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + NotificacionAdapter.NAME + ": "+ notificacionAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(NotificacionAdapter.NAME, notificacionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(NotificacionAdapter.NAME, notificacionAdapterVO);
			 
			saveDemodaMessages(request, notificacionAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, NotificacionAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, NotSecurityConstants.ABM_NOTIFICACION, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			NotificacionAdapter notificacionAdapterVO = (NotificacionAdapter) userSession.get(NotificacionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (notificacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + NotificacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, NotificacionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(notificacionAdapterVO, request);
			
            // Tiene errores recuperables
			if (notificacionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + notificacionAdapterVO.infoString()); 
				saveDemodaErrors(request, notificacionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, NotificacionAdapter.NAME, notificacionAdapterVO);
			}
			
			// llamada al servicio
			NotificacionVO notificacionVO = NotServiceLocator.getNotificacionService().createNotificacion(userSession, notificacionAdapterVO.getNotificacion());
			
            // Tiene errores recuperables
			if (notificacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + notificacionVO.infoString()); 
				saveDemodaErrors(request, notificacionVO);
				return forwardErrorRecoverable(mapping, request, userSession, NotificacionAdapter.NAME, notificacionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (notificacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + notificacionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, NotificacionAdapter.NAME, notificacionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, NotificacionAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, NotificacionAdapter.NAME);
		}
	}

	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, NotificacionAdapter.NAME);
		
	}
	
}
