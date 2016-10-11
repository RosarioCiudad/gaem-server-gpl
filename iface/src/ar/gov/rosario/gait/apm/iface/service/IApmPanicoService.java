package ar.gov.rosario.gait.apm.iface.service;

import ar.gov.rosario.gait.apm.iface.model.PanicoAdapter;
import ar.gov.rosario.gait.apm.iface.model.PanicoSearchPage;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.UserContext;

public interface IApmPanicoService {
	
	// ----> APM Panico				
	public PanicoSearchPage getPanicoSearchPageInit(UserContext userContext) throws DemodaServiceException;
	public PanicoSearchPage getPanicoSearchPageResult(UserContext usercontext, PanicoSearchPage panicoSearchPageVO) throws DemodaServiceException;
	
	public PanicoAdapter getPanicoAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public PanicoAdapter getPanicoAdapterForView(UserContext userContext, CommonKey commonKey, PanicoSearchPage panicoSearchPageVO) throws DemodaServiceException;
	public PanicoAdapter getPanicoAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public PanicoAdapter getPanicoAdapterForUpdate(UserContext userContext, CommonKey commonKey, PanicoSearchPage panicoSearchPageVO) throws DemodaServiceException;

	public PanicoAdapter getPanicoAdapterForCambioEstado(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public PanicoAdapter cambiarEstadoPanico(UserContext userContext, PanicoAdapter panicoAdapter) throws DemodaServiceException;
	// ----> APM Panico
	
}