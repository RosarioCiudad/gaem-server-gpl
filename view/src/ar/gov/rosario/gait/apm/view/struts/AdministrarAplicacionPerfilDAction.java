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

import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
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



public final class AdministrarAplicacionPerfilDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarAplicacionPerfilDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFIL, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		AplicacionPerfilAdapter aplicacionPerfilAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getAplicacionPerfilAdapterForView(userSession, commonKey)";
				aplicacionPerfilAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFIL_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplicacionPerfilAdapterForUpdate(userSession, commonKey)";
				aplicacionPerfilAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFIL_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getAplicacionPerfilAdapterForView(userSession, commonKey)";
				aplicacionPerfilAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilAdapterForView(userSession, commonKey);				
				aplicacionPerfilAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.APLICACIONPERFIL_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFIL_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplicacionPerfilAdapterForCreate(userSession)";
				aplicacionPerfilAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFIL_ADAPTER);				
			}


			log.debug("ANTES DE ENTRAR al if CLonado");
			if (act.equals(BaseConstants.ACT_CLONAR)) {
				log.debug("Entro al if CLonado");
				stringServicio = "getAplicacionPerfilAdapterForView(userSession, commonKey)";
				aplicacionPerfilAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilAdapterForView(userSession, commonKey);				
				aplicacionPerfilAdapterVO.addMessage(ApmError.APLICACIONPERFIL_CLONAR);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFIL_VIEW_ADAPTER);				
			}

			
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (aplicacionPerfilAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			aplicacionPerfilAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplicacionPerfilAdapter.NAME + ": "+ aplicacionPerfilAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);

			saveDemodaMessages(request, aplicacionPerfilAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFIL, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplicacionPerfilAdapter aplicacionPerfilAdapterVO = (AplicacionPerfilAdapter) userSession.get(AplicacionPerfilAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionPerfilAdapterVO, request);

			// Tiene errores recuperables
			if (aplicacionPerfilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			}

			// llamada al servicio
			AplicacionPerfilVO aplicacionPerfilVO = ApmServiceLocator.getAplicacionService().createAplicacionPerfil(userSession, aplicacionPerfilAdapterVO.getAplicacionPerfil());

			// Tiene errores recuperables
			if (aplicacionPerfilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplicacionPerfilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFIL, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplicacionPerfilAdapter aplicacionPerfilAdapterVO = (AplicacionPerfilAdapter) userSession.get(AplicacionPerfilAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionPerfilAdapterVO, request);

			// Tiene errores recuperables
			if (aplicacionPerfilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			}

			// llamada al servicio
			AplicacionPerfilVO aplicacionPerfilVO = ApmServiceLocator.getAplicacionService().updateAplicacionPerfil(userSession, aplicacionPerfilAdapterVO.getAplicacionPerfil());

			// Tiene errores recuperables
			if (aplicacionPerfilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplicacionPerfilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilAdapter.NAME);
		}
	}


	public ActionForward modificarEncabezado(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_APLICACIONPERFIL, BaseConstants.ACT_MODIFICAR);
		
	}
	
	public ActionForward agregarAplicacionPerfilSeccion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_APLICACIONPERFILSECCION);
	}	

	public ActionForward verAplicacionPerfilSeccion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPERFILSECCION);
	}	
	public ActionForward modificarAplicacionPerfilSeccion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPERFILSECCION);
	}	
	public ActionForward eliminarAplicacionPerfilSeccion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPERFILSECCION);
	}	
	
	public ActionForward agregarAplicacionPerfilParametro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPERFILPARAMETRO);
	}	

	public ActionForward verAplicacionPerfilParametro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPERFILPARAMETRO);
	}	
	public ActionForward modificarAplicacionPerfilParametro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPERFILPARAMETRO);
	}	
	public ActionForward eliminarAplicacionPerfilParametro(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLICACIONPERFILPARAMETRO);
	}
	
	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFIL, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplicacionPerfilAdapter aplicacionPerfilAdapterVO = (AplicacionPerfilAdapter) userSession.get(AplicacionPerfilAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilAdapter.NAME); 
			}

			// llamada al servicio
			AplicacionPerfilVO aplicacionPerfilVO = ApmServiceLocator.getAplicacionService().deleteAplicacionPerfil
					(userSession, aplicacionPerfilAdapterVO.getAplicacionPerfil());

			// Tiene errores recuperables
			if (aplicacionPerfilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.infoString());
				saveDemodaErrors(request, aplicacionPerfilVO);				
				request.setAttribute(AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
				return mapping.findForward(ApmConstants.FWD_APLICACIONPERFIL_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (aplicacionPerfilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilAdapter.NAME);
		}
	}

// Empieza QVPOP
	public ActionForward clonar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFIL, BaseSecurityConstants.CLONAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplicacionPerfilAdapter aplicacionPerfilAdapterVO = (AplicacionPerfilAdapter) userSession.get(AplicacionPerfilAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilAdapter.NAME); 
			}

			// llamada al servicio
			AplicacionPerfilVO aplicacionPerfilVO = ApmServiceLocator.getAplicacionService().cloneAplicacionPerfil
					(userSession, aplicacionPerfilAdapterVO.getAplicacionPerfil());

			// Tiene errores recuperables
			if (aplicacionPerfilVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.infoString());
				saveDemodaErrors(request, aplicacionPerfilVO);				
				request.setAttribute(AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
				return mapping.findForward(ApmConstants.FWD_APLICACIONPERFIL_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (aplicacionPerfilVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilAdapter.NAME);
		}
	}
//	Termina QVPOP
	
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return baseVolver(mapping, form, request, response, AplicacionPerfilAdapter.NAME);

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
			AplicacionPerfilAdapter aplicacionPerfilAdapterVO = (AplicacionPerfilAdapter) userSession.get(AplicacionPerfilAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplicacionPerfilAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionPerfilAdapterVO, request);

			// Tiene errores recuperables
			if (aplicacionPerfilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			}

			// llamada al servicio
			//aplicacionPerfilAdapterVO = ApmServiceLocator.getAplicacionPerfilService().getAplicacionPerfilAdapterParam(userSession, aplicacionPerfilAdapterVO);

			// Tiene errores recuperables
			if (aplicacionPerfilAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplicacionPerfilAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionPerfilAdapter.NAME, aplicacionPerfilAdapterVO);

			return mapping.findForward(ApmConstants.FWD_APLICACIONPERFIL_ADAPTER);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilAdapter.NAME);
		}
	}


	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, AplicacionPerfilAdapter.NAME);			
	}


} 
