package ar.gov.rosario.gait.base.buss.dao;



/**
 * @author tecso
 * 
 */
public class BaseDAOFactory {

    private static final BaseDAOFactory INSTANCE = new BaseDAOFactory();

    //private TributoDAO tributoDAO;
    
    private BaseDAOFactory() {
        super();
        //this.tributoDAO = new TributoDAO();
    }

    //public static TributoDAO getTributoDAO() {
    //    return INSTANCE.tributoDAO;
    //}
}
