package ar.gov.rosario.gait.frm.buss.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.frm.buss.bean.Numeracion;
import ar.gov.rosario.gait.frm.buss.bean.Serie;
import ar.gov.rosario.gait.frm.buss.bean.TipoFormulario;
import ar.gov.rosario.gait.frm.iface.model.NumeracionAdapter;
import ar.gov.rosario.gait.frm.iface.model.NumeracionSearchPage;
import ar.gov.rosario.gait.frm.iface.model.NumeracionVO;
import ar.gov.rosario.gait.frm.iface.model.SerieAdapter;
import ar.gov.rosario.gait.frm.iface.model.SerieSearchPage;
import ar.gov.rosario.gait.frm.iface.model.SerieVO;
import ar.gov.rosario.gait.frm.iface.model.TipoFormularioVO;
import ar.gov.rosario.gait.frm.iface.service.IFrmNumeracionService;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.Estado;
import coop.tecso.demoda.iface.model.UserContext;

public class FrmNumeracionServiceHbmImpl implements IFrmNumeracionService {
	private Logger log = Logger.getLogger(FrmNumeracionServiceHbmImpl.class);

	// ABM_SERIE
	public SerieSearchPage getSerieSearchPageInit(UserContext userContext) throws DemodaServiceException {		
		return new SerieSearchPage();
	}

	//
	public SerieSearchPage getSerieSearchPageResult(UserContext userContext, SerieSearchPage serieSearchPage) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			serieSearchPage.clearError();

			// Aqui obtiene lista de BOs
			List<Serie> listSerie = ApmDAOFactory.getSerieDAO().getBySearchPage(serieSearchPage);  

			//Aqui pasamos BO a VO
			serieSearchPage.setListResult(ListUtilBean.toVO(listSerie,0,false));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return serieSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SerieAdapter getSerieAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			Serie serie = Serie.getById(commonKey.getId());

			SerieAdapter serieAdapter = new SerieAdapter();
			serieAdapter.setSerie((SerieVO) serie.toVO(1,false));

			log.debug(funcName + ": exit");
			return serieAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SerieAdapter getSerieAdapterForCreate(UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			SerieAdapter serieAdapter = new SerieAdapter();

			log.debug(funcName + ": exit");
			return serieAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public SerieAdapter getSerieAdapterForUpdate(UserContext userContext, CommonKey commonKeySerie) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			Serie serie = Serie.getById(commonKeySerie.getId());

			SerieAdapter serieAdapter = new SerieAdapter();
			serieAdapter.setSerie((SerieVO) serie.toVO(1, false));

			log.debug(funcName + ": exit");
			return serieAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SerieVO createSerie(UserContext userContext, SerieVO serieVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			serieVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Serie serie = new Serie();

			serie.setCodigo(serieVO.getCodigo());
			serie.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			serie = FrmFormularioManager.getInstance().createSerie(serie);

			if (serie.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				serieVO =  (SerieVO) serie.toVO(0,false);
			}
			serie.passErrorMessages(serieVO);

			log.debug(funcName + ": exit");
			return serieVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SerieVO updateSerie(UserContext userContext, SerieVO serieVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			serieVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Serie serie = Serie.getById(serieVO.getId());

			if(!serieVO.validateVersion(serie.getFechaUltMdf())) return serieVO;
			serie.setCodigo(serieVO.getCodigo());
			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			serie = FrmFormularioManager.getInstance().updateSerie(serie);

			if (serie.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				serieVO =  (SerieVO) serie.toVO(0,false);
			}
			serie.passErrorMessages(serieVO);

			log.debug(funcName + ": exit");
			return serieVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SerieVO deleteSerie(UserContext userContext, SerieVO serieVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			serieVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			Serie serie = Serie.getById(serieVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad de otro bean
			serie = FrmFormularioManager.getInstance().deleteSerie(serie);

			if (serie.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				serieVO =  (SerieVO) serie.toVO(0,false);
			}
			serie.passErrorMessages(serieVO);

			log.debug(funcName + ": exit");
			return serieVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			try { tx.rollback(); } catch (Exception ee) {}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	// ABM_NUMERACION
	public NumeracionSearchPage getNumeracionSearchPageInit(UserContext userContext) throws DemodaServiceException {		
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			NumeracionSearchPage numeracionSearchPage = new NumeracionSearchPage();

			numeracionSearchPage.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),0,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_TODOS)));

			numeracionSearchPage.setListSerie(
					ListUtilBean.toVO(Serie.getListActivos(),0,
							false, new SerieVO(-1, StringUtil.SELECT_OPCION_TODOS)));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return numeracionSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NumeracionSearchPage getNumeracionSearchPageResult(UserContext userContext, NumeracionSearchPage numeracionSearchPage) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			numeracionSearchPage.clearError();

			// Aqui obtiene lista de BOs
			List<Numeracion> listNumeracion = ApmDAOFactory.getNumeracionDAO().getBySearchPage(numeracionSearchPage);  

			//Aqui pasamos BO a VO
			numeracionSearchPage.setListResult(ListUtilBean.toVO(listNumeracion,1,false));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return numeracionSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NumeracionAdapter getNumeracionAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			Numeracion numeracion = Numeracion.getById(commonKey.getId());

			NumeracionAdapter numeracionAdapter = new NumeracionAdapter();
			numeracionAdapter.setNumeracion((NumeracionVO) numeracion.toVO(1));

			log.debug(funcName + ": exit");
			return numeracionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NumeracionAdapter getNumeracionAdapterForCreate(UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			NumeracionAdapter numeracionAdapter = new NumeracionAdapter();

			numeracionAdapter.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),1,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			numeracionAdapter.setListSerie(
					ListUtilBean.toVO(Serie.getListActivos(),0,
							false, new SerieVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			log.debug(funcName + ": exit");
			return numeracionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}


	public NumeracionAdapter getNumeracionAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			NumeracionAdapter numeracionAdapter = new NumeracionAdapter();

			Numeracion numeracion = Numeracion.getById(commonKey.getId());
			numeracionAdapter.setNumeracion(
					(NumeracionVO) numeracion.toVO(1,false));

			// Seteo la lista para combo, valores, etc
			numeracionAdapter.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),1,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			numeracionAdapter.setListSerie(
					ListUtilBean.toVO(Serie.getListActivos(),0,
							false, new SerieVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			log.debug(funcName + ": exit");
			return numeracionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NumeracionVO createNumeracion(UserContext userContext, NumeracionVO numeracionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			numeracionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Numeracion numeracion = new Numeracion();

			Serie serie = Serie.getByIdNull(numeracionVO.getSerie().getId());
			numeracion.setSerie(serie);
			TipoFormulario tipoFormulario = TipoFormulario.getByIdNull(numeracionVO.getTipoFormulario().getId());
			numeracion.setTipoFormulario(tipoFormulario);
			numeracion.setValorDesde(numeracionVO.getValorDesde());
			numeracion.setValorHasta(numeracionVO.getValorHasta());
			numeracion.setValorRestante(999999 - (numeracionVO.getValorDesde()-numeracionVO.getValorDesde()));

			numeracion.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			numeracion = FrmFormularioManager.getInstance().createNumeracion(numeracion);

			if (numeracion.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				numeracionVO =  (NumeracionVO) numeracion.toVO(0,false);
			}
			numeracion.passErrorMessages(numeracionVO);

			log.debug(funcName + ": exit");
			return numeracionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NumeracionVO updateNumeracion(UserContext userContext, NumeracionVO numeracionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			numeracionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Numeracion numeracion = Numeracion.getById(numeracionVO.getId());

			if(!numeracionVO.validateVersion(numeracion.getFechaUltMdf())) return numeracionVO;

			Serie serie = Serie.getByIdNull(numeracionVO.getSerie().getId());
			numeracion.setSerie(serie);
			TipoFormulario tipoFormulario = TipoFormulario.getByIdNull(numeracionVO.getTipoFormulario().getId());
			numeracion.setTipoFormulario(tipoFormulario);
			numeracion.setValorDesde(numeracionVO.getValorDesde());
			numeracion.setValorHasta(numeracionVO.getValorHasta());

			numeracion.setEstado(Estado.ACTIVO.getId());
			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			numeracion = FrmFormularioManager.getInstance().updateNumeracion(numeracion);

			if (numeracion.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				numeracionVO =  (NumeracionVO) numeracion.toVO(0,false);
			}
			numeracion.passErrorMessages(numeracionVO);

			log.debug(funcName + ": exit");
			return numeracionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NumeracionVO deleteNumeracion(UserContext userContext, NumeracionVO numeracionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			numeracionVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			Numeracion numeracion = Numeracion.getById(numeracionVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad de otro bean
			numeracion = FrmFormularioManager.getInstance().deleteNumeracion(numeracion);

			if (numeracion.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				numeracionVO =  (NumeracionVO) numeracion.toVO(0,false);
			}
			numeracion.passErrorMessages(numeracionVO);

			log.debug(funcName + ": exit");
			return numeracionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			try { tx.rollback(); } catch (Exception ee) {}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
}