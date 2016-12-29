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

import ar.gov.rosario.gait.apm.iface.model.CampoValorOpcionAdapter;
import ar.gov.rosario.gait.apm.iface.model.CampoValorOpcionVO;
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






public final class AdministrarCampoValorOpcionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarCampoValorOpcionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPOVALOR_OPCION, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		CampoValorOpcionAdapter campoValorOpcionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getCampoValorOpcionAdapterForView(userSession, commonKey)";
				campoValorOpcionAdapterVO = ApmServiceLocator.getAplicacionService().getCampoValorOpcionAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPOVALOROPCION_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getCampoValorOpcionAdapterForUpdate(userSession, commonKey)";
				campoValorOpcionAdapterVO = ApmServiceLocator.getAplicacionService().getCampoValorOpcionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPOVALOROPCION_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getCampoValorOpcionAdapterForView(userSession, commonKey)";
				campoValorOpcionAdapterVO = ApmServiceLocator.getAplicacionService().getCampoValorOpcionAdapterForView(userSession, commonKey);				
				campoValorOpcionAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.CAMPO_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPOVALOROPCION_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getCampoValorOpcionAdapterForCreate(userSession)";
				campoValorOpcionAdapterVO = ApmServiceLocator.getAplicacionService().getCampoValorOpcionAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMPOVALOROPCION_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (campoValorOpcionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + campoValorOpcionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			campoValorOpcionAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + CampoValorOpcionAdapter.NAME + ": "+ campoValorOpcionAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);

			saveDemodaMessages(request, campoValorOpcionAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoValorOpcionAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPOVALOR_OPCION, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			CampoValorOpcionAdapter campoValorOpcionAdapterVO = (CampoValorOpcionAdapter) userSession.get(CampoValorOpcionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (campoValorOpcionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoValorOpcionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoValorOpcionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(campoValorOpcionAdapterVO, request);

			// Tiene errores recuperables
			if (campoValorOpcionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorOpcionAdapterVO.infoString()); 
				saveDemodaErrors(request, campoValorOpcionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
			}

			// llamada al servicio
			CampoValorOpcionVO campoValorOpcionVO = ApmServiceLocator.getAplicacionService().createCampoValorOpcion(userSession, campoValorOpcionAdapterVO.getCampoValorOpcion());

			// Tiene errores recuperables
			if (campoValorOpcionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorOpcionVO.infoString()); 
				saveDemodaErrors(request, campoValorOpcionVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
			}

			// Tiene errores no recuperables
			if (campoValorOpcionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoValorOpcionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, CampoValorOpcionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoValorOpcionAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPOVALOR_OPCION, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			CampoValorOpcionAdapter campoValorOpcionAdapterVO = (CampoValorOpcionAdapter) userSession.get(CampoValorOpcionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (campoValorOpcionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoValorOpcionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoValorOpcionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(campoValorOpcionAdapterVO, request);

			// Tiene errores recuperables
			if (campoValorOpcionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorOpcionAdapterVO.infoString()); 
				saveDemodaErrors(request, campoValorOpcionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
			}

			// llamada al servicio
			CampoValorOpcionVO campoValorOpcionVO = ApmServiceLocator.getAplicacionService().updateCampoValorOpcion(userSession, campoValorOpcionAdapterVO.getCampoValorOpcion());

			// Tiene errores recuperables
			if (campoValorOpcionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorOpcionAdapterVO.infoString()); 
				saveDemodaErrors(request, campoValorOpcionVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
			}

			// Tiene errores no recuperables
			if (campoValorOpcionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoValorOpcionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, CampoValorOpcionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoValorOpcionAdapter.NAME);
		}
	}


	public ActionForward modificarEncabezado(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_CAMPO, BaseConstants.ACT_MODIFICAR);

	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_CAMPOVALOR_OPCION, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			CampoValorOpcionAdapter campoValorOpcionAdapterVO = (CampoValorOpcionAdapter) userSession.get(CampoValorOpcionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (campoValorOpcionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoValorOpcionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoValorOpcionAdapter.NAME); 
			}

			// llamada al servicio
			CampoValorOpcionVO campoValorOpcionVO = ApmServiceLocator.getAplicacionService().deleteCampoValorOpcion
					(userSession, campoValorOpcionAdapterVO.getCampoValorOpcion());

			// Tiene errores recuperables
			if (campoValorOpcionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorOpcionAdapterVO.infoString());
				saveDemodaErrors(request, campoValorOpcionVO);				
				request.setAttribute(CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
				return mapping.findForward(ApmConstants.FWD_CAMPOVALOROPCION_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (campoValorOpcionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoValorOpcionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, CampoValorOpcionAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoValorOpcionAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {

		return baseVolver(mapping, form, request, response, CampoValorOpcionAdapter.NAME);

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
			CampoValorOpcionAdapter campoValorOpcionAdapterVO = (CampoValorOpcionAdapter) userSession.get(CampoValorOpcionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (campoValorOpcionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + CampoValorOpcionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, CampoValorOpcionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(campoValorOpcionAdapterVO, request);

			// Tiene errores recuperables
			if (campoValorOpcionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorOpcionAdapterVO.infoString()); 
				saveDemodaErrors(request, campoValorOpcionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
			}

			// llamada al servicio
			//campoValorOpcionAdapterVO = ApmServiceLocator.getCampoValorService().getCampoValorOpcionAdapterParam(userSession, campoValorOpcionAdapterVO);

			// Tiene errores recuperables
			if (campoValorOpcionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + campoValorOpcionAdapterVO.infoString()); 
				saveDemodaErrors(request, campoValorOpcionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
			}

			// Tiene errores no recuperables
			if (campoValorOpcionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + campoValorOpcionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(CampoValorOpcionAdapter.NAME, campoValorOpcionAdapterVO);

			return mapping.findForward(ApmConstants.FWD_CAMPOVALOROPCION_EDIT_ADAPTER);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CampoValorOpcionAdapter.NAME);
		}
	}


	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, CampoValorOpcionAdapter.NAME);			
	}


} 
