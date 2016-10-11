package ar.gov.rosario.gait.not.buss.dao;




/**
 * Factory de Definicion DAOs
 * 
 * @author tecso
 * 
 */
public class NotDAOFactory {

	private static final NotDAOFactory INSTANCE = new NotDAOFactory();

	/**
	 * 
	 */
	private NotificacionDAO	notificacionDAO;
	private TipoNotificacionDAO tipoNotificacionDAO;
	private EstadoNotificacionDAO estadoNotificacionDAO;

	/**
	 * 
	 */
	private NotDAOFactory() {
		super();  
		this.notificacionDAO = new NotificacionDAO();       
		this.tipoNotificacionDAO = new TipoNotificacionDAO();
		this.estadoNotificacionDAO = new EstadoNotificacionDAO();
	}

	/**
	 * 
	 * @return
	 */
	public static NotificacionDAO getNotificacionDAO() {
		return INSTANCE.notificacionDAO;
	}
	
	/**
	 * 
	 * @return
	 */
	public static TipoNotificacionDAO getTipoNotificacionDAO() {
		return INSTANCE.tipoNotificacionDAO;
	}

	/**
	 * 
	 * @return
	 */
	public static EstadoNotificacionDAO getEstadoNotificacionDAO() {
		return INSTANCE.estadoNotificacionDAO;
	}
}
