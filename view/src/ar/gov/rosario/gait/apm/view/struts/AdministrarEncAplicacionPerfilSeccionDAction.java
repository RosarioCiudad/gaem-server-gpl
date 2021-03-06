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
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarEncAplicacionPerfilSeccionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarEncAplicacionPerfilSeccionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);		
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFILSECCION_ENC, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		AplicacionPerfilSeccionAdapter aplicacionPerfilSeccionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (navModel.getAct().equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplicacionPerfilSeccionAdapterForUpdate(userSession, commonKey)";
				aplicacionPerfilSeccionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilSeccionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFILSECCION_ENC_EDIT_ADAPTER);
			}
			if (navModel.getAct().equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplicacionPerfilSeccionAdapterForCreate(userSession)";
				aplicacionPerfilSeccionAdapterVO = ApmServiceLocator.getAplicacionService().getAplicacionPerfilSeccionAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLICACIONPERFILSECCION_ENC_EDIT_ADAPTER);
				//Copiar los filtros de busqueda al adapter 
				if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) DemodaUtil.populateVO(aplicacionPerfilSeccionAdapterVO, request);				
			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (aplicacionPerfilSeccionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplicacionPerfilSeccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilSeccionAdapter.ENC_NAME, aplicacionPerfilSeccionAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			aplicacionPerfilSeccionAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplicacionPerfilSeccionAdapter.ENC_NAME + ": "+ aplicacionPerfilSeccionAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(AplicacionPerfilSeccionAdapter.ENC_NAME, aplicacionPerfilSeccionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplicacionPerfilSeccionAdapter.ENC_NAME, aplicacionPerfilSeccionAdapterVO);

			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilSeccionAdapter.ENC_NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, 
			ApmSecurityConstants.ABM_APLICACIONPERFILSECCION_ENC, BaseSecurityConstants.AGREGAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionPerfilSeccionAdapter aplicacionPerfilSeccionAdapterVO = (AplicacionPerfilSeccionAdapter) userSession.get(AplicacionPerfilSeccionAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionPerfilSeccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilSeccionAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilSeccionAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionPerfilSeccionAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionPerfilSeccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSeccionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilSeccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilSeccionAdapter.ENC_NAME, aplicacionPerfilSeccionAdapterVO);
			}
			
			// llamada al servicio
			AplicacionPerfilSeccionVO aplicacionPerfilSeccionVO = ApmServiceLocator.getAplicacionService().createAplicacionPerfilSeccion(userSession, aplicacionPerfilSeccionAdapterVO.getAplicacionPerfilSeccion());
			
            // Tiene errores recuperables
			if (aplicacionPerfilSeccionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSeccionVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilSeccionVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilSeccionAdapter.ENC_NAME, aplicacionPerfilSeccionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionPerfilSeccionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilSeccionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilSeccionAdapter.ENC_NAME, aplicacionPerfilSeccionAdapterVO);
			}

			// Si tiene permiso lo dirijo al adapter de modificacion, 
			// sino vuelve al searchPage
			if (hasAccess(userSession, ApmSecurityConstants.ABM_APLICACIONPERFILSECCION, 
				BaseSecurityConstants.MODIFICAR)) {
				
				// seteo el id para que lo use el siguiente action 
				userSession.getNavModel().setSelectedId(aplicacionPerfilSeccionVO.getId().toString());

				// lo dirijo al adapter de modificacion
				return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilSeccionAdapter.ENC_NAME, 
					ApmConstants.PATH_ADMINISTRAR_APLICACIONPERFILSECCION, BaseConstants.METHOD_INICIALIZAR, 
					BaseConstants.ACT_MODIFICAR);
			} else {
				
				// lo dirijo al searchPage				
				return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilSeccionAdapter.ENC_NAME);
				
			}
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilSeccionAdapter.ENC_NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLICACIONPERFILSECCION_ENC, BaseSecurityConstants.MODIFICAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AplicacionPerfilSeccionAdapter aplicacionPerfilSeccionAdapterVO = (AplicacionPerfilSeccionAdapter) userSession.get(AplicacionPerfilSeccionAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (aplicacionPerfilSeccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplicacionPerfilSeccionAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplicacionPerfilSeccionAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplicacionPerfilSeccionAdapterVO, request);
			
            // Tiene errores recuperables
			if (aplicacionPerfilSeccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSeccionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilSeccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilSeccionAdapter.ENC_NAME, aplicacionPerfilSeccionAdapterVO);
			}
			
			// llamada al servicio
			AplicacionPerfilSeccionVO aplicacionPerfilSeccionVO = ApmServiceLocator.getAplicacionService().updateAplicacionPerfilSeccion(userSession, aplicacionPerfilSeccionAdapterVO.getAplicacionPerfilSeccion());
			
            // Tiene errores recuperables
			if (aplicacionPerfilSeccionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplicacionPerfilSeccionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplicacionPerfilSeccionVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplicacionPerfilSeccionAdapter.ENC_NAME, aplicacionPerfilSeccionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (aplicacionPerfilSeccionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplicacionPerfilSeccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplicacionPerfilSeccionAdapter.ENC_NAME, aplicacionPerfilSeccionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplicacionPerfilSeccionAdapter.ENC_NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplicacionPerfilSeccionAdapter.ENC_NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AplicacionPerfilSeccionAdapter.ENC_NAME);
	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, AplicacionPerfilSeccionAdapter.ENC_NAME);		
	}
	
}
	
