package ar.gov.rosario.gait.seg.view.struts;

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
import ar.gov.rosario.gait.seg.iface.model.UsuarioGaitAdapter;
import ar.gov.rosario.gait.seg.iface.model.UsuarioGaitVO;
import ar.gov.rosario.gait.seg.iface.service.SegServiceLocator;
import ar.gov.rosario.gait.seg.iface.util.SegError;
import ar.gov.rosario.gait.seg.iface.util.SegSecurityConstants;
import ar.gov.rosario.gait.seg.view.util.SegConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarUsuarioGaitDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger((AdministrarUsuarioGaitDAction.class));

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, SegSecurityConstants.ABM_USUARIOGAIT, act); 
		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		UsuarioGaitAdapter usuarioGaitAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getUsuarioGaitAdapterForView(userSession, commonKey)";
				usuarioGaitAdapterVO = SegServiceLocator.getSeguridadService().getUsuarioGaitAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(SegConstants.FWD_USUARIOGAIT_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getUsuarioGaitAdapterForUpdate(userSession, commonKey)";
				usuarioGaitAdapterVO = SegServiceLocator.getSeguridadService().getUsuarioGaitAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(SegConstants.FWD_USUARIOGAIT_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getUsuarioGaitAdapterForView(userSession, commonKey)";
				usuarioGaitAdapterVO = SegServiceLocator.getSeguridadService().getUsuarioGaitAdapterForView(userSession, commonKey);				
				usuarioGaitAdapterVO.addMessage(BaseError.MSG_ELIMINAR, SegError.USUARIOGAIT_LABEL);
				actionForward = mapping.findForward(SegConstants.FWD_USUARIOGAIT_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getUsuarioGaitAdapterForCreate(userSession)";
				usuarioGaitAdapterVO = SegServiceLocator.getSeguridadService().getUsuarioGaitAdapterForCreate(userSession);
				actionForward = mapping.findForward(SegConstants.FWD_USUARIOGAIT_EDIT_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_ACTIVAR)) {
				stringServicio = "getUsuarioGaitAdapterForView(userSession)";
				usuarioGaitAdapterVO = SegServiceLocator.getSeguridadService().getUsuarioGaitAdapterForView(userSession, commonKey);
				usuarioGaitAdapterVO.addMessage(BaseError.MSG_ACTIVAR, SegError.USUARIOGAIT_LABEL);
				actionForward = mapping.findForward(SegConstants.FWD_USUARIOGAIT_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_DESACTIVAR)) {
				stringServicio = "getUsuarioGaitAdapterForView(userSession)";
				usuarioGaitAdapterVO = SegServiceLocator.getSeguridadService().getUsuarioGaitAdapterForView(userSession, commonKey);
				usuarioGaitAdapterVO.addMessage(BaseError.MSG_DESACTIVAR, SegError.USUARIOGAIT_LABEL);
				actionForward = mapping.findForward(SegConstants.FWD_USUARIOGAIT_VIEW_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (usuarioGaitAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + usuarioGaitAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			usuarioGaitAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + UsuarioGaitAdapter.NAME + ": "+ usuarioGaitAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			// Subo el apdater al userSession
			userSession.put(UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);

			saveDemodaMessages(request, usuarioGaitAdapterVO);

			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioGaitAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, SegSecurityConstants.ABM_USUARIOGAIT, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			UsuarioGaitAdapter usuarioGaitAdapterVO = (UsuarioGaitAdapter) userSession.get(UsuarioGaitAdapter.NAME);

			// Si es nulo no se puede continuar
			if (usuarioGaitAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioGaitAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioGaitAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(usuarioGaitAdapterVO, request);

			// Tiene errores recuperables
			if (usuarioGaitAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioGaitAdapterVO.infoString()); 
				saveDemodaErrors(request, usuarioGaitAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			}

			// llamada al servicio
			UsuarioGaitVO usuarioGaitVO = SegServiceLocator.getSeguridadService().createUsuarioGait(userSession, usuarioGaitAdapterVO.getUsuarioGait());

			// Tiene errores recuperables
			if (usuarioGaitVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioGaitVO.infoString()); 
				saveDemodaErrors(request, usuarioGaitVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			}

			// Tiene errores no recuperables
			if (usuarioGaitVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioGaitVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioGaitAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioGaitAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, SegSecurityConstants.ABM_USUARIOGAIT, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			UsuarioGaitAdapter usuarioGaitAdapterVO = (UsuarioGaitAdapter) userSession.get(UsuarioGaitAdapter.NAME);

			// Si es nulo no se puede continuar
			if (usuarioGaitAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioGaitAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioGaitAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(usuarioGaitAdapterVO, request);

			// Tiene errores recuperables
			if (usuarioGaitAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioGaitAdapterVO.infoString()); 
				saveDemodaErrors(request, usuarioGaitAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			}

			// llamada al servicio
			UsuarioGaitVO usuarioGaitVO = SegServiceLocator.getSeguridadService().updateUsuarioGait(userSession, usuarioGaitAdapterVO.getUsuarioGait());

			// Tiene errores recuperables
			if (usuarioGaitVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioGaitAdapterVO.infoString()); 
				saveDemodaErrors(request, usuarioGaitVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			}

			// Tiene errores no recuperables
			if (usuarioGaitVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioGaitAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioGaitAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioGaitAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, SegSecurityConstants.ABM_USUARIOGAIT, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			UsuarioGaitAdapter usuarioGaitAdapterVO = (UsuarioGaitAdapter) userSession.get(UsuarioGaitAdapter.NAME);

			// Si es nulo no se puede continuar
			if (usuarioGaitAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioGaitAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioGaitAdapter.NAME); 
			}

			// llamada al servicio
			UsuarioGaitVO usuarioGaitVO = SegServiceLocator.getSeguridadService().deleteUsuarioGait
					(userSession, usuarioGaitAdapterVO.getUsuarioGait());

			// Tiene errores recuperables
			if (usuarioGaitVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioGaitAdapterVO.infoString());
				saveDemodaErrors(request, usuarioGaitVO);				
				request.setAttribute(UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
				return mapping.findForward(SegConstants.FWD_USUARIOGAIT_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (usuarioGaitVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioGaitAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioGaitAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioGaitAdapter.NAME);
		}
	}

	public ActionForward activar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, SegSecurityConstants.ABM_USUARIOGAIT, BaseSecurityConstants.ACTIVAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			UsuarioGaitAdapter usuarioGaitAdapterVO = (UsuarioGaitAdapter) userSession.get(UsuarioGaitAdapter.NAME);

			// Si es nulo no se puede continuar
			if (usuarioGaitAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioGaitAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioGaitAdapter.NAME); 
			}

			// llamada al servicio
			UsuarioGaitVO usuarioGaitVO = SegServiceLocator.getSeguridadService().activarUsuarioGait
					(userSession, usuarioGaitAdapterVO.getUsuarioGait());

			// Tiene errores recuperables
			if (usuarioGaitVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioGaitAdapterVO.infoString());
				saveDemodaErrors(request, usuarioGaitVO);				
				request.setAttribute(UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
				return mapping.findForward(SegConstants.FWD_USUARIOGAIT_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (usuarioGaitVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioGaitAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioGaitAdapter.NAME);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioGaitAdapter.NAME);
		}	
	}

	public ActionForward desactivar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, SegSecurityConstants.ABM_USUARIOGAIT, BaseSecurityConstants.DESACTIVAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			UsuarioGaitAdapter usuarioGaitAdapterVO = (UsuarioGaitAdapter) userSession.get(UsuarioGaitAdapter.NAME);

			// Si es nulo no se puede continuar
			if (usuarioGaitAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioGaitAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioGaitAdapter.NAME); 
			}

			// llamada al servicio
			UsuarioGaitVO usuarioGaitVO = SegServiceLocator.getSeguridadService().desactivarUsuarioGait
					(userSession, usuarioGaitAdapterVO.getUsuarioGait());

			// Tiene errores recuperables
			if (usuarioGaitVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioGaitAdapterVO.infoString());
				saveDemodaErrors(request, usuarioGaitVO);				
				request.setAttribute(UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
				return mapping.findForward(SegConstants.FWD_USUARIOGAIT_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (usuarioGaitVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioGaitAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioGaitAdapter.NAME);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioGaitAdapter.NAME);
		}
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
			UsuarioGaitAdapter usuarioGaitAdapterVO = (UsuarioGaitAdapter) userSession.get(UsuarioGaitAdapter.NAME);

			// Si es nulo no se puede continuar
			if (usuarioGaitAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioGaitAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioGaitAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(usuarioGaitAdapterVO, request);

			// Tiene errores recuperables
			if (usuarioGaitAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioGaitAdapterVO.infoString()); 
				saveDemodaErrors(request, usuarioGaitAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			}

			// llamada al servicio
			usuarioGaitAdapterVO = SegServiceLocator.getSeguridadService().getUsuarioGaitAdapterParam(userSession, usuarioGaitAdapterVO);

			// Tiene errores recuperables
			if (usuarioGaitAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioGaitAdapterVO.infoString()); 
				saveDemodaErrors(request, usuarioGaitAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			}

			// Tiene errores no recuperables
			if (usuarioGaitAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioGaitAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);
			// Subo el apdater al userSession
			userSession.put(UsuarioGaitAdapter.NAME, usuarioGaitAdapterVO);

			return  mapping.findForward(SegConstants.FWD_USUARIOGAIT_EDIT_ADAPTER);	

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioGaitAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return baseVolver(mapping, form, request, response, UsuarioGaitAdapter.NAME);

	}

}
