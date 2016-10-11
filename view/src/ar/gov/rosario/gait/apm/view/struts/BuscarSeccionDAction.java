package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.SeccionSearchPage;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarSeccionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(BuscarSeccionDAction.class);
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_SECCION, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			SeccionSearchPage seccionSearchPageVO = ApmServiceLocator.getAplicacionService().getSeccionSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (seccionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + seccionSearchPageVO.infoString()); 
				saveDemodaErrors(request, seccionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, SeccionSearchPage.NAME, seccionSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (seccionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + seccionSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, SeccionSearchPage.NAME, seccionSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , SeccionSearchPage.NAME, seccionSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_SECCION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, SeccionSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, SeccionSearchPage.NAME);
		
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
			SeccionSearchPage seccionSearchPageVO = (SeccionSearchPage) userSession.get(SeccionSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (seccionSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + SeccionSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, SeccionSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(seccionSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				seccionSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (seccionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + seccionSearchPageVO.infoString()); 
				saveDemodaErrors(request, seccionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, SeccionSearchPage.NAME, seccionSearchPageVO);
			}
				
			// Llamada al servicio	
			seccionSearchPageVO = ApmServiceLocator.getAplicacionService().getSeccionSearchPageResult(userSession, seccionSearchPageVO);			

			// Tiene errores recuperables
			if (seccionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + seccionSearchPageVO.infoString()); 
				saveDemodaErrors(request, seccionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, SeccionSearchPage.NAME, seccionSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (seccionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + seccionSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, SeccionSearchPage.NAME, seccionSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(SeccionSearchPage.NAME, seccionSearchPageVO);
			// Nuleo el list result
			//seccionSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(SeccionSearchPage.NAME, seccionSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_SECCION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, SeccionSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_SECCION);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		//Se utiliza uno de los dos return, segun sea un encabezado detalle o no.
		return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_SECCION);
		//return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_SECCION);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_SECCION);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_SECCION);

	}
	
	public ActionForward activar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardActivarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_SECCION);			
	}
	
	public ActionForward desactivar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardDesactivarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_SECCION);
	}
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, SeccionSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, SeccionSearchPage.NAME);
		
	}
		
	
}
