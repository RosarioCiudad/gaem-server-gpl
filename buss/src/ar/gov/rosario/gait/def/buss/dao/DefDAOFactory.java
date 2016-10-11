package ar.gov.rosario.gait.def.buss.dao;

public class DefDAOFactory {

    private static final DefDAOFactory INSTANCE = new DefDAOFactory();
    
    private ParametroDAO			parametroDAO;
    private DireccionDAO			direccionDAO;
    private AreaDAO					areaDAO;
    private DireccionAplicacionPerfilDAO direccionAplicacionPerfilDAO;
    private AreaAplicacionPerfilDAO					areaAplicacionPerfilDAO;
   
    private DefDAOFactory() {
        super();  
        this.parametroDAO				= new ParametroDAO();
        this.direccionDAO				= new DireccionDAO();
        this.areaDAO					= new AreaDAO();
        this.direccionAplicacionPerfilDAO = new DireccionAplicacionPerfilDAO();
        this.areaAplicacionPerfilDAO					= new AreaAplicacionPerfilDAO();
    }

	public static ParametroDAO getParametroDAO() {
		return INSTANCE.parametroDAO;
	}
	
	public static DireccionDAO getDireccionDAO() {
		return INSTANCE.direccionDAO;
	}

	public static AreaDAO getAreaDAO() {
		return INSTANCE.areaDAO;
	}
	public static DireccionAplicacionPerfilDAO getDireccionAplicacionPerfilDAO() {
		return INSTANCE.direccionAplicacionPerfilDAO;
	}
	public static AreaAplicacionPerfilDAO getAreaAplicacionPerfilDAO() {
		return INSTANCE.areaAplicacionPerfilDAO;
	}
}
