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
package ar.gov.rosario.gait.def.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.def.iface.model.AreaAdapter;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.def.iface.service.DefServiceLocator;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;
import ar.gov.rosario.gait.def.view.util.DefConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarEncAreaDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarEncAreaDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);		
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_AREA_ENC, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		AreaAdapter areaAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (navModel.getAct().equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAreaAdapterForUpdate(userSession, commonKey)";
				areaAdapterVO = DefServiceLocator.getConfiguracionService().getAreaAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_AREA_ENC_EDIT_ADAPTER);
			}
			if (navModel.getAct().equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAreaAdapterForCreate(userSession)";
				areaAdapterVO = DefServiceLocator.getConfiguracionService().getAreaAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_AREA_ENC_EDIT_ADAPTER);
				//Copiar los filtros de busqueda al adapter 
				if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) DemodaUtil.populateVO(areaAdapterVO, request);				
			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (areaAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + areaAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AreaAdapter.ENC_NAME, areaAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			areaAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + AreaAdapter.ENC_NAME + ": "+ areaAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(AreaAdapter.ENC_NAME, areaAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AreaAdapter.ENC_NAME, areaAdapterVO);

			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AreaAdapter.ENC_NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, 
			DefSecurityConstants.ABM_AREA_ENC, BaseSecurityConstants.AGREGAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AreaAdapter areaAdapterVO = (AreaAdapter) userSession.get(AreaAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (areaAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AreaAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AreaAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(areaAdapterVO, request);
			
            // Tiene errores recuperables
			if (areaAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAdapterVO.infoString()); 
				saveDemodaErrors(request, areaAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAdapter.ENC_NAME, areaAdapterVO);
			}
			
			// llamada al servicio
			AreaVO areaVO = DefServiceLocator.getConfiguracionService().createArea(userSession, areaAdapterVO.getArea());
			
            // Tiene errores recuperables
			if (areaVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaVO.infoString()); 
				saveDemodaErrors(request, areaVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAdapter.ENC_NAME, areaAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (areaVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + areaVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AreaAdapter.ENC_NAME, areaAdapterVO);
			}

			// Si tiene permiso lo dirijo al adapter de modificacion, 
			// sino vuelve al searchPage
			if (hasAccess(userSession, DefSecurityConstants.ABM_AREA, 
				BaseSecurityConstants.MODIFICAR)) {
				
				// seteo el id para que lo use el siguiente action 
				userSession.getNavModel().setSelectedId(areaVO.getId().toString());

				// lo dirijo al adapter de modificacion
				return forwardConfirmarOk(mapping, request, funcName, AreaAdapter.ENC_NAME, 
					DefConstants.PATH_ADMINISTRAR_AREA, BaseConstants.METHOD_INICIALIZAR, 
					BaseConstants.ACT_MODIFICAR);
			} else {
				
				// lo dirijo al searchPage				
				return forwardConfirmarOk(mapping, request, funcName, AreaAdapter.ENC_NAME);
				
			}
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AreaAdapter.ENC_NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_AREA_ENC, BaseSecurityConstants.MODIFICAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			AreaAdapter areaAdapterVO = (AreaAdapter) userSession.get(AreaAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (areaAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AreaAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AreaAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(areaAdapterVO, request);
			
            // Tiene errores recuperables
			if (areaAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAdapterVO.infoString()); 
				saveDemodaErrors(request, areaAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAdapter.ENC_NAME, areaAdapterVO);
			}
			
			// llamada al servicio
			AreaVO areaVO = DefServiceLocator.getConfiguracionService().updateArea(userSession, areaAdapterVO.getArea());
			
            // Tiene errores recuperables
			if (areaVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAdapterVO.infoString()); 
				saveDemodaErrors(request, areaVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAdapter.ENC_NAME, areaAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (areaVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + areaAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AreaAdapter.ENC_NAME, areaAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AreaAdapter.ENC_NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AreaAdapter.ENC_NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, AreaAdapter.ENC_NAME);
	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, AreaAdapter.ENC_NAME);		
	}
	
}
	
