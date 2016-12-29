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
package ar.gov.rosario.gait.frm.buss.dao;

import ar.gov.rosario.gait.not.buss.dao.NumeracionDAO;
import ar.gov.rosario.gait.not.buss.dao.TalonarioDAO;



/**
 * Factory de Formulario DAOs
 * 
 * @author tecso.coop
 * 
 */
public class FrmDAOFactory {

    private static final FrmDAOFactory INSTANCE = new FrmDAOFactory();
    
	private FormularioDAO 					 formularioDAO;
	private FormularioDetalleDAO 			 formularioDetalleDAO;
    private TipoFormularioDAO 			     tipoFormularioDAO;
    private SerieDAO						 serieDAO;
    private MotivoCierreTipoFormularioDAO    motivoCierreTipoFormularioDAO;
    private MotivoAnulacionTipoFormularioDAO motivoAnulacionTipoFormularioDAO;
    private EstadoTipoFormularioDAO 	     estadoTipoFormularioDAO;
    private NumeracionDAO					 numeracionDAO;
    private TalonarioDAO					 talonarioDAO;
    private ReporteFormularioDAO			 reporteFormularioDAO;
    
    private FrmDAOFactory() {
        super();  
        this.formularioDAO 					  = new FormularioDAO();
        this.formularioDetalleDAO 			  = new FormularioDetalleDAO();
        this.tipoFormularioDAO                = new TipoFormularioDAO();
        this.serieDAO						  = new SerieDAO();
        this.motivoCierreTipoFormularioDAO    = new MotivoCierreTipoFormularioDAO();
        this.estadoTipoFormularioDAO          = new EstadoTipoFormularioDAO();
        this.motivoAnulacionTipoFormularioDAO = new MotivoAnulacionTipoFormularioDAO();
        this.numeracionDAO					  = new NumeracionDAO();
        this.talonarioDAO					  = new TalonarioDAO();
        this.reporteFormularioDAO			  = new ReporteFormularioDAO();
    }

    public static FormularioDAO getFormularioDAO() {
        return INSTANCE.formularioDAO;
    }
    
    public static FormularioDetalleDAO getFormularioDetalleDAO() {
        return INSTANCE.formularioDetalleDAO;
    }
    
    public static TipoFormularioDAO getTipoFormularioDAO() {
        return INSTANCE.tipoFormularioDAO;
    }
    
    public static SerieDAO getSerieDAO() {
        return INSTANCE.serieDAO;
    }
    
    public static EstadoTipoFormularioDAO getEstadoTipoFormularioDAO() {
        return INSTANCE.estadoTipoFormularioDAO;
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
	
	public static TalonarioDAO getTalonarioDAO() {
		return INSTANCE.talonarioDAO;
	}
	
    public static ReporteFormularioDAO getReporteFormularioDAO() {
        return INSTANCE.reporteFormularioDAO;
    }
}