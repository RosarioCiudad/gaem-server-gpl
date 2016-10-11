package ar.gov.rosario.gait.def.buss.service;

import java.util.List;

import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.def.buss.bean.Parametro;
import ar.gov.rosario.gait.def.iface.model.ParametroVO;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;

/**
 * Obtiene los Parametros del server Gait
 * 
 *     GET /gait/api/parametro/list.json
 * 
 * Input:
 * 
 *     user: Nombre de usuario
 *     utok: Token de usuario
 * 
 * Response:
 * 
 *     code: 200, 
 *     message: "ok", 
 *     result: {
 *         result: (ParametroVO[])
 *     } 
 */
public class ParametroResource {
	To to = new To();

	public Route[] routes() {
		return new Route[] {
				Route.create("GET", "/gait/api/parametro/list.json", this.getClass(), "list")
		};
	}
		
	public Reply<List<ParametroVO>> list(RestRequest<String> req) throws Exception {				
		Session session = null;
		try {
			session = GaitHibernateUtil.currentSession();

			List<Parametro> listParametro = Parametro.getList();
			List<ParametroVO> listVO = ListUtilBean.toVO(listParametro, 0);
			
			return new Reply<List<ParametroVO>>(listVO);
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}
	
}
