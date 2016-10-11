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
