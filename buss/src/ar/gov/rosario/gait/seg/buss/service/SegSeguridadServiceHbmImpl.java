package ar.gov.rosario.gait.seg.buss.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.apm.buss.bean.UsuarioApm;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.iface.util.GaitCache;
import ar.gov.rosario.gait.def.buss.bean.Area;
import ar.gov.rosario.gait.def.buss.bean.Direccion;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.def.iface.model.DireccionVO;
import ar.gov.rosario.gait.seg.buss.bean.SegSeguridadManager;
import ar.gov.rosario.gait.seg.buss.bean.UsuarioGait;
import ar.gov.rosario.gait.seg.buss.dao.SegDAOFactory;
import ar.gov.rosario.gait.seg.iface.model.MenuAdapter;
import ar.gov.rosario.gait.seg.iface.model.UsuarioGaitAdapter;
import ar.gov.rosario.gait.seg.iface.model.UsuarioGaitSearchPage;
import ar.gov.rosario.gait.seg.iface.model.UsuarioGaitVO;
import ar.gov.rosario.gait.seg.iface.service.ISegSeguridadService;
import ar.gov.rosario.gait.seg.iface.util.SegError;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.http.ReplyException;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ModelUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.Estado;
import coop.tecso.demoda.iface.model.UserContext;
import coop.tecso.swe.iface.model.ItemMenuVO;
import coop.tecso.swe.iface.model.SweUserSession;
import coop.tecso.swe.iface.model.UsuarioVO;
/**
 * 
 * Implementacion de servicios de seguridad
 * @author tecso
 */


public class SegSeguridadServiceHbmImpl implements ISegSeguridadService { 
	private Logger log = Logger.getLogger(SegSeguridadServiceHbmImpl.class);

	public MenuAdapter getMenu(UserContext userContext, MenuAdapter menuAdapter) throws DemodaServiceException {
		boolean canContinue = true;

		Long idItemMenuNivel1 = menuAdapter.getIdItemMenuNivel1();
		Long idItemMenuNivel2 = menuAdapter.getIdItemMenuNivel2();

		try {
			SweUserSession sweTmp = new SweUserSession();
			sweTmp.setIdsAccionesModuloUsuario(userContext.getIdsAccionesModuloUsuario());
			sweTmp.setCodApplication("GAEM");

			List<ItemMenuVO> listItemMenuVOUsuario =  GaitCache.getInstance().getSweContext().getTreeMenu(sweTmp);

			// si se selecciono un item de nivel2 lleno la lista de nivel3
			if (!idItemMenuNivel2.equals(new Long(0))) {
				// recupero el itemMenu de nivel 2
				ItemMenuVO itemMenuNivel2 = this.getItemMenu( new CommonKey(idItemMenuNivel2), listItemMenuVOUsuario);
				menuAdapter.setListItemMenuNivel3 (itemMenuNivel2.getListItemMenuHijos());

				// Seteamos el titulo nivel 3 e item seleccionado nivel 2
				for(ItemMenuVO item : menuAdapter.getListItemMenuNivel2()) {
					item.setSeleccionadoView(false);
					if (item.getId().equals(menuAdapter.getIdItemMenuNivel2())) {
						menuAdapter.setTituloNivel3(item.getTitulo());
						item.setSeleccionadoView(true);
					}
				}
				canContinue = false;
			}

			if ( canContinue ) {
				// si se selecciono un item de nivel1 lleno la lista de nivel2 y borro la lista nivel 3
				if (!idItemMenuNivel1.equals(new Long(0))) {
					// recupero el itemMenu de nivel 1
					ItemMenuVO itemMenuNivel1 = this.getItemMenu( new CommonKey (idItemMenuNivel1), listItemMenuVOUsuario);
					menuAdapter.setListItemMenuNivel2 ( itemMenuNivel1.getListItemMenuHijos());
					menuAdapter.setListItemMenuNivel3(new ArrayList<ItemMenuVO>());

					// Seteamos titulo nivel 2 y item seleccionado nivel 1
					for(ItemMenuVO item : menuAdapter.getListItemMenuNivel1()) {
						item.setSeleccionadoView(false);
						if (item.getId().equals(menuAdapter.getIdItemMenuNivel1())) {
							menuAdapter.setTituloNivel2(item.getTitulo());
							item.setSeleccionadoView(true);
						}
					}				
					canContinue = false;
				}
			}

			if (canContinue) {
				// si no hay ningun nivel seleccionado lleno la lista de nivel 1 y borro la demas.
				if ( idItemMenuNivel1.equals(new Long(0)) ) {
					menuAdapter.setListItemMenuNivel1 (listItemMenuVOUsuario);
					menuAdapter.setListItemMenuNivel2(new ArrayList<ItemMenuVO>());
					menuAdapter.setListItemMenuNivel3(new ArrayList<ItemMenuVO>());
					canContinue = false;
				}
			}
			if(menuAdapter.getTituloNivel2().length()>16){
				menuAdapter.setTituloNivel2(menuAdapter.getTituloNivel2().substring(0, 16).concat("..."));
			}
			if(menuAdapter.getTituloNivel3().length()>36){
				menuAdapter.setTituloNivel3(menuAdapter.getTituloNivel3().substring(0, 36).concat("..."));
			}

			return menuAdapter;
		} catch (Exception e) {
			log.error("Service Exception: getMenu(): ",e);
			throw new DemodaServiceException(e);
		}
	}

	public ItemMenuVO getItemMenu(CommonKey itemMenuKey, List<ItemMenuVO> listItemMenuVOUsuario) throws DemodaServiceException {
		ItemMenuVO itemMenu = null; 
		try {
			//Iterator iterListItemMenu = this.listItemMenu.iterator();
			Iterator iterListItemMenu =  listItemMenuVOUsuario.iterator();
			while (iterListItemMenu.hasNext() && itemMenu == null) {
				ItemMenuVO im = (ItemMenuVO) iterListItemMenu.next();
				itemMenu = im.buscarItemById(itemMenuKey.getId());
			}
			return itemMenu;
		} catch (Exception e) {
			log.error("Service Exception: ", e);
			throw new DemodaServiceException(e);
		}
	}

	public String getMenu(String a, String b) throws DemodaServiceException {
		return "";
	}

	// ---> ABM UsuarioGait 	
	public UsuarioGaitSearchPage getUsuarioGaitSearchPageInit
	(UserContext usercontext) throws DemodaServiceException {		

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {

			GaitHibernateUtil.currentSession();			

			UsuarioGaitSearchPage usuarioGaitSearchPage = new UsuarioGaitSearchPage();
			// Direcciones
			usuarioGaitSearchPage.setListDireccion(
					ListUtilBean.toVO(Direccion.getListActivos(), 0, false, 
							new DireccionVO(-1, StringUtil.SELECT_OPCION_TODAS)));
			// Areas
			usuarioGaitSearchPage.setListArea(ListUtilBean.toVO(Area.getListActivos(), 0, false, 
					new AreaVO(-1, StringUtil.SELECT_OPCION_TODAS)));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return usuarioGaitSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioGaitSearchPage getUsuarioGaitSearchPageResult
	(UserContext userContext, UsuarioGaitSearchPage usuarioGaitSearchPage) throws DemodaServiceException {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			usuarioGaitSearchPage.clearError();

			// Aqui obtiene lista de BOs
			List<UsuarioGait> listUsuarioGait = SegDAOFactory.getUsuarioGaitDAO().getBySearchPage
					(usuarioGaitSearchPage);  
			//Aqui pasamos BO a VO
			usuarioGaitSearchPage.setListResult(ListUtilBean.toVO(listUsuarioGait,1));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return usuarioGaitSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioGaitAdapter getUsuarioGaitAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {

		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			UsuarioGait usuarioGait = UsuarioGait.getById(commonKey.getId());

			UsuarioGaitAdapter usuarioGaitAdapter = new UsuarioGaitAdapter();
			usuarioGaitAdapter.setUsuarioGait((UsuarioGaitVO) usuarioGait.toVO(1));

			log.debug(funcName + ": exit");
			return usuarioGaitAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	/**
	 * Obtiene datos de un usuario gait.
	 * Obtiene el Area y Oficina del Usuario.
	 * Si no posee Area, asigna el area por defecto.
	 * Si el area posee una sola oficina, asigna esa oficina
	 */
	public UsuarioGaitVO getUsuarioGaitForLogin(UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 
			UsuarioGaitVO usuarioGaitVO = new UsuarioGaitVO();

			//Buscamos usuario
			UsuarioGait usuarioGait = UsuarioGait.getByUserName(userContext.getUserName());
			if (usuarioGait == null) {
				usuarioGaitVO.addRecoverableError(SegError.NO_EXISTE_USUARIOGAIT);
				return usuarioGaitVO;
			} //else if(usuarioGait.getArea()==null){
			//usuarioGait.setArea(Area.getByCodigo(Area.COD_AREA_DEFAULT_GAIT));
			//}

			// toVO con su lista de oficinas.
			usuarioGaitVO = (UsuarioGaitVO) usuarioGait.toVO(2, true);

			log.debug(funcName + ": exit");
			return usuarioGaitVO;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioGaitAdapter getUsuarioGaitAdapterForCreate(UserContext userContext) 
			throws DemodaServiceException {

		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			UsuarioGaitAdapter usuarioGaitAdapter = new UsuarioGaitAdapter();

			usuarioGaitAdapter.setListDireccion(
					ListUtilBean.toVO(Direccion.getListActivos(), 0, false, 
							new DireccionVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			usuarioGaitAdapter.setListArea(Arrays.asList(new AreaVO(-1, StringUtil.NO_POSEE_DESCRIPCION)));

			log.debug(funcName + ": exit");
			return usuarioGaitAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public UsuarioGaitAdapter getUsuarioGaitAdapterForUpdate(UserContext userContext, CommonKey commonKeyUsuarioGait) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			UsuarioGait usuarioGait = UsuarioGait.getById(commonKeyUsuarioGait.getId());

			UsuarioGaitAdapter usuarioGaitAdapter = new UsuarioGaitAdapter();
			usuarioGaitAdapter.setUsuarioGait((UsuarioGaitVO) usuarioGait.toVO(1, false));

			usuarioGaitAdapter.setListDireccion(ListUtilBean.toVO(Direccion.getListActivos(), 0, false));
			usuarioGaitAdapter.setListArea(ListUtilBean.toVO(usuarioGait.getDireccion().getListArea(), 0, false));

			log.debug(funcName + ": exit");
			return usuarioGaitAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioGaitAdapter getUsuarioGaitAdapterParam(UserContext userContext, UsuarioGaitAdapter usuarioGaitAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			usuarioGaitAdapter.clearError();

			UsuarioGaitVO usuarioGait = usuarioGaitAdapter.getUsuarioGait();
			// Logica del param
			if(ModelUtil.isNullOrEmpty(usuarioGait.getDireccion())){
				usuarioGaitAdapter.setListArea(Arrays.asList(new AreaVO(-1, StringUtil.NO_POSEE_DESCRIPCION)));
			}else{
				Direccion direccion = Direccion.getById(usuarioGait.getDireccion().getId());
				usuarioGaitAdapter.setListArea(ListUtilBean.toVO(direccion.getListArea(), 0, false));
			}

			log.debug(funcName + ": exit");
			return usuarioGaitAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public UsuarioGaitVO createUsuarioGait(UserContext userContext, UsuarioGaitVO usuarioGaitVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			usuarioGaitVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			UsuarioGait usuarioGait = new UsuarioGait();
			usuarioGait.setUsuarioGAIT(usuarioGaitVO.getUsuarioGAIT());
			usuarioGait.setArea(Area.getByIdNull(usuarioGaitVO.getArea().getId()));
			usuarioGait.setDireccion(Direccion.getByIdNull(usuarioGaitVO.getDireccion().getId()));

			usuarioGait.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			usuarioGait = SegSeguridadManager.getInstance().createUsuarioGait(usuarioGait);

			if (usuarioGait.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				usuarioGaitVO =  (UsuarioGaitVO) usuarioGait.toVO(3);
			}
			usuarioGait.passErrorMessages(usuarioGaitVO);

			log.debug(funcName + ": exit");
			return usuarioGaitVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioGaitVO updateUsuarioGait(UserContext userContext, UsuarioGaitVO usuarioGaitVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			usuarioGaitVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			UsuarioGait usuarioGait = UsuarioGait.getById(usuarioGaitVO.getId());

			if(!usuarioGaitVO.validateVersion(usuarioGait.getFechaUltMdf())) return usuarioGaitVO;

			usuarioGait.setEstado(Estado.ACTIVO.getId());
			usuarioGait.setArea(Area.getByIdNull(usuarioGaitVO.getArea().getId()));
			usuarioGait.setDireccion(Direccion.getByIdNull(usuarioGaitVO.getDireccion().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			usuarioGait = SegSeguridadManager.getInstance().updateUsuarioGait(usuarioGait);

			if (usuarioGait.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				usuarioGaitVO =  (UsuarioGaitVO) usuarioGait.toVO(3);
			}
			usuarioGait.passErrorMessages(usuarioGaitVO);

			log.debug(funcName + ": exit");
			return usuarioGaitVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioGaitVO deleteUsuarioGait(UserContext userContext, UsuarioGaitVO usuarioGaitVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			usuarioGaitVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			UsuarioGait usuarioGait = UsuarioGait.getById(usuarioGaitVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad de otro bean
			usuarioGait = SegSeguridadManager.getInstance().deleteUsuarioGait(usuarioGait);

			if (usuarioGait.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				usuarioGaitVO =  (UsuarioGaitVO) usuarioGait.toVO(3);
			}
			usuarioGait.passErrorMessages(usuarioGaitVO);

			log.debug(funcName + ": exit");
			return usuarioGaitVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			try { tx.rollback(); } catch (Exception ee) {}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioGaitVO activarUsuarioGait(UserContext userContext, UsuarioGaitVO usuarioGaitVO ) throws DemodaServiceException{
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx  = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();

			UsuarioGait usuarioGait = UsuarioGait.getById(usuarioGaitVO.getId());

			usuarioGait.activar();

			if (usuarioGait.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				usuarioGaitVO =  (UsuarioGaitVO) usuarioGait.toVO();
			}
			usuarioGait.passErrorMessages(usuarioGaitVO);

			log.debug(funcName + ": exit");
			return usuarioGaitVO;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioGaitVO desactivarUsuarioGait(UserContext userContext, UsuarioGaitVO usuarioGaitVO ) throws DemodaServiceException{
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx  = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();

			UsuarioGait usuarioGait = UsuarioGait.getById(usuarioGaitVO.getId());

			usuarioGait.desactivar();

			if (usuarioGait.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				usuarioGaitVO =  (UsuarioGaitVO) usuarioGait.toVO();
			}
			usuarioGait.passErrorMessages(usuarioGaitVO);

			log.debug(funcName + ": exit");
			return usuarioGaitVO;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}	

	public void grabarUltimoLoginUsuarioGait(UserContext userContext) throws DemodaServiceException{
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx  = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();

			UsuarioGait usuarioGait = UsuarioGait.getByUserName(userContext.getUserName());
			if (usuarioGait != null) {
				usuarioGait.setFechaUltLogin(new Date());

				SegDAOFactory.getUsuarioGaitDAO().update(usuarioGait);
			}

			if (usuarioGait == null || usuarioGait.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
			}

			log.debug(funcName + ": exit");
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			if(tx != null) tx.rollback();
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
	// <--- ABM UsuarioGait

	public UsuarioVO changePass(UserContext userSession, UsuarioVO userVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {

			log.debug(funcName + ": exit");

			return SegSeguridadManager.getInstance().changePassword(userSession, userVO);

		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		}
	}

	@Override
	public UserContext initUserApm(String username, String usertoken) {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			GaitHibernateUtil.currentSession();
			//
			if(!UsuarioApm.canAccess(username, usertoken)){
				throw new ReplyException(403, "Usuario inhabilitado para el acceso.");
			}

			UserContext userContext = new UserContext();
			userContext.setUserName(username);
			//uc.setSessionToken(stoken);
			log.debug(funcName + ": exit");
			return userContext;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new ReplyException(403, "Error Inesperado.");
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

}
