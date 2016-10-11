package ar.gov.rosario.gait.seg.buss.dao;



/**
 * Factory de Seguridad DAOs
 * 
 * 
 * @author tecso
 * 
 */
public class SegDAOFactory {

    private static final SegDAOFactory INSTANCE = new SegDAOFactory();
    
    private UsuarioGaitDAO	usuarioGaitDAO;
    
    private SegDAOFactory() {
        super();  
        
        this.usuarioGaitDAO	= new UsuarioGaitDAO();
    }

    public static UsuarioGaitDAO getUsuarioGaitDAO() {
        return INSTANCE.usuarioGaitDAO;
    }

}
