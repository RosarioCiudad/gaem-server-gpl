package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilSearchPage;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarDispositivoMovilDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(BuscarDispositivoMovilDAction.class);
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_DISPOSITIVOMOVIL, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			DispositivoMovilSearchPage dispositivoMovilSearchPageVO = ApmServiceLocator.getAplicacionService().getDispositivoMovilSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (dispositivoMovilSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + dispositivoMovilSearchPageVO.infoString()); 
				saveDemodaErrors(request, dispositivoMovilSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, DispositivoMovilSearchPage.NAME, dispositivoMovilSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (dispositivoMovilSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + dispositivoMovilSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DispositivoMovilSearchPage.NAME, dispositivoMovilSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , DispositivoMovilSearchPage.NAME, dispositivoMovilSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_DISPOSITIVOMOVIL_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DispositivoMovilSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, DispositivoMovilSearchPage.NAME);
		
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
			DispositivoMovilSearchPage dispositivoMovilSearchPageVO = (DispositivoMovilSearchPage) userSession.get(DispositivoMovilSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (dispositivoMovilSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + DispositivoMovilSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DispositivoMovilSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(dispositivoMovilSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				dispositivoMovilSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (dispositivoMovilSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + dispositivoMovilSearchPageVO.infoString()); 
				saveDemodaErrors(request, dispositivoMovilSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, DispositivoMovilSearchPage.NAME, dispositivoMovilSearchPageVO);
			}
				
			// Llamada al servicio	
			dispositivoMovilSearchPageVO = ApmServiceLocator.getAplicacionService().getDispositivoMovilSearchPageResult(userSession, dispositivoMovilSearchPageVO);			

			// Tiene errores recuperables
			if (dispositivoMovilSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + dispositivoMovilSearchPageVO.infoString()); 
				saveDemodaErrors(request, dispositivoMovilSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, DispositivoMovilSearchPage.NAME, dispositivoMovilSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (dispositivoMovilSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + dispositivoMovilSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, DispositivoMovilSearchPage.NAME, dispositivoMovilSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(DispositivoMovilSearchPage.NAME, dispositivoMovilSearchPageVO);
			// Nuleo el list result
			//dispositivoMovilSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(DispositivoMovilSearchPage.NAME, dispositivoMovilSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_DISPOSITIVOMOVIL_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DispositivoMovilSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_DISPOSITIVOMOVIL);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		//Se utiliza uno de los dos return, segun sea un encabezado detalle o no.
		return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_DISPOSITIVOMOVIL);
		//return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_DISPOSITIVOMOVIL);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_DISPOSITIVOMOVIL);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_DISPOSITIVOMOVIL);

	}
	
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, DispositivoMovilSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, DispositivoMovilSearchPage.NAME);
		
	}
		
	
}
