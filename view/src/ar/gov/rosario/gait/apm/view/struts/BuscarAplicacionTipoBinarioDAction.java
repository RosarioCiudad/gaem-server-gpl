package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.AplicacionTipoBinarioSearchPage;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarAplicacionTipoBinarioDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(BuscarAplicacionTipoBinarioDAction.class);
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONTIPOBINARIO, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			AplicacionTipoBinarioSearchPage aplicacionTipoBinarioSearchPageVO = ApmServiceLocator.getAplicacionService().getAplicacionTipoBinarioSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (aplicacionTipoBinarioSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTipoBinarioSearchPageVO.infoString()); 
				saveDemodaErrors(request, aplicacionTipoBinarioSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTipoBinarioSearchPage.NAME, aplicacionTipoBinarioSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (aplicacionTipoBinarioSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionTipoBinarioSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionTipoBinarioSearchPage.NAME, aplicacionTipoBinarioSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , AplicacionTipoBinarioSearchPage.NAME, aplicacionTipoBinarioSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_APLICACIONTIPOBINARIO_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionTipoBinarioSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, AplicacionTipoBinarioSearchPage.NAME);
		
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
			AplicacionTipoBinarioSearchPage aplicacionTipoBinarioSearchPageVO = (AplicacionTipoBinarioSearchPage) userSession.get(AplicacionTipoBinarioSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionTipoBinarioSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionTipoBinarioSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionTipoBinarioSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(aplicacionTipoBinarioSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				aplicacionTipoBinarioSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (aplicacionTipoBinarioSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTipoBinarioSearchPageVO.infoString()); 
				saveDemodaErrors(request, aplicacionTipoBinarioSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTipoBinarioSearchPage.NAME, aplicacionTipoBinarioSearchPageVO);
			}
				
			// Llamada al servicio	
			aplicacionTipoBinarioSearchPageVO = ApmServiceLocator.getAplicacionService().getAplicacionTipoBinarioSearchPageResult(userSession, aplicacionTipoBinarioSearchPageVO);			

			// Tiene errores recuperables
			if (aplicacionTipoBinarioSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTipoBinarioSearchPageVO.infoString()); 
				saveDemodaErrors(request, aplicacionTipoBinarioSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTipoBinarioSearchPage.NAME, aplicacionTipoBinarioSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionTipoBinarioSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionTipoBinarioSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionTipoBinarioSearchPage.NAME, aplicacionTipoBinarioSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(AplicacionTipoBinarioSearchPage.NAME, aplicacionTipoBinarioSearchPageVO);
			// Nuleo el list result
			//aplicacionTipoBinarioSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(AplicacionTipoBinarioSearchPage.NAME, aplicacionTipoBinarioSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_APLICACIONTIPOBINARIO_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionTipoBinarioSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONTIPOBINARIO);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		//Se utiliza uno de los dos return, segun sea un encabezado detalle o no.
		return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONTIPOBINARIO);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONTIPOBINARIO);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONTIPOBINARIO);

	}
	
	public ActionForward activar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardActivarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONTIPOBINARIO);			
	}
	
	public ActionForward desactivar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardDesactivarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONTIPOBINARIO);
	}
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, AplicacionTipoBinarioSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AplicacionTipoBinarioSearchPage.NAME);
		
	}
		
	
}
