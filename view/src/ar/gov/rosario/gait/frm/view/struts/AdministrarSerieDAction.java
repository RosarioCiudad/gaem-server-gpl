package ar.gov.rosario.gait.frm.view.struts;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.base.iface.util.BaseSecurityConstants;
import ar.gov.rosario.gait.base.view.struts.BaseDispatchAction;
import ar.gov.rosario.gait.base.view.util.BaseConstants;
import ar.gov.rosario.gait.base.view.util.UserSession;
import ar.gov.rosario.gait.def.iface.util.DefError;
import ar.gov.rosario.gait.frm.iface.model.SerieAdapter;
import ar.gov.rosario.gait.frm.iface.model.SerieVO;
import ar.gov.rosario.gait.frm.iface.service.FrmServiceLocator;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import ar.gov.rosario.gait.frm.view.util.FrmConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;



public final class AdministrarSerieDAction extends BaseDispatchAction {

	private Logger log = Logger.getLogger(AdministrarTipoFormularioDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_SERIE, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		SerieAdapter serieAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getSerieAdapterForView(userSession, commonKey)";
				serieAdapterVO = FrmServiceLocator.getNumeracionService().getSerieAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_SERIE_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getSerieAdapterForUpdate(userSession, commonKey)";
				serieAdapterVO= FrmServiceLocator.getNumeracionService().getSerieAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_SERIE_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getSerieAdapterForView(userSession, commonKey)";
				serieAdapterVO = FrmServiceLocator.getNumeracionService().getSerieAdapterForView(userSession, commonKey);				
				serieAdapterVO.addMessage(BaseError.MSG_ELIMINAR, DefError.AREA_LABEL);
				actionForward = mapping.findForward(FrmConstants.FWD_SERIE_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getSerieAdapterForCreate(userSession)";
				serieAdapterVO = FrmServiceLocator.getNumeracionService().getSerieAdapterForCreate(userSession);
				actionForward = mapping.findForward(FrmConstants.FWD_SERIE_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (serieAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + serieAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, SerieAdapter.NAME, serieAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			serieAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + SerieAdapter.NAME + ": "+ serieAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(SerieAdapter.NAME, serieAdapterVO);
			// Subo el apdater al userSession
			userSession.put(SerieAdapter.NAME, serieAdapterVO);

			saveDemodaMessages(request, serieAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, SerieAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_SERIE, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			SerieAdapter serieAdapterVO = (SerieAdapter) userSession.get(SerieAdapter.NAME);

			// Si es nulo no se puede continuar
			if (serieAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + SerieAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, SerieAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(serieAdapterVO, request);

			// Tiene errores recuperabless
			if (serieAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + serieAdapterVO.infoString()); 
				saveDemodaErrors(request, serieAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, SerieAdapter.NAME, serieAdapterVO);
			}

			// llamada al servicio
			SerieVO serieVO = FrmServiceLocator.getNumeracionService().createSerie(userSession, serieAdapterVO.getSerie());

			// Tiene errores recuperables
			if (serieVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + serieVO.infoString()); 
				saveDemodaErrors(request, serieVO);
				return forwardErrorRecoverable(mapping, request, userSession, SerieAdapter.NAME, serieAdapterVO);
			}

			// Tiene errores no recuperables
			if (serieVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + serieVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, SerieAdapter.NAME, serieAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, SerieAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, SerieAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_SERIE, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			SerieAdapter serieAdapterVO = (SerieAdapter) userSession.get(SerieAdapter.NAME);

			// Si es nulo no se puede continuar
			if (serieAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + SerieAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, SerieAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(serieAdapterVO, request);

			// Tiene errores recuperables
			if (serieAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + serieAdapterVO.infoString()); 
				saveDemodaErrors(request, serieAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, SerieAdapter.NAME, serieAdapterVO);
			}

			// llamada al servicio
			SerieVO serieVO = FrmServiceLocator.getNumeracionService().updateSerie(userSession, serieAdapterVO.getSerie());

			// Tiene errores recuperables
			if (serieVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + serieAdapterVO.infoString()); 
				saveDemodaErrors(request, serieVO);
				return forwardErrorRecoverable(mapping, request, userSession, SerieAdapter.NAME, serieAdapterVO);
			}

			// Tiene errores no recuperables
			if (serieVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + serieAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, SerieAdapter.NAME, serieAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, SerieAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, SerieAdapter.NAME);
		}
	}

	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_SERIE, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			SerieAdapter serieAdapterVO = (SerieAdapter) userSession.get(SerieAdapter.NAME);

			// Si es nulo no se puede continuar
			if (serieAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + SerieAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, SerieAdapter.NAME); 
			}

			// llamada al servicio
			SerieVO serieVO = FrmServiceLocator.getNumeracionService().deleteSerie(userSession, serieAdapterVO.getSerie());

			// Tiene errores recuperables
			if (serieVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + serieAdapterVO.infoString());
				saveDemodaErrors(request, serieVO);				
				request.setAttribute(SerieAdapter.NAME, serieAdapterVO);
				return mapping.findForward(FrmConstants.FWD_SERIE_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (serieVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + serieAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, SerieAdapter.NAME, serieAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, SerieAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, SerieAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return baseVolver(mapping, form, request, response, SerieAdapter.NAME);
	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, SerieAdapter.NAME);			
	}
} 