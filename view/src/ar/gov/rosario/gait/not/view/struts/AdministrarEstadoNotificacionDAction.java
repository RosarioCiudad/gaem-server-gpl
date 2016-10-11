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
import ar.gov.rosario.gait.not.iface.model.EstadoNotificacionAdapter;
import ar.gov.rosario.gait.not.iface.model.EstadoNotificacionVO;
import ar.gov.rosario.gait.not.iface.service.NotServiceLocator;
import ar.gov.rosario.gait.not.iface.util.NotError;
import ar.gov.rosario.gait.not.iface.util.NotSecurityConstants;
import ar.gov.rosario.gait.not.view.util.NotConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarEstadoNotificacionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarEstadoNotificacionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, NotSecurityConstants.ABM_ESTADONOTIFICACION, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		EstadoNotificacionAdapter estadoNotificacionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getEstadoNotificacionAdapterForView(userSession, commonKey)";
				estadoNotificacionAdapterVO = NotServiceLocator.getNotificacionService().getEstadoNotificacionAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(NotConstants.FWD_ESTADONOTIFICACION_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getEstadoNotificacionAdapterForUpdate(userSession, commonKey)";
				estadoNotificacionAdapterVO = NotServiceLocator.getNotificacionService().getEstadoNotificacionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(NotConstants.FWD_ESTADONOTIFICACION_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getEstadoNotificacionAdapterForView(userSession, commonKey)";
				estadoNotificacionAdapterVO = NotServiceLocator.getNotificacionService().getEstadoNotificacionAdapterForView(userSession, commonKey);				
				estadoNotificacionAdapterVO.addMessage(BaseError.MSG_ELIMINAR, NotError.ESTADONOTIFICACION_LABEL);
				actionForward = mapping.findForward(NotConstants.FWD_ESTADONOTIFICACION_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getEstadoNotificacionAdapterForCreate(userSession)";
				estadoNotificacionAdapterVO = NotServiceLocator.getNotificacionService().getEstadoNotificacionAdapterForCreate(userSession);
				actionForward = mapping.findForward(NotConstants.FWD_ESTADONOTIFICACION_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (estadoNotificacionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + estadoNotificacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, EstadoNotificacionAdapter.NAME, estadoNotificacionAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			estadoNotificacionAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + EstadoNotificacionAdapter.NAME + ": "+ estadoNotificacionAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(EstadoNotificacionAdapter.NAME, estadoNotificacionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(EstadoNotificacionAdapter.NAME, estadoNotificacionAdapterVO);
			 
			saveDemodaMessages(request, estadoNotificacionAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, EstadoNotificacionAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, NotSecurityConstants.ABM_ESTADONOTIFICACION, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			EstadoNotificacionAdapter estadoNotificacionAdapterVO = (EstadoNotificacionAdapter) userSession.get(EstadoNotificacionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (estadoNotificacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + EstadoNotificacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, EstadoNotificacionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(estadoNotificacionAdapterVO, request);
			
            // Tiene errores recuperables
			if (estadoNotificacionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoNotificacionAdapterVO.infoString()); 
				saveDemodaErrors(request, estadoNotificacionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoNotificacionAdapter.NAME, estadoNotificacionAdapterVO);
			}
			
			// llamada al servicio
			EstadoNotificacionVO estadoNotificacionVO = NotServiceLocator.getNotificacionService().createEstadoNotificacion(userSession, estadoNotificacionAdapterVO.getEstadoNotificacion());
			
            // Tiene errores recuperables
			if (estadoNotificacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoNotificacionVO.infoString()); 
				saveDemodaErrors(request, estadoNotificacionVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoNotificacionAdapter.NAME, estadoNotificacionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (estadoNotificacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + estadoNotificacionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, EstadoNotificacionAdapter.NAME, estadoNotificacionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, EstadoNotificacionAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, EstadoNotificacionAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, NotSecurityConstants.ABM_ESTADONOTIFICACION, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			EstadoNotificacionAdapter estadoNotificacionAdapterVO = (EstadoNotificacionAdapter) userSession.get(EstadoNotificacionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (estadoNotificacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + EstadoNotificacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, EstadoNotificacionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(estadoNotificacionAdapterVO, request);
			
            // Tiene errores recuperables
			if (estadoNotificacionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoNotificacionAdapterVO.infoString()); 
				saveDemodaErrors(request, estadoNotificacionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoNotificacionAdapter.NAME, estadoNotificacionAdapterVO);
			}
			
			// llamada al servicio
			EstadoNotificacionVO estadoNotificacionVO = NotServiceLocator.getNotificacionService().updateEstadoNotificacion(userSession, estadoNotificacionAdapterVO.getEstadoNotificacion());
			
            // Tiene errores recuperables
			if (estadoNotificacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoNotificacionAdapterVO.infoString()); 
				saveDemodaErrors(request, estadoNotificacionVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoNotificacionAdapter.NAME, estadoNotificacionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (estadoNotificacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + estadoNotificacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, EstadoNotificacionAdapter.NAME, estadoNotificacionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, EstadoNotificacionAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, EstadoNotificacionAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, NotSecurityConstants.ABM_ESTADONOTIFICACION, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			EstadoNotificacionAdapter estadoNotificacionAdapterVO = (EstadoNotificacionAdapter) userSession.get(EstadoNotificacionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (estadoNotificacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + EstadoNotificacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, EstadoNotificacionAdapter.NAME); 
			}

			// llamada al servicio
			EstadoNotificacionVO estadoNotificacionVO = NotServiceLocator.getNotificacionService().deleteEstadoNotificacion
				(userSession, estadoNotificacionAdapterVO.getEstadoNotificacion());
			
            // Tiene errores recuperables
			if (estadoNotificacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoNotificacionAdapterVO.infoString());
				saveDemodaErrors(request, estadoNotificacionVO);				
				request.setAttribute(EstadoNotificacionAdapter.NAME, estadoNotificacionAdapterVO);
				return mapping.findForward(NotConstants.FWD_ESTADONOTIFICACION_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (estadoNotificacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + estadoNotificacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, EstadoNotificacionAdapter.NAME, estadoNotificacionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, EstadoNotificacionAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, EstadoNotificacionAdapter.NAME);
		}
	}
		
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, EstadoNotificacionAdapter.NAME);
		
	}
	
} 
