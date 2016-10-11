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