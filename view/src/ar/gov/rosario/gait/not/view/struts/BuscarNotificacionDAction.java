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

import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.not.iface.model.NotificacionSearchPage;
import ar.gov.rosario.gait.not.iface.service.NotServiceLocator;
import ar.gov.rosario.gait.not.iface.util.NotSecurityConstants;
import ar.gov.rosario.gait.not.view.util.NotConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarNotificacionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger((BuscarNotificacionDAction.class));
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, NotSecurityConstants.ABM_NOTIFICACION, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			NotificacionSearchPage notificacionSearchPageVO = NotServiceLocator.getNotificacionService().getNotificacionSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (notificacionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + notificacionSearchPageVO.infoString()); 
				saveDemodaErrors(request, notificacionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, NotificacionSearchPage.NAME, notificacionSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (notificacionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + notificacionSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, NotificacionSearchPage.NAME, notificacionSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , NotificacionSearchPage.NAME, notificacionSearchPageVO);
			
			return mapping.findForward(NotConstants.FWD_NOTIFICACION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, NotificacionSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, NotificacionSearchPage.NAME);
		
	}
	
	public ActionForward buscar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");		
		UserSession userSession = getCurrentUserSession(request, mapping); 
		if (userSession == null) return forwardErrorSession(request);

		if (log.isDebugEnabled()) log.debug(funcName + ": Hay session!!!: nombre usuario:" + userSession.getUserName());		
		
		try {
			
			// Bajo el searchPage del userSession
			NotificacionSearchPage notificacionSearchPageVO = (NotificacionSearchPage) userSession.get(NotificacionSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (notificacionSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + NotificacionSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, NotificacionSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(notificacionSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				notificacionSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (notificacionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + notificacionSearchPageVO.infoString()); 
				saveDemodaErrors(request, notificacionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, NotificacionSearchPage.NAME, notificacionSearchPageVO);
			}
				
			// Llamada al servicio	
			notificacionSearchPageVO = NotServiceLocator.getNotificacionService().getNotificacionSearchPageResult(userSession, notificacionSearchPageVO);			

			// Tiene errores recuperables
			if (notificacionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + notificacionSearchPageVO.infoString()); 
				saveDemodaErrors(request, notificacionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, NotificacionSearchPage.NAME, notificacionSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (notificacionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + notificacionSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, NotificacionSearchPage.NAME, notificacionSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(NotificacionSearchPage.NAME, notificacionSearchPageVO);
			// Nuleo el list result
			//parametroSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(NotificacionSearchPage.NAME, notificacionSearchPageVO);
			
			return mapping.findForward(NotConstants.FWD_NOTIFICACION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, NotificacionSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_NOTIFICACION);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		return forwardAgregarSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_NOTIFICACION);
	}

	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, NotificacionSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, NotificacionSearchPage.NAME);
		
	}
		
	
}
