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

import ar.gov.rosario.gait.apm.iface.model.PanicoSearchPage;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarPanicoDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(BuscarPanicoDAction.class);
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PANICO, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			PanicoSearchPage panicoSearchPageVO = ApmServiceLocator.getPanicoService().getPanicoSearchPageInit(userSession);
			
			// Tiene errores recuperables o no recuperables
			if (panicoSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + panicoSearchPageVO.infoString()); 
				saveDemodaErrors(request, panicoSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, PanicoSearchPage.NAME, panicoSearchPageVO);
			} 
			if (panicoSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + panicoSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PanicoSearchPage.NAME, panicoSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , PanicoSearchPage.NAME, panicoSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_PANICO_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PanicoSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, PanicoSearchPage.NAME);
		
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		return baseVolver(mapping, form, request, response, PanicoSearchPage.NAME);
		
	}
	
	public ActionForward ver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PANICO);
		
	}
	
	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PANICO);
		
	}
	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PANICO);
		
	}
	
	public ActionForward buscar(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");		
		UserSession userSession = getCurrentUserSession(request, mapping); 
		if (userSession == null) return forwardErrorSession(request);
		
		try {
			
			// Bajo el searchPage del userSession
			PanicoSearchPage panicoSearchPageVO = (PanicoSearchPage) userSession.get(PanicoSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (panicoSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + PanicoSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PanicoSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(panicoSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				panicoSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (panicoSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + panicoSearchPageVO.infoString()); 
				saveDemodaErrors(request, panicoSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, PanicoSearchPage.NAME, panicoSearchPageVO);
			}

			// Llamada al servicio
			panicoSearchPageVO = ApmServiceLocator.getPanicoService().getPanicoSearchPageResult(userSession, panicoSearchPageVO);			

			// Tiene errores recuperables o no recuperables
			if (panicoSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + panicoSearchPageVO.infoString()); 
				saveDemodaErrors(request, panicoSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, PanicoSearchPage.NAME, panicoSearchPageVO);
			}
			if (panicoSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + panicoSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, PanicoSearchPage.NAME, panicoSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(PanicoSearchPage.NAME, panicoSearchPageVO);
			// Subo en el el searchPage al userSession
			userSession.put(PanicoSearchPage.NAME, panicoSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_PANICO_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PanicoSearchPage.NAME);
		}
	}


}
