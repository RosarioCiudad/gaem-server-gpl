package ar.gov.rosario.gait.def.view.struts;

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
import ar.gov.rosario.gait.def.iface.model.DireccionAdapter;
import ar.gov.rosario.gait.def.iface.model.DireccionVO;
import ar.gov.rosario.gait.def.iface.service.DefServiceLocator;
import ar.gov.rosario.gait.def.iface.util.DefError;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;
import ar.gov.rosario.gait.def.view.util.DefConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;



public final class AdministrarDireccionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarDireccionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_DIRECCION, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		DireccionAdapter direccionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getDireccionAdapterForView(userSession, commonKey)";
				direccionAdapterVO = DefServiceLocator.getConfiguracionService().getDireccionAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_DIRECCION_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getDireccionAdapterForUpdate(userSession, commonKey)";
				direccionAdapterVO = DefServiceLocator.getConfiguracionService().getDireccionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_DIRECCION_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getDireccionAdapterForView(userSession, commonKey)";
				direccionAdapterVO = DefServiceLocator.getConfiguracionService().getDireccionAdapterForView(userSession, commonKey);				
				direccionAdapterVO.addMessage(BaseError.MSG_ELIMINAR, DefError.DIRECCION_LABEL);
				actionForward = mapping.findForward(DefConstants.FWD_DIRECCION_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getDireccionAdapterForCreate(userSession)";
				direccionAdapterVO = DefServiceLocator.getConfiguracionService().getDireccionAdapterForCreate(userSession);
				actionForward = mapping.findForward(DefConstants.FWD_DIRECCION_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (direccionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + direccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionAdapter.NAME, direccionAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			direccionAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + DireccionAdapter.NAME + ": "+ direccionAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(DireccionAdapter.NAME, direccionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(DireccionAdapter.NAME, direccionAdapterVO);

			saveDemodaMessages(request, direccionAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_DIRECCION, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			DireccionAdapter direccionAdapterVO = (DireccionAdapter) userSession.get(DireccionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (direccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + DireccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DireccionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(direccionAdapterVO, request);

			// Tiene errores recuperables
			if (direccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAdapterVO.infoString()); 
				saveDemodaErrors(request, direccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAdapter.NAME, direccionAdapterVO);
			}

			// llamada al servicio
			DireccionVO direccionVO = DefServiceLocator.getConfiguracionService().createDireccion(userSession, direccionAdapterVO.getDireccion());

			// Tiene errores recuperables
			if (direccionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionVO.infoString()); 
				saveDemodaErrors(request, direccionVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAdapter.NAME, direccionAdapterVO);
			}

			// Tiene errores no recuperables
			if (direccionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + direccionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionAdapter.NAME, direccionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, DireccionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_DIRECCION, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			DireccionAdapter direccionAdapterVO = (DireccionAdapter) userSession.get(DireccionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (direccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + DireccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DireccionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(direccionAdapterVO, request);

			// Tiene errores recuperables
			if (direccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAdapterVO.infoString()); 
				saveDemodaErrors(request, direccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAdapter.NAME, direccionAdapterVO);
			}

			// llamada al servicio
			DireccionVO direccionVO = DefServiceLocator.getConfiguracionService().updateDireccion(userSession, direccionAdapterVO.getDireccion());

			// Tiene errores recuperables
			if (direccionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAdapterVO.infoString()); 
				saveDemodaErrors(request, direccionVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAdapter.NAME, direccionAdapterVO);
			}

			// Tiene errores no recuperables
			if (direccionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + direccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionAdapter.NAME, direccionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, DireccionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionAdapter.NAME);
		}
	}


	public ActionForward modificarEncabezado(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_ENC_DIRECCION, BaseConstants.ACT_MODIFICAR);

	}
	
	public ActionForward agregarArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_ENC_AREA);
	}	

	public ActionForward verArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_AREA);
	}	
	public ActionForward modificarArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_AREA);
	}	
	public ActionForward eliminarArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_AREA);
	}	
	
	public ActionForward agregarDireccionAplicacionPerfil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_DIRECCIONAPLICACIONPERFIL);
	}	

	public ActionForward verDireccionAplicacionPerfil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_DIRECCIONAPLICACIONPERFIL);
	}	
	public ActionForward modificarDireccionAplicacionPerfil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_DIRECCIONAPLICACIONPERFIL);
	}	
	public ActionForward eliminarDireccionAplicacionPerfil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_DIRECCIONAPLICACIONPERFIL);
	}	
	
	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_DIRECCION, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			DireccionAdapter direccionAdapterVO = (DireccionAdapter) userSession.get(DireccionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (direccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + DireccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DireccionAdapter.NAME); 
			}

			// llamada al servicio
			DireccionVO direccionVO = DefServiceLocator.getConfiguracionService().deleteDireccion
					(userSession, direccionAdapterVO.getDireccion());

			// Tiene errores recuperables
			if (direccionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAdapterVO.infoString());
				saveDemodaErrors(request, direccionVO);				
				request.setAttribute(DireccionAdapter.NAME, direccionAdapterVO);
				return mapping.findForward(DefConstants.FWD_DIRECCION_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (direccionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + direccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionAdapter.NAME, direccionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, DireccionAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return baseVolver(mapping, form, request, response, DireccionAdapter.NAME);

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
			DireccionAdapter direccionAdapterVO = (DireccionAdapter) userSession.get(DireccionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (direccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + DireccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DireccionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(direccionAdapterVO, request);

			// Tiene errores recuperables
			if (direccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAdapterVO.infoString()); 
				saveDemodaErrors(request, direccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAdapter.NAME, direccionAdapterVO);
			}

			// llamada al servicio
			//direccionAdapterVO = DefServiceLocator.getDireccionService().getDireccionAdapterParam(userSession, direccionAdapterVO);

			// Tiene errores recuperables
			if (direccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAdapterVO.infoString()); 
				saveDemodaErrors(request, direccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAdapter.NAME, direccionAdapterVO);
			}

			// Tiene errores no recuperables
			if (direccionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + direccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionAdapter.NAME, direccionAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(DireccionAdapter.NAME, direccionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(DireccionAdapter.NAME, direccionAdapterVO);

			return mapping.findForward(DefConstants.FWD_DIRECCION_ADAPTER);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionAdapter.NAME);
		}
	}


	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, DireccionAdapter.NAME);			
	}


} 
