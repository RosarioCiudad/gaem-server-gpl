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
import ar.gov.rosario.gait.frm.iface.model.NumeracionAdapter;
import ar.gov.rosario.gait.frm.iface.model.NumeracionVO;
import ar.gov.rosario.gait.frm.iface.service.FrmServiceLocator;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import ar.gov.rosario.gait.frm.view.util.FrmConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public class AdministrarNumeracionDAction extends BaseDispatchAction{

	private Logger log = Logger.getLogger(AdministrarNumeracionDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_NUMERACION, act); 
		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		NumeracionAdapter numeracionAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getNumeracionAdapterForView(userSession, commonKey)";
				numeracionAdapterVO = FrmServiceLocator.getNumeracionService().getNumeracionAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_NUMERACION_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getNumeracionAdapterForUpdate(userSession, commonKey)";
				numeracionAdapterVO= FrmServiceLocator.getNumeracionService().getNumeracionAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_NUMERACION_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getNumeracionAdapterForView(userSession, commonKey)";
				numeracionAdapterVO = FrmServiceLocator.getNumeracionService().getNumeracionAdapterForView(userSession, commonKey);				
				numeracionAdapterVO.addMessage(BaseError.MSG_ELIMINAR, DefError.AREA_LABEL);
				actionForward = mapping.findForward(FrmConstants.FWD_NUMERACION_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getNumeracionAdapterForCreate(userSession)";
				numeracionAdapterVO = FrmServiceLocator.getNumeracionService().getNumeracionAdapterForCreate(userSession);
				actionForward = mapping.findForward(FrmConstants.FWD_NUMERACION_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (numeracionAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + numeracionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, NumeracionAdapter.NAME, numeracionAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			numeracionAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + NumeracionAdapter.NAME + ": "+ numeracionAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(NumeracionAdapter.NAME, numeracionAdapterVO);
			// Subo el apdater al userSession
			userSession.put(NumeracionAdapter.NAME, numeracionAdapterVO);

			saveDemodaMessages(request, numeracionAdapterVO);

			return actionForward;
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, NumeracionAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_NUMERACION, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			NumeracionAdapter numeracionAdapterVO = (NumeracionAdapter) userSession.get(NumeracionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (numeracionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + NumeracionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, NumeracionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(numeracionAdapterVO, request);

			// Tiene errores recuperables
			if (numeracionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + numeracionAdapterVO.infoString()); 
				saveDemodaErrors(request, numeracionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, NumeracionAdapter.NAME, numeracionAdapterVO);
			}

			// llamada al servicio
			NumeracionVO numeracionVO = FrmServiceLocator.getNumeracionService().createNumeracion(userSession, numeracionAdapterVO.getNumeracion());

			// Tiene errores recuperables
			if (numeracionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + numeracionVO.infoString()); 
				saveDemodaErrors(request, numeracionVO);
				return forwardErrorRecoverable(mapping, request, userSession, NumeracionAdapter.NAME, numeracionAdapterVO);
			}

			// Tiene errores no recuperables
			if (numeracionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + numeracionVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, NumeracionAdapter.NAME, numeracionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, NumeracionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, NumeracionAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_NUMERACION, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			NumeracionAdapter numeracionAdapterVO = (NumeracionAdapter) userSession.get(NumeracionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (numeracionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + NumeracionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, NumeracionAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(numeracionAdapterVO, request);

			// Tiene errores recuperables
			if (numeracionAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + numeracionAdapterVO.infoString()); 
				saveDemodaErrors(request, numeracionAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, NumeracionAdapter.NAME, numeracionAdapterVO);
			}

			// llamada al servicio
			NumeracionVO numeracionVO = FrmServiceLocator.getNumeracionService().updateNumeracion(userSession, numeracionAdapterVO.getNumeracion());

			// Tiene errores recuperables
			if (numeracionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + numeracionAdapterVO.infoString()); 
				saveDemodaErrors(request, numeracionVO);
				return forwardErrorRecoverable(mapping, request, userSession, NumeracionAdapter.NAME, numeracionAdapterVO);
			}

			// Tiene errores no recuperables
			if (numeracionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + numeracionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, NumeracionAdapter.NAME, numeracionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, NumeracionAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, NumeracionAdapter.NAME);
		}
	}


	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_NUMERACION, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			NumeracionAdapter numeracionAdapterVO = (NumeracionAdapter) userSession.get(NumeracionAdapter.NAME);

			// Si es nulo no se puede continuar
			if (numeracionAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + NumeracionAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, NumeracionAdapter.NAME); 
			}

			// llamada al servicio
			NumeracionVO numeracionVO = FrmServiceLocator.getNumeracionService().deleteNumeracion(userSession, numeracionAdapterVO.getNumeracion());

			// Tiene errores recuperables
			if (numeracionVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + numeracionAdapterVO.infoString());
				saveDemodaErrors(request, numeracionVO);				
				request.setAttribute(NumeracionAdapter.NAME, numeracionAdapterVO);
				return mapping.findForward(FrmConstants.FWD_NUMERACION_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (numeracionVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + numeracionAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, NumeracionAdapter.NAME, numeracionAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, NumeracionAdapter.NAME);
		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, NumeracionAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		return baseVolver(mapping, form, request, response, NumeracionAdapter.NAME);
	}

	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, NumeracionAdapter.NAME);			
	}
}