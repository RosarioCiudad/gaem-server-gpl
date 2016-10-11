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
