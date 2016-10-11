package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.AplicacionTablaAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTablaVO;
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

public final class AdministrarAplicacionTablaDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarAplicacionTablaDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONTABLA, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		AplicacionTablaAdapter aplicacionTablaAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getAplicacionTablaAdapterForView(userSession, commonKey)";
				aplicacionTablaAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionTablaAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONTABLA_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplicacionTablaAdapterForUpdate(userSession, commonKey)";
				aplicacionTablaAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionTablaAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONTABLA_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getAplicacionTablaAdapterForView(userSession, commonKey)";
				aplicacionTablaAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionTablaAdapterForView(userSession, commonKey);				
				aplicacionTablaAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.APLICACIONTABLA_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONTABLA_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplicacionTablaAdapterForCreate(userSession)";
				aplicacionTablaAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionTablaAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONTABLA_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (aplicacionTablaAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplicacionTablaAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			aplicacionTablaAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplicacionTablaAdapter.NAME + ": "+ aplicacionTablaAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			 
			saveDemodaMessages(request, aplicacionTablaAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionTablaAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONTABLA, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionTablaAdapter aplicacionTablaAdapterVO = (AplicacionTablaAdapter) userSession.get(AplicacionTablaAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionTablaAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionTablaAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionTablaAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionTablaAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionTablaAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTablaAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionTablaAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			}
			
			// llamada al servicio
			AplicacionTablaVO aplicacionTablaVO = ApmServiceLocator.getAplicacionService().createAplicacionTabla(userSession, aplicacionTablaAdapterVO.getAplicacionTabla());
			
            // Tiene errores recuperables
			if (aplicacionTablaVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTablaVO.infoString()); 
				saveDemodaErrors(request, aplicacionTablaVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionTablaVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionTablaVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionTablaAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionTablaAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONTABLA, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionTablaAdapter aplicacionTablaAdapterVO = (AplicacionTablaAdapter) userSession.get(AplicacionTablaAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionTablaAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionTablaAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionTablaAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionTablaAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionTablaAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTablaAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionTablaAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			}
			
			// llamada al servicio
			AplicacionTablaVO aplicacionTablaVO = ApmServiceLocator.getAplicacionService().updateAplicacionTabla(userSession, aplicacionTablaAdapterVO.getAplicacionTabla());
			
            // Tiene errores recuperables
			if (aplicacionTablaVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTablaAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionTablaVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionTablaVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionTablaAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionTablaAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionTablaAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONTABLA, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionTablaAdapter aplicacionTablaAdapterVO = (AplicacionTablaAdapter) userSession.get(AplicacionTablaAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionTablaAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionTablaAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionTablaAdapter.NAME); 
			}

			// llamada al servicio
			AplicacionTablaVO aplicacionTablaVO = ApmServiceLocator.getAplicacionService().deleteAplicacionTabla
				(userSession, aplicacionTablaAdapterVO.getAplicacionTabla());
			
            // Tiene errores recuperables
			if (aplicacionTablaVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTablaAdapterVO.infoString());
				saveDemodaErrors(request, aplicacionTablaVO);				
				request.setAttribute(AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
				return mapping.findForward(ApmConstants.FWD_APLICACIONTABLA_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (aplicacionTablaVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionTablaAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionTablaAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionTablaAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AplicacionTablaAdapter.NAME);
		
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
			AplicacionTablaAdapter aplicacionTablaAdapterVO = (AplicacionTablaAdapter) userSession.get(AplicacionTablaAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionTablaAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionTablaAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionTablaAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionTablaAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionTablaAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTablaAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionTablaAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			}
			
			// llamada al servicio
			//aplicacionTablaAdapterVO = ApmServiceLocator.getAplicacionTablaService().getAplicacionTablaAdapterParam(userSession, aplicacionTablaAdapterVO);
			
            // Tiene errores recuperables
			if (aplicacionTablaAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionTablaAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionTablaAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionTablaAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionTablaAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			}
			
			// Envio el VO al request
			request.setAttribute(AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionTablaAdapter.NAME, aplicacionTablaAdapterVO);
			
			return mapping.findForward(ApmConstants.FWD_APLICACIONTABLA_EDIT_ADAPTER);
		
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionTablaAdapter.NAME);
		}
	}
		
		
	
} 
