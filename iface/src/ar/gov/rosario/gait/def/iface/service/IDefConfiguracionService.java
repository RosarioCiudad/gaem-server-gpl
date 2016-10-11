package ar.gov.rosario.gait.def.iface.service;

import ar.gov.rosario.gait.apm.iface.model.TelefonoPanicoAdapter;
import ar.gov.rosario.gait.apm.iface.model.TelefonoPanicoVO;
import ar.gov.rosario.gait.def.iface.model.AreaAdapter;
import ar.gov.rosario.gait.def.iface.model.AreaAplicacionPerfilAdapter;
import ar.gov.rosario.gait.def.iface.model.AreaAplicacionPerfilVO;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.def.iface.model.DireccionAdapter;
import ar.gov.rosario.gait.def.iface.model.DireccionAplicacionPerfilAdapter;
import ar.gov.rosario.gait.def.iface.model.DireccionAplicacionPerfilVO;
import ar.gov.rosario.gait.def.iface.model.DireccionSearchPage;
import ar.gov.rosario.gait.def.iface.model.DireccionVO;
import ar.gov.rosario.gait.def.iface.model.ParametroAdapter;
import ar.gov.rosario.gait.def.iface.model.ParametroSearchPage;
import ar.gov.rosario.gait.def.iface.model.ParametroVO;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.UserContext;

public interface IDefConfiguracionService {
	
	// ---> ABM Parametro
	public ParametroSearchPage getParametroSearchPageInit(UserContext usercontext) throws DemodaServiceException;
	public ParametroSearchPage getParametroSearchPageResult(UserContext usercontext, ParametroSearchPage ParametroSearchPage) throws DemodaServiceException;

	public ParametroAdapter getParametroAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public ParametroAdapter getParametroAdapterForCreate(UserContext userContext) throws DemodaServiceException;
	public ParametroAdapter getParametroAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	
	public ParametroVO createParametro(UserContext userContext, ParametroVO ParametroVO ) throws DemodaServiceException;
	public ParametroVO updateParametro(UserContext userContext, ParametroVO ParametroVO ) throws DemodaServiceException;
	public ParametroVO deleteParametro(UserContext userContext, ParametroVO ParametroVO ) throws DemodaServiceException;
	
	public ParametroVO activarParametro(UserContext userContext, ParametroVO ParametroVO ) throws DemodaServiceException;
	public ParametroVO desactivarParametro(UserContext userContext, ParametroVO ParametroVO ) throws DemodaServiceException;
	// <--- ABM Parametro
	
	public void updateGaitParam()throws DemodaServiceException;
	public void initializeGait()throws DemodaServiceException;
	public void destroyGait()throws DemodaServiceException;
	public void refreshCache(String cacheFlag) throws DemodaServiceException;	
	
	// ---> ABM Direccion
	public DireccionSearchPage getDireccionSearchPageInit(UserContext usercontext) throws DemodaServiceException;
	public DireccionSearchPage getDireccionSearchPageResult(UserContext usercontext, DireccionSearchPage DireccionSearchPage) throws DemodaServiceException;

	public DireccionAdapter getDireccionAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public DireccionAdapter getDireccionAdapterForCreate(UserContext userContext) throws DemodaServiceException;
	public DireccionAdapter getDireccionAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	
	public DireccionVO createDireccion(UserContext userContext, DireccionVO direccionVO ) throws DemodaServiceException;
	public DireccionVO updateDireccion(UserContext userContext, DireccionVO direccionVO ) throws DemodaServiceException;
	public DireccionVO deleteDireccion(UserContext userContext, DireccionVO direccionVO ) throws DemodaServiceException;
	// <--- ABM Direccion
	
	// ---> ABM Area
	public AreaAdapter getAreaAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public AreaAdapter getAreaAdapterForCreate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public AreaAdapter getAreaAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	
	public AreaVO createArea(UserContext userContext, AreaVO areaVO ) throws DemodaServiceException;
	public AreaVO updateArea(UserContext userContext, AreaVO areaVO ) throws DemodaServiceException;
	public AreaVO deleteArea(UserContext userContext, AreaVO areaVO ) throws DemodaServiceException;
	// <--- ABM Area
	
	// ---> ABM TelefonoPanico
	public TelefonoPanicoAdapter getTelefonoPanicoAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public TelefonoPanicoAdapter getTelefonoPanicoAdapterForCreate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public TelefonoPanicoAdapter getTelefonoPanicoAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	
	public TelefonoPanicoVO createTelefonoPanico(UserContext userContext, TelefonoPanicoVO telefonoPanicoVO ) throws DemodaServiceException;
	public TelefonoPanicoVO updateTelefonoPanico(UserContext userContext, TelefonoPanicoVO telefonoPanicoVO ) throws DemodaServiceException;
	public TelefonoPanicoVO deleteTelefonoPanico(UserContext userContext, TelefonoPanicoVO telefonoPanicoVO ) throws DemodaServiceException;
	// <--- ABM TelefonoPanico
	
	// ---> ABM DireccionAplicacionPerfil
	public DireccionAplicacionPerfilAdapter getDireccionAplicacionPerfilAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public DireccionAplicacionPerfilAdapter getDireccionAplicacionPerfilAdapterForCreate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public DireccionAplicacionPerfilAdapter getDireccionAplicacionPerfilAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	
	public DireccionAplicacionPerfilVO createDireccionAplicacionPerfil(UserContext userContext, DireccionAplicacionPerfilVO direccionAplicacionPerfilVO ) throws DemodaServiceException;
	public DireccionAplicacionPerfilVO updateDireccionAplicacionPerfil(UserContext userContext, DireccionAplicacionPerfilVO direccionAplicacionPerfilVO ) throws DemodaServiceException;
	public DireccionAplicacionPerfilVO deleteDireccionAplicacionPerfil(UserContext userContext, DireccionAplicacionPerfilVO direccionAplicacionPerfilVO ) throws DemodaServiceException;
	// <--- ABM DireccionAplicacionPerfil
	
	// ----> APM AreaAplicacionPerfil
	public AreaAplicacionPerfilAdapter getAreaAplicacionPerfilAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public AreaAplicacionPerfilAdapter getAreaAplicacionPerfilAdapterForCreate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public AreaAplicacionPerfilAdapter getAreaAplicacionPerfilAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
	public AreaAplicacionPerfilVO createAreaAplicacionPerfil(UserContext userContext, AreaAplicacionPerfilVO areaAplicacionPerfilVO ) throws DemodaServiceException;
	public AreaAplicacionPerfilVO updateAreaAplicacionPerfil(UserContext userContext, AreaAplicacionPerfilVO areaAplicacionPerfilVO ) throws DemodaServiceException;
	public AreaAplicacionPerfilVO deleteAreaAplicacionPerfil(UserContext userContext, AreaAplicacionPerfilVO areaAplicacionPerfilVO ) throws DemodaServiceException;	
	// ----> APM AreaAplicacionPerfil
}