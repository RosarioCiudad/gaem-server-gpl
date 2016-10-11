package ar.gov.rosario.gait.frm.iface.service;

import ar.gov.rosario.gait.frm.iface.model.NumeracionAdapter;
import ar.gov.rosario.gait.frm.iface.model.NumeracionSearchPage;
import ar.gov.rosario.gait.frm.iface.model.NumeracionVO;
import ar.gov.rosario.gait.frm.iface.model.SerieAdapter;
import ar.gov.rosario.gait.frm.iface.model.SerieSearchPage;
import ar.gov.rosario.gait.frm.iface.model.SerieVO;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.UserContext;


public interface IFrmNumeracionService {
	
	// ---> ABM Serie
	public SerieSearchPage getSerieSearchPageInit(UserContext usercontext) throws DemodaServiceException;
	public SerieSearchPage getSerieSearchPageResult(UserContext usercontext, SerieSearchPage serieSearchPage) throws DemodaServiceException;

	public SerieAdapter getSerieAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public SerieAdapter getSerieAdapterForCreate(UserContext userContext) throws DemodaServiceException;
	public SerieAdapter getSerieAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	
	public SerieVO createSerie(UserContext userContext, SerieVO serieVO ) throws DemodaServiceException;
	public SerieVO updateSerie(UserContext userContext, SerieVO serieVO ) throws DemodaServiceException;
	public SerieVO deleteSerie(UserContext userContext, SerieVO serieVO ) throws DemodaServiceException;
	// <--- ABM Serie
	
	// ---> ABM Numeracion
	public NumeracionSearchPage getNumeracionSearchPageInit(UserContext usercontext) throws DemodaServiceException;
	public NumeracionSearchPage getNumeracionSearchPageResult(UserContext usercontext, NumeracionSearchPage numeracionSearchPage) throws DemodaServiceException;

	public NumeracionAdapter getNumeracionAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
	public NumeracionAdapter getNumeracionAdapterForCreate(UserContext userContext) throws DemodaServiceException;
	public NumeracionAdapter getNumeracionAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;

	public NumeracionVO createNumeracion(UserContext userContext, NumeracionVO numeracionVO ) throws DemodaServiceException;
	public NumeracionVO updateNumeracion(UserContext userContext, NumeracionVO numeracionVO ) throws DemodaServiceException;
	public NumeracionVO deleteNumeracion(UserContext userContext, NumeracionVO numeracionVO ) throws DemodaServiceException;
	// <--- ABM Numeracion
}