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

import java.io.File;
import java.util.Map;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import coop.tecso.adpcore.AdpRun;
import coop.tecso.adpcore.AdpRunDirEnum;
import coop.tecso.adpcore.AdpRunState;
import coop.tecso.adpcore.AdpWorker;
import coop.tecso.grs.Grs;
import coop.tecso.grs.GrsEngine;

public class GrsWorker implements AdpWorker {

	public void cancel(AdpRun adpRun) throws Exception {
	}

	public void execute(AdpRun adpRun) throws Exception {
		try {
			String jsFilename = jsFilename(adpRun);
			Grs grs = new Grs();
			grs.stdout(adpRun.getLogWriter(), false);
			
			grs.workdir(new File(jsFilename).getParentFile().getParentFile().getAbsolutePath());
			grs.outdir(grs.workdir()  + File.separatorChar + "salida" + File.separatorChar + adpRun.getId());			
			grs.mkdirs();
			
			GrsEngine.run(jsFilename, grs);

			if (adpRun.getIdEstadoCorrida() == AdpRunState.PROCESANDO.id()) {
				adpRun.changeState(AdpRunState.FIN_OK, "Finalizado.");
			}
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public void reset(AdpRun adpRun) throws Exception {
	}

	public boolean validate(AdpRun adpRun) throws Exception {
		return true;
	}

	private String jsFilename(AdpRun adpRun) throws Exception {
		String procDir = adpRun.getProcessDir(AdpRunDirEnum.BASE);
		Map<String, String> info = Grs.processInfo(procDir);
		
		String jsName = info.get("proc");
		String jsFilename = procDir + File.separatorChar + jsName;
		return jsFilename;
	}
}
