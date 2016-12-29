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
package ar.gov.rosario.gait.def.buss.service;

import java.io.File;
import java.security.Security;
import java.util.List;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.gov.rosario.gait.apm.buss.bean.AplicacionPerfil;
import ar.gov.rosario.gait.apm.buss.bean.PerfilAcceso;
import ar.gov.rosario.gait.apm.buss.bean.TelefonoPanico;
import ar.gov.rosario.gait.apm.iface.model.AplicacionParametroVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoVO;
import ar.gov.rosario.gait.apm.iface.model.TelefonoPanicoAdapter;
import ar.gov.rosario.gait.apm.iface.model.TelefonoPanicoVO;
import ar.gov.rosario.gait.base.buss.Routes;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.iface.model.GaitParam;
import ar.gov.rosario.gait.base.iface.util.GaitCache;
import ar.gov.rosario.gait.def.buss.bean.Area;
import ar.gov.rosario.gait.def.buss.bean.AreaAplicacionPerfil;
import ar.gov.rosario.gait.def.buss.bean.DefConfiguracionManager;
import ar.gov.rosario.gait.def.buss.bean.Direccion;
import ar.gov.rosario.gait.def.buss.bean.DireccionAplicacionPerfil;
import ar.gov.rosario.gait.def.buss.bean.Parametro;
import ar.gov.rosario.gait.def.buss.dao.DefDAOFactory;
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
import ar.gov.rosario.gait.def.iface.service.IDefConfiguracionService;
import ar.gov.rosario.gait.def.iface.util.DefError;
import ar.gov.rosario.gait.pro.buss.AdpHandler;
import ar.gov.rosario.gait.pro.buss.bean.Proceso;
import ar.gov.rosario.gait.pro.buss.dao.ProDAOFactory;
import coop.tecso.adpcore.AdpEngine;
import coop.tecso.adpcore.AdpRunDirEnum;
import coop.tecso.adpcore.engine.AdpDao;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DemodaTimer;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.Estado;
import coop.tecso.demoda.iface.model.SiNo;
import coop.tecso.demoda.iface.model.UserContext;
import coop.tecso.grs.Grs;
import coop.tecso.swe.SweServiceLocator;
import coop.tecso.swe.iface.model.SweContext;
import coop.tecso.swe.iface.service.ISweService;

/**
 * Implementacion de servicios del submodulo Configuracion del modulo Definicion
 * 
 * @author tecso
 */

public class DefConfiguracionServiceHbmImpl implements IDefConfiguracionService {
	private Logger log = Logger.getLogger(DefConfiguracionServiceHbmImpl.class);

	// ---> ABM Parametro
	public ParametroSearchPage getParametroSearchPageInit(
			UserContext userContext) throws DemodaServiceException {
		return new ParametroSearchPage();
	}

	public ParametroSearchPage getParametroSearchPageResult(
			UserContext userContext, ParametroSearchPage parametroSearchPage)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			parametroSearchPage.clearError();

			// Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<Parametro> listParametro = DefDAOFactory.getParametroDAO()
					.getBySearchPage(parametroSearchPage);

			// Aqui se podria iterar la lista de BO para setear banderas en VOs
			// y otras cosas del negocio.

			// Aqui pasamos BO a VO
			parametroSearchPage.setListResult(ListUtilBean.toVO(listParametro,
					0));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return parametroSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ParametroAdapter getParametroAdapterForView(UserContext userContext,
			CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Parametro parametro = Parametro.getById(commonKey.getId());

			ParametroAdapter parametroAdapter = new ParametroAdapter();
			parametroAdapter.setParametro((ParametroVO) parametro.toVO(1));

			log.debug(funcName + ": exit");
			return parametroAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ParametroAdapter getParametroAdapterForCreate(UserContext userContext)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			ParametroAdapter parametroAdapter = new ParametroAdapter();

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return parametroAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ParametroAdapter getParametroAdapterParam(UserContext userContext,
			ParametroAdapter parametroAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			parametroAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return parametroAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ParametroAdapter getParametroAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyParametro)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Parametro parametro = Parametro.getById(commonKeyParametro.getId());

			ParametroAdapter parametroAdapter = new ParametroAdapter();
			parametroAdapter.setParametro((ParametroVO) parametro.toVO(1));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return parametroAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ParametroVO createParametro(UserContext userContext,
			ParametroVO parametroVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			parametroVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Parametro parametro = new Parametro();
			parametro.setCodParam(parametroVO.getCodParam());
			parametro.setDesParam(parametroVO.getDesParam());
			parametro.setValor(parametroVO.getValor());

			parametro.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			parametro = DefConfiguracionManager.getInstance().createParametro(
					parametro);

			if (parametro.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				parametroVO = (ParametroVO) parametro.toVO(3);
			}
			parametro.passErrorMessages(parametroVO);

			log.debug(funcName + ": exit");
			return parametroVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ParametroVO updateParametro(UserContext userContext,
			ParametroVO parametroVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			parametroVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Parametro parametro = Parametro.getById(parametroVO.getId());

			if (!parametroVO.validateVersion(parametro.getFechaUltMdf()))
				return parametroVO;

			parametro.setCodParam(parametroVO.getCodParam());
			parametro.setDesParam(parametroVO.getDesParam());
			parametro.setValor(parametroVO.getValor());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			parametro = DefConfiguracionManager.getInstance().updateParametro(	parametro);

			if (parametro.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				parametroVO = (ParametroVO) parametro.toVO(3);
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				// Actualizamos cache de parametros
				DefConfiguracionManager.getInstance().updateGaitParam();
			}
			parametro.passErrorMessages(parametroVO);

			log.debug(funcName + ": exit");
			return parametroVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ParametroVO deleteParametro(UserContext userContext,
			ParametroVO parametroVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			parametroVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			Parametro parametro = Parametro.getById(parametroVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			parametro = DefConfiguracionManager.getInstance().deleteParametro(
					parametro);

			if (parametro.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				parametroVO = (ParametroVO) parametro.toVO(3);
			}
			parametro.passErrorMessages(parametroVO);

			log.debug(funcName + ": exit");
			return parametroVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			try {
				tx.rollback();
			} catch (Exception ee) {
			}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ParametroVO activarParametro(UserContext userContext,
			ParametroVO parametroVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();

			Parametro parametro = Parametro.getById(parametroVO.getId());

			parametro.activar();

			if (parametro.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				parametroVO = (ParametroVO) parametro.toVO();
			}
			parametro.passErrorMessages(parametroVO);

			log.debug(funcName + ": exit");
			return parametroVO;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ParametroVO desactivarParametro(UserContext userContext,
			ParametroVO parametroVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();

			Parametro parametro = Parametro.getById(parametroVO.getId());

			parametro.desactivar();

			if (parametro.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				parametroVO = (ParametroVO) parametro.toVO();
			}
			parametro.passErrorMessages(parametroVO);

			log.debug(funcName + ": exit");
			return parametroVO;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	// <--- ABM Parametro

	// ----> Incializacion / Destruccion de GAIT
	public void updateGaitParam() throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			GaitHibernateUtil.currentSession();

			/*
			 * Throwable e = new Exception().fillInStackTrace(); log.debug(
			 * funcName + ": ", e);
			 */

			DefConfiguracionManager.getInstance().updateGaitParam();

			log.debug(funcName + ": exit");
			return;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public void initializeGait() throws DemodaServiceException {
		try {
			System.out.println("Iniciando AdoDao");
			AdpDao.DATASOURCE_CONTEXT = "java:comp/env/ds/gait";

			System.out.println("Iniciando SWE Context");
			DemodaTimer dt = new DemodaTimer();
			if (GaitCache.getInstance().getSweContext() == null) {
				ISweService sweService = SweServiceLocator.getSweService();
				SweContext sweContext = sweService.getSweContext("GAEM");
				GaitCache.getInstance().setSweContext(sweContext);
			}
			long swetime = dt.stop();
			// Actualiza el contenido del mapa de los parametros del singleton
			// GaitParam en Iface
			System.out.println("Iniciando Tabla de Parametros");
			updateGaitParam();
			long paramtime = dt.stop();

			System.out
			.println("Iniciando Estructura de directorios de Procesps de Adp.");
			initFileShareDirs();
			long dirstime = dt.stop();
			
			System.out.println("Iniciando Grs.");
			Grs.GrsPath = new File(GaitParam.getFileSharePath(), "reportes")
			.getPath();
			Grs.GrsPageTemplate = "/Grs.do";
			Grs.FmtForcePoint = true;
			Grs.SrcEncoding = "ISO-8859-1";
			Grs.HtmlEncoding = "ISO-8859-1";
			long grstime = dt.stop();

			System.out.println("Iniciando Adp Handler");
			AdpEngine.setclientHandler(new AdpHandler());
			
			System.out.println("Iniciando Rutas Rest.");
			Routes.init();
			System.out.println("Cargando BouncyCastleProvider.");
			Security.addProvider(new BouncyCastleProvider());
			
			//--
			//System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
			//--

			String summary = "\nGAIT startup summary:\n";
			summary += "swe:" + swetime + "ms\n";
			summary += "parametros:" + paramtime + "ms\n";
			summary += "directorios:" + dirstime + "ms\n";
			summary += "grs:" + grstime + "ms\n";
			summary += "total:" + (swetime + paramtime + dirstime) + "(ms)\n";
			log.info(summary);
		} catch (Exception e) {
			throw new DemodaServiceException("Error al inicializar gait.", e);
		}
	}

	public void destroyGait() throws DemodaServiceException {
		GaitHibernateUtil.closeSessionFactory();
		SweServiceLocator.getSweService().destroySwe();
	}

	public void initFileShareDirs() throws Exception {
		// crea la estructura definida por los procesos
		File dir = null;
		@SuppressWarnings({ "unchecked" })
		List<Proceso> listProceso = ProDAOFactory.getProcesoDAO()
		.getListActiva();
		for (Proceso proceso : listProceso) {
			String di = proceso.getDirectorioInput();
			if (di == null || di.equals("null") || di.trim().equals("")) {
				continue;
			}

			for (AdpRunDirEnum e : AdpRunDirEnum.values()) {
				dir = new File(proceso.getDirectorioInput() + "/" + e.dirName());
				if (!dir.exists() && !dir.mkdirs()) {
					log.error("No se pudo crear el directorio: "
							+ dir.getPath());
				}
			}
		}

		// crea directorio temporal
		dir = new File(GaitParam.getFileSharePath() + "/publico/tmp");
		if (!dir.exists() && !dir.mkdirs()) {
			log.error("No se pudo crear el directorio: " + dir.getPath());
		}

		// Estructura de reportes
		dir = new File(GaitParam.getFileSharePath()
				+ "/publico/general/reportes/db");
		if (!dir.exists() && !dir.mkdirs()) {
			log.error("No se pudo crear el directorio: " + dir.getPath());
		}
		dir = new File(GaitParam.getFileSharePath()
				+ "/publico/general/reportes/images");
		if (!dir.exists() && !dir.mkdirs()) {
			log.error("No se pudo crear el directorio: " + dir.getPath());
		}
	}

	/***
	 * Refresca todos los caches
	 */
	public void refreshCache(String cacheFlag) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			GaitHibernateUtil.currentSession();

			if (cacheFlag == null || cacheFlag.equals(DefError.CACHE_SWE)) {
				log.info("Refrescando cachede  Seguridad SWE");
				SweContext sweContext = SweServiceLocator.getSweService()
						.getSweContext("GAEM");
				GaitCache.getInstance().setSweContext(sweContext);
			}

			if (cacheFlag == null || cacheFlag.equals(DefError.CACHE_PARAMETRO)) {
				log.info("Refrescando cache de Parametros del Gait.");
				updateGaitParam();
			}

		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	// <----- Incializacion / Destruccion de GAIT

	// ---> ABM Direccion
	public DireccionSearchPage getDireccionSearchPageInit(
			UserContext userContext) throws DemodaServiceException {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		DireccionSearchPage direccionSearchPage = new DireccionSearchPage();
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			// Inicializar combos
			direccionSearchPage.setListSiNo(SiNo.getList(SiNo.OpcionTodo));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");

		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}

		return direccionSearchPage;
	}

	public DireccionSearchPage getDireccionSearchPageResult(
			UserContext userContext, DireccionSearchPage direccionSearchPage)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			direccionSearchPage.clearError();

			// Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<Direccion> listDireccion = DefDAOFactory.getDireccionDAO()
					.getBySearchPage(direccionSearchPage);

			// Aqui se podria iterar la lista de BO para setear banderas en VOs
			// y otras cosas del negocio.

			// Aqui pasamos BO a VO
			direccionSearchPage.setListResult(ListUtilBean.toVO(listDireccion,
					0));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return direccionSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DireccionAdapter getDireccionAdapterForView(UserContext userContext,
			CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Direccion direccion = Direccion.getById(commonKey.getId());

			DireccionAdapter direccionAdapter = new DireccionAdapter();
			direccionAdapter.setDireccion((DireccionVO) direccion.toVO(2));

			log.debug(funcName + ": exit");
			return direccionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DireccionAdapter getDireccionAdapterForCreate(UserContext userContext)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			DireccionAdapter direccionAdapter = new DireccionAdapter();

			direccionAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));
			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return direccionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DireccionAdapter getDireccionAdapterParam(UserContext userContext,
			DireccionAdapter direccionAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			direccionAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return direccionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DireccionAdapter getDireccionAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyDireccion)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Direccion direccion = Direccion.getById(commonKeyDireccion.getId());

			DireccionAdapter direccionAdapter = new DireccionAdapter();
			direccionAdapter.setDireccion((DireccionVO) direccion.toVO(2));
			direccionAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));
			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return direccionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DireccionVO createDireccion(UserContext userContext,
			DireccionVO direccionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			direccionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Direccion direccion = new Direccion();
			direccion.setDescripcion(direccionVO.getDescripcion());
			direccion.setEsDGI(direccionVO.getEsDGI().getBussId());

			// direccion.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			direccion = DefConfiguracionManager.getInstance().createDireccion(
					direccion);

			if (direccion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				direccionVO = (DireccionVO) direccion.toVO(3);
			}
			direccion.passErrorMessages(direccionVO);

			log.debug(funcName + ": exit");
			return direccionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DireccionVO updateDireccion(UserContext userContext,
			DireccionVO direccionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			direccionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Direccion direccion = Direccion.getById(direccionVO.getId());

			if (!direccionVO.validateVersion(direccion.getFechaUltMdf()))
				return direccionVO;

			direccion.setDescripcion(direccionVO.getDescripcion());
			direccion.setEsDGI(direccionVO.getEsDGI().getBussId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			direccion = DefConfiguracionManager.getInstance().updateDireccion(
					direccion);

			if (direccion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				direccionVO = (DireccionVO) direccion.toVO(3);
			}
			direccion.passErrorMessages(direccionVO);

			log.debug(funcName + ": exit");
			return direccionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DireccionVO deleteDireccion(UserContext userContext,
			DireccionVO direccionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			direccionVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			Direccion direccion = Direccion.getById(direccionVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			direccion = DefConfiguracionManager.getInstance().deleteDireccion(
					direccion);

			if (direccion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				direccionVO = (DireccionVO) direccion.toVO(3);
			}
			direccion.passErrorMessages(direccionVO);

			log.debug(funcName + ": exit");
			return direccionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			try {
				tx.rollback();
			} catch (Exception ee) {
			}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	// <------Area
	public AreaAdapter getAreaAdapterForView(UserContext userContext,
			CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Area area = Area.getById(commonKey.getId());

			AreaAdapter areaAdapter = new AreaAdapter();
			areaAdapter.setArea((AreaVO) area.toVO(1,false));

			log.debug(funcName + ": exit");
			return areaAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AreaAdapter getAreaAdapterForCreate(UserContext userContext, 
			CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AreaAdapter areaAdapter = new AreaAdapter();

			Direccion direccion = Direccion.getById(commonKey.getId());
			areaAdapter.getArea().setDireccion((DireccionVO) direccion.toVO(0,false));

			log.debug(funcName + ": exit");
			return areaAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AreaAdapter getAreaAdapterParam(UserContext userContext,
			AreaAdapter areaAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			areaAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return areaAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AreaAdapter getAreaAdapterForUpdate(UserContext userContext,
			CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Area area = Area.getById(commonKey.getId());

			AreaAdapter areaAdapter = new AreaAdapter();
			areaAdapter.setArea((AreaVO) area.toVO(1));

			List<AreaAplicacionPerfil> listAreaAplicacionPerfil = AreaAplicacionPerfil.getListByArea(commonKey.getId());
			areaAdapter.setListAreaAplicacionPerfil(ListUtilBean.toVO(listAreaAplicacionPerfil, 1, false));

			log.debug(funcName + ": exit");
			return areaAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AreaVO createArea(UserContext userContext, AreaVO areaVO)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			areaVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Area area = new Area();
			area.setDescripcion(areaVO.getDescripcion());
			area.setDireccion(Direccion.getById(areaVO.getDireccion().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			area = DefConfiguracionManager.getInstance().createArea(area);

			if (area.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				areaVO = (AreaVO) area.toVO(3);
			}
			area.passErrorMessages(areaVO);

			log.debug(funcName + ": exit");
			return areaVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AreaVO updateArea(UserContext userContext, AreaVO areaVO)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			areaVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Area area = Area.getById(areaVO.getId());

			if (!areaVO.validateVersion(area.getFechaUltMdf()))
				return areaVO;

			area.setDescripcion(areaVO.getDescripcion());
			area.setDireccion(Direccion.getById(areaVO.getDireccion().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			area = DefConfiguracionManager.getInstance().updateArea(area);

			if (area.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				areaVO = (AreaVO) area.toVO(3);
			}
			area.passErrorMessages(areaVO);

			log.debug(funcName + ": exit");
			return areaVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AreaVO deleteArea(UserContext userContext, AreaVO areaVO)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			areaVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			Area area = Area.getById(areaVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			area = DefConfiguracionManager.getInstance().deleteArea(area);

			if (area.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				areaVO = (AreaVO) area.toVO(3);
			}
			area.passErrorMessages(areaVO);

			log.debug(funcName + ": exit");
			return areaVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			try {
				tx.rollback();
			} catch (Exception ee) {
			}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	// Direccion Aplicación Perfil
	public DireccionAplicacionPerfilAdapter getDireccionAplicacionPerfilAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			DireccionAplicacionPerfil direccionAplicacionPerfil = DireccionAplicacionPerfil
					.getById(commonKey.getId());

			DireccionAplicacionPerfilAdapter direccionAplicacionPerfilAdapter = new DireccionAplicacionPerfilAdapter();
			direccionAplicacionPerfilAdapter
			.setDireccionAplicacionPerfil((DireccionAplicacionPerfilVO) direccionAplicacionPerfil
					.toVO(1));

			log.debug(funcName + ": exit");
			return direccionAplicacionPerfilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DireccionAplicacionPerfilAdapter getDireccionAplicacionPerfilAdapterForCreate(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			DireccionAplicacionPerfilAdapter direccionAplicacionPerfilAdapter = new DireccionAplicacionPerfilAdapter();

			// Direccion
			Direccion direccion = Direccion.getById(commonKey.getId());
			direccionAplicacionPerfilAdapter.getDireccionAplicacionPerfil().setDireccion((DireccionVO) direccion.toVO(0,false));

			List<AplicacionPerfil> listAplicacionPerfil = AplicacionPerfil.getListActivos();
			direccionAplicacionPerfilAdapter.setListAplicacionPerfil(ListUtilBean.toVO(listAplicacionPerfil, 0, false,
					new AplicacionPerfilVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));


			log.debug(funcName + ": exit");
			return direccionAplicacionPerfilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DireccionAplicacionPerfilAdapter getDireccionAplicacionPerfilAdapterForUpdate(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			DireccionAplicacionPerfil direccionAplicacionPerfil = DireccionAplicacionPerfil
					.getById(commonKey.getId());

			DireccionAplicacionPerfilAdapter direccionAplicacionPerfilAdapter = new DireccionAplicacionPerfilAdapter();
			direccionAplicacionPerfilAdapter
			.setDireccionAplicacionPerfil((DireccionAplicacionPerfilVO) direccionAplicacionPerfil
					.toVO(1));

			List<Direccion> listDireccion = Direccion.getListActivos();
			direccionAplicacionPerfilAdapter.setListDireccion(ListUtilBean
					.toVO(listDireccion, 0, false));

			List<AplicacionPerfil> listAplicacionPerfil = AplicacionPerfil
					.getListActivos();
			direccionAplicacionPerfilAdapter
			.setListAplicacionPerfil(ListUtilBean.toVO(
					listAplicacionPerfil, 0, false));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return direccionAplicacionPerfilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DireccionAplicacionPerfilVO createDireccionAplicacionPerfil(
			UserContext userContext, DireccionAplicacionPerfilVO direccionAplicacionPerfilVO)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			direccionAplicacionPerfilVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			DireccionAplicacionPerfil direccionAplicacionPerfil = new DireccionAplicacionPerfil();
			direccionAplicacionPerfil.setAplicacionPerfil(
					AplicacionPerfil.getByIdNull(direccionAplicacionPerfilVO.getAplicacionPerfil().getId()));
			direccionAplicacionPerfil
			.setDireccion(Direccion.getById(direccionAplicacionPerfilVO
					.getDireccion().getId()));

			direccionAplicacionPerfil.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			direccionAplicacionPerfil = DefConfiguracionManager.getInstance()
					.createDireccionAplicacionPerfil(direccionAplicacionPerfil);

			if (direccionAplicacionPerfil.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				direccionAplicacionPerfilVO = (DireccionAplicacionPerfilVO) direccionAplicacionPerfil
						.toVO(3);
			}
			direccionAplicacionPerfil
			.passErrorMessages(direccionAplicacionPerfilVO);

			log.debug(funcName + ": exit");
			return direccionAplicacionPerfilVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DireccionAplicacionPerfilVO updateDireccionAplicacionPerfil(
			UserContext userContext,
			DireccionAplicacionPerfilVO direccionAplicacionPerfilVO)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			direccionAplicacionPerfilVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			DireccionAplicacionPerfil direccionAplicacionPerfil = DireccionAplicacionPerfil
					.getById(direccionAplicacionPerfilVO.getId());

			if (!direccionAplicacionPerfilVO
					.validateVersion(direccionAplicacionPerfil.getFechaUltMdf()))
				return direccionAplicacionPerfilVO;

			direccionAplicacionPerfil.setAplicacionPerfil(AplicacionPerfil
					.getById(direccionAplicacionPerfilVO.getAplicacionPerfil()
							.getId()));
			direccionAplicacionPerfil
			.setDireccion(Direccion.getById(direccionAplicacionPerfilVO
					.getDireccion().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			direccionAplicacionPerfil = DefConfiguracionManager.getInstance()
					.updateDireccionAplicacionPerfil(direccionAplicacionPerfil);

			if (direccionAplicacionPerfil.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				direccionAplicacionPerfilVO = (DireccionAplicacionPerfilVO) direccionAplicacionPerfil
						.toVO(3);
			}
			direccionAplicacionPerfil
			.passErrorMessages(direccionAplicacionPerfilVO);

			log.debug(funcName + ": exit");
			return direccionAplicacionPerfilVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DireccionAplicacionPerfilVO deleteDireccionAplicacionPerfil(
			UserContext userContext,
			DireccionAplicacionPerfilVO direccionAplicacionPerfilVO)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			direccionAplicacionPerfilVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			DireccionAplicacionPerfil direccionAplicacionPerfil = DireccionAplicacionPerfil
					.getById(direccionAplicacionPerfilVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			direccionAplicacionPerfil = DefConfiguracionManager.getInstance()
					.deleteDireccionAplicacionPerfil(direccionAplicacionPerfil);

			if (direccionAplicacionPerfil.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				direccionAplicacionPerfilVO = (DireccionAplicacionPerfilVO) direccionAplicacionPerfil
						.toVO(3);
			}
			direccionAplicacionPerfil
			.passErrorMessages(direccionAplicacionPerfilVO);

			log.debug(funcName + ": exit");
			return direccionAplicacionPerfilVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			try {
				tx.rollback();
			} catch (Exception ee) {
			}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	// ----> Area Aplicacion Perfil
	public AreaAplicacionPerfilAdapter getAreaAplicacionPerfilAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AreaAplicacionPerfil areaAplicacionPerfil = AreaAplicacionPerfil.getById(commonKey.getId());

			AreaAplicacionPerfilAdapter areaAplicacionPerfilAdapter = new AreaAplicacionPerfilAdapter();
			areaAplicacionPerfilAdapter.setAreaAplicacionPerfil((AreaAplicacionPerfilVO) areaAplicacionPerfil.toVO(2,false));

			log.debug(funcName + ": exit");
			return areaAplicacionPerfilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AreaAplicacionPerfilAdapter getAreaAplicacionPerfilAdapterForCreate(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AreaAplicacionPerfilAdapter areaAplicacionPerfilAdapter = new AreaAplicacionPerfilAdapter();

			// Perfil de Acceso
			PerfilAcceso perfilAcceso = PerfilAcceso.getById(commonKey.getId());
			areaAplicacionPerfilAdapter.getAreaAplicacionPerfil().setPerfilAcceso((PerfilAccesoVO) perfilAcceso.toVO(0,false));

			// Area
			Area area = perfilAcceso.getArea();
			areaAplicacionPerfilAdapter.getAreaAplicacionPerfil().setArea((AreaVO) area.toVO(0,false));

			// Lista de Formularios asociados a la Direccion
			List<AplicacionPerfil> listAplicacionPerfil = 
					DefDAOFactory.getDireccionAplicacionPerfilDAO().getListAplicacionPerfilByDireccion(area.getDireccion());

			// Seteo la listas para combos, etc
			areaAplicacionPerfilAdapter.setListAplicacionPerfil(ListUtilBean.toVO(listAplicacionPerfil, 
					0,false, new AplicacionParametroVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			// Seteo de banderas


			log.debug(funcName + ": exit");
			return areaAplicacionPerfilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AreaAplicacionPerfilAdapter getAreaAplicacionPerfilAdapterParam(
			UserContext userContext,
			AreaAplicacionPerfilAdapter areaAplicacionPerfilAdapter)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			areaAplicacionPerfilAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return areaAplicacionPerfilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AreaAplicacionPerfilAdapter getAreaAplicacionPerfilAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyAreaAplicacionPerfil)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AreaAplicacionPerfil areaAplicacionPerfil = AreaAplicacionPerfil
					.getById(commonKeyAreaAplicacionPerfil.getId());

			AreaAplicacionPerfilAdapter areaAplicacionPerfilAdapter = new AreaAplicacionPerfilAdapter();
			areaAplicacionPerfilAdapter.setAreaAplicacionPerfil((AreaAplicacionPerfilVO) areaAplicacionPerfil.toVO(1,false));

			// Seteo la lista para combo, valores, etc
			areaAplicacionPerfilAdapter.setListAplicacionPerfil(ListUtilBean.toVO(
					AplicacionPerfil.getListActivos(), 0, false));

			log.debug(funcName + ": exit");
			return areaAplicacionPerfilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AreaAplicacionPerfilVO createAreaAplicacionPerfil(
			UserContext userContext, AreaAplicacionPerfilVO areaAplicacionPerfilVO)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			areaAplicacionPerfilVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AreaAplicacionPerfil areaAplicacionPerfil = new AreaAplicacionPerfil();

			// AplicacionPerfil
			AplicacionPerfil aplicacionPerfil = 
					AplicacionPerfil.getByIdNull(areaAplicacionPerfilVO.getAplicacionPerfil().getId());
			areaAplicacionPerfil.setAplicacionPerfil(aplicacionPerfil);

			// Area
			Area area = Area.getById(areaAplicacionPerfilVO.getArea().getId());
			areaAplicacionPerfil.setArea(area);

			//
			PerfilAcceso perfilAcceso = PerfilAcceso.getById(areaAplicacionPerfilVO.getPerfilAcceso().getId());
			areaAplicacionPerfil.setPerfilAcceso(perfilAcceso);

			areaAplicacionPerfil.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			areaAplicacionPerfil = DefConfiguracionManager.getInstance().createAreaAplicacionPerfil(areaAplicacionPerfil);

			if (areaAplicacionPerfil.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				areaAplicacionPerfilVO = (AreaAplicacionPerfilVO) areaAplicacionPerfil.toVO(0, false);
			}
			areaAplicacionPerfil.passErrorMessages(areaAplicacionPerfilVO);

			log.debug(funcName + ": exit");
			return areaAplicacionPerfilVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AreaAplicacionPerfilVO updateAreaAplicacionPerfil(
			UserContext userContext, AreaAplicacionPerfilVO areaAplicacionPerfilVO)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			areaAplicacionPerfilVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AreaAplicacionPerfil areaAplicacionPerfil = AreaAplicacionPerfil
					.getById(areaAplicacionPerfilVO.getId());

			if (!areaAplicacionPerfilVO.validateVersion(areaAplicacionPerfil
					.getFechaUltMdf()))
				return areaAplicacionPerfilVO;

			AplicacionPerfil aplicacionPerfil = AplicacionPerfil.getById(areaAplicacionPerfilVO
					.getAplicacionPerfil().getId());
			areaAplicacionPerfil.setAplicacionPerfil(aplicacionPerfil);
			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			areaAplicacionPerfil = DefConfiguracionManager.getInstance()
					.updateAreaAplicacionPerfil(areaAplicacionPerfil);

			if (areaAplicacionPerfil.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				areaAplicacionPerfilVO = (AreaAplicacionPerfilVO) areaAplicacionPerfil
						.toVO(0, false);
			}
			areaAplicacionPerfil.passErrorMessages(areaAplicacionPerfilVO);

			log.debug(funcName + ": exit");
			return areaAplicacionPerfilVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AreaAplicacionPerfilVO deleteAreaAplicacionPerfil(
			UserContext userContext, AreaAplicacionPerfilVO areaAplicacionPerfilVO)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			areaAplicacionPerfilVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			AreaAplicacionPerfil areaAplicacionPerfil = AreaAplicacionPerfil
					.getById(areaAplicacionPerfilVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			areaAplicacionPerfil = DefConfiguracionManager.getInstance()
					.deleteAreaAplicacionPerfil(areaAplicacionPerfil);

			if (areaAplicacionPerfil.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				areaAplicacionPerfilVO = (AreaAplicacionPerfilVO) areaAplicacionPerfil
						.toVO(0, false);
			}
			areaAplicacionPerfil.passErrorMessages(areaAplicacionPerfilVO);

			log.debug(funcName + ": exit");
			return areaAplicacionPerfilVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			try {
				tx.rollback();
			} catch (Exception ee) {
			}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}


	// --->>> TelefonoPanico
	public TelefonoPanicoAdapter getTelefonoPanicoAdapterForView(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			TelefonoPanico telefonoPanico = TelefonoPanico.getById(commonKey.getId());

			TelefonoPanicoAdapter telefonoPanicoAdapter = new TelefonoPanicoAdapter();
			telefonoPanicoAdapter.setTelefonoPanico((TelefonoPanicoVO) telefonoPanico.toVO(2,false));

			log.debug(funcName + ": exit");
			return telefonoPanicoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TelefonoPanicoAdapter getTelefonoPanicoAdapterForCreate(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			TelefonoPanicoAdapter telefonoPanicoAdapter = new TelefonoPanicoAdapter();

			Area area = Area.getById(commonKey.getId());
			telefonoPanicoAdapter.getTelefonoPanico().setArea((AreaVO)area.toVO(0,false));

			log.debug(funcName + ": exit");
			return telefonoPanicoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TelefonoPanicoAdapter getTelefonoPanicoAdapterForUpdate(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			TelefonoPanico telefonoPanico = TelefonoPanico.getById(commonKey.getId());

			TelefonoPanicoAdapter telefonoPanicoAdapter = new TelefonoPanicoAdapter();
			telefonoPanicoAdapter.setTelefonoPanico((TelefonoPanicoVO) telefonoPanico.toVO(1,false));

			log.debug(funcName + ": exit");
			return telefonoPanicoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TelefonoPanicoVO createTelefonoPanico(UserContext userContext, 
			TelefonoPanicoVO telefonoPanicoVO)	throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			telefonoPanicoVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			TelefonoPanico telefonoPanico = new TelefonoPanico();
			telefonoPanico.setDescripcion(telefonoPanicoVO.getDescripcion());
			telefonoPanico.setNumero(telefonoPanicoVO.getNumero());
			telefonoPanico.setArea(Area.getById(telefonoPanicoVO.getArea().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			telefonoPanico = DefConfiguracionManager.getInstance().createTelefonoPanico(telefonoPanico);

			if (telefonoPanico.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				telefonoPanicoVO = (TelefonoPanicoVO) telefonoPanico.toVO(1, false);
			}
			telefonoPanico.passErrorMessages(telefonoPanicoVO);

			log.debug(funcName + ": exit");
			return telefonoPanicoVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TelefonoPanicoVO updateTelefonoPanico(UserContext userContext, TelefonoPanicoVO telefonoPanicoVO)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			telefonoPanicoVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			TelefonoPanico telefonoPanico = TelefonoPanico.getById(telefonoPanicoVO.getId());

			if (!telefonoPanicoVO.validateVersion(telefonoPanico.getFechaUltMdf()))
				return telefonoPanicoVO;

			telefonoPanico.setDescripcion(telefonoPanicoVO.getDescripcion());
			telefonoPanico.setNumero(telefonoPanicoVO.getNumero());
			telefonoPanico.setArea(Area.getById(telefonoPanicoVO.getArea().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			telefonoPanico = DefConfiguracionManager.getInstance().updateTelefonoPanico(telefonoPanico);

			if (telefonoPanico.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				telefonoPanicoVO = (TelefonoPanicoVO) telefonoPanico.toVO(1,false);
			}
			telefonoPanico.passErrorMessages(telefonoPanicoVO);

			log.debug(funcName + ": exit");
			return telefonoPanicoVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TelefonoPanicoVO deleteTelefonoPanico(UserContext userContext, 
			TelefonoPanicoVO telefonoPanicoVO)	throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			telefonoPanicoVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			TelefonoPanico telefonoPanico = TelefonoPanico.getById(telefonoPanicoVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			telefonoPanico = DefConfiguracionManager.getInstance().deleteTelefonoPanico(telefonoPanico);

			if (telefonoPanico.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				
				telefonoPanicoVO = (TelefonoPanicoVO) telefonoPanico.toVO(1, false);
			}
			telefonoPanico.passErrorMessages(telefonoPanicoVO);

			log.debug(funcName + ": exit");
			return telefonoPanicoVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
	// <<<--- TelefonoPanico
}