package ar.gov.rosario.gait.pro.buss.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.pro.buss.bean.Corrida;
import ar.gov.rosario.gait.pro.buss.bean.FileCorrida;
import ar.gov.rosario.gait.pro.buss.bean.PasoCorrida;
import ar.gov.rosario.gait.pro.iface.model.AdpCorridaAdapter;
import ar.gov.rosario.gait.pro.iface.model.CorridaVO;
import ar.gov.rosario.gait.pro.iface.model.PasoCorridaVO;
import ar.gov.rosario.gait.pro.iface.service.IProAdpProcesoService;
import ar.gov.rosario.gait.pro.iface.util.ProError;
import coop.tecso.adpcore.AdpRun;
import coop.tecso.adpcore.AdpRunState;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.UserContext;

/**
 * Implementacion de servicios del submodulo Proceso del modulo Proceso. Especifico para funcionar mediante ADP.
 * @author tecso
 */
public class ProAdpProcesoServiceHbmImpl implements IProAdpProcesoService {

	private Logger log = Logger.getLogger(ProAdpProcesoServiceHbmImpl.class);
	
	public AdpCorridaAdapter getAdpCorridaAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 
			
			Corrida corrida = Corrida.getById(commonKey.getId());

	        AdpCorridaAdapter adpCorridaAdapter = new AdpCorridaAdapter();
	        adpCorridaAdapter.setCorrida((CorridaVO) corrida.toVO(1));
	        adpCorridaAdapter.getCorrida().setFechaInicio(new Date());
	        adpCorridaAdapter.getCorrida().setHoraInicio(DateUtil.getTimeFromDate(new Date()));
			
			log.debug(funcName + ": exit");
			return adpCorridaAdapter;
			
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public CorridaVO activarProceso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		
		Session session = null;
		Transaction tx  = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			
			String fecha = DateUtil.formatDate(corridaVO.getFechaInicio(), DateUtil.ddSMMSYYYY_MASK);
			String hora = DateUtil.formatDate(corridaVO.getHoraInicio(), DateUtil.HOUR_MINUTE_MASK);
			String fechaConHora = fecha+" "+hora;
			Date fechaInicioConHora = DateUtil.getDate(fechaConHora, DateUtil.ddSMMSYYYY_HH_MM_MASK);
			
			AdpRun run = null;
			run = AdpRun.getRun(corridaVO.getId());
			if(run!=null){
				boolean activadoOK = run.execute(fechaInicioConHora);
				if(!activadoOK){
					corridaVO.addRecoverableError(ProError.CORRIDA_NO_PERMITE_ACTIVAR_PASO);
				}
			}else{
				corridaVO.addRecoverableValueError(ProError.CORRIDA_NO_PERMITE_ACTIVAR_PASO);
			}

            if (corridaVO.hasError()) {
            	tx.rollback();
            	if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
			}
                   
            log.debug(funcName + ": exit");
            return corridaVO;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public CorridaVO cancelarProceso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		
		Session session = null;
		Transaction tx  = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			
			AdpRun run = null;
			run = AdpRun.getRun(corridaVO.getId());
			if(run!=null){
				boolean canceladoOK = run.cancel();
				if(!canceladoOK){
					corridaVO.addRecoverableError(ProError.CORRIDA_NO_PERMITE_CANCELAR_PASO);
				}
			}else{
				corridaVO.addRecoverableValueError(ProError.CORRIDA_NO_PERMITE_CANCELAR_PASO);
			}
			
            if (corridaVO.hasError()) {
            	tx.rollback();
            	if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
			}
                   
            log.debug(funcName + ": exit");
            return corridaVO;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public CorridaVO reiniciarProceso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		
		Session session = null;
		Transaction tx  = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			
			AdpRun run = null;
			run = AdpRun.getRun(corridaVO.getId());
			if(run!=null){
				log.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$"+run.getIdEstadoCorrida());
				boolean resetOK = run.reset();
				log.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$"+run.getIdEstadoCorrida());
				if (!resetOK){
					corridaVO.addRecoverableError(ProError.CORRIDA_NO_PERMITE_REINICIAR_PASO);
				}
			}else{
				corridaVO.addRecoverableError(ProError.CORRIDA_NO_PERMITE_REINICIAR_PASO);
			}
			
            if (corridaVO.hasError()) {
            	tx.rollback();
            	if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
			}
                   
            log.debug(funcName + ": exit");
            return corridaVO;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	

	}

	public CorridaVO reprogramarProceso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		
		Session session = null;
		Transaction tx  = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			
			String fecha = DateUtil.formatDate(corridaVO.getFechaInicio(), DateUtil.ddSMMSYYYY_MASK);
			String hora = DateUtil.formatDate(corridaVO.getHoraInicio(), DateUtil.HOUR_MINUTE_MASK);
			String fechaConHora = fecha+" "+hora;
			Date fechaInicioConHora = DateUtil.getDate(fechaConHora, DateUtil.ddSMMSYYYY_HH_MM_MASK);
			
			AdpRun run = null;
			run = AdpRun.getRun(corridaVO.getId());
			if(run!=null){
				boolean reprogramarOK = run.execute(fechaInicioConHora);
				if(!reprogramarOK){
					corridaVO.addRecoverableValueError("No permite reprogramar la corrida.");
				}
			}else{
				corridaVO.addRecoverableValueError("No permite reprogramar la corrida.");
			}
			
            if (corridaVO.hasError()) {
            	tx.rollback();
            	if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
			}
                   
            log.debug(funcName + ": exit");
            return corridaVO;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
		
	}

	public CorridaVO siguientePaso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		
		Session session = null;
		Transaction tx  = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			
			AdpRun run = null;
			run = AdpRun.getRun(corridaVO.getId());
			if(run!=null){
				log.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$ siguiente: " + run.getId());
				log.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$ siguiente: " + run.getPasoActual());
				String msg = "Fin de paso " + run.getPasoActual();
				boolean siguienteOK = run.changeState(AdpRunState.STEP, msg, true);
				log.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$ siguiente: " + run.getPasoActual());
				if(!siguienteOK){
					corridaVO.addRecoverableError(ProError.CORRIDA_NO_PERMITE_SIGUIENTE_PASO);
				}
			}else{
				corridaVO.addRecoverableError(ProError.CORRIDA_NO_PERMITE_SIGUIENTE_PASO);
			}
			
            if (corridaVO.hasError()) {
            	tx.rollback();
            	if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
			}
                   
            log.debug(funcName + ": exit");
            return corridaVO;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public String obtenerFileCorridaName(Long idFileCorrida) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			
			String fileName = null; 
			FileCorrida fileCorrida = FileCorrida.getByIdNull(idFileCorrida);
			
			if(fileCorrida != null){
				fileName = fileCorrida.getFileName();
			}
			
            log.debug(funcName + ": exit");

            return fileName;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		}	
	}

	public AdpCorridaAdapter getEstadoPaso(UserContext userContext, CommonKey id, Integer paso) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 
			
			Corrida corrida = Corrida.getById(id.getId());
			CorridaVO corridaVO = (CorridaVO) corrida.toVO(1, false); 
			AdpCorridaAdapter adpCorridaAdapter = new AdpCorridaAdapter();
				
			// Si se pide informacion por el paso actual, retornamos tal cual esta corrida
			// Sino retornamos con las observasiones y estado del paso solicitado.
			log.debug("PasoCorrida: pasoActual=" + corrida.getPasoActual());
			log.debug("PasoCorrida: paso=" + paso);
			log.debug("PasoCorrida: equal? !corrida.getPasoActual().equals(paso)=" + !corrida.getPasoActual().equals(paso));
			if (!corrida.getPasoActual().equals(paso)) {
				PasoCorrida pc = corrida.getPasoCorrida(paso.intValue());
				if (pc == null) { 
					return null;
				} else {
					PasoCorridaVO pcVO = (PasoCorridaVO) pc.toVO(1, false); 
					corridaVO.setEstadoCorrida(pcVO.getEstadoCorrida());
					corridaVO.setMensajeEstado("");
					corridaVO.setObservacion(pcVO.getObservacion());
					corridaVO.setPasoCorrida(pcVO.getPaso());
					corridaVO.setFechaInicio(pcVO.getFechaCorrida());
				} 
			}
			
			adpCorridaAdapter.setCorrida(corridaVO);
			
			log.debug(funcName + ": exit");
			return adpCorridaAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public String getLogFile(UserContext userContext, CommonKey id) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 
			
			AdpRun run = AdpRun.getRun(id.getId());
			if(run == null){
				return null;
			}
			
			return run.getLogFileName();
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
}
