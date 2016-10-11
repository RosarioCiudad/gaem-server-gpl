package ar.gov.rosario.gait.def.buss.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.TelefonoPanico;
import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.base.iface.model.GaitParam;
import ar.gov.rosario.gait.def.buss.dao.DefDAOFactory;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.db.HibernateQueryMaker;
import coop.tecso.demoda.db.QueryMaker;

/**
 * Manejador del submodulo de configuracion
 * 
 * @author tecso
 *
 */
public class DefConfiguracionManager {

	static final private Map<String, String> joins = new HashMap<String, String>(){
		private static final long serialVersionUID = 1L;
		{
			// JOINS por Usuario
			put("UsuarioApmDM"," t.usuarioApm.username = ? ");
			put("UsuarioApmImpresora"," t.usuarioApm.username = ? ");
			put("Impresora"," t IN (SELECT u.impresora FROM UsuarioApmImpresora u WHERE u.usuarioApm.username = ?) ");
			
			// JOINS por Aplicacion
			put("AplicacionBinarioVersion"," t.aplicacion.codigo ");
			put("AplicacionParametro"," t.aplicacion.codigo ");
			put("AplicacionPerfilSeccion"," t.aplicacionPerfil.aplicacion.codigo "); 
			put("AplPerfilSeccionCampo"," t.aplPerfilSeccion.aplicacionPerfil.aplicacion.codigo ");
			put("AplPerfilSeccionCampoValor"," t.aplPerfilSeccionCampo.aplPerfilSeccion.aplicacionPerfil.aplicacion.codigo ");
			put("AplPerfilSeccionCampoValorOpcion"," t.aplPerfilSeccionCampoValor.aplPerfilSeccionCampo.aplPerfilSeccion.aplicacionPerfil.aplicacion.codigo ");
		}
	};

	private static Logger log = Logger.getLogger(DefConfiguracionManager.class);

	private static final DefConfiguracionManager INSTANCE = new DefConfiguracionManager();

	/**
	 * Constructor privado
	 */
	private DefConfiguracionManager() {

	}

	/**
	 * Devuelve unica instancia
	 */
	public static DefConfiguracionManager getInstance() {
		return INSTANCE;
	}

	// ---> ABM Parametro
	public Parametro createParametro(Parametro parametro) throws Exception {

		// Validaciones de negocio
		if (!parametro.validateCreate()) {
			return parametro;
		}

		DefDAOFactory.getParametroDAO().update(parametro);

		return parametro;
	}

	public Parametro updateParametro(Parametro parametro) throws Exception {

		// Validaciones de negocio
		if (!parametro.validateUpdate()) {
			return parametro;
		}

		DefDAOFactory.getParametroDAO().update(parametro);

		return parametro;
	}

	public Parametro deleteParametro(Parametro parametro) throws Exception {

		// Validaciones de negocio
		if (!parametro.validateDelete()) {
			return parametro;
		}

		DefDAOFactory.getParametroDAO().delete(parametro);

		return parametro;
	}
	// <--- ABM Parametro

	// ---> ABM Direccion
	public Direccion createDireccion(Direccion direccion) throws Exception {

		// Validaciones de negocio
		if (!direccion.validateCreate()) {
			return direccion;
		}

		DefDAOFactory.getDireccionDAO().update(direccion);

		return direccion;
	}

	public Direccion updateDireccion(Direccion direccion) throws Exception {

		// Validaciones de negocio
		if (!direccion.validateUpdate()) {
			return direccion;
		}

		DefDAOFactory.getDireccionDAO().update(direccion);

		return direccion;
	}

	public Direccion deleteDireccion(Direccion direccion) throws Exception {

		// Validaciones de negocio
		if (!direccion.validateDelete()) {
			return direccion;
		}

		DefDAOFactory.getDireccionDAO().delete(direccion);

		return direccion;
	}
	// <--- ABM Direccion

	// ---> ABM Area
	public Area createArea(Area area) throws Exception {

		// Validaciones de negocio
		if (!area.validateCreate()) {
			return area;
		}

		DefDAOFactory.getAreaDAO().update(area);

		return area;
	}

	public Area updateArea(Area area) throws Exception {

		// Validaciones de negocio
		if (!area.validateUpdate()) {
			return area;
		}

		DefDAOFactory.getAreaDAO().update(area);

		return area;
	}

	public Area deleteArea(Area area) throws Exception {

		// Validaciones de negocio
		if (!area.validateDelete()) {
			return area;
		}

		DefDAOFactory.getAreaDAO().delete(area);

		return area;
	}
	// <--- ABM Area

	// ---> ABM Direccion Aplicacion Perfil
	public DireccionAplicacionPerfil createDireccionAplicacionPerfil(DireccionAplicacionPerfil direccionAplicacionPerfil) throws Exception {

		// Validaciones de negocio
		if (!direccionAplicacionPerfil.validateCreate()) {
			return direccionAplicacionPerfil;
		}

		DefDAOFactory.getDireccionAplicacionPerfilDAO().update(direccionAplicacionPerfil);

		return direccionAplicacionPerfil;
	}

	public DireccionAplicacionPerfil updateDireccionAplicacionPerfil(DireccionAplicacionPerfil direccionAplicacionPerfil) throws Exception {

		// Validaciones de negocio
		if (!direccionAplicacionPerfil.validateUpdate()) {
			return direccionAplicacionPerfil;
		}

		DefDAOFactory.getDireccionAplicacionPerfilDAO().update(direccionAplicacionPerfil);

		return direccionAplicacionPerfil;
	}

	public DireccionAplicacionPerfil deleteDireccionAplicacionPerfil(DireccionAplicacionPerfil direccionAplicacionPerfil) throws Exception {

		// Validaciones de negocio
		if (!direccionAplicacionPerfil.validateDelete()) {
			return direccionAplicacionPerfil;
		}

		DefDAOFactory.getDireccionAplicacionPerfilDAO().delete(direccionAplicacionPerfil);

		return direccionAplicacionPerfil;
	}
	// <--- ABM Direccion Aplciacion Perfil

	// ----> Actualiza la gait Param
	/**
	 * Actualiza el contenido del mapa de los parametros del singleton GaitParam en Iface.
	 * @throws Exception
	 */
	public void updateGaitParam() throws Exception {
		//-- recorrer todos los parametros


		log.debug("********	  UPDATE GAIT PARAM  ********");

		//-- armar Map<String, String>
		Map<String, String> mapa = new HashMap<String, String>();

		for (Parametro p : Parametro.getList()) {
			log.debug("CodParam: " + p.getCodParam() + " valor: " + p.getValor());
			mapa.put(p.getCodParam(), p.getValor());
		}
		//-- GaitParam.getInstance.updateValues(mapa);
		GaitParam.getInstance().updateValues(mapa);

		//--al servicio DefConfiguracionService hacerle updateGaitParam
		//--llamarlo desde BaseDispatch static
	}
	// <---- Actualiza la gait Param

	// -----> Entidades
	@SuppressWarnings("unchecked")
	public static List<VersionableBO> getEntities(String entidad, Integer version) throws Exception {
		log.debug(" getEntities : enter \n param entidad: " + entidad + "\n param version: " + version);
		return (List<VersionableBO>) GenericDAO.entityDelta(entidad, version);
	}
	// <----- Entidades

	public static VersionableBO getEntity(String entidad, Integer id) {
		log.debug(" getEntity : enter \n param entidad: " + entidad + "\n param id: " + id);
		return (VersionableBO) GenericDAO.getEntity(entidad, id);
	}

	@SuppressWarnings("unchecked")
	public static List<VersionableBO> getEntities(String entidad, Integer version, String codigoApp) throws Exception {
		log.debug(" getEntities : enter \n param entidad: " + entidad + "\n param version: " + version + "\n param codigoAppID: " + codigoApp);
		return (List<VersionableBO>) GenericDAO.entityDelta(entidad, version, codigoApp, joins.get(entidad));
	}
	
	@SuppressWarnings("unchecked")
	public static List<VersionableBO> getEntitiesByUsuario(String entidad, Integer version, String username) throws Exception {
		log.debug(" getEntitiesByUsuario : enter ");
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		// Entidad
		query.add(String.format("FROM %s t WHERE 1=1", entidad));
		// Usuario
		query.add(String.format(" AND %s ", joins.get(entidad)), username);
		// Version
		query.add("AND t.version > ? ORDER BY t.version ", version);
		log.debug(" query: " + query);
		return HibernateQueryMaker.query(query).list();
	}
	
	@SuppressWarnings("unchecked")
	public static List<VersionableBO> getEntitiesByArea(String entidad, Integer version, Long areaId) throws Exception {
		log.debug(" getEntitiesByUsuario : enter ");
		QueryMaker query = HibernateQueryMaker.make(GaitHibernateUtil.currentSession());
		// Entidad
		query.add(String.format("FROM %s t WHERE 1=1", entidad));
		// Usuario
		query.add(" AND t.area.id = ? ", areaId);
		// Version
		query.add("AND t.version > ? ORDER BY t.version ", version);
		log.debug(" query: " + query);
		return HibernateQueryMaker.query(query).list();
	}


	// ---> ABM AreaAplicacionPerfil
	public AreaAplicacionPerfil createAreaAplicacionPerfil(AreaAplicacionPerfil areaAplicacionPerfil) throws Exception {
		// Validaciones de negocio
		if (!areaAplicacionPerfil.validateCreate()) {
			return areaAplicacionPerfil;
		}

		DefDAOFactory.getAreaAplicacionPerfilDAO().update(areaAplicacionPerfil);
		return areaAplicacionPerfil;
	}

	public AreaAplicacionPerfil updateAreaAplicacionPerfil(AreaAplicacionPerfil areaAplicacionPerfil) throws Exception {
		// Validaciones de negocio
		if (!areaAplicacionPerfil.validateUpdate()) {
			return areaAplicacionPerfil;
		}

		DefDAOFactory.getAreaAplicacionPerfilDAO().update(areaAplicacionPerfil);
		return areaAplicacionPerfil;
	}

	public AreaAplicacionPerfil deleteAreaAplicacionPerfil(AreaAplicacionPerfil areaAplicacionPerfil) throws Exception {
		// Validaciones de negocio
		if (!areaAplicacionPerfil.validateDelete()) {
			return areaAplicacionPerfil;
		}

		DefDAOFactory.getAreaAplicacionPerfilDAO().delete(areaAplicacionPerfil);
		return areaAplicacionPerfil;
	}
	// <--- ABM AreaAplicacionPerfil
	
	// ---> ABM TelefonoPanico
	public TelefonoPanico createTelefonoPanico(TelefonoPanico telefonoPanico) throws Exception {
		// Validaciones de negocio
		if (!telefonoPanico.validateCreate()) {
			return telefonoPanico;
		}

		ApmDAOFactory.getTelefonoPanicoDAO().update(telefonoPanico);
		return telefonoPanico;
	}

	public TelefonoPanico updateTelefonoPanico(TelefonoPanico telefonoPanico) throws Exception {
		// Validaciones de negocio
		if (!telefonoPanico.validateUpdate()) {
			return telefonoPanico;
		}

		ApmDAOFactory.getTelefonoPanicoDAO().update(telefonoPanico);
		return telefonoPanico;
	}

	public TelefonoPanico deleteTelefonoPanico(TelefonoPanico telefonoPanico) throws Exception {
		// Validaciones de negocio
		if (!telefonoPanico.validateDelete()) {
			return telefonoPanico;
		}

		ApmDAOFactory.getTelefonoPanicoDAO().delete(telefonoPanico);
		return telefonoPanico;
	}
	// <--- ABM TelefonoPanico
}