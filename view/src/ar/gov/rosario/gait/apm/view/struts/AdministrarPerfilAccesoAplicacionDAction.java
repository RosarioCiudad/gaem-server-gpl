package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoAplicacionAdapter;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoAplicacionVO;
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

public final class AdministrarPerfilAccesoAplicacionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarPerfilAccesoAplicacionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESOAPLICACION, act); 
		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		PerfilAccesoAplicacionAdapter perfilAccesoAplicacionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getPerfilAccesoAplicacionAdapterForView(userSession, commonKey)";
				perfilAccesoAplicacionAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoAplicacionAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESOAPLICACION_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getPerfilAccesoAplicacionAdapterForUpdate(userSession, commonKey)";
				perfilAccesoAplicacionAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoAplicacionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESOAPLICACION_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getPerfilAccesoAplicacionAdapterForView(userSession, commonKey)";
				perfilAccesoAplicacionAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoAplicacionAdapterForView(userSession, commonKey);				
				perfilAccesoAplicacionAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.PERFILACCESOAPLICACION_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESOAPLICACION_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getPerfilAccesoAplicacionAdapterForCreate(userSession)";
				perfilAccesoAplicacionAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoAplicacionAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESOAPLICACION_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (perfilAccesoAplicacionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + perfilAccesoAplicacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoAplicacionAdapter.NAME, perfilAccesoAplicacionAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			perfilAccesoAplicacionAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + PerfilAccesoAplicacionAdapter.NAME + ": "+ perfilAccesoAplicacionAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(PerfilAccesoAplicacionAdapter.NAME, perfilAccesoAplicacionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(PerfilAccesoAplicacionAdapter.NAME, perfilAccesoAplicacionAdapterVO);

			saveDemodaMessages(request, perfilAccesoAplicacionAdapterVO);

			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoAplicacionAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESOAPLICACION, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			PerfilAccesoAplicacionAdapter perfilAccesoAplicacionAdapterVO = (PerfilAccesoAplicacionAdapter) userSession.get(PerfilAccesoAplicacionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (perfilAccesoAplicacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + PerfilAccesoAplicacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PerfilAccesoAplicacionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(perfilAccesoAplicacionAdapterVO, request);

			// Tiene errores recuperables
			if (perfilAccesoAplicacionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoAplicacionAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoAplicacionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoAplicacionAdapter.NAME, perfilAccesoAplicacionAdapterVO);
			}

			// llamada al servicio
			PerfilAccesoAplicacionVO perfilAccesoAplicacionVO = ApmServiceLocator.getAplicacionService().createPerfilAccesoAplicacion(userSession, perfilAccesoAplicacionAdapterVO.getPerfilAccesoAplicacion());

			// Tiene errores recuperables
			if (perfilAccesoAplicacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoAplicacionVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoAplicacionVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoAplicacionAdapter.NAME, perfilAccesoAplicacionAdapterVO);
			}

			// Tiene errores no recuperables
			if (perfilAccesoAplicacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoAplicacionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoAplicacionAdapter.NAME, perfilAccesoAplicacionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, PerfilAccesoAplicacionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoAplicacionAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESOAPLICACION, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			PerfilAccesoAplicacionAdapter perfilAccesoAplicacionAdapterVO = (PerfilAccesoAplicacionAdapter) userSession.get(PerfilAccesoAplicacionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (perfilAccesoAplicacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + PerfilAccesoAplicacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PerfilAccesoAplicacionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(perfilAccesoAplicacionAdapterVO, request);

			// Tiene errores recuperables
			if (perfilAccesoAplicacionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoAplicacionAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoAplicacionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoAplicacionAdapter.NAME, perfilAccesoAplicacionAdapterVO);
			}

			// llamada al servicio
			PerfilAccesoAplicacionVO perfilAccesoAplicacionVO = ApmServiceLocator.getAplicacionService().updatePerfilAccesoAplicacion(userSession, perfilAccesoAplicacionAdapterVO.getPerfilAccesoAplicacion());

			// Tiene errores recuperables
			if (perfilAccesoAplicacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoAplicacionAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoAplicacionVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoAplicacionAdapter.NAME, perfilAccesoAplicacionAdapterVO);
			}

			// Tiene errores no recuperables
			if (perfilAccesoAplicacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoAplicacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoAplicacionAdapter.NAME, perfilAccesoAplicacionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, PerfilAccesoAplicacionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoAplicacionAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESOAPLICACION, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			PerfilAccesoAplicacionAdapter perfilAccesoAplicacionAdapterVO = (PerfilAccesoAplicacionAdapter) userSession.get(PerfilAccesoAplicacionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (perfilAccesoAplicacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + PerfilAccesoAplicacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PerfilAccesoAplicacionAdapter.NAME); 
			}

			// llamada al servicio
			PerfilAccesoAplicacionVO perfilAccesoAplicacionVO = ApmServiceLocator.getAplicacionService().deletePerfilAccesoAplicacion
					(userSession, perfilAccesoAplicacionAdapterVO.getPerfilAccesoAplicacion());

			// Tiene errores recuperables
			if (perfilAccesoAplicacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoAplicacionAdapterVO.infoString());
				saveDemodaErrors(request, perfilAccesoAplicacionVO);				
				request.setAttribute(PerfilAccesoAplicacionAdapter.NAME, perfilAccesoAplicacionAdapterVO);
				return mapping.findForward(ApmConstants.FWD_PERFILACCESOAPLICACION_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (perfilAccesoAplicacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoAplicacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoAplicacionAdapter.NAME, perfilAccesoAplicacionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, PerfilAccesoAplicacionAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoAplicacionAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return baseVolver(mapping, form, request, response, PerfilAccesoAplicacionAdapter.NAME);
	}
} 