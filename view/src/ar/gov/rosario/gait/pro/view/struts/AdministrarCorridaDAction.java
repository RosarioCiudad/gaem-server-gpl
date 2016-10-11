package ar.gov.rosario.gait.pro.view.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.pro.iface.model.CorridaAdapter;
import ar.gov.rosario.gait.pro.iface.service.ProServiceLocator;
import ar.gov.rosario.gait.pro.iface.util.ProSecurityConstants;
import ar.gov.rosario.gait.pro.view.util.ProConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public final class AdministrarCorridaDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger((AdministrarCorridaDAction.class));

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		
		UserSession userSession = canAccess(request, mapping, ProSecurityConstants.ABM_CORRIDA, act);
		if (userSession==null) canAccess(request, mapping, ProSecurityConstants.ABM_CORRIDA_PROCESO, act);
		if (userSession==null) canAccess(request, mapping, ProSecurityConstants.ABM_CORRIDA_USUARIO, act);
		if (userSession == null) return forwardErrorSession(request);
		
		NavModel navModel = userSession.getNavModel();
		CorridaAdapter corridaAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {
			
			CommonKey commonKey = new CommonKey(navModel.getSelectedId());
			
			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getCorridaAdapterForView(userSession, commonKey)";
				corridaAdapterVO = ProServiceLocator.getConsultaService().getCorridaAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(ProConstants.FWD_CORRIDA_VIEW_ADAPTER);
			}
			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables
			
			// Tiene errores no recuperables
			if (corridaAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + corridaAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, CorridaAdapter.NAME, corridaAdapterVO);
			}
			
			// Seteo los valores de navegacion en el adapter
			corridaAdapterVO.setValuesFromNavModel(navModel);
						
			if (log.isDebugEnabled()) log.debug(funcName + ": " + CorridaAdapter.NAME + ": "+ corridaAdapterVO.infoString());
			
			// Envio el VO al request
			request.setAttribute(CorridaAdapter.NAME, corridaAdapterVO);
			// Subo el apdater al userSession
			userSession.put(CorridaAdapter.NAME, corridaAdapterVO);
			 
			saveDemodaMessages(request, corridaAdapterVO);
			
			return actionForward;
			
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, CorridaAdapter.NAME);
		}
	}
	
	public ActionForward volver(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		
		return baseVolver(mapping, form, request, response, CorridaAdapter.NAME);
	}
	
	public ActionForward downloadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			String funcName = DemodaUtil.currentMethodName();
			if (log.isDebugEnabled()) log.debug(funcName + ": enter");		
			UserSession userSession = getCurrentUserSession(request, mapping); 
			if (userSession == null) return forwardErrorSession(request);

			try {
					
				String fileId = request.getParameter("fileParam");	
				CommonKey commonKey = new CommonKey(fileId);
				
				// Obtenemos el id del archivo seleccionado
				Long idFileCorrida = commonKey.getId();			

				// Obtenemos el nombre del archivo seleccionado mediante una llamada a un servicio
				String fileName = ProServiceLocator.getAdpProcesoService().obtenerFileCorridaName(idFileCorrida);
				
				baseResponseFile(response,fileName);

				log.debug("exit: " + funcName);
				
				return null;
			} catch (Exception exception) {
				return baseException(mapping, request, funcName, exception, CorridaAdapter.NAME);
			}
	}

}
