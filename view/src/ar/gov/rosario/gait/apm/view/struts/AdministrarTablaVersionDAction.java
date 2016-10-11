package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.TablaVersionAdapter;
import ar.gov.rosario.gait.apm.iface.model.TablaVersionVO;
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

public final class AdministrarTablaVersionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarTablaVersionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_TABLAVERSION, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		TablaVersionAdapter tablaVersionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getTablaVersionAdapterForView(userSession, commonKey)";
				tablaVersionAdapterVO = ApmServiceLocator.getAplicacionService().getTablaVersionAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_TABLAVERSION_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getTablaVersionAdapterForUpdate(userSession, commonKey)";
				tablaVersionAdapterVO = ApmServiceLocator.getAplicacionService().getTablaVersionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_TABLAVERSION_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getTablaVersionAdapterForView(userSession, commonKey)";
				tablaVersionAdapterVO = ApmServiceLocator.getAplicacionService().getTablaVersionAdapterForView(userSession, commonKey);				
				tablaVersionAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.TABLAVERSION_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_TABLAVERSION_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getTablaVersionAdapterForCreate(userSession)";
				tablaVersionAdapterVO = ApmServiceLocator.getAplicacionService().getTablaVersionAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_TABLAVERSION_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (tablaVersionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + tablaVersionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TablaVersionAdapter.NAME, tablaVersionAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			tablaVersionAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + TablaVersionAdapter.NAME + ": "+ tablaVersionAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(TablaVersionAdapter.NAME, tablaVersionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(TablaVersionAdapter.NAME, tablaVersionAdapterVO);
			 
			saveDemodaMessages(request, tablaVersionAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TablaVersionAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_TABLAVERSION, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			TablaVersionAdapter tablaVersionAdapterVO = (TablaVersionAdapter) userSession.get(TablaVersionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (tablaVersionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + TablaVersionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TablaVersionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(tablaVersionAdapterVO, request);
			
            // Tiene errores recuperables
			if (tablaVersionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tablaVersionAdapterVO.infoString()); 
				saveDemodaErrors(request, tablaVersionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, TablaVersionAdapter.NAME, tablaVersionAdapterVO);
			}
			
			// llamada al servicio
			TablaVersionVO tablaVersionVO = ApmServiceLocator.getAplicacionService().createTablaVersion(userSession, tablaVersionAdapterVO.getTablaVersion());
			
            // Tiene errores recuperables
			if (tablaVersionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tablaVersionVO.infoString()); 
				saveDemodaErrors(request, tablaVersionVO);
				return forwardErrorRecoverable(mapping, request, userSession, TablaVersionAdapter.NAME, tablaVersionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (tablaVersionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tablaVersionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TablaVersionAdapter.NAME, tablaVersionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, TablaVersionAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TablaVersionAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_TABLAVERSION, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			TablaVersionAdapter tablaVersionAdapterVO = (TablaVersionAdapter) userSession.get(TablaVersionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (tablaVersionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + TablaVersionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TablaVersionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(tablaVersionAdapterVO, request);
			
            // Tiene errores recuperables
			if (tablaVersionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tablaVersionAdapterVO.infoString()); 
				saveDemodaErrors(request, tablaVersionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, TablaVersionAdapter.NAME, tablaVersionAdapterVO);
			}
			
			// llamada al servicio
			TablaVersionVO tablaVersionVO = ApmServiceLocator.getAplicacionService().updateTablaVersion(userSession, tablaVersionAdapterVO.getTablaVersion());
			
            // Tiene errores recuperables
			if (tablaVersionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tablaVersionAdapterVO.infoString()); 
				saveDemodaErrors(request, tablaVersionVO);
				return forwardErrorRecoverable(mapping, request, userSession, TablaVersionAdapter.NAME, tablaVersionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (tablaVersionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tablaVersionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TablaVersionAdapter.NAME, tablaVersionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, TablaVersionAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TablaVersionAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_TABLAVERSION, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			TablaVersionAdapter tablaVersionAdapterVO = (TablaVersionAdapter) userSession.get(TablaVersionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (tablaVersionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + TablaVersionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TablaVersionAdapter.NAME); 
			}

			// llamada al servicio
			TablaVersionVO tablaVersionVO = ApmServiceLocator.getAplicacionService().deleteTablaVersion
				(userSession, tablaVersionAdapterVO.getTablaVersion());
			
            // Tiene errores recuperables
			if (tablaVersionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tablaVersionAdapterVO.infoString());
				saveDemodaErrors(request, tablaVersionVO);				
				request.setAttribute(TablaVersionAdapter.NAME, tablaVersionAdapterVO);
				return mapping.findForward(ApmConstants.FWD_TABLAVERSION_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (tablaVersionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tablaVersionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TablaVersionAdapter.NAME, tablaVersionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, TablaVersionAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TablaVersionAdapter.NAME);
		}
	}
		
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, TablaVersionAdapter.NAME);
		
	}
	
} 
