package ar.gov.rosario.gait.frm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.frm.iface.model.TipoFormularioSearchPage;
import ar.gov.rosario.gait.frm.iface.service.FrmServiceLocator;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import ar.gov.rosario.gait.frm.view.util.FrmConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarTipoFormularioDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger((BuscarTipoFormularioDAction.class));
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_TIPOFORMULARIO, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			log.debug("Test" + userSession.getNavModel().getSelectedId());
			
			TipoFormularioSearchPage tipoFormularioSearchPageVO = FrmServiceLocator.getFormularioService().getTipoFormularioSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (tipoFormularioSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoFormularioSearchPageVO.infoString()); 
				saveDemodaErrors(request, tipoFormularioSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoFormularioSearchPage.NAME, tipoFormularioSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (tipoFormularioSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tipoFormularioSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TipoFormularioSearchPage.NAME, tipoFormularioSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , TipoFormularioSearchPage.NAME, tipoFormularioSearchPageVO);
			
			return mapping.findForward(FrmConstants.FWD_TIPOFORMULARIO_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TipoFormularioSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, TipoFormularioSearchPage.NAME);
		
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
			TipoFormularioSearchPage tipoFormularioSearchPageVO = (TipoFormularioSearchPage) userSession.get(TipoFormularioSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (tipoFormularioSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + TipoFormularioSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TipoFormularioSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(tipoFormularioSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				tipoFormularioSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (tipoFormularioSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoFormularioSearchPageVO.infoString()); 
				saveDemodaErrors(request, tipoFormularioSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoFormularioSearchPage.NAME, tipoFormularioSearchPageVO);
			}
				
			// Llamada al servicio	
			tipoFormularioSearchPageVO = FrmServiceLocator.getFormularioService().getTipoFormularioSearchPageResult(userSession, tipoFormularioSearchPageVO);			

			// Tiene errores recuperables
			if (tipoFormularioSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoFormularioSearchPageVO.infoString()); 
				saveDemodaErrors(request, tipoFormularioSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoFormularioSearchPage.NAME, tipoFormularioSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (tipoFormularioSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tipoFormularioSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, TipoFormularioSearchPage.NAME, tipoFormularioSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(TipoFormularioSearchPage.NAME, tipoFormularioSearchPageVO);
			// Nuleo el list result
			//tipoFormularioSearchPageVO.setListResult(new ArrayList()); TODO ver como solucionar esto
			// Subo en el el searchPage al userSession
			userSession.put(TipoFormularioSearchPage.NAME, tipoFormularioSearchPageVO);
			
			return mapping.findForward(FrmConstants.FWD_TIPOFORMULARIO_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TipoFormularioSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_TIPOFORMULARIO);

	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		
		return forwardAgregarSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_TIPOFORMULARIO);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_TIPOFORMULARIO);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_TIPOFORMULARIO);

	}
	
		
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, TipoFormularioSearchPage.NAME);
		
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, TipoFormularioSearchPage.NAME);
		
	}
		
	
}
