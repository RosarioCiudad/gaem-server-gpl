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
package ar.gov.rosario.gait.aid.buss;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.frm.buss.bean.Formulario;
import ar.gov.rosario.gait.frm.buss.dao.FrmDAOFactory;
import coop.tecso.adpcore.AdpRun;
import coop.tecso.adpcore.AdpWorker;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.Estado;




public class ReenvioActasFallidasWorker  implements AdpWorker {

	private Logger log = Logger.getLogger(RegistrarActaWorker.class);

	public void cancel(AdpRun adpRun) throws Exception {

	}

	public void reset(AdpRun adpRun) throws Exception {
	}

	public boolean validate(AdpRun adpRun) throws Exception {
		return true;
	}

	public void execute(AdpRun adpRun) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		log.debug(funcName + ": enter");

		Session session = null;
		Transaction tx = null; 
		try {
			// Lista de Actas Procesadas con error
			List<Long> listIdFormulario = 
					FrmDAOFactory.getFormularioDAO().getListIdProcesadoError();

			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			int proc = 0;
			log.debug("Se procesaran "+listIdFormulario.size()+" formularios");
			for (Long idFormulario : listIdFormulario) {
				Formulario formulario = Formulario.getById(idFormulario); 
				formulario.setObservacion("Acta reprogramada el dia "+DateUtil.formatDate(new Date(),DateUtil.ddSMMSYYYY_HH_MM_MASK));
				formulario.setEstado(Estado.ACTIVO.getId());
				FrmDAOFactory.getFormularioDAO().update(formulario);
				proc++;
			}
			tx.commit();
			String msg;
			if(proc == 0){
				msg = "No existen actas a Procesar";
			}else{
				msg = String.format("Procesadas %s actas",proc);
			}

			adpRun.changeStateFinOk(msg);
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			adpRun.changeStateError("Hubo errores durante el procesamiento", e);
		}finally{
			GaitHibernateUtil.closeSession();
		}
		log.debug(funcName + ": exit");
	}
}
