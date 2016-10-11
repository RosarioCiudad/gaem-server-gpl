package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.CampoSearchPage;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarCampoDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(BuscarCampoDAction.class);
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPO, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			CampoSearchPage campoSearchPageVO = ApmServiceLocator.getAplicacionService().getCampoSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (campoSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoSearchPageVO.infoString()); 
				saveDemodaErrors(request, campoSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoSearchPage.NAME, campoSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (campoSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoSearchPage.NAME, campoSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , CampoSearchPage.NAME, campoSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_CAMPO_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, CampoSearchPage.NAME);
		
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
			CampoSearchPage campoSearchPageVO = (CampoSearchPage) userSession.get(CampoSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (campoSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + CampoSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(campoSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				campoSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (campoSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoSearchPageVO.infoString()); 
				saveDemodaErrors(request, campoSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoSearchPage.NAME, campoSearchPageVO);
			}
				
			// Llamada al servicio	
			campoSearchPageVO = ApmServiceLocator.getAplicacionService().getCampoSearchPageResult(userSession, campoSearchPageVO);			

			// Tiene errores recuperables
			if (campoSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoSearchPageVO.infoString()); 
				saveDemodaErrors(request, campoSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoSearchPage.NAME, campoSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (campoSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoSearchPage.NAME, campoSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(CampoSearchPage.NAME, campoSearchPageVO);
			// Nuleo el list result
			//campoSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(CampoSearchPage.NAME, campoSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_CAMPO_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_CAMPO);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_CAMPO);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_CAMPO);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_CAMPO);

	}
	
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, CampoSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, CampoSearchPage.NAME);
		
	}
		
	
}
