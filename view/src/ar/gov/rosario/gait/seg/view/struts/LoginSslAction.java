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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta clase se utiliza para manejar los request relacionados con el manejo de Login y Logout que utilizan SSL
 * Esta clase solo hace publico algunos metodos de LoginGait. En especial los metodos que pueden ser llamados utilizando SSL
 * Los metodos que son usados SIN SSL, estan en LoginAction
*/
public final class LoginSslAction extends LoginGait {
	
	private Logger log = Logger.getLogger((LoginSslAction.class));
	
	/**
	 * Hace publico el metodo para obtner el formulario de ingreso de login via WEB
	 * Este metodo solo funciona si se trata de webGait.war
	 * Si es ejecutado apuntando a intraGait.war retorna error 
	 */
	public ActionForward webInit(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		return super.init(mapping, form, request, response, LoginGait.FROM_WEB_LOGIN);
	}
	
	/**
	 * Hace publico el metodo para autentica usuarios desde la web
	 * Este metodo solo funciona si se trata de webGait.war
	 * Si es ejecutado apuntando a intraGait.war retorna error 
	 */
	public ActionForward webLogin(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		return super.login(mapping, form, request, response, LoginGait.FROM_WEB_LOGIN);
	}
	
	/**
	 * Hace publico el metodo para mostrar el formulario de ingreso a gait desde la intranet.
	 * Este metodo solo funciona si se trata de intraGait.war
	 * Si es ejecutado apuntando a webGait.war retorna error 
	 */
	public ActionForward intranetInit(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		return super.init(mapping, form, request, response, LoginGait.FROM_INTRANET_LOGIN);
	}
	
	/**
	 * Hace publico el metodo para autentica usuarios desde la intraner
	 * Este metodo solo funciona si se trata de intraGait.war
	 * Si es ejecutado apuntando a webGait.war retorna error 
	 */
	public ActionForward intranetLogin(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
	throws Exception {
		return super.login(mapping, form, request, response, LoginGait.FROM_INTRANET_LOGIN);
	}
	
	public ActionForward changePassInit(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {
			return super.changePassInit(mapping, form, request, response);
	}
	
	public ActionForward changePass(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {
			return super.changePass(mapping, form, request, response);
	}
	
	public ActionForward volver(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {
			return super.volver(mapping, form, request, response);
	}
}
