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
import ar.gov.rosario.gait.def.iface.util.DefError;
import ar.gov.rosario.gait.def.iface.util.DefSecurityConstants;
import ar.gov.rosario.gait.frm.iface.model.EstadoTipoFormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.EstadoTipoFormularioVO;
import ar.gov.rosario.gait.frm.iface.service.FrmServiceLocator;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import ar.gov.rosario.gait.frm.view.util.FrmConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;



public final class AdministrarEstadoTipoFormularioDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarEstadoTipoFormularioDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_ESTADOTIPOFORMULARIO, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		EstadoTipoFormularioAdapter estadoTipoFormularioAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getEstadoTipoFormularioAdapterForView(userSession, commonKey)";
				estadoTipoFormularioAdapterVO = FrmServiceLocator.getFormularioService().getEstadoTipoFormularioAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_ESTADOTIPOFORMULARIO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getEstadoTipoFormularioAdapterForUpdate(userSession, commonKey)";
				estadoTipoFormularioAdapterVO= FrmServiceLocator.getFormularioService().getEstadoTipoFormularioAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_ESTADOTIPOFORMULARIO_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getEstadoTipoFormularioAdapterForView(userSession, commonKey)";
				estadoTipoFormularioAdapterVO = FrmServiceLocator.getFormularioService().getEstadoTipoFormularioAdapterForView(userSession, commonKey);				
				estadoTipoFormularioAdapterVO.addMessage(BaseError.MSG_ELIMINAR, DefError.AREA_LABEL);
				actionForward = mapping.findForward(FrmConstants.FWD_ESTADOTIPOFORMULARIO_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getEstadoTipoFormularioAdapterForCreate(userSession)";
				estadoTipoFormularioAdapterVO = FrmServiceLocator.getFormularioService().getEstadoTipoFormularioAdapterForCreate(userSession);
				actionForward = mapping.findForward(FrmConstants.FWD_ESTADOTIPOFORMULARIO_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (estadoTipoFormularioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + estadoTipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			estadoTipoFormularioAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + EstadoTipoFormularioAdapter.NAME + ": "+ estadoTipoFormularioAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
			// Subo el apdater al userSession
			userSession.put(EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);

			saveDemodaMessages(request, estadoTipoFormularioAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, EstadoTipoFormularioAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_AREA, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			EstadoTipoFormularioAdapter estadoTipoFormularioAdapterVO = (EstadoTipoFormularioAdapter) userSession.get(EstadoTipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (estadoTipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + EstadoTipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, EstadoTipoFormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(estadoTipoFormularioAdapterVO, request);

			// Tiene errores recuperables
			if (estadoTipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, estadoTipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
			}

			// llamada al servicio
			EstadoTipoFormularioVO estadoTipoFormularioVO = FrmServiceLocator.getFormularioService().createEstadoTipoFormulario(userSession, estadoTipoFormularioAdapterVO.getEstadoTipoFormulario());

			// Tiene errores recuperables
			if (estadoTipoFormularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoTipoFormularioVO.infoString()); 
				saveDemodaErrors(request, estadoTipoFormularioVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
			}

			// Tiene errores no recuperables
			if (estadoTipoFormularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + estadoTipoFormularioVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, EstadoTipoFormularioAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, EstadoTipoFormularioAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, DefSecurityConstants.ABM_AREA, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			EstadoTipoFormularioAdapter estadoTipoFormularioAdapterVO = (EstadoTipoFormularioAdapter) userSession.get(EstadoTipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (estadoTipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + EstadoTipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, EstadoTipoFormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(estadoTipoFormularioAdapterVO, request);

			// Tiene errores recuperables
			if (estadoTipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, estadoTipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
			}

			// llamada al servicio
			EstadoTipoFormularioVO estadoTipoFormularioVO = FrmServiceLocator.getFormularioService().updateEstadoTipoFormulario(userSession, estadoTipoFormularioAdapterVO.getEstadoTipoFormulario());

			// Tiene errores recuperables
			if (estadoTipoFormularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, estadoTipoFormularioVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
			}

			// Tiene errores no recuperables
			if (estadoTipoFormularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + estadoTipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, EstadoTipoFormularioAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, EstadoTipoFormularioAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_ESTADOTIPOFORMULARIO, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			EstadoTipoFormularioAdapter estadoTipoFormularioAdapterVO = (EstadoTipoFormularioAdapter) userSession.get(EstadoTipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (estadoTipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + EstadoTipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, EstadoTipoFormularioAdapter.NAME); 
			}

			// llamada al servicio
			EstadoTipoFormularioVO estadoTipoFormularioVO = FrmServiceLocator.getFormularioService().deleteEstadoTipoFormulario
					(userSession, estadoTipoFormularioAdapterVO.getEstadoTipoFormulario());
			
			// Tiene errores recuperables
			if (estadoTipoFormularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoTipoFormularioAdapterVO.infoString());
				saveDemodaErrors(request, estadoTipoFormularioVO);				
				request.setAttribute(EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
				return mapping.findForward(FrmConstants.FWD_ESTADOTIPOFORMULARIO_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (estadoTipoFormularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + estadoTipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, EstadoTipoFormularioAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, EstadoTipoFormularioAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return baseVolver(mapping, form, request, response, EstadoTipoFormularioAdapter.NAME);

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
			EstadoTipoFormularioAdapter estadoTipoFormularioAdapterVO = (EstadoTipoFormularioAdapter) userSession.get(EstadoTipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (estadoTipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + EstadoTipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, EstadoTipoFormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(estadoTipoFormularioAdapterVO, request);

			// Tiene errores recuperables
			if (estadoTipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, estadoTipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
			}

			// Tiene errores recuperables
			if (estadoTipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + estadoTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, estadoTipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
			}

			// Tiene errores no recuperables
			if (estadoTipoFormularioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + estadoTipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);
			// Subo el apdater al userSession
			userSession.put(EstadoTipoFormularioAdapter.NAME, estadoTipoFormularioAdapterVO);

			return mapping.findForward(FrmConstants.FWD_ESTADOTIPOFORMULARIO_VIEW_ADAPTER);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, EstadoTipoFormularioAdapter.NAME);
		}
	}


	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, EstadoTipoFormularioAdapter.NAME);			
	}


} 
