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
package ar.gov.rosario.gait.gps.buss.service;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.apm.buss.bean.UsuarioApm;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.gps.buss.bean.GpsUbicacionManager;
import ar.gov.rosario.gait.gps.buss.bean.HistorialUbicacion;
import ar.gov.rosario.gait.gps.iface.model.HistorialUbicacionVO;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;
import coop.tecso.demoda.util.VoJsoner;

public class GpsResource {

	private Logger log = Logger.getLogger(GpsResource.class);
	
	To to = new To();

	public Route[] routes() {
		return new Route[] {
				Route.create("POST", "/gait/api/historialUbicacion.json", this.getClass(), "saveHistorialUbicacion")
		};
	}
	
	/**
	 * 
	 * 
	 */
	public synchronized Reply<Boolean> saveHistorialUbicacion(RestRequest<String> req) throws Exception {
		log.info("saveHistorialUbicacion: enter");
		try {
			String data = to.String(req.parameters.get("data"));
			
			HistorialUbicacionVO historialUbicacionVO =
					new VoJsoner().fromJson(data, HistorialUbicacionVO.class);
			
			HistorialUbicacion historialUbicacion = new HistorialUbicacion();
			copy(historialUbicacionVO, historialUbicacion);
			
			GpsUbicacionManager.getInstance().saveHistorialUbicacion(historialUbicacion);
			
			return new Reply<Boolean>(Boolean.TRUE);
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	/**
	 * Copia los datos de value object a business object
	 * 
	 * @param fVO
	 * @param f
	 */
	private void copy(HistorialUbicacionVO historialUbicacionVO, HistorialUbicacion historialUbicacion) {
		
		// Usuario
		UsuarioApm usuarioApm = UsuarioApm.getById(historialUbicacionVO.getUsuarioApm().getId());
		historialUbicacion.setUsuarioApm(usuarioApm);
		// Movil que informa la posicion
		DispositivoMovil dispositivoMovil = 
				DispositivoMovil.getById(historialUbicacionVO.getDispositivoMovil().getId());
		historialUbicacion.setDispositivoMovil(dispositivoMovil);
		historialUbicacion.setArea(dispositivoMovil.getArea());
		// Datos de Posicion
		historialUbicacion.setFechaPosicion(historialUbicacionVO.getFechaPosicion());
		historialUbicacion.setFechaUbicacion(historialUbicacionVO.getFechaUbicacion());
		historialUbicacion.setLatitud(historialUbicacionVO.getLatitud());
		historialUbicacion.setLongitud(historialUbicacionVO.getLongitud());
		historialUbicacion.setPrecision(historialUbicacionVO.getPrecision());
		historialUbicacion.setOrigen(historialUbicacionVO.getOrigen());
		historialUbicacion.setVelocidad(historialUbicacionVO.getVelocidad());
		historialUbicacion.setRadio(historialUbicacionVO.getRadio());
		historialUbicacion.setAltitud(historialUbicacionVO.getAltitud());
		historialUbicacion.setNivelBateria(historialUbicacionVO.getNivelBateria());
		historialUbicacion.setNivelSenial(historialUbicacionVO.getNivelSenial());
		// Auditoria
		historialUbicacion.setUsuarioUltMdf(usuarioApm.getUsername());
	}
}