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
package ar.gov.rosario.gait.seg.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.iface.model.GaitParam;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.seg.iface.model.MenuAdapter;
import ar.gov.rosario.gait.seg.iface.service.SegServiceLocator;
import ar.gov.rosario.gait.seg.view.util.SegConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.NavModel;
import coop.tecso.swe.iface.model.ItemMenuVO;

public final class GaitMenuAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger((GaitMenuAction.class));
	
	public ActionForward build(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String funcName = "build()";
		HttpSession session = request.getSession();
		
		log.debug(funcName + ": enter");
		
		//Limpiamos el user session
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		if (userSession != null) {
			MenuAdapter menuAdapter = (MenuAdapter) userSession.get(MenuAdapter.NAME);
			userSession.getUserMap().clear();
			userSession.put(MenuAdapter.NAME, menuAdapter);
		}

		session = canAccess(request); 
		if (session == null) return forwardErrorSession(mapping, request);
		
		try {
			userSession = (UserSession) session.getAttribute("userSession");
			if (userSession == null) return forwardErrorSession(mapping, request);
			
			// Permitimos "apagar" la aplicacion por instancia
			if (GaitParam.isWebGait() && "0".equals(GaitParam.getString("webGaitOn")) && !userSession.getEsAdmin()){
				return mapping.findForward(BaseConstants.FWD_GAIT_OFFLINE);
			}
			if (GaitParam.isIntranetGait() && "0".equals(GaitParam.getString("intraGaitOn")) && !userSession.getEsAdmin()){
				return mapping.findForward(BaseConstants.FWD_GAIT_OFFLINE);
			}
			
			NavModel navModel = new NavModel();
			navModel.setPrevAction(mapping.getPath());

			// establecemos cadena de usuario logueado
			userSession.setNavModel(navModel);

			MenuAdapter menuAdapter = (MenuAdapter) userSession.get(MenuAdapter.NAME);
			if (menuAdapter == null) {
				// establecemos menu de usuario
				menuAdapter = new MenuAdapter();
				menuAdapter = SegServiceLocator.getSeguridadService().getMenu(userSession, menuAdapter);
			}

			userSession.put(MenuAdapter.NAME, menuAdapter);
			request.setAttribute(MenuAdapter.NAME, menuAdapter);
			menuAdapter.setValuesFromNavModel(navModel);
			
			// actualiza en la session el id del menu activo
			userSession.setIdMenuSession(MenuAdapter.ID_MENU_PPAL);

			return mapping.findForward(SegConstants.FWD_MENUFORM);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, MenuAdapter.NAME);
		}
	}

	public ActionForward select(ActionMapping mapping, ActionForm form, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		HttpSession session = canAccess(request); 
		if (session==null) return forwardErrorSession(mapping, request);
		
		String funcName = "select()";
		log.debug(funcName + ": enter");
		
		try {
			UserSession userSession = (UserSession)session.getAttribute("userSession");
			if (userSession==null) return forwardErrorSession(mapping, request);
			
			
			// Permitimos "apagar" la aplicacion por instancia
			if (GaitParam.isWebGait() && "0".equals(GaitParam.getString("webGaitOn")) && !userSession.getEsAdmin()){
				return mapping.findForward(BaseConstants.FWD_GAIT_OFFLINE);
			}
			if (GaitParam.isIntranetGait() && "0".equals(GaitParam.getString("intraGaitOn")) && !userSession.getEsAdmin()){
				return mapping.findForward(BaseConstants.FWD_GAIT_OFFLINE);
			}

			
			NavModel navModel = new NavModel();
			navModel.setPrevAction(mapping.getPath());
			navModel.setPrevActionParameter("build");
			navModel.setAct("buscar");			
			userSession.setNavModel(navModel);		
			
			// establecemos menu de usuario
			MenuAdapter menuAdapter = (MenuAdapter) userSession.get(MenuAdapter.NAME);
			
			//limpiamos los id seleccionados previos de la session, y tomamos los nuevos del request.
			menuAdapter.setIdAccionModulo(new Long(0));
			menuAdapter.setIdItemMenuNivel1(new Long(0));
			menuAdapter.setIdItemMenuNivel2(new Long(0));
			menuAdapter.setIdItemMenuNivel3(new Long(0));
			
			//compiamos valores del request
			DemodaUtil.populateVO(menuAdapter, request);

			// si el item de menu tiene asociada accion,
			// buscamos item de ultimo nivel submitido
			if (menuAdapter.getIdAccionModulo() != null && menuAdapter.getIdAccionModulo() != 0L) {
				ItemMenuVO itemMenu = menuAdapter.findItemMenu(menuAdapter.getIdItemMenuNivel3());
				if (itemMenu == null) {
					itemMenu = menuAdapter.findItemMenu(menuAdapter.getIdItemMenuNivel2());
				}
				if (itemMenu == null) {
					itemMenu = menuAdapter.findItemMenu(menuAdapter.getIdItemMenuNivel1());
				}
				if (itemMenu == null || itemMenu.getUrl() == null || itemMenu.getUrl().equals("")) {
					userSession.put(MenuAdapter.NAME, menuAdapter);
					request.setAttribute(MenuAdapter.NAME, menuAdapter);
					return (mapping.findForward(SegConstants.FWD_MENUFORM));
				}
				log.info("GaitMenuAction.select(): usuario:" + userSession.getUserName() + 
						 " url:" + itemMenu.getUrl());
				userSession.setUrlReComenzar(itemMenu.getUrl());
				request.setAttribute("vieneDe", "menu");
				return new ActionForward(itemMenu.getUrl());
			}

			// si me pasan un idAccModApl, buscamos los datos de la accion
			// y forwardeo a MenuAccionAction method accionDefault
			/*
			if (menuAdapter.getIdAccionModulo() != null &&
					menuAdapter.getIdAccionModulo().intValue() != 0 ) {
				AccModAplVO selectedAccionModulo = menuAdapter.findAccionModulo(menuAdapter.getIdAccionModulo());
				if (selectedAccionModulo == null) {
					return (mapping.findForward(SeguridadConstants.FWD_MENUFORM));
				}
				
				String path = selectedAccionModulo.getNombreAccion() + ".do?method=" + selectedAccionModulo.getNombreMetodo();
				log.info("GaitMenuAction.select(): usuario:" + userSession.getUserName() + " path:" + path);
				return new ActionForward(path);
			}
			*/

			menuAdapter = SegServiceLocator.getSeguridadService().getMenu(userSession, menuAdapter);
			userSession.put(MenuAdapter.NAME, menuAdapter);
			request.setAttribute(MenuAdapter.NAME, menuAdapter);

			return mapping.findForward(SegConstants.FWD_MENUFORM);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, MenuAdapter.NAME);
		}
	}

}
