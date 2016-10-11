package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilAdapter;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilVO;
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

public final class AdministrarDispositivoMovilDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarDispositivoMovilDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_DISPOSITIVOMOVIL, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		DispositivoMovilAdapter dispositivoMovilAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getDispositivoMovilAdapterForView(userSession, commonKey)";
				dispositivoMovilAdapterVO = ApmServiceLocator.getAplicacionService().getDispositivoMovilAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_DISPOSITIVOMOVIL_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getDispositivoMovilAdapterForUpdate(userSession, commonKey)";
				dispositivoMovilAdapterVO = ApmServiceLocator.getAplicacionService().getDispositivoMovilAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_DISPOSITIVOMOVIL_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getDispositivoMovilAdapterForView(userSession, commonKey)";
				dispositivoMovilAdapterVO = ApmServiceLocator.getAplicacionService().getDispositivoMovilAdapterForView(userSession, commonKey);				
				dispositivoMovilAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.DISPOSITIVOMOVIL_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_DISPOSITIVOMOVIL_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getDispositivoMovilAdapterForCreate(userSession)";
				dispositivoMovilAdapterVO = ApmServiceLocator.getAplicacionService().getDispositivoMovilAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_DISPOSITIVOMOVIL_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (dispositivoMovilAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + dispositivoMovilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			dispositivoMovilAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + DispositivoMovilAdapter.NAME + ": "+ dispositivoMovilAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			// Subo el apdater al userSession
			userSession.put(DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			 
			saveDemodaMessages(request, dispositivoMovilAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DispositivoMovilAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_DISPOSITIVOMOVIL, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			DispositivoMovilAdapter dispositivoMovilAdapterVO = (DispositivoMovilAdapter) userSession.get(DispositivoMovilAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (dispositivoMovilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + DispositivoMovilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DispositivoMovilAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(dispositivoMovilAdapterVO, request);
			
            // Tiene errores recuperables
			if (dispositivoMovilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + dispositivoMovilAdapterVO.infoString()); 
				saveDemodaErrors(request, dispositivoMovilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			}
			
			// llamada al servicio
			DispositivoMovilVO dispositivoMovilVO = ApmServiceLocator.getAplicacionService().createDispositivoMovil(userSession, dispositivoMovilAdapterVO.getDispositivoMovil());
			
            // Tiene errores recuperables
			if (dispositivoMovilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + dispositivoMovilVO.infoString()); 
				saveDemodaErrors(request, dispositivoMovilVO);
				return forwardErrorRecoverable(mapping, request, userSession, DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (dispositivoMovilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + dispositivoMovilVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, DispositivoMovilAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DispositivoMovilAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_DISPOSITIVOMOVIL, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			DispositivoMovilAdapter dispositivoMovilAdapterVO = (DispositivoMovilAdapter) userSession.get(DispositivoMovilAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (dispositivoMovilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + DispositivoMovilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DispositivoMovilAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(dispositivoMovilAdapterVO, request);
			
            // Tiene errores recuperables
			if (dispositivoMovilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + dispositivoMovilAdapterVO.infoString()); 
				saveDemodaErrors(request, dispositivoMovilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			}
			
			// llamada al servicio
			DispositivoMovilVO dispositivoMovilVO = ApmServiceLocator.getAplicacionService().updateDispositivoMovil(userSession, dispositivoMovilAdapterVO.getDispositivoMovil());
			
            // Tiene errores recuperables
			if (dispositivoMovilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + dispositivoMovilAdapterVO.infoString()); 
				saveDemodaErrors(request, dispositivoMovilVO);
				return forwardErrorRecoverable(mapping, request, userSession, DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (dispositivoMovilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + dispositivoMovilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, DispositivoMovilAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DispositivoMovilAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_DISPOSITIVOMOVIL, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			DispositivoMovilAdapter dispositivoMovilAdapterVO = (DispositivoMovilAdapter) userSession.get(DispositivoMovilAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (dispositivoMovilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + DispositivoMovilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DispositivoMovilAdapter.NAME); 
			}

			// llamada al servicio
			DispositivoMovilVO dispositivoMovilVO = ApmServiceLocator.getAplicacionService().deleteDispositivoMovil
				(userSession, dispositivoMovilAdapterVO.getDispositivoMovil());
			
            // Tiene errores recuperables
			if (dispositivoMovilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + dispositivoMovilAdapterVO.infoString());
				saveDemodaErrors(request, dispositivoMovilVO);				
				request.setAttribute(DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
				return mapping.findForward(ApmConstants.FWD_DISPOSITIVOMOVIL_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (dispositivoMovilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + dispositivoMovilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, DispositivoMovilAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DispositivoMovilAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, DispositivoMovilAdapter.NAME);
		
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
			DispositivoMovilAdapter dispositivoMovilAdapterVO = (DispositivoMovilAdapter) userSession.get(DispositivoMovilAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (dispositivoMovilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + DispositivoMovilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DispositivoMovilAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(dispositivoMovilAdapterVO, request);
			
            // Tiene errores recuperables
			if (dispositivoMovilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + dispositivoMovilAdapterVO.infoString()); 
				saveDemodaErrors(request, dispositivoMovilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			}
			
			// llamada al servicio
			//dispositivoMovilAdapterVO = ApmServiceLocator.getDispositivoMovilService().getDispositivoMovilAdapterParam(userSession, dispositivoMovilAdapterVO);
			
            // Tiene errores recuperables
			if (dispositivoMovilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + dispositivoMovilAdapterVO.infoString()); 
				saveDemodaErrors(request, dispositivoMovilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (dispositivoMovilAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + dispositivoMovilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			}
			
			// Envio el VO al request
			request.setAttribute(DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			// Subo el apdater al userSession
			userSession.put(DispositivoMovilAdapter.NAME, dispositivoMovilAdapterVO);
			
			return mapping.findForward(ApmConstants.FWD_DISPOSITIVOMOVIL_EDIT_ADAPTER);
		
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DispositivoMovilAdapter.NAME);
		}
	}
} 
