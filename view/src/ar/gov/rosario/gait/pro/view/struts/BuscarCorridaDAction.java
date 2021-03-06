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
package ar.gov.rosario.gait.pro.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.pro.iface.model.CorridaSearchPage;
import ar.gov.rosario.gait.pro.iface.model.ProcesoSearchPage;
import ar.gov.rosario.gait.pro.iface.service.ProServiceLocator;
import ar.gov.rosario.gait.pro.iface.util.ProSecurityConstants;
import ar.gov.rosario.gait.pro.view.util.ProConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class BuscarCorridaDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger((BuscarCorridaDAction.class));
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, ProSecurityConstants.ABM_CORRIDA, act); 
		if (userSession==null) canAccess(request, mapping, ProSecurityConstants.ABM_CORRIDA_PROCESO, act);
		if (userSession==null) canAccess(request, mapping, ProSecurityConstants.ABM_CORRIDA_USUARIO, act);
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			CorridaSearchPage corridaSearchPageVO = ProServiceLocator.getConsultaService().getCorridaSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (corridaSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + corridaSearchPageVO.infoString()); 
				saveDemodaErrors(request, corridaSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, CorridaSearchPage.NAME, corridaSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (corridaSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + corridaSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CorridaSearchPage.NAME, corridaSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , CorridaSearchPage.NAME, corridaSearchPageVO);

			// Analisis de paramentro usuario. usuario=NombreUsuario o usuario[=]-> usa usuario actual 
			String usuario = request.getParameter("usuario");
			if (usuario != null) {
				if (usuario.length()==0)
					corridaSearchPageVO.getCorrida().setUsuario(userSession.getUserName());
				else
					corridaSearchPageVO.getCorrida().setUsuario(usuario);
			}
			
			// Analisis de paramentro proceso=codigo de proceso. 
			corridaSearchPageVO.getProceso().setCodProceso(request.getParameter("proceso"));

			if (corridaSearchPageVO.getProceso().getCodProceso() != null
					|| corridaSearchPageVO.getCorrida().getUsuario() != null) {
				corridaSearchPageVO.setPageNumber(1L);
				corridaSearchPageVO = ProServiceLocator.getConsultaService().getCorridaSearchPageResult(userSession, corridaSearchPageVO);
			}

			return mapping.findForward(ProConstants.FWD_CORRIDA_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CorridaSearchPage.NAME);
		}
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
			CorridaSearchPage corridaSearchPageVO = (CorridaSearchPage) userSession.get(CorridaSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (corridaSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + CorridaSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CorridaSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(corridaSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				corridaSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (corridaSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + corridaSearchPageVO.infoString()); 
				saveDemodaErrors(request, corridaSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, CorridaSearchPage.NAME, corridaSearchPageVO);
			}
				
			// Llamada al servicio	
			corridaSearchPageVO = ProServiceLocator.getConsultaService().getCorridaSearchPageResult(userSession, corridaSearchPageVO);			

			// Tiene errores recuperables
			if (corridaSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + corridaSearchPageVO.infoString()); 
				saveDemodaErrors(request, corridaSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, CorridaSearchPage.NAME, corridaSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (corridaSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + corridaSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, CorridaSearchPage.NAME, corridaSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(CorridaSearchPage.NAME, corridaSearchPageVO);
			// Nuleo el list result
			//corridaSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(CorridaSearchPage.NAME, corridaSearchPageVO);
			
			return mapping.findForward(ProConstants.FWD_CORRIDA_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CorridaSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			String funcName = DemodaUtil.currentMethodName();
			return forwardVerSearchPage(mapping, request, funcName, ProConstants.ACTION_ADMINISTRAR_CORRIDA);

		}

	public ActionForward downloadLogFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {

			String funcName = DemodaUtil.currentMethodName();

			UserSession userSession = getCurrentUserSession(request, mapping);			
			if (userSession == null) return forwardErrorSession(request);
			
			NavModel navModel = userSession.getNavModel();
			if (log.isDebugEnabled()) log.debug("baseForward: navModel" + navModel.infoString());
			// Bajo el adapter del userSession
			CorridaSearchPage corridaSearchPage = (CorridaSearchPage) userSession.get(CorridaSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (corridaSearchPage == null) {
				log.error("error en: "  + funcName + ": " + ProcesoSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CorridaSearchPage.NAME); 
			}
			
			CommonKey idCorrida = new CommonKey(request.getParameter("fileParam"));

			// llamada al servicio
			String fileName = ProServiceLocator.getAdpProcesoService().getLogFile(userSession, idCorrida);
			
			log.debug("fileName:"+fileName);
			
			if(fileName == null){			
				log.error("error en: "  + funcName + ": No se pudo formar logFileName para la corrida con id="+idCorrida.getId()+".");
				return forwardErrorSession(request); 
			}
			try{
				baseResponseFile(response,fileName);								
			}catch(Exception e){
				log.error("Error al abrir archivo: ", e);
				return null;
			}

			log.debug("exit: " + funcName);
			
			return null;
	}
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, CorridaSearchPage.NAME);
		
	}
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, CorridaSearchPage.NAME);
		
	}
		
	
}
