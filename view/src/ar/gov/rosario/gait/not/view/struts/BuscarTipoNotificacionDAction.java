package ar.gov.rosario.gait.not.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.not.iface.model.TipoNotificacionSearchPage;
import ar.gov.rosario.gait.not.iface.service.NotServiceLocator;
import ar.gov.rosario.gait.not.iface.util.NotSecurityConstants;
import ar.gov.rosario.gait.not.view.util.NotConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarTipoNotificacionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(BuscarTipoNotificacionDAction.class);
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, NotSecurityConstants.ABM_TIPONOTIFICACION, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			TipoNotificacionSearchPage tipoNotificacionSearchPageVO = ar.gov.rosario.gait.not.iface.service.NotServiceLocator.getNotificacionService().getTipoNotificacionSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (tipoNotificacionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoNotificacionSearchPageVO.infoString()); 
				saveDemodaErrors(request, tipoNotificacionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoNotificacionSearchPage.NAME, tipoNotificacionSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (tipoNotificacionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tipoNotificacionSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TipoNotificacionSearchPage.NAME, tipoNotificacionSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , TipoNotificacionSearchPage.NAME, tipoNotificacionSearchPageVO);
			
			return mapping.findForward(NotConstants.FWD_TIPONOTIFICACION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TipoNotificacionSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, TipoNotificacionSearchPage.NAME);
		
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
			TipoNotificacionSearchPage tipoNotificacionSearchPageVO = (TipoNotificacionSearchPage) userSession.get(TipoNotificacionSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (tipoNotificacionSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + TipoNotificacionSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TipoNotificacionSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(tipoNotificacionSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				tipoNotificacionSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (tipoNotificacionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoNotificacionSearchPageVO.infoString()); 
				saveDemodaErrors(request, tipoNotificacionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoNotificacionSearchPage.NAME, tipoNotificacionSearchPageVO);
			}
				
			// Llamada al servicio	
			tipoNotificacionSearchPageVO = NotServiceLocator.getNotificacionService().getTipoNotificacionSearchPageResult(userSession, tipoNotificacionSearchPageVO);			

			// Tiene errores recuperables
			if (tipoNotificacionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoNotificacionSearchPageVO.infoString()); 
				saveDemodaErrors(request, tipoNotificacionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoNotificacionSearchPage.NAME, tipoNotificacionSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (tipoNotificacionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tipoNotificacionSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, TipoNotificacionSearchPage.NAME, tipoNotificacionSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(TipoNotificacionSearchPage.NAME, tipoNotificacionSearchPageVO);
			// Nuleo el list result
			//tipoNotificacionSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(TipoNotificacionSearchPage.NAME, tipoNotificacionSearchPageVO);
			
			return mapping.findForward(NotConstants.FWD_TIPONOTIFICACION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TipoNotificacionSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_TIPONOTIFICACION);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		//Se utiliza uno de los dos return, segun sea un encabezado detalle o no.
		return forwardAgregarSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_TIPONOTIFICACION);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_TIPONOTIFICACION);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_TIPONOTIFICACION);

	}
	
	public ActionForward activar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardActivarSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_TIPONOTIFICACION);			
	}
	
	public ActionForward desactivar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardDesactivarSearchPage(mapping, request, funcName, NotConstants.ACTION_ADMINISTRAR_TIPONOTIFICACION);
	}
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, TipoNotificacionSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, TipoNotificacionSearchPage.NAME);
		
	}
		
	
}
