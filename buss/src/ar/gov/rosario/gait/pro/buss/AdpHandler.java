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
package ar.gov.rosario.gait.pro.buss;

import org.apache.log4j.Logger;

import coop.tecso.adpcore.AdpClientHandler;
import coop.tecso.adpcore.AdpRun;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.UserContext;

public class AdpHandler implements AdpClientHandler {

	Logger log = Logger.getLogger(AdpHandler.class);

	@Override
	public void startExecute(AdpRun run) {
		UserContext userContext = new UserContext();
		userContext.setUserName(run.getUsuario() == null ? "" : run.getUsuario());
		DemodaUtil.setCurrentUserContext(userContext);
	}

	@Override
	public String currentUserName() {
		try {
			return DemodaUtil.currentUserContext().getUserName();
		} catch (DemodaServiceException e) {
			log.info("No se puede obtener informacion del usuario para Adp. Usamos valor por defecto", e);
			return "unknown";
		}
	}

}
