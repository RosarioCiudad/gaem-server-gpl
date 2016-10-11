package ar.gov.rosario.gait.not.buss.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.not.buss.bean.EstadoNotificacion;
import ar.gov.rosario.gait.not.buss.bean.Notificacion;
import ar.gov.rosario.gait.not.iface.model.NotificacionSearchPage;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;

public class NotificacionDAO extends GenericDAO {

	private Logger log = Logger.getLogger((NotificacionDAO.class));	

	public NotificacionDAO() {
		super(Notificacion.class);
	}

	public List<Notificacion> getBySearchPage(NotificacionSearchPage notificacionSearchPage) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		String queryString = "from Notificacion t ";
		boolean flagAnd = false;

		if (log.isDebugEnabled()) { 
			log.debug("log de filtros del Notificacion SearchPage: " + notificacionSearchPage.infoString()); 
		}

		// Armamos filtros del HQL
		if (notificacionSearchPage.getModoSeleccionar()) {
			queryString += flagAnd ? " and " : " where ";
			queryString += " t.estado = "+ Estado.ACTIVO.getId();
			flagAnd = true;
		}

		// Filtros aqui

		//Descripcion Reducida
		if (!StringUtil.isNullOrEmpty(notificacionSearchPage.getNotificacion().getDescripcionReducida())) {
			queryString += flagAnd ? " and " : " where ";
			queryString += " UPPER(TRIM(t.descripcionReducida)) like '%" + 
					StringUtil.escaparUpper(notificacionSearchPage.getNotificacion().getDescripcionReducida()) + "%'";
			flagAnd = true;
		}

		//Descripcion Ampliada
		if (!StringUtil.isNullOrEmpty(notificacionSearchPage.getNotificacion().getDescripcionAmpliada())) {
			queryString += flagAnd ? " and " : " where ";
			queryString += " UPPER(TRIM(t.descripcionAmpliada)) like '%" + 
					StringUtil.escaparUpper(notificacionSearchPage.getNotificacion().getDescripcionAmpliada()) + "%'";
			flagAnd = true;
		}

	
		 //Aplicacion 
		if (!StringUtil.isNullOrEmpty(notificacionSearchPage.getNotificacion().getAplicacion().getDescripcion())) {
			queryString += flagAnd ? " and " : " where ";
			queryString += " t.aplicacion.id = " +notificacionSearchPage.getNotificacion().getAplicacion().getIdView();
			flagAnd = true;
		}
		
		 //Estado 
		if (!StringUtil.isNullOrEmpty(notificacionSearchPage.getNotificacion().getEstado().getValue())) {
			queryString += flagAnd ? " and " : " where ";
			queryString += " t.estadoNotificacion.id = " +notificacionSearchPage.getNotificacion().getEstado().getId();
			flagAnd = true;
		}
		
		 //Dispositivo
		if (!StringUtil.isNullOrEmpty(notificacionSearchPage.getNotificacion().getDispositivoMovil().getDescripcion())) {
			queryString += flagAnd ? " and " : " where ";
			queryString += " t.dispositivoMovil.id = " +notificacionSearchPage.getNotificacion().getDispositivoMovil().getIdView();
			flagAnd = true;
		}
		
		// Order By
		queryString += " order by t.id ";

		if (log.isDebugEnabled()) log.debug(funcName + ": Query: " + queryString);

		List<Notificacion> listNotificacion = (ArrayList<Notificacion>) executeCountedSearch(queryString, notificacionSearchPage);

		if (log.isDebugEnabled()) log.debug(funcName + ": exit");

		return listNotificacion;
	}


	@SuppressWarnings("unchecked")
	public List<Notificacion> getNotifications(Integer dID) {
		Session session = GaitHibernateUtil.currentSession();
		String queryStr = "FROM Notificacion t WHERE t.estadoNotificacion.id = :estado AND t.dispositivoMovil.id = :deviceID ";
    	log.debug(" query: " + queryStr);
    	
		Query query = session.createQuery(queryStr).setLong("estado", EstadoNotificacion.ID_ESTADO_PENDIENTE).setInteger("deviceID", dID);
		
		return query.list();
	}


}
