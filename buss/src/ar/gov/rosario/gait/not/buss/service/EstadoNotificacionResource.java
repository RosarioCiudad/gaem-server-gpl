package ar.gov.rosario.gait.not.buss.service;

import java.util.List;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.not.buss.bean.EstadoNotificacion;
import ar.gov.rosario.gait.not.buss.bean.NotNotificacionManager;
import ar.gov.rosario.gait.not.iface.model.EstadoNotificacionVO;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;

public class EstadoNotificacionResource {

	To to = new To();

	public Route[] routes() {
		return new Route[] { Route
				.create("GET", "/gait/api/notification/estadonotification/list.json",
						this.getClass(), "list"), };
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Reply<List<EstadoNotificacionVO>> list(RestRequest<String> req)
			throws Exception {
		try {
			String app = to.String(req.parameters.get("app"));

			List<EstadoNotificacion> objs = NotNotificacionManager.getNotEstadoNotificacion(app);

			return new Reply<List<EstadoNotificacionVO>>(ListUtilBean.toVO(objs));
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

}
