
package ar.gov.rosario.gait.proceso.ejemplo;

import org.apache.log4j.Logger;

import coop.tecso.adpcore.AdpRun;
import coop.tecso.adpcore.AdpWorker;

/**
 * Proceso ADP de anulación de deudas posteriores a fecha cierre definitivo 
 * 
 * @author Tecso Coop. Ltda.
 */
public class EjemploWorker implements AdpWorker {
	
	private static Logger log = Logger.getLogger(EjemploWorker.class);

	public void reset(AdpRun adpRun) throws Exception {
	}
	
	public void cancel(AdpRun adpRun) throws Exception {
	}

	public void execute(AdpRun adpRun) throws Exception {
	}

	public boolean validate(AdpRun adpRun) throws Exception {
			return true;
	}
}
