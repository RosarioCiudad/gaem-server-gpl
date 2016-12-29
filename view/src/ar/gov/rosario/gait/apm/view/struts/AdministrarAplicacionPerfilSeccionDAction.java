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

import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilSeccionAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilSeccionVO;
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


public final class AdministrarAplicacionPerfilSeccionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarAplicacionPerfilSeccionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFILSECCION, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		AplicacionPerfilSeccionAdapter aplicacionPerfilSeccionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getAplicacionPerfilSeccionAdapterForView(userSession, commonKey)";
				aplicacionPerfilSeccionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilSeccionAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFILSECCION_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplicacionPerfilSeccionAdapterForUpdate(userSession, commonKey)";
				aplicacionPerfilSeccionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilSeccionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFILSECCION_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getAplicacionPerfilSeccionAdapterForView(userSession, commonKey)";
				aplicacionPerfilSeccionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilSeccionAdapterForView(userSession, commonKey);				
				aplicacionPerfilSeccionAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.CAMPO_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFILSECCION_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplicacionPerfilSeccionAdapterForCreate(userSession)";
				aplicacionPerfilSeccionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilSeccionAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFILSECCION_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (aplicacionPerfilSeccionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplicacionPerfilSeccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			aplicacionPerfilSeccionAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplicacionPerfilSeccionAdapter.NAME + ": "+ aplicacionPerfilSeccionAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);

			saveDemodaMessages(request, aplicacionPerfilSeccionAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilSeccionAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFILSECCION, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplicacionPerfilSeccionAdapter aplicacionPerfilSeccionAdapterVO = (AplicacionPerfilSeccionAdapter) userSession.get(AplicacionPerfilSeccionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplicacionPerfilSeccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilSeccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilSeccionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionPerfilSeccionAdapterVO, request);

			// Tiene errores recuperables
			if (aplicacionPerfilSeccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSeccionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilSeccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
			}

			// llamada al servicio
			AplicacionPerfilSeccionVO aplicacionPerfilSeccionVO = ApmServiceLocator.getAplicacionService().createAplicacionPerfilSeccion(userSession, aplicacionPerfilSeccionAdapterVO.getAplicacionPerfilSeccion());

			// Tiene errores recuperables
			if (aplicacionPerfilSeccionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSeccionVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilSeccionVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplicacionPerfilSeccionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilSeccionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilSeccionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilSeccionAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFILSECCION, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplicacionPerfilSeccionAdapter aplicacionPerfilSeccionAdapterVO = (AplicacionPerfilSeccionAdapter) userSession.get(AplicacionPerfilSeccionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplicacionPerfilSeccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilSeccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilSeccionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionPerfilSeccionAdapterVO, request);

			// Tiene errores recuperables
			if (aplicacionPerfilSeccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSeccionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilSeccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
			}

			// llamada al servicio
			AplicacionPerfilSeccionVO aplicacionPerfilSeccionVO = ApmServiceLocator.getAplicacionService().updateAplicacionPerfilSeccion(userSession, aplicacionPerfilSeccionAdapterVO.getAplicacionPerfilSeccion());

			// Tiene errores recuperables
			if (aplicacionPerfilSeccionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSeccionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilSeccionVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplicacionPerfilSeccionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilSeccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilSeccionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilSeccionAdapter.NAME);
		}
	}


	public ActionForward modificarEncabezado(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_APLICACIONPERFILSECCION, BaseConstants.ACT_MODIFICAR);

	}

	
	
	
	public ActionForward agregarAplPerfilSeccionCampo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_APLPERFILSECCIONCAMPO);
	}	

	public ActionForward verAplPerfilSeccionCampo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLPERFILSECCIONCAMPO);
	}	
	public ActionForward modificarAplPerfilSeccionCampo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLPERFILSECCIONCAMPO);
	}	
	public ActionForward eliminarAplPerfilSeccionCampo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLPERFILSECCIONCAMPO);
	}	

	
	
	
	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFILSECCION, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplicacionPerfilSeccionAdapter aplicacionPerfilSeccionAdapterVO = (AplicacionPerfilSeccionAdapter) userSession.get(AplicacionPerfilSeccionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplicacionPerfilSeccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilSeccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilSeccionAdapter.NAME); 
			}

			// llamada al servicio
			AplicacionPerfilSeccionVO aplicacionPerfilSeccionVO = ApmServiceLocator.getAplicacionService().deleteAplicacionPerfilSeccion
					(userSession, aplicacionPerfilSeccionAdapterVO.getAplicacionPerfilSeccion());

			// Tiene errores recuperables
			if (aplicacionPerfilSeccionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSeccionAdapterVO.infoString());
				saveDemodaErrors(request, aplicacionPerfilSeccionVO);				
				request.setAttribute(AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
				return mapping.findForward(ApmConstants.FWD_APLICACIONPERFILSECCION_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (aplicacionPerfilSeccionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilSeccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilSeccionAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilSeccionAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return baseVolver(mapping, form, request, response, AplicacionPerfilSeccionAdapter.NAME);

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
			AplicacionPerfilSeccionAdapter aplicacionPerfilSeccionAdapterVO = (AplicacionPerfilSeccionAdapter) userSession.get(AplicacionPerfilSeccionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplicacionPerfilSeccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilSeccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilSeccionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionPerfilSeccionAdapterVO, request);

			// Tiene errores recuperables
			if (aplicacionPerfilSeccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSeccionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilSeccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
			}

			// llamada al servicio
			//aplicacionPerfilSeccionAdapterVO = ApmServiceLocator.getAplicacionPerfilSeccionService().getAplicacionPerfilSeccionAdapterParam(userSession, aplicacionPerfilSeccionAdapterVO);

			// Tiene errores recuperables
			if (aplicacionPerfilSeccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSeccionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilSeccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplicacionPerfilSeccionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilSeccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionPerfilSeccionAdapter.NAME, aplicacionPerfilSeccionAdapterVO);

			return mapping.findForward(ApmConstants.FWD_APLICACIONPERFILSECCION_ADAPTER);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilSeccionAdapter.NAME);
		}
	}


	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, AplicacionPerfilSeccionAdapter.NAME);			
	}


} 
