package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorVO;
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

public final class AdministrarEncAplPerfilSeccionCampoValorDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarEncAplPerfilSeccionCampoValorDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);		
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR_ENC, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		AplPerfilSeccionCampoValorAdapter aplPerfilSeccionCampoValorAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (navModel.getAct().equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplPerfilSeccionCampoValorAdapterForUpdate(userSession, commonKey)";
				aplPerfilSeccionCampoValorAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoValorAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOR_ENC_EDIT_ADAPTER);
			}
			if (navModel.getAct().equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplPerfilSeccionCampoValorAdapterForCreate(userSession)";
				aplPerfilSeccionCampoValorAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoValorAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOR_ENC_EDIT_ADAPTER);
				//Copiar los filtros de busqueda al adapter 
				if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) DemodaUtil.populateVO(aplPerfilSeccionCampoValorAdapterVO, request);				
			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (aplPerfilSeccionCampoValorAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplPerfilSeccionCampoValorAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.ENC_NAME, aplPerfilSeccionCampoValorAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			aplPerfilSeccionCampoValorAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplPerfilSeccionCampoValorAdapter.ENC_NAME + ": "+ aplPerfilSeccionCampoValorAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(AplPerfilSeccionCampoValorAdapter.ENC_NAME, aplPerfilSeccionCampoValorAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplPerfilSeccionCampoValorAdapter.ENC_NAME, aplPerfilSeccionCampoValorAdapterVO);

			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoValorAdapter.ENC_NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, 
			ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR_ENC, BaseSecurityConstants.AGREGAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplPerfilSeccionCampoValorAdapter aplPerfilSeccionCampoValorAdapterVO = (AplPerfilSeccionCampoValorAdapter) userSession.get(AplPerfilSeccionCampoValorAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (aplPerfilSeccionCampoValorAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoValorAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplPerfilSeccionCampoValorAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplPerfilSeccionCampoValorAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoValorAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorAdapter.ENC_NAME, aplPerfilSeccionCampoValorAdapterVO);
			}
			
			// llamada al servicio
			AplPerfilSeccionCampoValorVO aplPerfilSeccionCampoValorVO = ApmServiceLocator.getAplicacionService().createAplPerfilSeccionCampoValor(userSession, aplPerfilSeccionCampoValorAdapterVO.getAplPerfilSeccionCampoValor());
			
            // Tiene errores recuperables
			if (aplPerfilSeccionCampoValorVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoValorVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoValorVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorAdapter.ENC_NAME, aplPerfilSeccionCampoValorAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplPerfilSeccionCampoValorVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionCampoValorVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.ENC_NAME, aplPerfilSeccionCampoValorAdapterVO);
			}

			// Si tiene permiso lo dirijo al adapter de modificacion, 
			// sino vuelve al searchPage
//			if (hasAccess(userSession, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR, 
//				BaseSecurityConstants.MODIFICAR)) {
//				
//				// seteo el id para que lo use el siguiente action 
//				userSession.getNavModel().setSelectedId(aplPerfilSeccionCampoValorVO.getId().toString());
//
//				// lo dirijo al adapter de modificacion
//				return forwardConfirmarOk(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.ENC_NAME, 
//					ApmConstants.PATH_ADMINISTRAR_APLPERFILSECCIONCAMPOVALOR, BaseConstants.METHOD_INICIALIZAR, 
//					BaseConstants.ACT_MODIFICAR);
//			} else {
//				
				// lo dirijo al searchPage				
				return forwardConfirmarOk(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.ENC_NAME);
				
			//}
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoValorAdapter.ENC_NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR_ENC, BaseSecurityConstants.MODIFICAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplPerfilSeccionCampoValorAdapter aplPerfilSeccionCampoValorAdapterVO = (AplPerfilSeccionCampoValorAdapter) userSession.get(AplPerfilSeccionCampoValorAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (aplPerfilSeccionCampoValorAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoValorAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplPerfilSeccionCampoValorAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplPerfilSeccionCampoValorAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoValorAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorAdapter.ENC_NAME, aplPerfilSeccionCampoValorAdapterVO);
			}
			
			// llamada al servicio
			AplPerfilSeccionCampoValorVO aplPerfilSeccionCampoValorVO = ApmServiceLocator.getAplicacionService().updateAplPerfilSeccionCampoValor(userSession, aplPerfilSeccionCampoValorAdapterVO.getAplPerfilSeccionCampoValor());
			
            // Tiene errores recuperables
			if (aplPerfilSeccionCampoValorVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoValorVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorAdapter.ENC_NAME, aplPerfilSeccionCampoValorAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplPerfilSeccionCampoValorVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionCampoValorAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.ENC_NAME, aplPerfilSeccionCampoValorAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.ENC_NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoValorAdapter.ENC_NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AplPerfilSeccionCampoValorAdapter.ENC_NAME);
	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, AplPerfilSeccionCampoValorAdapter.ENC_NAME);		
	}
	
}
	
