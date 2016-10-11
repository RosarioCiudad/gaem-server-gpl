package ar.gov.rosario.gait.base.buss;

import coop.tecso.demoda.sys.ServiceCaller;
import coop.tecso.demoda.util.To;

public class ResourceCaller implements ServiceCaller {
	static final To to = new To(); 
	@Override
	public Object call(Class<?> klass, String methodName, Object params) {
		//Map<String, Object> reqparms = Work.attr(WorkAttr)
		
//		if (!klass.getName().equals("ar.gov.rosario.gait.seg.buss.service.LoginResource")) {
//			// Determinar UserContext
//			String user = Work.get().  req.getParameter("user");
//			String stoken = req.getParameter("utok");
//			UserContext userContext = SegServiceLocator.getSeguridadService().initUserApm(user, stoken);
//			DemodaUtil.setCurrentUserContext(userContext);
//		}
		return null;
	}
	
}
