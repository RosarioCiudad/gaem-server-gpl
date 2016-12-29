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
package ar.gov.rosario.gait.base.buss;

import ar.gov.rosario.gait.aid.buss.service.ApsvResource;
import ar.gov.rosario.gait.aid.buss.service.ReparticionResource;
import ar.gov.rosario.gait.apm.buss.service.AplicacionResource;
import ar.gov.rosario.gait.apm.buss.service.DispositivoMovilResource;
import ar.gov.rosario.gait.apm.buss.service.TablaVersionResource;
import ar.gov.rosario.gait.def.buss.service.EntityDeltaResource;
import ar.gov.rosario.gait.def.buss.service.ParametroResource;
import ar.gov.rosario.gait.frm.buss.service.FormularioResource;
import ar.gov.rosario.gait.frm.buss.service.TalonarioResource;
import ar.gov.rosario.gait.gps.buss.service.GpsResource;
import ar.gov.rosario.gait.not.buss.service.NotificacionResource;
import ar.gov.rosario.gait.seg.buss.service.LoginResource;
import coop.tecso.demoda.http.Route;

public class Routes {

	public static void init() {
		Route.bind(new LoginResource().routes()); // Login
		Route.bind(new ParametroResource().routes()); // Parametros
		Route.bind(new TablaVersionResource().routes()); // Tabla Version
		Route.bind(new EntityDeltaResource().routes()); // Entity Delta
		Route.bind(new DispositivoMovilResource().routes()); // DispositivoMovil
		Route.bind(new NotificacionResource().routes()); // Notificacion
		Route.bind(new FormularioResource().routes()); // Formulario
		Route.bind(new TalonarioResource().routes()); // Talonario
		Route.bind(new ReparticionResource().routes()); // Talonario
		Route.bind(new GpsResource().routes()); // GPS
		Route.bind(new ApsvResource().routes()); // APSV
		// APM-AplicacionResources
		Route.bind(new AplicacionResource().routes());
	}
}