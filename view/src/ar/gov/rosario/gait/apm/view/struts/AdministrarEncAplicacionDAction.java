package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.AplicacionAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionVO;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarEncAplicacionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarEncAplicacionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);		
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACION_ENC, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		AplicacionAdapter aplicacionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (navModel.getAct().equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplicacionAdapterForUpdate(userSession, commonKey)";
				aplicacionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACION_ENC_EDIT_ADAPTER);
			}
			if (navModel.getAct().equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplicacionAdapterForCreate(userSession)";
				aplicacionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACION_ENC_EDIT_ADAPTER);
				//Copiar los filtros de busqueda al adapter 
				if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) DemodaUtil.populateVO(aplicacionAdapterVO, request);				
			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (aplicacionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplicacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionAdapter.ENC_NAME, aplicacionAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			aplicacionAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplicacionAdapter.ENC_NAME + ": "+ aplicacionAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(AplicacionAdapter.ENC_NAME, aplicacionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionAdapter.ENC_NAME, aplicacionAdapterVO);

			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionAdapter.ENC_NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, 
			ApmSecurityConstants.ABM_APLICACION_ENC, BaseSecurityConstants.AGREGAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionAdapter aplicacionAdapterVO = (AplicacionAdapter) userSession.get(AplicacionAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionAdapter.ENC_NAME, aplicacionAdapterVO);
			}
			
			// llamada al servicio
			AplicacionVO aplicacionVO = ApmServiceLocator.getAplicacionService().createAplicacion(userSession, aplicacionAdapterVO.getAplicacion());
			
            // Tiene errores recuperables
			if (aplicacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionVO.infoString()); 
				saveDemodaErrors(request, aplicacionVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionAdapter.ENC_NAME, aplicacionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionAdapter.ENC_NAME, aplicacionAdapterVO);
			}

			// Si tiene permiso lo dirijo al adapter de modificacion, 
			// sino vuelve al searchPage
			if (hasAccess(userSession, ApmSecurityConstants.ABM_APLICACION, 
				BaseSecurityConstants.MODIFICAR)) {
				
				// seteo el id para que lo use el siguiente action 
				userSession.getNavModel().setSelectedId(aplicacionVO.getId().toString());

				// lo dirijo al adapter de modificacion
				return forwardConfirmarOk(mapping, request, funcName, AplicacionAdapter.ENC_NAME, 
					ApmConstants.PATH_ADMINISTRAR_APLICACION, BaseConstants.METHOD_INICIALIZAR, 
					BaseConstants.ACT_MODIFICAR);
			} else {
				
				// lo dirijo al searchPage				
				return forwardConfirmarOk(mapping, request, funcName, AplicacionAdapter.ENC_NAME);
				
			}
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionAdapter.ENC_NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACION_ENC, BaseSecurityConstants.MODIFICAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionAdapter aplicacionAdapterVO = (AplicacionAdapter) userSession.get(AplicacionAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionAdapter.ENC_NAME, aplicacionAdapterVO);
			}
			
			// llamada al servicio
			AplicacionVO aplicacionVO = ApmServiceLocator.getAplicacionService().updateAplicacion(userSession, aplicacionAdapterVO.getAplicacion());
			
            // Tiene errores recuperables
			if (aplicacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionAdapter.ENC_NAME, aplicacionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionAdapter.ENC_NAME, aplicacionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionAdapter.ENC_NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionAdapter.ENC_NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AplicacionAdapter.ENC_NAME);
	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, AplicacionAdapter.ENC_NAME);		
	}
	
}
	
