package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoSearchPage;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarPerfilAccesoDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(BuscarPerfilAccesoDAction.class);
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESO, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			PerfilAccesoSearchPage perfilAccesoSearchPageVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (perfilAccesoSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoSearchPageVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoSearchPage.NAME, perfilAccesoSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (perfilAccesoSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoSearchPage.NAME, perfilAccesoSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , PerfilAccesoSearchPage.NAME, perfilAccesoSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_PERFILACCESO_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, PerfilAccesoSearchPage.NAME);
		
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
			PerfilAccesoSearchPage perfilAccesoSearchPageVO = (PerfilAccesoSearchPage) userSession.get(PerfilAccesoSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (perfilAccesoSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + PerfilAccesoSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PerfilAccesoSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(perfilAccesoSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				perfilAccesoSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (perfilAccesoSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoSearchPageVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoSearchPage.NAME, perfilAccesoSearchPageVO);
			}
				
			// Llamada al servicio	
			perfilAccesoSearchPageVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoSearchPageResult(userSession, perfilAccesoSearchPageVO);			

			// Tiene errores recuperables
			if (perfilAccesoSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoSearchPageVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoSearchPage.NAME, perfilAccesoSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (perfilAccesoSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoSearchPage.NAME, perfilAccesoSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(PerfilAccesoSearchPage.NAME, perfilAccesoSearchPageVO);
			// Nuleo el list result
			//perfilAccesoSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(PerfilAccesoSearchPage.NAME, perfilAccesoSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_PERFILACCESO_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PERFILACCESO);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_PERFILACCESO);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PERFILACCESO);
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PERFILACCESO);

	}
	
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, PerfilAccesoSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, PerfilAccesoSearchPage.NAME);
		
	}
		
	
}
