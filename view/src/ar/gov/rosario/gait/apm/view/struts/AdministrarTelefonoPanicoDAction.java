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

import ar.gov.rosario.gait.apm.iface.model.TelefonoPanicoAdapter;
import ar.gov.rosario.gait.apm.iface.model.TelefonoPanicoVO;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.def.iface.service.DefServiceLocator;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarTelefonoPanicoDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarTelefonoPanicoDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_TELEFONOPANICO, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		TelefonoPanicoAdapter telefonoPanicoAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getTelefonoPanicoAdapterForView(userSession, commonKey)";
				telefonoPanicoAdapterVO = DefServiceLocator.getConfiguracionService().getTelefonoPanicoAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_TELEFONOPANICO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getTelefonoPanicoAdapterForUpdate(userSession, commonKey)";
				telefonoPanicoAdapterVO = DefServiceLocator.getConfiguracionService().getTelefonoPanicoAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_TELEFONOPANICO_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getTelefonoPanicoAdapterForView(userSession, commonKey)";
				telefonoPanicoAdapterVO = DefServiceLocator.getConfiguracionService().getTelefonoPanicoAdapterForView(userSession, commonKey);				
				telefonoPanicoAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.TELEFONOPANICO_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_TELEFONOPANICO_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getTelefonoPanicoAdapterForCreate(userSession)";
				telefonoPanicoAdapterVO = DefServiceLocator.getConfiguracionService().getTelefonoPanicoAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_TELEFONOPANICO_EDIT_ADAPTER);				
			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Tiene errores no recuperables
			if (telefonoPanicoAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + telefonoPanicoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TelefonoPanicoAdapter.NAME, telefonoPanicoAdapterVO);
			}
			// Seteo los valores de navegacion en el adapter
			telefonoPanicoAdapterVO.setValuesFromNavModel(navModel);
			if (log.isDebugEnabled()) log.debug(funcName + ": " + TelefonoPanicoAdapter.NAME + ": "+ telefonoPanicoAdapterVO.infoString());
			// Envio el VO al request
			request.setAttribute(TelefonoPanicoAdapter.NAME, telefonoPanicoAdapterVO);
			// Subo el apdater al userSession
			userSession.put(TelefonoPanicoAdapter.NAME, telefonoPanicoAdapterVO);
			saveDemodaMessages(request, telefonoPanicoAdapterVO);
			return actionForward;
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TelefonoPanicoAdapter.NAME);
		}
	}
	
	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_TELEFONOPANICO, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);
		try {
			// Bajo el adapter del userSession
			TelefonoPanicoAdapter telefonoPanicoAdapterVO = (TelefonoPanicoAdapter) userSession.get(TelefonoPanicoAdapter.NAME);
			// Si es nulo no se puede continuar
			if (telefonoPanicoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + TelefonoPanicoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TelefonoPanicoAdapter.NAME); 
			}
			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(telefonoPanicoAdapterVO, request);
            // Tiene errores recuperables
			if (telefonoPanicoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + telefonoPanicoAdapterVO.infoString()); 
				saveDemodaErrors(request, telefonoPanicoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, TelefonoPanicoAdapter.NAME, telefonoPanicoAdapterVO);
			}
			// llamada al servicio
			TelefonoPanicoVO telefonoPanicoVO =  DefServiceLocator.getConfiguracionService().createTelefonoPanico(userSession, telefonoPanicoAdapterVO.getTelefonoPanico());
            // Tiene errores recuperables
			if (telefonoPanicoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + telefonoPanicoVO.infoString()); 
				saveDemodaErrors(request, telefonoPanicoVO);
				return forwardErrorRecoverable(mapping, request, userSession, TelefonoPanicoAdapter.NAME, telefonoPanicoAdapterVO);
			}
			// Tiene errores no recuperables
			if (telefonoPanicoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + telefonoPanicoVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TelefonoPanicoAdapter.NAME, telefonoPanicoAdapterVO);
			}
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, TelefonoPanicoAdapter.NAME);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TelefonoPanicoAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_TELEFONOPANICO, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		try {
			// Bajo el adapter del userSession
			TelefonoPanicoAdapter telefonoPanicoAdapterVO = (TelefonoPanicoAdapter) userSession.get(TelefonoPanicoAdapter.NAME);
			// Si es nulo no se puede continuar
			if (telefonoPanicoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + TelefonoPanicoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TelefonoPanicoAdapter.NAME); 
			}
			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(telefonoPanicoAdapterVO, request);
            // Tiene errores recuperables
			if (telefonoPanicoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + telefonoPanicoAdapterVO.infoString()); 
				saveDemodaErrors(request, telefonoPanicoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, TelefonoPanicoAdapter.NAME, telefonoPanicoAdapterVO);
			}
			// llamada al servicio
			TelefonoPanicoVO telefonoPanicoVO =  DefServiceLocator.getConfiguracionService().updateTelefonoPanico(userSession, telefonoPanicoAdapterVO.getTelefonoPanico());
            // Tiene errores recuperables
			if (telefonoPanicoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + telefonoPanicoAdapterVO.infoString()); 
				saveDemodaErrors(request, telefonoPanicoVO);
				return forwardErrorRecoverable(mapping, request, userSession, TelefonoPanicoAdapter.NAME, telefonoPanicoAdapterVO);
			}
			// Tiene errores no recuperables
			if (telefonoPanicoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + telefonoPanicoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TelefonoPanicoAdapter.NAME, telefonoPanicoAdapterVO);
			}
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, TelefonoPanicoAdapter.NAME);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TelefonoPanicoAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_TELEFONOPANICO, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);
		try {
			// Bajo el adapter del userSession
			TelefonoPanicoAdapter telefonoPanicoAdapterVO = (TelefonoPanicoAdapter) userSession.get(TelefonoPanicoAdapter.NAME);
			// Si es nulo no se puede continuar
			if (telefonoPanicoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + TelefonoPanicoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TelefonoPanicoAdapter.NAME); 
			}
			// llamada al servicio
			TelefonoPanicoVO telefonoPanicoVO =  DefServiceLocator.getConfiguracionService().deleteTelefonoPanico(userSession, telefonoPanicoAdapterVO.getTelefonoPanico());
            // Tiene errores recuperables
			if (telefonoPanicoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + telefonoPanicoAdapterVO.infoString());
				saveDemodaErrors(request, telefonoPanicoVO);				
				request.setAttribute(TelefonoPanicoAdapter.NAME, telefonoPanicoAdapterVO);
				return mapping.findForward(ApmConstants.FWD_TELEFONOPANICO_VIEW_ADAPTER);
			}
			// Tiene errores no recuperables
			if (telefonoPanicoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + telefonoPanicoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TelefonoPanicoAdapter.NAME, telefonoPanicoAdapterVO);
			}
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, TelefonoPanicoAdapter.NAME);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TelefonoPanicoAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		return baseVolver(mapping, form, request, response, TelefonoPanicoAdapter.NAME);
	}
} 