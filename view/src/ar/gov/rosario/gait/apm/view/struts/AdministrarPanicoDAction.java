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

import ar.gov.rosario.gait.apm.iface.model.PanicoAdapter;
import ar.gov.rosario.gait.apm.iface.model.PanicoSearchPage;
import ar.gov.rosario.gait.apm.iface.model.SeccionAdapter;
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarPanicoDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarPanicoDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_SECCION, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		PanicoAdapter panicoAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			// Bajo el searchPage del userSession
			PanicoSearchPage panicoSearchPageVO = (PanicoSearchPage) userSession.get(PanicoSearchPage.NAME);
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getPanicoAdapterForView(userSession, commonKey, panicoSearchPageVO)";
				panicoAdapterVO = ApmServiceLocator.getPanicoService().getPanicoAdapterForView(userSession, commonKey, panicoSearchPageVO);
				actionForward = mapping.findForward(ApmConstants.FWD_PANICO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getPanicoAdapterForUpdate(userSession, commonKey, panicoSearchPageVO)";
				panicoAdapterVO = ApmServiceLocator.getPanicoService().getPanicoAdapterForUpdate(userSession, commonKey, panicoSearchPageVO);
				actionForward = mapping.findForward(ApmConstants.FWD_PANICO_ADAPTER);
			}
//			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
//				stringServicio = "getSeccionAdapterForView(userSession, commonKey)";
//				seccionAdapterVO = ApmServiceLocator.getAplicacionService().getSeccionAdapterForView(userSession, commonKey);				
//				seccionAdapterVO.addMessage(BaseError.MSG_ELIMINAR, ApmError.SECCION_LABEL);
//				actionForward = mapping.findForward(ApmConstants.FWD_SECCION_VIEW_ADAPTER);				
//			}
		
			
			// Tiene errores no recuperables
			if (panicoAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + panicoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, SeccionAdapter.NAME, panicoAdapterVO);
			}	

			// Seteo los valores de navegacion en el adapter
			panicoAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + SeccionAdapter.NAME + ": "+ panicoAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(PanicoAdapter.NAME, panicoAdapterVO);
			// Subo el apdater al userSession
			userSession.put(PanicoAdapter.NAME, panicoAdapterVO);
			 
			saveDemodaMessages(request, panicoAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PanicoAdapter.NAME);
		}
	}
	
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return baseVolver(mapping, form, request, response, PanicoAdapter.NAME);
	}
	
	
	public ActionForward refill(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, PanicoAdapter.NAME);
	}
	
	public ActionForward cambiarEstado(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_CAMBIOESTADO_PANICO, PanicoAdapter.ACT_CAMBIARESTADO);
	}
	
	public ActionForward verHisEstPan(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardVerAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_HISESTPAN);
	}
	
	public ActionForward eliminarHisEstPan(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{		
		String funcName = DemodaUtil.currentMethodName();
		return forwardEliminarAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_HISESTPAN);
	}
	
	public ActionForward modificarEncabezado(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		return baseForwardAdapter(mapping, request, funcName, ApmConstants.ACTION_ADMINISTRAR_ENC_PANICO, BaseConstants.ACT_MODIFICAR);
	}
	

	//	public ActionForward agregar(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//
//		String funcName = DemodaUtil.currentMethodName();
//		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
//		
//		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_SECCION, BaseSecurityConstants.AGREGAR); 
//		if (userSession==null) return forwardErrorSession(request);
//		
//		try {
//			// Bajo el adapter del userSession
//			SeccionAdapter seccionAdapterVO = (SeccionAdapter) userSession.get(SeccionAdapter.NAME);
//			
//			// Si es nulo no se puede continuar
//			if (seccionAdapterVO == null) {
//				log.error("error en: "  + funcName + ": " + SeccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
//				return forwardErrorSessionNullObject(mapping, request, funcName, SeccionAdapter.NAME); 
//			}
//
//			// Recuperamos datos del form en el vo
//			DemodaUtil.populateVO(seccionAdapterVO, request);
//			
//            // Tiene errores recuperables
//			if (seccionAdapterVO.hasErrorRecoverable()) {
//				log.error("recoverable error en: "  + funcName + ": " + seccionAdapterVO.infoString()); 
//				saveDemodaErrors(request, seccionAdapterVO);
//				return forwardErrorRecoverable(mapping, request, userSession, SeccionAdapter.NAME, seccionAdapterVO);
//			}
//			
//			// llamada al servicio
//			SeccionVO seccionVO = ApmServiceLocator.getAplicacionService().createSeccion(userSession, seccionAdapterVO.getSeccion());
//			
//            // Tiene errores recuperables
//			if (seccionVO.hasErrorRecoverable()) {
//				log.error("recoverable error en: "  + funcName + ": " + seccionVO.infoString()); 
//				saveDemodaErrors(request, seccionVO);
//				return forwardErrorRecoverable(mapping, request, userSession, SeccionAdapter.NAME, seccionAdapterVO);
//			}
//			
//			// Tiene errores no recuperables
//			if (seccionVO.hasErrorNonRecoverable()) {
//				log.error("error en: "  + funcName + ": " + seccionVO.errorString()); 
//				return forwardErrorNonRecoverable(mapping, request, funcName, SeccionAdapter.NAME, seccionAdapterVO);
//			}
//			
//			// Fue Exitoso
//			return forwardConfirmarOk(mapping, request, funcName, SeccionAdapter.NAME);
//			
//		} catch (Exception exception) {
//			return baseException(mapping, request, funcName, exception, SeccionAdapter.NAME);
//		}
//	}
//
//	public ActionForward modificar(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//
//		String funcName = DemodaUtil.currentMethodName();
//		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
//		
//		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_SECCION, BaseSecurityConstants.MODIFICAR); 
//		if (userSession==null) return forwardErrorSession(request);
//		
//		try {
//			// Bajo el adapter del userSession
//			SeccionAdapter seccionAdapterVO = (SeccionAdapter) userSession.get(SeccionAdapter.NAME);
//			
//			// Si es nulo no se puede continuar
//			if (seccionAdapterVO == null) {
//				log.error("error en: "  + funcName + ": " + SeccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
//				return forwardErrorSessionNullObject(mapping, request, funcName, SeccionAdapter.NAME); 
//			}
//
//			// Recuperamos datos del form en el vo
//			DemodaUtil.populateVO(seccionAdapterVO, request);
//			
//            // Tiene errores recuperables
//			if (seccionAdapterVO.hasErrorRecoverable()) {
//				log.error("recoverable error en: "  + funcName + ": " + seccionAdapterVO.infoString()); 
//				saveDemodaErrors(request, seccionAdapterVO);
//				return forwardErrorRecoverable(mapping, request, userSession, SeccionAdapter.NAME, seccionAdapterVO);
//			}
//			
//			// llamada al servicio
//			SeccionVO seccionVO = ApmServiceLocator.getAplicacionService().updateSeccion(userSession, seccionAdapterVO.getSeccion());
//			
//            // Tiene errores recuperables
//			if (seccionVO.hasErrorRecoverable()) {
//				log.error("recoverable error en: "  + funcName + ": " + seccionAdapterVO.infoString()); 
//				saveDemodaErrors(request, seccionVO);
//				return forwardErrorRecoverable(mapping, request, userSession, SeccionAdapter.NAME, seccionAdapterVO);
//			}
//			
//			// Tiene errores no recuperables
//			if (seccionVO.hasErrorNonRecoverable()) {
//				log.error("error en: "  + funcName + ": " + seccionAdapterVO.errorString()); 
//				return forwardErrorNonRecoverable(mapping, request, funcName, SeccionAdapter.NAME, seccionAdapterVO);
//			}
//			
//			// Fue Exitoso
//			return forwardConfirmarOk(mapping, request, funcName, SeccionAdapter.NAME);
//			
//		} catch (Exception exception) {
//			return baseException(mapping, request, funcName, exception, SeccionAdapter.NAME);
//		}
//	}
//
//	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//
//		String funcName = DemodaUtil.currentMethodName();
//		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
//		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_SECCION, BaseSecurityConstants.ELIMINAR); 
//		if (userSession==null) return forwardErrorSession(request);
//		
//		try {
//			// Bajo el adapter del userSession
//			SeccionAdapter seccionAdapterVO = (SeccionAdapter) userSession.get(SeccionAdapter.NAME);
//			
//			// Si es nulo no se puede continuar
//			if (seccionAdapterVO == null) {
//				log.error("error en: "  + funcName + ": " + SeccionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
//				return forwardErrorSessionNullObject(mapping, request, funcName, SeccionAdapter.NAME); 
//			}
//
//			// llamada al servicio
//			SeccionVO seccionVO = ApmServiceLocator.getAplicacionService().deleteSeccion
//				(userSession, seccionAdapterVO.getSeccion());
//			
//            // Tiene errores recuperables
//			if (seccionVO.hasErrorRecoverable()) {
//				log.error("recoverable error en: "  + funcName + ": " + seccionAdapterVO.infoString());
//				saveDemodaErrors(request, seccionVO);				
//				request.setAttribute(SeccionAdapter.NAME, seccionAdapterVO);
//				return mapping.findForward(ApmConstants.FWD_SECCION_VIEW_ADAPTER);
//			}
//			
//			// Tiene errores no recuperables
//			if (seccionVO.hasErrorNonRecoverable()) {
//				log.error("error en: "  + funcName + ": " + seccionAdapterVO.errorString()); 
//				return forwardErrorNonRecoverable(mapping, request, funcName, SeccionAdapter.NAME, seccionAdapterVO);
//			}
//			
//			// Fue Exitoso
//			return forwardConfirmarOk(mapping, request, funcName, SeccionAdapter.NAME);
//			
//
//		} catch (Exception exception) {
//			return baseException(mapping, request, funcName, exception, SeccionAdapter.NAME);
//		}
//	}
	

} 