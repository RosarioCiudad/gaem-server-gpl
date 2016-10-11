package ar.gov.rosario.gait.base.iface.model;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.base.iface.util.GaitCache;
import ar.gov.rosario.gait.base.iface.util.GaitUtil;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.BussImageModel;
import coop.tecso.demoda.iface.model.Common;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.DemodaStringMsg;
import coop.tecso.demoda.iface.model.UserContext;

public class GaitBussImageModel extends BussImageModel  {
	
	static private Logger log = Logger.getLogger((GaitBussImageModel.class));

	/**
	 * Constructor
	 *
	 */
	public GaitBussImageModel() {    
		super();
	}

    /**
     * Constructor parametrizado
     * @param commonKey
     */
    public GaitBussImageModel(CommonKey commonKey) {
        super();
        super.setId(commonKey.getId());
    }
    
	/**
	 * Constructor parametrizado
	 * @param id
	 */
	public GaitBussImageModel(Long id) {
		super();
		super.setId(id);
	}

	/**
	 * Constructor parametrizado
	 * @param id
	 */
	public GaitBussImageModel(long id) {
		super();
		super.setId(Long.valueOf(id));
	}
	
	/**
	 * Constructor parametrizado
	 * @param id
	 */
	public GaitBussImageModel(String id) {
		super.setId(Long.valueOf(id));
	}

	/**
	 * Verifica si una bandera esta habilidata o no chequeando al mismo tiempo los 
	 * permisos segun la accion y metodo que dependen de esa bandera.
	 * Este metodo es un helper para verficar si una bandera esta habilitada o no segun
	 * la manera mas comunmete hecha en Demoda.
	 * Si necesita una verificacion mas compleja puede programar el checkeo uds. mismo en cada VO.
	 * Llamando a SweContext.hasAccess() de SWE.
	 * <p>Requiere:
	 * <br> - Que el UserContext este en el TLS. Ver: DemodaUtil.setCurrentUserContext()
	 * <br> - Que el UserContext tenga seteado los IdsAccionesModuloUsuario, obtenidos durante del login en SWE.
	*/
	static public String hasEnabledFlag(boolean flag, String accion, String metodo) {
		long hasAccess= Common.HASACCESS_SINACCESO;

		try {
			if (!flag) {
				if (log.isDebugEnabled()) log.debug("hasEnabledFlag(): " + accion + " " + metodo + " DISABLED by bussiness flag");
				return DISABLED;
			}

			UserContext uc = DemodaUtil.currentUserContext();
			hasAccess = GaitCache.getInstance().getSweContext().hasAccess(uc.getIdsAccionesModuloUsuario(), accion, metodo);

			if (hasAccess == Common.HASACCESS_OK || hasAccess == Common.HASACCESS_OK_NOEXISTE ) { 
				if (log.isDebugEnabled()) {
					log.debug("hasEnabledFlag(): " + accion + " " + metodo + (hasAccess == Common.HASACCESS_OK ? " OK_HASACCESS": " OK_NOEXISTE"));
				}
				return ENABLED;
			} else {  
				if (log.isDebugEnabled()) log.debug("hasEnabledFlag(): " + accion + " " + metodo + " DISABLED by swe");
				return DISABLED;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DISABLED;
		}	
	}

	/**
	 * Verifica si una bandera esta habilidata o no chequeando los 
	 * permisos segun la accion y metodo que dependen de esa bandera.
	 * Este metodo es un helper para verficar si una bandera esta habilitada o no segun
	 * la manera mas comunmete hecha en Demoda.
	 * Si necesita una verificacion mas compleja puede programar el checkeo uds. mismo en cada VO.
	 * Llamando a SweContext.hasAccess() de SWE.
	 * <p>Requiere:
	 * <br> - Que el UserContext este en el TLS. Ver: DemodaUtil.setCurrentUserContext()
	 * <br> - Que el UserContext tenga seteado los IdsAccionesModuloUsuario, obtenidos durante del login en SWE.
	*/
	static public String hasEnabledFlag(String accion, String metodo) {
		long hasAccess= Common.HASACCESS_SINACCESO;

		try {

			UserContext uc = DemodaUtil.currentUserContext();
			hasAccess = GaitCache.getInstance().getSweContext().hasAccess
				(uc.getIdsAccionesModuloUsuario(), accion, metodo);

			if (hasAccess == Common.HASACCESS_OK || hasAccess == Common.HASACCESS_OK_NOEXISTE ) { 
				if (log.isDebugEnabled()) {
					log.debug("hasEnabledFlag(): " + accion + " " + metodo + (hasAccess == Common.HASACCESS_OK ? " OK_HASACCESS": " OK_NOEXISTE"));
				}
				return ENABLED;
			} else {  
				if (log.isDebugEnabled()) log.debug("hasEnabledFlag(): " + accion + " " + metodo + " DISABLED by swe");
				return DISABLED;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DISABLED;
		}	
	}
	
	// View flags NO! chequeamos acceso por SWE
	public String getModificarEnabled() {
		return this.getModificarBussEnabled() ? ENABLED : DISABLED;
	}
	
	public String getEliminarEnabled() {

		return this.getEliminarBussEnabled() ? ENABLED : DISABLED;
	}

	public String getAltaEnabled() {
		return this.getAltaBussEnabled() ? ENABLED : DISABLED;
	}

	public String getBajaEnabled() {
		return this.getBajaBussEnabled() ? ENABLED : DISABLED;
	}
	
	public String getVerEnabled() {
		return this.getVerBussEnabled() ? ENABLED : DISABLED;
	}
	
	public String getActivarEnabled() {
		return this.getActivarBussEnabled() ? ENABLED : DISABLED;
	}
	
	public String getDesactivarEnabled() {
		return this.getDesactivarBussEnabled() ? ENABLED : DISABLED;
	}
	
	/**
	 * Devuelve una lista de String con los contenidos de los errores
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getListErrorValues(){
		
		ArrayList<String> listErrorValues = new ArrayList<String>();
		
		for (DemodaStringMsg dsm:this.getListError()){			
			if (dsm.key() !=null ){
				if( dsm.key().startsWith("&") ){
					listErrorValues.add( dsm.key().substring(1) );
				} else {
					listErrorValues.add(GaitUtil.getValueFromBundle(dsm.key()));
				}
			}
		}
		
		return listErrorValues;
	}
}
