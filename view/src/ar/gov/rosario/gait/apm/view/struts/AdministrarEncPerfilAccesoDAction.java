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

import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoAdapter;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoVO;
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

public final class AdministrarEncPerfilAccesoDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarEncPerfilAccesoDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);		
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESO_ENC, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		PerfilAccesoAdapter perfilAccesoAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (navModel.getAct().equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getPerfilAccesoAdapterForUpdate(userSession, commonKey)";
				perfilAccesoAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESO_ENC_EDIT_ADAPTER);
			}
			if (navModel.getAct().equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getPerfilAccesoAdapterForCreate(userSession)";
				perfilAccesoAdapterVO = ApmServiceLocator.getAplicacionService().getPerfilAccesoAdapterForCreate(userSession);
				actionForward = mapping.findForward(ApmConstants.FWD_PERFILACCESO_ENC_EDIT_ADAPTER);
				//Copiar los filtros de busqueda al adapter 
				if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) DemodaUtil.populateVO(perfilAccesoAdapterVO, request);				
			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (perfilAccesoAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + perfilAccesoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoAdapter.ENC_NAME, perfilAccesoAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			perfilAccesoAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + PerfilAccesoAdapter.ENC_NAME + ": "+ perfilAccesoAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(PerfilAccesoAdapter.ENC_NAME, perfilAccesoAdapterVO);
			// Subo el apdater al userSession
			userSession.put(PerfilAccesoAdapter.ENC_NAME, perfilAccesoAdapterVO);

			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoAdapter.ENC_NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, 
			ApmSecurityConstants.ABM_PERFILACCESO_ENC, BaseSecurityConstants.AGREGAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			PerfilAccesoAdapter perfilAccesoAdapterVO = (PerfilAccesoAdapter) userSession.get(PerfilAccesoAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (perfilAccesoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + PerfilAccesoAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PerfilAccesoAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(perfilAccesoAdapterVO, request);
			
            // Tiene errores recuperables
			if (perfilAccesoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoAdapter.ENC_NAME, perfilAccesoAdapterVO);
			}
			
			// llamada al servicio
			PerfilAccesoVO perfilAccesoVO = ApmServiceLocator.getAplicacionService().createPerfilAcceso(userSession, perfilAccesoAdapterVO.getPerfilAcceso());
			
            // Tiene errores recuperables
			if (perfilAccesoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoAdapter.ENC_NAME, perfilAccesoAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (perfilAccesoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoAdapter.ENC_NAME, perfilAccesoAdapterVO);
			}

			return forwardConfirmarOk(mapping, request, funcName, PerfilAccesoAdapter.ENC_NAME);
				
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoAdapter.ENC_NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PERFILACCESO_ENC, BaseSecurityConstants.MODIFICAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			PerfilAccesoAdapter perfilAccesoAdapterVO = (PerfilAccesoAdapter) userSession.get(PerfilAccesoAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (perfilAccesoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + PerfilAccesoAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PerfilAccesoAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(perfilAccesoAdapterVO, request);
			
            // Tiene errores recuperables
			if (perfilAccesoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoAdapter.ENC_NAME, perfilAccesoAdapterVO);
			}
			
			// llamada al servicio
			PerfilAccesoVO perfilAccesoVO = ApmServiceLocator.getAplicacionService().updatePerfilAcceso(userSession, perfilAccesoAdapterVO.getPerfilAcceso());
			
            // Tiene errores recuperables
			if (perfilAccesoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + perfilAccesoAdapterVO.infoString()); 
				saveDemodaErrors(request, perfilAccesoVO);
				return forwardErrorRecoverable(mapping, request, userSession, PerfilAccesoAdapter.ENC_NAME, perfilAccesoAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (perfilAccesoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + perfilAccesoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PerfilAccesoAdapter.ENC_NAME, perfilAccesoAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, PerfilAccesoAdapter.ENC_NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PerfilAccesoAdapter.ENC_NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, PerfilAccesoAdapter.ENC_NAME);
	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, PerfilAccesoAdapter.ENC_NAME);		
	}
	
}
	
