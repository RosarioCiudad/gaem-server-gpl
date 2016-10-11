package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoAdapter;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoVO;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.def.view.util.DefConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarPerfilAccesoDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger((AdministrarPerfilAccesoDAction.class));

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESO, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		PerfilAccesoAdapter perfilAccesoAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			log.debug(" ########  commonKey: " + commonKey.getId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getPerfilAccesoAdapterForView(userSession, commonKey)";
				perfilAccesoAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getPerfilAccesoAdapterForUpdate(userSession, commonKey)";
				perfilAccesoAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESO_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getPerfilAccesoAdapterForView(userSession, commonKey)";
				perfilAccesoAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoAdapterForView(userSession, commonKey);				
				perfilAccesoAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.PERFILACCESO_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESO_VIEW_ADAPTER);				
			}
//			if (act.equals(BaseConstants.ACT_AGREGAR)) {
//				stringServicio = "getPerfilAccesoAdapterForCreate(userSession)";
//				perfilAccesoAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoAdapterForCreate(userSession);
//				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESO_ENC_EDIT_ADAPTER);				
//			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (perfilAccesoAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + perfilAccesoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoAdapter.NAME, perfilAccesoAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			perfilAccesoAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + PerfilAccesoAdapter.NAME + ": "+ perfilAccesoAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(PerfilAccesoAdapter.NAME, perfilAccesoAdapterVO);
			// Subo el apdater al userSession
			userSession.put(PerfilAccesoAdapter.NAME, perfilAccesoAdapterVO);
			 
			saveDemodaMessages(request, perfilAccesoAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESO, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			PerfilAccesoAdapter perfilAccesoAdapterVO = (PerfilAccesoAdapter) userSession.get(PerfilAccesoAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (perfilAccesoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + PerfilAccesoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PerfilAccesoAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(perfilAccesoAdapterVO, request);
			
            // Tiene errores recuperables
			if (perfilAccesoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoAdapter.NAME, perfilAccesoAdapterVO);
			}
			
			// llamada al servicio
			PerfilAccesoVO perfilAccesoVO = ApmServiceLocator.getAplicacionService().createPerfilAcceso(userSession, perfilAccesoAdapterVO.getPerfilAcceso());
			
            // Tiene errores recuperables
			if (perfilAccesoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoAdapter.NAME, perfilAccesoAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (perfilAccesoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoAdapter.NAME, perfilAccesoAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, PerfilAccesoAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESO, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			PerfilAccesoAdapter perfilAccesoAdapterVO = (PerfilAccesoAdapter) userSession.get(PerfilAccesoAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (perfilAccesoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + PerfilAccesoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PerfilAccesoAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(perfilAccesoAdapterVO, request);
			
            // Tiene errores recuperables
			if (perfilAccesoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoAdapter.NAME, perfilAccesoAdapterVO);
			}
			
			// llamada al servicio
			PerfilAccesoVO perfilAccesoVO = ApmServiceLocator.getAplicacionService().updatePerfilAcceso(userSession, perfilAccesoAdapterVO.getPerfilAcceso());
			
            // Tiene errores recuperables
			if (perfilAccesoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoAdapter.NAME, perfilAccesoAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (perfilAccesoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoAdapter.NAME, perfilAccesoAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, PerfilAccesoAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESO, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			PerfilAccesoAdapter perfilAccesoAdapterVO = (PerfilAccesoAdapter) userSession.get(PerfilAccesoAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (perfilAccesoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + PerfilAccesoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PerfilAccesoAdapter.NAME); 
			}

			// llamada al servicio
			PerfilAccesoVO perfilAccesoVO = ApmServiceLocator.getAplicacionService().deletePerfilAcceso
				(userSession, perfilAccesoAdapterVO.getPerfilAcceso());
			
            // Tiene errores recuperables
			if (perfilAccesoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoAdapterVO.infoString());
				saveDemodaErrors(request, perfilAccesoVO);				
				request.setAttribute(PerfilAccesoAdapter.NAME, perfilAccesoAdapterVO);
				return mapping.findForward(ApmConstants.FWD_PERFILACCESO_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (perfilAccesoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoAdapter.NAME, perfilAccesoAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, PerfilAccesoAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoAdapter.NAME);
		}
	}
	
	public ActionForward modificarEncabezado(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_PERFILACCESO, BaseConstants.ACT_MODIFICAR);
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return baseVolver(mapping, form, request, response, PerfilAccesoAdapter.NAME);
	}
	
	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, PerfilAccesoAdapter.NAME);			
	}
	
	public ActionForward agregarAreaAplicacionPerfil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_AREAAPLICACIONPERFIL);
	}	

	public ActionForward verAreaAplicacionPerfil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_AREAAPLICACIONPERFIL);
	}	
	
	public ActionForward modificarAreaAplicacionPerfil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_AREAAPLICACIONPERFIL);
	}	
	
	public ActionForward eliminarAreaAplicacionPerfil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_AREAAPLICACIONPERFIL);
	}	
	
	public ActionForward verPerfilAccesoAplicacion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PERFILACCESOAPLICACION);
	}	
	
	public ActionForward modificarPerfilAccesoAplicacion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PERFILACCESOAPLICACION);
	}	
	
	public ActionForward eliminarPerfilAccesoAplicacion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PERFILACCESOAPLICACION);
	}	
	
	public ActionForward agregarPerfilAccesoAplicacion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_PERFILACCESOAPLICACION);
	}	
}