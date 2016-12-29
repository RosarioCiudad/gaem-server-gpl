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
package ar.gov.rosario.gait.apm.buss.service;

/**
 * Implementacion de servicios del submodulo Panico del modulo Apm
 * @author tecso
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.gov.rosario.gait.apm.buss.bean.ApmPanicoManager;
import ar.gov.rosario.gait.apm.buss.bean.EstadoPanico;
import ar.gov.rosario.gait.apm.buss.bean.Panico;
import ar.gov.rosario.gait.apm.buss.bean.UsuarioApm;
import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.model.EstadoPanicoVO;
import ar.gov.rosario.gait.apm.iface.model.HisEstPanVO;
import ar.gov.rosario.gait.apm.iface.model.PanicoAdapter;
import ar.gov.rosario.gait.apm.iface.model.PanicoSearchPage;
import ar.gov.rosario.gait.apm.iface.model.PanicoVO;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmImpresoraVO;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmVO;
import ar.gov.rosario.gait.apm.iface.service.IApmPanicoService;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.def.buss.bean.Area;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.gps.buss.bean.HistorialUbicacion;
import ar.gov.rosario.gait.gps.buss.dao.GpsDAOFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.UserContext;

public class ApmPanicoServiceHbmImpl implements IApmPanicoService {
	private Logger log = Logger.getLogger(ApmPanicoServiceHbmImpl.class);

	// ---> ABM Panico
	public PanicoSearchPage getPanicoSearchPageInit(UserContext userContext)
			throws DemodaServiceException {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		PanicoSearchPage panicoSearchPage = new PanicoSearchPage();

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			panicoSearchPage.setListEstadoPanico(ListUtilBean.toVO(EstadoPanico.getListActivos(),
					0,false,new EstadoPanicoVO(-1,StringUtil.SELECT_OPCION_TODOS)));
			
			panicoSearchPage.getPanico().getEstadoPanico().setId(EstadoPanico.ID_PENDIENTE);

			// Cargo combo Area
			if (userContext.getEsDGI()) {
				panicoSearchPage.setListArea(ListUtilBean.toVO(Area.getListActivos(),0,false,new AreaVO(-1,StringUtil.SELECT_OPCION_TODAS)));
			} else {
				AreaVO currentArea = new AreaVO(userContext.getIdArea().intValue(), userContext.getDesArea());
				panicoSearchPage.getDispositivoMovil().setArea(currentArea);
			}

			// Cargo combo Inspector
			if (userContext.getEsDGI()) {
				panicoSearchPage.setListUsuarioPanico((ListUtilBean.toVO(UsuarioApm.getListActivos(),0,false,new UsuarioApmVO(-1,StringUtil.SELECT_OPCION_TODAS))));
			} else {
				//					UsuarioApmVO currentUsuarioPanico = new UsuarioApmVO(userContext.getIdArea().intValue(), userContext.getDesArea());
				//					panicoSearchPage.setUsuarioPanico(currentUsuarioPanico); //TODO:
				panicoSearchPage.setListUsuarioPanico((ListUtilBean.toVO(UsuarioApm.getListActivos(),0,false,new UsuarioApmVO(-1,StringUtil.SELECT_OPCION_TODAS))));
			}

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}

		return panicoSearchPage;
	}


	public PanicoSearchPage getPanicoSearchPageResult(UserContext userContext, PanicoSearchPage panicoSearchPage) 
			throws DemodaServiceException {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			panicoSearchPage.clearError();
			
			
			//--
			boolean validaDesde = panicoSearchPage.getPanico().getFechaPanicoDesde() != null;
			boolean validaHasta = panicoSearchPage.getPanico().getFechaPanicoHasta() != null;
			Date fechaHoy = new Date();
			
			if(validaDesde && DateUtil.isDateAfter(panicoSearchPage.getPanico().getFechaPanicoDesde(), fechaHoy)){
				panicoSearchPage.addRecoverableError(BaseError.MSG_VALORMAYORQUE, BaseError.FECHA_DESDE, "Hoy");								
			}
			
			if(validaHasta && DateUtil.isDateAfter(panicoSearchPage.getPanico().getFechaPanicoHasta(), fechaHoy)){
				panicoSearchPage.addRecoverableError(BaseError.MSG_VALORMAYORQUE, BaseError.FECHA_HASTA, "Hoy");								
			}
			
			if(validaDesde && validaHasta && DateUtil.isDateAfter(panicoSearchPage.getPanico().getFechaPanicoDesde(), panicoSearchPage.getPanico().getFechaPanicoHasta())){
				panicoSearchPage.addRecoverableError(BaseError.MSG_VALORMAYORQUE, BaseError.FECHA_DESDE, BaseError.FECHA_HASTA);
			}
			
			if(panicoSearchPage.hasError()){
				return panicoSearchPage;
			}
			
			//---

			// Aqui obtiene lista de BOs
			List<Panico> listPanico = ApmDAOFactory.getPanicoDAO().getBySearchPage(panicoSearchPage);

			// Aqui se podria iterar la lista de BO para setear banderas en VOs
			// y otras cosas del negocio.

			// Aqui pasamos BO a VO
			panicoSearchPage.setListResult(ListUtilBean.toVO(listPanico, 1, false));

			if (log.isDebugEnabled())
				log.debug(funcName + ": exit");
			return panicoSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	@Override
	public PanicoAdapter getPanicoAdapterForView(UserContext userContext,
			CommonKey commonKey, PanicoSearchPage panicoSearchPageVO)
					throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);

			GaitHibernateUtil.currentSession();

			Panico panico = Panico.getById(commonKey.getId());

			PanicoAdapter panicoAdapter = new PanicoAdapter();
			panicoAdapter.setPanico((PanicoVO) panico.toVO(2,true));

			// Posiciones Inspectores
			List<HistorialUbicacion> listUbicacion = new ArrayList<HistorialUbicacion>();

			// Panicos pendientes
			List<Panico> listPendiente = new ArrayList<Panico>();

			// Panicos anulados
			List<Panico> listAnulado = new ArrayList<Panico>();

			// Panicos atendidos
			List<Panico> listAtendido = new ArrayList<Panico>();
			
			if (log.isDebugEnabled()) log.debug(" getId() :" + panicoSearchPageVO.getPanico().getEstadoPanico().getId());
			
			
			if(panicoSearchPageVO.getPanico().getEstadoPanico().getId() == -1){
				if (log.isDebugEnabled()) log.debug("opcion TODOS");
				
				// Posiciones Inspectores
				listUbicacion = GpsDAOFactory.getHistorialUbicacionDAO().getLastByArea(panico.getArea());
				// Panicos pendientes
				listPendiente = ApmDAOFactory.getPanicoDAO().getListPendienteByArea(panico.getArea());
				// Panicos anulados
				listAnulado = ApmDAOFactory.getPanicoDAO().getListAnuladosByArea(panico.getArea());
				// Panicos atendidos
				listAtendido = ApmDAOFactory.getPanicoDAO().getListAtendidoByArea(panico.getArea());
			}else if (panicoSearchPageVO.getPanico().getEstadoPanico().getId().equals(EstadoPanico.ID_PENDIENTE)){
				if (log.isDebugEnabled()) log.debug("opcion PENDIENTE");
				
				// Posiciones Inspectores
				listUbicacion = GpsDAOFactory.getHistorialUbicacionDAO().getLastByArea(panico.getArea());
				// Panicos pendientes
				listPendiente = ApmDAOFactory.getPanicoDAO().getListPendienteByArea(panico.getArea());
			}else if(panicoSearchPageVO.getPanico().getEstadoPanico().getId().equals(EstadoPanico.ID_ANULADO)){
				if (log.isDebugEnabled()) log.debug("opcion ANULADO");
				
				// Panicos anulados
				listAnulado = ApmDAOFactory.getPanicoDAO().getListAnuladosByArea(panico.getArea());
			}else if(panicoSearchPageVO.getPanico().getEstadoPanico().getId().equals(EstadoPanico.ID_ATENDIDO)){
				if (log.isDebugEnabled()) log.debug("opcion ATENDIDO");

				// Panicos atendidos
				listAtendido = ApmDAOFactory.getPanicoDAO().getListAtendidoByArea(panico.getArea());
			}

			JsonObject featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			JsonArray featureList = new JsonArray();
			for (HistorialUbicacion pos : listUbicacion) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				point.addProperty("label", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#0000FF");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#0000FF");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioApm().getUsername());
				properties.addProperty("groupLabel", "Posición Actual");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListPosicion(featureCollection.toString());


			//
			featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");

			featureList = new JsonArray();
			for (Panico pos : listPendiente) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#ff0000");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#ff0000");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioPanico().getUsername());
				properties.addProperty("groupLabel", "En Curso");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListPendiente(featureCollection.toString());


			featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			featureList = new JsonArray();
			for (Panico pos : listAnulado) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#000000");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#000000");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioPanico().getUsername());
				properties.addProperty("groupLabel", "Anulados");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListAnulado(featureCollection.toString());

			//
			featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			featureList = new JsonArray();
			for (Panico pos : listAtendido) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#00ff99");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#00ff99");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioPanico().getUsername());
				properties.addProperty("groupLabel", "Atendidos");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListAtendido(featureCollection.toString());				

			return panicoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}

	}

	public PanicoAdapter getPanicoAdapterForView(UserContext userContext,CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			
			GaitHibernateUtil.currentSession();

			Panico panico = Panico.getById(commonKey.getId());

			PanicoAdapter panicoAdapter = new PanicoAdapter();
			panicoAdapter.setPanico((PanicoVO) panico.toVO(2,true));

			// Posiciones Inspectores
			List<HistorialUbicacion> listUbicacion = GpsDAOFactory.getHistorialUbicacionDAO().getLastByArea(panico.getArea());
			//
			JsonObject featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			JsonArray featureList = new JsonArray();
			for (HistorialUbicacion pos : listUbicacion) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				point.addProperty("label", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#0000FF");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#0000FF");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioApm().getUsername());
				properties.addProperty("groupLabel", "Posición Actual");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListPosicion(featureCollection.toString());

			// Panicos pendientes
			List<Panico> listPendiente = ApmDAOFactory.getPanicoDAO().getListPendienteByArea(panico.getArea());
			//
			featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			
			featureList = new JsonArray();
			for (Panico pos : listPendiente) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#ff0000");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#ff0000");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioPanico().getUsername());
				properties.addProperty("groupLabel", "En Curso");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListPendiente(featureCollection.toString());

			// Panicos anulados
			List<Panico> listAnulado = 
					ApmDAOFactory.getPanicoDAO().getListAnuladosByArea(panico.getArea());
			//
			featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			featureList = new JsonArray();
			for (Panico pos : listAnulado) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#000000");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#000000");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioPanico().getUsername());
				properties.addProperty("groupLabel", "Anulados");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListAnulado(featureCollection.toString());

			// Panicos atendidos
			List<Panico> listAtendido = 
					ApmDAOFactory.getPanicoDAO().getListAtendidoByArea(panico.getArea());
			//
			featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			featureList = new JsonArray();
			for (Panico pos : listAtendido) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#00ff99");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#00ff99");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioPanico().getUsername());
				properties.addProperty("groupLabel", "Atendidos");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListAtendido(featureCollection.toString());

			log.debug(funcName + ": exit");
			return panicoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	@Override
	public PanicoAdapter getPanicoAdapterForUpdate(UserContext userContext,
			CommonKey commonKey, PanicoSearchPage panicoSearchPageVO)
			throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Panico panico = Panico.getById(commonKey.getId());

			PanicoAdapter panicoAdapter = new PanicoAdapter();
			panicoAdapter.setPanico((PanicoVO) panico.toVO(2,true));
			
			//Get estados activos para el estado actual del pánico
			panicoAdapter.setListEstadoPanico(ListUtilBean.toVO(EstadoPanico.getListTransicionesForEstado(panico.getEstadoPanico())));


			// Posiciones Inspectores
			List<HistorialUbicacion> listUbicacion = new ArrayList<HistorialUbicacion>();

			// Panicos pendientes
			List<Panico> listPendiente = new ArrayList<Panico>();

			// Panicos anulados
			List<Panico> listAnulado = new ArrayList<Panico>();

			// Panicos atendidos
			List<Panico> listAtendido = new ArrayList<Panico>();
			
			if (log.isDebugEnabled()) log.debug(" getId() :" + panicoSearchPageVO.getPanico().getEstadoPanico().getId());
			
			
			if(panicoSearchPageVO.getPanico().getEstadoPanico().getId() == -1){
				if (log.isDebugEnabled()) log.debug("opcion TODOS");
				
				// Posiciones Inspectores
				listUbicacion = GpsDAOFactory.getHistorialUbicacionDAO().getLastByArea(panico.getArea());
				// Panicos pendientes
				listPendiente = ApmDAOFactory.getPanicoDAO().getListPendienteByArea(panico.getArea());
				// Panicos anulados
				listAnulado = ApmDAOFactory.getPanicoDAO().getListAnuladosByArea(panico.getArea());
				// Panicos atendidos
				listAtendido = ApmDAOFactory.getPanicoDAO().getListAtendidoByArea(panico.getArea());
			}else if (panicoSearchPageVO.getPanico().getEstadoPanico().getId().equals(EstadoPanico.ID_PENDIENTE)){
				if (log.isDebugEnabled()) log.debug("opcion PENDIENTE");
				
				// Posiciones Inspectores
				listUbicacion = GpsDAOFactory.getHistorialUbicacionDAO().getLastByArea(panico.getArea());
				// Panicos pendientes
				listPendiente = ApmDAOFactory.getPanicoDAO().getListPendienteByArea(panico.getArea());
			}else if(panicoSearchPageVO.getPanico().getEstadoPanico().getId().equals(EstadoPanico.ID_ANULADO)){
				if (log.isDebugEnabled()) log.debug("opcion ANULADO");
				
				// Panicos anulados
				listAnulado = ApmDAOFactory.getPanicoDAO().getListAnuladosByArea(panico.getArea());
			}else if(panicoSearchPageVO.getPanico().getEstadoPanico().getId().equals(EstadoPanico.ID_ATENDIDO)){
				if (log.isDebugEnabled()) log.debug("opcion ATENDIDO");

				// Panicos atendidos
				listAtendido = ApmDAOFactory.getPanicoDAO().getListAtendidoByArea(panico.getArea());
			}

			JsonObject featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			JsonArray featureList = new JsonArray();
			for (HistorialUbicacion pos : listUbicacion) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				point.addProperty("label", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#0000FF");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#0000FF");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioApm().getUsername());
				properties.addProperty("groupLabel", "Posición Actual");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListPosicion(featureCollection.toString());


			//
			featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");

			featureList = new JsonArray();
			for (Panico pos : listPendiente) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#ff0000");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#ff0000");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioPanico().getUsername());
				properties.addProperty("groupLabel", "En Curso");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListPendiente(featureCollection.toString());


			featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			featureList = new JsonArray();
			for (Panico pos : listAnulado) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#000000");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#000000");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioPanico().getUsername());
				properties.addProperty("groupLabel", "Anulados");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListAnulado(featureCollection.toString());

			//
			featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			featureList = new JsonArray();
			for (Panico pos : listAtendido) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#00ff99");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#00ff99");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioPanico().getUsername());
				properties.addProperty("groupLabel", "Atendidos");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListAtendido(featureCollection.toString());				

			return panicoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}

	}

	public PanicoAdapter getPanicoAdapterForUpdate(UserContext userContext,CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			Panico panico = Panico.getById(commonKey.getId());

			PanicoAdapter panicoAdapter = new PanicoAdapter();
			panicoAdapter.setPanico((PanicoVO) panico.toVO(2,true));
			
			//Get estados activos para el estado actual del pánico
			panicoAdapter.setListEstadoPanico(ListUtilBean.toVO(EstadoPanico.getListTransicionesForEstado(panico.getEstadoPanico())));

			// Posiciones Actuales de los Inspectores
			List<HistorialUbicacion> listUbicacion = 
					GpsDAOFactory.getHistorialUbicacionDAO().getLastByArea(panico.getArea());
			//
			JsonObject featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			JsonArray featureList = new JsonArray();
			for (HistorialUbicacion pos : listUbicacion) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#0000FF");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#0000FF");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioApm().getUsername());
				properties.addProperty("groupLabel", "Posición Actual");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListPosicion(featureCollection.toString());

			// Panicos pendientes
			List<Panico> listPendiente = 
					ApmDAOFactory.getPanicoDAO().getListPendienteByArea(panico.getArea());
			//
			featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			featureList = new JsonArray();
			for (Panico pos : listPendiente) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#ff0000");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#ff0000");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioPanico().getUsername());
				properties.addProperty("groupLabel", "En Curso");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListPendiente(featureCollection.toString());

			// Panicos anulados
			List<Panico> listAnulado = 
					ApmDAOFactory.getPanicoDAO().getListAnuladosByArea(panico.getArea());
			//
			featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			featureList = new JsonArray();
			for (Panico pos : listAnulado) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#000000");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#000000");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioPanico().getUsername());
				properties.addProperty("groupLabel", "Anulados");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListAnulado(featureCollection.toString());

			// Panicos anulados
			List<Panico> listAtendido = 
					ApmDAOFactory.getPanicoDAO().getListAtendidoByArea(panico.getArea());
			//
			featureCollection = new JsonObject();
			featureCollection.addProperty("type", "FeatureCollection");
			featureList = new JsonArray();
			for (Panico pos : listAtendido) {
				// 
				JsonObject point = new JsonObject();
				point.addProperty("type", "Point");
				// 
				JsonArray coord = new JsonArray();
				coord.add(new JsonPrimitive(pos.getLongitud()));
				coord.add(new JsonPrimitive(pos.getLatitud()));
				point.add("coordinates", coord);
				// 
				JsonObject style = new JsonObject();
				style.addProperty("fillColor", "#00ff00");
				style.addProperty("opacity", 0.65);
				style.addProperty("strokeColor", "#00ff00");
				style.addProperty("weight", 5);
				//
				JsonObject properties = new JsonObject();
				properties.addProperty("label", pos.getUsuarioPanico().getUsername());
				properties.addProperty("groupLabel", "Atendidos");
				properties.addProperty("showLabel", true);
				properties.add("style", style);
				//
				JsonObject feature = new JsonObject();
				feature.add("geometry", point);
				feature.add("properties", properties);
				feature.addProperty("type", "Feature");
				featureList.add(feature);
			}
			featureCollection.add("features", featureList);
			panicoAdapter.setListAtendido(featureCollection.toString());

			log.debug(funcName + ": exit");
			return panicoAdapter;
		} catch (Exception e) {
			log.error("Service Error: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
	
	public PanicoAdapter cambiarEstadoPanico(UserContext userContext, PanicoAdapter panicoAdapter) 
			throws DemodaServiceException {
		
		Session session = null;
		Transaction tx = null;
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			
			Panico panico = Panico.getById(panicoAdapter.getPanico().getId());
			
			HisEstPanVO hisEstPan = panicoAdapter.getHisEstPan();
			
			ApmPanicoManager.getInstance().cambiarEstado(panico, 
					hisEstPan.getEstadoPanico().getId(), hisEstPan.getObservaciones(), hisEstPan.getFecha());

			
			if (panico.hasError()) {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.rollback");
				tx.rollback();
			} else {
				if (log.isDebugEnabled()) log.debug(funcName + ": tx.commit");
				tx.commit();
			}
			panico.passErrorMessages(panicoAdapter);
			
			
			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return panicoAdapter;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			
			GaitHibernateUtil.closeSession();
		}
	}


	@Override
	public PanicoAdapter getPanicoAdapterForCambioEstado(
			UserContext userContext, CommonKey commonKey) throws DemodaServiceException {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();
			
			Panico panico = Panico.getById(commonKey.getId());

			PanicoAdapter panicoAdapter = new PanicoAdapter();
			panicoAdapter.setPanico((PanicoVO) panico.toVO(2, false));	   
	        
			panicoAdapter.getPanico().setListHisEstPan(ListUtilBean.toVO(panico.getListHisEstPan(),1,false));
			
			panicoAdapter.setListEstadoPanico(ListUtilBean.toVO(
					EstadoPanico.getListTransicionesForEstado(panico.getEstadoPanico()),1,false, 
					new EstadoPanicoVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));
			
			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return panicoAdapter;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
	// <--- ABM Panico


}