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

import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.def.iface.model.AreaAdapter;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.def.iface.service.DefServiceLocator;
import ar.gov.rosario.gait.def.iface.util.DefError;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;
import ar.gov.rosario.gait.def.view.util.DefConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;



public final class AdministrarAreaDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarAreaDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_AREA, act); 

		if (userSession == null) return forwardErrorSession(request);
		NavModel navModel = userSession.getNavModel();
		AreaAdapter areaAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getAreaAdapterForView(userSession, commonKey)";
				areaAdapterVO = DefServiceLocator.getConfiguracionService().getAreaAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_AREA_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAreaAdapterForUpdate(userSession, commonKey)";
				areaAdapterVO = DefServiceLocator.getConfiguracionService().getAreaAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_AREA_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getAreaAdapterForView(userSession, commonKey)";
				areaAdapterVO = DefServiceLocator.getConfiguracionService().getAreaAdapterForView(userSession, commonKey);				
				areaAdapterVO.addMessage(BaseError.MSG_ELIMINAR, DefError.AREA_LABEL);
				actionForward = mapping.findForward(DefConstants.FWD_AREA_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAreaAdapterForCreate(userSession)";
				areaAdapterVO = DefServiceLocator.getConfiguracionService().getAreaAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(DefConstants.FWD_AREA_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (areaAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + areaAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AreaAdapter.NAME, areaAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			areaAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + AreaAdapter.NAME + ": "+ areaAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(AreaAdapter.NAME, areaAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AreaAdapter.NAME, areaAdapterVO);

			saveDemodaMessages(request, areaAdapterVO);

			return actionForward;
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AreaAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_AREA, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AreaAdapter areaAdapterVO = (AreaAdapter) userSession.get(AreaAdapter.NAME);
			// Si es nulo no se puede continuar
			if (areaAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AreaAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AreaAdapter.NAME); 
			}
			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(areaAdapterVO, request);
			// Tiene errores recuperables
			if (areaAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAdapterVO.infoString()); 
				saveDemodaErrors(request, areaAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAdapter.NAME, areaAdapterVO);
			}
			// llamada al servicio
			AreaVO areaVO = DefServiceLocator.getConfiguracionService().createArea(userSession, areaAdapterVO.getArea());
			// Tiene errores recuperables
			if (areaVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaVO.infoString()); 
				saveDemodaErrors(request, areaVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAdapter.NAME, areaAdapterVO);
			}
			// Tiene errores no recuperables
			if (areaVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + areaVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AreaAdapter.NAME, areaAdapterVO);
			}
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AreaAdapter.NAME);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AreaAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_AREA, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AreaAdapter areaAdapterVO = (AreaAdapter) userSession.get(AreaAdapter.NAME);
			// Si es nulo no se puede continuar
			if (areaAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AreaAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AreaAdapter.NAME); 
			}
			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(areaAdapterVO, request);
			// Tiene errores recuperables
			if (areaAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAdapterVO.infoString()); 
				saveDemodaErrors(request, areaAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAdapter.NAME, areaAdapterVO);
			}
			// llamada al servicio
			AreaVO areaVO = DefServiceLocator.getConfiguracionService().updateArea(userSession, areaAdapterVO.getArea());
			// Tiene errores recuperables
			if (areaVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAdapterVO.infoString()); 
				saveDemodaErrors(request, areaVO);
				return forwardErrorRecoverable(mapping, request, userSession, AreaAdapter.NAME, areaAdapterVO);
			}
			// Tiene errores no recuperables
			if (areaVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + areaAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AreaAdapter.NAME, areaAdapterVO);
			}
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AreaAdapter.NAME);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AreaAdapter.NAME);
		}
	}

	public ActionForward modificarEncabezado(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, DefConstants.ACTION_ADMINISTRAR_ENC_AREA, BaseConstants.ACT_MODIFICAR);
	}
	
	public ActionForward agregarPerfilAcceso(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_PERFILACCESO);
	}	

	public ActionForward verPerfilAcceso(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_PERFILACCESO);
	}	
	
	public ActionForward modificarPerfilAcceso(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_PERFILACCESO);
	}
	
	public ActionForward eliminarPerfilAcceso(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_PERFILACCESO);
	}	
	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_AREA, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AreaAdapter areaAdapterVO = (AreaAdapter) userSession.get(AreaAdapter.NAME);

			// Si es nulo no se puede continuar
			if (areaAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AreaAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AreaAdapter.NAME); 
			}

			// llamada al servicio
			AreaVO areaVO = DefServiceLocator.getConfiguracionService().deleteArea(userSession, areaAdapterVO.getArea());

			// Tiene errores recuperables
			if (areaVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + areaAdapterVO.infoString());
				saveDemodaErrors(request, areaVO);				
				request.setAttribute(AreaAdapter.NAME, areaAdapterVO);
				return mapping.findForward(DefConstants.FWD_AREA_VIEW_ADAPTER);
			}
			// Tiene errores no recuperables
			if (areaVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + areaAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AreaAdapter.NAME, areaAdapterVO);
			}
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AreaAdapter.NAME);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AreaAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return baseVolver(mapping, form, request, response, AreaAdapter.NAME);
	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {		
		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, AreaAdapter.NAME);			
	}
	
	public ActionForward agregarTelefonoPanico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_TELEFONOPANICO);
	}	

	public ActionForward verTelefonoPanico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_TELEFONOPANICO);
	}	
	
	public ActionForward modificarTelefonoPanico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_TELEFONOPANICO);
	}
	
	public ActionForward eliminarTelefonoPanico(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_TELEFONOPANICO);
	}	
} 