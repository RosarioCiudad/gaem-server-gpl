package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.CampoAdapter;
import ar.gov.rosario.gait.apm.iface.model.CampoVO;
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

public final class AdministrarEncCampoDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarEncCampoDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);		
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPO_ENC, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		CampoAdapter campoAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (navModel.getAct().equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getCampoAdapterForUpdate(userSession, commonKey)";
				campoAdapterVO = ApmServiceLocator.getAplicacionService().getCampoAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPO_ENC_EDIT_ADAPTER);
			}
			if (navModel.getAct().equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getCampoAdapterForCreate(userSession)";
				campoAdapterVO = ApmServiceLocator.getAplicacionService().getCampoAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPO_ENC_EDIT_ADAPTER);
				//Copiar los filtros de busqueda al adapter 
				if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) DemodaUtil.populateVO(campoAdapterVO, request);				
			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (campoAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + campoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoAdapter.ENC_NAME, campoAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			campoAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + CampoAdapter.ENC_NAME + ": "+ campoAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(CampoAdapter.ENC_NAME, campoAdapterVO);
			// Subo el apdater al userSession
			userSession.put(CampoAdapter.ENC_NAME, campoAdapterVO);

			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoAdapter.ENC_NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, 
			ApmSecurityConstants.ABM_CAMPO_ENC, BaseSecurityConstants.AGREGAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			CampoAdapter campoAdapterVO = (CampoAdapter) userSession.get(CampoAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (campoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(campoAdapterVO, request);
			
            // Tiene errores recuperables
			if (campoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoAdapterVO.infoString()); 
				saveDemodaErrors(request, campoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoAdapter.ENC_NAME, campoAdapterVO);
			}
			
			// llamada al servicio
			CampoVO campoVO = ApmServiceLocator.getAplicacionService().createCampo(userSession, campoAdapterVO.getCampo());
			
            // Tiene errores recuperables
			if (campoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoVO.infoString()); 
				saveDemodaErrors(request, campoVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoAdapter.ENC_NAME, campoAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (campoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoAdapter.ENC_NAME, campoAdapterVO);
			}

			// Si tiene permiso lo dirijo al adapter de modificacion, 
			// sino vuelve al searchPage
			if (hasAccess(userSession, ApmSecurityConstants.ABM_CAMPO, 
				BaseSecurityConstants.MODIFICAR)) {
				
				// seteo el id para que lo use el siguiente action 
				userSession.getNavModel().setSelectedId(campoVO.getId().toString());

				// lo dirijo al adapter de modificacion
				return forwardConfirmarOk(mapping, request, funcName, CampoAdapter.ENC_NAME, 
					ApmConstants.PATH_ADMINISTRAR_CAMPO, BaseConstants.METHOD_INICIALIZAR, 
					BaseConstants.ACT_MODIFICAR);
			} else {
				
				// lo dirijo al searchPage				
				return forwardConfirmarOk(mapping, request, funcName, CampoAdapter.ENC_NAME);
				
			}
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoAdapter.ENC_NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPO_ENC, BaseSecurityConstants.MODIFICAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			CampoAdapter campoAdapterVO = (CampoAdapter) userSession.get(CampoAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (campoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(campoAdapterVO, request);
			
            // Tiene errores recuperables
			if (campoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoAdapterVO.infoString()); 
				saveDemodaErrors(request, campoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoAdapter.ENC_NAME, campoAdapterVO);
			}
			
			// llamada al servicio
			CampoVO campoVO = ApmServiceLocator.getAplicacionService().updateCampo(userSession, campoAdapterVO.getCampo());
			
            // Tiene errores recuperables
			if (campoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoAdapterVO.infoString()); 
				saveDemodaErrors(request, campoVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoAdapter.ENC_NAME, campoAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (campoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoAdapter.ENC_NAME, campoAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, CampoAdapter.ENC_NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoAdapter.ENC_NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, CampoAdapter.ENC_NAME);
	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, CampoAdapter.ENC_NAME);		
	}
	
}
	
