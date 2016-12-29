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

import ar.gov.rosario.gait.apm.iface.model.AplicacionTablaSearchPage;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarAplicacionTablaDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(BuscarAplicacionTablaDAction.class);
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONTABLA, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			AplicacionTablaSearchPage aplicacionTablaSearchPageVO = ApmServiceLocator.getAplicacionService().getAplicacionTablaSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (aplicacionTablaSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTablaSearchPageVO.infoString()); 
				saveDemodaErrors(request, aplicacionTablaSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTablaSearchPage.NAME, aplicacionTablaSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (aplicacionTablaSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionTablaSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionTablaSearchPage.NAME, aplicacionTablaSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , AplicacionTablaSearchPage.NAME, aplicacionTablaSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_APLICACIONTABLA_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionTablaSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, AplicacionTablaSearchPage.NAME);
		
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
			AplicacionTablaSearchPage aplicacionTablaSearchPageVO = (AplicacionTablaSearchPage) userSession.get(AplicacionTablaSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionTablaSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionTablaSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionTablaSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(aplicacionTablaSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				aplicacionTablaSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (aplicacionTablaSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTablaSearchPageVO.infoString()); 
				saveDemodaErrors(request, aplicacionTablaSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTablaSearchPage.NAME, aplicacionTablaSearchPageVO);
			}
				
			// Llamada al servicio	
			aplicacionTablaSearchPageVO = ApmServiceLocator.getAplicacionService().getAplicacionTablaSearchPageResult(userSession, aplicacionTablaSearchPageVO);			

			// Tiene errores recuperables
			if (aplicacionTablaSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTablaSearchPageVO.infoString()); 
				saveDemodaErrors(request, aplicacionTablaSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTablaSearchPage.NAME, aplicacionTablaSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionTablaSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionTablaSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionTablaSearchPage.NAME, aplicacionTablaSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(AplicacionTablaSearchPage.NAME, aplicacionTablaSearchPageVO);
			// Nuleo el list result
			//aplicacionTablaSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(AplicacionTablaSearchPage.NAME, aplicacionTablaSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_APLICACIONTABLA_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionTablaSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONTABLA);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		//Se utiliza uno de los dos return, segun sea un encabezado detalle o no.
		return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONTABLA);
		//return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_APLICACIONTABLA);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONTABLA);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONTABLA);

	}
	
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, AplicacionTablaSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AplicacionTablaSearchPage.NAME);
		
	}
		
	
}
