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

import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorOpcionAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorOpcionVO;
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






public final class AdministrarAplPerfilSeccionCampoValorOpcionDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarAplPerfilSeccionCampoValorOpcionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR_OPCION, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		AplPerfilSeccionCampoValorOpcionAdapter aplPerfilSeccionCampoValorOpcionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getAplPerfilSeccionCampoValorOpcionAdapterForView(userSession, commonKey)";
				aplPerfilSeccionCampoValorOpcionAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoValorOpcionAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOROPCION_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplPerfilSeccionCampoValorOpcionAdapterForUpdate(userSession, commonKey)";
				aplPerfilSeccionCampoValorOpcionAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoValorOpcionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOROPCION_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getAplPerfilSeccionCampoValorOpcionAdapterForView(userSession, commonKey)";
				aplPerfilSeccionCampoValorOpcionAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoValorOpcionAdapterForView(userSession, commonKey);				
				aplPerfilSeccionCampoValorOpcionAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.CAMPO_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOROPCION_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplPerfilSeccionCampoValorOpcionAdapterForCreate(userSession)";
				aplPerfilSeccionCampoValorOpcionAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoValorOpcionAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOROPCION_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (aplPerfilSeccionCampoValorOpcionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplPerfilSeccionCampoValorOpcionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			aplPerfilSeccionCampoValorOpcionAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplPerfilSeccionCampoValorOpcionAdapter.NAME + ": "+ aplPerfilSeccionCampoValorOpcionAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);

			saveDemodaMessages(request, aplPerfilSeccionCampoValorOpcionAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoValorOpcionAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR_OPCION, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplPerfilSeccionCampoValorOpcionAdapter aplPerfilSeccionCampoValorOpcionAdapterVO = (AplPerfilSeccionCampoValorOpcionAdapter) userSession.get(AplPerfilSeccionCampoValorOpcionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplPerfilSeccionCampoValorOpcionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoValorOpcionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoValorOpcionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplPerfilSeccionCampoValorOpcionAdapterVO, request);

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoValorOpcionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoValorOpcionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoValorOpcionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
			}

			// llamada al servicio
			AplPerfilSeccionCampoValorOpcionVO aplPerfilSeccionCampoValorOpcionVO = ApmServiceLocator.getAplicacionService().
					createAplPerfilSeccionCampoValorOpcion(userSession, aplPerfilSeccionCampoValorOpcionAdapterVO.getAplPerfilSeccionCampoValorOpcion());

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoValorOpcionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoValorOpcionVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoValorOpcionVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplPerfilSeccionCampoValorOpcionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionCampoValorOpcionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplPerfilSeccionCampoValorOpcionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoValorOpcionAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR_OPCION, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplPerfilSeccionCampoValorOpcionAdapter aplPerfilSeccionCampoValorOpcionAdapterVO = (AplPerfilSeccionCampoValorOpcionAdapter) userSession.get(AplPerfilSeccionCampoValorOpcionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplPerfilSeccionCampoValorOpcionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoValorOpcionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoValorOpcionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplPerfilSeccionCampoValorOpcionAdapterVO, request);

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoValorOpcionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoValorOpcionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoValorOpcionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
			}

			// llamada al servicio
			AplPerfilSeccionCampoValorOpcionVO aplPerfilSeccionCampoValorOpcionVO = ApmServiceLocator.getAplicacionService().updateAplPerfilSeccionCampoValorOpcion(userSession, aplPerfilSeccionCampoValorOpcionAdapterVO.getAplPerfilSeccionCampoValorOpcion());

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoValorOpcionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoValorOpcionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoValorOpcionVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplPerfilSeccionCampoValorOpcionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionCampoValorOpcionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplPerfilSeccionCampoValorOpcionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoValorOpcionAdapter.NAME);
		}
	}

	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO_VALOR_OPCION, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplPerfilSeccionCampoValorOpcionAdapter aplPerfilSeccionCampoValorOpcionAdapterVO = (AplPerfilSeccionCampoValorOpcionAdapter) userSession.get(AplPerfilSeccionCampoValorOpcionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplPerfilSeccionCampoValorOpcionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoValorOpcionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoValorOpcionAdapter.NAME); 
			}

			// llamada al servicio
			AplPerfilSeccionCampoValorOpcionVO aplPerfilSeccionCampoValorOpcionVO = ApmServiceLocator.getAplicacionService().deleteAplPerfilSeccionCampoValorOpcion
					(userSession, aplPerfilSeccionCampoValorOpcionAdapterVO.getAplPerfilSeccionCampoValorOpcion());

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoValorOpcionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoValorOpcionAdapterVO.infoString());
				saveDemodaErrors(request, aplPerfilSeccionCampoValorOpcionVO);				
				request.setAttribute(AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
				return mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOROPCION_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (aplPerfilSeccionCampoValorOpcionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionCampoValorOpcionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplPerfilSeccionCampoValorOpcionAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoValorOpcionAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return baseVolver(mapping, form, request, response, AplPerfilSeccionCampoValorOpcionAdapter.NAME);

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
			AplPerfilSeccionCampoValorOpcionAdapter aplPerfilSeccionCampoValorOpcionAdapterVO = (AplPerfilSeccionCampoValorOpcionAdapter) userSession.get(AplPerfilSeccionCampoValorOpcionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplPerfilSeccionCampoValorOpcionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoValorOpcionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoValorOpcionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplPerfilSeccionCampoValorOpcionAdapterVO, request);

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoValorOpcionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoValorOpcionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoValorOpcionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
			}

			// llamada al servicio
			//aplPerfilSeccionCampoValorOpcionAdapterVO = ApmServiceLocator.getCampoValorService().getAplPerfilSeccionCampoValorOpcionAdapterParam(userSession, aplPerfilSeccionCampoValorOpcionAdapterVO);

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoValorOpcionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoValorOpcionAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoValorOpcionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplPerfilSeccionCampoValorOpcionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionCampoValorOpcionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplPerfilSeccionCampoValorOpcionAdapter.NAME, aplPerfilSeccionCampoValorOpcionAdapterVO);

			return mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPOVALOROPCION_EDIT_ADAPTER);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoValorOpcionAdapter.NAME);
		}
	}


	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, AplPerfilSeccionCampoValorOpcionAdapter.NAME);			
	}


} 
