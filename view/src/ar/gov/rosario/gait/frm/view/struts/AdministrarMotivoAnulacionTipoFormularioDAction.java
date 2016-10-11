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
import ar.gov.rosario.gait.frm.iface.model.MotivoAnulacionTipoFormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.MotivoAnulacionTipoFormularioVO;
import ar.gov.rosario.gait.frm.iface.service.FrmServiceLocator;
import ar.gov.rosario.gait.frm.iface.util.FrmSecurityConstants;
import ar.gov.rosario.gait.frm.view.util.FrmConstants;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.NavModel;

public class AdministrarMotivoAnulacionTipoFormularioDAction extends BaseDispatchAction{
	
	private Logger log = Logger.getLogger(AdministrarMotivoAnulacionTipoFormularioDAction.class);

	public ActionForward inicializar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		String act = getCurrentAct(request);
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_MOTIVOANULACIONTIPOFORMULARIO, act); 


		if (userSession == null) return forwardErrorSession(request);

		NavModel navModel = userSession.getNavModel();
		MotivoAnulacionTipoFormularioAdapter motivoAnulacionTipoFormularioAdapterVO = null;
		String stringServicio = "";
		ActionForward actionForward = null;
		try {

			CommonKey commonKey = new CommonKey(navModel.getSelectedId());

			if (act.equals(BaseConstants.ACT_VER)) {
				stringServicio = "getMotivoAnulacionTipoFormularioAdapterForView(userSession, commonKey)";
				motivoAnulacionTipoFormularioAdapterVO = FrmServiceLocator.getFormularioService().getMotivoAnulacionTipoFormularioAdapterForView(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_MOTIVOANULACIONTIPOFORMULARIO_VIEW_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_MODIFICAR)) {
				stringServicio = "getMotivoAnulacionTipoFormularioAdapterForUpdate(userSession, commonKey)";
				motivoAnulacionTipoFormularioAdapterVO= FrmServiceLocator.getFormularioService().getMotivoAnulacionTipoFormularioAdapterForUpdate(userSession, commonKey);
				actionForward = mapping.findForward(FrmConstants.FWD_MOTIVOANULACIONTIPOFORMULARIO_EDIT_ADAPTER);
			}
			if (act.equals(BaseConstants.ACT_ELIMINAR)) {
				stringServicio = "getMotivoAnulacionTipoFormularioAdapterForView(userSession, commonKey)";
				motivoAnulacionTipoFormularioAdapterVO = FrmServiceLocator.getFormularioService().getMotivoAnulacionTipoFormularioAdapterForView(userSession, commonKey);				
				motivoAnulacionTipoFormularioAdapterVO.addMessage(BaseError.MSG_ELIMINAR, DefError.AREA_LABEL);
				actionForward = mapping.findForward(FrmConstants.FWD_MOTIVOANULACIONTIPOFORMULARIO_VIEW_ADAPTER);				
			}
			if (act.equals(BaseConstants.ACT_AGREGAR)) {
				stringServicio = "getMotivoAnulacionTipoFormularioAdapterForCreate(userSession)";
				motivoAnulacionTipoFormularioAdapterVO = FrmServiceLocator.getFormularioService().getMotivoAnulacionTipoFormularioAdapterForCreate(userSession);
				actionForward = mapping.findForward(FrmConstants.FWD_MOTIVOANULACIONTIPOFORMULARIO_EDIT_ADAPTER);				
			}

			if (log.isDebugEnabled()) log.debug(funcName + " salimos de servicio: " + stringServicio + " para " + act );
			// Nunca Tiene errores recuperables

			// Tiene errores no recuperables
			if (motivoAnulacionTipoFormularioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": servicio: " + stringServicio + ": " + motivoAnulacionTipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
			}

			// Seteo los valores de navegacion en el adapter
			motivoAnulacionTipoFormularioAdapterVO.setValuesFromNavModel(navModel);

			if (log.isDebugEnabled()) log.debug(funcName + ": " + MotivoAnulacionTipoFormularioAdapter.NAME + ": "+ motivoAnulacionTipoFormularioAdapterVO.infoString());

			// Envio el VO al request
			request.setAttribute(MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
			// Subo el apdater al userSession
			userSession.put(MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);

			saveDemodaMessages(request, motivoAnulacionTipoFormularioAdapterVO);


			return actionForward;

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, MotivoAnulacionTipoFormularioAdapter.NAME);
		}
	}

	public ActionForward agregar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_MOTIVOANULACIONTIPOFORMULARIO, BaseSecurityConstants.AGREGAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			MotivoAnulacionTipoFormularioAdapter motivoAnulacionTipoFormularioAdapterVO = (MotivoAnulacionTipoFormularioAdapter) userSession.get(MotivoAnulacionTipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (motivoAnulacionTipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + MotivoAnulacionTipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, MotivoAnulacionTipoFormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(motivoAnulacionTipoFormularioAdapterVO, request);

			// Tiene errores recuperables
			if (motivoAnulacionTipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoAnulacionTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, motivoAnulacionTipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
			}

			// llamada al servicio
			MotivoAnulacionTipoFormularioVO motivoAnulacionTipoFormularioVO = FrmServiceLocator.getFormularioService().createMotivoAnulacionTipoFormulario(userSession, motivoAnulacionTipoFormularioAdapterVO.getMotivoAnulacionTipoFormulario());

			// Tiene errores recuperables
			if (motivoAnulacionTipoFormularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoAnulacionTipoFormularioVO.infoString()); 
				saveDemodaErrors(request, motivoAnulacionTipoFormularioVO);
				return forwardErrorRecoverable(mapping, request, userSession, MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
			}

			// Tiene errores no recuperables
			if (motivoAnulacionTipoFormularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + motivoAnulacionTipoFormularioVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, MotivoAnulacionTipoFormularioAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, MotivoAnulacionTipoFormularioAdapter.NAME);
		}
	}

	public ActionForward modificar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);

		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_MOTIVOANULACIONTIPOFORMULARIO, BaseSecurityConstants.MODIFICAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			MotivoAnulacionTipoFormularioAdapter motivoAnulacionTipoFormularioAdapterVO = (MotivoAnulacionTipoFormularioAdapter) userSession.get(MotivoAnulacionTipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (motivoAnulacionTipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + MotivoAnulacionTipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, MotivoAnulacionTipoFormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(motivoAnulacionTipoFormularioAdapterVO, request);

			// Tiene errores recuperables
			if (motivoAnulacionTipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoAnulacionTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, motivoAnulacionTipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
			}

			// llamada al servicio
			MotivoAnulacionTipoFormularioVO motivoAnulacionTipoFormularioVO = FrmServiceLocator.getFormularioService().updateMotivoAnulacionTipoFormulario(userSession, motivoAnulacionTipoFormularioAdapterVO.getMotivoAnulacionTipoFormulario());

			// Tiene errores recuperables
			if (motivoAnulacionTipoFormularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoAnulacionTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, motivoAnulacionTipoFormularioVO);
				return forwardErrorRecoverable(mapping, request, userSession, MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
			}

			// Tiene errores no recuperables
			if (motivoAnulacionTipoFormularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + motivoAnulacionTipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, MotivoAnulacionTipoFormularioAdapter.NAME);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, MotivoAnulacionTipoFormularioAdapter.NAME);
		}
	}



	
	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug("entrando en " + funcName);
		UserSession userSession = canAccess(request, mapping, FrmSecurityConstants.ABM_MOTIVOANULACIONTIPOFORMULARIO, BaseSecurityConstants.ELIMINAR); 
		if (userSession==null) return forwardErrorSession(request);

		try {
			// Bajo el adapter del userSession
			MotivoAnulacionTipoFormularioAdapter motivoAnulacionTipoFormularioAdapterVO = (MotivoAnulacionTipoFormularioAdapter) userSession.get(MotivoAnulacionTipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (motivoAnulacionTipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + MotivoAnulacionTipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, MotivoAnulacionTipoFormularioAdapter.NAME); 
			}

			// llamada al servicio
			MotivoAnulacionTipoFormularioVO motivoAnulacionTipoFormularioVO = FrmServiceLocator.getFormularioService().deleteMotivoAnulacionTipoFormulario
					(userSession, motivoAnulacionTipoFormularioAdapterVO.getMotivoAnulacionTipoFormulario());
			
			//${Bean}VO ${bean}VO = ${Modulo}ServiceLocator.get${Submodulo}Service().delete${Bean}
			//(userSession, ${bean}AdapterVO.get${Bean}());

			// Tiene errores recuperables
			if (motivoAnulacionTipoFormularioVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoAnulacionTipoFormularioAdapterVO.infoString());
				saveDemodaErrors(request, motivoAnulacionTipoFormularioVO);				
				request.setAttribute(MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
				return mapping.findForward(FrmConstants.FWD_MOTIVOANULACIONTIPOFORMULARIO_VIEW_ADAPTER);
			}

			// Tiene errores no recuperables
			if (motivoAnulacionTipoFormularioVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + motivoAnulacionTipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
			}

			// Fue Exitoso
			return forwardConfirmarOk(mapping, request, funcName, MotivoAnulacionTipoFormularioAdapter.NAME);


		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, MotivoAnulacionTipoFormularioAdapter.NAME);
		}
	}

	public ActionForward volver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {

		return baseVolver(mapping, form, request, response, MotivoAnulacionTipoFormularioAdapter.NAME);

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
			MotivoAnulacionTipoFormularioAdapter motivoAnulacionTipoFormularioAdapterVO = (MotivoAnulacionTipoFormularioAdapter) userSession.get(MotivoAnulacionTipoFormularioAdapter.NAME);

			// Si es nulo no se puede continuar
			if (motivoAnulacionTipoFormularioAdapterVO == null) {
				log.error("error en: "  + funcName + ": " + MotivoAnulacionTipoFormularioAdapter.NAME + " IS NULL. No se pudo obtener de la sesion");
				return forwardErrorSessionNullObject(mapping, request, funcName, MotivoAnulacionTipoFormularioAdapter.NAME); 
			}

			// Recuperamos datos del form en el vo
			DemodaUtil.populateVO(motivoAnulacionTipoFormularioAdapterVO, request);

			// Tiene errores recuperables
			if (motivoAnulacionTipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoAnulacionTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, motivoAnulacionTipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
			}

			// llamada al servicio
			//motivoAnulacionTipoFormularioAdapterVO = FrmServiceLocator.getAreaService().getAreaAdapterParam(userSession, motivoAnulacionTipoFormularioAdapterVO);

			// Tiene errores recuperables
			if (motivoAnulacionTipoFormularioAdapterVO.hasErrorRecoverable()) {
				log.error("recoverable error en: "  + funcName + ": " + motivoAnulacionTipoFormularioAdapterVO.infoString()); 
				saveDemodaErrors(request, motivoAnulacionTipoFormularioAdapterVO);
				return forwardErrorRecoverable(mapping, request, userSession, MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
			}

			// Tiene errores no recuperables
			if (motivoAnulacionTipoFormularioAdapterVO.hasErrorNonRecoverable()) {
				log.error("error en: "  + funcName + ": " + motivoAnulacionTipoFormularioAdapterVO.errorString()); 
				return forwardErrorNonRecoverable(mapping, request, funcName, MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
			}

			// Envio el VO al request
			request.setAttribute(MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);
			// Subo el apdater al userSession
			userSession.put(MotivoAnulacionTipoFormularioAdapter.NAME, motivoAnulacionTipoFormularioAdapterVO);

			return mapping.findForward(FrmConstants.FWD_MOTIVOANULACIONTIPOFORMULARIO_VIEW_ADAPTER);

		} catch (Exception exception) {
			return baseException(mapping, request, funcName, exception, MotivoAnulacionTipoFormularioAdapter.NAME);
		}
	}


	public ActionForward refill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {		

		String funcName = DemodaUtil.currentMethodName();
		return baseRefill(mapping, form, request, response, funcName, MotivoAnulacionTipoFormularioAdapter.NAME);			
	}
	

}


