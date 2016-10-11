package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.UsuarioApmDMAdapter;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmDMVO;
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

public final class AdministrarUsuarioApmDMDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarUsuarioApmDMDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPMDM, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		UsuarioApmDMAdapter aplicacionUsuarioApmDMAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getUsuarioApmDMAdapterForView(userSession, commonKey)";
				aplicacionUsuarioApmDMAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmDMAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPMDM_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getUsuarioApmDMAdapterForUpdate(userSession, commonKey)";
				aplicacionUsuarioApmDMAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmDMAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPMDM_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getUsuarioApmDMAdapterForView(userSession, commonKey)";
				aplicacionUsuarioApmDMAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmDMAdapterForView(userSession, commonKey);				
				aplicacionUsuarioApmDMAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.USUARIOAPMDM_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPMDM_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getUsuarioApmDMAdapterForCreate(userSession)";
				aplicacionUsuarioApmDMAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmDMAdapterForCreate(userSession,commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPMDM_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (aplicacionUsuarioApmDMAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplicacionUsuarioApmDMAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			aplicacionUsuarioApmDMAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + UsuarioApmDMAdapter.NAME + ": "+ aplicacionUsuarioApmDMAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			// Subo el apdater al userSession
			userSession.put(UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			 
			saveDemodaMessages(request, aplicacionUsuarioApmDMAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmDMAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPMDM, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			UsuarioApmDMAdapter aplicacionUsuarioApmDMAdapterVO = (UsuarioApmDMAdapter) userSession.get(UsuarioApmDMAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionUsuarioApmDMAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioApmDMAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioApmDMAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionUsuarioApmDMAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionUsuarioApmDMAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionUsuarioApmDMAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionUsuarioApmDMAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			}
			
			// llamada al servicio
			UsuarioApmDMVO aplicacionUsuarioApmDMVO = ApmServiceLocator.getAplicacionService().createUsuarioApmDM(userSession, aplicacionUsuarioApmDMAdapterVO.getUsuarioApmDM());
			
            // Tiene errores recuperables
			if (aplicacionUsuarioApmDMVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionUsuarioApmDMVO.infoString()); 
				saveDemodaErrors(request, aplicacionUsuarioApmDMVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionUsuarioApmDMVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionUsuarioApmDMVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioApmDMAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmDMAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPMDM, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			UsuarioApmDMAdapter aplicacionUsuarioApmDMAdapterVO = (UsuarioApmDMAdapter) userSession.get(UsuarioApmDMAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionUsuarioApmDMAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioApmDMAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioApmDMAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionUsuarioApmDMAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionUsuarioApmDMAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionUsuarioApmDMAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionUsuarioApmDMAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			}
			
			// llamada al servicio
			UsuarioApmDMVO aplicacionUsuarioApmDMVO = ApmServiceLocator.getAplicacionService().updateUsuarioApmDM(userSession, aplicacionUsuarioApmDMAdapterVO.getUsuarioApmDM());
			
            // Tiene errores recuperables
			if (aplicacionUsuarioApmDMVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionUsuarioApmDMAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionUsuarioApmDMVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionUsuarioApmDMVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionUsuarioApmDMAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioApmDMAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmDMAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPMDM, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			UsuarioApmDMAdapter aplicacionUsuarioApmDMAdapterVO = (UsuarioApmDMAdapter) userSession.get(UsuarioApmDMAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionUsuarioApmDMAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioApmDMAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioApmDMAdapter.NAME); 
			}

			// llamada al servicio
			UsuarioApmDMVO aplicacionUsuarioApmDMVO = ApmServiceLocator.getAplicacionService().deleteUsuarioApmDM
				(userSession, aplicacionUsuarioApmDMAdapterVO.getUsuarioApmDM());
			
            // Tiene errores recuperables
			if (aplicacionUsuarioApmDMVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionUsuarioApmDMAdapterVO.infoString());
				saveDemodaErrors(request, aplicacionUsuarioApmDMVO);				
				request.setAttribute(UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
				return mapping.findForward(ApmConstants.FWD_USUARIOAPMDM_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (aplicacionUsuarioApmDMVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionUsuarioApmDMAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioApmDMAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmDMAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, UsuarioApmDMAdapter.NAME);
		
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
			UsuarioApmDMAdapter aplicacionUsuarioApmDMAdapterVO = (UsuarioApmDMAdapter) userSession.get(UsuarioApmDMAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionUsuarioApmDMAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioApmDMAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioApmDMAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionUsuarioApmDMAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionUsuarioApmDMAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionUsuarioApmDMAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionUsuarioApmDMAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			}
			
			// llamada al servicio
			//aplicacionUsuarioApmDMAdapterVO = ApmServiceLocator.getUsuarioApmDMService().getUsuarioApmDMAdapterParam(userSession, aplicacionUsuarioApmDMAdapterVO);
			
            // Tiene errores recuperables
			if (aplicacionUsuarioApmDMAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionUsuarioApmDMAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionUsuarioApmDMAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionUsuarioApmDMAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionUsuarioApmDMAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			}
			
			// Envio el VO al request
			request.setAttribute(UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			// Subo el apdater al userSession
			userSession.put(UsuarioApmDMAdapter.NAME, aplicacionUsuarioApmDMAdapterVO);
			
			return mapping.findForward(ApmConstants.FWD_USUARIOAPMDM_EDIT_ADAPTER);
		
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmDMAdapter.NAME);
		}
	}
		
		
	
} 
