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
package ar.gov.rosario.gait.frm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.frm.iface.model.NumeracionSearchPage;
import ar.gov.rosario.gait.frm.iface.service.FrmServiceLocator;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import ar.gov.rosario.gait.frm.view.util.FrmConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public class BuscarNumeracionDAction extends BaseDispatchAction{


	private Logger log = Logger.getLogger((BuscarNumeracionDAction.class));
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_NUMERACION, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			NumeracionSearchPage numeracionSearchPageVO = FrmServiceLocator.getNumeracionService().getNumeracionSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (numeracionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + numeracionSearchPageVO.infoString()); 
				saveDemodaErrors(request, numeracionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, NumeracionSearchPage.NAME, numeracionSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (numeracionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + numeracionSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, NumeracionSearchPage.NAME, numeracionSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , NumeracionSearchPage.NAME, numeracionSearchPageVO);
			
			return mapping.findForward(FrmConstants.FWD_NUMERACION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, NumeracionSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, NumeracionSearchPage.NAME);
		
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
			NumeracionSearchPage numeracionSearchPageVO = (NumeracionSearchPage) userSession.get(NumeracionSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (numeracionSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + NumeracionSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, NumeracionSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(numeracionSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				numeracionSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (numeracionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + numeracionSearchPageVO.infoString()); 
				saveDemodaErrors(request, numeracionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, NumeracionSearchPage.NAME, numeracionSearchPageVO);
			}
				
			// Llamada al servicio	
			numeracionSearchPageVO = FrmServiceLocator.getNumeracionService().getNumeracionSearchPageResult(userSession, numeracionSearchPageVO);			

			// Tiene errores recuperables
			if (numeracionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + numeracionSearchPageVO.infoString()); 
				saveDemodaErrors(request, numeracionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, NumeracionSearchPage.NAME, numeracionSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (numeracionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + numeracionSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, NumeracionSearchPage.NAME, numeracionSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(NumeracionSearchPage.NAME, numeracionSearchPageVO);
			// Nuleo el list result
			//numeracionSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(NumeracionSearchPage.NAME, numeracionSearchPageVO);
			
			return mapping.findForward(FrmConstants.FWD_NUMERACION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, NumeracionSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_NUMERACION);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		return forwardAgregarSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_NUMERACION);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_NUMERACION);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_NUMERACION);

	}
	
		
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, NumeracionSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, NumeracionSearchPage.NAME);
		
	}

}


