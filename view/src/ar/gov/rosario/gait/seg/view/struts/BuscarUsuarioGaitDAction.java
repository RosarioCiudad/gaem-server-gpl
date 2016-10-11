package ar.gov.rosario.gait.seg.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.seg.iface.model.UsuarioGaitSearchPage;
import ar.gov.rosario.gait.seg.iface.service.SegServiceLocator;
import ar.gov.rosario.gait.seg.iface.util.SegSecurityConstants;
import ar.gov.rosario.gait.seg.view.util.SegConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarUsuarioGaitDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger((BuscarUsuarioGaitDAction.class));
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, SegSecurityConstants.ABM_USUARIOGAIT, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			UsuarioGaitSearchPage usuarioGaitSearchPageVO = SegServiceLocator.getSeguridadService().getUsuarioGaitSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (usuarioGaitSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioGaitSearchPageVO.infoString()); 
				saveDemodaErrors(request, usuarioGaitSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioGaitSearchPage.NAME, usuarioGaitSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (usuarioGaitSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioGaitSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioGaitSearchPage.NAME, usuarioGaitSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , UsuarioGaitSearchPage.NAME, usuarioGaitSearchPageVO);
			
			return mapping.findForward(SegConstants.FWD_USUARIOGAIT_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioGaitSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, UsuarioGaitSearchPage.NAME);
		
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
			UsuarioGaitSearchPage usuarioGaitSearchPageVO = (UsuarioGaitSearchPage) userSession.get(UsuarioGaitSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (usuarioGaitSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioGaitSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioGaitSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(usuarioGaitSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				usuarioGaitSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (usuarioGaitSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioGaitSearchPageVO.infoString()); 
				saveDemodaErrors(request, usuarioGaitSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioGaitSearchPage.NAME, usuarioGaitSearchPageVO);
			}
				
			// Llamada al servicio	
			usuarioGaitSearchPageVO = SegServiceLocator.getSeguridadService().getUsuarioGaitSearchPageResult(userSession, usuarioGaitSearchPageVO);			

			// Tiene errores recuperables
			if (usuarioGaitSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioGaitSearchPageVO.infoString()); 
				saveDemodaErrors(request, usuarioGaitSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioGaitSearchPage.NAME, usuarioGaitSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (usuarioGaitSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioGaitSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioGaitSearchPage.NAME, usuarioGaitSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(UsuarioGaitSearchPage.NAME, usuarioGaitSearchPageVO);
			// Nuleo el list result
			//usuarioGaitSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(UsuarioGaitSearchPage.NAME, usuarioGaitSearchPageVO);
			
			return mapping.findForward(SegConstants.FWD_USUARIOGAIT_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioGaitSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, SegConstants.ACTION_ADMINISTRAR_USUARIOGAIT);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarSearchPage(mapping, request, funcName, SegConstants.ACTION_ADMINISTRAR_USUARIOGAIT);

	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, SegConstants.ACTION_ADMINISTRAR_USUARIOGAIT);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, SegConstants.ACTION_ADMINISTRAR_USUARIOGAIT);

	}
	
	public ActionForward activar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardActivarSearchPage(mapping, request, funcName, SegConstants.ACTION_ADMINISTRAR_USUARIOGAIT);			
	}
	
	public ActionForward desactivar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardDesactivarSearchPage(mapping, request, funcName, SegConstants.ACTION_ADMINISTRAR_USUARIOGAIT);
	}
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, UsuarioGaitSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, UsuarioGaitSearchPage.NAME);
		
	}
		
	
}
