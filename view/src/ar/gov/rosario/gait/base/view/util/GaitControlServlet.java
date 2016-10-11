package ar.gov.rosario.gait.base.view.util;

import javax.servlet.http.HttpServletRequest;

import ar.gov.rosario.gait.seg.iface.service.SegServiceLocator;
import coop.tecso.demoda.http.ControlServlet;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.UserContext;

public class GaitControlServlet extends ControlServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void preInvoke(HttpServletRequest req, Class<?> klass, String methodname) {
		if (!klass.getName().equals("ar.gov.rosario.gait.seg.buss.service.LoginResource")) {
			// Determinar UserContext
			String username = req.getParameter("username");
			String usertoken = req.getParameter("usertoken");
			UserContext userContext = SegServiceLocator.getSeguridadService().initUserApm(username, usertoken);
			DemodaUtil.setCurrentUserContext(userContext);
		}
	}
	
}
