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
import ar.gov.rosario.gait.apm.iface.service.ApmServiceLocator;
import ar.gov.rosario.gait.apm.iface.util.ApmSecurityConstants;
import ar.gov.rosario.gait.apm.view.util.ApmConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.UserSession;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarCambioEstadoPanicoDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarPanicoDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PANICO, act); 
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		PanicoAdapter panicoAdapterVO = null;
		String stringServicio = "";

		ActionForward actionForward = null;
		try {
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			if (act.equals(PanicoAdapter.ACT_CAMBIARESTADO)) {
				stringServicio = "getPanicoAdapterForCambioEstado(userSession, commonKey)";
				panicoAdapterVO = ApmServiceLocator.getPanicoService().getPanicoAdapterForCambioEstado(userSession, commonKey);
				actionForward = mapping.findForward(ApmConstants.FWD_CAMBIOESTADO_PANICO_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (panicoAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + panicoAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, PanicoAdapter.CAMBIOESTADO_NAME, panicoAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			panicoAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + PanicoAdapter.CAMBIOESTADO_NAME + ": "+ panicoAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(PanicoAdapter.CAMBIOESTADO_NAME, panicoAdapterVO);
			// Subo el apdater al userSession
			userSession.put(PanicoAdapter.CAMBIOESTADO_NAME, panicoAdapterVO);
			 
			saveDemodaMessages(request, panicoAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PanicoAdapter.CAMBIOESTADO_NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return baseVolver(mapping, form, request, response, PanicoAdapter.CAMBIOESTADO_NAME);
	}
	
	public ActionForward cambiarEstado(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ApmSecurityConstants.ABM_PANICO, PanicoAdapter.ACT_CAMBIARESTADO); 
		if (userSession==null) return forwardErrorSession(request);
		try {
			// Bajo el adapter del userSession
			PanicoAdapter panicoAdapterVO = (PanicoAdapter) userSession.get(PanicoAdapter.CAMBIOESTADO_NAME);
			// Si es nulo no se puede continuar
			if (panicoAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + PanicoAdapter.CAMBIOESTADO_NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, PanicoAdapter.CAMBIOESTADO_NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(panicoAdapterVO, request);
						
			panicoAdapterVO = ApmServiceLocator.getPanicoService().cambiarEstadoPanico(userSession, panicoAdapterVO);
            // Tiene errores recuperables
			if (panicoAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + panicoAdapterVO.infoString()); 
				saveDemodaErrors(request, panicoAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, PanicoAdapter.CAMBIOESTADO_NAME, panicoAdapterVO);
			}			

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, PanicoAdapter.CAMBIOESTADO_NAME);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, PanicoAdapter.CAMBIOESTADO_NAME);
		}
	}
		
	public ActionForward refill(ActionMapping mapping, ActionForm form,	
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, PanicoAdapter.CAMBIOESTADO_NAME);			
	}
}