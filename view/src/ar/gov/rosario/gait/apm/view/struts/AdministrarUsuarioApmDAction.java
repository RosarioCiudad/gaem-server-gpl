package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.UsuarioApmAdapter;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmVO;
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



public final class AdministrarUsuarioApmDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarUsuarioApmDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPM, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		UsuarioApmAdapter usuarioApmAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getUsuarioApmAdapterForView(userSession, commonKey)";
				usuarioApmAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPM_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getUsuarioApmAdapterForUpdate(userSession, commonKey)";
				usuarioApmAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPM_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getUsuarioApmAdapterForView(userSession, commonKey)";
				usuarioApmAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmAdapterForView(userSession, commonKey);				
				usuarioApmAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.USUARIOAPM_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPM_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getUsuarioApmAdapterForCreate(userSession)";
				usuarioApmAdapterVO = ApmServiceLocator.getAplicacionService().getUsuarioApmAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_USUARIOAPM_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (usuarioApmAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + usuarioApmAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmAdapter.NAME, usuarioApmAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			usuarioApmAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + UsuarioApmAdapter.NAME + ": "+ usuarioApmAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(UsuarioApmAdapter.NAME, usuarioApmAdapterVO);
			// Subo el apdater al userSession
			userSession.put(UsuarioApmAdapter.NAME, usuarioApmAdapterVO);

			saveDemodaMessages(request, usuarioApmAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPM, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			UsuarioApmAdapter usuarioApmAdapterVO = (UsuarioApmAdapter) userSession.get(UsuarioApmAdapter.NAME);

			// Si es nulo no se puede continuar
			if (usuarioApmAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioApmAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioApmAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(usuarioApmAdapterVO, request);

			// Tiene errores recuperables
			if (usuarioApmAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmAdapterVO.infoString()); 
				saveDemodaErrors(request, usuarioApmAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmAdapter.NAME, usuarioApmAdapterVO);
			}

			// llamada al servicio
			UsuarioApmVO usuarioApmVO = ApmServiceLocator.getAplicacionService().createUsuarioApm(userSession, usuarioApmAdapterVO.getUsuarioApm());

			// Tiene errores recuperables
			if (usuarioApmVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmVO.infoString()); 
				saveDemodaErrors(request, usuarioApmVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmAdapter.NAME, usuarioApmAdapterVO);
			}

			// Tiene errores no recuperables
			if (usuarioApmVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioApmVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmAdapter.NAME, usuarioApmAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioApmAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPM, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			UsuarioApmAdapter usuarioApmAdapterVO = (UsuarioApmAdapter) userSession.get(UsuarioApmAdapter.NAME);

			// Si es nulo no se puede continuar
			if (usuarioApmAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioApmAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioApmAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(usuarioApmAdapterVO, request);

			// Tiene errores recuperables
			if (usuarioApmAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmAdapterVO.infoString()); 
				saveDemodaErrors(request, usuarioApmAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmAdapter.NAME, usuarioApmAdapterVO);
			}

			// llamada al servicio
			UsuarioApmVO usuarioApmVO = ApmServiceLocator.getAplicacionService().updateUsuarioApm(userSession, usuarioApmAdapterVO.getUsuarioApm());

			// Tiene errores recuperables
			if (usuarioApmVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmAdapterVO.infoString()); 
				saveDemodaErrors(request, usuarioApmVO);
				return forwardErrorRecoverable(mapping, request, userSession, UsuarioApmAdapter.NAME, usuarioApmAdapterVO);
			}

			// Tiene errores no recuperables
			if (usuarioApmVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioApmAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmAdapter.NAME, usuarioApmAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioApmAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmAdapter.NAME);
		}
	}


	public ActionForward modificarEncabezado(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_USUARIOAPM, BaseConstants.ACT_MODIFICAR);

	}

	//UsuarioApm Parametro

	public ActionForward agregarPerfilAccesoUsuario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PERFILACCESOUSUARIO);
	}	

	public ActionForward verPerfilAccesoUsuario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PERFILACCESOUSUARIO);
	}	
	public ActionForward modificarPerfilAccesoUsuario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PERFILACCESOUSUARIO);
	}	
	public ActionForward eliminarPerfilAccesoUsuario(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PERFILACCESOUSUARIO);
	}	

	public ActionForward agregarUsuarioApmDM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_USUARIOAPMDM);
	}	

	public ActionForward verUsuarioApmDM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_USUARIOAPMDM);
	}

	public ActionForward modificarUsuarioApmDM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_USUARIOAPMDM);
	}	

	public ActionForward eliminarUsuarioApmDM(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_USUARIOAPMDM);
	}	


	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_USUARIOAPM, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			UsuarioApmAdapter usuarioApmAdapterVO = (UsuarioApmAdapter) userSession.get(UsuarioApmAdapter.NAME);

			// Si es nulo no se puede continuar
			if (usuarioApmAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + UsuarioApmAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, UsuarioApmAdapter.NAME); 
			}

			// llamada al servicio
			UsuarioApmVO usuarioApmVO = ApmServiceLocator.getAplicacionService().deleteUsuarioApm
					(userSession, usuarioApmAdapterVO.getUsuarioApm());

			// Tiene errores recuperables
			if (usuarioApmVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + usuarioApmAdapterVO.infoString());
				saveDemodaErrors(request, usuarioApmVO);				
				request.setAttribute(UsuarioApmAdapter.NAME, usuarioApmAdapterVO);
				return mapping.findForward(ApmConstants.FWD_USUARIOAPM_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (usuarioApmVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + usuarioApmAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, UsuarioApmAdapter.NAME, usuarioApmAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, UsuarioApmAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, UsuarioApmAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return baseVolver(mapping, form, request, response, UsuarioApmAdapter.NAME);
	}

	public ActionForward agregarUsuarioApmImpresora(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_USUARIOAPMIMPRESORA);
	}	

	public ActionForward verUsuarioApmImpresora(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_USUARIOAPMIMPRESORA);
	}

	public ActionForward modificarUsuarioApmImpresora(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_USUARIOAPMIMPRESORA);
	}	

	public ActionForward eliminarUsuarioApmImpresora(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_USUARIOAPMIMPRESORA);
	}	

	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {		
		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, UsuarioApmAdapter.NAME);			
	}
} 