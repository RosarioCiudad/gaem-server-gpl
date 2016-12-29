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


public final class AdministrarCampoValorDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarCampoValorDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPOVALOR, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		CampoValorAdapter campoValorAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getCampoValorAdapterForView(userSession, commonKey)";
				campoValorAdapterVO = ApmServiceLocator.getAplicacionService().getCampoValorAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPOVALOR_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getCampoValorAdapterForUpdate(userSession, commonKey)";
				campoValorAdapterVO = ApmServiceLocator.getAplicacionService().getCampoValorAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPOVALOR_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getCampoValorAdapterForView(userSession, commonKey)";
				campoValorAdapterVO = ApmServiceLocator.getAplicacionService().getCampoValorAdapterForView(userSession, commonKey);				
				campoValorAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.CAMPO_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPOVALOR_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getCampoValorAdapterForCreate(userSession)";
				campoValorAdapterVO = ApmServiceLocator.getAplicacionService().getCampoValorAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPOVALOR_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (campoValorAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + campoValorAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoValorAdapter.NAME, campoValorAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			campoValorAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + CampoValorAdapter.NAME + ": "+ campoValorAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(CampoValorAdapter.NAME, campoValorAdapterVO);
			// Subo el apdater al userSession
			userSession.put(CampoValorAdapter.NAME, campoValorAdapterVO);

			saveDemodaMessages(request, campoValorAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoValorAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPOVALOR, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			CampoValorAdapter campoValorAdapterVO = (CampoValorAdapter) userSession.get(CampoValorAdapter.NAME);

			// Si es nulo no se puede continuar
			if (campoValorAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoValorAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoValorAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(campoValorAdapterVO, request);

			// Tiene errores recuperables
			if (campoValorAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, campoValorAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorAdapter.NAME, campoValorAdapterVO);
			}

			// llamada al servicio
			CampoValorVO campoValorVO = ApmServiceLocator.getAplicacionService().createCampoValor(userSession, campoValorAdapterVO.getCampoValor());

			// Tiene errores recuperables
			if (campoValorVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorVO.infoString()); 
				saveDemodaErrors(request, campoValorVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorAdapter.NAME, campoValorAdapterVO);
			}

			// Tiene errores no recuperables
			if (campoValorVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoValorVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoValorAdapter.NAME, campoValorAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, CampoValorAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoValorAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPOVALOR, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			CampoValorAdapter campoValorAdapterVO = (CampoValorAdapter) userSession.get(CampoValorAdapter.NAME);

			// Si es nulo no se puede continuar
			if (campoValorAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoValorAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoValorAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(campoValorAdapterVO, request);

			// Tiene errores recuperables
			if (campoValorAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, campoValorAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorAdapter.NAME, campoValorAdapterVO);
			}

			// llamada al servicio
			CampoValorVO campoValorVO = ApmServiceLocator.getAplicacionService().updateCampoValor(userSession, campoValorAdapterVO.getCampoValor());

			// Tiene errores recuperables
			if (campoValorVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorAdapterVO.infoString()); 
				saveDemodaErrors(request, campoValorVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorAdapter.NAME, campoValorAdapterVO);
			}

			// Tiene errores no recuperables
			if (campoValorVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoValorAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoValorAdapter.NAME, campoValorAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, CampoValorAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoValorAdapter.NAME);
		}
	}


	public ActionForward modificarEncabezado(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_CAMPOVALOR, BaseConstants.ACT_MODIFICAR);
	}

	public ActionForward agregarCampoValorOpcion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_CAMPOVALOROPCION);
	}	

	public ActionForward verCampoValorOpcion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_CAMPOVALOROPCION);
	}	
	public ActionForward modificarCampoValorOpcion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_CAMPOVALOROPCION);
	}	
	public ActionForward eliminarCampoValorOpcion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_CAMPOVALOROPCION);
	}	

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPOVALOR, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			CampoValorAdapter campoValorAdapterVO = (CampoValorAdapter) userSession.get(CampoValorAdapter.NAME);

			// Si es nulo no se puede continuar
			if (campoValorAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoValorAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoValorAdapter.NAME); 
			}

			// llamada al servicio
			CampoValorVO campoValorVO = ApmServiceLocator.getAplicacionService().deleteCampoValor(userSession, campoValorAdapterVO.getCampoValor());

			// Tiene errores recuperables
			if (campoValorVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorAdapterVO.infoString());
				saveDemodaErrors(request, campoValorVO);				
				request.setAttribute(CampoValorAdapter.NAME, campoValorAdapterVO);
				return mapping.findForward(ApmConstants.FWD_CAMPOVALOR_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (campoValorVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoValorAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoValorAdapter.NAME, campoValorAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, CampoValorAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoValorAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {

		return baseVolver(mapping, form, request, response, CampoValorAdapter.NAME);

	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, CampoValorAdapter.NAME);			
	}
} 
