package ar.gov.rosario.gait.gps.buss.dao;

/**
 * Factory de GPS DAOs
 * 
 * @author tecso
 * 
 */
public class GpsDAOFactory {

    private static final GpsDAOFactory INSTANCE = new GpsDAOFactory();
    
    private HistorialUbicacionDAO historialUbicacionDAO;
    
    private GpsDAOFactory() {
        super();  
        this.historialUbicacionDAO = new HistorialUbicacionDAO();
    }

    public static HistorialUbicacionDAO getHistorialUbicacionDAO() {
        return INSTANCE.historialUbicacionDAO;
    }
}
