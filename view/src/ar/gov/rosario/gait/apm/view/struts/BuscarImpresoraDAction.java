package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.ImpresoraSearchPage;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarImpresoraDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(BuscarImpresoraDAction.class);
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_IMPRESORA, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			ImpresoraSearchPage impresoraSearchPageVO = ApmServiceLocator.getAplicacionService().getImpresoraSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (impresoraSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + impresoraSearchPageVO.infoString()); 
				saveDemodaErrors(request, impresoraSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, ImpresoraSearchPage.NAME, impresoraSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (impresoraSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + impresoraSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, ImpresoraSearchPage.NAME, impresoraSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , ImpresoraSearchPage.NAME, impresoraSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_IMPRESORA_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, ImpresoraSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, ImpresoraSearchPage.NAME);
		
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
			ImpresoraSearchPage impresoraSearchPageVO = (ImpresoraSearchPage) userSession.get(ImpresoraSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (impresoraSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + ImpresoraSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, ImpresoraSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(impresoraSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				impresoraSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (impresoraSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + impresoraSearchPageVO.infoString()); 
				saveDemodaErrors(request, impresoraSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, ImpresoraSearchPage.NAME, impresoraSearchPageVO);
			}
				
			// Llamada al servicio	
			impresoraSearchPageVO = ApmServiceLocator.getAplicacionService().getImpresoraSearchPageResult(userSession, impresoraSearchPageVO);			

			// Tiene errores recuperables
			if (impresoraSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + impresoraSearchPageVO.infoString()); 
				saveDemodaErrors(request, impresoraSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, ImpresoraSearchPage.NAME, impresoraSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (impresoraSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + impresoraSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, ImpresoraSearchPage.NAME, impresoraSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(ImpresoraSearchPage.NAME, impresoraSearchPageVO);
			// Nuleo el list result
			//impresoraSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(ImpresoraSearchPage.NAME, impresoraSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_IMPRESORA_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, ImpresoraSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_IMPRESORA);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		//Se utiliza uno de los dos return, segun sea un encabezado detalle o no.
		return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_IMPRESORA);
		//return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_IMPRESORA);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_IMPRESORA);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_IMPRESORA);

	}
	
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, ImpresoraSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, ImpresoraSearchPage.NAME);
		
	}
		
	
}
