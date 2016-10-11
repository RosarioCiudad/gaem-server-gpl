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



public final class AdministrarCampoDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarCampoDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPO, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		CampoAdapter campoAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getCampoAdapterForView(userSession, commonKey)";
				campoAdapterVO = ApmServiceLocator.getAplicacionService().getCampoAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getCampoAdapterForUpdate(userSession, commonKey)";
				campoAdapterVO = ApmServiceLocator.getAplicacionService().getCampoAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPO_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getCampoAdapterForView(userSession, commonKey)";
				campoAdapterVO = ApmServiceLocator.getAplicacionService().getCampoAdapterForView(userSession, commonKey);				
				campoAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.CAMPO_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPO_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getCampoAdapterForCreate(userSession)";
				campoAdapterVO = ApmServiceLocator.getAplicacionService().getCampoAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPO_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (campoAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + campoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoAdapter.NAME, campoAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			campoAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + CampoAdapter.NAME + ": "+ campoAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(CampoAdapter.NAME, campoAdapterVO);
			// Subo el apdater al userSession
			userSession.put(CampoAdapter.NAME, campoAdapterVO);

			saveDemodaMessages(request, campoAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPO, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			CampoAdapter campoAdapterVO = (CampoAdapter) userSession.get(CampoAdapter.NAME);

			// Si es nulo no se puede continuar
			if (campoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(campoAdapterVO, request);

			// Tiene errores recuperables
			if (campoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoAdapterVO.infoString()); 
				saveDemodaErrors(request, campoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoAdapter.NAME, campoAdapterVO);
			}

			// llamada al servicio
			CampoVO campoVO = ApmServiceLocator.getAplicacionService().createCampo(userSession, campoAdapterVO.getCampo());

			// Tiene errores recuperables
			if (campoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoVO.infoString()); 
				saveDemodaErrors(request, campoVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoAdapter.NAME, campoAdapterVO);
			}

			// Tiene errores no recuperables
			if (campoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoAdapter.NAME, campoAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, CampoAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPO, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			CampoAdapter campoAdapterVO = (CampoAdapter) userSession.get(CampoAdapter.NAME);

			// Si es nulo no se puede continuar
			if (campoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(campoAdapterVO, request);

			// Tiene errores recuperables
			if (campoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoAdapterVO.infoString()); 
				saveDemodaErrors(request, campoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoAdapter.NAME, campoAdapterVO);
			}

			// llamada al servicio
			CampoVO campoVO = ApmServiceLocator.getAplicacionService().updateCampo(userSession, campoAdapterVO.getCampo());

			// Tiene errores recuperables
			if (campoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoAdapterVO.infoString()); 
				saveDemodaErrors(request, campoVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoAdapter.NAME, campoAdapterVO);
			}

			// Tiene errores no recuperables
			if (campoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoAdapter.NAME, campoAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, CampoAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoAdapter.NAME);
		}
	}


	public ActionForward modificarEncabezado(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_CAMPO, BaseConstants.ACT_MODIFICAR);

	}
	
	public ActionForward agregarCampoValor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_CAMPOVALOR);
	}	

	public ActionForward verCampoValor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_CAMPOVALOR);
	}	
	public ActionForward modificarCampoValor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_CAMPOVALOR);
	}	
	public ActionForward eliminarCampoValor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_CAMPOVALOR);
	}	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPO, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			CampoAdapter campoAdapterVO = (CampoAdapter) userSession.get(CampoAdapter.NAME);

			// Si es nulo no se puede continuar
			if (campoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoAdapter.NAME); 
			}

			// llamada al servicio
			CampoVO campoVO = ApmServiceLocator.getAplicacionService().deleteCampo
					(userSession, campoAdapterVO.getCampo());

			// Tiene errores recuperables
			if (campoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoAdapterVO.infoString());
				saveDemodaErrors(request, campoVO);				
				request.setAttribute(CampoAdapter.NAME, campoAdapterVO);
				return mapping.findForward(ApmConstants.FWD_CAMPO_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (campoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoAdapter.NAME, campoAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, CampoAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return baseVolver(mapping, form, request, response, CampoAdapter.NAME);

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
			CampoAdapter campoAdapterVO = (CampoAdapter) userSession.get(CampoAdapter.NAME);

			// Si es nulo no se puede continuar
			if (campoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(campoAdapterVO, request);

			// Tiene errores recuperables
			if (campoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoAdapterVO.infoString()); 
				saveDemodaErrors(request, campoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoAdapter.NAME, campoAdapterVO);
			}

			// llamada al servicio
			//campoAdapterVO = ApmServiceLocator.getCampoService().getCampoAdapterParam(userSession, campoAdapterVO);

			// Tiene errores recuperables
			if (campoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoAdapterVO.infoString()); 
				saveDemodaErrors(request, campoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoAdapter.NAME, campoAdapterVO);
			}

			// Tiene errores no recuperables
			if (campoAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoAdapter.NAME, campoAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(CampoAdapter.NAME, campoAdapterVO);
			// Subo el apdater al userSession
			userSession.put(CampoAdapter.NAME, campoAdapterVO);

			return mapping.findForward(ApmConstants.FWD_CAMPO_ADAPTER);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoAdapter.NAME);
		}
	}


	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, CampoAdapter.NAME);			
	}


} 
