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
import ar.gov.rosario.gait.frm.iface.model.MotivoCierreTipoFormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.MotivoCierreTipoFormularioVO;
import ar.gov.rosario.gait.frm.iface.service.FrmServiceLocator;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import ar.gov.rosario.gait.frm.view.util.FrmConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public class AdministrarMotivoCierreTipoFormularioDAction extends BaseDispatchAction{
	
	private Logger log = Logger.getLogger(AdministrarMotivoCierreTipoFormularioDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_TIPOFORMULARIO, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		MotivoCierreTipoFormularioAdapter motivoCierreTipoFormularioAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getMotivoCierreTipoFormularioAdapterForView(userSession, commonKey)";
				motivoCierreTipoFormularioAdapterVO = FrmServiceLocator.getFormularioService().getMotivoCierreTipoFormularioAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_MOTIVOCIERRETIPOFORMULARIO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getMotivoCierreTipoFormularioAdapterForUpdate(userSession, commonKey)";
				motivoCierreTipoFormularioAdapterVO= FrmServiceLocator.getFormularioService().getMotivoCierreTipoFormularioAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_MOTIVOCIERRETIPOFORMULARIO_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getMotivoCierreTipoFormularioAdapterForView(userSession, commonKey)";
				motivoCierreTipoFormularioAdapterVO = FrmServiceLocator.getFormularioService().getMotivoCierreTipoFormularioAdapterForView(userSession, commonKey);				
				motivoCierreTipoFormularioAdapterVO.addMessage(BaseError.MSG_ELIMINAR, DefError.AREA_LABEL);
				actionForward = mapping.findForward(FrmConstants.FWD_MOTIVOCIERRETIPOFORMULARIO_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getMotivoCierreTipoFormularioAdapterForCreate(userSession)";
				motivoCierreTipoFormularioAdapterVO = FrmServiceLocator.getFormularioService().getMotivoCierreTipoFormularioAdapterForCreate(userSession);
				actionForward = mapping.findForward(FrmConstants.FWD_MOTIVOCIERRETIPOFORMULARIO_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (motivoCierreTipoFormularioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + motivoCierreTipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			motivoCierreTipoFormularioAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + MotivoCierreTipoFormularioAdapter.NAME + ": "+ motivoCierreTipoFormularioAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
			// Subo el apdater al userSession
			userSession.put(MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);

			saveDemodaMessages(request, motivoCierreTipoFormularioAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, MotivoCierreTipoFormularioAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_MOTIVOCIERRETIPOFORMULARIO, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			MotivoCierreTipoFormularioAdapter motivoCierreTipoFormularioAdapterVO = (MotivoCierreTipoFormularioAdapter) userSession.get(MotivoCierreTipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (motivoCierreTipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + MotivoCierreTipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, MotivoCierreTipoFormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(motivoCierreTipoFormularioAdapterVO, request);

			// Tiene errores recuperables
			if (motivoCierreTipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoCierreTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, motivoCierreTipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
			}

			// llamada al servicio
			MotivoCierreTipoFormularioVO motivoCierreTipoFormularioVO = FrmServiceLocator.getFormularioService().createMotivoCierreTipoFormulario(userSession, motivoCierreTipoFormularioAdapterVO.getMotivoCierreTipoFormulario());

			// Tiene errores recuperables
			if (motivoCierreTipoFormularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoCierreTipoFormularioVO.infoString()); 
				saveDemodaErrors(request, motivoCierreTipoFormularioVO);
				return forwardErrorRecoverable(mapping, request, userSession, MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
			}

			// Tiene errores no recuperables
			if (motivoCierreTipoFormularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + motivoCierreTipoFormularioVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, MotivoCierreTipoFormularioAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, MotivoCierreTipoFormularioAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping,FrmSecurityConstants.ABM_MOTIVOCIERRETIPOFORMULARIO, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			MotivoCierreTipoFormularioAdapter motivoCierreTipoFormularioAdapterVO = (MotivoCierreTipoFormularioAdapter) userSession.get(MotivoCierreTipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (motivoCierreTipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + MotivoCierreTipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, MotivoCierreTipoFormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(motivoCierreTipoFormularioAdapterVO, request);

			// Tiene errores recuperables
			if (motivoCierreTipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoCierreTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, motivoCierreTipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
			}

			// llamada al servicio
			MotivoCierreTipoFormularioVO motivoCierreTipoFormularioVO = FrmServiceLocator.getFormularioService().updateMotivoCierreTipoFormulario(userSession, motivoCierreTipoFormularioAdapterVO.getMotivoCierreTipoFormulario());

			// Tiene errores recuperables
			if (motivoCierreTipoFormularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoCierreTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, motivoCierreTipoFormularioVO);
				return forwardErrorRecoverable(mapping, request, userSession, MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
			}

			// Tiene errores no recuperables
			if (motivoCierreTipoFormularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + motivoCierreTipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, MotivoCierreTipoFormularioAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, MotivoCierreTipoFormularioAdapter.NAME);
		}
	}



	
	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_MOTIVOCIERRETIPOFORMULARIO, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			MotivoCierreTipoFormularioAdapter motivoCierreTipoFormularioAdapterVO = (MotivoCierreTipoFormularioAdapter) userSession.get(MotivoCierreTipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (motivoCierreTipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + MotivoCierreTipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, MotivoCierreTipoFormularioAdapter.NAME); 
			}

			// llamada al servicio
			MotivoCierreTipoFormularioVO motivoCierreTipoFormularioVO = FrmServiceLocator.getFormularioService().deleteMotivoCierreTipoFormulario
					(userSession, motivoCierreTipoFormularioAdapterVO.getMotivoCierreTipoFormulario());
			
			//${Bean}VO ${bean}VO = ${Modulo}ServiceLocator.get${Submodulo}Service().delete${Bean}
			//(userSession, ${bean}AdapterVO.get${Bean}());

			// Tiene errores recuperables
			if (motivoCierreTipoFormularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoCierreTipoFormularioAdapterVO.infoString());
				saveDemodaErrors(request, motivoCierreTipoFormularioVO);				
				request.setAttribute(MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
				return mapping.findForward(FrmConstants.FWD_MOTIVOCIERRETIPOFORMULARIO_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (motivoCierreTipoFormularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + motivoCierreTipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, MotivoCierreTipoFormularioAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, MotivoCierreTipoFormularioAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return baseVolver(mapping, form, request, response, MotivoCierreTipoFormularioAdapter.NAME);

	}

	public ActionForward param (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = getCurrentUserSession(request, mapping); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			MotivoCierreTipoFormularioAdapter motivoCierreTipoFormularioAdapterVO = (MotivoCierreTipoFormularioAdapter) userSession.get(MotivoCierreTipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (motivoCierreTipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + MotivoCierreTipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, MotivoCierreTipoFormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(motivoCierreTipoFormularioAdapterVO, request);

			// Tiene errores recuperables
			if (motivoCierreTipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoCierreTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, motivoCierreTipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
			}

			// llamada al servicio
			//motivoCierreTipoFormularioAdapterVO = FrmServiceLocator.getAreaService().getAreaAdapterParam(userSession, motivoCierreTipoFormularioAdapterVO);

			// Tiene errores recuperables
			if (motivoCierreTipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoCierreTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, motivoCierreTipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
			}

			// Tiene errores no recuperables
			if (motivoCierreTipoFormularioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + motivoCierreTipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);
			// Subo el apdater al userSession
			userSession.put(MotivoCierreTipoFormularioAdapter.NAME, motivoCierreTipoFormularioAdapterVO);

			return mapping.findForward(FrmConstants.FWD_MOTIVOCIERRETIPOFORMULARIO_VIEW_ADAPTER);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, MotivoCierreTipoFormularioAdapter.NAME);
		}
	}


	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, MotivoCierreTipoFormularioAdapter.NAME);			
	}
	

}

