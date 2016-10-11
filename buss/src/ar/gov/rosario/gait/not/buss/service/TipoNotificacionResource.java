package ar.gov.rosario.gait.not.buss.service;

import java.util.List;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.not.buss.bean.NotNotificacionManager;
import ar.gov.rosario.gait.not.buss.bean.TipoNotificacion;
import ar.gov.rosario.gait.not.iface.model.TipoNotificacionVO;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;

public class TipoNotificacionResource {

	To to = new To();

	public Route[] routes() {
		return new Route[] { Route
				.create("GET", "/gait/api/notification/tiponotification/list.json",
						this.getClass(), "list"), };
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Reply<List<TipoNotificacionVO>> list(RestRequest<String> req)
			throws Exception {
		try {
			String app = to.String(req.parameters.get("app"));

			List<TipoNotificacion> objs = NotNotificacionManager.getNotNotificacion(app);

			return new Reply<List<TipoNotificacionVO>>(ListUtilBean.toVO(objs));
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

}
