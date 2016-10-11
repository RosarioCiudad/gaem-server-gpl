package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.UsuarioApmSearchPage;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarUsuarioApmDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(BuscarUsuarioApmDAction.class);
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPM, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			UsuarioApmSearchPage usuarioApmSearchPageVO = ApmServiceLocator.getAplicacionService().getUsuarioApmSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (usuarioApmSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmSearchPageVO.infoString()); 
				saveDemodaErrors(request, usuarioApmSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmSearchPage.NAME, usuarioApmSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (usuarioApmSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioApmSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmSearchPage.NAME, usuarioApmSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , UsuarioApmSearchPage.NAME, usuarioApmSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_USUARIOAPM_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, UsuarioApmSearchPage.NAME);
		
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
			UsuarioApmSearchPage usuarioApmSearchPageVO = (UsuarioApmSearchPage) userSession.get(UsuarioApmSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (usuarioApmSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioApmSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioApmSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(usuarioApmSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				usuarioApmSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (usuarioApmSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmSearchPageVO.infoString()); 
				saveDemodaErrors(request, usuarioApmSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmSearchPage.NAME, usuarioApmSearchPageVO);
			}
				
			// Llamada al servicio	
			usuarioApmSearchPageVO = ApmServiceLocator.getAplicacionService().getUsuarioApmSearchPageResult(userSession, usuarioApmSearchPageVO);			

			// Tiene errores recuperables
			if (usuarioApmSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmSearchPageVO.infoString()); 
				saveDemodaErrors(request, usuarioApmSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmSearchPage.NAME, usuarioApmSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (usuarioApmSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioApmSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmSearchPage.NAME, usuarioApmSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(UsuarioApmSearchPage.NAME, usuarioApmSearchPageVO);
			// Nuleo el list result
			//usuarioApmSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(UsuarioApmSearchPage.NAME, usuarioApmSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_USUARIOAPM_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_USUARIOAPM);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_USUARIOAPM);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_USUARIOAPM);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_USUARIOAPM);

	}
	
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, UsuarioApmSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, UsuarioApmSearchPage.NAME);
		
	}
		
	
}
