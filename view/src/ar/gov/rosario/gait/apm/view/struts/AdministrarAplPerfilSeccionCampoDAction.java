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

import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoVO;
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


public final class AdministrarAplPerfilSeccionCampoDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarAplPerfilSeccionCampoDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		AplPerfilSeccionCampoAdapter aplPerfilSeccionCampoAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getAplPerfilSeccionCampoAdapterForView(userSession, commonKey)";
				aplPerfilSeccionCampoAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getAplPerfilSeccionCampoAdapterForUpdate(userSession, commonKey)";
				aplPerfilSeccionCampoAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPO_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getAplPerfilSeccionCampoAdapterForView(userSession, commonKey)";
				aplPerfilSeccionCampoAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoAdapterForView(userSession, commonKey);				
				aplPerfilSeccionCampoAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.CAMPO_LABEL);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPO_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getAplPerfilSeccionCampoAdapterForCreate(userSession)";
				aplPerfilSeccionCampoAdapterVO = ApmServiceLocator.getAplicacionService().getAplPerfilSeccionCampoAdapterForCreate(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPO_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (aplPerfilSeccionCampoAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + aplPerfilSeccionCampoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			aplPerfilSeccionCampoAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + AplPerfilSeccionCampoAdapter.NAME + ": "+ aplPerfilSeccionCampoAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);

			saveDemodaMessages(request, aplPerfilSeccionCampoAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplPerfilSeccionCampoAdapter aplPerfilSeccionCampoAdapterVO = (AplPerfilSeccionCampoAdapter) userSession.get(AplPerfilSeccionCampoAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplPerfilSeccionCampoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplPerfilSeccionCampoAdapterVO, request);

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
			}

			// llamada al servicio
			AplPerfilSeccionCampoVO aplPerfilSeccionCampoVO = ApmServiceLocator.getAplicacionService().createAplPerfilSeccionCampo(userSession, aplPerfilSeccionCampoAdapterVO.getAplPerfilSeccionCampo());

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplPerfilSeccionCampoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionCampoVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplPerfilSeccionCampoAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplPerfilSeccionCampoAdapter aplPerfilSeccionCampoAdapterVO = (AplPerfilSeccionCampoAdapter) userSession.get(AplPerfilSeccionCampoAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplPerfilSeccionCampoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplPerfilSeccionCampoAdapterVO, request);

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
			}

			// llamada al servicio
			AplPerfilSeccionCampoVO aplPerfilSeccionCampoVO = ApmServiceLocator.getAplicacionService().updateAplPerfilSeccionCampo(userSession, aplPerfilSeccionCampoAdapterVO.getAplPerfilSeccionCampo());

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplPerfilSeccionCampoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionCampoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplPerfilSeccionCampoAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoAdapter.NAME);
		}
	}


	public ActionForward modificarEncabezado(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_APLPERFILSECCIONCAMPO, BaseConstants.ACT_MODIFICAR);

	}

	
	
	
	public ActionForward agregarAplPerfilSeccionCampoValor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardAgregarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_APLPERFILSECCIONCAMPOVALOR);
	}	

	public ActionForward verAplPerfilSeccionCampoValor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLPERFILSECCIONCAMPOVALOR);
	}	
	public ActionForward modificarAplPerfilSeccionCampoValor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardModificarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLPERFILSECCIONCAMPOVALOR);
	}	
	public ActionForward eliminarAplPerfilSeccionCampoValor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{		

		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_APLPERFILSECCIONCAMPOVALOR);
	}	

	
	
	
	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_APLPERFILSECCION_CAMPO, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			AplPerfilSeccionCampoAdapter aplPerfilSeccionCampoAdapterVO = (AplPerfilSeccionCampoAdapter) userSession.get(AplPerfilSeccionCampoAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplPerfilSeccionCampoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoAdapter.NAME); 
			}

			// llamada al servicio
			AplPerfilSeccionCampoVO aplPerfilSeccionCampoVO = ApmServiceLocator.getAplicacionService().deleteAplPerfilSeccionCampo
					(userSession, aplPerfilSeccionCampoAdapterVO.getAplPerfilSeccionCampo());

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoAdapterVO.infoString());
				saveDemodaErrors(request, aplPerfilSeccionCampoVO);				
				request.setAttribute(AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
				return mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPO_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (aplPerfilSeccionCampoVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionCampoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, AplPerfilSeccionCampoAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return baseVolver(mapping, form, request, response, AplPerfilSeccionCampoAdapter.NAME);

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
			AplPerfilSeccionCampoAdapter aplPerfilSeccionCampoAdapterVO = (AplPerfilSeccionCampoAdapter) userSession.get(AplPerfilSeccionCampoAdapter.NAME);

			// Si es nulo no se puede continuar
			if (aplPerfilSeccionCampoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + AplPerfilSeccionCampoAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, AplPerfilSeccionCampoAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(aplPerfilSeccionCampoAdapterVO, request);

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
			}

			// llamada al servicio
			//aplPerfilSeccionCampoAdapterVO = ApmServiceLocator.getAplPerfilSeccionCampoService().getAplPerfilSeccionCampoAdapterParam(userSession, aplPerfilSeccionCampoAdapterVO);

			// Tiene errores recuperables
			if (aplPerfilSeccionCampoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + aplPerfilSeccionCampoAdapterVO.infoString()); 
				saveDemodaErrors(request, aplPerfilSeccionCampoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
			}

			// Tiene errores no recuperables
			if (aplPerfilSeccionCampoAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + aplPerfilSeccionCampoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);
			// Subo el apdater al userSession
			userSession.put(AplPerfilSeccionCampoAdapter.NAME, aplPerfilSeccionCampoAdapterVO);

			return mapping.findForward(ApmConstants.FWD_APLPERFILSECCIONCAMPO_ADAPTER);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, AplPerfilSeccionCampoAdapter.NAME);
		}
	}


	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, AplPerfilSeccionCampoAdapter.NAME);			
	}


} 
