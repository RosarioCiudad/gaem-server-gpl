package ar.gov.rosario.gait.apm.view.struts;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import ar.gov.rosario.gait.apm.iface.model.AplicacionBinarioVersionAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionBinarioVersionVO;
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
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarAplicacionBinarioVersionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarAplicacionBinarioVersionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONBINARIOVERSION, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		AplicacionBinarioVersionAdapter aplicacionBinarioVersionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getAplicacionBinarioVersionAdapterForView(userSession, commonKey)";
				aplicacionBinarioVersionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionBinarioVersionAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONBINARIOVERSION_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplicacionBinarioVersionAdapterForUpdate(userSession, commonKey)";
				aplicacionBinarioVersionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionBinarioVersionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONBINARIOVERSION_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getAplicacionBinarioVersionAdapterForView(userSession, commonKey)";
				aplicacionBinarioVersionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionBinarioVersionAdapterForView(userSession, commonKey);				
				aplicacionBinarioVersionAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.APLICACIONBINARIOVERSION_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONBINARIOVERSION_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplicacionBinarioVersionAdapterForCreate(userSession)";
				aplicacionBinarioVersionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionBinarioVersionAdapterForCreate(userSession,commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONBINARIOVERSION_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (aplicacionBinarioVersionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplicacionBinarioVersionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			aplicacionBinarioVersionAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplicacionBinarioVersionAdapter.NAME + ": "+ aplicacionBinarioVersionAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			 
			saveDemodaMessages(request, aplicacionBinarioVersionAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionBinarioVersionAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONBINARIOVERSION, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionBinarioVersionAdapter aplicacionBinarioVersionAdapterVO = (AplicacionBinarioVersionAdapter) userSession.get(AplicacionBinarioVersionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionBinarioVersionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionBinarioVersionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionBinarioVersionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionBinarioVersionAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionBinarioVersionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionBinarioVersionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionBinarioVersionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			}
			
			Collection<FormFile> col = form.getMultipartRequestHandler().getFileElements().values();
			FormFile file = col.iterator().next();
			// ---> Valida que sea un archivo correcto
			if(file==null || file.getFileData()==null || StringUtil.isNullOrEmpty(file.getFileName()) || file.getFileSize()==0){
				aplicacionBinarioVersionAdapterVO.addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.ARCHIVO_UPLOAD_FILE_LABEL);
				//
				saveDemodaErrors(request, aplicacionBinarioVersionAdapterVO);
				// Envio el VO al request
				request.setAttribute(AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
				// Subo el apdater al userSession
				userSession.put(AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
				
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			}
			// <--- Valida que sea un archivo correcto
			aplicacionBinarioVersionAdapterVO.getAplicacionBinarioVersion().setFileData(file.getFileData());
			aplicacionBinarioVersionAdapterVO.getAplicacionBinarioVersion().setFileName(file.getFileName());
			
			// llamada al servicio
			AplicacionBinarioVersionVO aplicacionBinarioVersionVO = ApmServiceLocator.getAplicacionService().createAplicacionBinarioVersion(userSession, aplicacionBinarioVersionAdapterVO.getAplicacionBinarioVersion());
			
            // Tiene errores recuperables
			if (aplicacionBinarioVersionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionBinarioVersionVO.infoString()); 
				saveDemodaErrors(request, aplicacionBinarioVersionVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionBinarioVersionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionBinarioVersionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionBinarioVersionAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionBinarioVersionAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONBINARIOVERSION, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionBinarioVersionAdapter aplicacionBinarioVersionAdapterVO = (AplicacionBinarioVersionAdapter) userSession.get(AplicacionBinarioVersionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionBinarioVersionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionBinarioVersionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionBinarioVersionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionBinarioVersionAdapterVO, request);
			
			Collection<FormFile> col = form.getMultipartRequestHandler().getFileElements().values();
			FormFile file = col.iterator().next();
			// ---> Valida que sea un archivo correcto
			if(file==null || file.getFileData()==null || StringUtil.isNullOrEmpty(file.getFileName()) || file.getFileSize()==0){
				aplicacionBinarioVersionAdapterVO.addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.ARCHIVO_UPLOAD_FILE_LABEL);
				//
				saveDemodaErrors(request, aplicacionBinarioVersionAdapterVO);
				// Envio el VO al request
				request.setAttribute(AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
				// Subo el apdater al userSession
				userSession.put(AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
				
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			}
			// <--- Valida que sea un archivo correcto
			aplicacionBinarioVersionAdapterVO.getAplicacionBinarioVersion().setFileData(file.getFileData());
			aplicacionBinarioVersionAdapterVO.getAplicacionBinarioVersion().setFileName(file.getFileName());
			
            // Tiene errores recuperables
			if (aplicacionBinarioVersionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionBinarioVersionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionBinarioVersionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			}
			
			// llamada al servicio
			AplicacionBinarioVersionVO aplicacionBinarioVersionVO = ApmServiceLocator.getAplicacionService().updateAplicacionBinarioVersion(userSession, aplicacionBinarioVersionAdapterVO.getAplicacionBinarioVersion());
			
            // Tiene errores recuperables
			if (aplicacionBinarioVersionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionBinarioVersionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionBinarioVersionVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionBinarioVersionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionBinarioVersionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionBinarioVersionAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionBinarioVersionAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONBINARIOVERSION, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionBinarioVersionAdapter aplicacionBinarioVersionAdapterVO = (AplicacionBinarioVersionAdapter) userSession.get(AplicacionBinarioVersionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionBinarioVersionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionBinarioVersionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionBinarioVersionAdapter.NAME); 
			}

			// llamada al servicio
			AplicacionBinarioVersionVO aplicacionBinarioVersionVO = ApmServiceLocator.getAplicacionService().deleteAplicacionBinarioVersion
				(userSession, aplicacionBinarioVersionAdapterVO.getAplicacionBinarioVersion());
			
            // Tiene errores recuperables
			if (aplicacionBinarioVersionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionBinarioVersionAdapterVO.infoString());
				saveDemodaErrors(request, aplicacionBinarioVersionVO);				
				request.setAttribute(AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
				return mapping.findForward(ApmConstants.FWD_APLICACIONBINARIOVERSION_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (aplicacionBinarioVersionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionBinarioVersionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionBinarioVersionAdapter.NAME);
			

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionBinarioVersionAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AplicacionBinarioVersionAdapter.NAME);
		
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
			AplicacionBinarioVersionAdapter aplicacionBinarioVersionAdapterVO = (AplicacionBinarioVersionAdapter) userSession.get(AplicacionBinarioVersionAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionBinarioVersionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionBinarioVersionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionBinarioVersionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionBinarioVersionAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionBinarioVersionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionBinarioVersionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionBinarioVersionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			}
			
			// llamada al servicio
			//aplicacionBinarioVersionAdapterVO = ApmServiceLocator.getAplicacionBinarioVersionService().getAplicacionBinarioVersionAdapterParam(userSession, aplicacionBinarioVersionAdapterVO);
			
            // Tiene errores recuperables
			if (aplicacionBinarioVersionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionBinarioVersionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionBinarioVersionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionBinarioVersionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionBinarioVersionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			}
			
			// Envio el VO al request
			request.setAttribute(AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionBinarioVersionAdapter.NAME, aplicacionBinarioVersionAdapterVO);
			
			return mapping.findForward(ApmConstants.FWD_APLICACIONBINARIOVERSION_EDIT_ADAPTER);
		
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionBinarioVersionAdapter.NAME);
		}
	}
		
		
	
} 
