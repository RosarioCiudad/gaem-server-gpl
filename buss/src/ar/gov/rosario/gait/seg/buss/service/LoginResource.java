package ar.gov.rosario.gait.seg.buss.service;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.iface.model.UsuarioApmVO;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.seg.buss.bean.SegSeguridadManager;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.util.To;

/**
 * Login de usuario
 * 
 *     GET /gait/api/login.json
 * 
 * Input:
 * 
 *     username: Nombre de usuario
 *     password: Password
 *     deviceid: Device Id. (string que identifica al dispositivo)
 * 
 * Response:
 * 
 *     code: 200, 
 *     message: "ok", 
 *     result: (string)
 *     } 
 */
public class LoginResource {

	private Logger log = Logger.getLogger(LoginResource.class);
	private To to = new To();

	public Route[] routes() {
		return new Route[] {
				Route.create("POST", "/gait/api/loginManager.json", this.getClass(), "loginPost"),
				Route.create("GET", "/gait/api/login.json", this.getClass(), "loginGet")
		};
	}

	/**
	 * 
	 */
	public Reply<UsuarioApmVO> loginPost(RestRequest<String> req) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		log.debug("LOGIN: entro a loginPost()");

		String username = to.String(req.parameters.get("username"));
		String password = to.String(req.parameters.get("password"));
		String deviceid = to.String(req.parameters.get("deviceid"));

		try {
			// Obtengo certificado y token
			UsuarioApmVO usuarioApmVO = SegSeguridadManager.loginUsuarioApm(username, password, deviceid);

			if(usuarioApmVO.hasError()){
				String msg = usuarioApmVO.getListError().get(0).key().substring(1);
				return new Reply<UsuarioApmVO>(500, msg, new UsuarioApmVO());
			}
			return new Reply<UsuarioApmVO>(usuarioApmVO);
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	/**
	 * En un principio el login se hacia por post, en la versión nueva de la aplicación para el moto E se cambió para que sea por post.
	 * Se mantiene este método por la compatibilidad hacia atrás, para que los celulares con la app vieja sigan podiendo conectarse al server.
	 */
	@Deprecated
	public Reply<UsuarioApmVO> loginGet(RestRequest<String> req) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		log.debug("LOGIN: entro a loginGet()");

		String username = to.String(req.parameters.get("username"));
		String password = to.String(req.parameters.get("password"));
		String deviceid = to.String(req.parameters.get("deviceid"));

		try {
			// Obtengo certificado y token
			UsuarioApmVO usuarioApmVO = SegSeguridadManager.loginUsuarioApm(username, password, deviceid);

			if(usuarioApmVO.hasError()){
				String msg = usuarioApmVO.getListError().get(0).key().substring(1);
				return new Reply<UsuarioApmVO>(500, msg, new UsuarioApmVO());
			}
			return new Reply<UsuarioApmVO>(usuarioApmVO);
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
}