package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.SeccionAdapter;
import ar.gov.rosario.gait.apm.iface.model.SeccionVO;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarSeccionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarSeccionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_SECCION, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		SeccionAdapter seccionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getSeccionAdapterForView(userSession, commonKey)";
				seccionAdapterVO = ApmServiceLocator.getAplicacionService().getSeccionAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_SECCION_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getSeccionAdapterForUpdate(userSession, commonKey)";
				seccionAdapterVO = ApmServiceLocator.getAplicacionService().getSeccionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_SECCION_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getSeccionAdapterForView(userSession, commonKey)";
				seccionAdapterVO = ApmServiceLocator.getAplicacionService().getSeccionAdapterForView(userSession, commonKey);				
				seccionAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.SECCION_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_SECCION_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getSeccionAdapterForCreate(userSession)";
				seccionAdapterVO = ApmServiceLocator.getAplicacionService().getSeccionAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_SECCION_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (seccionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + seccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, SeccionAdapter.NAME, seccionAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			seccionAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + SeccionAdapter.NAME + ": "+ seccionAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(SeccionAdapter.NAME, seccionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(SeccionAdapter.NAME, seccionAdapterVO);
			 
			saveDemodaMessages(request, seccionAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, SeccionAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_SECCION, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			SeccionAdapter seccionAdapterVO = (SeccionAdapter) userSession.get(SeccionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (seccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + SeccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, SeccionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(seccionAdapterVO, request);
			
            // Tiene errores recuperables
			if (seccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + seccionAdapterVO.infoString()); 
				saveDemodaErrors(request, seccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, SeccionAdapter.NAME, seccionAdapterVO);
			}
			
			// llamada al servicio
			SeccionVO seccionVO = ApmServiceLocator.getAplicacionService().createSeccion(userSession, seccionAdapterVO.getSeccion());
			
            // Tiene errores recuperables
			if (seccionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + seccionVO.infoString()); 
				saveDemodaErrors(request, seccionVO);
				return forwardErrorRecoverable(mapping, request, userSession, SeccionAdapter.NAME, seccionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (seccionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + seccionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, SeccionAdapter.NAME, seccionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, SeccionAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, SeccionAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_SECCION, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			SeccionAdapter seccionAdapterVO = (SeccionAdapter) userSession.get(SeccionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (seccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + SeccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, SeccionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(seccionAdapterVO, request);
			
            // Tiene errores recuperables
			if (seccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + seccionAdapterVO.infoString()); 
				saveDemodaErrors(request, seccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, SeccionAdapter.NAME, seccionAdapterVO);
			}
			
			// llamada al servicio
			SeccionVO seccionVO = ApmServiceLocator.getAplicacionService().updateSeccion(userSession, seccionAdapterVO.getSeccion());
			
            // Tiene errores recuperables
			if (seccionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + seccionAdapterVO.infoString()); 
				saveDemodaErrors(request, seccionVO);
				return forwardErrorRecoverable(mapping, request, userSession, SeccionAdapter.NAME, seccionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (seccionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + seccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, SeccionAdapter.NAME, seccionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, SeccionAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, SeccionAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_SECCION, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			SeccionAdapter seccionAdapterVO = (SeccionAdapter) userSession.get(SeccionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (seccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + SeccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, SeccionAdapter.NAME); 
			}

			// llamada al servicio
			SeccionVO seccionVO = ApmServiceLocator.getAplicacionService().deleteSeccion
				(userSession, seccionAdapterVO.getSeccion());
			
            // Tiene errores recuperables
			if (seccionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + seccionAdapterVO.infoString());
				saveDemodaErrors(request, seccionVO);				
				request.setAttribute(SeccionAdapter.NAME, seccionAdapterVO);
				return mapping.findForward(ApmConstants.FWD_SECCION_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (seccionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + seccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, SeccionAdapter.NAME, seccionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, SeccionAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, SeccionAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, SeccionAdapter.NAME);
		
	}
	
	public ActionForward param (ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = getCurrentUserSession(request, mapping); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			SeccionAdapter seccionAdapterVO = (SeccionAdapter) userSession.get(SeccionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (seccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + SeccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, SeccionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(seccionAdapterVO, request);
			
            // Tiene errores recuperables
			if (seccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + seccionAdapterVO.infoString()); 
				saveDemodaErrors(request, seccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, SeccionAdapter.NAME, seccionAdapterVO);
			}
			
			// llamada al servicio
			//seccionAdapterVO = ApmServiceLocator.getSeccionService().getSeccionAdapterParam(userSession, seccionAdapterVO);
			
            // Tiene errores recuperables
			if (seccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + seccionAdapterVO.infoString()); 
				saveDemodaErrors(request, seccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, SeccionAdapter.NAME, seccionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (seccionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + seccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, SeccionAdapter.NAME, seccionAdapterVO);
			}
			
			// Envio el VO al request
			request.setAttribute(SeccionAdapter.NAME, seccionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(SeccionAdapter.NAME, seccionAdapterVO);
			
			return mapping.findForward(ApmConstants.FWD_SECCION_EDIT_ADAPTER);
		
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, SeccionAdapter.NAME);
		}
	}
		
		
	public ActionForward imprimirReportFromAdapter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");		
		UserSession userSession = getCurrentUserSession(request, mapping); 
		if (userSession == null) return forwardErrorSession(request);
		
		try {
			// obtiene el nombre del page del request
			String name = SeccionAdapter.NAME;
			String reportFormat = request.getParameter("report.reportFormat");
			
			// **Bajo el searchPage del userSession
			String responseFile = request.getParameter("responseFile");
			if ("1".equals(responseFile)) {
				String fileName = (String) userSession.get("baseImprimir.reportFilename");
				// realiza la visualizacion del reporte
				baseResponseEmbedContent(response, fileName, "application/pdf");
				return null;
			}
			
			// Bajo el adapter del userSession
			SeccionAdapter seccionAdapterVO = (SeccionAdapter) userSession.get(name);
			
			// Si es nulo no se puede continuar
			if (seccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + name + " IS NULL. No se pudo obtener de la sesion");
				return mapping.findForward(BaseConstants.FWD_ERROR_PRINT);
				//return forwardErrorSessionNullObject(mapping, request, funcName, SeccionAdapter.NAME); 
			}

			// prepara el report del adapter para luego generar el reporte
			seccionAdapterVO.prepareReport(Long.valueOf(reportFormat));
			
			// llamada al servicio que genera el reporte
			seccionAdapterVO = ApmServiceLocator.getAplicacionService().imprimirSeccion(userSession, seccionAdapterVO);

			// limpia la lista de reports y la lista de tablas
			seccionAdapterVO.getReport().getListReport().clear();
			seccionAdapterVO.getReport().getReportListTable().clear();
			
            // Tiene errores recuperables
			if (seccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + seccionAdapterVO.infoString());
				saveDemodaErrors(request, seccionAdapterVO);				
				request.setAttribute(name, seccionAdapterVO);
				return mapping.findForward(BaseConstants.FWD_ERROR_PRINT);
			}
			
			// Tiene errores no recuperables
			if (seccionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + seccionAdapterVO.errorString());
				return mapping.findForward(BaseConstants.FWD_ERROR_PRINT);
				//return forwardErrorNonRecoverable(mapping, request, funcName, seccionAdapter.NAME, obraAdapterVO);
			}
			
			// Subo en el el searchPage al userSession
			userSession.put(name, seccionAdapterVO);
			
			// obtenemos el nombre del archivo seleccionado
			String fileName = seccionAdapterVO.getReport().getReportFileName();

			// **preparamos para mostrar el imprimir
			request.setAttribute("path", request.getRequestURI());
			userSession.put("baseImprimir.reportFilename", fileName);
			log.debug("exit: " + funcName);
			return new ActionForward(BaseConstants.FWD_VIEW_IMPRIMIR);

		} catch (Exception exception) {
			return mapping.findForward(BaseConstants.FWD_ERROR_PRINT);
		}
	}
	
} 
