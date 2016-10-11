package ar.gov.rosario.gait.frm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.frm.iface.model.EstadoTipoFormularioSearchPage;
import ar.gov.rosario.gait.frm.iface.service.FrmServiceLocator;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import ar.gov.rosario.gait.frm.view.util.FrmConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarEstadoTipoFormularioDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger((BuscarEstadoTipoFormularioDAction.class));
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_ESTADOTIPOFORMULARIO, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			log.debug("Test" + userSession.getNavModel().getSelectedId());
			
			EstadoTipoFormularioSearchPage estadoTipoFormularioSearchPageVO = FrmServiceLocator.getFormularioService().getEstadoTipoFormularioSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (estadoTipoFormularioSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoTipoFormularioSearchPageVO.infoString()); 
				saveDemodaErrors(request, estadoTipoFormularioSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoTipoFormularioSearchPage.NAME, estadoTipoFormularioSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (estadoTipoFormularioSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + estadoTipoFormularioSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, EstadoTipoFormularioSearchPage.NAME, estadoTipoFormularioSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , EstadoTipoFormularioSearchPage.NAME, estadoTipoFormularioSearchPageVO);
			
			return mapping.findForward(FrmConstants.FWD_ESTADOTIPOFORMULARIO_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, EstadoTipoFormularioSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, EstadoTipoFormularioSearchPage.NAME);
		
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
			EstadoTipoFormularioSearchPage estadoTipoFormularioSearchPageVO = (EstadoTipoFormularioSearchPage) userSession.get(EstadoTipoFormularioSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (estadoTipoFormularioSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + EstadoTipoFormularioSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, EstadoTipoFormularioSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(estadoTipoFormularioSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				estadoTipoFormularioSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (estadoTipoFormularioSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoTipoFormularioSearchPageVO.infoString()); 
				saveDemodaErrors(request, estadoTipoFormularioSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoTipoFormularioSearchPage.NAME, estadoTipoFormularioSearchPageVO);
			}
				
			// Llamada al servicio	
			estadoTipoFormularioSearchPageVO = FrmServiceLocator.getFormularioService().getEstadoTipoFormularioSearchPageResult(userSession, estadoTipoFormularioSearchPageVO);			

			// Tiene errores recuperables
			if (estadoTipoFormularioSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoTipoFormularioSearchPageVO.infoString()); 
				saveDemodaErrors(request, estadoTipoFormularioSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoTipoFormularioSearchPage.NAME, estadoTipoFormularioSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (estadoTipoFormularioSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + estadoTipoFormularioSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, EstadoTipoFormularioSearchPage.NAME, estadoTipoFormularioSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(EstadoTipoFormularioSearchPage.NAME, estadoTipoFormularioSearchPageVO);
			// Nuleo el list result
			//estadoTipoFormularioSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(EstadoTipoFormularioSearchPage.NAME, estadoTipoFormularioSearchPageVO);
			
			return mapping.findForward(FrmConstants.FWD_ESTADOTIPOFORMULARIO_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, EstadoTipoFormularioSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_ESTADOTIPOFORMULARIO);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		return forwardAgregarSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_ESTADOTIPOFORMULARIO);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_ESTADOTIPOFORMULARIO);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_ESTADOTIPOFORMULARIO);

	}
	
		
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, EstadoTipoFormularioSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, EstadoTipoFormularioSearchPage.NAME);
		
	}
		
	
}
