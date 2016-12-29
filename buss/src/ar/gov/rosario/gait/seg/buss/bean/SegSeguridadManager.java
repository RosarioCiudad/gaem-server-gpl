package ar.gov.rosario.gait.seg.buss.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.gov.rosario.gait.apm.buss.bean.UsuarioApm;
import ar.gov.rosario.gait.apm.buss.bean.UsuarioApmDM;
import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmVO;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.iface.model.GaitParam;
import ar.gov.rosario.gait.seg.buss.dao.SegDAOFactory;
import ar.gov.rosario.gait.seg.iface.service.SegServiceLocator;
import ar.gov.rosario.gait.seg.iface.util.SegError;
import ar.gov.rosario.gait.simgei.auth.AuthenticationWebServiceManager;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.UserContext;
import coop.tecso.demoda.util.Base64;
import coop.tecso.swe.iface.model.UsuarioVO;

/**
 * Manejador del modulo Seg y submodulo Seguridad
 * 
 * @author tecso
 *
 */
public class SegSeguridadManager {

	private static Logger log = Logger.getLogger(SegSeguridadManager.class);

	private static final SegSeguridadManager INSTANCE = new SegSeguridadManager();

	/**
	 * Constructor privado
	 */
	private SegSeguridadManager() {

	}

	/**
	 * Devuelve unica instancia
	 */
	public static SegSeguridadManager getInstance() {
		return INSTANCE;
	}

	// ---> ABM UsuarioGait
	public UsuarioGait createUsuarioGait(UsuarioGait usuarioGait) throws Exception {

		// Validaciones de negocio
		if (!usuarioGait.validateCreate()) {
			return usuarioGait;
		}

		SegDAOFactory.getUsuarioGaitDAO().update(usuarioGait);

		return usuarioGait;
	}

	public UsuarioGait updateUsuarioGait(UsuarioGait usuarioGait) throws Exception {

		// Validaciones de negocio
		if (!usuarioGait.validateUpdate()) {
			return usuarioGait;
		}

		SegDAOFactory.getUsuarioGaitDAO().update(usuarioGait);

		return usuarioGait;
	}

	public UsuarioGait deleteUsuarioGait(UsuarioGait usuarioGait) throws Exception {

		// Validaciones de negocio
		if (!usuarioGait.validateDelete()) {
			return usuarioGait;
		}

		SegDAOFactory.getUsuarioGaitDAO().delete(usuarioGait);

		return usuarioGait;
	}
	// <--- ABM UsuarioGait

	/**
	 * Cambio de password a travez de MCRFacade
	 * 
	 * 
	 */
	public UsuarioVO changePassword(UserContext userSession, UsuarioVO userVO) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {	            
			// estamos Ok
			return userVO;
		} catch (Exception swe) {
			log.error(swe.getMessage());

			swe.printStackTrace();
			// salimos con error
			userVO.addRecoverableError(SegError.MSG_SERVICE_ERROR);
			return userVO;
		}
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param deviceid
	 * @return
	 * @throws DemodaServiceException
	 */
	public static UsuarioApmVO loginUsuarioApm(String username, String password, String deviceid) throws DemodaServiceException{
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		Session session = null;
		Transaction tx = null; 
		try {
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			// Instancio VO para trasmitirlo
			UsuarioApmVO usuarioApmVO = new UsuarioApmVO();

			// Verifico que el Usuario exista Localmente
			UsuarioApm usuarioApm = UsuarioApm.getByUserName(username);
			if(null == usuarioApm){
				usuarioApmVO.addRecoverableValueError("1- Usuario o Móvil sin permisos de acceso");
				return usuarioApmVO;
			}
			// Verifico asignacion Usuario -> DispositivoMovil
			if(!UsuarioApmDM.hasAccess(username, deviceid)){
				usuarioApmVO.addRecoverableValueError("2- Usuario o Móvil sin permisos de acceso");
				return usuarioApmVO;
			}
			boolean hasAccess = false;
			try {
				hasAccess = SegServiceLocator.getAuthService().auth(username, password);
			} catch (Exception e) {
				if (log.isDebugEnabled()) log.debug(funcName + ": SIMGEI - **ERROR**", e);
			}
			if(!hasAccess){
				usuarioApmVO.addRecoverableValueError("3- Usuario o Móvil sin permisos de acceso");
				return usuarioApmVO;
			}
			
			// Obtengo cerificado pksc12 del Usuario			
			Path certFile = Paths.get(GaitParam.getString(GaitParam.CERTIFICATE_PATH));
			// Obtengo cerificado pksc12 del Usuario		
			byte[] userCert = Files.readAllBytes(certFile);
			// Cargo el certificado al KeyStore
			KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
			keyStore.load(new ByteArrayInputStream(userCert), "".toCharArray());
			// Encripto certificado con la clave unificada
			ByteArrayOutputStream outputCert = new ByteArrayOutputStream();
			keyStore.store(outputCert, password.toCharArray());
			// Serializo certificado PKCS12 en Base64
			String userB64Cert = Base64.encodeToString(outputCert.toByteArray(), Base64.DEFAULT);

			// Genero token de usuario
			StringBuilder userToken = new StringBuilder();
			userToken.append(UUID.randomUUID().toString().toUpperCase());
			userToken.append(StringUtil.completarCaracteresIzq(username.toUpperCase(), 10, '0'));
			userToken.append(System.currentTimeMillis());

			// Actualizo token de usuario
			usuarioApm.setUsertoken(userToken.toString());
			if (usuarioApm.validateUpdate()) {
				ApmDAOFactory.getUsuarioApmDAO().update(usuarioApm);
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				usuarioApmVO = (UsuarioApmVO) usuarioApm.toVO(0, false);
			} else {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			}
			// Seteo el certificado a este nivel, ya que el mismo es solo propiedad del VO
			usuarioApmVO.setUsercert(userB64Cert);

			log.debug(funcName + ": exit");
			return usuarioApmVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
}