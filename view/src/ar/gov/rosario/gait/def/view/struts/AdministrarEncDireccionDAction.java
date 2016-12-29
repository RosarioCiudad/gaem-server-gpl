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
import ar.gov.rosario.gait.def.iface.model.DireccionAdapter;
import ar.gov.rosario.gait.def.iface.model.DireccionVO;
import ar.gov.rosario.gait.def.iface.service.DefServiceLocator;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;
import ar.gov.rosario.gait.def.view.util.DefConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarEncDireccionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarEncDireccionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);		
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_DIRECCION_ENC, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		DireccionAdapter direccionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (navModel.getAct().equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getDireccionAdapterForUpdate(userSession, commonKey)";
				direccionAdapterVO = DefServiceLocator.getConfiguracionService().getDireccionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_DIRECCION_ENC_EDIT_ADAPTER);
			}
			if (navModel.getAct().equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getDireccionAdapterForCreate(userSession)";
				direccionAdapterVO = DefServiceLocator.getConfiguracionService().getDireccionAdapterForCreate(userSession);
				actionForward = mapping.findForward(DefConstants.FWD_DIRECCION_ENC_EDIT_ADAPTER);
				//Copiar los filtros de busqueda al adapter 
				if (((String)userSession.get("reqAttIsSubmittedForm")).equals("true")) DemodaUtil.populateVO(direccionAdapterVO, request);				
			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (direccionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + direccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionAdapter.ENC_NAME, direccionAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			direccionAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + DireccionAdapter.ENC_NAME + ": "+ direccionAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(DireccionAdapter.ENC_NAME, direccionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(DireccionAdapter.ENC_NAME, direccionAdapterVO);

			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionAdapter.ENC_NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, 
			DefSecurityConstants.ABM_DIRECCION_ENC, BaseSecurityConstants.AGREGAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			DireccionAdapter direccionAdapterVO = (DireccionAdapter) userSession.get(DireccionAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (direccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + DireccionAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DireccionAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(direccionAdapterVO, request);
			
            // Tiene errores recuperables
			if (direccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAdapterVO.infoString()); 
				saveDemodaErrors(request, direccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAdapter.ENC_NAME, direccionAdapterVO);
			}
			
			// llamada al servicio
			DireccionVO direccionVO = DefServiceLocator.getConfiguracionService().createDireccion(userSession, direccionAdapterVO.getDireccion());
			
            // Tiene errores recuperables
			if (direccionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionVO.infoString()); 
				saveDemodaErrors(request, direccionVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAdapter.ENC_NAME, direccionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (direccionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + direccionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionAdapter.ENC_NAME, direccionAdapterVO);
			}

			// Si tiene permiso lo dirijo al adapter de modificacion, 
			// sino vuelve al searchPage
			if (hasAccess(userSession, DefSecurityConstants.ABM_DIRECCION, 
				BaseSecurityConstants.MODIFICAR)) {
				
				// seteo el id para que lo use el siguiente action 
				userSession.getNavModel().setSelectedId(direccionVO.getId().toString());

				// lo dirijo al adapter de modificacion
				return forwardConfirmarOk(mapping, request, funcName, DireccionAdapter.ENC_NAME, 
					DefConstants.PATH_ADMINISTRAR_DIRECCION, BaseConstants.METHOD_INICIALIZAR, 
					BaseConstants.ACT_MODIFICAR);
			} else {
				
				// lo dirijo al searchPage				
				return forwardConfirmarOk(mapping, request, funcName, DireccionAdapter.ENC_NAME);
				
			}
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionAdapter.ENC_NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_DIRECCION_ENC, BaseSecurityConstants.MODIFICAR);		
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			DireccionAdapter direccionAdapterVO = (DireccionAdapter) userSession.get(DireccionAdapter.ENC_NAME);
			
			// Si es nulo no se puede continuar
			if (direccionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + DireccionAdapter.ENC_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, DireccionAdapter.ENC_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(direccionAdapterVO, request);
			
            // Tiene errores recuperables
			if (direccionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAdapterVO.infoString()); 
				saveDemodaErrors(request, direccionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAdapter.ENC_NAME, direccionAdapterVO);
			}
			
			// llamada al servicio
			DireccionVO direccionVO = DefServiceLocator.getConfiguracionService().updateDireccion(userSession, direccionAdapterVO.getDireccion());
			
            // Tiene errores recuperables
			if (direccionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + direccionAdapterVO.infoString()); 
				saveDemodaErrors(request, direccionVO);
				return forwardErrorRecoverable(mapping, request, userSession, DireccionAdapter.ENC_NAME, direccionAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (direccionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + direccionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, DireccionAdapter.ENC_NAME, direccionAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, DireccionAdapter.ENC_NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, DireccionAdapter.ENC_NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, DireccionAdapter.ENC_NAME);
	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, DireccionAdapter.ENC_NAME);		
	}
	
}
	
