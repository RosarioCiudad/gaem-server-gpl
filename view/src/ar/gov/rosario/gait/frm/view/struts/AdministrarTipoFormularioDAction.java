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
import ar.gov.rosario.gait.frm.iface.model.TipoFormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.TipoFormularioVO;
import ar.gov.rosario.gait.frm.iface.service.FrmServiceLocator;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import ar.gov.rosario.gait.frm.view.util.FrmConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;



public final class AdministrarTipoFormularioDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarTipoFormularioDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_TIPOFORMULARIO, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		TipoFormularioAdapter tipoFormularioAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getTipoFormularioAdapterForView(userSession, commonKey)";
				tipoFormularioAdapterVO = FrmServiceLocator.getFormularioService().getTipoFormularioAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_TIPOFORMULARIO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getTipoFormularioAdapterForUpdate(userSession, commonKey)";
				tipoFormularioAdapterVO= FrmServiceLocator.getFormularioService().getTipoFormularioAdapterForUpdate(userSession, commonKey);
				//${bean}AdapterVO = ${Modulo}ServiceLocator.get${Submodulo}Service().get${Bean}AdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_TIPOFORMULARIO_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getTipoFormularioAdapterForView(userSession, commonKey)";
				tipoFormularioAdapterVO = FrmServiceLocator.getFormularioService().getTipoFormularioAdapterForView(userSession, commonKey);				
				tipoFormularioAdapterVO.addMessage(BaseError.MSG_ELIMINAR, DefError.AREA_LABEL);
				actionForward = mapping.findForward(FrmConstants.FWD_TIPOFORMULARIO_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getTipoFormularioAdapterForCreate(userSession)";
				tipoFormularioAdapterVO = FrmServiceLocator.getFormularioService().getTipoFormularioAdapterForCreate(userSession);
				actionForward = mapping.findForward(FrmConstants.FWD_TIPOFORMULARIO_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (tipoFormularioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + tipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			tipoFormularioAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + TipoFormularioAdapter.NAME + ": "+ tipoFormularioAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
			// Subo el apdater al userSession
			userSession.put(TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);

			saveDemodaMessages(request, tipoFormularioAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TipoFormularioAdapter.NAME);
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
			TipoFormularioAdapter tipoFormularioAdapterVO = (TipoFormularioAdapter) userSession.get(TipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (tipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + TipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TipoFormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(tipoFormularioAdapterVO, request);

			// Tiene errores recuperables
			if (tipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, tipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
			}

			// llamada al servicio
			TipoFormularioVO tipoFormularioVO = FrmServiceLocator.getFormularioService().createTipoFormulario(userSession, tipoFormularioAdapterVO.getTipoFormulario());

			// Tiene errores recuperables
			if (tipoFormularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoFormularioVO.infoString()); 
				saveDemodaErrors(request, tipoFormularioVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
			}

			// Tiene errores no recuperables
			if (tipoFormularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tipoFormularioVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, TipoFormularioAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TipoFormularioAdapter.NAME);
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
			TipoFormularioAdapter tipoFormularioAdapterVO = (TipoFormularioAdapter) userSession.get(TipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (tipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + TipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TipoFormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(tipoFormularioAdapterVO, request);

			// Tiene errores recuperables
			if (tipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, tipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
			}

			// llamada al servicio
			TipoFormularioVO tipoFormularioVO = FrmServiceLocator.getFormularioService().updateTipoFormulario(userSession, tipoFormularioAdapterVO.getTipoFormulario());

			// Tiene errores recuperables
			if (tipoFormularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, tipoFormularioVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
			}

			// Tiene errores no recuperables
			if (tipoFormularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, TipoFormularioAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TipoFormularioAdapter.NAME);
		}
	}



	
	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_TIPOFORMULARIO, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			TipoFormularioAdapter tipoFormularioAdapterVO = (TipoFormularioAdapter) userSession.get(TipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (tipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + TipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TipoFormularioAdapter.NAME); 
			}

			// llamada al servicio
			TipoFormularioVO tipoFormularioVO = FrmServiceLocator.getFormularioService().deleteTipoFormulario
					(userSession, tipoFormularioAdapterVO.getTipoFormulario());
			
			//${Bean}VO ${bean}VO = ${Modulo}ServiceLocator.get${Submodulo}Service().delete${Bean}
			//(userSession, ${bean}AdapterVO.get${Bean}());

			// Tiene errores recuperables
			if (tipoFormularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoFormularioAdapterVO.infoString());
				saveDemodaErrors(request, tipoFormularioVO);				
				request.setAttribute(TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
				return mapping.findForward(FrmConstants.FWD_TIPOFORMULARIO_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (tipoFormularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, TipoFormularioAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TipoFormularioAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return baseVolver(mapping, form, request, response, TipoFormularioAdapter.NAME);

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
			TipoFormularioAdapter tipoFormularioAdapterVO = (TipoFormularioAdapter) userSession.get(TipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (tipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + TipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, TipoFormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(tipoFormularioAdapterVO, request);

			// Tiene errores recuperables
			if (tipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, tipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
			}

			// llamada al servicio
			//tipoFormularioAdapterVO = FrmServiceLocator.getAreaService().getAreaAdapterParam(userSession, tipoFormularioAdapterVO);

			// Tiene errores recuperables
			if (tipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + tipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, tipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
			}

			// Tiene errores no recuperables
			if (tipoFormularioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + tipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);
			// Subo el apdater al userSession
			userSession.put(TipoFormularioAdapter.NAME, tipoFormularioAdapterVO);

			return mapping.findForward(FrmConstants.FWD_TIPOFORMULARIO_VIEW_ADAPTER);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, TipoFormularioAdapter.NAME);
		}
	}


	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, TipoFormularioAdapter.NAME);			
	}


} 
