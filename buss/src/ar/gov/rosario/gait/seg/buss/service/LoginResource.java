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
				Route.create("GET", "/gait/api/login.json", this.getClass(), "login")
		};
	}

	/**
	 * 
	 */
	public Reply<UsuarioApmVO> login(RestRequest<String> req) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		
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