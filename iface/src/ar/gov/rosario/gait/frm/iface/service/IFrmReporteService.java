package ar.gov.rosario.gait.frm.iface.service;

import ar.gov.rosario.gait.frm.iface.model.ReporteFormularioSearchPage;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.model.UserContext;

public interface IFrmReporteService {
	
	// ---> ABM ReporteFormulario
	public ReporteFormularioSearchPage getReporteFormularioSearchPageInit(UserContext usercontext) throws DemodaServiceException;
	public ReporteFormularioSearchPage getReporteFormularioSearchPageResult(UserContext usercontext, ReporteFormularioSearchPage reporteFormularioSearchPage) throws DemodaServiceException;
	public ReporteFormularioSearchPage imprimirReporteFormulario(UserContext usercontext, ReporteFormularioSearchPage reporteFormularioSearchPage) throws DemodaServiceException;
	
	/*
	public ReporteFormularioAdapter getReporteFormularioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public ReporteFormularioAdapter getReporteFormularioAdapterForCreate(UserContext userContext) throws DemodaServiceException;
	public ReporteFormularioAdapter getReporteFormularioAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	
	public ReporteFormularioVO createReporteFormulario(UserContext userContext, ReporteFormularioVO reporteFormularioVO ) throws DemodaServiceException;
	public ReporteFormularioVO updateReporteFormulario(UserContext userContext, ReporteFormularioVO reporteFormularioVO ) throws DemodaServiceException;
	public ReporteFormularioVO deleteReporteFormulario(UserContext userContext, ReporteFormularioVO reporteFormularioVO ) throws DemodaServiceException;
	public ReporteFormularioVO activarReporteFormulario(UserContext userContext, ReporteFormularioVO reporteFormularioVO ) throws DemodaServiceException;
	public ReporteFormularioVO desactivarReporteFormulario(UserContext userContext, ReporteFormularioVO reporteFormularioVO ) throws DemodaServiceException;	
	public ReporteFormularioAdapter imprimirReporteFormulario(UserContext userContext, ReporteFormularioAdapter reporteFormularioAdapterVO ) throws DemodaServiceException;
	*/
	// <--- ABM ReporteFormulario
	
}