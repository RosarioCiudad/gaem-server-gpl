/*******************************************************************************
 * Copyright (c) 2016 Municipalidad de Rosario, Coop. de Trabajo Tecso Ltda.
 *
 * This file is part of GAEM.
 *
 * GAEM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * GAEM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAEM.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package ar.gov.rosario.gait.apm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.apm.iface.model.AplicacionAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionVO;
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



public final class AdministrarAplicacionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarAplicacionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACION, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		AplicacionAdapter aplicacionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getAplicacionAdapterForView(userSession, commonKey)";
				aplicacionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACION_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplicacionAdapterForUpdate(userSession, commonKey)";
				aplicacionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACION_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getAplicacionAdapterForView(userSession, commonKey)";
				aplicacionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionAdapterForView(userSession, commonKey);				
				aplicacionAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.APLICACION_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACION_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplicacionAdapterForCreate(userSession)";
				aplicacionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACION_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (aplicacionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplicacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionAdapter.NAME, aplicacionAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			aplicacionAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplicacionAdapter.NAME + ": "+ aplicacionAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(AplicacionAdapter.NAME, aplicacionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionAdapter.NAME, aplicacionAdapterVO);

			saveDemodaMessages(request, aplicacionAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACION, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplicacionAdapter aplicacionAdapterVO = (AplicacionAdapter) userSession.get(AplicacionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplicacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionAdapterVO, request);

			// Tiene errores recuperables
			if (aplicacionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionAdapter.NAME, aplicacionAdapterVO);
			}

			// llamada al servicio
			AplicacionVO aplicacionVO = ApmServiceLocator.getAplicacionService().createAplicacion(userSession, aplicacionAdapterVO.getAplicacion());

			// Tiene errores recuperables
			if (aplicacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionVO.infoString()); 
				saveDemodaErrors(request, aplicacionVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionAdapter.NAME, aplicacionAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplicacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionAdapter.NAME, aplicacionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACION, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplicacionAdapter aplicacionAdapterVO = (AplicacionAdapter) userSession.get(AplicacionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplicacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionAdapterVO, request);

			// Tiene errores recuperables
			if (aplicacionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionAdapter.NAME, aplicacionAdapterVO);
			}

			// llamada al servicio
			AplicacionVO aplicacionVO = ApmServiceLocator.getAplicacionService().updateAplicacion(userSession, aplicacionAdapterVO.getAplicacion());

			// Tiene errores recuperables
			if (aplicacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionAdapter.NAME, aplicacionAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplicacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionAdapter.NAME, aplicacionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionAdapter.NAME);
		}
	}


	public ActionForward modificarEncabezado(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_APLICACION, BaseConstants.ACT_MODIFICAR);

	}
	
	//Aplicacion Parametro
	
	public ActionForward agregarAplicacionParametro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPARAMETRO);
	}	

	public ActionForward verAplicacionParametro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPARAMETRO);
	}	
	public ActionForward modificarAplicacionParametro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPARAMETRO);
	}	
	public ActionForward eliminarAplicacionParametro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPARAMETRO);
	}	
	
	//Aplicacion Binario Version
	
	public ActionForward agregarAplicacionBinarioVersion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONBINARIOVERSION);
	}	

	public ActionForward verAplicacionBinarioVersion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONBINARIOVERSION);
	}	
	public ActionForward modificarAplicacionBinarioVersion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONBINARIOVERSION);
	}	
	public ActionForward eliminarAplicacionBinarioVersion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONBINARIOVERSION);
	}	

	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACION, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplicacionAdapter aplicacionAdapterVO = (AplicacionAdapter) userSession.get(AplicacionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplicacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionAdapter.NAME); 
			}

			// llamada al servicio
			AplicacionVO aplicacionVO = ApmServiceLocator.getAplicacionService().deleteAplicacion
					(userSession, aplicacionAdapterVO.getAplicacion());

			// Tiene errores recuperables
			if (aplicacionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionAdapterVO.infoString());
				saveDemodaErrors(request, aplicacionVO);				
				request.setAttribute(AplicacionAdapter.NAME, aplicacionAdapterVO);
				return mapping.findForward(ApmConstants.FWD_APLICACION_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (aplicacionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionAdapter.NAME, aplicacionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return baseVolver(mapping, form, request, response, AplicacionAdapter.NAME);

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
			AplicacionAdapter aplicacionAdapterVO = (AplicacionAdapter) userSession.get(AplicacionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplicacionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionAdapterVO, request);

			// Tiene errores recuperables
			if (aplicacionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionAdapter.NAME, aplicacionAdapterVO);
			}

			// llamada al servicio
			//aplicacionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionAdapterParam(userSession, aplicacionAdapterVO);

			// Tiene errores recuperables
			if (aplicacionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionAdapter.NAME, aplicacionAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplicacionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionAdapter.NAME, aplicacionAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(AplicacionAdapter.NAME, aplicacionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionAdapter.NAME, aplicacionAdapterVO);

			return mapping.findForward(ApmConstants.FWD_APLICACION_ADAPTER);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionAdapter.NAME);
		}
	}


	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, AplicacionAdapter.NAME);			
	}


} 
