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

import java.util.Date;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.apm.buss.bean.ApmPanicoManager;
import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.apm.buss.bean.EstadoPanico;
import ar.gov.rosario.gait.apm.buss.bean.Panico;
import ar.gov.rosario.gait.apm.buss.bean.UsuarioApm;
import ar.gov.rosario.gait.apm.iface.model.PanicoVO;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.util.To;
import coop.tecso.demoda.util.VoJsoner;

public class AplicacionResource {

	private Logger log = Logger.getLogger(AplicacionResource.class);
	
	To to = new To();

	public Route[] routes() {
		return new Route[] {
				Route.create("POST", "<%= request.getContextPath()%>/api/panico.json", this.getClass(), "savePanico")
		};
	}
	
	/**
	 * 
	 * 
	 */
	public synchronized Reply<Boolean> savePanico(RestRequest<String> req) throws Exception {
		log.info("savePanico: enter");
		try {
			// JSON
			String json = to.String(req.parameters.get("data"));
			
			VoJsoner voJsoner = new VoJsoner();
			PanicoVO panicoVO = voJsoner.fromJson(json, PanicoVO.class);
			
			Panico panico = new Panico();
			copy(panicoVO, panico);
			
			ApmPanicoManager.getInstance().createPanico(panico);
			return new Reply<Boolean>(Boolean.TRUE);
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	/**
	 * Copia los datos del formulario y la lista de formularioValor
	 * 
	 * @param fVO
	 * @param f
	 */
	private void copy(PanicoVO panicoVO, Panico panico) {
		// DispositivoMovil
		DispositivoMovil dispositivoMovil = DispositivoMovil.getById(panicoVO.getDispositivoMovil().getId());
		panico.setDispositivoMovil(dispositivoMovil);
		// Area
		panico.setArea(dispositivoMovil.getArea());
		// Estado
		panico.setEstadoPanico(EstadoPanico.getById(EstadoPanico.ID_PENDIENTE));
		// Usuario
		panico.setUsuarioPanico(UsuarioApm.getById(panicoVO.getUsuarioPanico().getId()));
		//
		panico.setFechaPosicion(panicoVO.getFechaPosicion());
		panico.setLatitud(panicoVO.getLatitud());
		panico.setLongitud(panicoVO.getLongitud());
		panico.setPrecision(panicoVO.getPrecision());
		panico.setOrigen(panicoVO.getOrigen());
		panico.setFechaPanico(panicoVO.getFechaPanico());
		panico.setFechaRecepcion(new Date());
	}
}