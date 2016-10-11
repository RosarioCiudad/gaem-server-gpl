package ar.gov.rosario.gait.apm.iface.service;

import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoVO;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorOpcionAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorOpcionVO;
import ar.gov.rosario.gait.apm.iface.model.AplPerfilSeccionCampoValorVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionBinarioVersionAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionBinarioVersionVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionParametroAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionParametroVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilParametroAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilParametroVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilSearchPage;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilSeccionAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilSeccionVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionSearchPage;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTablaAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTablaSearchPage;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTablaVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTipoBinarioAdapter;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTipoBinarioSearchPage;
import ar.gov.rosario.gait.apm.iface.model.AplicacionTipoBinarioVO;
import ar.gov.rosario.gait.apm.iface.model.AplicacionVO;
import ar.gov.rosario.gait.apm.iface.model.CampoAdapter;
import ar.gov.rosario.gait.apm.iface.model.CampoSearchPage;
import ar.gov.rosario.gait.apm.iface.model.CampoVO;
import ar.gov.rosario.gait.apm.iface.model.CampoValorAdapter;
import ar.gov.rosario.gait.apm.iface.model.CampoValorOpcionAdapter;
import ar.gov.rosario.gait.apm.iface.model.CampoValorOpcionVO;
import ar.gov.rosario.gait.apm.iface.model.CampoValorVO;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilAdapter;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilSearchPage;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilVO;
import ar.gov.rosario.gait.apm.iface.model.ImpresoraAdapter;
import ar.gov.rosario.gait.apm.iface.model.ImpresoraSearchPage;
import ar.gov.rosario.gait.apm.iface.model.ImpresoraVO;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoAdapter;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoAplicacionAdapter;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoAplicacionVO;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoSearchPage;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoUsuarioAdapter;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoUsuarioVO;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoVO;
import ar.gov.rosario.gait.apm.iface.model.SeccionAdapter;
import ar.gov.rosario.gait.apm.iface.model.SeccionSearchPage;
import ar.gov.rosario.gait.apm.iface.model.SeccionVO;
import ar.gov.rosario.gait.apm.iface.model.TablaVersionAdapter;
import ar.gov.rosario.gait.apm.iface.model.TablaVersionSearchPage;
import ar.gov.rosario.gait.apm.iface.model.TablaVersionVO;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmAdapter;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmDMAdapter;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmDMVO;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmImpresoraAdapter;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmImpresoraVO;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmSearchPage;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmVO;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.UserContext;


public interface IApmAplicacionService {

		// ---> ABM Seccion
		public SeccionSearchPage getSeccionSearchPageInit(UserContext usercontext) throws DemodaServiceException;
		public SeccionSearchPage getSeccionSearchPageResult(UserContext usercontext, SeccionSearchPage seccionSearchPage) throws DemodaServiceException;

		public SeccionAdapter getSeccionAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public SeccionAdapter getSeccionAdapterForCreate(UserContext userContext) throws DemodaServiceException;
		public SeccionAdapter getSeccionAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		
		public SeccionVO createSeccion(UserContext userContext, SeccionVO seccionVO ) throws DemodaServiceException;
		public SeccionVO updateSeccion(UserContext userContext, SeccionVO seccionVO ) throws DemodaServiceException;
		public SeccionVO deleteSeccion(UserContext userContext, SeccionVO seccionVO ) throws DemodaServiceException;	
		public SeccionAdapter imprimirSeccion(UserContext userContext, SeccionAdapter seccionAdapterVO ) throws DemodaServiceException;
		// <--- ABM Seccion
		
		// ---> APM DispositivoMovil
		public DispositivoMovilSearchPage getDispositivoMovilSearchPageInit(UserContext usercontext) throws DemodaServiceException;
		public DispositivoMovilSearchPage getDispositivoMovilSearchPageResult(UserContext usercontext, DispositivoMovilSearchPage dispositivoMovilSearchPage) throws DemodaServiceException;

		public DispositivoMovilAdapter getDispositivoMovilAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public DispositivoMovilAdapter getDispositivoMovilAdapterForCreate(UserContext userContext) throws DemodaServiceException;
		public DispositivoMovilAdapter getDispositivoMovilAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		
		public DispositivoMovilVO createDispositivoMovil(UserContext userContext, DispositivoMovilVO dispositivoMovilVO ) throws DemodaServiceException;
		public DispositivoMovilVO updateDispositivoMovil(UserContext userContext, DispositivoMovilVO dispositivoMovilVO ) throws DemodaServiceException;
		public DispositivoMovilVO deleteDispositivoMovil(UserContext userContext, DispositivoMovilVO dispositivoMovilVO ) throws DemodaServiceException;	
		// <--- APM DispositivoMovil
		
		// ----> APM Campo			
		public CampoSearchPage getCampoSearchPageInit(UserContext usercontext) throws DemodaServiceException;
		public CampoSearchPage getCampoSearchPageResult(UserContext usercontext, CampoSearchPage campoSearchPage) throws DemodaServiceException;

		public CampoAdapter getCampoAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public CampoAdapter getCampoAdapterForCreate(UserContext userContext) throws DemodaServiceException;
		public CampoAdapter getCampoAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		
		public CampoVO createCampo(UserContext userContext, CampoVO campoVO ) throws DemodaServiceException;
		public CampoVO updateCampo(UserContext userContext, CampoVO campoVO ) throws DemodaServiceException;
		public CampoVO deleteCampo(UserContext userContext, CampoVO campoVO ) throws DemodaServiceException;	
		// ----> APM Campo
		
		// ----> APM CampoValor				
		public CampoValorAdapter getCampoValorAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public CampoValorAdapter getCampoValorAdapterForCreate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public CampoValorAdapter getCampoValorAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public CampoValorVO createCampoValor(UserContext userContext, CampoValorVO campoValorVO ) throws DemodaServiceException;
		public CampoValorVO updateCampoValor(UserContext userContext, CampoValorVO campoValorVO ) throws DemodaServiceException;
		public CampoValorVO deleteCampoValor(UserContext userContext, CampoValorVO campoValorVO ) throws DemodaServiceException;	
		// ----> APM CampoValor
		
		// ----> APM CampoValorOpcion				
		public CampoValorOpcionAdapter getCampoValorOpcionAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public CampoValorOpcionAdapter getCampoValorOpcionAdapterForCreate(UserContext userContext,CommonKey commonKey) throws DemodaServiceException;
		public CampoValorOpcionAdapter getCampoValorOpcionAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public CampoValorOpcionVO createCampoValorOpcion(UserContext userContext, CampoValorOpcionVO campoValorOpcionVO ) throws DemodaServiceException;
		public CampoValorOpcionVO updateCampoValorOpcion(UserContext userContext, CampoValorOpcionVO campoValorOpcionVO ) throws DemodaServiceException;
		public CampoValorOpcionVO deleteCampoValorOpcion(UserContext userContext, CampoValorOpcionVO campoValorOpcionVO ) throws DemodaServiceException;	
		// ----> APM CampoValorOpcion
		
		// ----> APM Aplicacion Perfil			
		public AplicacionPerfilSearchPage getAplicacionPerfilSearchPageInit(UserContext usercontext) throws DemodaServiceException;
		public AplicacionPerfilSearchPage getAplicacionPerfilSearchPageResult(UserContext usercontext, AplicacionPerfilSearchPage aplicacionPerfilSearchPage) throws DemodaServiceException;

		public AplicacionPerfilAdapter getAplicacionPerfilAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplicacionPerfilAdapter getAplicacionPerfilAdapterForCreate(UserContext userContext) throws DemodaServiceException;
		public AplicacionPerfilAdapter getAplicacionPerfilAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		
		public AplicacionPerfilVO createAplicacionPerfil(UserContext userContext, AplicacionPerfilVO campoVO ) throws DemodaServiceException;
		public AplicacionPerfilVO updateAplicacionPerfil(UserContext userContext, AplicacionPerfilVO campoVO ) throws DemodaServiceException;
		public AplicacionPerfilVO deleteAplicacionPerfil(UserContext userContext, AplicacionPerfilVO campoVO ) throws DemodaServiceException;	
		public AplicacionPerfilVO cloneAplicacionPerfil(UserContext userContext, AplicacionPerfilVO campoVO  ) throws DemodaServiceException;	
		// ----> APM Aplicacion Perfil	
		
		// ----> APM AplicacionPerfilSeccion				
		public AplicacionPerfilSeccionAdapter getAplicacionPerfilSeccionAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplicacionPerfilSeccionAdapter getAplicacionPerfilSeccionAdapterForCreate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplicacionPerfilSeccionAdapter getAplicacionPerfilSeccionAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public AplicacionPerfilSeccionVO createAplicacionPerfilSeccion(UserContext userContext, AplicacionPerfilSeccionVO aplicacionPerfilSeccionVO ) throws DemodaServiceException;
		public AplicacionPerfilSeccionVO updateAplicacionPerfilSeccion(UserContext userContext, AplicacionPerfilSeccionVO aplicacionPerfilSeccionVO ) throws DemodaServiceException;
		public AplicacionPerfilSeccionVO deleteAplicacionPerfilSeccion(UserContext userContext, AplicacionPerfilSeccionVO aplicacionPerfilSeccionVO ) throws DemodaServiceException;	
		// ----> APM AplicacionPerfilSeccion
		
		// ----> APM AplPerfilSeccionCampo			
		public AplPerfilSeccionCampoAdapter getAplPerfilSeccionCampoAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplPerfilSeccionCampoAdapter getAplPerfilSeccionCampoAdapterForCreate(UserContext userContext,CommonKey commonKey) throws DemodaServiceException;
		public AplPerfilSeccionCampoAdapter getAplPerfilSeccionCampoAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public AplPerfilSeccionCampoVO createAplPerfilSeccionCampo(UserContext userContext, AplPerfilSeccionCampoVO aplPerfilSeccionCampoVO ) throws DemodaServiceException;
		public AplPerfilSeccionCampoVO updateAplPerfilSeccionCampo(UserContext userContext, AplPerfilSeccionCampoVO aplPerfilSeccionCampoVO ) throws DemodaServiceException;
		public AplPerfilSeccionCampoVO deleteAplPerfilSeccionCampo(UserContext userContext, AplPerfilSeccionCampoVO aplPerfilSeccionCampoVO ) throws DemodaServiceException;	
		// ----> APM AplPerfilSeccionCampo
		
		// ----> APM AplPerfilSeccionCampoValor			
		public AplPerfilSeccionCampoValorAdapter getAplPerfilSeccionCampoValorAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplPerfilSeccionCampoValorAdapter getAplPerfilSeccionCampoValorAdapterForCreate(UserContext userContext,CommonKey commonKey) throws DemodaServiceException;
		public AplPerfilSeccionCampoValorAdapter getAplPerfilSeccionCampoValorAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public AplPerfilSeccionCampoValorVO createAplPerfilSeccionCampoValor(UserContext userContext, AplPerfilSeccionCampoValorVO aplPerfilSeccionCampoValorVO ) throws DemodaServiceException;
		public AplPerfilSeccionCampoValorVO updateAplPerfilSeccionCampoValor(UserContext userContext, AplPerfilSeccionCampoValorVO aplPerfilSeccionCampoValorVO ) throws DemodaServiceException;
		public AplPerfilSeccionCampoValorVO deleteAplPerfilSeccionCampoValor(UserContext userContext, AplPerfilSeccionCampoValorVO aplPerfilSeccionCampoValorVO ) throws DemodaServiceException;	
		// ----> APM AplPerfilSeccionCampoValor
				
		// ----> APM AplPerfilSeccionCampoValorOpcion				
		public AplPerfilSeccionCampoValorOpcionAdapter getAplPerfilSeccionCampoValorOpcionAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplPerfilSeccionCampoValorOpcionAdapter getAplPerfilSeccionCampoValorOpcionAdapterForCreate(UserContext userContext,CommonKey commonKey) throws DemodaServiceException;
		public AplPerfilSeccionCampoValorOpcionAdapter getAplPerfilSeccionCampoValorOpcionAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public AplPerfilSeccionCampoValorOpcionVO createAplPerfilSeccionCampoValorOpcion(UserContext userContext, AplPerfilSeccionCampoValorOpcionVO aplPerfilSeccionCampoValorOpcionVO ) throws DemodaServiceException;
		public AplPerfilSeccionCampoValorOpcionVO updateAplPerfilSeccionCampoValorOpcion(UserContext userContext, AplPerfilSeccionCampoValorOpcionVO aplPerfilSeccionCampoValorOpcionVO ) throws DemodaServiceException;
		public AplPerfilSeccionCampoValorOpcionVO deleteAplPerfilSeccionCampoValorOpcion(UserContext userContext, AplPerfilSeccionCampoValorOpcionVO aplPerfilSeccionCampoValorOpcionVO ) throws DemodaServiceException;	
		// ----> APM AplPerfilSeccionCampoValorOpcion
		
		// ----> APM AplicacionPerfilParametro				
		public AplicacionPerfilParametroAdapter getAplicacionPerfilParametroAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplicacionPerfilParametroAdapter getAplicacionPerfilParametroAdapterForCreate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplicacionPerfilParametroAdapter getAplicacionPerfilParametroAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public AplicacionPerfilParametroVO createAplicacionPerfilParametro(UserContext userContext, AplicacionPerfilParametroVO aplicacionPerfilParametroVO ) throws DemodaServiceException;
		public AplicacionPerfilParametroVO updateAplicacionPerfilParametro(UserContext userContext, AplicacionPerfilParametroVO aplicacionPerfilParametroVO ) throws DemodaServiceException;
		public AplicacionPerfilParametroVO deleteAplicacionPerfilParametro(UserContext userContext, AplicacionPerfilParametroVO aplicacionPerfilParametroVO ) throws DemodaServiceException;	
		// ----> APM AplicacionPerfilParametro	
		
		// ---> APM Impresora
		public ImpresoraSearchPage getImpresoraSearchPageInit(UserContext usercontext) throws DemodaServiceException;
		public ImpresoraSearchPage getImpresoraSearchPageResult(UserContext usercontext, ImpresoraSearchPage impresoraSearchPage) throws DemodaServiceException;

		public ImpresoraAdapter getImpresoraAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public ImpresoraAdapter getImpresoraAdapterForCreate(UserContext userContext) throws DemodaServiceException;
		public ImpresoraAdapter getImpresoraAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		
		public ImpresoraVO createImpresora(UserContext userContext, ImpresoraVO impresoraVO ) throws DemodaServiceException;
		public ImpresoraVO updateImpresora(UserContext userContext, ImpresoraVO impresoraVO ) throws DemodaServiceException;
		public ImpresoraVO deleteImpresora(UserContext userContext, ImpresoraVO impresoraVO ) throws DemodaServiceException;	
		// <--- APM Impresora
		
		// ----> APM Aplicacion 			
		public AplicacionSearchPage getAplicacionSearchPageInit(UserContext usercontext) throws DemodaServiceException;
		public AplicacionSearchPage getAplicacionSearchPageResult(UserContext usercontext, AplicacionSearchPage aplicacionSearchPage) throws DemodaServiceException;
		public AplicacionAdapter getAplicacionAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplicacionAdapter getAplicacionAdapterForCreate(UserContext userContext) throws DemodaServiceException;
		public AplicacionAdapter getAplicacionAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public AplicacionVO createAplicacion(UserContext userContext, AplicacionVO campoVO ) throws DemodaServiceException;
		public AplicacionVO updateAplicacion(UserContext userContext, AplicacionVO campoVO ) throws DemodaServiceException;
		public AplicacionVO deleteAplicacion(UserContext userContext, AplicacionVO campoVO ) throws DemodaServiceException;			// ----> APM Aplicacion 	
		// ----> APM Aplicacion 
		
		// ----> APM AplicacionParametro				
		public AplicacionParametroAdapter getAplicacionParametroAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplicacionParametroAdapter getAplicacionParametroAdapterForCreate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplicacionParametroAdapter getAplicacionParametroAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public AplicacionParametroVO createAplicacionParametro(UserContext userContext, AplicacionParametroVO aplicacionPerfilParametroVO ) throws DemodaServiceException;
		public AplicacionParametroVO updateAplicacionParametro(UserContext userContext, AplicacionParametroVO aplicacionPerfilParametroVO ) throws DemodaServiceException;
		public AplicacionParametroVO deleteAplicacionParametro(UserContext userContext, AplicacionParametroVO aplicacionPerfilParametroVO ) throws DemodaServiceException;	
		// ----> APM Aplicacionarametro	
		
		// ----> APM Binario Version				
		public AplicacionBinarioVersionAdapter getAplicacionBinarioVersionAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplicacionBinarioVersionAdapter getAplicacionBinarioVersionAdapterForCreate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplicacionBinarioVersionAdapter getAplicacionBinarioVersionAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public AplicacionBinarioVersionVO createAplicacionBinarioVersion(UserContext userContext, AplicacionBinarioVersionVO aplicacionBinarioVersionVO) throws DemodaServiceException;
		public AplicacionBinarioVersionVO updateAplicacionBinarioVersion(UserContext userContext, AplicacionBinarioVersionVO aplicacionBinarioVersionVO ) throws DemodaServiceException;
		public AplicacionBinarioVersionVO deleteAplicacionBinarioVersion(UserContext userContext, AplicacionBinarioVersionVO aplicacionBinarioVersionVO ) throws DemodaServiceException;	
		// ----> APM Binario Version	

		// ----> APM UsuarioApm 			
		public UsuarioApmSearchPage getUsuarioApmSearchPageInit(UserContext usercontext) throws DemodaServiceException;
		public UsuarioApmSearchPage getUsuarioApmSearchPageResult(UserContext usercontext, UsuarioApmSearchPage usuarioApmSearchPage) throws DemodaServiceException;
		public UsuarioApmAdapter getUsuarioApmAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public UsuarioApmAdapter getUsuarioApmAdapterForCreate(UserContext userContext) throws DemodaServiceException;
		public UsuarioApmAdapter getUsuarioApmAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public UsuarioApmVO createUsuarioApm(UserContext userContext, UsuarioApmVO campoVO ) throws DemodaServiceException;
		public UsuarioApmVO updateUsuarioApm(UserContext userContext, UsuarioApmVO campoVO ) throws DemodaServiceException;
		public UsuarioApmVO deleteUsuarioApm(UserContext userContext, UsuarioApmVO campoVO ) throws DemodaServiceException;			
		// ----> APM UsuarioApm 
		
		// ----> APM PerfilAccesoUsuario				
		public PerfilAccesoUsuarioAdapter getPerfilAccesoUsuarioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public PerfilAccesoUsuarioAdapter getPerfilAccesoUsuarioAdapterForCreate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public PerfilAccesoUsuarioAdapter getPerfilAccesoUsuarioAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public PerfilAccesoUsuarioVO createPerfilAccesoUsuario(UserContext userContext, PerfilAccesoUsuarioVO perfilAccesoUsuarioVO ) throws DemodaServiceException;
		public PerfilAccesoUsuarioVO updatePerfilAccesoUsuario(UserContext userContext, PerfilAccesoUsuarioVO perfilAccesoUsuarioVO ) throws DemodaServiceException;
		public PerfilAccesoUsuarioVO deletePerfilAccesoUsuario(UserContext userContext, PerfilAccesoUsuarioVO perfilAccesoUsuarioVO ) throws DemodaServiceException;	
		// ----> APM PerfilAccesoUsuario	
		
		// ----> APM Usuario Apm DM				
		public UsuarioApmDMAdapter getUsuarioApmDMAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public UsuarioApmDMAdapter getUsuarioApmDMAdapterForCreate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public UsuarioApmDMAdapter getUsuarioApmDMAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public UsuarioApmDMVO createUsuarioApmDM(UserContext userContext, UsuarioApmDMVO usuarioApmDMVO ) throws DemodaServiceException;
		public UsuarioApmDMVO updateUsuarioApmDM(UserContext userContext, UsuarioApmDMVO usuarioApmDMVO ) throws DemodaServiceException;
		public UsuarioApmDMVO deleteUsuarioApmDM(UserContext userContext, UsuarioApmDMVO usuarioApmDMVO ) throws DemodaServiceException;	
		// ----> APM Usuario Apm DM		
		
		// ---> ABM TablaVersion
		public TablaVersionSearchPage getTablaVersionSearchPageInit(UserContext usercontext) throws DemodaServiceException;
		public TablaVersionSearchPage getTablaVersionSearchPageResult(UserContext usercontext, TablaVersionSearchPage tablaVersionSearchPage) throws DemodaServiceException;

		public TablaVersionAdapter getTablaVersionAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public TablaVersionAdapter getTablaVersionAdapterForCreate(UserContext userContext) throws DemodaServiceException;
		public TablaVersionAdapter getTablaVersionAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		
		public TablaVersionVO createTablaVersion(UserContext userContext, TablaVersionVO tablaVersionVO ) throws DemodaServiceException;
		public TablaVersionVO updateTablaVersion(UserContext userContext, TablaVersionVO tablaVersionVO ) throws DemodaServiceException;
		public TablaVersionVO deleteTablaVersion(UserContext userContext, TablaVersionVO tablaVersionVO ) throws DemodaServiceException;
		// <--- ABM TablaVersion

		// ---> ABM AplicacionTabla
		public AplicacionTablaSearchPage getAplicacionTablaSearchPageInit(UserContext usercontext) throws DemodaServiceException;
		public AplicacionTablaSearchPage getAplicacionTablaSearchPageResult(UserContext usercontext, AplicacionTablaSearchPage aplicacionTablaSearchPage) throws DemodaServiceException;

		public AplicacionTablaAdapter getAplicacionTablaAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplicacionTablaAdapter getAplicacionTablaAdapterForCreate(UserContext userContext) throws DemodaServiceException;
		public AplicacionTablaAdapter getAplicacionTablaAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		
		public AplicacionTablaVO createAplicacionTabla(UserContext userContext, AplicacionTablaVO aplicacionTablaVO ) throws DemodaServiceException;
		public AplicacionTablaVO updateAplicacionTabla(UserContext userContext, AplicacionTablaVO aplicacionTablaVO ) throws DemodaServiceException;
		public AplicacionTablaVO deleteAplicacionTabla(UserContext userContext, AplicacionTablaVO aplicacionTablaVO ) throws DemodaServiceException;
		// <--- ABM AplicacionTabla

		// ---> ABM AplicacionTipoBinario
		public AplicacionTipoBinarioSearchPage getAplicacionTipoBinarioSearchPageInit(UserContext usercontext) throws DemodaServiceException;
		public AplicacionTipoBinarioSearchPage getAplicacionTipoBinarioSearchPageResult(UserContext usercontext, AplicacionTipoBinarioSearchPage aplicacionTipoBinarioSearchPage) throws DemodaServiceException;

		public AplicacionTipoBinarioAdapter getAplicacionTipoBinarioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public AplicacionTipoBinarioAdapter getAplicacionTipoBinarioAdapterForCreate(UserContext userContext) throws DemodaServiceException;
		public AplicacionTipoBinarioAdapter getAplicacionTipoBinarioAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
				
		public AplicacionTipoBinarioVO createAplicacionTipoBinario(UserContext userContext, AplicacionTipoBinarioVO aplicacionTipoBinarioVO ) throws DemodaServiceException;
		public AplicacionTipoBinarioVO updateAplicacionTipoBinario(UserContext userContext, AplicacionTipoBinarioVO aplicacionTipoBinarioVO ) throws DemodaServiceException;
		public AplicacionTipoBinarioVO deleteAplicacionTipoBinario(UserContext userContext, AplicacionTipoBinarioVO aplicacionTipoBinarioVO ) throws DemodaServiceException;
		// <--- ABM AplicacionTipoBinario

		// ----> APM PerfilAcceso 			
		public PerfilAccesoSearchPage getPerfilAccesoSearchPageInit(UserContext usercontext) throws DemodaServiceException;
		public PerfilAccesoSearchPage getPerfilAccesoSearchPageResult(UserContext usercontext, PerfilAccesoSearchPage perfilAccesoSearchPage) throws DemodaServiceException;
		
		public PerfilAccesoAdapter getPerfilAccesoAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public PerfilAccesoAdapter getPerfilAccesoAdapterForCreate(UserContext userContext) throws DemodaServiceException;
		public PerfilAccesoAdapter getPerfilAccesoAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		
		public PerfilAccesoVO createPerfilAcceso(UserContext userContext, PerfilAccesoVO campoVO ) throws DemodaServiceException;
		public PerfilAccesoVO updatePerfilAcceso(UserContext userContext, PerfilAccesoVO campoVO ) throws DemodaServiceException;
		public PerfilAccesoVO deletePerfilAcceso(UserContext userContext, PerfilAccesoVO campoVO ) throws DemodaServiceException; 	
		// <---- APM PerfilAcceso 
		
		// ----> APM UsuarioApmImpresora				
		public UsuarioApmImpresoraAdapter getUsuarioApmImpresoraAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public UsuarioApmImpresoraAdapter getUsuarioApmImpresoraAdapterForCreate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public UsuarioApmImpresoraAdapter getUsuarioApmImpresoraAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		public UsuarioApmImpresoraVO createUsuarioApmImpresora(UserContext userContext, UsuarioApmImpresoraVO usuarioApmDMVO ) throws DemodaServiceException;
		public UsuarioApmImpresoraVO updateUsuarioApmImpresora(UserContext userContext, UsuarioApmImpresoraVO usuarioApmDMVO ) throws DemodaServiceException;
		public UsuarioApmImpresoraVO deleteUsuarioApmImpresora(UserContext userContext, UsuarioApmImpresoraVO usuarioApmDMVO ) throws DemodaServiceException;	
		// ----> APM UsuarioApmImpresora
		
		// ----> APM PerfilAccesoAplicacion 			
		public PerfilAccesoAplicacionAdapter getPerfilAccesoAplicacionAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public PerfilAccesoAplicacionAdapter getPerfilAccesoAplicacionAdapterForCreate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;
		public PerfilAccesoAplicacionAdapter getPerfilAccesoAplicacionAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException;		
		
		public PerfilAccesoAplicacionVO createPerfilAccesoAplicacion(UserContext userContext, PerfilAccesoAplicacionVO campoVO ) throws DemodaServiceException;
		public PerfilAccesoAplicacionVO updatePerfilAccesoAplicacion(UserContext userContext, PerfilAccesoAplicacionVO campoVO ) throws DemodaServiceException;
		public PerfilAccesoAplicacionVO deletePerfilAccesoAplicacion(UserContext userContext, PerfilAccesoAplicacionVO campoVO ) throws DemodaServiceException; 	
		// <---- APM PerfilAccesoAplicacion 
}