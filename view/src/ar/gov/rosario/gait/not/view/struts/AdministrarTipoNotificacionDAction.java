package ar.gov.rosario.gait.not.view.struts;

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
import ar.gov.rosario.gait.not.iface.model.TipoNotificacionAdapter;
import ar.gov.rosario.gait.not.iface.model.TipoNotificacionVO;
import ar.gov.rosario.gait.not.iface.service.NotServiceLocator;
import ar.gov.rosario.gait.not.iface.util.NotError;
import ar.gov.rosario.gait.not.iface.util.NotSecurityConstants;
import ar.gov.rosario.gait.not.view.util.NotConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarTipoNotificacionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarTipoNotificacionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, NotSecurityConstants.ABM_TIPONOTIFICACION, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		TipoNotificacionAdapter tipoNotificacionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getTipoNotificacionAdapterForView(userSession, commonKey)";
				tipoNotificacionAdapterVO = NotServiceLocator.getNotificacionService().getTipoNotificacionAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(NotConstants.FWD_TIPONOTIFICACION_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getTipoNotificacionAdapterForUpdate(userSession, commonKey)";
				tipoNotificacionAdapterVO = NotServiceLocator.getNotificacionService().getTipoNotificacionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(NotConstants.FWD_TIPONOTIFICACION_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getTipoNotificacionAdapterForView(userSession, commonKey)";
				tipoNotificacionAdapterVO = NotServiceLocator.getNotificacionService().getTipoNotificacionAdapterForView(userSession, commonKey);				
				tipoNotificacionAdapterVO.addMessage(BaseError.MSG_ELIMINAR, NotError.TIPONOTIFICACION_LABEL);
				actionForward = mapping.findForward(NotConstants.FWD_TIPONOTIFICACION_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getTipoNotificacionAdapterForCreate(userSession)";
				tipoNotificacionAdapterVO = NotServiceLocator.getNotificacionService().getTipoNotificacionAdapterForCreate(userSession);
				actionForward = mapping.findForward(NotConstants.FWD_TIPONOTIFICACION_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (tipoNotificacionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + tipoNotificacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TipoNotificacionAdapter.NAME, tipoNotificacionAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			tipoNotificacionAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + TipoNotificacionAdapter.NAME + ": "+ tipoNotificacionAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(TipoNotificacionAdapter.NAME, tipoNotificacionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(TipoNotificacionAdapter.NAME, tipoNotificacionAdapterVO);
			 
			saveDemodaMessages(request, tipoNotificacionAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TipoNotificacionAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, NotSecurityConstants.ABM_TIPONOTIFICACION, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			TipoNotificacionAdapter tipoNotificacionAdapterVO = (TipoNotificacionAdapter) userSession.get(TipoNotificacionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (tipoNotificacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + TipoNotificacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TipoNotificacionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(tipoNotificacionAdapterVO, request);
			
            // Tiene errores recuperables
			if (tipoNotificacionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoNotificacionAdapterVO.infoString()); 
				saveDemodaErrors(request, tipoNotificacionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoNotificacionAdapter.NAME, tipoNotificacionAdapterVO);
			}
			
			// llamada al servicio
			TipoNotificacionVO tipoNotificacionVO = NotServiceLocator.getNotificacionService().createTipoNotificacion(userSession, tipoNotificacionAdapterVO.getTipoNotificacion());
			
            // Tiene errores recuperables
			if (tipoNotificacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoNotificacionVO.infoString()); 
				saveDemodaErrors(request, tipoNotificacionVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoNotificacionAdapter.NAME, tipoNotificacionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (tipoNotificacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tipoNotificacionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TipoNotificacionAdapter.NAME, tipoNotificacionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, TipoNotificacionAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TipoNotificacionAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, NotSecurityConstants.ABM_TIPONOTIFICACION, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			TipoNotificacionAdapter tipoNotificacionAdapterVO = (TipoNotificacionAdapter) userSession.get(TipoNotificacionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (tipoNotificacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + TipoNotificacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TipoNotificacionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(tipoNotificacionAdapterVO, request);
			
            // Tiene errores recuperables
			if (tipoNotificacionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoNotificacionAdapterVO.infoString()); 
				saveDemodaErrors(request, tipoNotificacionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoNotificacionAdapter.NAME, tipoNotificacionAdapterVO);
			}
			
			// llamada al servicio
			TipoNotificacionVO tipoNotificacionVO = NotServiceLocator.getNotificacionService().updateTipoNotificacion(userSession, tipoNotificacionAdapterVO.getTipoNotificacion());
			
            // Tiene errores recuperables
			if (tipoNotificacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoNotificacionAdapterVO.infoString()); 
				saveDemodaErrors(request, tipoNotificacionVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoNotificacionAdapter.NAME, tipoNotificacionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (tipoNotificacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tipoNotificacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TipoNotificacionAdapter.NAME, tipoNotificacionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, TipoNotificacionAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TipoNotificacionAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, NotSecurityConstants.ABM_TIPONOTIFICACION, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			TipoNotificacionAdapter tipoNotificacionAdapterVO = (TipoNotificacionAdapter) userSession.get(TipoNotificacionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (tipoNotificacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + TipoNotificacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TipoNotificacionAdapter.NAME); 
			}

			// llamada al servicio
			TipoNotificacionVO tipoNotificacionVO = NotServiceLocator.getNotificacionService().deleteTipoNotificacion
				(userSession, tipoNotificacionAdapterVO.getTipoNotificacion());
			
            // Tiene errores recuperables
			if (tipoNotificacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoNotificacionAdapterVO.infoString());
				saveDemodaErrors(request, tipoNotificacionVO);				
				request.setAttribute(TipoNotificacionAdapter.NAME, tipoNotificacionAdapterVO);
				return mapping.findForward(NotConstants.FWD_TIPONOTIFICACION_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (tipoNotificacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tipoNotificacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TipoNotificacionAdapter.NAME, tipoNotificacionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, TipoNotificacionAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TipoNotificacionAdapter.NAME);
		}
	}
		
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, TipoNotificacionAdapter.NAME);
		
	}
	
} 
