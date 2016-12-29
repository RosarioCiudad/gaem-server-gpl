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
package ar.gov.rosario.gait.not.buss.service;

/**
 * Implementacion de servicios del submodulo Notificacion del modulo Notificacion
 * @author tecso
 */

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.gov.rosario.gait.apm.buss.bean.Aplicacion;
import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.model.AplicacionVO;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilVO;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.not.buss.bean.EstadoNotificacion;
import ar.gov.rosario.gait.not.buss.bean.NotNotificacionManager;
import ar.gov.rosario.gait.not.buss.bean.Notificacion;
import ar.gov.rosario.gait.not.buss.bean.TipoNotificacion;
import ar.gov.rosario.gait.not.buss.dao.NotDAOFactory;
import ar.gov.rosario.gait.not.iface.model.EstadoNotificacionAdapter;
import ar.gov.rosario.gait.not.iface.model.EstadoNotificacionSearchPage;
import ar.gov.rosario.gait.not.iface.model.EstadoNotificacionVO;
import ar.gov.rosario.gait.not.iface.model.NotificacionAdapter;
import ar.gov.rosario.gait.not.iface.model.NotificacionSearchPage;
import ar.gov.rosario.gait.not.iface.model.NotificacionVO;
import ar.gov.rosario.gait.not.iface.model.TipoNotificacionAdapter;
import ar.gov.rosario.gait.not.iface.model.TipoNotificacionSearchPage;
import ar.gov.rosario.gait.not.iface.model.TipoNotificacionVO;
import ar.gov.rosario.gait.not.iface.service.INotNotificacionService;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.Estado;
import coop.tecso.demoda.iface.model.UserContext;

public class NotNotificacionServiceHbmImpl implements INotNotificacionService {
	private Logger log = Logger.getLogger(NotNotificacionServiceHbmImpl.class);

	// ---> ABM Notificacion
	@SuppressWarnings({ "unchecked" })
	public NotificacionSearchPage getNotificacionSearchPageInit(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		NotificacionSearchPage notificacionSearchPage = new NotificacionSearchPage();
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			// Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<Aplicacion> listAplicacion = ApmDAOFactory.getAplicacionDAO()
					.getListActiva();
			notificacionSearchPage.setListAplicacion(ListUtilBean.toVO(
					listAplicacion, 0, false, new TipoNotificacionVO(-1,
							StringUtil.SELECT_OPCION_TODAS)));

			List<TipoNotificacion> listTipoNotificacion = NotDAOFactory
					.getTipoNotificacionDAO().getListActiva();
			notificacionSearchPage.setListTipoNotificacion(ListUtilBean.toVO(
					listTipoNotificacion, 0, false, new TipoNotificacionVO(-1,
							StringUtil.SELECT_OPCION_TODOS)));

			List<EstadoNotificacion> listEstadoNotificacion = NotDAOFactory
					.getEstadoNotificacionDAO().getListActiva();
			notificacionSearchPage.setListEstadoNotificacion(ListUtilBean.toVO(
					listEstadoNotificacion, 0, false, new EstadoNotificacionVO(
							-1, StringUtil.SELECT_OPCION_TODOS)));

			List<DispositivoMovil> listDispositivoMovil = ApmDAOFactory
					.getDispositivoMovilDAO().getListActiva();
			notificacionSearchPage.setListDispositivoMovil(ListUtilBean.toVO(
					listDispositivoMovil, 0, false, new DispositivoMovilVO(-1,
							StringUtil.SELECT_OPCION_TODOS)));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");

		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
		return notificacionSearchPage;
	}

	public NotificacionSearchPage getNotificacionSearchPageResult(
			UserContext userContext,
			NotificacionSearchPage notificacionSearchPage)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			notificacionSearchPage.clearError();

			// Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<Notificacion> listNotificacion = NotDAOFactory
					.getNotificacionDAO().getBySearchPage(
							notificacionSearchPage);

			// Aqui se podria iterar la lista de BO para setear banderas en VOs
			// y otras cosas del negocio.

			// Aqui pasamos BO a VO
			notificacionSearchPage.setListResult(ListUtilBean.toVO(
					listNotificacion, 1, false));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return notificacionSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NotificacionAdapter getNotificacionAdapterForView(
			UserContext userContext, CommonKey commonKey)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Notificacion notificacion = Notificacion.getById(commonKey.getId());

			NotificacionAdapter notificacionAdapter = new NotificacionAdapter();
			notificacionAdapter.setNotificacion((NotificacionVO) notificacion
					.toVO(1));

			log.debug(funcName + ": exit");
			return notificacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NotificacionAdapter getNotificacionAdapterForCreate(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			NotificacionAdapter notificacionAdapter = new NotificacionAdapter();
			
			// Seteo la listas para combos, etc
			notificacionAdapter.setListAplicacion(ListUtilBean.toVO(
					Aplicacion.getListActivos(), 0, false, new AplicacionVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			notificacionAdapter.setListTipoNotificacion(ListUtilBean.toVO(
					TipoNotificacion.getListActivos(), 0, false, new TipoNotificacionVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));
			
			notificacionAdapter.setListDispositivoMovil(ListUtilBean.toVO(
					DispositivoMovil.getListActivos(), 0, false, new DispositivoMovilVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			log.debug(funcName + ": exit");
			return notificacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NotificacionAdapter getNotificacionAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyNotificacion)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Notificacion notificacion = Notificacion
					.getById(commonKeyNotificacion.getId());

			NotificacionAdapter notificacionAdapter = new NotificacionAdapter();
			notificacionAdapter.setNotificacion((NotificacionVO) notificacion
					.toVO(1));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return notificacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NotificacionVO createNotificacion(UserContext userContext,
			NotificacionVO notificacionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			notificacionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Notificacion notificacion = new Notificacion();
			notificacion.setDescripcionAmpliada(notificacionVO
					.getDescripcionAmpliada());
			notificacion.setDescripcionReducida(notificacionVO
					.getDescripcionReducida());
			notificacion.setEstado(Estado.ACTIVO.getId());

			notificacion.setFechaGeneracion(notificacionVO.getFecha());

			notificacion.setTipoNotificacion(TipoNotificacion
					.getById(notificacionVO.getTipoNotificacion().getId()));
			notificacion.setAplicacion(Aplicacion.getById(notificacionVO
					.getAplicacion().getId()));
			notificacion.setDispositivoMovil(DispositivoMovil
					.getById(notificacionVO.getDispositivoMovil().getId()));
			notificacion.setEstadoNotificacion(EstadoNotificacion
					.getById(EstadoNotificacion.ID_ESTADO_PENDIENTE));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			notificacion = NotNotificacionManager.getInstance()
					.createNotificacion(notificacion);

			if (notificacion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				notificacionVO = (NotificacionVO) notificacion.toVO(0, false);
			}
			notificacion.passErrorMessages(notificacionVO);

			log.debug(funcName + ": exit");
			return notificacionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	// ---> ABM TipoNotificacion
	public TipoNotificacionSearchPage getTipoNotificacionSearchPageInit(
			UserContext userContext) throws DemodaServiceException {
		return new TipoNotificacionSearchPage();
	}

	public TipoNotificacionSearchPage getTipoNotificacionSearchPageResult(
			UserContext userContext,
			TipoNotificacionSearchPage tipoNotificacionSearchPage)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			tipoNotificacionSearchPage.clearError();

			// Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<TipoNotificacion> listTipoNotificacion = NotDAOFactory
					.getTipoNotificacionDAO().getBySearchPage(
							tipoNotificacionSearchPage);

			// Aqui se podria iterar la lista de BO para setear banderas en VOs
			// y otras cosas del negocio.

			// Aqui pasamos BO a VO
			tipoNotificacionSearchPage.setListResult(ListUtilBean.toVO(
					listTipoNotificacion, 0));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return tipoNotificacionSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TipoNotificacionAdapter getTipoNotificacionAdapterForView(
			UserContext userContext, CommonKey commonKey)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			TipoNotificacion tipoNotificacion = TipoNotificacion
					.getById(commonKey.getId());

			TipoNotificacionAdapter tipoNotificacionAdapter = new TipoNotificacionAdapter();
			tipoNotificacionAdapter
					.setTipoNotificacion((TipoNotificacionVO) tipoNotificacion
							.toVO(1));

			log.debug(funcName + ": exit");
			return tipoNotificacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TipoNotificacionAdapter getTipoNotificacionAdapterForCreate(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			TipoNotificacionAdapter tipoNotificacionAdapter = new TipoNotificacionAdapter();

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return tipoNotificacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TipoNotificacionAdapter getTipoNotificacionAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyTipoNotificacion)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			TipoNotificacion tipoNotificacion = TipoNotificacion
					.getById(commonKeyTipoNotificacion.getId());

			TipoNotificacionAdapter tipoNotificacionAdapter = new TipoNotificacionAdapter();
			tipoNotificacionAdapter
					.setTipoNotificacion((TipoNotificacionVO) tipoNotificacion
							.toVO(1));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return tipoNotificacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TipoNotificacionVO createTipoNotificacion(UserContext userContext,
			TipoNotificacionVO tipoNotificacionVO)
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
			tipoNotificacionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			TipoNotificacion tipoNotificacion = new TipoNotificacion();

			tipoNotificacion
					.setDescripcion(tipoNotificacionVO.getDescripcion());
			tipoNotificacion.setUbicacionIcono(tipoNotificacionVO
					.getUbicacionIcono());
			tipoNotificacion.setUbicacionSonido(tipoNotificacionVO
					.getUbicacionSonido());

			tipoNotificacion.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			tipoNotificacion = NotNotificacionManager.getInstance()
					.createTipoNotificacion(tipoNotificacion);

			if (tipoNotificacion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				tipoNotificacionVO = (TipoNotificacionVO) tipoNotificacion
						.toVO(0, false);
			}
			tipoNotificacion.passErrorMessages(tipoNotificacionVO);

			log.debug(funcName + ": exit");
			return tipoNotificacionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TipoNotificacionVO updateTipoNotificacion(UserContext userContext,
			TipoNotificacionVO tipoNotificacionVO)
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
			tipoNotificacionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			TipoNotificacion tipoNotificacion = TipoNotificacion
					.getById(tipoNotificacionVO.getId());

			if (!tipoNotificacionVO.validateVersion(tipoNotificacion
					.getFechaUltMdf()))
				return tipoNotificacionVO;

			tipoNotificacion
					.setDescripcion(tipoNotificacionVO.getDescripcion());
			tipoNotificacion.setUbicacionIcono(tipoNotificacionVO
					.getUbicacionIcono());
			tipoNotificacion.setUbicacionSonido(tipoNotificacionVO
					.getUbicacionSonido());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			tipoNotificacion = NotNotificacionManager.getInstance()
					.updateTipoNotificacion(tipoNotificacion);

			if (tipoNotificacion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				tipoNotificacionVO = (TipoNotificacionVO) tipoNotificacion
						.toVO(0, false);
			}
			tipoNotificacion.passErrorMessages(tipoNotificacionVO);

			log.debug(funcName + ": exit");
			return tipoNotificacionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TipoNotificacionVO deleteTipoNotificacion(UserContext userContext,
			TipoNotificacionVO tipoNotificacionVO)
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
			tipoNotificacionVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			TipoNotificacion tipoNotificacion = TipoNotificacion
					.getById(tipoNotificacionVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			tipoNotificacion = NotNotificacionManager.getInstance()
					.deleteTipoNotificacion(tipoNotificacion);

			if (tipoNotificacion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				tipoNotificacionVO = (TipoNotificacionVO) tipoNotificacion
						.toVO(0, false);
			}
			tipoNotificacion.passErrorMessages(tipoNotificacionVO);

			log.debug(funcName + ": exit");
			return tipoNotificacionVO;
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

	// <--- ABM TipoNotificacion

	// ---> ABM EstadoNotificacion
	public EstadoNotificacionSearchPage getEstadoNotificacionSearchPageInit(
			UserContext userContext) throws DemodaServiceException {
		return new EstadoNotificacionSearchPage();
	}

	public EstadoNotificacionSearchPage getEstadoNotificacionSearchPageResult(
			UserContext userContext,
			EstadoNotificacionSearchPage estadoNotificacionSearchPage)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			estadoNotificacionSearchPage.clearError();

			// Aqui obtiene lista de BOs
			List<EstadoNotificacion> listEstadoNotificacion = NotDAOFactory
					.getEstadoNotificacionDAO().getBySearchPage(
							estadoNotificacionSearchPage);

			// Aqui pasamos BO a VO
			estadoNotificacionSearchPage.setListResult(ListUtilBean.toVO(
					listEstadoNotificacion, 0));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return estadoNotificacionSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public EstadoNotificacionAdapter getEstadoNotificacionAdapterForView(
			UserContext userContext, CommonKey commonKey)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			EstadoNotificacion estadoNotificacion = EstadoNotificacion
					.getById(commonKey.getId());

			EstadoNotificacionAdapter estadoNotificacionAdapter = new EstadoNotificacionAdapter();
			estadoNotificacionAdapter
					.setEstadoNotificacion((EstadoNotificacionVO) estadoNotificacion
							.toVO(1));

			log.debug(funcName + ": exit");
			return estadoNotificacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public EstadoNotificacionAdapter getEstadoNotificacionAdapterForCreate(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			EstadoNotificacionAdapter estadoNotificacionAdapter = new EstadoNotificacionAdapter();

			log.debug(funcName + ": exit");
			return estadoNotificacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public EstadoNotificacionAdapter getEstadoNotificacionAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyEstadoNotificacion)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			EstadoNotificacion estadoNotificacion = EstadoNotificacion
					.getById(commonKeyEstadoNotificacion.getId());

			EstadoNotificacionAdapter estadoNotificacionAdapter = new EstadoNotificacionAdapter();
			estadoNotificacionAdapter
					.setEstadoNotificacion((EstadoNotificacionVO) estadoNotificacion
							.toVO(1));

			log.debug(funcName + ": exit");
			return estadoNotificacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public EstadoNotificacionVO createEstadoNotificacion(UserContext userContext,
			EstadoNotificacionVO estadoNotificacionVO)
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
			estadoNotificacionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			EstadoNotificacion estadoNotificacion = new EstadoNotificacion();

			estadoNotificacion
					.setDescripcion(estadoNotificacionVO.getDescripcion());
			estadoNotificacion.setEstado(Estado.ACTIVO.getId());

			// corresponder a un Bean contenedor
			estadoNotificacion = NotNotificacionManager.getInstance()
					.createEstadoNotificacion(estadoNotificacion);

			if (estadoNotificacion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				estadoNotificacionVO = (EstadoNotificacionVO) estadoNotificacion
						.toVO(0, false);
			}
			estadoNotificacion.passErrorMessages(estadoNotificacionVO);

			log.debug(funcName + ": exit");
			return estadoNotificacionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public EstadoNotificacionVO updateEstadoNotificacion(UserContext userContext,
			EstadoNotificacionVO estadoNotificacionVO)
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
			estadoNotificacionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			EstadoNotificacion estadoNotificacion = EstadoNotificacion
					.getById(estadoNotificacionVO.getId());

			if (!estadoNotificacionVO.validateVersion(estadoNotificacion
					.getFechaUltMdf()))
				return estadoNotificacionVO;

			estadoNotificacion
					.setDescripcion(estadoNotificacionVO.getDescripcion());

			// corresponder a un Bean contenedor
			estadoNotificacion = NotNotificacionManager.getInstance()
					.updateEstadoNotificacion(estadoNotificacion);

			if (estadoNotificacion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				estadoNotificacionVO = (EstadoNotificacionVO) estadoNotificacion
						.toVO(0, false);
			}
			estadoNotificacion.passErrorMessages(estadoNotificacionVO);

			log.debug(funcName + ": exit");
			return estadoNotificacionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public EstadoNotificacionVO deleteEstadoNotificacion(UserContext userContext,
			EstadoNotificacionVO estadoNotificacionVO)
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
			estadoNotificacionVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			EstadoNotificacion estadoNotificacion = EstadoNotificacion
					.getById(estadoNotificacionVO.getId());

			// de otro bean
			estadoNotificacion = NotNotificacionManager.getInstance()
					.deleteEstadoNotificacion(estadoNotificacion);

			if (estadoNotificacion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				estadoNotificacionVO = (EstadoNotificacionVO) estadoNotificacion
						.toVO(0, false);
			}
			estadoNotificacion.passErrorMessages(estadoNotificacionVO);

			log.debug(funcName + ": exit");
			return estadoNotificacionVO;
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
	// <--- ABM EstadoNotificacion
}