package ar.gov.rosario.gait.frm.buss.service;

import java.util.List;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.frm.buss.bean.EstadoTipoFormulario;
import ar.gov.rosario.gait.frm.iface.model.EstadoTipoFormularioVO;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;


	public class EstadoTipoFormularioResource {
		To to = new To();

		public Route[] routes() {
			return new Route[] {
					Route.create("GET", "/gait/api/estadotipoformulario/list.json", this.getClass(), "list")
			};
		}
		
		public Reply<List<EstadoTipoFormularioVO>> list(RestRequest<String> req) throws Exception {
			
			try {
				String app = to.String(req.parameters.get("app"));
				
				List<EstadoTipoFormulario> objs = FrmFormularioManager.getFrmEstadoTipoFormulario(app);
				
				return new Reply<List<EstadoTipoFormularioVO>>(ListUtilBean.toVO(objs));
			} catch (Exception e) {
				throw new DemodaServiceException(e);
			} finally {
				GaitHibernateUtil.closeSession();
			}
		}
}