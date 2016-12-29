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

import ar.gov.rosario.gait.apm.iface.model.CampoValorAdapter;
import ar.gov.rosario.gait.apm.iface.model.CampoValorVO;
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

public final class AdministrarEncCampoValorDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarEncCampoValorDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);		
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPOVALOR_ENC, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		CampoValorAdapter campoValorAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (navModel.getAct().equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getCampoValorAdapterForUpdate(userSession, commonKey)";
				campoValorAdapterVO = ApmServiceLocator.getAplicacionService().getCampoValorAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPOVALOR_ENC_EDIT_ADAPTER);
			}
			if (navModel.getAct().equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getCampoValorAdapterForCreate(userSession)";
				campoValorAdapterVO = ApmServiceLocator.getAplicacionService().getCampoValorAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPOVALOR_ENC_EDIT_ADAPTER);
				//Copiar los filtros de busqueda al adapter 
				if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) DemodaUtil.populateVO(campoValorAdapterVO, request);				
			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (campoValorAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + campoValorAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoValorAdapter.ENC_NAME, campoValorAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			campoValorAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + CampoValorAdapter.ENC_NAME + ": "+ campoValorAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(CampoValorAdapter.ENC_NAME, campoValorAdapterVO);
			// Subo el apdater al userSession
			userSession.put(CampoValorAdapter.ENC_NAME, campoValorAdapterVO);

			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoValorAdapter.ENC_NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, 
			ApmSecurityConstants.ABM_CAMPOVALOR_ENC, BaseSecurityConstants.AGREGAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			CampoValorAdapter campoValorAdapterVO = (CampoValorAdapter) userSession.get(CampoValorAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (campoValorAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoValorAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoValorAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(campoValorAdapterVO, request);
			
            // Tiene errores recuperables
			if (campoValorAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, campoValorAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorAdapter.ENC_NAME, campoValorAdapterVO);
			}
			
			// llamada al servicio
			CampoValorVO campoValorVO = ApmServiceLocator.getAplicacionService().createCampoValor(userSession, campoValorAdapterVO.getCampoValor());
			
            // Tiene errores recuperables
			if (campoValorVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorVO.infoString()); 
				saveDemodaErrors(request, campoValorVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorAdapter.ENC_NAME, campoValorAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (campoValorVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoValorVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoValorAdapter.ENC_NAME, campoValorAdapterVO);
			}

			// Si tiene permiso lo dirijo al adapter de modificacion, 
			// sino vuelve al searchPage
			if (hasAccess(userSession, ApmSecurityConstants.ABM_CAMPOVALOR, 
				BaseSecurityConstants.MODIFICAR)) {
				
				// seteo el id para que lo use el siguiente action 
				userSession.getNavModel().setSelectedId(campoValorVO.getId().toString());

				// lo dirijo al adapter de modificacion
				return forwardConfirmarOk(mapping, request, funcName, CampoValorAdapter.ENC_NAME, 
					ApmConstants.PATH_ADMINISTRAR_CAMPOVALOR, BaseConstants.METHOD_INICIALIZAR, 
					BaseConstants.ACT_MODIFICAR);
			} else {
				
				// lo dirijo al searchPage				
				return forwardConfirmarOk(mapping, request, funcName, CampoValorAdapter.ENC_NAME);
				
			}
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoValorAdapter.ENC_NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPOVALOR_ENC, BaseSecurityConstants.MODIFICAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			CampoValorAdapter campoValorAdapterVO = (CampoValorAdapter) userSession.get(CampoValorAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (campoValorAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoValorAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoValorAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(campoValorAdapterVO, request);
			
            // Tiene errores recuperables
			if (campoValorAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, campoValorAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorAdapter.ENC_NAME, campoValorAdapterVO);
			}
			
			// llamada al servicio
			CampoValorVO campoValorVO = ApmServiceLocator.getAplicacionService().updateCampoValor(userSession, campoValorAdapterVO.getCampoValor());
			
            // Tiene errores recuperables
			if (campoValorVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, campoValorVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorAdapter.ENC_NAME, campoValorAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (campoValorVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoValorAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoValorAdapter.ENC_NAME, campoValorAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, CampoValorAdapter.ENC_NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoValorAdapter.ENC_NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, CampoValorAdapter.ENC_NAME);
	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, CampoValorAdapter.ENC_NAME);		
	}
	
}
	
