package ar.gov.rosario.gait.def.buss.service;

import java.util.List;

import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.def.buss.bean.AreaAplicacionPerfil;
import ar.gov.rosario.gait.def.iface.model.AreaAplicacionPerfilVO;
import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;

public class AreaAplicacionPerfilResource {
	To to = new To();

	public Route[] routes() {
		return new Route[] {
				Route.create("GET", "/gait/api/areaaplicacionperfil/list.json", this.getClass(), "list")
		};
	}
		
	public Reply<List<AreaAplicacionPerfilVO>> list(RestRequest<String> req) throws Exception {				
		Session session = null;
		try {
			session = GaitHibernateUtil.currentSession();

			List<AreaAplicacionPerfil> listAreaAplicacionPerfil = AreaAplicacionPerfil.getList();
			List<AreaAplicacionPerfilVO> listVO = ListUtilBean.toVO(listAreaAplicacionPerfil, 0);
			
			return new Reply<List<AreaAplicacionPerfilVO>>(listVO);
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}
	
}
