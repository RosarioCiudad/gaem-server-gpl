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
import ar.gov.rosario.gait.not.iface.model.EstadoNotificacionSearchPage;
import ar.gov.rosario.gait.not.iface.service.NotServiceLocator;
import ar.gov.rosario.gait.not.iface.util.NotSecurityConstants;
import ar.gov.rosario.gait.not.view.util.NotConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarEstadoNotificacionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(BuscarEstadoNotificacionDAction.class);
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, NotSecurityConstants.ABM_ESTADONOTIFICACION, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			EstadoNotificacionSearchPage estadoNotificacionSearchPageVO = ar.gov.rosario.gait.not.iface.service.NotServiceLocator.getNotificacionService().getEstadoNotificacionSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (estadoNotificacionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoNotificacionSearchPageVO.infoString()); 
				saveDemodaErrors(request, estadoNotificacionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoNotificacionSearchPage.NAME, estadoNotificacionSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (estadoNotificacionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + estadoNotificacionSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, EstadoNotificacionSearchPage.NAME, estadoNotificacionSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , EstadoNotificacionSearchPage.NAME, estadoNotificacionSearchPageVO);
			
			return mapping.findForward(NotConstants.FWD_ESTADONOTIFICACION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, EstadoNotificacionSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, EstadoNotificacionSearchPage.NAME);
		
	}
	
	public ActionForward buscar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");		
		UserSession userSession = getCurrentUserSession(request, mapping); 
		if (userSession == null) return forwardErrorSession(request);
		
		try {
			
			// Bajo el searchPage del userSession
			EstadoNotificacionSearchPage estadoNotificacionSearchPageVO = (EstadoNotificacionSearchPage) userSession.get(EstadoNotificacionSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (estadoNotificacionSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + EstadoNotificacionSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, EstadoNotificacionSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(estadoNotificacionSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				estadoNotificacionSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (estadoNotificacionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoNotificacionSearchPageVO.infoString()); 
				saveDemodaErrors(request, estadoNotificacionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoNotificacionSearchPage.NAME, estadoNotificacionSearchPageVO);
			}
				
			// Llamada al servicio	
			estadoNotificacionSearchPageVO = NotServiceLocator.getNotificacionService().getEstadoNotificacionSearchPageResult(userSession, estadoNotificacionSearchPageVO);			

			// Tiene errores recuperables
			if (estadoNotificacionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoNotificacionSearchPageVO.infoString()); 
				saveDemodaErrors(request, estadoNotificacionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoNotificacionSearchPage.NAME, estadoNotificacionSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (estadoNotificacionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + estadoNotificacionSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, EstadoNotificacionSearchPage.NAME, estadoNotificacionSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(EstadoNotificacionSearchPage.NAME, estadoNotificacionSearchPageVO);
			// Nuleo el list result
			//estadoNotificacionSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(EstadoNotificacionSearchPage.NAME, estadoNotificacionSearchPageVO);
			
			return mapping.findForward(NotConstants.FWD_ESTADONOTIFICACION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, EstadoNotificacionSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_ESTADONOTIFICACION);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		//Se utiliza uno de los dos return, segun sea un encabezado detalle o no.
		return forwardAgregarSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_ESTADONOTIFICACION);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_ESTADONOTIFICACION);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_ESTADONOTIFICACION);

	}
	
	public ActionForward activar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardActivarSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_ESTADONOTIFICACION);			
	}
	
	public ActionForward desactivar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardDesactivarSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_ESTADONOTIFICACION);
	}
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, EstadoNotificacionSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, EstadoNotificacionSearchPage.NAME);
		
	}
		
	
}
