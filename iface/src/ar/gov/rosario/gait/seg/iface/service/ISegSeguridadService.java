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
package ar.gov.rosario.gait.seg.iface.service;

import java.util.List;

import ar.gov.rosario.gait.seg.iface.model.MenuAdapter;
import ar.gov.rosario.gait.seg.iface.model.UsuarioGaitAdapter;
import ar.gov.rosario.gait.seg.iface.model.UsuarioGaitSearchPage;
import ar.gov.rosario.gait.seg.iface.model.UsuarioGaitVO;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.UserContext;
import coop.tecso.swe.iface.model.ItemMenuVO;
import coop.tecso.swe.iface.model.UsuarioVO;

public interface ISegSeguridadService {
	
	public MenuAdapter getMenu(UserContext userContext, MenuAdapter menuAdapter ) throws DemodaServiceException;
	public ItemMenuVO getItemMenu(CommonKey itemMenuKey, List<ItemMenuVO> listItemMenuVOUsuario) throws DemodaServiceException;
	public UsuarioGaitVO getUsuarioGaitForLogin(UserContext userContext) throws DemodaServiceException;
	public UsuarioVO changePass(UserContext userSession, UsuarioVO userVO) throws DemodaServiceException;
	public UserContext initUserApm(String user, String stoken);

	// ---> ABM UsuarioGait
	public UsuarioGaitSearchPage getUsuarioGaitSearchPageInit(UserContext usercontext) throws DemodaServiceException;
	public UsuarioGaitSearchPage getUsuarioGaitSearchPageResult(UserContext usercontext, UsuarioGaitSearchPage usuarioGaitSearchPage) throws DemodaServiceException;

	public UsuarioGaitAdapter getUsuarioGaitAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public UsuarioGaitAdapter getUsuarioGaitAdapterForCreate(UserContext userContext) throws DemodaServiceException;
	public UsuarioGaitAdapter getUsuarioGaitAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public UsuarioGaitAdapter getUsuarioGaitAdapterParam(UserContext userContext, UsuarioGaitAdapter usuarioGaitAdapter) throws DemodaServiceException;

	public UsuarioGaitVO createUsuarioGait(UserContext userContext, UsuarioGaitVO usuarioGaitVO ) throws DemodaServiceException;
	public UsuarioGaitVO updateUsuarioGait(UserContext userContext, UsuarioGaitVO usuarioGaitVO ) throws DemodaServiceException;
	public UsuarioGaitVO deleteUsuarioGait(UserContext userContext, UsuarioGaitVO usuarioGaitVO ) throws DemodaServiceException;
	public UsuarioGaitVO activarUsuarioGait(UserContext userContext, UsuarioGaitVO usuarioGaitVO ) throws DemodaServiceException;
	public UsuarioGaitVO desactivarUsuarioGait(UserContext userContext, UsuarioGaitVO usuarioGaitVO ) throws DemodaServiceException;
	
	public void grabarUltimoLoginUsuarioGait(UserContext userContext) throws DemodaServiceException;
	// <--- ABM UsuarioGait


}
