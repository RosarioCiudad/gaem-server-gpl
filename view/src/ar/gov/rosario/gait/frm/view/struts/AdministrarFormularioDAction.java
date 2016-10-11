package ar.gov.rosario.gait.frm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.frm.iface.model.FormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.FormularioVO;
import ar.gov.rosario.gait.frm.iface.service.FrmServiceLocator;
import ar.gov.rosario.gait.frm.iface.util.FrmError;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import ar.gov.rosario.gait.frm.view.util.FrmConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarFormularioDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarFormularioDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_FORMULARIO, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		FormularioAdapter formularioAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getFormularioAdapterForView(userSession, commonKey)";
				formularioAdapterVO = FrmServiceLocator.getFormularioService().getFormularioAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_FORMULARIO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getFormularioAdapterForUpdate(userSession, commonKey)";
				formularioAdapterVO = FrmServiceLocator.getFormularioService().getFormularioAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_FORMULARIO_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getFormularioAdapterForView(userSession, commonKey)";
				formularioAdapterVO = FrmServiceLocator.getFormularioService().getFormularioAdapterForView(userSession, commonKey);				
				formularioAdapterVO.addMessage(BaseError.MSG_ELIMINAR, FrmError.FORMULARIO_LABEL);
				actionForward = mapping.findForward(FrmConstants.FWD_FORMULARIO_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getFormularioAdapterForCreate(userSession)";
				formularioAdapterVO = FrmServiceLocator.getFormularioService().getFormularioAdapterForCreate(userSession);
				actionForward = mapping.findForward(FrmConstants.FWD_FORMULARIO_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_ACTIVAR)) {
				stringServicio = "getFormularioAdapterForView(userSession)";
				formularioAdapterVO = FrmServiceLocator.getFormularioService().getFormularioAdapterForView(userSession, commonKey);
				formularioAdapterVO.addMessage(BaseError.MSG_ACTIVAR, FrmError.FORMULARIO_LABEL);
				actionForward = mapping.findForward(FrmConstants.FWD_FORMULARIO_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_DESACTIVAR)) {
				stringServicio = "getFormularioAdapterForView(userSession)";
				formularioAdapterVO = FrmServiceLocator.getFormularioService().getFormularioAdapterForView(userSession, commonKey);
				formularioAdapterVO.addMessage(BaseError.MSG_DESACTIVAR, FrmError.FORMULARIO_LABEL);
				actionForward = mapping.findForward(FrmConstants.FWD_FORMULARIO_VIEW_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (formularioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + formularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, FormularioAdapter.NAME, formularioAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			formularioAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + FormularioAdapter.NAME + ": "+ formularioAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(FormularioAdapter.NAME, formularioAdapterVO);
			// Subo el apdater al userSession
			userSession.put(FormularioAdapter.NAME, formularioAdapterVO);
			 
			saveDemodaMessages(request, formularioAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, FormularioAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_FORMULARIO, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			FormularioAdapter formularioAdapterVO = (FormularioAdapter) userSession.get(FormularioAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (formularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + FormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, FormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(formularioAdapterVO, request);
			
            // Tiene errores recuperables
			if (formularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + formularioAdapterVO.infoString()); 
				saveDemodaErrors(request, formularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, FormularioAdapter.NAME, formularioAdapterVO);
			}
			
			// llamada al servicio
			FormularioVO formularioVO = FrmServiceLocator.getFormularioService().createFormulario(userSession, formularioAdapterVO.getFormulario());
			
            // Tiene errores recuperables
			if (formularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + formularioVO.infoString()); 
				saveDemodaErrors(request, formularioVO);
				return forwardErrorRecoverable(mapping, request, userSession, FormularioAdapter.NAME, formularioAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (formularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + formularioVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, FormularioAdapter.NAME, formularioAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, FormularioAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, FormularioAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_FORMULARIO, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			FormularioAdapter formularioAdapterVO = (FormularioAdapter) userSession.get(FormularioAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (formularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + FormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, FormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(formularioAdapterVO, request);
			
            // Tiene errores recuperables
			if (formularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + formularioAdapterVO.infoString()); 
				saveDemodaErrors(request, formularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, FormularioAdapter.NAME, formularioAdapterVO);
			}
			
			// llamada al servicio
			FormularioVO formularioVO = FrmServiceLocator.getFormularioService().updateFormulario(userSession, formularioAdapterVO.getFormulario());
			
            // Tiene errores recuperables
			if (formularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + formularioAdapterVO.infoString()); 
				saveDemodaErrors(request, formularioVO);
				return forwardErrorRecoverable(mapping, request, userSession, FormularioAdapter.NAME, formularioAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (formularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + formularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, FormularioAdapter.NAME, formularioAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, FormularioAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, FormularioAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_FORMULARIO, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			FormularioAdapter formularioAdapterVO = (FormularioAdapter) userSession.get(FormularioAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (formularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + FormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, FormularioAdapter.NAME); 
			}

			// llamada al servicio
			FormularioVO formularioVO = FrmServiceLocator.getFormularioService().deleteFormulario
				(userSession, formularioAdapterVO.getFormulario());
			
            // Tiene errores recuperables
			if (formularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + formularioAdapterVO.infoString());
				saveDemodaErrors(request, formularioVO);				
				request.setAttribute(FormularioAdapter.NAME, formularioAdapterVO);
				return mapping.findForward(FrmConstants.FWD_FORMULARIO_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (formularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + formularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, FormularioAdapter.NAME, formularioAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, FormularioAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, FormularioAdapter.NAME);
		}
	}
	
	public ActionForward activar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_FORMULARIO, BaseSecurityConstants.ACTIVAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			FormularioAdapter formularioAdapterVO = (FormularioAdapter) userSession.get(FormularioAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (formularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + FormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, FormularioAdapter.NAME); 
			}

			// llamada al servicio
			FormularioVO formularioVO = FrmServiceLocator.getFormularioService().activarFormulario
				(userSession, formularioAdapterVO.getFormulario());
			
            // Tiene errores recuperables
			if (formularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + formularioAdapterVO.infoString());
				saveDemodaErrors(request, formularioVO);				
				request.setAttribute(FormularioAdapter.NAME, formularioAdapterVO);
				return mapping.findForward(FrmConstants.FWD_FORMULARIO_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (formularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + formularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, FormularioAdapter.NAME, formularioAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, FormularioAdapter.NAME);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, FormularioAdapter.NAME);
		}	
	}
	
	public ActionForward desactivar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_FORMULARIO, BaseSecurityConstants.DESACTIVAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			FormularioAdapter formularioAdapterVO = (FormularioAdapter) userSession.get(FormularioAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (formularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + FormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, FormularioAdapter.NAME); 
			}

			// llamada al servicio
			FormularioVO formularioVO = FrmServiceLocator.getFormularioService().desactivarFormulario
				(userSession, formularioAdapterVO.getFormulario());
			
            // Tiene errores recuperables
			if (formularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + formularioAdapterVO.infoString());
				saveDemodaErrors(request, formularioVO);				
				request.setAttribute(FormularioAdapter.NAME, formularioAdapterVO);
				return mapping.findForward(FrmConstants.FWD_FORMULARIO_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (formularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + formularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, FormularioAdapter.NAME, formularioAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, FormularioAdapter.NAME);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, FormularioAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, FormularioAdapter.NAME);
		
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
			FormularioAdapter formularioAdapterVO = (FormularioAdapter) userSession.get(FormularioAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (formularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + FormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, FormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(formularioAdapterVO, request);
			
            // Tiene errores recuperables
			if (formularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + formularioAdapterVO.infoString()); 
				saveDemodaErrors(request, formularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, FormularioAdapter.NAME, formularioAdapterVO);
			}
			
			// llamada al servicio
//			formularioAdapterVO = FrmServiceLocator.getFormularioService().getFormularioAdapterParam(userSession, formularioAdapterVO);
			
            // Tiene errores recuperables
			if (formularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + formularioAdapterVO.infoString()); 
				saveDemodaErrors(request, formularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, FormularioAdapter.NAME, formularioAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (formularioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + formularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, FormularioAdapter.NAME, formularioAdapterVO);
			}
			
			// Envio el VO al request
			request.setAttribute(FormularioAdapter.NAME, formularioAdapterVO);
			// Subo el apdater al userSession
			userSession.put(FormularioAdapter.NAME, formularioAdapterVO);
			
			return mapping.findForward(FrmConstants.FWD_FORMULARIO_ADAPTER);
		
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, FormularioAdapter.NAME);
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
			String name = FormularioAdapter.NAME;
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
			FormularioAdapter formularioAdapterVO = (FormularioAdapter) userSession.get(name);
			
			// Si es nulo no se puede continuar
			if (formularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + name + " IS NULL. No se pudo obtener de la sesion");
				return mapping.findForward(BaseConstants.FWD_ERROR_PRINT);
				//return forwardErrorSessionNullObject(mapping, request, funcName, FormularioAdapter.NAME); 
			}

			// prepara el report del adapter para luego generar el reporte
			formularioAdapterVO.prepareReport(Long.valueOf(reportFormat));
			
			// llamada al servicio que genera el reporte
			formularioAdapterVO = FrmServiceLocator.getFormularioService().imprimirFormulario(userSession, formularioAdapterVO);

			// limpia la lista de reports y la lista de tablas
			formularioAdapterVO.getReport().getListReport().clear();
			formularioAdapterVO.getReport().getReportListTable().clear();
			
            // Tiene errores recuperables
			if (formularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + formularioAdapterVO.infoString());
				saveDemodaErrors(request, formularioAdapterVO);				
				request.setAttribute(name, formularioAdapterVO);
				return mapping.findForward(BaseConstants.FWD_ERROR_PRINT);
			}
			
			// Tiene errores no recuperables
			if (formularioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + formularioAdapterVO.errorString());
				return mapping.findForward(BaseConstants.FWD_ERROR_PRINT);
				//return forwardErrorNonRecoverable(mapping, request, funcName, formularioAdapter.NAME, obraAdapterVO);
			}
			
			// Subo en el el searchPage al userSession
			userSession.put(name, formularioAdapterVO);
			
			// obtenemos el nombre del archivo seleccionado
			String fileName = formularioAdapterVO.getReport().getReportFileName();

			// **preparamos para mostrar el imprimir
			request.setAttribute("path", request.getRequestURI());
			userSession.put("baseImprimir.reportFilename", fileName);
			log.debug("exit: " + funcName);
			return new ActionForward(BaseConstants.FWD_VIEW_IMPRIMIR);

		} catch (Exception exception) {
			return mapping.findForward(BaseConstants.FWD_ERROR_PRINT);
		}
	}
	
	public ActionForward verFormularioDetalle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_FORMULARIODETALLE);
	}	
	
	public ActionForward modificarFormularioDetalle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_FORMULARIODETALLE);
	}	
	
	public ActionForward eliminarFormularioDetalle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, FrmConstants.ACTION_ADMINISTRAR_FORMULARIODETALLE);
	}	
	
	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, FormularioAdapter.NAME);			
	}
} 