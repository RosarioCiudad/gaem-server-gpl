package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.TablaVersionSearchPage;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarTablaVersionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(BuscarTablaVersionDAction.class);
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_TABLAVERSION, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			
			TablaVersionSearchPage tablaVersionSearchPageVO = ApmServiceLocator.getAplicacionService().getTablaVersionSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (tablaVersionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tablaVersionSearchPageVO.infoString()); 
				saveDemodaErrors(request, tablaVersionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, TablaVersionSearchPage.NAME, tablaVersionSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (tablaVersionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tablaVersionSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TablaVersionSearchPage.NAME, tablaVersionSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , TablaVersionSearchPage.NAME, tablaVersionSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_TABLAVERSION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TablaVersionSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, TablaVersionSearchPage.NAME);
		
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
			TablaVersionSearchPage tablaVersionSearchPageVO = (TablaVersionSearchPage) userSession.get(TablaVersionSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (tablaVersionSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + TablaVersionSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TablaVersionSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(tablaVersionSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				tablaVersionSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (tablaVersionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tablaVersionSearchPageVO.infoString()); 
				saveDemodaErrors(request, tablaVersionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, TablaVersionSearchPage.NAME, tablaVersionSearchPageVO);
			}
				
			// Llamada al servicio	
			tablaVersionSearchPageVO = ApmServiceLocator.getAplicacionService().getTablaVersionSearchPageResult(userSession, tablaVersionSearchPageVO);			

			// Tiene errores recuperables
			if (tablaVersionSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tablaVersionSearchPageVO.infoString()); 
				saveDemodaErrors(request, tablaVersionSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, TablaVersionSearchPage.NAME, tablaVersionSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (tablaVersionSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tablaVersionSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, TablaVersionSearchPage.NAME, tablaVersionSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(TablaVersionSearchPage.NAME, tablaVersionSearchPageVO);
			// Nuleo el list result
			//tablaVersionSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(TablaVersionSearchPage.NAME, tablaVersionSearchPageVO);
			
			return mapping.findForward(ApmConstants.FWD_TABLAVERSION_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TablaVersionSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_TABLAVERSION);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		//Se utiliza uno de los dos return, segun sea un encabezado detalle o no.
		return forwardAgregarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_TABLAVERSION);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_TABLAVERSION);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_TABLAVERSION);

	}
	
	public ActionForward activar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardActivarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_TABLAVERSION);			
	}
	
	public ActionForward desactivar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		return forwardDesactivarSearchPage(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_TABLAVERSION);
	}
	
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, TablaVersionSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, TablaVersionSearchPage.NAME);
		
	}
		
	
}
