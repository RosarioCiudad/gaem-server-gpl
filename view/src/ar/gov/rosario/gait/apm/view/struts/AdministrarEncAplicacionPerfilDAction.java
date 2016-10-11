package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
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

public final class AdministrarEncAplicacionPerfilDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarEncAplicacionPerfilDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);		
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFIL_ENC, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		AplicacionPerfilAdapter aplicacionPerfilAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (navModel.getAct().equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplicacionPerfilAdapterForUpdate(userSession, commonKey)";
				aplicacionPerfilAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFIL_ENC_EDIT_ADAPTER);
			}
			if (navModel.getAct().equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplicacionPerfilAdapterForCreate(userSession)";
				aplicacionPerfilAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFIL_ENC_EDIT_ADAPTER);
				//Copiar los filtros de busqueda al adapter 
				if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) DemodaUtil.populateVO(aplicacionPerfilAdapterVO, request);				
			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (aplicacionPerfilAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilAdapter.ENC_NAME, aplicacionPerfilAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			aplicacionPerfilAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplicacionPerfilAdapter.ENC_NAME + ": "+ aplicacionPerfilAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(AplicacionPerfilAdapter.ENC_NAME, aplicacionPerfilAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionPerfilAdapter.ENC_NAME, aplicacionPerfilAdapterVO);

			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilAdapter.ENC_NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, 
			ApmSecurityConstants.ABM_APLICACIONPERFIL_ENC, BaseSecurityConstants.AGREGAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionPerfilAdapter aplicacionPerfilAdapterVO = (AplicacionPerfilAdapter) userSession.get(AplicacionPerfilAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionPerfilAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionPerfilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilAdapter.ENC_NAME, aplicacionPerfilAdapterVO);
			}
			
			// llamada al servicio
			AplicacionPerfilVO aplicacionPerfilVO = ApmServiceLocator.getAplicacionService().createAplicacionPerfil(userSession, aplicacionPerfilAdapterVO.getAplicacionPerfil());
			
            // Tiene errores recuperables
			if (aplicacionPerfilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilAdapter.ENC_NAME, aplicacionPerfilAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionPerfilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilAdapter.ENC_NAME, aplicacionPerfilAdapterVO);
			}

		/*	// Si tiene permiso lo dirijo al adapter de modificacion, 
			// sino vuelve al searchPage
			if (hasAccess(userSession, ApmSecurityConstants.ABM_APLICACIONPERFIL, 
				BaseSecurityConstants.MODIFICAR)) {
				
				// seteo el id para que lo use el siguiente action 
				userSession.getNavModel().setSelectedId(aplicacionPerfilVO.getId().toString());

				// lo dirijo al adapter de modificacion
				return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilAdapter.ENC_NAME, 
					ApmConstants.FWD_APLICACIONPERFIL_SEARCHPAGE, BaseConstants.METHOD_INICIALIZAR, 
					BaseConstants.ACT_MODIFICAR);
			} else {*/
				
				// lo dirijo al searchPage				
				return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilAdapter.ENC_NAME);
				
			//}
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilAdapter.ENC_NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFIL_ENC, BaseSecurityConstants.MODIFICAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionPerfilAdapter aplicacionPerfilAdapterVO = (AplicacionPerfilAdapter) userSession.get(AplicacionPerfilAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionPerfilAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionPerfilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilAdapter.ENC_NAME, aplicacionPerfilAdapterVO);
			}
			
			// llamada al servicio
			AplicacionPerfilVO aplicacionPerfilVO = ApmServiceLocator.getAplicacionService().updateAplicacionPerfil(userSession, aplicacionPerfilAdapterVO.getAplicacionPerfil());
			
            // Tiene errores recuperables
			if (aplicacionPerfilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilAdapter.ENC_NAME, aplicacionPerfilAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionPerfilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilAdapter.ENC_NAME, aplicacionPerfilAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilAdapter.ENC_NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilAdapter.ENC_NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AplicacionPerfilAdapter.ENC_NAME);
	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, AplicacionPerfilAdapter.ENC_NAME);		
	}
	
}
	
