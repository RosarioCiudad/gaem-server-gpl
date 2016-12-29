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

import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilSearchPage;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarAplicacionPerfilDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(BuscarAplicacionPerfilDAction.class);
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFIL, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			AplicacionPerfilSearchPage aplicacionPerfilSearchPageVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (aplicacionPerfilSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSearchPageVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilSearchPage.NAME, aplicacionPerfilSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (aplicacionPerfilSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilSearchPage.NAME, aplicacionPerfilSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , AplicacionPerfilSearchPage.NAME, aplicacionPerfilSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_APLICACIONPERFIL_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, AplicacionPerfilSearchPage.NAME);
		
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
			AplicacionPerfilSearchPage aplicacionPerfilSearchPageVO = (AplicacionPerfilSearchPage) userSession.get(AplicacionPerfilSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionPerfilSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(aplicacionPerfilSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				aplicacionPerfilSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (aplicacionPerfilSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSearchPageVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilSearchPage.NAME, aplicacionPerfilSearchPageVO);
			}
				
			// Llamada al servicio	
			aplicacionPerfilSearchPageVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilSearchPageResult(userSession, aplicacionPerfilSearchPageVO);			

			// Tiene errores recuperables
			if (aplicacionPerfilSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSearchPageVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilSearchPage.NAME, aplicacionPerfilSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionPerfilSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilSearchPage.NAME, aplicacionPerfilSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(AplicacionPerfilSearchPage.NAME, aplicacionPerfilSearchPageVO);
			// Nuleo el list result
			//aplicacionPerfilSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(AplicacionPerfilSearchPage.NAME, aplicacionPerfilSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_APLICACIONPERFIL_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPERFIL);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_APLICACIONPERFIL);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPERFIL);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPERFIL);

	}

//Empieza	QVPOP
	public ActionForward clonar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			String funcName = DemodaUtil.currentMethodName();
			return forwardClonarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPERFIL);

		}
//Termina QVPOP	

	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, AplicacionPerfilSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AplicacionPerfilSearchPage.NAME);
		
	}
		
	
}
