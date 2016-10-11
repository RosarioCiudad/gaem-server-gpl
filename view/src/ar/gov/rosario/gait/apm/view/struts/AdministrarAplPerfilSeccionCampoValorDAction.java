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


public final class AdministrarAplPerfilSeccionCampoValorDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarAplPerfilSeccionCampoValorDAction.class);
	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		AplPerfilSeccionCampoValorAdapter aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getAplPerfilSeccionCampoValorAdapterForView(userSession, commonKey)";
				aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoValorAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOR_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplPerfilSeccionCampoValorAdapterForUpdate(userSession, commonKey)";
				aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoValorAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOR_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getAplPerfilSeccionCampoValorAdapterForView(userSession, commonKey)";
				aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoValorAdapterForView(userSession, commonKey);				
				aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.CAMPO_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOR_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplPerfilSeccionCampoValorAdapterForCreate(userSession)";
				aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoValorAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOR_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplPerfilSeccionCampoValorAdapter.NAME + ": "+ aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);

			saveDemodaMessages(request, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoValorAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplPerfilSeccionCampoValorAdapter aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO = (AplPerfilSeccionCampoValorAdapter) userSession.get(AplPerfilSeccionCampoValorAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoValorAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO, request);

			// Tiene errores recuperables
			if (aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
			}

			// llamada al servicio
			AplPerfilSeccionCampoValorVO aplPerfilSeccionAplPerfilSeccionCampoValorVO = ApmServiceLocator.getAplicacionService().createAplPerfilSeccionCampoValor(userSession, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.getAplPerfilSeccionCampoValor());

			// Tiene errores recuperables
			if (aplPerfilSeccionAplPerfilSeccionCampoValorVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionAplPerfilSeccionCampoValorVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionAplPerfilSeccionCampoValorVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplPerfilSeccionAplPerfilSeccionCampoValorVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionAplPerfilSeccionCampoValorVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoValorAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplPerfilSeccionCampoValorAdapter aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO = (AplPerfilSeccionCampoValorAdapter) userSession.get(AplPerfilSeccionCampoValorAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoValorAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO, request);

			// Tiene errores recuperables
			if (aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
			}

			// llamada al servicio
			AplPerfilSeccionCampoValorVO aplPerfilSeccionAplPerfilSeccionCampoValorVO = ApmServiceLocator.getAplicacionService().updateAplPerfilSeccionCampoValor(userSession, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.getAplPerfilSeccionCampoValor());

			// Tiene errores recuperables
			if (aplPerfilSeccionAplPerfilSeccionCampoValorVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionAplPerfilSeccionCampoValorVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplPerfilSeccionAplPerfilSeccionCampoValorVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoValorAdapter.NAME);
		}
	}


	public ActionForward modificarEncabezado(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_APLPERFILSECCIONCAMPOVALOR, BaseConstants.ACT_MODIFICAR);

	}

	
	
	
	public ActionForward agregarAplPerfilSeccionCampoValorOpcion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLPERFILSECCIONCAMPOVALOROPCION);
	}	

	public ActionForward verAplPerfilSeccionCampoValorOpcion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLPERFILSECCIONCAMPOVALOROPCION);
	}	
	public ActionForward modificarAplPerfilSeccionCampoValorOpcion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLPERFILSECCIONCAMPOVALOROPCION);
	}	
	public ActionForward eliminarAplPerfilSeccionCampoValorOpcion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLPERFILSECCIONCAMPOVALOROPCION);
	}	

	
	
	
	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplPerfilSeccionCampoValorAdapter aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO = (AplPerfilSeccionCampoValorAdapter) userSession.get(AplPerfilSeccionCampoValorAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoValorAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.NAME); 
			}

			// llamada al servicio
			AplPerfilSeccionCampoValorVO aplPerfilSeccionAplPerfilSeccionCampoValorVO = ApmServiceLocator.getAplicacionService().deleteAplPerfilSeccionCampoValor
					(userSession, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.getAplPerfilSeccionCampoValor());

			// Tiene errores recuperables
			if (aplPerfilSeccionAplPerfilSeccionCampoValorVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.infoString());
				saveDemodaErrors(request, aplPerfilSeccionAplPerfilSeccionCampoValorVO);				
				request.setAttribute(AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
				return mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOR_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (aplPerfilSeccionAplPerfilSeccionCampoValorVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoValorAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return baseVolver(mapping, form, request, response, AplPerfilSeccionCampoValorAdapter.NAME);

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
			AplPerfilSeccionCampoValorAdapter aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO = (AplPerfilSeccionCampoValorAdapter) userSession.get(AplPerfilSeccionCampoValorAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoValorAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO, request);

			// Tiene errores recuperables
			if (aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
			}

			// llamada al servicio
			//aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO = ApmServiceLocator.getAplPerfilSeccionCampoValorService().getAplPerfilSeccionCampoValorAdapterParam(userSession, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);

			// Tiene errores recuperables
			if (aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplPerfilSeccionCampoValorAdapter.NAME, aplPerfilSeccionAplPerfilSeccionCampoValorAdapterVO);

			return mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOR_ADAPTER);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoValorAdapter.NAME);
		}
	}


	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, AplPerfilSeccionCampoValorAdapter.NAME);			
	}


} 
