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
package ar.gov.rosario.gait.frm.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.frm.iface.model.FormularioDetalleAdapter;
import ar.gov.rosario.gait.frm.iface.model.FormularioDetalleVO;
import ar.gov.rosario.gait.frm.iface.service.FrmServiceLocator;
import ar.gov.rosario.gait.frm.iface.util.FrmError;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import ar.gov.rosario.gait.frm.view.util.FrmConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarFormularioDetalleDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarFormularioDetalleDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_FORMULARIODETALLE, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		FormularioDetalleAdapter formularioDetalleAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getFormularioDetalleAdapterForView(userSession, commonKey)";
				formularioDetalleAdapterVO = FrmServiceLocator.getFormularioService().getFormularioDetalleAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_FORMULARIODETALLE_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getFormularioDetalleAdapterForUpdate(userSession, commonKey)";
				formularioDetalleAdapterVO = FrmServiceLocator.getFormularioService().getFormularioDetalleAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_FORMULARIODETALLE_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getFormularioDetalleAdapterForView(userSession, commonKey)";
				formularioDetalleAdapterVO = FrmServiceLocator.getFormularioService().getFormularioDetalleAdapterForView(userSession, commonKey);				
				formularioDetalleAdapterVO.addMessage(BaseError.MSG_ELIMINAR, FrmError.FORMULARIODETALLE_LABEL);
				actionForward = mapping.findForward(FrmConstants.FWD_FORMULARIODETALLE_VIEW_ADAPTER);				
			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (formularioDetalleAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + formularioDetalleAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, FormularioDetalleAdapter.NAME, formularioDetalleAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			formularioDetalleAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + FormularioDetalleAdapter.NAME + ": "+ formularioDetalleAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(FormularioDetalleAdapter.NAME, formularioDetalleAdapterVO);
			// Subo el apdater al userSession
			userSession.put(FormularioDetalleAdapter.NAME, formularioDetalleAdapterVO);
			 
			saveDemodaMessages(request, formularioDetalleAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, FormularioDetalleAdapter.NAME);
		}
	}
	
	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_FORMULARIODETALLE, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			FormularioDetalleAdapter formularioDetalleAdapterVO = (FormularioDetalleAdapter) userSession.get(FormularioDetalleAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (formularioDetalleAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + FormularioDetalleAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, FormularioDetalleAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(formularioDetalleAdapterVO, request);
			
            // Tiene errores recuperables
			if (formularioDetalleAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + formularioDetalleAdapterVO.infoString()); 
				saveDemodaErrors(request, formularioDetalleAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, FormularioDetalleAdapter.NAME, formularioDetalleAdapterVO);
			}
			
			// llamada al servicio
			FormularioDetalleVO formularioDetalleVO = FrmServiceLocator.getFormularioService().updateFormularioDetalle(userSession, formularioDetalleAdapterVO.getFormularioDetalle());
			
            // Tiene errores recuperables
			if (formularioDetalleVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + formularioDetalleAdapterVO.infoString()); 
				saveDemodaErrors(request, formularioDetalleVO);
				return forwardErrorRecoverable(mapping, request, userSession, FormularioDetalleAdapter.NAME, formularioDetalleAdapterVO);
			}
			
			// Tiene errores no recuperables
			if (formularioDetalleVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + formularioDetalleAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, FormularioDetalleAdapter.NAME, formularioDetalleAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, FormularioDetalleAdapter.NAME);
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, FormularioDetalleAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_FORMULARIODETALLE, BaseSecurityConstants.ELIMINAR); 
		if (userSession == null) return forwardErrorSession(request);
		
		try {
			// Bajo el adapter del userSession
			FormularioDetalleAdapter formularioDetalleAdapterVO = (FormularioDetalleAdapter) userSession.get(FormularioDetalleAdapter.NAME);
			
			// Si es nulo no se puede continuar
			if (formularioDetalleAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + FormularioDetalleAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, FormularioDetalleAdapter.NAME); 
			}

			// llamada al servicio
			FormularioDetalleVO formularioDetalleVO = FrmServiceLocator.getFormularioService().deleteFormularioDetalle
				(userSession, formularioDetalleAdapterVO.getFormularioDetalle());
			
            // Tiene errores recuperables
			if (formularioDetalleVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + formularioDetalleAdapterVO.infoString());
				saveDemodaErrors(request, formularioDetalleVO);				
				request.setAttribute(FormularioDetalleAdapter.NAME, formularioDetalleAdapterVO);
				return mapping.findForward(FrmConstants.FWD_FORMULARIODETALLE_VIEW_ADAPTER);
			}
			
			// Tiene errores no recuperables
			if (formularioDetalleVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + formularioDetalleAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, FormularioDetalleAdapter.NAME, formularioDetalleAdapterVO);
			}
			
			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, FormularioDetalleAdapter.NAME);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, FormularioDetalleAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, FormularioDetalleAdapter.NAME);
	}
}