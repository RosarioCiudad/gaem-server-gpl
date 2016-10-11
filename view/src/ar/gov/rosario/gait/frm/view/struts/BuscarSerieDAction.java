package ar.gov.rosario.gait.frm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.frm.iface.model.SerieSearchPage;
import ar.gov.rosario.gait.frm.iface.service.FrmServiceLocator;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import ar.gov.rosario.gait.frm.view.util.FrmConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;

public final class BuscarSerieDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger((BuscarSerieDAction.class));
		
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_SERIE, act); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			log.debug("Test" + userSession.getNavModel().getSelectedId());
			
			SerieSearchPage serieSearchPageVO = FrmServiceLocator.getNumeracionService().getSerieSearchPageInit(userSession);
			
			// Tiene errores recuperables
			if (serieSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + serieSearchPageVO.infoString()); 
				saveDemodaErrors(request, serieSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, SerieSearchPage.NAME, serieSearchPageVO);
			} 

			// Tiene errores no recuperables
			if (serieSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + serieSearchPageVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, SerieSearchPage.NAME, serieSearchPageVO);
			}
			
			// Si no tiene error
			baseInicializarSearchPage(mapping, request, userSession , SerieSearchPage.NAME, serieSearchPageVO);
			
			return mapping.findForward(FrmConstants.FWD_SERIE_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, SerieSearchPage.NAME);
		}
	}

	public ActionForward limpiar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return this.baseRefill(mapping, form, request, response, funcName, SerieSearchPage.NAME);
	}
	
	public ActionForward buscar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");		
		UserSession userSession = getCurrentUserSession(request, mapping); 
		if (userSession == null) return forwardErrorSession(request);

		if (log.isDebugEnabled()) log.debug(funcName + ": Hay session!!!: nombre usuario:" + userSession.getUserName());		
		
		try {
			
			// Bajo el searchPage del userSession
			SerieSearchPage serieSearchPageVO = (SerieSearchPage) userSession.get(SerieSearchPage.NAME);
			
			// Si es nulo no se puede continuar
			if (serieSearchPageVO == null) {
				log.error("error en: "  + funcName + ": " + SerieSearchPage.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, SerieSearchPage.NAME); 
			}
			
			// si el buscar diparado desde la pagina de busqueda
			if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) {
				// Recuperamos datos del form en el vo
				DemodaUtil.populateVO(serieSearchPageVO, request);
				// Setea el PageNumber del PageModel				
				serieSearchPageVO.setPageNumber(new Long((String)userSession.get("reqAttPageNumber")));
			}
			
            // Tiene errores recuperables
			if (serieSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + serieSearchPageVO.infoString()); 
				saveDemodaErrors(request, serieSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, SerieSearchPage.NAME, serieSearchPageVO);
			}
				
			// Llamada al servicio	
			serieSearchPageVO = FrmServiceLocator.getNumeracionService().getSerieSearchPageResult(userSession, serieSearchPageVO);			

			// Tiene errores recuperables
			if (serieSearchPageVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + serieSearchPageVO.infoString()); 
				saveDemodaErrors(request, serieSearchPageVO);
				return forwardErrorRecoverable(mapping, request, userSession, SerieSearchPage.NAME, serieSearchPageVO);
			}
			
			// Tiene errores no recuperables
			if (serieSearchPageVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + serieSearchPageVO.errorString());
				return forwardErrorNonRecoverable(mapping, request, funcName, SerieSearchPage.NAME, serieSearchPageVO);
			}
		
			// Envio el VO al request
			request.setAttribute(SerieSearchPage.NAME, serieSearchPageVO);
			// Subo en el el searchPage al userSession
			userSession.put(SerieSearchPage.NAME, serieSearchPageVO);
			
			return mapping.findForward(FrmConstants.FWD_SERIE_SEARCHPAGE);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, SerieSearchPage.NAME);
		}
	}

	public ActionForward ver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_SERIE);
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_SERIE);
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_SERIE);
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarSearchPage(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_SERIE);
	}
		
	public ActionForward seleccionar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseSeleccionar(mapping, request, response, funcName, SerieSearchPage.NAME);
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return baseVolver(mapping, form, request, response, SerieSearchPage.NAME);
	}
}