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
package ar.gov.rosario.gait.apm.buss.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampo;
import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampoValor;
import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampoValorOpcion;
import ar.gov.rosario.gait.apm.buss.bean.Aplicacion;
import ar.gov.rosario.gait.apm.buss.bean.AplicacionBinarioVersion;
import ar.gov.rosario.gait.apm.buss.bean.AplicacionParametro;
import ar.gov.rosario.gait.apm.buss.bean.AplicacionPerfil;
import ar.gov.rosario.gait.apm.buss.bean.AplicacionPerfilParametro;
import ar.gov.rosario.gait.apm.buss.bean.AplicacionPerfilSeccion;
import ar.gov.rosario.gait.apm.buss.bean.AplicacionTabla;
import ar.gov.rosario.gait.apm.buss.bean.AplicacionTipoBinario;
import ar.gov.rosario.gait.apm.buss.bean.ApmAplicacionManager;
import ar.gov.rosario.gait.apm.buss.bean.Campo;
import ar.gov.rosario.gait.apm.buss.bean.CampoValor;
import ar.gov.rosario.gait.apm.buss.bean.CampoValorOpcion;
import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.apm.buss.bean.Impresora;
import ar.gov.rosario.gait.apm.buss.bean.PerfilAcceso;
import ar.gov.rosario.gait.apm.buss.bean.PerfilAccesoAplicacion;
import ar.gov.rosario.gait.apm.buss.bean.PerfilAccesoUsuario;
import ar.gov.rosario.gait.apm.buss.bean.Seccion;
import ar.gov.rosario.gait.apm.buss.bean.TablaVersion;
import ar.gov.rosario.gait.apm.buss.bean.UsuarioApm;
import ar.gov.rosario.gait.apm.buss.bean.UsuarioApmDM;
import ar.gov.rosario.gait.apm.buss.bean.UsuarioApmImpresora;
import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoVO;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorOpcionAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorOpcionVO;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionBinarioVersionAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionBinarioVersionVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionParametroAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionParametroVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilParametroAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilParametroVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilSearchPage;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilSeccionAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilSeccionVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionSearchPage;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTablaAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTablaSearchPage;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTablaVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTipoBinarioAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTipoBinarioSearchPage;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTipoBinarioVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionVO;
import ar.gov.rosario.gait.apm.iface.model.CampoAdapter;
import ar.gov.rosario.gait.apm.iface.model.CampoSearchPage;
import ar.gov.rosario.gait.apm.iface.model.CampoVO;
import ar.gov.rosario.gait.apm.iface.model.CampoValorAdapter;
import ar.gov.rosario.gait.apm.iface.model.CampoValorOpcionAdapter;
import ar.gov.rosario.gait.apm.iface.model.CampoValorOpcionVO;
import ar.gov.rosario.gait.apm.iface.model.CampoValorVO;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilAdapter;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilSearchPage;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilVO;
import ar.gov.rosario.gait.apm.iface.model.ImpresoraAdapter;
import ar.gov.rosario.gait.apm.iface.model.ImpresoraSearchPage;
import ar.gov.rosario.gait.apm.iface.model.ImpresoraVO;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoAdapter;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoAplicacionAdapter;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoAplicacionVO;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoSearchPage;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoUsuarioAdapter;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoUsuarioVO;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoVO;
import ar.gov.rosario.gait.apm.iface.model.SeccionAdapter;
import ar.gov.rosario.gait.apm.iface.model.SeccionSearchPage;
import ar.gov.rosario.gait.apm.iface.model.SeccionVO;
import ar.gov.rosario.gait.apm.iface.model.TablaVersionAdapter;
import ar.gov.rosario.gait.apm.iface.model.TablaVersionSearchPage;
import ar.gov.rosario.gait.apm.iface.model.TablaVersionVO;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmAdapter;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmDMAdapter;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmDMVO;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmImpresoraAdapter;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmImpresoraVO;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmSearchPage;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmVO;
import ar.gov.rosario.gait.apm.iface.service.IApmAplicacionService;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.iface.model.GaitParam;
import ar.gov.rosario.gait.def.buss.bean.Area;
import ar.gov.rosario.gait.def.buss.bean.AreaAplicacionPerfil;
import ar.gov.rosario.gait.def.buss.dao.DefDAOFactory;
import ar.gov.rosario.gait.def.iface.model.AreaAplicacionPerfilVO;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.frm.buss.bean.TipoFormulario;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.Estado;
import coop.tecso.demoda.iface.model.SiNo;
import coop.tecso.demoda.iface.model.Tratamiento;
import coop.tecso.demoda.iface.model.UserContext;

public class ApmAplicacionServiceHbmImpl implements IApmAplicacionService {
	private Logger log = Logger.getLogger(ApmAplicacionServiceHbmImpl.class);

	// ---> ABM Seccion
	public SeccionSearchPage getSeccionSearchPageInit(UserContext userContext)
			throws DemodaServiceException {
		return new SeccionSearchPage();
	}

	public SeccionSearchPage getSeccionSearchPageResult(
			UserContext userContext, SeccionSearchPage seccionSearchPage)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			seccionSearchPage.clearError();

			// Aqui obtiene lista de BOs
			List<Seccion> listSeccion = ApmDAOFactory.getSeccionDAO()
					.getBySearchPage(seccionSearchPage);

			// Aqui se podria iterar la lista de BO para setear banderas en VOs
			// y otras cosas del negocio.

			// Aqui pasamos BO a VO
			seccionSearchPage.setListResult(ListUtilBean.toVO(listSeccion, 0));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return seccionSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SeccionAdapter getSeccionAdapterForView(UserContext userContext,
			CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Seccion seccion = Seccion.getById(commonKey.getId());

			SeccionAdapter seccionAdapter = new SeccionAdapter();
			seccionAdapter.setSeccion((SeccionVO) seccion.toVO(1));

			log.debug(funcName + ": exit");
			return seccionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SeccionAdapter getSeccionAdapterForCreate(UserContext userContext)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			SeccionAdapter seccionAdapter = new SeccionAdapter();

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return seccionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SeccionAdapter getSeccionAdapterParam(UserContext userContext,
			SeccionAdapter seccionAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			seccionAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return seccionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SeccionAdapter getSeccionAdapterForUpdate(UserContext userContext,
			CommonKey commonKeySeccion) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Seccion seccion = Seccion.getById(commonKeySeccion.getId());

			SeccionAdapter seccionAdapter = new SeccionAdapter();
			seccionAdapter.setSeccion((SeccionVO) seccion.toVO(1));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return seccionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SeccionVO createSeccion(UserContext userContext, SeccionVO seccionVO)
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
			seccionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Seccion seccion = new Seccion();

			seccion.setDescripcion(seccionVO.getDescripcion());

			seccion.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			seccion = ApmAplicacionManager.getInstance().createSeccion(seccion);

			if (seccion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				seccionVO = (SeccionVO) seccion.toVO(0, false);
			}
			seccion.passErrorMessages(seccionVO);

			log.debug(funcName + ": exit");
			return seccionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SeccionVO updateSeccion(UserContext userContext, SeccionVO seccionVO)
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
			seccionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Seccion seccion = Seccion.getById(seccionVO.getId());

			if (!seccionVO.validateVersion(seccion.getFechaUltMdf()))
				return seccionVO;

			seccion.setDescripcion(seccionVO.getDescripcion());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			seccion = ApmAplicacionManager.getInstance().updateSeccion(seccion);

			if (seccion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				seccionVO = (SeccionVO) seccion.toVO(0, false);
			}
			seccion.passErrorMessages(seccionVO);

			log.debug(funcName + ": exit");
			return seccionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SeccionVO deleteSeccion(UserContext userContext, SeccionVO seccionVO)
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
			seccionVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			Seccion seccion = Seccion.getById(seccionVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			seccion = ApmAplicacionManager.getInstance().deleteSeccion(seccion);

			if (seccion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				seccionVO = (SeccionVO) seccion.toVO(0, false);
			}
			seccion.passErrorMessages(seccionVO);

			log.debug(funcName + ": exit");
			return seccionVO;
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

	public SeccionVO activarSeccion(UserContext userContext, SeccionVO seccionVO)
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

			Seccion seccion = Seccion.getById(seccionVO.getId());

			seccion.activar();

			if (seccion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				seccionVO = (SeccionVO) seccion.toVO(0, false);
			}
			seccion.passErrorMessages(seccionVO);

			log.debug(funcName + ": exit");
			return seccionVO;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SeccionVO desactivarSeccion(UserContext userContext,
			SeccionVO seccionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();

			Seccion seccion = Seccion.getById(seccionVO.getId());

			seccion.desactivar();

			if (seccion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				seccionVO = (SeccionVO) seccion.toVO(0, false);
			}
			seccion.passErrorMessages(seccionVO);

			log.debug(funcName + ": exit");
			return seccionVO;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SeccionAdapter imprimirSeccion(UserContext userContext,
			SeccionAdapter seccionAdapterVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Seccion seccion = Seccion.getById(seccionAdapterVO.getSeccion()
					.getId());

			ApmDAOFactory.getSeccionDAO().imprimirGenerico(seccion,
					seccionAdapterVO.getReport());

			log.debug(funcName + ": exit");
			return seccionAdapterVO;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	// <--- ABM Seccion

	// --->>>
	// --------------------------------------------------------------------------------------------<<<---////////////
	// ---> APM dispositivoMovil
	public DispositivoMovilSearchPage getDispositivoMovilSearchPageInit(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		DispositivoMovilSearchPage dispositivoMovilSearchPage = new DispositivoMovilSearchPage();
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			dispositivoMovilSearchPage.setListSiNo(SiNo
					.getList(SiNo.OpcionTodo));
			// Seteo de banderas
			if (userContext.getEsDGI()) {
				dispositivoMovilSearchPage.setListArea(ListUtilBean.toVO(Area
						.getListActivos(), 0, false, new AreaVO(-1,
								StringUtil.SELECT_OPCION_TODAS)));
				dispositivoMovilSearchPage.setEditarAreaEnabled(true);
			} else {
				AreaVO currentArea = new AreaVO(userContext.getIdArea()
						.intValue(), userContext.getDesArea());
				dispositivoMovilSearchPage.getDispositivoMovil().setArea(
						currentArea);
			}

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}

		return dispositivoMovilSearchPage;
	}

	public DispositivoMovilSearchPage getDispositivoMovilSearchPageResult(
			UserContext userContext,
			DispositivoMovilSearchPage dispositivoMovilSearchPage)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			dispositivoMovilSearchPage.clearError();

			// Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<DispositivoMovil> listDispositivoMovil = ApmDAOFactory
					.getDispositivoMovilDAO().getBySearchPage(
							dispositivoMovilSearchPage);

			// Aqui se podria iterar la lista de BO para setear banderas en VOs
			// y otras cosas del negocio.

			// Aqui pasamos BO a VO
			dispositivoMovilSearchPage.setListResult(ListUtilBean.toVO(
					listDispositivoMovil, 1, false));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return dispositivoMovilSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DispositivoMovilAdapter getDispositivoMovilAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			DispositivoMovil dispositivoMovil = DispositivoMovil
					.getById(commonKey.getId());

			DispositivoMovilAdapter dispositivoMovilAdapter = new DispositivoMovilAdapter();
			dispositivoMovilAdapter
			.setDispositivoMovil((DispositivoMovilVO) dispositivoMovil
					.toVO(1, false));

			log.debug(funcName + ": exit");
			return dispositivoMovilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DispositivoMovilAdapter getDispositivoMovilAdapterForCreate(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			DispositivoMovilAdapter dispositivoMovilAdapter = new DispositivoMovilAdapter();

			// Seteo de banderas
			if (userContext.getEsDGI()) {
				dispositivoMovilAdapter.setListArea(ListUtilBean.toVO(Area
						.getListActivos(), 0, false, new AreaVO(-1,
								StringUtil.SELECT_OPCION_SELECCIONAR)));
				dispositivoMovilAdapter.setEditarAreaEnabled(true);
			} else {
				AreaVO currentArea = new AreaVO(userContext.getIdArea()
						.intValue(), userContext.getDesArea());
				dispositivoMovilAdapter.getDispositivoMovil().setArea(
						currentArea);
			}

			// Seteo la listas para combos, etc
			dispositivoMovilAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));

			log.debug(funcName + ": exit");
			return dispositivoMovilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DispositivoMovilAdapter getDispositivoMovilAdapterParam(
			UserContext userContext,
			DispositivoMovilAdapter dispositivoMovilAdapter)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			dispositivoMovilAdapter.clearError();

			log.debug(funcName + ": exit");
			return dispositivoMovilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DispositivoMovilAdapter getDispositivoMovilAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyDispositivoMovil)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			DispositivoMovilAdapter dispositivoMovilAdapter = new DispositivoMovilAdapter();

			DispositivoMovil dispositivoMovil = DispositivoMovil
					.getById(commonKeyDispositivoMovil.getId());
			dispositivoMovilAdapter
			.setDispositivoMovil((DispositivoMovilVO) dispositivoMovil
					.toVO(1, false));

			// Seteo la lista para combo, valores, etc
			dispositivoMovilAdapter.setListSiNo(SiNo.getListSiNo(SiNo.SI));
			if (userContext.getEsDGI()) {
				dispositivoMovilAdapter.setEditarAreaEnabled(true);
				dispositivoMovilAdapter.setListArea(ListUtilBean.toVO(
						Area.getListActivos(), 0, false));
			}

			log.debug(funcName + ": exit");
			return dispositivoMovilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DispositivoMovilVO createDispositivoMovil(UserContext userContext,
			DispositivoMovilVO dispositivoMovilVO)
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
			dispositivoMovilVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			DispositivoMovil dispositivoMovil = new DispositivoMovil();

			dispositivoMovil
			.setDescripcion(dispositivoMovilVO.getDescripcion());
			dispositivoMovil.setEstado(Estado.ACTIVO.getId());
			dispositivoMovil.setEmailAddress(dispositivoMovilVO
					.getEmailAddress());
			dispositivoMovil
			.setNumeroSerie(dispositivoMovilVO.getNumeroSerie());
			dispositivoMovil
			.setNumeroLinea(dispositivoMovilVO.getNumeroLinea());
			dispositivoMovil.setForzarActualizacion(dispositivoMovilVO
					.getForzarActualizacion().getBussId());
			dispositivoMovil.setArea(Area.getByIdNull(dispositivoMovilVO
					.getArea().getId()));
			dispositivoMovil.setNumeroIMEI(dispositivoMovilVO.getNumeroIMEI());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			dispositivoMovil = ApmAplicacionManager.getInstance()
					.createDispositivoMovil(dispositivoMovil);

			if (dispositivoMovil.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				dispositivoMovilVO = (DispositivoMovilVO) dispositivoMovil
						.toVO(0, false);
			}
			dispositivoMovil.passErrorMessages(dispositivoMovilVO);

			log.debug(funcName + ": exit");
			return dispositivoMovilVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DispositivoMovilVO updateDispositivoMovil(UserContext userContext,
			DispositivoMovilVO dispositivoMovilVO)
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
			dispositivoMovilVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			DispositivoMovil dispositivoMovil = DispositivoMovil
					.getById(dispositivoMovilVO.getId());

			if (!dispositivoMovilVO.validateVersion(dispositivoMovil
					.getFechaUltMdf()))
				return dispositivoMovilVO;

			dispositivoMovil
			.setDescripcion(dispositivoMovilVO.getDescripcion());
			dispositivoMovil.setEstado(Estado.ACTIVO.getId());
			dispositivoMovil.setEmailAddress(dispositivoMovilVO
					.getEmailAddress());
			dispositivoMovil
			.setNumeroSerie(dispositivoMovilVO.getNumeroSerie());
			dispositivoMovil
			.setNumeroLinea(dispositivoMovilVO.getNumeroLinea());
			dispositivoMovil.setForzarActualizacion(dispositivoMovilVO
					.getForzarActualizacion().getBussId());
			dispositivoMovil.setArea(Area.getByIdNull(dispositivoMovilVO
					.getArea().getId()));
			dispositivoMovil.setNumeroIMEI(dispositivoMovilVO.getNumeroIMEI());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			dispositivoMovil = ApmAplicacionManager.getInstance()
					.updateDispositivoMovil(dispositivoMovil);

			if (dispositivoMovil.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				dispositivoMovilVO = (DispositivoMovilVO) dispositivoMovil
						.toVO(0, false);
			}
			dispositivoMovil.passErrorMessages(dispositivoMovilVO);

			log.debug(funcName + ": exit");
			return dispositivoMovilVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public DispositivoMovilVO deleteDispositivoMovil(UserContext userContext,
			DispositivoMovilVO dispositivoMovilVO)
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
			dispositivoMovilVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			DispositivoMovil dispositivoMovil = DispositivoMovil
					.getById(dispositivoMovilVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			dispositivoMovil = ApmAplicacionManager.getInstance()
					.deleteDispositivoMovil(dispositivoMovil);

			if (dispositivoMovil.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				dispositivoMovilVO = (DispositivoMovilVO) dispositivoMovil
						.toVO(0, false);
			}
			dispositivoMovil.passErrorMessages(dispositivoMovilVO);

			log.debug(funcName + ": exit");
			return dispositivoMovilVO;
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

	// ---> APM Campo
	public CampoSearchPage getCampoSearchPageInit(UserContext userContext)
			throws DemodaServiceException {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		CampoSearchPage campoSearchPage = new CampoSearchPage();
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			// Inicializar combos
			campoSearchPage.setListSiNo(SiNo.getList(SiNo.OpcionTodo));
			campoSearchPage.setListTratamiento(Tratamiento
					.getList(Tratamiento.OpcionTodo));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");

		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
		return campoSearchPage;
	}

	public CampoSearchPage getCampoSearchPageResult(UserContext userContext,
			CampoSearchPage campoSearchPage) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			campoSearchPage.clearError();

			// Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<Campo> listCampo = ApmDAOFactory.getCampoDAO()
					.getBySearchPage(campoSearchPage);

			// Aqui se podria iterar la lista de BO para setear banderas en VOs
			// y otras cosas del negocio.

			// Aqui pasamos BO a VO
			campoSearchPage.setListResult(ListUtilBean.toVO(listCampo, 0));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return campoSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoAdapter getCampoAdapterForView(UserContext userContext,
			CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Campo campo = Campo.getById(commonKey.getId());

			CampoAdapter campoAdapter = new CampoAdapter();
			campoAdapter.setCampo((CampoVO) campo.toVO(1, false));

			log.debug(funcName + ": exit");
			return campoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoAdapter getCampoAdapterForCreate(UserContext userContext)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			CampoAdapter campoAdapter = new CampoAdapter();

			// Seteo la listas para combos, etc
			campoAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));
			campoAdapter
			.setListTratamiento(Tratamiento.getList(Tratamiento.TA));

			log.debug(funcName + ": exit");
			return campoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoAdapter getCampoAdapterParam(UserContext userContext,
			CampoAdapter campoAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			campoAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return campoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoAdapter getCampoAdapterForUpdate(UserContext userContext,
			CommonKey commonKeyCampo) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Campo campo = Campo.getById(commonKeyCampo.getId());

			CampoAdapter campoAdapter = new CampoAdapter();

			campoAdapter.setListSiNo(SiNo.getListSiNo(SiNo.SI));
			campoAdapter.setListTratamiento(Tratamiento.getList());

			campoAdapter.setCampo((CampoVO) campo.toVO(1));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return campoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoVO createCampo(UserContext userContext, CampoVO campoVO)
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
			campoVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Campo campo = new Campo();

			campo.setEtiqueta(campoVO.getEtiqueta());
			campo.setTratamiento(campoVO.getTratamiento().getBussId());
			campo.setValorDefault(campoVO.getValorDefault());
			campo.setMascaraVisual(campoVO.getMascaraVisual());
			campo.setObligatorio(campoVO.getObligatorio().getBussId());
			campo.setSoloLectura(campoVO.getSoloLectura().getBussId());
			campo.setCodigo(campoVO.getCodigo());
			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			campo = ApmAplicacionManager.getInstance().createCampo(campo);

			if (campo.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				campoVO = (CampoVO) campo.toVO(0, false);
			}
			campo.passErrorMessages(campoVO);

			log.debug(funcName + ": exit");
			return campoVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoVO updateCampo(UserContext userContext, CampoVO campoVO)
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
			campoVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Campo campo = Campo.getById(campoVO.getId());

			if (!campoVO.validateVersion(campo.getFechaUltMdf()))
				return campoVO;

			campo.setEtiqueta(campoVO.getEtiqueta());
			campo.setMascaraVisual(campoVO.getMascaraVisual());
			campo.setTratamiento(campoVO.getTratamiento().getBussId());
			campo.setValorDefault(campoVO.getValorDefault());
			campo.setObligatorio(campoVO.getObligatorio().getBussId());
			campo.setSoloLectura(campoVO.getSoloLectura().getBussId());
			campo.setCodigo(campoVO.getCodigo());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			campo = ApmAplicacionManager.getInstance().updateCampo(campo);

			if (campo.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				campoVO = (CampoVO) campo.toVO(0, false);
			}
			campo.passErrorMessages(campoVO);

			log.debug(funcName + ": exit");
			return campoVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoVO deleteCampo(UserContext userContext, CampoVO campoVO)
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
			campoVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			Campo campo = Campo.getById(campoVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			campo = ApmAplicacionManager.getInstance().deleteCampo(campo);

			if (campo.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				campoVO = (CampoVO) campo.toVO(0, false);
			}
			campo.passErrorMessages(campoVO);

			log.debug(funcName + ": exit");
			return campoVO;
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

	public CampoValorAdapter getCampoValorAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			CampoValor campoValor = CampoValor.getById(commonKey.getId());

			CampoValorAdapter campoValorAdapter = new CampoValorAdapter();
			campoValorAdapter.setCampoValor((CampoValorVO) campoValor.toVO(1));

			log.debug(funcName + ": exit");
			return campoValorAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoValorAdapter getCampoValorAdapterForCreate(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			CampoValorAdapter campoValorAdapter = new CampoValorAdapter();

			campoValorAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));
			campoValorAdapter.setListTratamiento(Tratamiento
					.getList(Tratamiento.TA));
			campoValorAdapter.getCampoValor().getCampo()
			.setId(commonKey.getId());

			//
			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return campoValorAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoValorAdapter getCampoValorAdapterParam(UserContext userContext,
			CampoValorAdapter campoValorAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			campoValorAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return campoValorAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoValorAdapter getCampoValorAdapterForUpdate(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			CampoValor campoValor = CampoValor.getById(commonKey.getId());

			CampoValorAdapter campoValorAdapter = new CampoValorAdapter();

			campoValorAdapter.setListSiNo(SiNo.getListSiNo(SiNo.SI));
			campoValorAdapter.setListTratamiento(Tratamiento.getList());

			campoValorAdapter.setCampoValor((CampoValorVO) campoValor.toVO(1));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return campoValorAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoValorVO createCampoValor(UserContext userContext,
			CampoValorVO campoValorVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			campoValorVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			CampoValor campoValor = new CampoValor();

			Campo campo = Campo.getById(campoValorVO.getCampo().getId());

			campoValor.setCampo(campo);
			campoValor.setEtiqueta(campoValorVO.getEtiqueta());
			campoValor.setTratamiento(campoValorVO.getTratamiento().getBussId());
			campoValor.setValorDefault(campoValorVO.getValorDefault());
			campoValor.setObligatorio(campoValorVO.getObligatorio().getBussId());
			campoValor.setSoloLectura(campoValorVO.getSoloLectura().getBussId());
			campoValor.setMascaraVisual(campoValorVO.getMascaraVisual());
			campoValor.setCodigo(campoValorVO.getCodigo());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			campoValor = ApmAplicacionManager.getInstance().createCampoValor(campoValor);

			if (campoValor.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				campoValorVO = (CampoValorVO) campoValor.toVO(0, false);
			}
			campoValor.passErrorMessages(campoValorVO);

			log.debug(funcName + ": exit");
			return campoValorVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoValorVO updateCampoValor(UserContext userContext,
			CampoValorVO campoValorVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			campoValorVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			CampoValor campoValor = CampoValor.getById(campoValorVO.getId());

			if (!campoValorVO.validateVersion(campoValor.getFechaUltMdf()))
				return campoValorVO;

			campoValor.setEtiqueta(campoValorVO.getEtiqueta());
			campoValor.setTratamiento(campoValorVO.getTratamiento().getBussId());
			campoValor.setValorDefault(campoValorVO.getValorDefault());
			campoValor.setObligatorio(campoValorVO.getObligatorio().getBussId());
			campoValor.setSoloLectura(campoValorVO.getSoloLectura().getBussId());
			campoValor.setMascaraVisual(campoValorVO.getMascaraVisual());
			campoValor.setCodigo(campoValorVO.getCodigo());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			campoValor = ApmAplicacionManager.getInstance().updateCampoValor(campoValor);

			if (campoValor.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {log.debug(funcName + ": tx.commit");}
				campoValorVO = (CampoValorVO) campoValor.toVO(0, false);
			}
			campoValor.passErrorMessages(campoValorVO);

			log.debug(funcName + ": exit");
			return campoValorVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoValorVO deleteCampoValor(UserContext userContext,
			CampoValorVO campoValorVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			campoValorVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			CampoValor campoValor = CampoValor.getById(campoValorVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			campoValor = ApmAplicacionManager.getInstance().deleteCampoValor(
					campoValor);

			if (campoValor.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				campoValorVO = (CampoValorVO) campoValor.toVO(0, false);
			}
			campoValor.passErrorMessages(campoValorVO);

			log.debug(funcName + ": exit");
			return campoValorVO;
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

	// Campo Valor Opcion
	public CampoValorOpcionAdapter getCampoValorOpcionAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			CampoValorOpcion campoValorOpcion = CampoValorOpcion
					.getById(commonKey.getId());

			CampoValorOpcionAdapter campoValorOpcionAdapter = new CampoValorOpcionAdapter();
			campoValorOpcionAdapter
			.setCampoValorOpcion((CampoValorOpcionVO) campoValorOpcion
					.toVO(1, false));

			log.debug(funcName + ": exit");
			return campoValorOpcionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoValorOpcionAdapter getCampoValorOpcionAdapterForCreate(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			CampoValorOpcionAdapter campoValorOpcionAdapter = new CampoValorOpcionAdapter();
			campoValorOpcionAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));
			campoValorOpcionAdapter.setListTratamiento(Tratamiento
					.getList(Tratamiento.TA));
			campoValorOpcionAdapter.getCampoValorOpcion().getCampoValor()
			.setId(commonKey.getId());

			log.debug(funcName + ": exit");
			return campoValorOpcionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoValorOpcionAdapter getCampoValorOpcionAdapterParam(
			UserContext userContext,
			CampoValorOpcionAdapter campoValorOpcionAdapter)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			campoValorOpcionAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return campoValorOpcionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoValorOpcionAdapter getCampoValorOpcionAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyCampo)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			CampoValorOpcion campoValorOpcion = CampoValorOpcion
					.getById(commonKeyCampo.getId());

			CampoValorOpcionAdapter campoValorOpcionAdapter = new CampoValorOpcionAdapter();

			campoValorOpcionAdapter.setListSiNo(SiNo.getListSiNo(SiNo.SI));
			campoValorOpcionAdapter.setListTratamiento(Tratamiento.getList());

			campoValorOpcionAdapter
			.setCampoValorOpcion((CampoValorOpcionVO) campoValorOpcion
					.toVO(1));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return campoValorOpcionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoValorOpcionVO createCampoValorOpcion(UserContext userContext,
			CampoValorOpcionVO campoValorOpcionVO)
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
			campoValorOpcionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			CampoValorOpcion campoValorOpcion = new CampoValorOpcion();

			CampoValor campoValor = CampoValor.getById(campoValorOpcionVO
					.getCampoValor().getId());

			campoValorOpcion.setCampoValor(campoValor);
			campoValorOpcion.setEtiqueta(campoValorOpcionVO.getEtiqueta());
			campoValorOpcion.setTratamiento(campoValorOpcionVO.getTratamiento().getBussId());
			campoValorOpcion.setValorDefault(campoValorOpcionVO.getValorDefault());
			campoValorOpcion.setObligatorio(campoValorOpcionVO.getObligatorio().getBussId());
			campoValorOpcion.setSoloLectura(campoValorOpcionVO.getSoloLectura().getBussId());
			campoValorOpcion.setMascaraVisual(campoValorOpcionVO.getMascaraVisual());
			campoValorOpcion.setCodigo(campoValorOpcionVO.getCodigo());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			campoValorOpcion = ApmAplicacionManager.getInstance().createCampoValorOpcion(campoValorOpcion);

			if (campoValorOpcion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				campoValorOpcionVO = (CampoValorOpcionVO) campoValorOpcion
						.toVO(0, false);
			}
			campoValorOpcion.passErrorMessages(campoValorOpcionVO);

			log.debug(funcName + ": exit");
			return campoValorOpcionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoValorOpcionVO updateCampoValorOpcion(UserContext userContext,
			CampoValorOpcionVO campoValorOpcionVO)
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
			campoValorOpcionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			CampoValorOpcion campoValorOpcion = CampoValorOpcion
					.getById(campoValorOpcionVO.getId());

			if (!campoValorOpcionVO.validateVersion(campoValorOpcion
					.getFechaUltMdf()))
				return campoValorOpcionVO;

			campoValorOpcion.setEtiqueta(campoValorOpcionVO.getEtiqueta());
			campoValorOpcion.setTratamiento(campoValorOpcionVO.getTratamiento().getBussId());
			campoValorOpcion.setValorDefault(campoValorOpcionVO.getValorDefault());
			campoValorOpcion.setObligatorio(campoValorOpcionVO.getObligatorio().getBussId());
			campoValorOpcion.setSoloLectura(campoValorOpcionVO.getSoloLectura().getBussId());
			campoValorOpcion.setMascaraVisual(campoValorOpcionVO.getMascaraVisual());
			campoValorOpcion.setCodigo(campoValorOpcionVO.getCodigo());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			campoValorOpcion = ApmAplicacionManager.getInstance().updateCampoValorOpcion(campoValorOpcion);

			if (campoValorOpcion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {log.debug(funcName + ": tx.commit");}
				campoValorOpcionVO = (CampoValorOpcionVO) campoValorOpcion.toVO(0, false);
			}
			campoValorOpcion.passErrorMessages(campoValorOpcionVO);

			log.debug(funcName + ": exit");
			return campoValorOpcionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CampoValorOpcionVO deleteCampoValorOpcion(UserContext userContext,
			CampoValorOpcionVO campoValorOpcionVO)
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
			campoValorOpcionVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			CampoValorOpcion campoValorOpcion = CampoValorOpcion
					.getById(campoValorOpcionVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			campoValorOpcion = ApmAplicacionManager.getInstance()
					.deleteCampoValorOpcion(campoValorOpcion);

			if (campoValorOpcion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				campoValorOpcionVO = (CampoValorOpcionVO) campoValorOpcion
						.toVO(0, false);
			}
			campoValorOpcion.passErrorMessages(campoValorOpcionVO);

			log.debug(funcName + ": exit");
			return campoValorOpcionVO;
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

	// ---> APM Aplicacion Perfil
	public AplicacionPerfilSearchPage getAplicacionPerfilSearchPageInit(
			UserContext userContext) throws DemodaServiceException {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		AplicacionPerfilSearchPage aplicacionPerfilSearchPage = new AplicacionPerfilSearchPage();
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			// Inicializar combos

			// Aqui obtiene lista de BOs
			aplicacionPerfilSearchPage.setListAplicacion(ListUtilBean.toVO(
					Aplicacion.getListActivos(), 0, false, new AplicacionVO(-1,
							StringUtil.SELECT_OPCION_TODAS)));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");

		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
		return aplicacionPerfilSearchPage;
	}

	public AplicacionPerfilSearchPage getAplicacionPerfilSearchPageResult(
			UserContext userContext,
			AplicacionPerfilSearchPage aplicacionPerfilSearchPage)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplicacionPerfilSearchPage.clearError();

			// Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<AplicacionPerfil> listAplicacionPerfil = ApmDAOFactory
					.getAplicacionPerfilDAO().getBySearchPage(
							aplicacionPerfilSearchPage);

			// Aqui se podria iterar la lista de BO para setear banderas en VOs
			// y otras cosas del negocio.

			// Aqui pasamos BO a VO
			aplicacionPerfilSearchPage.setListResult(ListUtilBean.toVO(
					listAplicacionPerfil, 1,false));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return aplicacionPerfilSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilAdapter getAplicacionPerfilAdapterForView(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionPerfil aplicacionPerfil = AplicacionPerfil.getById(commonKey.getId());

			AplicacionPerfilAdapter aplicacionPerfilAdapter = new AplicacionPerfilAdapter();
			aplicacionPerfilAdapter.setAplicacionPerfil((AplicacionPerfilVO) aplicacionPerfil.toVO(2));

			log.debug(funcName + ": exit");
			return aplicacionPerfilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilAdapter getAplicacionPerfilAdapterForCreate(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionPerfilAdapter aplicacionPerfilAdapter = new AplicacionPerfilAdapter();

			List<Aplicacion> listAplicacion = Aplicacion.getListActivos();
			aplicacionPerfilAdapter.setListAplicacion(ListUtilBean.toVO(
					listAplicacion, 1, false, new AplicacionVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));
			
			
			List<TipoFormulario> listTipoFormulario = TipoFormulario.getListActivos();
			aplicacionPerfilAdapter.setlistTipoFormulario(ListUtilBean.toVO(
					listTipoFormulario, 1, false, new AplicacionVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return aplicacionPerfilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}


	public AplicacionPerfilVO cloneAplicacionPerfil(UserContext userContext,
			AplicacionPerfilVO aplicacionPerfilVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplicacionPerfilVO.clearErrorMessages();


			AplicacionPerfil aplicacionPerfil= AplicacionPerfil.getById(aplicacionPerfilVO.getId());

			// Se recupera el Bean dado su id
			AplicacionPerfil aplicacionPerfilClon = new AplicacionPerfil();
			aplicacionPerfilClon.setAplicacion(aplicacionPerfil.getAplicacion());
			aplicacionPerfilClon.setTipoFormulario(aplicacionPerfil.getTipoFormulario());
			aplicacionPerfilClon.setDescripcion(aplicacionPerfil.getDescripcion()+" - clon");
			

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacionPerfilClon = ApmAplicacionManager.getInstance().createAplicacionPerfil(aplicacionPerfilClon);

			//comienzo		
			for (AplicacionPerfilSeccion aplicacionPerfilSeccion : aplicacionPerfil.getListAplicacionPerfilSeccion()) {
				AplicacionPerfilSeccion aplicacionPerfilSeccionClon = new AplicacionPerfilSeccion();

				aplicacionPerfilSeccionClon.setNoDesplegar(aplicacionPerfilSeccion.getNoDesplegar());
				aplicacionPerfilSeccionClon.setSeccion(aplicacionPerfilSeccion.getSeccion());
				aplicacionPerfilSeccionClon.setOrden(aplicacionPerfilSeccion.getOrden());
				aplicacionPerfilSeccionClon.setOpcional(aplicacionPerfilSeccion.getOpcional());
				aplicacionPerfilSeccionClon.setPermiteImpresion(aplicacionPerfilSeccion.getPermiteImpresion());
				aplicacionPerfilSeccionClon.setAplicacionPerfil(aplicacionPerfilClon);

				ApmDAOFactory.getAplicacionPerfilSeccionDAO().update(aplicacionPerfilSeccionClon);

				for (AplPerfilSeccionCampo aplPerfilSeccionCampo : aplicacionPerfilSeccion.getListAplPerfilSeccionCampo()) {
					AplPerfilSeccionCampo aplPerfilSeccionCampoClon = new AplPerfilSeccionCampo();

					aplPerfilSeccionCampoClon.setCampo(aplPerfilSeccionCampo.getCampo());
					aplPerfilSeccionCampoClon.setOrden(aplPerfilSeccionCampo.getOrden());
					aplPerfilSeccionCampoClon.setSoloLectura(aplPerfilSeccionCampo.getSoloLectura());
					aplPerfilSeccionCampoClon.setAplicacionPerfilSeccion(aplicacionPerfilSeccionClon);

					ApmDAOFactory.getAplPerfilSeccionCampoDAO().update(aplPerfilSeccionCampoClon);
					for (AplPerfilSeccionCampoValor aplPerfilSeccionCampoValor : aplPerfilSeccionCampo.getListAplPerfilSeccionCampoValor()) {

						AplPerfilSeccionCampoValor aplPerfilSeccionCampoValorClon = new AplPerfilSeccionCampoValor();

						aplPerfilSeccionCampoValorClon.setCampoValor(aplPerfilSeccionCampoValor.getCampoValor());
						aplPerfilSeccionCampoValorClon.setOrden(aplPerfilSeccionCampoValor.getOrden());
						aplPerfilSeccionCampoValorClon.setAplPerfilSeccionCampo(aplPerfilSeccionCampoClon);

						ApmDAOFactory.getAplPerfilSeccionCampoValorDAO().update(aplPerfilSeccionCampoValorClon);

						for (AplPerfilSeccionCampoValorOpcion aplPerfilSeccionCampoValorOpcion : aplPerfilSeccionCampoValor.getListAplPerfilSeccionCampoValorOpcion()) {

							AplPerfilSeccionCampoValorOpcion aplPerfilSeccionCampoValorOpcionClon = new AplPerfilSeccionCampoValorOpcion();


							aplPerfilSeccionCampoValorOpcionClon.setCampoValorOpcion(aplPerfilSeccionCampoValorOpcion.getCampoValorOpcion());
							aplPerfilSeccionCampoValorOpcionClon.setOrden(aplPerfilSeccionCampoValorOpcion.getOrden());
							aplPerfilSeccionCampoValorOpcionClon.setAplPerfilSeccionCampoValor(aplPerfilSeccionCampoValorClon);

							ApmDAOFactory.getAplPerfilSeccionCampoValorOpcionDAO().update(aplPerfilSeccionCampoValorOpcionClon);
						}
					}

				}
			}


			///fin




			if (aplicacionPerfilClon.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				aplicacionPerfilVO = (AplicacionPerfilVO) aplicacionPerfilClon.toVO(0, false);
			}
			aplicacionPerfilClon.passErrorMessages(aplicacionPerfilVO);

			log.debug(funcName + ": exit");
			return aplicacionPerfilVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)	tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}	



	public AplicacionPerfilAdapter getAplicacionPerfilAdapterParam(
			UserContext userContext,
			AplicacionPerfilAdapter aplicacionPerfilAdapter)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplicacionPerfilAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return aplicacionPerfilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilAdapter getAplicacionPerfilAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyAplicacionPerfil)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionPerfil aplicacionPerfil = AplicacionPerfil.getById(commonKeyAplicacionPerfil.getId());
			List<Aplicacion> listAplicacion = Aplicacion.getListActivos();
			List<TipoFormulario> listTipoFormulario = TipoFormulario.getListActivos();
			AplicacionPerfilAdapter aplicacionPerfilAdapter = new AplicacionPerfilAdapter();
			aplicacionPerfilAdapter.setListAplicacion(ListUtilBean.toVO(listAplicacion, 1, false));
			aplicacionPerfilAdapter.setlistTipoFormulario(ListUtilBean.toVO(listTipoFormulario, 1, false));
			aplicacionPerfilAdapter.setAplicacionPerfil((AplicacionPerfilVO) aplicacionPerfil.toVO(2));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return aplicacionPerfilAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilVO createAplicacionPerfil(UserContext userContext,
			AplicacionPerfilVO aplicacionPerfilVO)
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
			aplicacionPerfilVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplicacionPerfil aplicacionPerfil = new AplicacionPerfil();

			aplicacionPerfil.setAplicacion(Aplicacion.getById(aplicacionPerfilVO.getAplicacion().getId()));
			aplicacionPerfil.setDescripcion(aplicacionPerfilVO.getDescripcion());
			aplicacionPerfil.setTipoFormulario(TipoFormulario.getByIdNull(aplicacionPerfilVO.getTipoFormulario().getId()));


			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacionPerfil = ApmAplicacionManager.getInstance().createAplicacionPerfil(aplicacionPerfil);

			if (aplicacionPerfil.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				aplicacionPerfilVO = (AplicacionPerfilVO) aplicacionPerfil.toVO(0, false);
			}
			aplicacionPerfil.passErrorMessages(aplicacionPerfilVO);

			log.debug(funcName + ": exit");
			return aplicacionPerfilVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)	tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilVO updateAplicacionPerfil(UserContext userContext,
			AplicacionPerfilVO aplicacionPerfilVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplicacionPerfilVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplicacionPerfil aplicacionPerfil = AplicacionPerfil.getById(aplicacionPerfilVO.getId());

			aplicacionPerfil.setAplicacion(Aplicacion.getById(aplicacionPerfilVO.getAplicacion().getId()));
			aplicacionPerfil.setDescripcion(aplicacionPerfilVO.getDescripcion());
			aplicacionPerfil.setTipoFormulario(TipoFormulario.getByIdNull(aplicacionPerfilVO.getTipoFormulario().getId()));


			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacionPerfil = ApmAplicacionManager.getInstance().updateAplicacionPerfil(aplicacionPerfil);

			if (aplicacionPerfil.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				aplicacionPerfilVO = (AplicacionPerfilVO) aplicacionPerfil.toVO(1, false);
			}
			aplicacionPerfil.passErrorMessages(aplicacionPerfilVO);

			log.debug(funcName + ": exit");
			return aplicacionPerfilVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilVO deleteAplicacionPerfil(UserContext userContext,
			AplicacionPerfilVO aplicacionPerfilVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplicacionPerfilVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			AplicacionPerfil aplicacionPerfil = AplicacionPerfil.getById(aplicacionPerfilVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad de otro bean
			aplicacionPerfil = ApmAplicacionManager.getInstance().deleteAplicacionPerfil(aplicacionPerfil);

			if (aplicacionPerfil.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				aplicacionPerfilVO = (AplicacionPerfilVO) aplicacionPerfil.toVO(1, false);
			}
			aplicacionPerfil.passErrorMessages(aplicacionPerfilVO);

			log.debug(funcName + ": exit");
			return aplicacionPerfilVO;
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

	// ---> APM Aplicacion Perfil Seccion
	public AplicacionPerfilSeccionAdapter getAplicacionPerfilSeccionAdapterForView(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionPerfilSeccion aplicacionPerfilSeccion = AplicacionPerfilSeccion.getById(commonKey.getId());

			AplicacionPerfilSeccionAdapter aplicacionPerfilSeccionAdapter = new AplicacionPerfilSeccionAdapter();
			aplicacionPerfilSeccionAdapter.setAplicacionPerfilSeccion((AplicacionPerfilSeccionVO) aplicacionPerfilSeccion.toVO(2, false));

			log.debug(funcName + ": exit");
			return aplicacionPerfilSeccionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilSeccionAdapter getAplicacionPerfilSeccionAdapterForCreate(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionPerfilSeccionAdapter aplicacionPerfilSeccionAdapter = new AplicacionPerfilSeccionAdapter();

			// Lista de Secciones
			// Descarto las que ya fueron asignadas a este perfil
			List<Seccion> listSeccion = ApmDAOFactory.getSeccionDAO().getListExcluded(commonKey.getId());
			aplicacionPerfilSeccionAdapter.setListSeccion(ListUtilBean.toVO(listSeccion, 0, false,
					new SeccionVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			aplicacionPerfilSeccionAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));
			aplicacionPerfilSeccionAdapter.getAplicacionPerfilSeccion().getAplicacionPerfil().setId(commonKey.getId());

			log.debug(funcName + ": exit");
			return aplicacionPerfilSeccionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilSeccionAdapter getAplicacionPerfilSeccionAdapterParam(
			UserContext userContext, 
			AplicacionPerfilSeccionAdapter aplicacionPerfilSeccionAdapter)	throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplicacionPerfilSeccionAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return aplicacionPerfilSeccionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilSeccionAdapter getAplicacionPerfilSeccionAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyAplicacionPerfilSeccion)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionPerfilSeccion aplicacionPerfilSeccion = AplicacionPerfilSeccion
					.getById(commonKeyAplicacionPerfilSeccion.getId());

			AplicacionPerfilSeccionAdapter aplicacionPerfilSeccionAdapter = new AplicacionPerfilSeccionAdapter();
			aplicacionPerfilSeccionAdapter.setAplicacionPerfilSeccion((AplicacionPerfilSeccionVO) aplicacionPerfilSeccion.toVO(2));

			// Lista de Secciones
			// Descarto las que ya fueron asignadas a este perfil
			List<Seccion> listSeccion = new ArrayList<>();
			listSeccion.add(aplicacionPerfilSeccion.getSeccion());
			listSeccion.addAll(ApmDAOFactory.getSeccionDAO().getListExcluded(aplicacionPerfilSeccion.getAplicacionPerfil().getId()));
			aplicacionPerfilSeccionAdapter.setListSeccion(ListUtilBean.toVO(listSeccion, 0, false));

			aplicacionPerfilSeccionAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));
			aplicacionPerfilSeccionAdapter.getAplicacionPerfilSeccion()
			.getAplicacionPerfil().setId(commonKeyAplicacionPerfilSeccion.getId());

			log.debug(funcName + ": exit");
			return aplicacionPerfilSeccionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilSeccionVO createAplicacionPerfilSeccion(UserContext userContext, 
			AplicacionPerfilSeccionVO aplicacionPerfilSeccionVO)throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplicacionPerfilSeccionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplicacionPerfilSeccion aplicacionPerfilSeccion = new AplicacionPerfilSeccion();

			aplicacionPerfilSeccion.setSeccion(Seccion.getByIdNull(aplicacionPerfilSeccionVO.getSeccion().getId()));
			aplicacionPerfilSeccion.setAplicacionPerfil(AplicacionPerfil.getByIdNull(aplicacionPerfilSeccionVO.getAplicacionPerfil().getId()));
			aplicacionPerfilSeccion.setOrden(aplicacionPerfilSeccionVO.getOrden());
			aplicacionPerfilSeccion.setNoDesplegar(aplicacionPerfilSeccionVO.getNoDesplegar().getBussId());
			aplicacionPerfilSeccion.setOpcional(aplicacionPerfilSeccionVO.getOpcional().getBussId());
			aplicacionPerfilSeccion.setPermiteImpresion(aplicacionPerfilSeccionVO.getPermiteImpresion().getBussId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacionPerfilSeccion = ApmAplicacionManager.getInstance().createAplicacionPerfilSeccion(aplicacionPerfilSeccion);
			if (aplicacionPerfilSeccion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				aplicacionPerfilSeccionVO = (AplicacionPerfilSeccionVO) aplicacionPerfilSeccion.toVO(1, false);
			}
			aplicacionPerfilSeccion.passErrorMessages(aplicacionPerfilSeccionVO);

			log.debug(funcName + ": exit");
			return aplicacionPerfilSeccionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilSeccionVO updateAplicacionPerfilSeccion(
			UserContext userContext,
			AplicacionPerfilSeccionVO aplicacionPerfilSeccionVO)
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
			aplicacionPerfilSeccionVO.clearErrorMessages();

			AplicacionPerfilSeccion aplicacionPerfilSeccion = AplicacionPerfilSeccion
					.getById(aplicacionPerfilSeccionVO.getId());

			// Copiado de propiadades de VO al BO

			aplicacionPerfilSeccion.setSeccion(Seccion
					.getById(aplicacionPerfilSeccionVO.getSeccion().getId()));
			aplicacionPerfilSeccion.setOrden(aplicacionPerfilSeccionVO
					.getOrden());
			aplicacionPerfilSeccion.setNoDesplegar(aplicacionPerfilSeccionVO
					.getNoDesplegar().getBussId());
			aplicacionPerfilSeccion.setOpcional(aplicacionPerfilSeccionVO
					.getOpcional().getBussId());

			aplicacionPerfilSeccion.setPermiteImpresion(aplicacionPerfilSeccionVO
					.getPermiteImpresion().getBussId());


			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacionPerfilSeccion = ApmAplicacionManager.getInstance()
					.updateAplicacionPerfilSeccion(aplicacionPerfilSeccion);

			if (aplicacionPerfilSeccion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionPerfilSeccionVO = (AplicacionPerfilSeccionVO) aplicacionPerfilSeccion
						.toVO(2, false);
			}
			aplicacionPerfilSeccion
			.passErrorMessages(aplicacionPerfilSeccionVO);

			log.debug(funcName + ": exit");
			return aplicacionPerfilSeccionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilSeccionVO deleteAplicacionPerfilSeccion(
			UserContext userContext,
			AplicacionPerfilSeccionVO aplicacionPerfilSeccionVO)
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
			aplicacionPerfilSeccionVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			AplicacionPerfilSeccion aplicacionPerfilSeccion = AplicacionPerfilSeccion
					.getById(aplicacionPerfilSeccionVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			aplicacionPerfilSeccion = ApmAplicacionManager.getInstance()
					.deleteAplicacionPerfilSeccion(aplicacionPerfilSeccion);

			if (aplicacionPerfilSeccion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionPerfilSeccionVO = (AplicacionPerfilSeccionVO) aplicacionPerfilSeccion
						.toVO(1, false);
			}
			aplicacionPerfilSeccion
			.passErrorMessages(aplicacionPerfilSeccionVO);

			log.debug(funcName + ": exit");
			return aplicacionPerfilSeccionVO;
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

	// ---> APM Aplicacion Perfil Seccion

	// ---> APM Apl Perfil Seccion Campo
	public AplPerfilSeccionCampoAdapter getAplPerfilSeccionCampoAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplPerfilSeccionCampo aplPerfilSeccionCampo = AplPerfilSeccionCampo
					.getById(commonKey.getId());

			AplPerfilSeccionCampoAdapter aplPerfilSeccionCampoAdapter = new AplPerfilSeccionCampoAdapter();
			aplPerfilSeccionCampoAdapter
			.setAplPerfilSeccionCampo((AplPerfilSeccionCampoVO) aplPerfilSeccionCampo
					.toVO(3, false));

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoAdapter getAplPerfilSeccionCampoAdapterForCreate(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplPerfilSeccionCampoAdapter aplPerfilSeccionCampoAdapter = new AplPerfilSeccionCampoAdapter();

			AplicacionPerfilSeccion aplicacionPerfilSeccion = AplicacionPerfilSeccion.getById(commonKey.getId());

			aplPerfilSeccionCampoAdapter.getAplPerfilSeccionCampo().setAplicacionPerfilSeccion(
					(AplicacionPerfilSeccionVO) aplicacionPerfilSeccion.toVO(2, false));

			// aplPerfilSeccionCampoAdapter.getAplPerfilSeccionCampo().getAplicacionPerfilSeccion().setId(commonKey.getId());
			List<Campo> listCampo = ApmDAOFactory.getCampoDAO().getListExcluded(commonKey.getId());
			aplPerfilSeccionCampoAdapter.setListCampo(ListUtilBean.toVO(listCampo, 0, false, new CampoVO(-1,StringUtil.SELECT_OPCION_SELECCIONAR)));
			aplPerfilSeccionCampoAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoAdapter getAplPerfilSeccionCampoAdapterParam(
			UserContext userContext,
			AplPerfilSeccionCampoAdapter aplPerfilSeccionCampoAdapter)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplPerfilSeccionCampoAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoAdapter getAplPerfilSeccionCampoAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyAplPerfilSeccionCampo) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplPerfilSeccionCampo aplPerfilSeccionCampo = AplPerfilSeccionCampo.getById(commonKeyAplPerfilSeccionCampo.getId());

			AplPerfilSeccionCampoAdapter aplPerfilSeccionCampoAdapter = new AplPerfilSeccionCampoAdapter();
			aplPerfilSeccionCampoAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));

			List<Campo> listCampo = new ArrayList<>();
			listCampo.add(aplPerfilSeccionCampo.getCampo());
			listCampo.addAll(ApmDAOFactory.getCampoDAO().getListExcluded(aplPerfilSeccionCampo.getAplicacionPerfilSeccion().getId()));
			//
			aplPerfilSeccionCampoAdapter.setListCampo(ListUtilBean.toVO(listCampo, 2, false));
			aplPerfilSeccionCampoAdapter.setAplPerfilSeccionCampo((AplPerfilSeccionCampoVO) aplPerfilSeccionCampo.toVO(3));

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoVO createAplPerfilSeccionCampo(	UserContext userContext,
			AplPerfilSeccionCampoVO aplPerfilSeccionCampoVO) throws DemodaServiceException {

		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplPerfilSeccionCampoVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplPerfilSeccionCampo aplPerfilSeccionCampo = new AplPerfilSeccionCampo();

			Campo campo = Campo.getByIdNull(aplPerfilSeccionCampoVO.getCampo().getId());

			aplPerfilSeccionCampo.setCampo(campo);
			aplPerfilSeccionCampo.setOrden(aplPerfilSeccionCampoVO.getOrden());
			aplPerfilSeccionCampo.setSoloLectura(aplPerfilSeccionCampoVO.getSoloLectura().getBussId());
			aplPerfilSeccionCampo.setAplicacionPerfilSeccion(
					AplicacionPerfilSeccion.getByIdNull(aplPerfilSeccionCampoVO.getAplicacionPerfilSeccion().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplPerfilSeccionCampo = ApmAplicacionManager.getInstance().createAplPerfilSeccionCampo(aplPerfilSeccionCampo);

			if (aplPerfilSeccionCampo.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				aplPerfilSeccionCampoVO = (AplPerfilSeccionCampoVO) aplPerfilSeccionCampo.toVO(0, false);
			}
			aplPerfilSeccionCampo.passErrorMessages(aplPerfilSeccionCampoVO);

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoVO updateAplPerfilSeccionCampo(UserContext userContext,
			AplPerfilSeccionCampoVO aplPerfilSeccionCampoVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplPerfilSeccionCampoVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplPerfilSeccionCampo aplPerfilSeccionCampo = AplPerfilSeccionCampo
					.getById(aplPerfilSeccionCampoVO.getId());

			Campo campo = Campo.getById(aplPerfilSeccionCampoVO.getCampo().getId());

			aplPerfilSeccionCampo.setCampo(campo);
			aplPerfilSeccionCampo.setOrden(aplPerfilSeccionCampoVO.getOrden());
			aplPerfilSeccionCampo.setSoloLectura(aplPerfilSeccionCampoVO.getSoloLectura().getBussId());
			aplPerfilSeccionCampo.setAplicacionPerfilSeccion(
					AplicacionPerfilSeccion.getById(aplPerfilSeccionCampoVO.getAplicacionPerfilSeccion().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplPerfilSeccionCampo = ApmAplicacionManager.getInstance()
					.updateAplPerfilSeccionCampo(aplPerfilSeccionCampo);

			if (aplPerfilSeccionCampo.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplPerfilSeccionCampoVO = (AplPerfilSeccionCampoVO) aplPerfilSeccionCampo
						.toVO(0, false);
			}
			aplPerfilSeccionCampo.passErrorMessages(aplPerfilSeccionCampoVO);

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoVO deleteAplPerfilSeccionCampo(
			UserContext userContext,
			AplPerfilSeccionCampoVO aplPerfilSeccionCampoVO)
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
			aplPerfilSeccionCampoVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			AplPerfilSeccionCampo aplPerfilSeccionCampo = AplPerfilSeccionCampo
					.getById(aplPerfilSeccionCampoVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			aplPerfilSeccionCampo = ApmAplicacionManager.getInstance()
					.deleteAplPerfilSeccionCampo(aplPerfilSeccionCampo);

			if (aplPerfilSeccionCampo.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplPerfilSeccionCampoVO = (AplPerfilSeccionCampoVO) aplPerfilSeccionCampo
						.toVO(0, false);
			}
			aplPerfilSeccionCampo.passErrorMessages(aplPerfilSeccionCampoVO);

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoVO;
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

	// ---> APM Apl Perfil Seccion Campo

	// ---> APM Apl Perfil Seccion Campo Valor
	public AplPerfilSeccionCampoValorAdapter getAplPerfilSeccionCampoValorAdapterForView(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplPerfilSeccionCampoValor aplPerfilSeccionCampoValor = AplPerfilSeccionCampoValor.getById(commonKey.getId());

			AplPerfilSeccionCampoValorAdapter aplPerfilSeccionCampoValorAdapter = new AplPerfilSeccionCampoValorAdapter();
			aplPerfilSeccionCampoValorAdapter.setAplPerfilSeccionCampoValor((AplPerfilSeccionCampoValorVO) aplPerfilSeccionCampoValor.toVO(3));

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoValorAdapter getAplPerfilSeccionCampoValorAdapterForCreate(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplPerfilSeccionCampoValorAdapter aplPerfilSeccionCampoValorAdapter = new AplPerfilSeccionCampoValorAdapter();

			AplPerfilSeccionCampo aplPerfilSeccionCampo = AplPerfilSeccionCampo.getById(commonKey.getId());
			// TODO: ver niveles de VO!!!
			aplPerfilSeccionCampoValorAdapter.getAplPerfilSeccionCampoValor()
			.setAplPerfilSeccionCampo((AplPerfilSeccionCampoVO) aplPerfilSeccionCampo.toVO(4, false));
			//
			List<CampoValor> listCampoValor =  ApmDAOFactory.getCampoValorDAO().getListExcluded(aplPerfilSeccionCampo);

			aplPerfilSeccionCampoValorAdapter.setListCampoValor(ListUtilBean.toVO(listCampoValor, 3, false));
			aplPerfilSeccionCampoValorAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoValorAdapter getAplPerfilSeccionCampoValorAdapterParam(
			UserContext userContext,
			AplPerfilSeccionCampoValorAdapter aplPerfilSeccionCampoValorAdapter)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplPerfilSeccionCampoValorAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoValorAdapter getAplPerfilSeccionCampoValorAdapterForUpdate(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplPerfilSeccionCampoValor aplPerfilSeccionCampoValor = AplPerfilSeccionCampoValor.getById(commonKey.getId());
			AplPerfilSeccionCampoValorAdapter aplPerfilSeccionCampoValorAdapter = new AplPerfilSeccionCampoValorAdapter();
			aplPerfilSeccionCampoValorAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));

			List<CampoValor> listCampoValor = new ArrayList<>();
			listCampoValor.add(aplPerfilSeccionCampoValor.getCampoValor());
			listCampoValor.addAll(ApmDAOFactory.getCampoValorDAO().getListExcluded(aplPerfilSeccionCampoValor.getAplPerfilSeccionCampo()));
			//
			aplPerfilSeccionCampoValorAdapter.setListCampoValor(ListUtilBean.toVO(listCampoValor, 0 , false));

			AplPerfilSeccionCampoValorVO aplPerfilSeccionCampoValorVO = 
					(AplPerfilSeccionCampoValorVO) aplPerfilSeccionCampoValor.toVO(4,false);
			aplPerfilSeccionCampoValorVO.setListAplPerfilSeccionCampoValorOpcion(
					ListUtilBean.toVO(aplPerfilSeccionCampoValor.getListAplPerfilSeccionCampoValorOpcion(), 1 , false));
			aplPerfilSeccionCampoValorAdapter.setAplPerfilSeccionCampoValor(aplPerfilSeccionCampoValorVO);

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoValorVO createAplPerfilSeccionCampoValor(
			UserContext userContext, AplPerfilSeccionCampoValorVO aplPerfilSeccionCampoValorVO)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplPerfilSeccionCampoValorVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplPerfilSeccionCampoValor aplPerfilSeccionCampoValor = new AplPerfilSeccionCampoValor();

			CampoValor campoValor = CampoValor.getByIdNull(aplPerfilSeccionCampoValorVO.getCampoValor().getId());

			aplPerfilSeccionCampoValor.setCampoValor(campoValor);
			aplPerfilSeccionCampoValor.setOrden(aplPerfilSeccionCampoValorVO.getOrden());
			aplPerfilSeccionCampoValor.setAplPerfilSeccionCampo(AplPerfilSeccionCampo.getByIdNull(aplPerfilSeccionCampoValorVO.getAplPerfilSeccionCampo().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplPerfilSeccionCampoValor = ApmAplicacionManager.getInstance()	.createAplPerfilSeccionCampoValor(aplPerfilSeccionCampoValor);
			if (aplPerfilSeccionCampoValor.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				aplPerfilSeccionCampoValorVO = (AplPerfilSeccionCampoValorVO) aplPerfilSeccionCampoValor.toVO(0, false);
			}
			aplPerfilSeccionCampoValor.passErrorMessages(aplPerfilSeccionCampoValorVO);

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)	tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoValorVO updateAplPerfilSeccionCampoValor(
			UserContext userContext, AplPerfilSeccionCampoValorVO aplPerfilSeccionCampoValorVO)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplPerfilSeccionCampoValorVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplPerfilSeccionCampoValor aplPerfilSeccionCampoValor = AplPerfilSeccionCampoValor
					.getById(aplPerfilSeccionCampoValorVO.getId());

			CampoValor campoValor = CampoValor.getByIdNull(aplPerfilSeccionCampoValorVO.getCampoValor().getId());

			aplPerfilSeccionCampoValor.setCampoValor(campoValor);
			aplPerfilSeccionCampoValor.setOrden(aplPerfilSeccionCampoValorVO.getOrden());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplPerfilSeccionCampoValor = ApmAplicacionManager.getInstance().updateAplPerfilSeccionCampoValor(aplPerfilSeccionCampoValor);

			if (aplPerfilSeccionCampoValor.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");

				aplPerfilSeccionCampoValorVO = (AplPerfilSeccionCampoValorVO) aplPerfilSeccionCampoValor.toVO(0, false);
			}
			aplPerfilSeccionCampoValor.passErrorMessages(aplPerfilSeccionCampoValorVO);

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoValorVO deleteAplPerfilSeccionCampoValor(
			UserContext userContext,
			AplPerfilSeccionCampoValorVO aplPerfilSeccionCampoValorVO)
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
			aplPerfilSeccionCampoValorVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			AplPerfilSeccionCampoValor aplPerfilSeccionCampoValor = AplPerfilSeccionCampoValor
					.getById(aplPerfilSeccionCampoValorVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			aplPerfilSeccionCampoValor = ApmAplicacionManager.getInstance()
					.deleteAplPerfilSeccionCampoValor(
							aplPerfilSeccionCampoValor);

			if (aplPerfilSeccionCampoValor.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplPerfilSeccionCampoValorVO = (AplPerfilSeccionCampoValorVO) aplPerfilSeccionCampoValor
						.toVO(0, false);
			}
			aplPerfilSeccionCampoValor
			.passErrorMessages(aplPerfilSeccionCampoValorVO);

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorVO;
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

	// ---> APM Apl Perfil Seccion Campo Valor

	// ---> APM Apl Perfil Seccion Campo Valor Opcion
	public AplPerfilSeccionCampoValorOpcionAdapter getAplPerfilSeccionCampoValorOpcionAdapterForView(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplPerfilSeccionCampoValorOpcion aplPerfilSeccionCampoValorOpcion = AplPerfilSeccionCampoValorOpcion.getById(commonKey.getId());

			AplPerfilSeccionCampoValorOpcionAdapter aplPerfilSeccionCampoValorOpcionAdapter = new AplPerfilSeccionCampoValorOpcionAdapter();
			aplPerfilSeccionCampoValorOpcionAdapter.setAplPerfilSeccionCampoValorOpcion(
					(AplPerfilSeccionCampoValorOpcionVO) aplPerfilSeccionCampoValorOpcion.toVO(1));

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorOpcionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoValorOpcionAdapter getAplPerfilSeccionCampoValorOpcionAdapterForCreate(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplPerfilSeccionCampoValorOpcionAdapter aplPerfilSeccionCampoValorOpcionAdapter = new AplPerfilSeccionCampoValorOpcionAdapter();

			AplPerfilSeccionCampoValor aplPerfilSeccionCampoValor = AplPerfilSeccionCampoValor.getById(commonKey.getId());

			aplPerfilSeccionCampoValorOpcionAdapter.getAplPerfilSeccionCampoValorOpcion()
			.setAplPerfilSeccionCampoValor((AplPerfilSeccionCampoValorVO) aplPerfilSeccionCampoValor.toVO(4, false));

			List<CampoValorOpcion> listCampoValorOpcion = ApmDAOFactory.getCampoValorOpcionDAO().getListExcluded(aplPerfilSeccionCampoValor);

			aplPerfilSeccionCampoValorOpcionAdapter
			.setListCampoValorOpcion(ListUtilBean.toVO(
					listCampoValorOpcion, 0, false, new CampoValorOpcionVO(-1,StringUtil.SELECT_OPCION_SELECCIONAR)));
			aplPerfilSeccionCampoValorOpcionAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorOpcionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoValorOpcionAdapter getAplPerfilSeccionCampoValorOpcionAdapterParam(
			UserContext userContext,
			AplPerfilSeccionCampoValorOpcionAdapter aplPerfilSeccionCampoValorOpcionAdapter)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplPerfilSeccionCampoValorOpcionAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorOpcionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoValorOpcionAdapter getAplPerfilSeccionCampoValorOpcionAdapterForUpdate(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplPerfilSeccionCampoValorOpcion aplPerfilSeccionCampoValorOpcion = AplPerfilSeccionCampoValorOpcion.getById(commonKey.getId());

			AplPerfilSeccionCampoValorOpcionAdapter aplPerfilSeccionCampoValorOpcionAdapter = new AplPerfilSeccionCampoValorOpcionAdapter();

			List<CampoValorOpcion> listCampoValorOpcion = new ArrayList<>();
			listCampoValorOpcion.addAll(ApmDAOFactory.getCampoValorOpcionDAO().getListExcluded(aplPerfilSeccionCampoValorOpcion.getAplPerfilSeccionCampoValor()));

			aplPerfilSeccionCampoValorOpcionAdapter.setAplPerfilSeccionCampoValorOpcion(
					(AplPerfilSeccionCampoValorOpcionVO) aplPerfilSeccionCampoValorOpcion.toVO(4,false));
			aplPerfilSeccionCampoValorOpcionAdapter.setListCampoValorOpcion(ListUtilBean.toVO(listCampoValorOpcion,4,false));

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorOpcionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoValorOpcionVO createAplPerfilSeccionCampoValorOpcion(
			UserContext userContext, 
			AplPerfilSeccionCampoValorOpcionVO aplPerfilSeccionCampoValorOpcionVO)	throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplPerfilSeccionCampoValorOpcionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplPerfilSeccionCampoValorOpcion aplPerfilSeccionCampoValorOpcion = new AplPerfilSeccionCampoValorOpcion();

			CampoValorOpcion campoValorOpcion = 
					CampoValorOpcion.getByIdNull(aplPerfilSeccionCampoValorOpcionVO.getCampoValorOpcion().getId());

			aplPerfilSeccionCampoValorOpcion.setCampoValorOpcion(campoValorOpcion);
			aplPerfilSeccionCampoValorOpcion.setOrden(aplPerfilSeccionCampoValorOpcionVO.getOrden());
			aplPerfilSeccionCampoValorOpcion.setAplPerfilSeccionCampoValor(AplPerfilSeccionCampoValor
					.getByIdNull(aplPerfilSeccionCampoValorOpcionVO.getAplPerfilSeccionCampoValor().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplPerfilSeccionCampoValorOpcion = ApmAplicacionManager
					.getInstance().createAplPerfilSeccionCampoValorOpcion(aplPerfilSeccionCampoValorOpcion);

			if (aplPerfilSeccionCampoValorOpcion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				aplPerfilSeccionCampoValorOpcionVO = (AplPerfilSeccionCampoValorOpcionVO) aplPerfilSeccionCampoValorOpcion.toVO(0, false);
			}
			aplPerfilSeccionCampoValorOpcion.passErrorMessages(aplPerfilSeccionCampoValorOpcionVO);

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorOpcionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoValorOpcionVO updateAplPerfilSeccionCampoValorOpcion(
			UserContext userContext,
			AplPerfilSeccionCampoValorOpcionVO aplPerfilSeccionCampoValorOpcionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplPerfilSeccionCampoValorOpcionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplPerfilSeccionCampoValorOpcion aplPerfilSeccionCampoValorOpcion = AplPerfilSeccionCampoValorOpcion
					.getById(aplPerfilSeccionCampoValorOpcionVO.getId());

			CampoValorOpcion campoValorOpcion = CampoValorOpcion
					.getById(aplPerfilSeccionCampoValorOpcionVO.getCampoValorOpcion().getId());

			aplPerfilSeccionCampoValorOpcion.setCampoValorOpcion(campoValorOpcion);
			aplPerfilSeccionCampoValorOpcion.setOrden(aplPerfilSeccionCampoValorOpcionVO.getOrden());

			aplPerfilSeccionCampoValorOpcion
			.setAplPerfilSeccionCampoValor(AplPerfilSeccionCampoValor
					.getById(aplPerfilSeccionCampoValorOpcionVO
							.getAplPerfilSeccionCampoValor().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplPerfilSeccionCampoValorOpcion = ApmAplicacionManager
					.getInstance().updateAplPerfilSeccionCampoValorOpcion(
							aplPerfilSeccionCampoValorOpcion);

			if (aplPerfilSeccionCampoValorOpcion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				aplPerfilSeccionCampoValorOpcionVO = 
						(AplPerfilSeccionCampoValorOpcionVO) aplPerfilSeccionCampoValorOpcion.toVO(0, false);
			}
			aplPerfilSeccionCampoValorOpcion.passErrorMessages(aplPerfilSeccionCampoValorOpcionVO);

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorOpcionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplPerfilSeccionCampoValorOpcionVO deleteAplPerfilSeccionCampoValorOpcion(
			UserContext userContext,
			AplPerfilSeccionCampoValorOpcionVO aplPerfilSeccionCampoValorOpcionVO)
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
			aplPerfilSeccionCampoValorOpcionVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			AplPerfilSeccionCampoValorOpcion aplPerfilSeccionCampoValorOpcion = AplPerfilSeccionCampoValorOpcion
					.getById(aplPerfilSeccionCampoValorOpcionVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			aplPerfilSeccionCampoValorOpcion = ApmAplicacionManager
					.getInstance().deleteAplPerfilSeccionCampoValorOpcion(
							aplPerfilSeccionCampoValorOpcion);

			if (aplPerfilSeccionCampoValorOpcion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplPerfilSeccionCampoValorOpcionVO = 
						(AplPerfilSeccionCampoValorOpcionVO) aplPerfilSeccionCampoValorOpcion.toVO(0, false);
			}
			aplPerfilSeccionCampoValorOpcion.passErrorMessages(aplPerfilSeccionCampoValorOpcionVO);

			log.debug(funcName + ": exit");
			return aplPerfilSeccionCampoValorOpcionVO;
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

	// ---> APM Apl Perfil Seccion Campo Valor Opcion

	// ---> APM Aplicacion Perfil Parametro
	public AplicacionPerfilParametroAdapter getAplicacionPerfilParametroAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionPerfilParametro aplicacionPerfilParametro = AplicacionPerfilParametro
					.getById(commonKey.getId());

			AplicacionPerfilParametroAdapter aplicacionPerfilParametroAdapter = new AplicacionPerfilParametroAdapter();
			aplicacionPerfilParametroAdapter
			.setAplicacionPerfilParametro((AplicacionPerfilParametroVO) aplicacionPerfilParametro
					.toVO(2, false));

			log.debug(funcName + ": exit");
			return aplicacionPerfilParametroAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilParametroAdapter getAplicacionPerfilParametroAdapterForCreate(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionPerfilParametroAdapter aplicacionPerfilParametroAdapter = new AplicacionPerfilParametroAdapter();

			aplicacionPerfilParametroAdapter.getAplicacionPerfilParametro()
			.getAplicacionPerfil().setId(commonKey.getId());

			//
			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return aplicacionPerfilParametroAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilParametroAdapter getAplicacionPerfilParametroAdapterParam(
			UserContext userContext,
			AplicacionPerfilParametroAdapter aplicacionPerfilParametroAdapter)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplicacionPerfilParametroAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return aplicacionPerfilParametroAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilParametroAdapter getAplicacionPerfilParametroAdapterForUpdate(
			UserContext userContext,
			CommonKey commonKeyAplicacionPerfilParametro)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionPerfilParametro aplicacionPerfilParametro = AplicacionPerfilParametro
					.getById(commonKeyAplicacionPerfilParametro.getId());

			AplicacionPerfilParametroAdapter aplicacionPerfilParametroAdapter = new AplicacionPerfilParametroAdapter();
			aplicacionPerfilParametroAdapter
			.setAplicacionPerfilParametro((AplicacionPerfilParametroVO) aplicacionPerfilParametro
					.toVO(2, false));

			aplicacionPerfilParametroAdapter.getAplicacionPerfilParametro()
			.getAplicacionPerfil()
			.setId(commonKeyAplicacionPerfilParametro.getId());

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return aplicacionPerfilParametroAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilParametroVO createAplicacionPerfilParametro(
			UserContext userContext,
			AplicacionPerfilParametroVO aplicacionPerfilParametroVO)
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
			aplicacionPerfilParametroVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplicacionPerfilParametro aplicacionPerfilParametro = new AplicacionPerfilParametro();
			aplicacionPerfilParametro.setCodigo(aplicacionPerfilParametroVO
					.getCodigo());
			aplicacionPerfilParametro
			.setDescripcion(aplicacionPerfilParametroVO
					.getDescripcion());
			aplicacionPerfilParametro.setValor(aplicacionPerfilParametroVO
					.getValor());
			aplicacionPerfilParametro.setAplicacionPerfil(AplicacionPerfil
					.getById(aplicacionPerfilParametroVO.getAplicacionPerfil()
							.getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacionPerfilParametro = ApmAplicacionManager.getInstance()
					.createAplicacionPerfilParametro(aplicacionPerfilParametro);

			if (aplicacionPerfilParametro.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionPerfilParametroVO = (AplicacionPerfilParametroVO) aplicacionPerfilParametro
						.toVO(1, false);
			}
			aplicacionPerfilParametro
			.passErrorMessages(aplicacionPerfilParametroVO);

			log.debug(funcName + ": exit");
			return aplicacionPerfilParametroVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilParametroVO updateAplicacionPerfilParametro(
			UserContext userContext,
			AplicacionPerfilParametroVO aplicacionPerfilParametroVO)
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
			aplicacionPerfilParametroVO.clearErrorMessages();

			AplicacionPerfilParametro aplicacionPerfilParametro = AplicacionPerfilParametro
					.getById(aplicacionPerfilParametroVO.getId());

			// Copiado de propiadades de VO al BO

			aplicacionPerfilParametro.setCodigo(aplicacionPerfilParametroVO
					.getCodigo());
			aplicacionPerfilParametro
			.setDescripcion(aplicacionPerfilParametroVO
					.getDescripcion());
			aplicacionPerfilParametro.setValor(aplicacionPerfilParametroVO
					.getValor());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacionPerfilParametro = ApmAplicacionManager.getInstance()
					.updateAplicacionPerfilParametro(aplicacionPerfilParametro);

			if (aplicacionPerfilParametro.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionPerfilParametroVO = (AplicacionPerfilParametroVO) aplicacionPerfilParametro
						.toVO(2, false);
			}
			aplicacionPerfilParametro
			.passErrorMessages(aplicacionPerfilParametroVO);

			log.debug(funcName + ": exit");
			return aplicacionPerfilParametroVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionPerfilParametroVO deleteAplicacionPerfilParametro(
			UserContext userContext,
			AplicacionPerfilParametroVO aplicacionPerfilParametroVO)
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
			aplicacionPerfilParametroVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			AplicacionPerfilParametro aplicacionPerfilParametro = AplicacionPerfilParametro
					.getById(aplicacionPerfilParametroVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			aplicacionPerfilParametro = ApmAplicacionManager.getInstance()
					.deleteAplicacionPerfilParametro(aplicacionPerfilParametro);

			if (aplicacionPerfilParametro.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionPerfilParametroVO = (AplicacionPerfilParametroVO) aplicacionPerfilParametro
						.toVO(1, false);
			}
			aplicacionPerfilParametro
			.passErrorMessages(aplicacionPerfilParametroVO);

			log.debug(funcName + ": exit");
			return aplicacionPerfilParametroVO;
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

	// ---> APM Aplicacion Perfil Parametro

	// ---> APM impresora
	public ImpresoraSearchPage getImpresoraSearchPageInit(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		ImpresoraSearchPage impresoraSearchPage = new ImpresoraSearchPage();
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			impresoraSearchPage.setListSiNo(SiNo.getList(SiNo.OpcionTodo));
			// Seteo de banderas
			if (userContext.getEsDGI()) {
				impresoraSearchPage.setListArea(ListUtilBean.toVO(Area
						.getListActivos(), 0, false, new AreaVO(-1,
								StringUtil.SELECT_OPCION_TODAS)));
				impresoraSearchPage.setEditarAreaEnabled(true);
			} else {
				AreaVO currentArea = new AreaVO(userContext.getIdArea()
						.intValue(), userContext.getDesArea());
				impresoraSearchPage.getImpresora().setArea(currentArea);
			}

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}

		return impresoraSearchPage;
	}

	public ImpresoraSearchPage getImpresoraSearchPageResult(
			UserContext userContext, ImpresoraSearchPage impresoraSearchPage)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			impresoraSearchPage.clearError();

			// Aqui obtiene lista de BOs
			List<Impresora> listImpresora = ApmDAOFactory.getImpresoraDAO()
					.getBySearchPage(impresoraSearchPage);

			// Aqui pasamos BO a VO
			impresoraSearchPage.setListResult(ListUtilBean.toVO(listImpresora,
					1, false));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return impresoraSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ImpresoraAdapter getImpresoraAdapterForView(UserContext userContext,
			CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Impresora impresora = Impresora.getById(commonKey.getId());

			ImpresoraAdapter impresoraAdapter = new ImpresoraAdapter();
			impresoraAdapter.setImpresora((ImpresoraVO) impresora
					.toVO(1, false));

			log.debug(funcName + ": exit");
			return impresoraAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ImpresoraAdapter getImpresoraAdapterForCreate(UserContext userContext)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			ImpresoraAdapter impresoraAdapter = new ImpresoraAdapter();

			// Seteo de banderas
			if (userContext.getEsDGI()) {
				impresoraAdapter.setListArea(ListUtilBean.toVO(Area
						.getListActivos(), 0, false, new AreaVO(-1,
								StringUtil.SELECT_OPCION_SELECCIONAR)));
				impresoraAdapter.setEditarAreaEnabled(true);
			} else {
				AreaVO currentArea = new AreaVO(userContext.getIdArea()
						.intValue(), userContext.getDesArea());
				impresoraAdapter.getImpresora().setArea(currentArea);
			}

			// Seteo la listas para combos, etc
			impresoraAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));

			log.debug(funcName + ": exit");
			return impresoraAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ImpresoraAdapter getImpresoraAdapterParam(UserContext userContext,
			ImpresoraAdapter impresoraAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			impresoraAdapter.clearError();

			log.debug(funcName + ": exit");
			return impresoraAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ImpresoraAdapter getImpresoraAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyImpresora)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Impresora impresora = Impresora.getById(commonKeyImpresora.getId());

			ImpresoraAdapter impresoraAdapter = new ImpresoraAdapter();

			impresoraAdapter.setImpresora((ImpresoraVO) impresora
					.toVO(1, false));

			// Seteo la lista para combo, valores, etc
			impresoraAdapter.setListSiNo(SiNo.getListSiNo(SiNo.SI));
			if (userContext.getEsDGI()) {
				impresoraAdapter.setEditarAreaEnabled(true);
				impresoraAdapter.setListArea(ListUtilBean.toVO(
						Area.getListActivos(), 0, false));
			}

			log.debug(funcName + ": exit");
			return impresoraAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ImpresoraVO createImpresora(UserContext userContext,
			ImpresoraVO impresoraVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			impresoraVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Impresora impresora = new Impresora();

			impresora.setDescripcion(impresoraVO.getDescripcion());
			impresora.setEstado(Estado.ACTIVO.getId());
			impresora.setNumeroUUID(impresoraVO.getNumeroUUID());
			impresora.setNumeroSerie(impresoraVO.getNumeroSerie());
			impresora.setArea(Area.getByIdNull(impresoraVO.getArea().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			impresora = ApmAplicacionManager.getInstance().createImpresora(
					impresora);

			if (impresora.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				impresoraVO = (ImpresoraVO) impresora.toVO(0, false);
			}
			impresora.passErrorMessages(impresoraVO);

			log.debug(funcName + ": exit");
			return impresoraVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ImpresoraVO updateImpresora(UserContext userContext,
			ImpresoraVO impresoraVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			impresoraVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Impresora impresora = Impresora.getById(impresoraVO.getId());

			if (!impresoraVO.validateVersion(impresora.getFechaUltMdf()))
				return impresoraVO;

			impresora.setDescripcion(impresoraVO.getDescripcion());
			impresora.setEstado(Estado.ACTIVO.getId());
			impresora.setNumeroUUID(impresoraVO.getNumeroUUID());
			impresora.setNumeroSerie(impresoraVO.getNumeroSerie());
			impresora.setArea(Area.getByIdNull(impresoraVO.getArea().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			impresora = ApmAplicacionManager.getInstance().updateImpresora(
					impresora);

			if (impresora.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				impresoraVO = (ImpresoraVO) impresora.toVO(0, false);
			}
			impresora.passErrorMessages(impresoraVO);

			log.debug(funcName + ": exit");
			return impresoraVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ImpresoraVO deleteImpresora(UserContext userContext,
			ImpresoraVO impresoraVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			impresoraVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			Impresora impresora = Impresora.getById(impresoraVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			impresora = ApmAplicacionManager.getInstance().deleteImpresora(
					impresora);

			if (impresora.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				impresoraVO = (ImpresoraVO) impresora.toVO(0, false);
			}
			impresora.passErrorMessages(impresoraVO);

			log.debug(funcName + ": exit");
			return impresoraVO;
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

	// ---> ABM Aplicacion
	public AplicacionSearchPage getAplicacionSearchPageInit(
			UserContext userContext) throws DemodaServiceException {
		return new AplicacionSearchPage();
	}

	//
	public AplicacionSearchPage getAplicacionSearchPageResult(
			UserContext userContext, AplicacionSearchPage aplicacionSearchPage)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplicacionSearchPage.clearError();

			// Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<Aplicacion> listAplicacion = ApmDAOFactory.getAplicacionDAO()
					.getBySearchPage(aplicacionSearchPage);

			// Aqui se podria iterar la lista de BO para setear banderas en VOs
			// y otras cosas del negocio.

			// Aqui pasamos BO a VO
			aplicacionSearchPage.setListResult(ListUtilBean.toVO(
					listAplicacion, 1));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return aplicacionSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionAdapter getAplicacionAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Aplicacion aplicacion = Aplicacion.getById(commonKey.getId());

			AplicacionAdapter aplicacionAdapter = new AplicacionAdapter();
			aplicacionAdapter.setAplicacion((AplicacionVO) aplicacion.toVO(1));

			log.debug(funcName + ": exit");
			return aplicacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionAdapter getAplicacionAdapterForCreate(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionAdapter aplicacionAdapter = new AplicacionAdapter();

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return aplicacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionAdapter getAplicacionAdapterParam(UserContext userContext,
			AplicacionAdapter aplicacionAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplicacionAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return aplicacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionAdapter getAplicacionAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyAplicacion)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Aplicacion aplicacion = Aplicacion.getById(commonKeyAplicacion
					.getId());

			AplicacionAdapter aplicacionAdapter = new AplicacionAdapter();
			aplicacionAdapter.setAplicacion((AplicacionVO) aplicacion.toVO(2));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return aplicacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionVO createAplicacion(UserContext userContext,
			AplicacionVO aplicacionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplicacionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Aplicacion aplicacion = new Aplicacion();

			aplicacion.setDescripcion(aplicacionVO.getDescripcion());
			aplicacion.setCodigo(aplicacionVO.getCodigo());
			aplicacion.setPackageName(aplicacionVO.getPackageName());
			aplicacion.setClassName(aplicacionVO.getClassName());
			aplicacion.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacion = ApmAplicacionManager.getInstance().createAplicacion(
					aplicacion);

			if (aplicacion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionVO = (AplicacionVO) aplicacion.toVO(0, false);
			}
			aplicacion.passErrorMessages(aplicacionVO);

			log.debug(funcName + ": exit");
			return aplicacionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionVO updateAplicacion(UserContext userContext,
			AplicacionVO aplicacionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplicacionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Aplicacion aplicacion = Aplicacion.getById(aplicacionVO.getId());

			if (!aplicacionVO.validateVersion(aplicacion.getFechaUltMdf()))
				return aplicacionVO;

			aplicacion.setDescripcion(aplicacionVO.getDescripcion());
			aplicacion.setCodigo(aplicacionVO.getCodigo());
			aplicacion.setPackageName(aplicacionVO.getPackageName());
			aplicacion.setClassName(aplicacionVO.getClassName());
			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacion = ApmAplicacionManager.getInstance().updateAplicacion(
					aplicacion);

			if (aplicacion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionVO = (AplicacionVO) aplicacion.toVO(0, false);
			}
			aplicacion.passErrorMessages(aplicacionVO);

			log.debug(funcName + ": exit");
			return aplicacionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionVO deleteAplicacion(UserContext userContext,
			AplicacionVO aplicacionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplicacionVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			Aplicacion aplicacion = Aplicacion.getById(aplicacionVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			aplicacion = ApmAplicacionManager.getInstance().deleteAplicacion(
					aplicacion);

			if (aplicacion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionVO = (AplicacionVO) aplicacion.toVO(0, false);
			}
			aplicacion.passErrorMessages(aplicacionVO);

			log.debug(funcName + ": exit");
			return aplicacionVO;
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
	// <--- Aplicacion


	// >..... Aplicación Binario Version
	public AplicacionBinarioVersionAdapter getAplicacionBinarioVersionAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionBinarioVersion aplicacionBinarioVersion = AplicacionBinarioVersion
					.getById(commonKey.getId());

			AplicacionBinarioVersionAdapter aplicacionBinarioVersionAdapter = new AplicacionBinarioVersionAdapter();
			aplicacionBinarioVersionAdapter
			.setAplicacionBinarioVersion((AplicacionBinarioVersionVO) aplicacionBinarioVersion
					.toVO(1));

			log.debug(funcName + ": exit");
			return aplicacionBinarioVersionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionBinarioVersionAdapter getAplicacionBinarioVersionAdapterForCreate(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionBinarioVersionAdapter aplicacionBinarioVersionAdapter = new AplicacionBinarioVersionAdapter();

			List<AplicacionTipoBinario> listAplicacionTipoBinario = AplicacionTipoBinario
					.getListActivos();
			aplicacionBinarioVersionAdapter
			.setListAplicacionTipoBinario(ListUtilBean.toVO(
					listAplicacionTipoBinario, 0, false));

			aplicacionBinarioVersionAdapter.getAplicacionBinarioVersion()
			.getAplicacion().setId(commonKey.getId());
			aplicacionBinarioVersionAdapter.getAplicacionBinarioVersion()
			.setFecha(new Date());

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return aplicacionBinarioVersionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionBinarioVersionAdapter getAplicacionBinarioVersionAdapterParam(
			UserContext userContext,
			AplicacionBinarioVersionAdapter aplicacionBinarioVersionAdapter)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplicacionBinarioVersionAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return aplicacionBinarioVersionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionBinarioVersionAdapter getAplicacionBinarioVersionAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyAplicacionBinarioVersion)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionBinarioVersion aplicacionBinarioVersion = AplicacionBinarioVersion
					.getById(commonKeyAplicacionBinarioVersion.getId());

			AplicacionBinarioVersionAdapter aplicacionBinarioVersionAdapter = new AplicacionBinarioVersionAdapter();
			aplicacionBinarioVersionAdapter
			.setAplicacionBinarioVersion((AplicacionBinarioVersionVO) aplicacionBinarioVersion
					.toVO(1, false));

			// Seteo la lista para combo, valores, etc
			List<AplicacionTipoBinario> listAplicacionTipoBinario = AplicacionTipoBinario
					.getListActivos();
			aplicacionBinarioVersionAdapter
			.setListAplicacionTipoBinario(ListUtilBean.toVO(
					listAplicacionTipoBinario, 0, false));

			log.debug(funcName + ": exit");
			return aplicacionBinarioVersionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionBinarioVersionVO createAplicacionBinarioVersion(UserContext userContext,
			AplicacionBinarioVersionVO aplicacionBinarioVersionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplicacionBinarioVersionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplicacionBinarioVersion aplicacionBinarioVersion = new AplicacionBinarioVersion();

			aplicacionBinarioVersion.setEstado(Estado.ACTIVO.getId());
			aplicacionBinarioVersion.setAplicacionTipoBinario(AplicacionTipoBinario.getById(aplicacionBinarioVersionVO.getAplicacionTipoBinario().getId()));
			aplicacionBinarioVersion.setDescripcion(aplicacionBinarioVersionVO.getDescripcion());
			aplicacionBinarioVersion.setFecha(new Date());
			aplicacionBinarioVersion.setAplicacion(Aplicacion.getById(aplicacionBinarioVersionVO.getAplicacion().getId()));

			//
			String filePath = GaitParam.getFileSharePath() + File.separatorChar + "data" + File.separatorChar;
			String fileName = aplicacionBinarioVersionVO.getFileName();
			log.debug("uploaded file: "+fileName+" Size: "+aplicacionBinarioVersionVO.getFileData().length);
			//
			String name = fileName;
			if(name.contains("-")) name = fileName.split("-")[0];
			if(name.contains(".")) name = fileName.split("\\.")[0];
			String extension = fileName.substring(fileName.lastIndexOf('.'));
			//
			fileName = name + extension;

			log.debug("saved file: "+filePath+fileName+" Size: "+aplicacionBinarioVersionVO.getFileData().length);
			DemodaUtil.grabarArchivo(filePath+fileName, aplicacionBinarioVersionVO.getFileData(), false);

			if(extension.contains("apk")){
				aplicacionBinarioVersion.setUbicacion("/download/core/"+fileName);
			} else {
				aplicacionBinarioVersion.setUbicacion("/download/data/"+fileName);
			}
			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacionBinarioVersion = ApmAplicacionManager.getInstance().createAplicacionBinarioVersion(aplicacionBinarioVersion);

			if (aplicacionBinarioVersion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				aplicacionBinarioVersionVO = (AplicacionBinarioVersionVO) aplicacionBinarioVersion.toVO(0, false);
			}
			aplicacionBinarioVersion.passErrorMessages(aplicacionBinarioVersionVO);

			log.debug(funcName + ": exit");
			return aplicacionBinarioVersionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)	tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionBinarioVersionVO updateAplicacionBinarioVersion(
			UserContext userContext,AplicacionBinarioVersionVO aplicacionBinarioVersionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplicacionBinarioVersionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplicacionBinarioVersion aplicacionBinarioVersion = AplicacionBinarioVersion.getById(aplicacionBinarioVersionVO.getId());

			if (!aplicacionBinarioVersionVO.validateVersion(aplicacionBinarioVersion.getFechaUltMdf()))
				return aplicacionBinarioVersionVO;

			aplicacionBinarioVersion.setAplicacionTipoBinario(AplicacionTipoBinario.getById(aplicacionBinarioVersionVO.getAplicacionTipoBinario().getId()));
			aplicacionBinarioVersion.setDescripcion(aplicacionBinarioVersionVO.getDescripcion());
			aplicacionBinarioVersion.setFecha(new Date());
			aplicacionBinarioVersion.setAplicacion(Aplicacion.getById(aplicacionBinarioVersionVO.getAplicacion().getId()));

			//
			String filePath = GaitParam.getFileSharePath() + File.separatorChar + "data" + File.separatorChar;
			String fileName = aplicacionBinarioVersionVO.getFileName();
			log.debug("uploaded file: "+fileName+" Size: "+aplicacionBinarioVersionVO.getFileData().length);

			//
			String name = fileName;
			if(name.contains("-")) name = fileName.split("-")[0];
			if(name.contains(".")) name = fileName.split("\\.")[0];
			String extension = fileName.substring(fileName.lastIndexOf('.'));
			//
			fileName = name + extension;

			log.debug("saved file: "+filePath+fileName+" Size: "+aplicacionBinarioVersionVO.getFileData().length);
			DemodaUtil.grabarArchivo(filePath+fileName, aplicacionBinarioVersionVO.getFileData(), false);

			if(extension.contains("apk")){
				aplicacionBinarioVersion.setUbicacion("/download/core/"+fileName);
			} else {
				aplicacionBinarioVersion.setUbicacion("/download/data/"+fileName);
			}

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacionBinarioVersion = ApmAplicacionManager.getInstance().updateAplicacionBinarioVersion(aplicacionBinarioVersion);

			if (aplicacionBinarioVersion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
			} else {
				tx.commit();
				if (log.isDebugEnabled()) 
					log.debug(funcName + ": tx.commit");
				aplicacionBinarioVersionVO = (AplicacionBinarioVersionVO) aplicacionBinarioVersion.toVO(0, false);
			}
			aplicacionBinarioVersion.passErrorMessages(aplicacionBinarioVersionVO);

			log.debug(funcName + ": exit");
			return aplicacionBinarioVersionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionBinarioVersionVO deleteAplicacionBinarioVersion(
			UserContext userContext,
			AplicacionBinarioVersionVO aplicacionBinarioVersionVO)
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
			aplicacionBinarioVersionVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			AplicacionBinarioVersion aplicacionBinarioVersion = AplicacionBinarioVersion
					.getById(aplicacionBinarioVersionVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			aplicacionBinarioVersion = ApmAplicacionManager.getInstance()
					.deleteAplicacionBinarioVersion(aplicacionBinarioVersion);

			if (aplicacionBinarioVersion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionBinarioVersionVO = (AplicacionBinarioVersionVO) aplicacionBinarioVersion
						.toVO(0, false);
			}
			aplicacionBinarioVersion
			.passErrorMessages(aplicacionBinarioVersionVO);

			log.debug(funcName + ": exit");
			return aplicacionBinarioVersionVO;
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

	// Aplicacion PArametro

	public AplicacionParametroAdapter getAplicacionParametroAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionParametro aplicacionParametro = AplicacionParametro
					.getById(commonKey.getId());

			AplicacionParametroAdapter aplicacionParametroAdapter = new AplicacionParametroAdapter();
			aplicacionParametroAdapter
			.setAplicacionParametro((AplicacionParametroVO) aplicacionParametro
					.toVO(1));

			log.debug(funcName + ": exit");
			return aplicacionParametroAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionParametroAdapter getAplicacionParametroAdapterForCreate(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionParametroAdapter aplicacionParametroAdapter = new AplicacionParametroAdapter();
			aplicacionParametroAdapter.getAplicacionParametro().getAplicacion()
			.setId(commonKey.getId());

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return aplicacionParametroAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionParametroAdapter getAplicacionParametroAdapterParam(
			UserContext userContext,
			AplicacionParametroAdapter aplicacionParametroAdapter)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplicacionParametroAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return aplicacionParametroAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionParametroAdapter getAplicacionParametroAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyAplicacionParametro)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionParametro aplicacionParametro = AplicacionParametro
					.getById(commonKeyAplicacionParametro.getId());

			AplicacionParametroAdapter aplicacionParametroAdapter = new AplicacionParametroAdapter();
			aplicacionParametroAdapter
			.setAplicacionParametro((AplicacionParametroVO) aplicacionParametro
					.toVO(1));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return aplicacionParametroAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionParametroVO createAplicacionParametro(
			UserContext userContext, AplicacionParametroVO aplicacionParametroVO)
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
			aplicacionParametroVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplicacionParametro aplicacionParametro = new AplicacionParametro();
			aplicacionParametro.setCodigo(aplicacionParametroVO.getCodigo());
			aplicacionParametro.setDescripcion(aplicacionParametroVO
					.getDescripcion());
			aplicacionParametro.setValor(aplicacionParametroVO.getValor());
			Aplicacion aplicacion = Aplicacion.getById(aplicacionParametroVO
					.getAplicacion().getId());

			aplicacionParametro.setAplicacion(aplicacion);

			aplicacionParametro.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacionParametro = ApmAplicacionManager.getInstance()
					.createAplicacionParametro(aplicacionParametro);

			if (aplicacionParametro.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionParametroVO = (AplicacionParametroVO) aplicacionParametro
						.toVO(0, false);
			}
			aplicacionParametro.passErrorMessages(aplicacionParametroVO);

			log.debug(funcName + ": exit");
			return aplicacionParametroVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionParametroVO updateAplicacionParametro(
			UserContext userContext, AplicacionParametroVO aplicacionParametroVO)
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
			aplicacionParametroVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplicacionParametro aplicacionParametro = AplicacionParametro
					.getById(aplicacionParametroVO.getId());

			if (!aplicacionParametroVO.validateVersion(aplicacionParametro
					.getFechaUltMdf()))
				return aplicacionParametroVO;

			aplicacionParametro.setCodigo(aplicacionParametroVO.getCodigo());
			aplicacionParametro.setDescripcion(aplicacionParametroVO
					.getDescripcion());
			Aplicacion aplicacion = Aplicacion.getById(aplicacionParametroVO
					.getAplicacion().getId());
			aplicacionParametro.setValor(aplicacionParametroVO.getValor());
			aplicacionParametro.setAplicacion(aplicacion);
			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacionParametro = ApmAplicacionManager.getInstance()
					.updateAplicacionParametro(aplicacionParametro);

			if (aplicacionParametro.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionParametroVO = (AplicacionParametroVO) aplicacionParametro
						.toVO(0, false);
			}
			aplicacionParametro.passErrorMessages(aplicacionParametroVO);

			log.debug(funcName + ": exit");
			return aplicacionParametroVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionParametroVO deleteAplicacionParametro(
			UserContext userContext, AplicacionParametroVO aplicacionParametroVO)
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
			aplicacionParametroVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			AplicacionParametro aplicacionParametro = AplicacionParametro
					.getById(aplicacionParametroVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			aplicacionParametro = ApmAplicacionManager.getInstance()
					.deleteAplicacionParametro(aplicacionParametro);

			if (aplicacionParametro.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionParametroVO = (AplicacionParametroVO) aplicacionParametro
						.toVO(0, false);
			}
			aplicacionParametro.passErrorMessages(aplicacionParametroVO);

			log.debug(funcName + ": exit");
			return aplicacionParametroVO;
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

	// ---> ABM UsuarioApm
	public UsuarioApmSearchPage getUsuarioApmSearchPageInit(
			UserContext userContext) throws DemodaServiceException {
		return new UsuarioApmSearchPage();
	}

	//
	public UsuarioApmSearchPage getUsuarioApmSearchPageResult(
			UserContext userContext, UsuarioApmSearchPage usuarioApmSearchPage)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			usuarioApmSearchPage.clearError();

			// Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<UsuarioApm> listUsuarioApm = ApmDAOFactory.getUsuarioApmDAO()
					.getBySearchPage(usuarioApmSearchPage);

			// Aqui se podria iterar la lista de BO para setear banderas en VOs
			// y otras cosas del negocio.

			// Aqui pasamos BO a VO
			usuarioApmSearchPage.setListResult(ListUtilBean.toVO(
					listUsuarioApm, 1));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return usuarioApmSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmAdapter getUsuarioApmAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			UsuarioApm usuarioApm = UsuarioApm.getById(commonKey.getId());

			UsuarioApmAdapter usuarioApmAdapter = new UsuarioApmAdapter();
			usuarioApmAdapter.setUsuarioApm((UsuarioApmVO) usuarioApm.toVO(1));

			log.debug(funcName + ": exit");
			return usuarioApmAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmAdapter getUsuarioApmAdapterForCreate(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			UsuarioApmAdapter usuarioApmAdapter = new UsuarioApmAdapter();

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return usuarioApmAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmAdapter getUsuarioApmAdapterParam(UserContext userContext,
			UsuarioApmAdapter usuarioApmAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			usuarioApmAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return usuarioApmAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmAdapter getUsuarioApmAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyUsuarioApm)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			UsuarioApm usuarioApm = UsuarioApm.getById(commonKeyUsuarioApm
					.getId());

			UsuarioApmAdapter usuarioApmAdapter = new UsuarioApmAdapter();
			usuarioApmAdapter.setUsuarioApm((UsuarioApmVO) usuarioApm.toVO(3));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return usuarioApmAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmVO createUsuarioApm(UserContext userContext,
			UsuarioApmVO usuarioApmVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			usuarioApmVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			UsuarioApm usuarioApm = new UsuarioApm();

			usuarioApm.setNombre(usuarioApmVO.getNombre());
			usuarioApm.setUsername(usuarioApmVO.getUsername());
			usuarioApm.setNumeroInspector(usuarioApmVO.getNumeroInspector());
			usuarioApm.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			usuarioApm = ApmAplicacionManager.getInstance().createUsuarioApm(
					usuarioApm);

			if (usuarioApm.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				usuarioApmVO = (UsuarioApmVO) usuarioApm.toVO(0, false);
			}
			usuarioApm.passErrorMessages(usuarioApmVO);

			log.debug(funcName + ": exit");
			return usuarioApmVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmVO updateUsuarioApm(UserContext userContext,
			UsuarioApmVO usuarioApmVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			usuarioApmVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			UsuarioApm usuarioApm = UsuarioApm.getById(usuarioApmVO.getId());

			if (!usuarioApmVO.validateVersion(usuarioApm.getFechaUltMdf()))
				return usuarioApmVO;

			usuarioApm.setNombre(usuarioApmVO.getNombre());
			usuarioApm.setUsername(usuarioApmVO.getUsername());
			usuarioApm.setNumeroInspector(usuarioApmVO.getNumeroInspector());
			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			usuarioApm = ApmAplicacionManager.getInstance().updateUsuarioApm(
					usuarioApm);

			if (usuarioApm.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				usuarioApmVO = (UsuarioApmVO) usuarioApm.toVO(0, false);
			}
			usuarioApm.passErrorMessages(usuarioApmVO);

			log.debug(funcName + ": exit");
			return usuarioApmVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmVO deleteUsuarioApm(UserContext userContext,
			UsuarioApmVO usuarioApmVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			usuarioApmVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			UsuarioApm usuarioApm = UsuarioApm.getById(usuarioApmVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			usuarioApm = ApmAplicacionManager.getInstance().deleteUsuarioApm(usuarioApm);

			if (usuarioApm.hasError()) {
				if (log.isDebugEnabled())log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				usuarioApmVO = (UsuarioApmVO) usuarioApm.toVO(0, false);
			}
			usuarioApm.passErrorMessages(usuarioApmVO);

			log.debug(funcName + ": exit");
			return usuarioApmVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)	tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoUsuarioAdapter getPerfilAccesoUsuarioAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			PerfilAccesoUsuario perfilAccesoUsuario = PerfilAccesoUsuario
					.getById(commonKey.getId());

			PerfilAccesoUsuarioAdapter perfilAccesoUsuarioAdapter = new PerfilAccesoUsuarioAdapter();
			perfilAccesoUsuarioAdapter
			.setPerfilAccesoUsuario((PerfilAccesoUsuarioVO) perfilAccesoUsuario
					.toVO(3));

			log.debug(funcName + ": exit");
			return perfilAccesoUsuarioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoUsuarioAdapter getPerfilAccesoUsuarioAdapterForCreate(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			PerfilAccesoUsuarioAdapter perfilAccesoUsuarioAdapter = new PerfilAccesoUsuarioAdapter();

			perfilAccesoUsuarioAdapter.getPerfilAccesoUsuario().getUsuarioAPM()
			.setId(commonKey.getId());

			List<PerfilAcceso> listPerfilAcceso = ApmDAOFactory.getPerfilAccesoDAO().getListActiva();
			perfilAccesoUsuarioAdapter.setListPerfilAcceso(ListUtilBean.toVO(listPerfilAcceso, 0, false));

			log.debug(funcName + ": exit");
			return perfilAccesoUsuarioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}


	public PerfilAccesoUsuarioAdapter getPerfilAccesoUsuarioAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyPerfilAccesoUsuario) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			PerfilAccesoUsuario perfilAccesoUsuario = PerfilAccesoUsuario.getById(commonKeyPerfilAccesoUsuario.getId());

			PerfilAccesoUsuarioAdapter perfilAccesoUsuarioAdapter = new PerfilAccesoUsuarioAdapter();
			perfilAccesoUsuarioAdapter.setPerfilAccesoUsuario((PerfilAccesoUsuarioVO) perfilAccesoUsuario.toVO(1, false));

			List<PerfilAcceso> listPerfilAcceso = ApmDAOFactory.getPerfilAccesoDAO().getListActiva();
			perfilAccesoUsuarioAdapter.setListPerfilAcceso(ListUtilBean.toVO(listPerfilAcceso, 0, false));

			log.debug(funcName + ": exit");
			return perfilAccesoUsuarioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoUsuarioVO createPerfilAccesoUsuario(
			UserContext userContext, PerfilAccesoUsuarioVO perfilAccesoUsuarioVO)
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
			perfilAccesoUsuarioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			PerfilAccesoUsuario perfilAccesoUsuario = new PerfilAccesoUsuario();

			perfilAccesoUsuario.setEstado(Estado.ACTIVO.getId());

			PerfilAcceso perfilAcceso = PerfilAcceso
					.getById(perfilAccesoUsuarioVO.getPerfilAcceso().getId());
			UsuarioApm usuarioApm = UsuarioApm.getById(perfilAccesoUsuarioVO
					.getUsuarioAPM().getId());

			perfilAccesoUsuario.setPerfilAcceso(perfilAcceso);
			perfilAccesoUsuario.setUsuarioAPM(usuarioApm);

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			perfilAccesoUsuario = ApmAplicacionManager.getInstance()
					.createPerfilAccesoUsuario(perfilAccesoUsuario);

			if (perfilAccesoUsuario.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				perfilAccesoUsuarioVO = (PerfilAccesoUsuarioVO) perfilAccesoUsuario
						.toVO(0, false);
			}
			perfilAccesoUsuario.passErrorMessages(perfilAccesoUsuarioVO);

			log.debug(funcName + ": exit");
			return perfilAccesoUsuarioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoUsuarioVO updatePerfilAccesoUsuario(
			UserContext userContext, PerfilAccesoUsuarioVO perfilAccesoUsuarioVO)
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
			perfilAccesoUsuarioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			PerfilAccesoUsuario perfilAccesoUsuario = PerfilAccesoUsuario
					.getById(perfilAccesoUsuarioVO.getId());

			if (!perfilAccesoUsuarioVO.validateVersion(perfilAccesoUsuario
					.getFechaUltMdf()))
				return perfilAccesoUsuarioVO;

			PerfilAcceso perfilAcceso = PerfilAcceso
					.getById(perfilAccesoUsuarioVO.getPerfilAcceso().getId());
			UsuarioApm usuarioApm = UsuarioApm.getById(perfilAccesoUsuarioVO
					.getUsuarioAPM().getId());

			perfilAccesoUsuario.setPerfilAcceso(perfilAcceso);
			perfilAccesoUsuario.setUsuarioAPM(usuarioApm);

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			perfilAccesoUsuario = ApmAplicacionManager.getInstance()
					.updatePerfilAccesoUsuario(perfilAccesoUsuario);

			if (perfilAccesoUsuario.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				perfilAccesoUsuarioVO = (PerfilAccesoUsuarioVO) perfilAccesoUsuario
						.toVO(0, false);
			}
			perfilAccesoUsuario.passErrorMessages(perfilAccesoUsuarioVO);

			log.debug(funcName + ": exit");
			return perfilAccesoUsuarioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoUsuarioVO deletePerfilAccesoUsuario(
			UserContext userContext, PerfilAccesoUsuarioVO perfilAccesoUsuarioVO)
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
			perfilAccesoUsuarioVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			PerfilAccesoUsuario perfilAccesoUsuario = PerfilAccesoUsuario
					.getById(perfilAccesoUsuarioVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			perfilAccesoUsuario = ApmAplicacionManager.getInstance()
					.deletePerfilAccesoUsuario(perfilAccesoUsuario);

			if (perfilAccesoUsuario.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				perfilAccesoUsuarioVO = (PerfilAccesoUsuarioVO) perfilAccesoUsuario
						.toVO(0, false);
			}
			perfilAccesoUsuario.passErrorMessages(perfilAccesoUsuarioVO);

			log.debug(funcName + ": exit");
			return perfilAccesoUsuarioVO;
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

	// ---> ABM UsuarioApmDM
	public UsuarioApmDMAdapter getUsuarioApmDMAdapterForView(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			UsuarioApmDM usuarioApmDM = UsuarioApmDM.getById(commonKey.getId());

			UsuarioApmDMAdapter usuarioApmDMAdapter = new UsuarioApmDMAdapter();
			usuarioApmDMAdapter.setUsuarioApmDM((UsuarioApmDMVO) usuarioApmDM.toVO(1,false));

			log.debug(funcName + ": exit");
			return usuarioApmDMAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmDMAdapter getUsuarioApmDMAdapterForCreate(
			UserContext userContext, CommonKey commonKey)throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			UsuarioApmDMAdapter usuarioApmDMAdapter = new UsuarioApmDMAdapter();
			usuarioApmDMAdapter.getUsuarioApmDM().getUsuarioApm().setId(commonKey.getId());

			List<DispositivoMovil> listDispositivoMovil;
			if (userContext.getEsDGI()) {
				listDispositivoMovil = ApmDAOFactory.getDispositivoMovilDAO().getListActiva();
			} else {
				listDispositivoMovil = ApmDAOFactory.getDispositivoMovilDAO().getListActivaByArea(userContext.getIdArea());
			}
			usuarioApmDMAdapter.setListDispositivoMovil(ListUtilBean.toVO(listDispositivoMovil, 0,
					false, new ImpresoraVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			log.debug(funcName + ": exit");
			return usuarioApmDMAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}


	public UsuarioApmDMAdapter getUsuarioApmDMAdapterForUpdate(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			UsuarioApmDM usuarioApmDM = UsuarioApmDM.getById(commonKey.getId());

			UsuarioApmDMAdapter usuarioApmDMAdapter = new UsuarioApmDMAdapter();
			usuarioApmDMAdapter.setUsuarioApmDM((UsuarioApmDMVO) usuarioApmDM.toVO(1,false));

			List<DispositivoMovil> listDispositivoMovil;
			if (userContext.getEsDGI()) {
				listDispositivoMovil = ApmDAOFactory.getDispositivoMovilDAO().getListActiva();
			} else {
				listDispositivoMovil = ApmDAOFactory.getDispositivoMovilDAO().getListActivaByArea(userContext.getIdArea());
			}
			usuarioApmDMAdapter.setListDispositivoMovil(ListUtilBean.toVO(listDispositivoMovil, 0,
					false, new ImpresoraVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			log.debug(funcName + ": exit");
			return usuarioApmDMAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmDMVO createUsuarioApmDM(UserContext userContext,
			UsuarioApmDMVO usuarioApmDMVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			usuarioApmDMVO.clearErrorMessages();

			UsuarioApmDM usuarioApmDM = new UsuarioApmDM();
			// Copiado de propiadades de VO al BO
			DispositivoMovil dispositivoMovil = DispositivoMovil.getById(usuarioApmDMVO.getDispositivoMovil().getId());
			UsuarioApm usuarioApm = UsuarioApm.getById(usuarioApmDMVO.getUsuarioApm().getId());

			usuarioApmDM.setDispositivoMovil(dispositivoMovil);
			usuarioApmDM.setUsuarioApm(usuarioApm);
			usuarioApmDM.setEstado(Estado.ACTIVO.getId());

			usuarioApmDM = ApmAplicacionManager.getInstance() .createUsuarioApmDM(usuarioApmDM);

			if (usuarioApmDM.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				usuarioApmDMVO = (UsuarioApmDMVO) usuarioApmDM.toVO(0, false);
			}
			usuarioApmDM.passErrorMessages(usuarioApmDMVO);

			log.debug(funcName + ": exit");
			return usuarioApmDMVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)	tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmDMVO updateUsuarioApmDM(UserContext userContext,
			UsuarioApmDMVO usuarioApmDMVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			usuarioApmDMVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			UsuarioApmDM usuarioApmDM = UsuarioApmDM.getById(usuarioApmDMVO.getId());

			if (!usuarioApmDMVO.validateVersion(usuarioApmDM.getFechaUltMdf()))
				return usuarioApmDMVO;

			DispositivoMovil dispositivoMovil = DispositivoMovil.getByIdNull(usuarioApmDMVO.getDispositivoMovil().getId());
			UsuarioApm usuarioApm = UsuarioApm.getById(usuarioApmDMVO.getUsuarioApm().getId());

			usuarioApmDM.setDispositivoMovil(dispositivoMovil);
			usuarioApmDM.setUsuarioApm(usuarioApm);

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			usuarioApmDM = ApmAplicacionManager.getInstance().updateUsuarioApmDM(usuarioApmDM);

			if (usuarioApmDM.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				usuarioApmDMVO = (UsuarioApmDMVO) usuarioApmDM.toVO(0, false);
			}
			usuarioApmDM.passErrorMessages(usuarioApmDMVO);

			log.debug(funcName + ": exit");
			return usuarioApmDMVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)	tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmDMVO deleteUsuarioApmDM(UserContext userContext,
			UsuarioApmDMVO usuarioApmDMVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			usuarioApmDMVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			UsuarioApmDM usuarioApmDM = UsuarioApmDM.getById(usuarioApmDMVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			usuarioApmDM = ApmAplicacionManager.getInstance().deleteUsuarioApmDM(usuarioApmDM);

			if (usuarioApmDM.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				usuarioApmDMVO = (UsuarioApmDMVO) usuarioApmDM.toVO(0, false);
			}
			usuarioApmDM.passErrorMessages(usuarioApmDMVO);

			log.debug(funcName + ": exit");
			return usuarioApmDMVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)	tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
	// <--- ABM UsuarioApmDM


	// ---> APM AplicacionTabla
	public AplicacionTablaSearchPage getAplicacionTablaSearchPageInit(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		AplicacionTablaSearchPage aplicacionTablaSearchPage = new AplicacionTablaSearchPage();
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			List<Aplicacion> listAplicacion = ApmDAOFactory.getAplicacionDAO()
					.getListActiva();
			aplicacionTablaSearchPage.setListAplicacion(ListUtilBean.toVO(
					listAplicacion, 0, false, new AplicacionVO(-1,
							StringUtil.SELECT_OPCION_TODAS)));

			List<TablaVersion> listTablaVersion = ApmDAOFactory
					.getTablaVersionDAO().getListActiva();
			aplicacionTablaSearchPage.setListTablaVersion(ListUtilBean.toVO(
					listTablaVersion, 0, false, new TablaVersionVO(-1,
							StringUtil.SELECT_OPCION_TODOS)));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}

		return aplicacionTablaSearchPage;
	}

	public AplicacionTablaSearchPage getAplicacionTablaSearchPageResult(
			UserContext userContext,
			AplicacionTablaSearchPage aplicacionTablaSearchPage)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplicacionTablaSearchPage.clearError();

			// Aqui obtiene lista de BOs
			List<AplicacionTabla> listAplicacionTabla = ApmDAOFactory
					.getAplicacionTablaDAO().getBySearchPage(
							aplicacionTablaSearchPage);

			// Aqui pasamos BO a VO
			aplicacionTablaSearchPage.setListResult(ListUtilBean.toVO(
					listAplicacionTabla, 1, false));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return aplicacionTablaSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionTablaAdapter getAplicacionTablaAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionTabla aplicacionTabla = AplicacionTabla.getById(commonKey
					.getId());

			AplicacionTablaAdapter aplicacionTablaAdapter = new AplicacionTablaAdapter();
			aplicacionTablaAdapter
			.setAplicacionTabla((AplicacionTablaVO) aplicacionTabla
					.toVO(1, false));

			log.debug(funcName + ": exit");
			return aplicacionTablaAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionTablaAdapter getAplicacionTablaAdapterForCreate(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionTablaAdapter aplicacionTablaAdapter = new AplicacionTablaAdapter();

			// Seteo de banderas
			List<Aplicacion> listAplicacion = ApmDAOFactory.getAplicacionDAO()
					.getListActiva();
			aplicacionTablaAdapter.setListAplicacion(ListUtilBean.toVO(
					listAplicacion, 0, false, new AplicacionVO(-1,
							StringUtil.SELECT_OPCION_TODAS)));

			List<TablaVersion> listTablaVersion = ApmDAOFactory
					.getTablaVersionDAO().getListActiva();
			aplicacionTablaAdapter.setListTablaVersion(ListUtilBean.toVO(
					listTablaVersion, 0, false, new TablaVersionVO(-1,
							StringUtil.SELECT_OPCION_TODOS)));

			// Seteo la listas para combos, etc
			aplicacionTablaAdapter.setListSiNo(SiNo.getListSiNo(SiNo.NO));

			log.debug(funcName + ": exit");
			return aplicacionTablaAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionTablaAdapter getAplicacionTablaAdapterParam(
			UserContext userContext,
			AplicacionTablaAdapter aplicacionTablaAdapter)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplicacionTablaAdapter.clearError();

			log.debug(funcName + ": exit");
			return aplicacionTablaAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionTablaAdapter getAplicacionTablaAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyAplicacionTabla)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionTablaAdapter aplicacionTablaAdapter = new AplicacionTablaAdapter();

			AplicacionTabla aplicacionTabla = AplicacionTabla
					.getById(commonKeyAplicacionTabla.getId());
			aplicacionTablaAdapter
			.setAplicacionTabla((AplicacionTablaVO) aplicacionTabla
					.toVO(1, false));

			// Seteo la lista para combo, valores, etc
			aplicacionTablaAdapter.setListAplicacion(ListUtilBean.toVO(
					Aplicacion.getListActivos(), 0, false));
			aplicacionTablaAdapter.setListTablaVersion(ListUtilBean.toVO(
					TablaVersion.getListActivos(), 0, false));

			log.debug(funcName + ": exit");
			return aplicacionTablaAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionTablaVO createAplicacionTabla(UserContext userContext,
			AplicacionTablaVO aplicacionTablaVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplicacionTablaVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplicacionTabla aplicacionTabla = new AplicacionTabla();

			aplicacionTabla.setEstado(Estado.ACTIVO.getId());
			aplicacionTabla.setAplicacion(Aplicacion
					.getByIdNull(aplicacionTablaVO.getAplicacion().getId()));
			aplicacionTabla.setTablaVersion(TablaVersion
					.getByIdNull(aplicacionTablaVO.getTablaVersion().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacionTabla = ApmAplicacionManager.getInstance()
					.createAplicacionTabla(aplicacionTabla);

			if (aplicacionTabla.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionTablaVO = (AplicacionTablaVO) aplicacionTabla.toVO(0,
						false);
			}
			aplicacionTabla.passErrorMessages(aplicacionTablaVO);

			log.debug(funcName + ": exit");
			return aplicacionTablaVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionTablaVO updateAplicacionTabla(UserContext userContext,
			AplicacionTablaVO aplicacionTablaVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplicacionTablaVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplicacionTabla aplicacionTabla = AplicacionTabla
					.getById(aplicacionTablaVO.getId());

			if (!aplicacionTablaVO.validateVersion(aplicacionTabla
					.getFechaUltMdf()))
				return aplicacionTablaVO;

			aplicacionTabla.setEstado(Estado.ACTIVO.getId());
			aplicacionTabla.setAplicacion(Aplicacion
					.getByIdNull(aplicacionTablaVO.getAplicacion().getId()));
			aplicacionTabla.setTablaVersion(TablaVersion
					.getByIdNull(aplicacionTablaVO.getTablaVersion().getId()));

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			aplicacionTabla = ApmAplicacionManager.getInstance()
					.updateAplicacionTabla(aplicacionTabla);

			if (aplicacionTabla.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionTablaVO = (AplicacionTablaVO) aplicacionTabla.toVO(0,
						false);
			}
			aplicacionTabla.passErrorMessages(aplicacionTablaVO);

			log.debug(funcName + ": exit");
			return aplicacionTablaVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionTablaVO deleteAplicacionTabla(UserContext userContext,
			AplicacionTablaVO aplicacionTablaVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			aplicacionTablaVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			AplicacionTabla aplicacionTabla = AplicacionTabla
					.getById(aplicacionTablaVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			aplicacionTabla = ApmAplicacionManager.getInstance()
					.deleteAplicacionTabla(aplicacionTabla);

			if (aplicacionTabla.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionTablaVO = (AplicacionTablaVO) aplicacionTabla.toVO(0,
						false);
			}
			aplicacionTabla.passErrorMessages(aplicacionTablaVO);

			log.debug(funcName + ": exit");
			return aplicacionTablaVO;
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

	// -----------Fin AplicacionTabla

	// ---> ABM AplicacionTipoBinario
	public AplicacionTipoBinarioSearchPage getAplicacionTipoBinarioSearchPageInit(
			UserContext userContext) throws DemodaServiceException {
		return new AplicacionTipoBinarioSearchPage();
	}

	public AplicacionTipoBinarioSearchPage getAplicacionTipoBinarioSearchPageResult(
			UserContext userContext,
			AplicacionTipoBinarioSearchPage aplicacionTipoBinarioSearchPage)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			aplicacionTipoBinarioSearchPage.clearError();

			// Aqui obtiene lista de BOs
			List<AplicacionTipoBinario> listAplicacionTipoBinario = ApmDAOFactory
					.getAplicacionTipoBinarioDAO().getBySearchPage(
							aplicacionTipoBinarioSearchPage);

			// Aqui pasamos BO a VO
			aplicacionTipoBinarioSearchPage.setListResult(ListUtilBean.toVO(
					listAplicacionTipoBinario, 0));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return aplicacionTipoBinarioSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionTipoBinarioAdapter getAplicacionTipoBinarioAdapterForView(
			UserContext userContext, CommonKey commonKey)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionTipoBinario aplicacionTipoBinario = AplicacionTipoBinario
					.getById(commonKey.getId());

			AplicacionTipoBinarioAdapter aplicacionTipoBinarioAdapter = new AplicacionTipoBinarioAdapter();
			aplicacionTipoBinarioAdapter
			.setAplicacionTipoBinario((AplicacionTipoBinarioVO) aplicacionTipoBinario
					.toVO(1));

			log.debug(funcName + ": exit");
			return aplicacionTipoBinarioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionTipoBinarioAdapter getAplicacionTipoBinarioAdapterForCreate(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionTipoBinarioAdapter aplicacionTipoBinarioAdapter = new AplicacionTipoBinarioAdapter();

			log.debug(funcName + ": exit");
			return aplicacionTipoBinarioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionTipoBinarioAdapter getAplicacionTipoBinarioAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyAplicacionTipoBinario)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			AplicacionTipoBinario aplicacionTipoBinario = AplicacionTipoBinario
					.getById(commonKeyAplicacionTipoBinario.getId());

			AplicacionTipoBinarioAdapter aplicacionTipoBinarioAdapter = new AplicacionTipoBinarioAdapter();
			aplicacionTipoBinarioAdapter
			.setAplicacionTipoBinario((AplicacionTipoBinarioVO) aplicacionTipoBinario
					.toVO(1));

			log.debug(funcName + ": exit");
			return aplicacionTipoBinarioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionTipoBinarioVO createAplicacionTipoBinario(
			UserContext userContext,
			AplicacionTipoBinarioVO aplicacionTipoBinarioVO)
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
			aplicacionTipoBinarioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplicacionTipoBinario aplicacionTipoBinario = new AplicacionTipoBinario();

			aplicacionTipoBinario.setDescripcion(aplicacionTipoBinarioVO
					.getDescripcion());
			aplicacionTipoBinario.setEstado(Estado.ACTIVO.getId());

			// corresponder a un Bean contenedor
			aplicacionTipoBinario = ApmAplicacionManager.getInstance()
					.createAplicacionTipoBinario(aplicacionTipoBinario);

			if (aplicacionTipoBinario.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionTipoBinarioVO = (AplicacionTipoBinarioVO) aplicacionTipoBinario
						.toVO(0, false);
			}
			aplicacionTipoBinario.passErrorMessages(aplicacionTipoBinarioVO);

			log.debug(funcName + ": exit");
			return aplicacionTipoBinarioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionTipoBinarioVO updateAplicacionTipoBinario(
			UserContext userContext,
			AplicacionTipoBinarioVO aplicacionTipoBinarioVO)
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
			aplicacionTipoBinarioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			AplicacionTipoBinario aplicacionTipoBinario = AplicacionTipoBinario
					.getById(aplicacionTipoBinarioVO.getId());

			if (!aplicacionTipoBinarioVO.validateVersion(aplicacionTipoBinario
					.getFechaUltMdf()))
				return aplicacionTipoBinarioVO;

			aplicacionTipoBinario.setDescripcion(aplicacionTipoBinarioVO
					.getDescripcion());

			// corresponder a un Bean contenedor
			aplicacionTipoBinario = ApmAplicacionManager.getInstance()
					.updateAplicacionTipoBinario(aplicacionTipoBinario);

			if (aplicacionTipoBinario.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				aplicacionTipoBinarioVO = (AplicacionTipoBinarioVO) aplicacionTipoBinario
						.toVO(0, false);
			}
			aplicacionTipoBinario.passErrorMessages(aplicacionTipoBinarioVO);

			log.debug(funcName + ": exit");
			return aplicacionTipoBinarioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public AplicacionTipoBinarioVO deleteAplicacionTipoBinario(
			UserContext userContext, AplicacionTipoBinarioVO aplicacionTipoBinarioVO)
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
			aplicacionTipoBinarioVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			AplicacionTipoBinario aplicacionTipoBinario = AplicacionTipoBinario
					.getById(aplicacionTipoBinarioVO.getId());

			// de otro bean
			aplicacionTipoBinario = ApmAplicacionManager.getInstance()
					.deleteAplicacionTipoBinario(aplicacionTipoBinario);

			if (aplicacionTipoBinario.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();

				aplicacionTipoBinarioVO = (AplicacionTipoBinarioVO) aplicacionTipoBinario
						.toVO(0, false);
			}
			aplicacionTipoBinario.passErrorMessages(aplicacionTipoBinarioVO);

			log.debug(funcName + ": exit");
			return aplicacionTipoBinarioVO;
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

	// ---> fin AplicacionTipoBinario

	// ---> ABM PerfilAcceso
	public PerfilAccesoSearchPage getPerfilAccesoSearchPageInit(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		PerfilAccesoSearchPage perfilAccesoSearchPage = new PerfilAccesoSearchPage();
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			List<Area> listArea = DefDAOFactory.getAreaDAO().getListActiva();
			perfilAccesoSearchPage.setListArea(ListUtilBean.toVO(
					listArea, 0, false, new AplicacionVO(-1, StringUtil.SELECT_OPCION_TODAS)));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}

		return perfilAccesoSearchPage;
	}

	public PerfilAccesoSearchPage getPerfilAccesoSearchPageResult(
			UserContext userContext, PerfilAccesoSearchPage perfilAccesoSearchPage)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			perfilAccesoSearchPage.clearError();

			// Aqui obtiene lista de BOs
			List<PerfilAcceso> listPerfilAcceso = ApmDAOFactory.getPerfilAccesoDAO().getBySearchPage(perfilAccesoSearchPage);

			// Aqui pasamos BO a VO
			perfilAccesoSearchPage.setListResult(ListUtilBean.toVO(listPerfilAcceso, 1));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return perfilAccesoSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoAdapter getPerfilAccesoAdapterForView(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			PerfilAcceso perfilAcceso = PerfilAcceso.getById(commonKey.getId());

			PerfilAccesoAdapter perfilAccesoAdapter = new PerfilAccesoAdapter();
			perfilAccesoAdapter.setPerfilAcceso((PerfilAccesoVO) perfilAcceso.toVO(1));

			log.debug(funcName + ": exit");
			return perfilAccesoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoAdapter getPerfilAccesoAdapterForCreate(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			PerfilAccesoAdapter perfilAccesoAdapter = new PerfilAccesoAdapter();

			// Seteo de banderas
			List<Area> listArea = DefDAOFactory.getAreaDAO().getListActiva();
			perfilAccesoAdapter.setListArea(ListUtilBean.toVO(listArea, 0, false, 
					new AreaVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			log.debug(funcName + ": exit");
			return perfilAccesoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoAdapter getPerfilAccesoAdapterParam(UserContext userContext,
			PerfilAccesoAdapter perfilAccesoAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			perfilAccesoAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return perfilAccesoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoAdapter getPerfilAccesoAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyPerfilAcceso)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			PerfilAcceso perfilAcceso = PerfilAcceso.getById(commonKeyPerfilAcceso.getId());

			PerfilAccesoAdapter perfilAccesoAdapter = new PerfilAccesoAdapter();
			perfilAccesoAdapter.setPerfilAcceso((PerfilAccesoVO) perfilAcceso.toVO(2,false));

			List<AreaAplicacionPerfilVO> listAreaAplicacionPerfil = new ArrayList<>();
			for (AreaAplicacionPerfil areaAplicacionPerfil : perfilAcceso.getListAreaAplicacionPerfil()) {
				AreaAplicacionPerfilVO areaAplicacionPerfilVO = (AreaAplicacionPerfilVO) areaAplicacionPerfil.toVO(1,false);
				areaAplicacionPerfilVO.setAplicacionPerfil((AplicacionPerfilVO) areaAplicacionPerfil.getAplicacionPerfil().toVO(2,false));

				listAreaAplicacionPerfil.add(areaAplicacionPerfilVO);
			}
			perfilAccesoAdapter.getPerfilAcceso().setListPerfilAccesoAplicacion(
					ListUtilBean.toVO(perfilAcceso.getListPerfilAccesoAplicacion(), 1, false));
			perfilAccesoAdapter.getPerfilAcceso().setListAreaAplicacionPerfil(listAreaAplicacionPerfil);
			perfilAccesoAdapter.setListArea(ListUtilBean.toVO(Area.getListActivos(), 0, false));

			log.debug(funcName + ": exit");
			return perfilAccesoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoVO createPerfilAcceso(UserContext userContext,
			PerfilAccesoVO perfilAccesoVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			perfilAccesoVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			PerfilAcceso perfilAcceso = new PerfilAcceso();

			perfilAcceso.setDescripcion(perfilAccesoVO.getDescripcion());
			perfilAcceso.setArea(Area.getByIdNull(perfilAccesoVO.getArea().getId()));
			perfilAcceso.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			perfilAcceso = ApmAplicacionManager.getInstance().createPerfilAcceso(
					perfilAcceso);

			if (perfilAcceso.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				perfilAccesoVO = (PerfilAccesoVO) perfilAcceso.toVO(0, false);
			}
			perfilAcceso.passErrorMessages(perfilAccesoVO);

			log.debug(funcName + ": exit");
			return perfilAccesoVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoVO updatePerfilAcceso(UserContext userContext,
			PerfilAccesoVO perfilAccesoVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			perfilAccesoVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			PerfilAcceso perfilAcceso = PerfilAcceso.getById(perfilAccesoVO.getId());

			if (!perfilAccesoVO.validateVersion(perfilAcceso.getFechaUltMdf()))
				return perfilAccesoVO;

			perfilAcceso.setDescripcion(perfilAccesoVO.getDescripcion());
			perfilAcceso.setArea(Area.getByIdNull(perfilAccesoVO.getArea().getId()));

			// corresponder a un Bean contenedor
			perfilAcceso = ApmAplicacionManager.getInstance().updatePerfilAcceso(
					perfilAcceso);

			if (perfilAcceso.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.rollback");
				}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {
					log.debug(funcName + ": tx.commit");
				}
				perfilAccesoVO = (PerfilAccesoVO) perfilAcceso.toVO(0, false);
			}
			perfilAcceso.passErrorMessages(perfilAccesoVO);

			log.debug(funcName + ": exit");
			return perfilAccesoVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoVO deletePerfilAcceso(UserContext userContext,
			PerfilAccesoVO perfilAccesoVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			perfilAccesoVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			PerfilAcceso perfilAcceso = PerfilAcceso.getById(perfilAccesoVO.getId());

			perfilAcceso = ApmAplicacionManager.getInstance().deletePerfilAcceso(perfilAcceso);

			if (perfilAcceso.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				perfilAccesoVO = (PerfilAccesoVO) perfilAcceso.toVO(0, false);
			}
			perfilAcceso.passErrorMessages(perfilAccesoVO);

			log.debug(funcName + ": exit");
			return perfilAccesoVO;
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
	// <--- ABM PerfilAcceso

	// ---> ABM TablaVersion
	public TablaVersionSearchPage getTablaVersionSearchPageInit(
			UserContext userContext) throws DemodaServiceException {
		return new TablaVersionSearchPage();
	}

	public TablaVersionSearchPage getTablaVersionSearchPageResult(
			UserContext userContext, TablaVersionSearchPage tablaVersionSearchPage)	throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			tablaVersionSearchPage.clearError();

			// Aqui obtiene lista de BOs
			List<TablaVersion> listTablaVersion = ApmDAOFactory.getTablaVersionDAO().getBySearchPage(tablaVersionSearchPage);

			// Aqui pasamos BO a VO
			tablaVersionSearchPage.setListResult(ListUtilBean.toVO(listTablaVersion, 0, false));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return tablaVersionSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TablaVersionAdapter getTablaVersionAdapterForView(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			TablaVersion tablaVersion = TablaVersion.getById(commonKey.getId());

			TablaVersionAdapter tablaVersionAdapter = new TablaVersionAdapter();
			tablaVersionAdapter.setTablaVersion((TablaVersionVO) tablaVersion.toVO(1));

			log.debug(funcName + ": exit");
			return tablaVersionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TablaVersionAdapter getTablaVersionAdapterForCreate(
			UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			TablaVersionAdapter tablaVersionAdapter = new TablaVersionAdapter();

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return tablaVersionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TablaVersionAdapter getTablaVersionAdapterForUpdate(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			TablaVersion tablaVersion = TablaVersion.getById(commonKey.getId());

			TablaVersionAdapter tablaVersionAdapter = new TablaVersionAdapter();
			tablaVersionAdapter.setTablaVersion((TablaVersionVO) tablaVersion.toVO(1));

			log.debug(funcName + ": exit");
			return tablaVersionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TablaVersionVO createTablaVersion(UserContext userContext,
			TablaVersionVO tablaVersionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			tablaVersionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			TablaVersion tablaVersion = new TablaVersion();

			tablaVersion.setTabla(tablaVersionVO.getTabla());
			tablaVersion.setLastVersion(tablaVersionVO.getLastVersion());

			tablaVersion.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			tablaVersion = ApmAplicacionManager.getInstance().createTablaVersion(tablaVersion);

			if (tablaVersion.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				tablaVersionVO = (TablaVersionVO) tablaVersion.toVO(0, false);
			}
			tablaVersion.passErrorMessages(tablaVersionVO);

			log.debug(funcName + ": exit");
			return tablaVersionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TablaVersionVO updateTablaVersion(UserContext userContext,
			TablaVersionVO tablaVersionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			tablaVersionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			TablaVersion tablaVersion = TablaVersion.getById(tablaVersionVO.getId());

			if (!tablaVersionVO.validateVersion(tablaVersion.getFechaUltMdf()))
				return tablaVersionVO;

			tablaVersion.setTabla(tablaVersionVO.getTabla());
			tablaVersion.setLastVersion(tablaVersionVO.getLastVersion());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			tablaVersion = ApmAplicacionManager.getInstance().updateTablaVersion(tablaVersion);

			if (tablaVersion.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				tablaVersionVO = (TablaVersionVO) tablaVersion.toVO(0, false);
			}
			tablaVersion.passErrorMessages(tablaVersionVO);

			log.debug(funcName + ": exit");
			return tablaVersionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TablaVersionVO deleteTablaVersion(UserContext userContext,
			TablaVersionVO tablaVersionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			tablaVersionVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			TablaVersion tablaVersion = TablaVersion.getById(tablaVersionVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			// de otro bean
			tablaVersion = ApmAplicacionManager.getInstance().deleteTablaVersion(tablaVersion);

			if (tablaVersion.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				tablaVersionVO = (TablaVersionVO) tablaVersion.toVO(0, false);
			}
			tablaVersion.passErrorMessages(tablaVersionVO);

			log.debug(funcName + ": exit");
			return tablaVersionVO;
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
	// ---> fin TablaVersion

	// ---> ABM UsuarioApmImpresora
	public UsuarioApmImpresoraAdapter getUsuarioApmImpresoraAdapterForView(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			UsuarioApmImpresora usuarioApmImprosar = UsuarioApmImpresora.getById(commonKey.getId());

			UsuarioApmImpresoraAdapter usuarioApmDMAdapter = new UsuarioApmImpresoraAdapter();
			usuarioApmDMAdapter.setUsuarioApmImpresora((UsuarioApmImpresoraVO) usuarioApmImprosar.toVO(1,false));

			log.debug(funcName + ": exit");
			return usuarioApmDMAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmImpresoraAdapter getUsuarioApmImpresoraAdapterForCreate(
			UserContext userContext, CommonKey commonKey)throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			UsuarioApmImpresoraAdapter usuarioApmImpresoraAdapter = new UsuarioApmImpresoraAdapter();
			usuarioApmImpresoraAdapter.getUsuarioApmImpresora().getUsuarioApm().setId(commonKey.getId());

			List<Impresora> listImpresora;
			if (userContext.getEsDGI()) {
				listImpresora = ApmDAOFactory.getImpresoraDAO().getListActiva();
			} else {
				listImpresora = ApmDAOFactory.getImpresoraDAO().getListActivaByArea(userContext.getIdArea());
			}
			usuarioApmImpresoraAdapter.setListImpresora(ListUtilBean.toVO(listImpresora, 0,
					false, new ImpresoraVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			log.debug(funcName + ": exit");
			return usuarioApmImpresoraAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmImpresoraAdapter getUsuarioApmImpresoraAdapterForUpdate(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			UsuarioApmImpresora usuarioApmImpresora = UsuarioApmImpresora.getById(commonKey.getId());

			UsuarioApmImpresoraAdapter usuarioApmImpresoraAdapter = new UsuarioApmImpresoraAdapter();
			usuarioApmImpresoraAdapter.setUsuarioApmImpresora((UsuarioApmImpresoraVO) usuarioApmImpresora.toVO(1,false));

			List<Impresora> listImpresora;
			if (userContext.getEsDGI()) {
				listImpresora = ApmDAOFactory.getImpresoraDAO().getListActiva();
			} else {
				listImpresora = ApmDAOFactory.getImpresoraDAO().getListActivaByArea(userContext.getIdArea());
			}
			usuarioApmImpresoraAdapter.setListImpresora(ListUtilBean.toVO(listImpresora, 0,
					false, new ImpresoraVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			log.debug(funcName + ": exit");
			return usuarioApmImpresoraAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmImpresoraVO createUsuarioApmImpresora(UserContext userContext,
			UsuarioApmImpresoraVO usuarioApmImpresoraVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			usuarioApmImpresoraVO.clearErrorMessages();

			UsuarioApmImpresora usuarioApmImpresora = new UsuarioApmImpresora();
			// Copiado de propiadades de VO al BO
			Impresora impresora = Impresora.getByIdNull(usuarioApmImpresoraVO.getImpresora().getId());
			UsuarioApm usuarioApm = UsuarioApm.getById(usuarioApmImpresoraVO.getUsuarioApm().getId());

			usuarioApmImpresora.setImpresora(impresora);
			usuarioApmImpresora.setUsuarioApm(usuarioApm);
			usuarioApmImpresora.setEstado(Estado.ACTIVO.getId());

			usuarioApmImpresora = ApmAplicacionManager.getInstance().createUsuarioApmImpresora(usuarioApmImpresora);

			if (usuarioApmImpresora.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				usuarioApmImpresoraVO = (UsuarioApmImpresoraVO) usuarioApmImpresora.toVO(0, false);
			}
			usuarioApmImpresora.passErrorMessages(usuarioApmImpresoraVO);

			log.debug(funcName + ": exit");
			return usuarioApmImpresoraVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)	tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmImpresoraVO updateUsuarioApmImpresora(UserContext userContext,
			UsuarioApmImpresoraVO usuarioApmImpresoraVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			usuarioApmImpresoraVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			UsuarioApmImpresora usuarioApmImpresora = UsuarioApmImpresora.getById(usuarioApmImpresoraVO.getId());

			if (!usuarioApmImpresoraVO.validateVersion(usuarioApmImpresora.getFechaUltMdf()))
				return usuarioApmImpresoraVO;

			Impresora impresora = Impresora.getByIdNull(usuarioApmImpresoraVO.getImpresora().getId());
			UsuarioApm usuarioApm = UsuarioApm.getById(usuarioApmImpresoraVO.getUsuarioApm().getId());

			usuarioApmImpresora.setImpresora(impresora);
			usuarioApmImpresora.setUsuarioApm(usuarioApm);

			//
			usuarioApmImpresora = ApmAplicacionManager.getInstance().updateUsuarioApmImpresora(usuarioApmImpresora);

			if (usuarioApmImpresora.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				usuarioApmImpresoraVO = (UsuarioApmImpresoraVO) usuarioApmImpresora.toVO(0, false);
			}
			usuarioApmImpresora.passErrorMessages(usuarioApmImpresoraVO);

			log.debug(funcName + ": exit");
			return usuarioApmImpresoraVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)	tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public UsuarioApmImpresoraVO deleteUsuarioApmImpresora(UserContext userContext,
			UsuarioApmImpresoraVO usuarioApmDMVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			usuarioApmDMVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			UsuarioApmImpresora usuarioApmDM = UsuarioApmImpresora.getById(usuarioApmDMVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad
			usuarioApmDM = ApmAplicacionManager.getInstance().deleteUsuarioApmImpresora(usuarioApmDM);

			if (usuarioApmDM.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				usuarioApmDMVO = (UsuarioApmImpresoraVO) usuarioApmDM.toVO(0, false);
			}
			usuarioApmDM.passErrorMessages(usuarioApmDMVO);

			log.debug(funcName + ": exit");
			return usuarioApmDMVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)	tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
	// <--- ABM UsuarioApmImpresora

	// ---> ABM PerfilAccesoAplicacion
	public PerfilAccesoAplicacionAdapter getPerfilAccesoAplicacionAdapterForView(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			PerfilAccesoAplicacion perfilAccesoAplicacion = PerfilAccesoAplicacion.getById(commonKey.getId());

			PerfilAccesoAplicacionAdapter perfilAccesoAplicacionAdapter = new PerfilAccesoAplicacionAdapter();
			perfilAccesoAplicacionAdapter.setPerfilAccesoAplicacion((PerfilAccesoAplicacionVO) perfilAccesoAplicacion.toVO(1));

			log.debug(funcName + ": exit");
			return perfilAccesoAplicacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoAplicacionAdapter getPerfilAccesoAplicacionAdapterForCreate(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			PerfilAccesoAplicacionAdapter perfilAccesoAplicacionAdapter = new PerfilAccesoAplicacionAdapter();
			
			PerfilAcceso perfilAcceso = PerfilAcceso.getById(commonKey.getId());
			
			PerfilAccesoVO perfilAccesoVO = (PerfilAccesoVO) perfilAcceso.toVO(1, false);
			
			perfilAccesoAplicacionAdapter.setListAplicacion(ListUtilBean.toVO(Aplicacion.getListActivos(), 0, false));
			perfilAccesoAplicacionAdapter.getPerfilAccesoAplicacion().setPerfilAcceso(perfilAccesoVO);
			log.debug(funcName + ": exit");
			return perfilAccesoAplicacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoAplicacionAdapter getPerfilAccesoAplicacionAdapterForUpdate(
			UserContext userContext, CommonKey commonKeyPerfilAccesoAplicacion)	throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			PerfilAccesoAplicacion perfilAccesoAplicacion = PerfilAccesoAplicacion.getById(commonKeyPerfilAccesoAplicacion.getId());

			PerfilAccesoAplicacionAdapter perfilAccesoAplicacionAdapter = new PerfilAccesoAplicacionAdapter();
			perfilAccesoAplicacionAdapter.setPerfilAccesoAplicacion((PerfilAccesoAplicacionVO) perfilAccesoAplicacion.toVO(2,false));
			perfilAccesoAplicacionAdapter.setListAplicacion(ListUtilBean.toVO(Aplicacion.getListActivos(), 0, false));

			log.debug(funcName + ": exit");
			return perfilAccesoAplicacionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoAplicacionVO createPerfilAccesoAplicacion(UserContext userContext,
			PerfilAccesoAplicacionVO perfilAccesoAplicacionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			perfilAccesoAplicacionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			PerfilAccesoAplicacion perfilAccesoAplicacion = new PerfilAccesoAplicacion();

			perfilAccesoAplicacion.setAplicacion(Aplicacion.getByIdNull(perfilAccesoAplicacionVO.getAplicacion().getId()));
			perfilAccesoAplicacion.setPerfilAcceso(PerfilAcceso.getByIdNull(perfilAccesoAplicacionVO.getPerfilAcceso().getId()));
			perfilAccesoAplicacion.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede
			// corresponder a un Bean contenedor
			perfilAccesoAplicacion = ApmAplicacionManager.getInstance().createPerfilAccesoAplicacion(perfilAccesoAplicacion);

			if (perfilAccesoAplicacion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {log.debug(funcName + ": tx.commit");}
				perfilAccesoAplicacionVO = (PerfilAccesoAplicacionVO) perfilAccesoAplicacion.toVO(0, false);
			}
			perfilAccesoAplicacion.passErrorMessages(perfilAccesoAplicacionVO);

			log.debug(funcName + ": exit");
			return perfilAccesoAplicacionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoAplicacionVO updatePerfilAccesoAplicacion(UserContext userContext,
			PerfilAccesoAplicacionVO perfilAccesoAplicacionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled())
			log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			perfilAccesoAplicacionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			PerfilAccesoAplicacion perfilAccesoAplicacion = PerfilAccesoAplicacion.getById(perfilAccesoAplicacionVO.getId());

			if (!perfilAccesoAplicacionVO.validateVersion(perfilAccesoAplicacion.getFechaUltMdf()))
				return perfilAccesoAplicacionVO;
			
			perfilAccesoAplicacion.setAplicacion(Aplicacion.getByIdNull(perfilAccesoAplicacionVO.getAplicacion().getId()));
			perfilAccesoAplicacion.setPerfilAcceso(PerfilAcceso.getByIdNull(perfilAccesoAplicacionVO.getPerfilAcceso().getId()));

			// corresponder a un Bean contenedor
			perfilAccesoAplicacion = ApmAplicacionManager.getInstance().updatePerfilAccesoAplicacion(perfilAccesoAplicacion);

			if (perfilAccesoAplicacion.hasError()) {
				tx.rollback();
				if (log.isDebugEnabled()) {log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if (log.isDebugEnabled()) {log.debug(funcName + ": tx.commit");}
				perfilAccesoAplicacionVO = (PerfilAccesoAplicacionVO) perfilAccesoAplicacion.toVO(0, false);
			}
			perfilAccesoAplicacion.passErrorMessages(perfilAccesoAplicacionVO);

			log.debug(funcName + ": exit");
			return perfilAccesoAplicacionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ", e);
			if (tx != null)
				tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public PerfilAccesoAplicacionVO deletePerfilAccesoAplicacion(UserContext userContext,
			PerfilAccesoAplicacionVO perfilAccesoAplicacionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			perfilAccesoAplicacionVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			PerfilAccesoAplicacion perfilAccesoAplicacion = PerfilAccesoAplicacion.getById(perfilAccesoAplicacionVO.getId());

			perfilAccesoAplicacion = ApmAplicacionManager.getInstance().deletePerfilAccesoAplicacion(perfilAccesoAplicacion);

			if (perfilAccesoAplicacion.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
				perfilAccesoAplicacionVO = (PerfilAccesoAplicacionVO) perfilAccesoAplicacion.toVO(0, false);
			}
			perfilAccesoAplicacion.passErrorMessages(perfilAccesoAplicacionVO);

			log.debug(funcName + ": exit");
			return perfilAccesoAplicacionVO;
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
	// <--- ABM PerfilAccesoAplicacion
}
