/*******************************************************************************
 * Copyright (c) 2016 Municipalidad de Rosario, Coop. de Trabajo Tecso Ltda.
 *
 * This file is part of GAEM.
 *
 * GAEM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * GAEM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAEM.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package ar.gov.rosario.gait.apm.buss.dao;

import ar.gov.rosario.gait.frm.buss.dao.MotivoAnulacionTipoFormularioDAO;
import ar.gov.rosario.gait.frm.buss.dao.MotivoCierreTipoFormularioDAO;
import ar.gov.rosario.gait.frm.buss.dao.NumeracionDAO;
import ar.gov.rosario.gait.frm.buss.dao.SerieDAO;
import ar.gov.rosario.gait.frm.buss.dao.TipoFormularioDAO;





public class ApmDAOFactory {

	private static final ApmDAOFactory INSTANCE = new ApmDAOFactory();

    private UsuarioApmDAO 						usuarioApmDAO;
    private UsuarioApmDMDAO 					usuarioApmDMDAO;
    private UsuarioApmImpresoraDAO				usuarioApmImpresoraDAO;
    private AplicacionDAO	 					aplicacionDAO;
    private SeccionDAO	   	    				seccionDAO;
    private TablaVersionDAO						tablaVersionDAO;
    private DispositivoMovilDAO					dispositivoMovilDAO;
    private CampoDAO							campoDAO;
    private AplicacionPerfilDAO	 				aplicacionPerfilDAO;
    private AplicacionPerfilSeccionDAO	 		aplicacionPerfilSeccionDAO;
    private AplPerfilSeccionCampoDAO 			aplPerfilSeccionCampoDAO;
    private AplPerfilSeccionCampoValorDAO 		aplPerfilSeccionCampoValorDAO;
    private AplPerfilSeccionCampoValorOpcionDAO aplPerfilSeccionCampoValorOpcionDAO;
    private CampoValorDAO						campoValorDAO;
    private CampoValorOpcionDAO					campoValorOpcionDAO;
    private AplicacionPerfilParametroDAO	 	aplicacionPerfilParametroDAO;
    private PerfilAccesoDAO 					perfilAccesoDAO;
    private ImpresoraDAO 						impresoraDAO;
    private AplicacionTablaDAO 					aplicacionTablaDAO;
    private AplicacionParametroDAO 				aplicacionParametroDAO;
    private AplicacionBinarioVersionDAO 		aplicacionBinarioVersionDAO;
    private AplicacionTipoBinarioDAO 			aplicacionTipoBinarioDAO;
    private PerfilAccesoUsuarioDAO 				perfilAccesoUsuarioDAO;
    private TipoFormularioDAO	   	    		tipoFormularioDAO;
    private SerieDAO	   	    				serieDAO;
    private MotivoCierreTipoFormularioDAO	   	motivoCierreTipoFormularioDAO;
    private MotivoAnulacionTipoFormularioDAO	motivoAnulacionTipoFormularioDAO;
    private NumeracionDAO						numeracionDAO;
    private TelefonoPanicoDAO					telefonoPanicoDAO;
    private PanicoDAO							panicoDAO;
    private EstadoPanicoDAO						estadoPanicoDAO;
    private HisEstPanDAO						hisEstPanDAO;
    private PerfilAccesoAplicacionDAO			perfilAccesoAplicacionDAO;
    
    private ApmDAOFactory() {
        super();
        this.aplicacionDAO	                        = new AplicacionDAO();
        this.usuarioApmDAO                          = new UsuarioApmDAO();
        this.usuarioApmDMDAO                        = new UsuarioApmDMDAO();
        this.seccionDAO   	                        = new SeccionDAO();
        this.tablaVersionDAO                        = new TablaVersionDAO();
        this.dispositivoMovilDAO                    = new DispositivoMovilDAO();
        this.campoDAO 			                    = new CampoDAO();
        this.campoValorDAO      				    = new CampoValorDAO();
        this.campoValorOpcionDAO      			    = new CampoValorOpcionDAO();
        this.aplicacionDAO							= new AplicacionDAO();
        this.usuarioApmDAO							= new UsuarioApmDAO();
        this.usuarioApmDMDAO						= new UsuarioApmDMDAO();
        this.seccionDAO								= new SeccionDAO();
        this.tablaVersionDAO						= new TablaVersionDAO();
        this.dispositivoMovilDAO 					= new DispositivoMovilDAO();
        this.campoDAO 			 					= new CampoDAO();
        this.aplicacionPerfilDAO 					= new AplicacionPerfilDAO();
        this.aplicacionPerfilSeccionDAO				= new AplicacionPerfilSeccionDAO();
        this.aplicacionPerfilParametroDAO			= new AplicacionPerfilParametroDAO();
        this.aplPerfilSeccionCampoDAO 				= new AplPerfilSeccionCampoDAO();
        this.aplPerfilSeccionCampoValorDAO 			= new AplPerfilSeccionCampoValorDAO();
        this.aplPerfilSeccionCampoValorOpcionDAO	= new AplPerfilSeccionCampoValorOpcionDAO();
        this.perfilAccesoDAO                        = new PerfilAccesoDAO();
        this.impresoraDAO                       	= new ImpresoraDAO();
        this.aplicacionTablaDAO	                    = new AplicacionTablaDAO();
        this.aplicacionParametroDAO	                = new AplicacionParametroDAO();
        this.aplicacionBinarioVersionDAO	        = new AplicacionBinarioVersionDAO();
        this.aplicacionTipoBinarioDAO	      		= new AplicacionTipoBinarioDAO();
        this.perfilAccesoUsuarioDAO					= new PerfilAccesoUsuarioDAO();
        this.tipoFormularioDAO   	                = new TipoFormularioDAO();
        this.serieDAO								= new SerieDAO();
        this.motivoCierreTipoFormularioDAO   		= new MotivoCierreTipoFormularioDAO();
        this.motivoAnulacionTipoFormularioDAO		= new MotivoAnulacionTipoFormularioDAO();
        this.numeracionDAO							= new NumeracionDAO();
        this.usuarioApmImpresoraDAO					= new UsuarioApmImpresoraDAO();
        this.telefonoPanicoDAO						= new TelefonoPanicoDAO();
        this.panicoDAO								= new PanicoDAO();
        this.estadoPanicoDAO						= new EstadoPanicoDAO();
        this.hisEstPanDAO							= new HisEstPanDAO();
        this.perfilAccesoAplicacionDAO				= new PerfilAccesoAplicacionDAO();
    }	
   
    public static AplicacionDAO getAplicacionDAO() {
        return INSTANCE.aplicacionDAO;
    }
    public static SeccionDAO getSeccionDAO() {
        return INSTANCE.seccionDAO;
    }
    public static TablaVersionDAO getTablaVersionDAO() {
        return INSTANCE.tablaVersionDAO;
    }
    public static DispositivoMovilDAO getDispositivoMovilDAO() {
        return INSTANCE.dispositivoMovilDAO;
    }
    public static UsuarioApmDAO getUsuarioApmDAO() {
        return INSTANCE.usuarioApmDAO;
    }
    public static UsuarioApmDMDAO getUsuarioApmDMDAO() {
        return INSTANCE.usuarioApmDMDAO;
    }
    public static UsuarioApmImpresoraDAO getUsuarioApmImpresoraDAO() {
        return INSTANCE.usuarioApmImpresoraDAO;
    }
    public static CampoDAO getCampoDAO() {
        return INSTANCE.campoDAO;
    }
    public static CampoValorDAO getCampoValorDAO() {
        return INSTANCE.campoValorDAO;
    }
    public static CampoValorOpcionDAO getCampoValorOpcionDAO() {
        return INSTANCE.campoValorOpcionDAO;
    }
    public static AplicacionPerfilDAO getAplicacionPerfilDAO() {
        return INSTANCE.aplicacionPerfilDAO;
    }
    public static AplPerfilSeccionCampoDAO getAplPerfilSeccionCampoDAO() {
        return INSTANCE.aplPerfilSeccionCampoDAO;
    }
    public static AplPerfilSeccionCampoValorDAO getAplPerfilSeccionCampoValorDAO() {
        return INSTANCE.aplPerfilSeccionCampoValorDAO;
    }
    public static AplPerfilSeccionCampoValorOpcionDAO getAplPerfilSeccionCampoValorOpcionDAO() {
        return INSTANCE.aplPerfilSeccionCampoValorOpcionDAO;
    }
    public static AplicacionPerfilSeccionDAO getAplicacionPerfilSeccionDAO() {
        return INSTANCE.aplicacionPerfilSeccionDAO;
    }
    public static AplicacionPerfilParametroDAO getAplicacionPerfilParametroDAO() {
        return INSTANCE.aplicacionPerfilParametroDAO;
    }
    public static PerfilAccesoDAO getPerfilAccesoDAO() {
        return INSTANCE.perfilAccesoDAO;
    }
    public static ImpresoraDAO getImpresoraDAO() {
        return INSTANCE.impresoraDAO;
    }
    public static AplicacionTablaDAO getAplicacionTablaDAO() {
        return INSTANCE.aplicacionTablaDAO;
    }
    public static AplicacionParametroDAO getAplicacionParametroDAO() {
        return INSTANCE.aplicacionParametroDAO;
    }
    public static AplicacionBinarioVersionDAO getAplicacionBinarioVersionDAO() {
        return INSTANCE.aplicacionBinarioVersionDAO;
    }
    public static AplicacionTipoBinarioDAO getAplicacionTipoBinarioDAO() {
        return INSTANCE.aplicacionTipoBinarioDAO;
    }
    public static PerfilAccesoUsuarioDAO getPerfilAccesoUsuarioDAO() {
        return INSTANCE.perfilAccesoUsuarioDAO;
    }
    public static TipoFormularioDAO getTipoFormularioDAO() {
        return INSTANCE.tipoFormularioDAO;
    }
    public static SerieDAO getSerieDAO() {
        return INSTANCE.serieDAO;
    }
    public static MotivoCierreTipoFormularioDAO getMotivoCierreTipoFormularioDAO() {
        return INSTANCE.motivoCierreTipoFormularioDAO;
    }
    public static MotivoAnulacionTipoFormularioDAO getMotivoAnulacionTipoFormularioDAO() {
        return INSTANCE.motivoAnulacionTipoFormularioDAO;
    }
    public static NumeracionDAO getNumeracionDAO() {
        return INSTANCE.numeracionDAO;
    }
    public static TelefonoPanicoDAO getTelefonoPanicoDAO() {
        return INSTANCE.telefonoPanicoDAO;
    }
    public static PanicoDAO getPanicoDAO() {
        return INSTANCE.panicoDAO;
    }
    public static EstadoPanicoDAO getEstadoPanicoDAO() {
        return INSTANCE.estadoPanicoDAO;
    }
    public static HisEstPanDAO getHisEstPanDAO(){
        return INSTANCE.hisEstPanDAO;
    }
    public static PerfilAccesoAplicacionDAO getPerfilAccesoAplicacionDAO(){
        return INSTANCE.perfilAccesoAplicacionDAO;
    }
}