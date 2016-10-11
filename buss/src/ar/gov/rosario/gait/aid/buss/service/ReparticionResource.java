package ar.gov.rosario.gait.aid.buss.service;

import java.util.List;

import ar.gov.rosario.gait.aid.iface.model.UsuarioApmReparticionVO;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.tmf.TmfWebServiceManager;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;

/**
 * Obtiene las versiones de cada tabla de una aplicacion.
 * 
 *     GET /gait/api/reparticion/list.json
 * 
 * Input:
 * 
 *     user: Nombre de usuario
 *     utok: Token de session
 *     app:  Codigo de la aplicacion
 * 
 * Response:
 * 
 *     code: 200, 
 *     message: "ok", 
 *     result: {
 *         result: (UsuarioApmReparticionVO[])
 *     } 
 */
public class ReparticionResource {
	To to = new To();

	public Route[] routes() {
		return new Route[] {
				Route.create("GET", "/gait/api/reparticion/list.json", this.getClass(), "list")
		};
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public Reply<List<UsuarioApmReparticionVO>> list(RestRequest<String> req) throws Exception {
		try {
			String username = to.String(req.parameters.get("username"));

			List<UsuarioApmReparticionVO> listUsuarioApmReparticion = 
					TmfWebServiceManager.getInstance().obtenerInspectorReparticion(username);

			return new Reply<List<UsuarioApmReparticionVO>>(listUsuarioApmReparticion);
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
}