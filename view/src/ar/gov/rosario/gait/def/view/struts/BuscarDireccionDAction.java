package ar.gov.rosario.gait.def.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.def.iface.model.DireccionSearchPage;
import ar.gov.rosario.gait.def.iface.service.DefServiceLocator;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;
import ar.gov.rosario.gait.def.view.util.DefConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarDireccionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger((BuscarDireccionDAction.class));
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_DIRECCION, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			log.debug("Test" + userSession.getNavModel().getSelectedId());
			
			DireccionSearchPage direccionSearchPageVO = DefServiceLocator.getConfiguracionService().getDireccionSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (direccionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionSearchPageVO.infoString()); 
				saveDemodaErrors(request, direccionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionSearchPage.NAME, direccionSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (direccionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + direccionSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionSearchPage.NAME, direccionSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , DireccionSearchPage.NAME, direccionSearchPageVO);
			
			return mapping.findForward(DefConstants.FWD_DIRECCION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, DireccionSearchPage.NAME);
		
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
			DireccionSearchPage direccionSearchPageVO = (DireccionSearchPage) userSession.get(DireccionSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (direccionSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + DireccionSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DireccionSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(direccionSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				direccionSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (direccionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionSearchPageVO.infoString()); 
				saveDemodaErrors(request, direccionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionSearchPage.NAME, direccionSearchPageVO);
			}
				
			// Llamada al servicio	
			direccionSearchPageVO = DefServiceLocator.getConfiguracionService().getDireccionSearchPageResult(userSession, direccionSearchPageVO);			

			// Tiene errores recuperables
			if (direccionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionSearchPageVO.infoString()); 
				saveDemodaErrors(request, direccionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionSearchPage.NAME, direccionSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (direccionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + direccionSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionSearchPage.NAME, direccionSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(DireccionSearchPage.NAME, direccionSearchPageVO);
			// Nuleo el list result
			//direccionSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(DireccionSearchPage.NAME, direccionSearchPageVO);
			
			return mapping.findForward(DefConstants.FWD_DIRECCION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_DIRECCION);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		return forwardAgregarSearchPage(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_ENC_DIRECCION);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_DIRECCION);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_DIRECCION);

	}
	
	public ActionForward activar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardActivarSearchPage(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_DIRECCION);			
	}
	
	public ActionForward desactivar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardDesactivarSearchPage(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_DIRECCION);
	}
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, DireccionSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, DireccionSearchPage.NAME);
		
	}
		
	
}
