package ar.gov.rosario.gait.pro.iface.service;

import ar.gov.rosario.gait.pro.iface.model.AdpCorridaAdapter;
import ar.gov.rosario.gait.pro.iface.model.CorridaVO;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.UserContext;

public interface IProAdpProcesoService {

	// ---> ABM_CORRIDA
	public AdpCorridaAdapter getAdpCorridaAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;

	public CorridaVO activarProceso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException;
	public CorridaVO reprogramarProceso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException;
	public CorridaVO cancelarProceso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException;
	public CorridaVO reiniciarProceso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException;
	public CorridaVO siguientePaso(UserContext userContext, CorridaVO corridaVO) throws DemodaServiceException;

	public String obtenerFileCorridaName(Long idFileCorrida) throws DemodaServiceException;
	// <--- ABM_CORRIDA

	public AdpCorridaAdapter getEstadoPaso(UserContext userSession, CommonKey id, Integer paso) throws DemodaServiceException;
	public String getLogFile(UserContext userSession, CommonKey id) throws DemodaServiceException;

}
