package ar.gov.rosario.gait.frm.buss.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.def.buss.bean.Area;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.frm.buss.bean.EstadoTipoFormulario;
import ar.gov.rosario.gait.frm.buss.bean.Formulario;
import ar.gov.rosario.gait.frm.buss.bean.FormularioDetalle;
import ar.gov.rosario.gait.frm.buss.bean.MotivoAnulacionTipoFormulario;
import ar.gov.rosario.gait.frm.buss.bean.MotivoCierreTipoFormulario;
import ar.gov.rosario.gait.frm.buss.bean.Numeracion;
import ar.gov.rosario.gait.frm.buss.bean.Serie;
import ar.gov.rosario.gait.frm.buss.bean.TipoFormulario;
import ar.gov.rosario.gait.frm.buss.dao.FrmDAOFactory;
import ar.gov.rosario.gait.frm.iface.model.EstadoTipoFormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.EstadoTipoFormularioSearchPage;
import ar.gov.rosario.gait.frm.iface.model.EstadoTipoFormularioVO;
import ar.gov.rosario.gait.frm.iface.model.FormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.FormularioDetalleAdapter;
import ar.gov.rosario.gait.frm.iface.model.FormularioDetalleVO;
import ar.gov.rosario.gait.frm.iface.model.FormularioSearchPage;
import ar.gov.rosario.gait.frm.iface.model.FormularioVO;
import ar.gov.rosario.gait.frm.iface.model.MotivoAnulacionTipoFormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.MotivoAnulacionTipoFormularioSearchPage;
import ar.gov.rosario.gait.frm.iface.model.MotivoAnulacionTipoFormularioVO;
import ar.gov.rosario.gait.frm.iface.model.MotivoCierreTipoFormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.MotivoCierreTipoFormularioSearchPage;
import ar.gov.rosario.gait.frm.iface.model.MotivoCierreTipoFormularioVO;
import ar.gov.rosario.gait.frm.iface.model.NumeracionAdapter;
import ar.gov.rosario.gait.frm.iface.model.NumeracionSearchPage;
import ar.gov.rosario.gait.frm.iface.model.NumeracionVO;
import ar.gov.rosario.gait.frm.iface.model.SerieAdapter;
import ar.gov.rosario.gait.frm.iface.model.SerieSearchPage;
import ar.gov.rosario.gait.frm.iface.model.SerieVO;
import ar.gov.rosario.gait.frm.iface.model.TipoFormularioAdapter;
import ar.gov.rosario.gait.frm.iface.model.TipoFormularioSearchPage;
import ar.gov.rosario.gait.frm.iface.model.TipoFormularioVO;
import ar.gov.rosario.gait.frm.iface.service.IFrmFormularioService;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;

import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.CommonKey;
import coop.tecso.demoda.iface.model.Estado;
import coop.tecso.demoda.iface.model.UserContext;
import coop.tecso.demoda.iface.model.ValueVO;
import coop.tecso.demoda.util.Base64;

public class FrmFormularioServiceHbmImpl implements IFrmFormularioService {
	private Logger log = Logger.getLogger(FrmFormularioServiceHbmImpl.class);

	// ---> ABM Formulario 	
	public FormularioSearchPage getFormularioSearchPageInit(UserContext userContext) throws DemodaServiceException {	
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			FormularioSearchPage formularioSearchPage = new FormularioSearchPage();

			// Estados
			List<ValueVO> listEstado = new ArrayList<>();
			listEstado.add(new ValueVO(Estado.CREADO.getId(), StringUtil.SELECT_OPCION_TODOS));
			listEstado.add(new ValueVO(Estado.ACTIVO.getId(), "Sin Procesar"));
			listEstado.add(new ValueVO(Estado.PROCESADO.getId(), Estado.PROCESADO.getValue()));
			listEstado.add(new ValueVO(Estado.PROCESADO_ERROR.getId(), Estado.PROCESADO_ERROR.getValue()));
			listEstado.add(new ValueVO(Estado.FORMULARIO_DUPLICADO.getId(), Estado.FORMULARIO_DUPLICADO.getValue()));
			listEstado.add(new ValueVO(Estado.NUMERO_DUPLICADO.getId(), Estado.NUMERO_DUPLICADO.getValue()));
			formularioSearchPage.setListEstado(listEstado);
			formularioSearchPage.getFormulario().setEstado(Estado.CREADO);
			
			formularioSearchPage.setListTipoFormulario(ListUtilBean.toVO(
					TipoFormulario.getListActivos(), 0, false, new AreaVO(-1, StringUtil.SELECT_OPCION_TODOS)));

			if (userContext.getEsDGI()) {
				formularioSearchPage.setListArea(ListUtilBean.toVO(
						Area.getListActivos(), 0, false, new AreaVO(-1, StringUtil.SELECT_OPCION_TODAS)));
			} else {
				AreaVO currentArea = new AreaVO(userContext.getIdArea().intValue(), userContext.getDesArea());
				formularioSearchPage.getFormulario().setArea(currentArea);
			}

			// Cantidad de Registros
			formularioSearchPage.setRecsByPage(10L);

			log.debug(funcName + ": exit");
			return formularioSearchPage;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public FormularioSearchPage imprimirFormularioSearchPageResult(UserContext userContext, FormularioSearchPage formularioSearchPage) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();

			formularioSearchPage.clearError();


			StringBuilder filtros = new StringBuilder();
			filtros.append(String.format("<h2>%s</h2>", "Reporte de Actas"));
			// Fecha y Hora
			filtros.append(String.format("<p><b>%s: </b>%s<b> - %s: </b>%s</p>", 
					"Fecha",DateUtil.formatDate(new Date(), DateUtil.ddSMMSYYYY_MASK),
					"Hora",DateUtil.formatDate(new Date(), DateUtil.HOUR_MINUTE_MASK)));
			// Usuario
			filtros.append(String.format("<p><b>%s: </b>%s</p>", 
					"Usuario",userContext.getUserName()));

			filtros.append(String.format("<h4>%s</h4>", "Filtros: "));

			formularioSearchPage.setPaged(false);
			List<Formulario> listFormulario = FrmDAOFactory.getFormularioDAO().getBySearchPage(formularioSearchPage);
			formularioSearchPage.setPaged(true);

			HtmlReportHelper report = new HtmlReportHelper();
			// Filtros
			report.addRawHTML(filtros.toString());
			// Inspector
			Integer numeroInsPectorRef = 0;
			for (Formulario formulario : listFormulario) {
				if(!numeroInsPectorRef.equals(formulario.getNumeroInspector())){
					// Cambio de Pagina
					if(numeroInsPectorRef != 0){
						report.endTable();
						report.startNewPage();
					}
					numeroInsPectorRef = formulario.getNumeroInspector();
					// Cabecera
					StringBuilder cabecera = new StringBuilder();
					cabecera.append(String.format("<h4>%s</h4>", "Datos del Inspector "));
					cabecera.append("<p>");
					cabecera.append(String.format(" <b>%s: </b>%s ", "Número",formulario.getNumeroInspector()));
					cabecera.append("</p>");
					cabecera.append("<p>");
					cabecera.append(String.format("<b>%s: </b>%s ", "Nombre",formulario.getUsuarioCierre().getNombre()));
					cabecera.append("</p>");
					cabecera.append("<p>&nbsp;</p>");
					//
					report.addRawHTML(cabecera.toString());
					report.startTable(new String[]{"Número Acta","Fecha Cierre","Hora Cierre","Resultado","Motivo"});
				}

				String motivo = "";
				// Anulada
				if(formulario.getMotivoAnulacionTipoFormulario() != null){
					motivo = formulario.getMotivoAnulacionTipoFormulario().getDescripcion();
				}
				// Cerrada
				if(formulario.getMotivoCierreTipoFormulario() != null){
					motivo = formulario.getMotivoCierreTipoFormulario().getDescripcion();
				}
				// Data
				report.addTableRow(
						new String[]{formulario.getNumero(),
								DateUtil.formatDate(formulario.getFechaCierre(), DateUtil.ddSMMSYYYY_MASK),
								DateUtil.formatDate(formulario.getFechaCierre(), DateUtil.HOUR_MINUTE_MASK),
								formulario.getEstadoTipoFormulario().getDescripcion(),
								motivo});

				formulario.setFechaImpresion(new Date());
			}

			if(numeroInsPectorRef != 0){
				report.endTable();
			}


			String fileName = "Reporte_"+UUID.randomUUID().toString().toUpperCase()+".pdf"; 
			//
			File pdfFile = new File("/tmp" + File.separator + fileName);
			//--

			formularioSearchPage.getReport().setReportFileName(pdfFile.getAbsolutePath());
			Document document = new Document(PageSize.LETTER);
			PdfWriter pdfWriter = PdfWriter.getInstance
					(document, new FileOutputStream(pdfFile));
			document.open();
			document.addAuthor("Municipalidad de Rosario");
			document.addCreator(userContext.getUserName());
			document.addSubject("Reporte de Actas");
			document.addCreationDate();
			document.addTitle("Reporte de Actas");

			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
			worker.parseXHtml(pdfWriter, document, new StringReader(report.out()));

			// CSS
			String str = "@page {@bottom-left {content: counter(page) '/' counter(pages);}}";
			CssFile cssFile = worker.getCSS(new ByteArrayInputStream(str.getBytes()));
			worker.getDefaultCssResolver(true).addCss(cssFile);

			document.close();

			tx.commit();

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return formularioSearchPage;
		} catch (Exception e) {
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public FormularioSearchPage getFormularioSearchPageResult(UserContext userContext, FormularioSearchPage formularioSearchPage) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			formularioSearchPage.clearError();

			//Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<Formulario> listFormulario = FrmDAOFactory.getFormularioDAO().getBySearchPage(formularioSearchPage);  

			//Aqui se podria iterar la lista de BO para setear banderas en VOs y otras cosas del negocio.
			List<FormularioVO> listResult = new ArrayList<>();
			for (Formulario formulario : listFormulario) {
				FormularioVO formularioVO = (FormularioVO) formulario.toVO(2,false);
				if(formularioVO.getEstado().equals(Estado.PROCESADO)){
					formularioVO.setModificarBussEnabled(false);
					formularioVO.setEliminarBussEnabled(false);
				}
				listResult.add(formularioVO);
			}

			//Aqui pasamos BO a VO
			formularioSearchPage.setListResult(listResult);

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return formularioSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public FormularioAdapter getFormularioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			Formulario formulario = Formulario.getById(commonKey.getId());

			FormularioAdapter formularioAdapter = new FormularioAdapter();
			// Formulario
			FormularioVO formularioVO = (FormularioVO) formulario.toVO(2, false);
			formularioVO.setFormularioDigital(Base64.encodeToString(formulario.getFormularioDigital(), Base64.DEFAULT));
			// FormularioDetalle
			List<FormularioDetalleVO> listFormularioDetalle = new ArrayList<>();
			for (FormularioDetalle formularioDetalle : formulario.getListFormularioDetalle()) {
				FormularioDetalleVO formularioDetalleVO = (FormularioDetalleVO) formularioDetalle.toVO(2,false);
				if(formularioDetalle.getImagen() != null)
					formularioDetalleVO.setImagen(Base64.encodeToString(formularioDetalle.getImagen(), Base64.DEFAULT));
				listFormularioDetalle.add(formularioDetalleVO);
			}
			formularioVO.setListFormularioDetalle(listFormularioDetalle);
			formularioAdapter.setFormulario(formularioVO);

			log.debug(funcName + ": exit");
			return formularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public FormularioAdapter getFormularioAdapterForCreate(UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			FormularioAdapter formularioAdapter = new FormularioAdapter();

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return formularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public FormularioAdapter getFormularioAdapterParam(UserContext userContext, FormularioAdapter formularioAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			formularioAdapter.clearError();

			// Logica del param

			log.debug(funcName + ": exit");
			return formularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public FormularioAdapter getFormularioAdapterForUpdate(UserContext userContext, CommonKey commonKeyFormulario) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			Formulario formulario = Formulario.getById(commonKeyFormulario.getId());

			FormularioAdapter formularioAdapter = new FormularioAdapter();
			// Formulario
			FormularioVO formularioVO = (FormularioVO) formulario.toVO(2, false);
			formularioVO.setFormularioDigital(Base64.encodeToString(formulario.getFormularioDigital(), Base64.DEFAULT));
			// FormularioDetalle
			List<FormularioDetalleVO> listFormularioDetalle = new ArrayList<>();
			for (FormularioDetalle formularioDetalle : formulario.getListFormularioDetalle()) {
				FormularioDetalleVO formularioDetalleVO = (FormularioDetalleVO) formularioDetalle.toVO(2,false);
				if(formularioDetalle.getImagen() != null)
					formularioDetalleVO.setImagen(Base64.encodeToString(formularioDetalle.getImagen(), Base64.DEFAULT));
				listFormularioDetalle.add(formularioDetalleVO);
			}
			formularioVO.setListFormularioDetalle(listFormularioDetalle);

			formularioAdapter.setFormulario(formularioVO);
			log.debug(funcName + ": exit");
			return formularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public FormularioVO createFormulario(UserContext userContext, FormularioVO formularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			formularioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Formulario formulario = new Formulario();

			//			this.copyFromVO(formulario, formularioVO);
			//
			//			formulario.setEstado(Estado.ACTIVO.getId());
			//
			//			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			//			formulario = Frm${Submodulo}Manager.getInstance().createFormulario(formulario);

			if (formulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				formularioVO =  (FormularioVO) formulario.toVO(0,false);
			}
			formulario.passErrorMessages(formularioVO);

			log.debug(funcName + ": exit");
			return formularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public FormularioVO updateFormulario(UserContext userContext, FormularioVO formularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			formularioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Formulario formulario = Formulario.getById(formularioVO.getId());

			if(!formularioVO.validateVersion(formulario.getFechaUltMdf())) return formularioVO;

			//			this.copyFromVO(formulario, formularioVO);
			//
			//			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			//			formulario = Frm${Submodulo}Manager.getInstance().updateFormulario(formulario);

			if (formulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				formularioVO =  (FormularioVO) formulario.toVO(0,false);
			}
			formulario.passErrorMessages(formularioVO);

			log.debug(funcName + ": exit");
			return formularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public FormularioVO deleteFormulario(UserContext userContext, FormularioVO formularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			formularioVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			Formulario formulario = Formulario.getById(formularioVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad de otro bean
			formulario = FrmFormularioManager.getInstance().deleteFormulario(formulario);

			if (formulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				formularioVO =  (FormularioVO) formulario.toVO(0,false);
			}
			formulario.passErrorMessages(formularioVO);

			log.debug(funcName + ": exit");
			return formularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			try { tx.rollback(); } catch (Exception ee) {}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public FormularioVO activarFormulario(UserContext userContext, FormularioVO formularioVO ) throws DemodaServiceException{
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx  = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();

			Formulario formulario = Formulario.getById(formularioVO.getId());

			formulario.activar();

			if (formulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				formularioVO =  (FormularioVO) formulario.toVO(0,false);
			}
			formulario.passErrorMessages(formularioVO);

			log.debug(funcName + ": exit");
			return formularioVO;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public FormularioVO desactivarFormulario(UserContext userContext, FormularioVO formularioVO ) throws DemodaServiceException{
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx  = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();

			Formulario formulario = Formulario.getById(formularioVO.getId());

			formulario.desactivar();

			if (formulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				formularioVO =  (FormularioVO) formulario.toVO(0,false);
			}
			formulario.passErrorMessages(formularioVO);

			log.debug(funcName + ": exit");
			return formularioVO;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}	       

	public FormularioAdapter imprimirFormulario(UserContext userContext, FormularioAdapter formularioAdapterVO ) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			Formulario formulario = Formulario.getById(formularioAdapterVO.getFormulario().getId());

			FrmDAOFactory.getFormularioDAO().imprimirGenerico(formulario, formularioAdapterVO.getReport());

			//--
			StringBuilder builder = new StringBuilder();



			//--



			log.debug(funcName + ": exit");
			return formularioAdapterVO;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();        
		} 
	}

	// <--- ABM Formulario

	// ---> ABM FormularioDetalle
	public FormularioDetalleAdapter getFormularioDetalleAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			FormularioDetalle formularioDetalle = FormularioDetalle.getById(commonKey.getId());

			FormularioDetalleAdapter formularioDetalleAdapter = new FormularioDetalleAdapter();
			FormularioDetalleVO formularioDetalleVO = (FormularioDetalleVO) formularioDetalle.toVO(2,false);
			if(formularioDetalle.getImagen() != null)
				formularioDetalleVO.setImagen(Base64.encodeToString(formularioDetalle.getImagen(), Base64.DEFAULT));
			formularioDetalleAdapter.setFormularioDetalle(formularioDetalleVO);

			log.debug(funcName + ": exit");
			return formularioDetalleAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public FormularioDetalleAdapter getFormularioDetalleAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			FormularioDetalle formularioDetalle = FormularioDetalle.getById(commonKey.getId());

			FormularioDetalleAdapter formularioDetalleAdapter = new FormularioDetalleAdapter();
			FormularioDetalleVO formularioDetalleVO = (FormularioDetalleVO) formularioDetalle.toVO(2,false);
			if(formularioDetalle.getImagen() != null)
				formularioDetalleVO.setImagen(Base64.encodeToString(formularioDetalle.getImagen(), Base64.DEFAULT));
			formularioDetalleAdapter.setFormularioDetalle(formularioDetalleVO);

			log.debug(funcName + ": exit");
			return formularioDetalleAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public FormularioDetalleVO updateFormularioDetalle(UserContext userContext, FormularioDetalleVO formularioDetalleVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			formularioDetalleVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			FormularioDetalle formularioDetalle = FormularioDetalle.getById(formularioDetalleVO.getId());

			if(!formularioDetalleVO.validateVersion(formularioDetalle.getFechaUltMdf())) return formularioDetalleVO;

			formularioDetalle.setValor(formularioDetalleVO.getValor());

			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			formularioDetalle = FrmFormularioManager.getInstance().updateFormularioDetalle(formularioDetalle);

			if (formularioDetalle.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				formularioDetalleVO =  (FormularioDetalleVO) formularioDetalle.toVO(0,false);
			}
			formularioDetalle.passErrorMessages(formularioDetalleVO);

			log.debug(funcName + ": exit");
			return formularioDetalleVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public FormularioDetalleVO deleteFormularioDetalle(UserContext userContext, FormularioDetalleVO formularioDetalleVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			formularioDetalleVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			FormularioDetalle formularioDetalle = FormularioDetalle.getById(formularioDetalleVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad de otro bean
			formularioDetalle = FrmFormularioManager.getInstance().deleteFormularioDetalle(formularioDetalle);

			if (formularioDetalle.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				formularioDetalleVO =  (FormularioDetalleVO) formularioDetalle.toVO(0,false);
			}
			formularioDetalle.passErrorMessages(formularioDetalleVO);

			log.debug(funcName + ": exit");
			return formularioDetalleVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			try { tx.rollback(); } catch (Exception ee) {}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}
	// <--- ABM FormularioDetalle

	// ---> ABM TipoFormulario 	
	public TipoFormularioSearchPage getTipoFormularioSearchPageInit(UserContext userContext) throws DemodaServiceException {		
		return new TipoFormularioSearchPage();
	}
	//
	public TipoFormularioSearchPage getTipoFormularioSearchPageResult(UserContext userContext, TipoFormularioSearchPage tipoFormularioSearchPage) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			tipoFormularioSearchPage.clearError();

			//Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<TipoFormulario> listTipoFormulario = ApmDAOFactory.getTipoFormularioDAO().getBySearchPage(tipoFormularioSearchPage);  

			//Aqui se podria iterar la lista de BO para setear banderas en VOs y otras cosas del negocio.

			//Aqui pasamos BO a VO
			tipoFormularioSearchPage.setListResult(ListUtilBean.toVO(listTipoFormulario,0));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return tipoFormularioSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TipoFormularioAdapter getTipoFormularioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			TipoFormulario tipoFormulario = TipoFormulario.getById(commonKey.getId());

			TipoFormularioAdapter tipoFormularioAdapter = new TipoFormularioAdapter();
			tipoFormularioAdapter.setTipoFormulario((TipoFormularioVO) tipoFormulario.toVO(1));

			log.debug(funcName + ": exit");
			return tipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TipoFormularioAdapter getTipoFormularioAdapterForCreate(UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			TipoFormularioAdapter tipoFormularioAdapter = new TipoFormularioAdapter();

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return tipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public TipoFormularioAdapter getTipoFormularioAdapterParam(UserContext userContext, TipoFormularioAdapter tipoFormularioAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			tipoFormularioAdapter.clearError();

			// Logica del param



			log.debug(funcName + ": exit");
			return tipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public TipoFormularioAdapter getTipoFormularioAdapterForUpdate(UserContext userContext, CommonKey commonKeyTipoFormulario) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			TipoFormulario tipoFormulario = TipoFormulario.getById(commonKeyTipoFormulario.getId());

			TipoFormularioAdapter tipoFormularioAdapter = new TipoFormularioAdapter();
			tipoFormularioAdapter.setTipoFormulario((TipoFormularioVO) tipoFormulario.toVO(1, false));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return tipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TipoFormularioVO createTipoFormulario(UserContext userContext, TipoFormularioVO tipoFormularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			tipoFormularioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			TipoFormulario tipoFormulario = new TipoFormulario();

			tipoFormulario.setDescripcion(tipoFormularioVO.getDescripcion());
			tipoFormulario.setCodigo(tipoFormularioVO.getCodigo());

			tipoFormulario.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			tipoFormulario = FrmFormularioManager.getInstance().createTipoFormulario(tipoFormulario);

			if (tipoFormulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				tipoFormularioVO =  (TipoFormularioVO) tipoFormulario.toVO(0,false);
			}
			tipoFormulario.passErrorMessages(tipoFormularioVO);

			log.debug(funcName + ": exit");
			return tipoFormularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TipoFormularioVO updateTipoFormulario(UserContext userContext, TipoFormularioVO tipoFormularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			tipoFormularioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			TipoFormulario tipoFormulario = TipoFormulario.getById(tipoFormularioVO.getId());

			if(!tipoFormularioVO.validateVersion(tipoFormulario.getFechaUltMdf())) return tipoFormularioVO;

			tipoFormulario.setDescripcion(tipoFormularioVO.getDescripcion());
			tipoFormulario.setCodigo(tipoFormularioVO.getCodigo());
			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			tipoFormulario = FrmFormularioManager.getInstance().updateTipoFormulario(tipoFormulario);

			if (tipoFormulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				tipoFormularioVO =  (TipoFormularioVO) tipoFormulario.toVO(0,false);
			}
			tipoFormulario.passErrorMessages(tipoFormularioVO);

			log.debug(funcName + ": exit");
			return tipoFormularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public TipoFormularioVO deleteTipoFormulario(UserContext userContext, TipoFormularioVO tipoFormularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			tipoFormularioVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			TipoFormulario tipoFormulario = TipoFormulario.getById(tipoFormularioVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad de otro bean
			tipoFormulario = FrmFormularioManager.getInstance().deleteTipoFormulario(tipoFormulario);

			if (tipoFormulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				tipoFormularioVO =  (TipoFormularioVO) tipoFormulario.toVO(0,false);
			}
			tipoFormulario.passErrorMessages(tipoFormularioVO);

			log.debug(funcName + ": exit");
			return tipoFormularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			try { tx.rollback(); } catch (Exception ee) {}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	//-----------------------------------------------------SERIE-----------------------------------------------------------

	public SerieSearchPage getSerieSearchPageInit(UserContext userContext) throws DemodaServiceException {		
		return new SerieSearchPage();
	}
	//
	public SerieSearchPage getSerieSearchPageResult(UserContext userContext, SerieSearchPage serieSearchPage) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			serieSearchPage.clearError();

			//Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<Serie> listSerie = ApmDAOFactory.getSerieDAO().getBySearchPage(serieSearchPage);  

			//Aqui se podria iterar la lista de BO para setear banderas en VOs y otras cosas del negocio.

			//Aqui pasamos BO a VO
			serieSearchPage.setListResult(ListUtilBean.toVO(listSerie,0));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return serieSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SerieAdapter getSerieAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			Serie serie = Serie.getById(commonKey.getId());

			SerieAdapter serieAdapter = new SerieAdapter();
			serieAdapter.setSerie((SerieVO) serie.toVO(1));

			log.debug(funcName + ": exit");
			return serieAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SerieAdapter getSerieAdapterForCreate(UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			SerieAdapter serieAdapter = new SerieAdapter();

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return serieAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public SerieAdapter getSerieAdapterParam(UserContext userContext, SerieAdapter serieAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			serieAdapter.clearError();

			// Logica del param



			log.debug(funcName + ": exit");
			return serieAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public SerieAdapter getSerieAdapterForUpdate(UserContext userContext, CommonKey commonKeySerie) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			Serie serie = Serie.getById(commonKeySerie.getId());

			SerieAdapter serieAdapter = new SerieAdapter();
			serieAdapter.setSerie((SerieVO) serie.toVO(1));

			// Seteo la lista para combo, valores, etc

			log.debug(funcName + ": exit");
			return serieAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SerieVO createSerie(UserContext userContext, SerieVO serieVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			serieVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Serie serie = new Serie();

			serie.setCodigo(serieVO.getCodigo());

			serie.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			serie = FrmFormularioManager.getInstance().createSerie(serie);

			if (serie.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				serieVO =  (SerieVO) serie.toVO(0,false);
			}
			serie.passErrorMessages(serieVO);

			log.debug(funcName + ": exit");
			return serieVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SerieVO updateSerie(UserContext userContext, SerieVO serieVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			serieVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Serie serie = Serie.getById(serieVO.getId());

			if(!serieVO.validateVersion(serie.getFechaUltMdf())) return serieVO;

			serie.setCodigo(serieVO.getCodigo());
			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			serie = FrmFormularioManager.getInstance().updateSerie(serie);

			if (serie.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				serieVO =  (SerieVO) serie.toVO(0,false);
			}
			serie.passErrorMessages(serieVO);

			log.debug(funcName + ": exit");
			return serieVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public SerieVO deleteSerie(UserContext userContext, SerieVO serieVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			serieVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			Serie serie = Serie.getById(serieVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad de otro bean
			serie = FrmFormularioManager.getInstance().deleteSerie(serie);

			if (serie.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				serieVO =  (SerieVO) serie.toVO(0,false);
			}
			serie.passErrorMessages(serieVO);

			log.debug(funcName + ": exit");
			return serieVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			try { tx.rollback(); } catch (Exception ee) {}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	//--------------------------------------MOTIVOCIERRETIPOFORMULARIO---------------------------------------------------------

	public MotivoCierreTipoFormularioSearchPage getMotivoCierreTipoFormularioSearchPageInit(UserContext userContext) throws DemodaServiceException {		
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			MotivoCierreTipoFormularioSearchPage motivoCierreTipoFormularioSearchPage = new MotivoCierreTipoFormularioSearchPage();


			motivoCierreTipoFormularioSearchPage.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),0,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_TODOS)));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return motivoCierreTipoFormularioSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}

	}

	public MotivoCierreTipoFormularioSearchPage getMotivoCierreTipoFormularioSearchPageResult(UserContext userContext, MotivoCierreTipoFormularioSearchPage motivoCierreTipoFormularioSearchPage) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			motivoCierreTipoFormularioSearchPage.clearError();

			//Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<MotivoCierreTipoFormulario> listMotivoCierreTipoFormulario = ApmDAOFactory.getMotivoCierreTipoFormularioDAO().getBySearchPage(motivoCierreTipoFormularioSearchPage);  

			//Aqui se podria iterar la lista de BO para setear banderas en VOs y otras cosas del negocio.

			//Aqui pasamos BO a VO
			motivoCierreTipoFormularioSearchPage.setListResult(ListUtilBean.toVO(listMotivoCierreTipoFormulario,1,false));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return motivoCierreTipoFormularioSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public MotivoCierreTipoFormularioAdapter getMotivoCierreTipoFormularioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			MotivoCierreTipoFormulario motivoCierreTipoFormulario = MotivoCierreTipoFormulario.getById(commonKey.getId());

			MotivoCierreTipoFormularioAdapter motivoCierreTipoFormularioAdapter = new MotivoCierreTipoFormularioAdapter();
			motivoCierreTipoFormularioAdapter.setMotivoCierreTipoFormulario((MotivoCierreTipoFormularioVO) motivoCierreTipoFormulario.toVO(1));

			log.debug(funcName + ": exit");
			return motivoCierreTipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public MotivoCierreTipoFormularioAdapter getMotivoCierreTipoFormularioAdapterForCreate(UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			MotivoCierreTipoFormularioAdapter motivoCierreTipoFormularioAdapter = new MotivoCierreTipoFormularioAdapter();

			motivoCierreTipoFormularioAdapter.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),1,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return motivoCierreTipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public MotivoCierreTipoFormularioAdapter getMotivoCierreTipoFormularioAdapterParam(UserContext userContext, MotivoCierreTipoFormularioAdapter motivoCierreTipoFormularioAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			motivoCierreTipoFormularioAdapter.clearError();

			// Logica del param



			log.debug(funcName + ": exit");
			return motivoCierreTipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public MotivoCierreTipoFormularioAdapter getMotivoCierreTipoFormularioAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			MotivoCierreTipoFormularioAdapter motivoCierreTipoFormularioAdapter = new MotivoCierreTipoFormularioAdapter();


			MotivoCierreTipoFormulario motivoCierreTipoFormulario = MotivoCierreTipoFormulario.getById(commonKey.getId());
			motivoCierreTipoFormularioAdapter.setMotivoCierreTipoFormulario(
					(MotivoCierreTipoFormularioVO) motivoCierreTipoFormulario.toVO(1,false));
			// Seteo la lista para combo, valores, etc
			motivoCierreTipoFormularioAdapter.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),1,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));


			log.debug(funcName + ": exit");
			return motivoCierreTipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public MotivoCierreTipoFormularioVO createMotivoCierreTipoFormulario(UserContext userContext, MotivoCierreTipoFormularioVO motivoCierreTipoFormularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			motivoCierreTipoFormularioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			MotivoCierreTipoFormulario motivoCierreTipoFormulario = new MotivoCierreTipoFormulario();

			motivoCierreTipoFormulario.setDescripcion(motivoCierreTipoFormularioVO.getDescripcion());
			TipoFormulario tipoFormulario = TipoFormulario.getByIdNull(motivoCierreTipoFormularioVO.getTipoFormulario().getId());
			motivoCierreTipoFormulario.setTipoFormulario(tipoFormulario);

			motivoCierreTipoFormulario.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			motivoCierreTipoFormulario = FrmFormularioManager.getInstance().createMotivoCierreTipoFormulario(motivoCierreTipoFormulario);

			if (motivoCierreTipoFormulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				motivoCierreTipoFormularioVO =  (MotivoCierreTipoFormularioVO) motivoCierreTipoFormulario.toVO(0,false);
			}
			motivoCierreTipoFormulario.passErrorMessages(motivoCierreTipoFormularioVO);

			log.debug(funcName + ": exit");
			return motivoCierreTipoFormularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public MotivoCierreTipoFormularioVO updateMotivoCierreTipoFormulario(UserContext userContext, MotivoCierreTipoFormularioVO motivoCierreTipoFormularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			motivoCierreTipoFormularioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			MotivoCierreTipoFormulario motivoCierreTipoFormulario = MotivoCierreTipoFormulario.getById(motivoCierreTipoFormularioVO.getId());

			if(!motivoCierreTipoFormularioVO.validateVersion(motivoCierreTipoFormulario.getFechaUltMdf())) return motivoCierreTipoFormularioVO;

			motivoCierreTipoFormulario.setDescripcion(motivoCierreTipoFormularioVO.getDescripcion());
			TipoFormulario tipoFormulario = TipoFormulario.getByIdNull(motivoCierreTipoFormularioVO.getTipoFormulario().getId());
			motivoCierreTipoFormulario.setTipoFormulario(tipoFormulario);

			motivoCierreTipoFormulario.setEstado(Estado.ACTIVO.getId());
			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			motivoCierreTipoFormulario = FrmFormularioManager.getInstance().updateMotivoCierreTipoFormulario(motivoCierreTipoFormulario);

			if (motivoCierreTipoFormulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				motivoCierreTipoFormularioVO =  (MotivoCierreTipoFormularioVO) motivoCierreTipoFormulario.toVO(0,false);
			}
			motivoCierreTipoFormulario.passErrorMessages(motivoCierreTipoFormularioVO);

			log.debug(funcName + ": exit");
			return motivoCierreTipoFormularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public MotivoCierreTipoFormularioVO deleteMotivoCierreTipoFormulario(UserContext userContext, MotivoCierreTipoFormularioVO motivoCierreTipoFormularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			motivoCierreTipoFormularioVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			MotivoCierreTipoFormulario motivoCierreTipoFormulario = MotivoCierreTipoFormulario.getById(motivoCierreTipoFormularioVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad de otro bean
			motivoCierreTipoFormulario = FrmFormularioManager.getInstance().deleteMotivoCierreTipoFormulario(motivoCierreTipoFormulario);

			if (motivoCierreTipoFormulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				motivoCierreTipoFormularioVO =  (MotivoCierreTipoFormularioVO) motivoCierreTipoFormulario.toVO(0,false);
			}
			motivoCierreTipoFormulario.passErrorMessages(motivoCierreTipoFormularioVO);

			log.debug(funcName + ": exit");
			return motivoCierreTipoFormularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			try { tx.rollback(); } catch (Exception ee) {}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}



	//--------------------------------------MOTIVOANULACIONTIPOFORMULARIO---------------------------------------------------------

	public MotivoAnulacionTipoFormularioSearchPage getMotivoAnulacionTipoFormularioSearchPageInit(UserContext userContext) throws DemodaServiceException {		
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			MotivoAnulacionTipoFormularioSearchPage motivoAnulacionTipoFormularioSearchPage = new MotivoAnulacionTipoFormularioSearchPage();


			motivoAnulacionTipoFormularioSearchPage.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),0,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_TODOS)));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return motivoAnulacionTipoFormularioSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}

	}

	public MotivoAnulacionTipoFormularioSearchPage getMotivoAnulacionTipoFormularioSearchPageResult(UserContext userContext, MotivoAnulacionTipoFormularioSearchPage motivoAnulacionTipoFormularioSearchPage) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			motivoAnulacionTipoFormularioSearchPage.clearError();

			//Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<MotivoAnulacionTipoFormulario> listMotivoAnulacionTipoFormulario = ApmDAOFactory.getMotivoAnulacionTipoFormularioDAO().getBySearchPage(motivoAnulacionTipoFormularioSearchPage);  

			//Aqui se podria iterar la lista de BO para setear banderas en VOs y otras cosas del negocio.

			//Aqui pasamos BO a VO
			motivoAnulacionTipoFormularioSearchPage.setListResult(ListUtilBean.toVO(listMotivoAnulacionTipoFormulario,1,false));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return motivoAnulacionTipoFormularioSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public MotivoAnulacionTipoFormularioAdapter getMotivoAnulacionTipoFormularioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			MotivoAnulacionTipoFormulario motivoAnulacionTipoFormulario = MotivoAnulacionTipoFormulario.getById(commonKey.getId());

			MotivoAnulacionTipoFormularioAdapter motivoAnulacionTipoFormularioAdapter = new MotivoAnulacionTipoFormularioAdapter();
			motivoAnulacionTipoFormularioAdapter.setMotivoAnulacionTipoFormulario((MotivoAnulacionTipoFormularioVO) motivoAnulacionTipoFormulario.toVO(1));

			log.debug(funcName + ": exit");
			return motivoAnulacionTipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public MotivoAnulacionTipoFormularioAdapter getMotivoAnulacionTipoFormularioAdapterForCreate(UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			MotivoAnulacionTipoFormularioAdapter motivoAnulacionTipoFormularioAdapter = new MotivoAnulacionTipoFormularioAdapter();

			motivoAnulacionTipoFormularioAdapter.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),1,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return motivoAnulacionTipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public MotivoAnulacionTipoFormularioAdapter getMotivoAnulacionTipoFormularioAdapterParam(UserContext userContext, MotivoAnulacionTipoFormularioAdapter motivoAnulacionTipoFormularioAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			motivoAnulacionTipoFormularioAdapter.clearError();

			// Logica del param



			log.debug(funcName + ": exit");
			return motivoAnulacionTipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public MotivoAnulacionTipoFormularioAdapter getMotivoAnulacionTipoFormularioAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			MotivoAnulacionTipoFormularioAdapter motivoAnulacionTipoFormularioAdapter = new MotivoAnulacionTipoFormularioAdapter();


			MotivoAnulacionTipoFormulario motivoAnulacionTipoFormulario = MotivoAnulacionTipoFormulario.getById(commonKey.getId());
			motivoAnulacionTipoFormularioAdapter.setMotivoAnulacionTipoFormulario(
					(MotivoAnulacionTipoFormularioVO) motivoAnulacionTipoFormulario.toVO(1,false));
			// Seteo la lista para combo, valores, etc
			motivoAnulacionTipoFormularioAdapter.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),1,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));


			log.debug(funcName + ": exit");
			return motivoAnulacionTipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public MotivoAnulacionTipoFormularioVO createMotivoAnulacionTipoFormulario(UserContext userContext, MotivoAnulacionTipoFormularioVO motivoAnulacionTipoFormularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			motivoAnulacionTipoFormularioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			MotivoAnulacionTipoFormulario motivoAnulacionTipoFormulario = new MotivoAnulacionTipoFormulario();

			motivoAnulacionTipoFormulario.setDescripcion(motivoAnulacionTipoFormularioVO.getDescripcion());
			TipoFormulario tipoFormulario = TipoFormulario.getByIdNull(motivoAnulacionTipoFormularioVO.getTipoFormulario().getId());
			motivoAnulacionTipoFormulario.setTipoFormulario(tipoFormulario);

			motivoAnulacionTipoFormulario.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			motivoAnulacionTipoFormulario = FrmFormularioManager.getInstance().createMotivoAnulacionTipoFormulario(motivoAnulacionTipoFormulario);

			if (motivoAnulacionTipoFormulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				motivoAnulacionTipoFormularioVO =  (MotivoAnulacionTipoFormularioVO) motivoAnulacionTipoFormulario.toVO(0,false);
			}
			motivoAnulacionTipoFormulario.passErrorMessages(motivoAnulacionTipoFormularioVO);

			log.debug(funcName + ": exit");
			return motivoAnulacionTipoFormularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public MotivoAnulacionTipoFormularioVO updateMotivoAnulacionTipoFormulario(UserContext userContext, MotivoAnulacionTipoFormularioVO motivoAnulacionTipoFormularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			motivoAnulacionTipoFormularioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			MotivoAnulacionTipoFormulario motivoAnulacionTipoFormulario = MotivoAnulacionTipoFormulario.getById(motivoAnulacionTipoFormularioVO.getId());

			if(!motivoAnulacionTipoFormularioVO.validateVersion(motivoAnulacionTipoFormulario.getFechaUltMdf())) return motivoAnulacionTipoFormularioVO;

			motivoAnulacionTipoFormulario.setDescripcion(motivoAnulacionTipoFormularioVO.getDescripcion());
			TipoFormulario tipoFormulario = TipoFormulario.getByIdNull(motivoAnulacionTipoFormularioVO.getTipoFormulario().getId());
			motivoAnulacionTipoFormulario.setTipoFormulario(tipoFormulario);

			motivoAnulacionTipoFormulario.setEstado(Estado.ACTIVO.getId());
			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			motivoAnulacionTipoFormulario = FrmFormularioManager.getInstance().updateMotivoAnulacionTipoFormulario(motivoAnulacionTipoFormulario);

			if (motivoAnulacionTipoFormulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				motivoAnulacionTipoFormularioVO =  (MotivoAnulacionTipoFormularioVO) motivoAnulacionTipoFormulario.toVO(0,false);
			}
			motivoAnulacionTipoFormulario.passErrorMessages(motivoAnulacionTipoFormularioVO);

			log.debug(funcName + ": exit");
			return motivoAnulacionTipoFormularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public MotivoAnulacionTipoFormularioVO deleteMotivoAnulacionTipoFormulario(UserContext userContext, MotivoAnulacionTipoFormularioVO motivoAnulacionTipoFormularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			motivoAnulacionTipoFormularioVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			MotivoAnulacionTipoFormulario motivoAnulacionTipoFormulario = MotivoAnulacionTipoFormulario.getById(motivoAnulacionTipoFormularioVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad de otro bean
			motivoAnulacionTipoFormulario = FrmFormularioManager.getInstance().deleteMotivoAnulacionTipoFormulario(motivoAnulacionTipoFormulario);

			if (motivoAnulacionTipoFormulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				motivoAnulacionTipoFormularioVO =  (MotivoAnulacionTipoFormularioVO) motivoAnulacionTipoFormulario.toVO(0,false);
			}
			motivoAnulacionTipoFormulario.passErrorMessages(motivoAnulacionTipoFormularioVO);

			log.debug(funcName + ": exit");
			return motivoAnulacionTipoFormularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			try { tx.rollback(); } catch (Exception ee) {}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}


	//--------------------------------------NUMERACION---------------------------------------------------------

	public NumeracionSearchPage getNumeracionSearchPageInit(UserContext userContext) throws DemodaServiceException {		
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			NumeracionSearchPage numeracionSearchPage = new NumeracionSearchPage();


			numeracionSearchPage.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),0,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_TODOS)));

			numeracionSearchPage.setListSerie(
					ListUtilBean.toVO(Serie.getListActivos(),0,
							false, new SerieVO(-1, StringUtil.SELECT_OPCION_TODOS)));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return numeracionSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}

	}

	public NumeracionSearchPage getNumeracionSearchPageResult(UserContext userContext, NumeracionSearchPage numeracionSearchPage) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			numeracionSearchPage.clearError();

			//Aqui realizar validaciones

			// Aqui obtiene lista de BOs
			List<Numeracion> listNumeracion = ApmDAOFactory.getNumeracionDAO().getBySearchPage(numeracionSearchPage);  

			//Aqui se podria iterar la lista de BO para setear banderas en VOs y otras cosas del negocio.

			//Aqui pasamos BO a VO
			numeracionSearchPage.setListResult(ListUtilBean.toVO(listNumeracion,1,false));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return numeracionSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NumeracionAdapter getNumeracionAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			Numeracion numeracion = Numeracion.getById(commonKey.getId());

			NumeracionAdapter numeracionAdapter = new NumeracionAdapter();
			numeracionAdapter.setNumeracion((NumeracionVO) numeracion.toVO(1));

			log.debug(funcName + ": exit");
			return numeracionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NumeracionAdapter getNumeracionAdapterForCreate(UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			NumeracionAdapter numeracionAdapter = new NumeracionAdapter();

			numeracionAdapter.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),1,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			numeracionAdapter.setListSerie(
					ListUtilBean.toVO(Serie.getListActivos(),0,
							false, new SerieVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));



			// Seteo de banderas

			// Seteo la listas para combos, etc

			log.debug(funcName + ": exit");
			return numeracionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public NumeracionAdapter getNumeracionAdapterParam(UserContext userContext, NumeracionAdapter numeracionAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			numeracionAdapter.clearError();

			// Logica del param



			log.debug(funcName + ": exit");
			return numeracionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public NumeracionAdapter getNumeracionAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			NumeracionAdapter numeracionAdapter = new NumeracionAdapter();


			Numeracion numeracion = Numeracion.getById(commonKey.getId());
			numeracionAdapter.setNumeracion(
					(NumeracionVO) numeracion.toVO(1,false));


			// Seteo la lista para combo, valores, etc
			numeracionAdapter.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),1,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			numeracionAdapter.setListSerie(
					ListUtilBean.toVO(Serie.getListActivos(),0,
							false, new SerieVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));




			log.debug(funcName + ": exit");
			return numeracionAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NumeracionVO createNumeracion(UserContext userContext, NumeracionVO numeracionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			numeracionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Numeracion numeracion = new Numeracion();

			Serie serie = Serie.getByIdNull(numeracionVO.getSerie().getId());
			numeracion.setSerie(serie);
			TipoFormulario tipoFormulario = TipoFormulario.getByIdNull(numeracionVO.getTipoFormulario().getId());
			numeracion.setTipoFormulario(tipoFormulario);
			numeracion.setValorDesde(numeracionVO.getValorDesde());
			numeracion.setValorHasta(numeracionVO.getValorHasta());
			numeracion.setValorRestante(999999 - (numeracionVO.getValorDesde()-numeracionVO.getValorDesde()));

			numeracion.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			numeracion = FrmFormularioManager.getInstance().createNumeracion(numeracion);

			if (numeracion.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				numeracionVO =  (NumeracionVO) numeracion.toVO(0,false);
			}
			numeracion.passErrorMessages(numeracionVO);

			log.debug(funcName + ": exit");
			return numeracionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NumeracionVO updateNumeracion(UserContext userContext, NumeracionVO numeracionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			numeracionVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			Numeracion numeracion = Numeracion.getById(numeracionVO.getId());

			if(!numeracionVO.validateVersion(numeracion.getFechaUltMdf())) return numeracionVO;


			Serie serie = Serie.getByIdNull(numeracionVO.getSerie().getId());
			numeracion.setSerie(serie);
			TipoFormulario tipoFormulario = TipoFormulario.getByIdNull(numeracionVO.getTipoFormulario().getId());
			numeracion.setTipoFormulario(tipoFormulario);
			numeracion.setValorDesde(numeracionVO.getValorDesde());
			numeracion.setValorHasta(numeracionVO.getValorHasta());


			numeracion.setEstado(Estado.ACTIVO.getId());
			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			numeracion = FrmFormularioManager.getInstance().updateNumeracion(numeracion);

			if (numeracion.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				numeracionVO =  (NumeracionVO) numeracion.toVO(0,false);
			}
			numeracion.passErrorMessages(numeracionVO);

			log.debug(funcName + ": exit");
			return numeracionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public NumeracionVO deleteNumeracion(UserContext userContext, NumeracionVO numeracionVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			numeracionVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			Numeracion numeracion = Numeracion.getById(numeracionVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad de otro bean
			numeracion = FrmFormularioManager.getInstance().deleteNumeracion(numeracion);

			if (numeracion.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				numeracionVO =  (NumeracionVO) numeracion.toVO(0,false);
			}
			numeracion.passErrorMessages(numeracionVO);

			log.debug(funcName + ": exit");
			return numeracionVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			try { tx.rollback(); } catch (Exception ee) {}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	//--------------------------------------ESTADOTIPOFORMULARIO---------------------------------------------------------

	public EstadoTipoFormularioSearchPage getEstadoTipoFormularioSearchPageInit(UserContext userContext) throws DemodaServiceException {		
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			EstadoTipoFormularioSearchPage estadoTipoFormularioSearchPage = new EstadoTipoFormularioSearchPage();

			estadoTipoFormularioSearchPage.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),0,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_TODOS)));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return estadoTipoFormularioSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}

	}

	public EstadoTipoFormularioSearchPage getEstadoTipoFormularioSearchPageResult(UserContext userContext, EstadoTipoFormularioSearchPage estadoTipoFormularioSearchPage) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			estadoTipoFormularioSearchPage.clearError();

			// Aqui obtiene lista de BOs
			List<EstadoTipoFormulario> listEstadoTipoFormulario = FrmDAOFactory.getEstadoTipoFormularioDAO().getBySearchPage(estadoTipoFormularioSearchPage);  

			//Aqui pasamos BO a VO
			estadoTipoFormularioSearchPage.setListResult(ListUtilBean.toVO(listEstadoTipoFormulario,1,false));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return estadoTipoFormularioSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public EstadoTipoFormularioAdapter getEstadoTipoFormularioAdapterForView(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			EstadoTipoFormulario estadoTipoFormulario = EstadoTipoFormulario.getById(commonKey.getId());

			EstadoTipoFormularioAdapter estadoTipoFormularioAdapter = new EstadoTipoFormularioAdapter();
			estadoTipoFormularioAdapter.setEstadoTipoFormulario((EstadoTipoFormularioVO) estadoTipoFormulario.toVO(1));

			log.debug(funcName + ": exit");
			return estadoTipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public EstadoTipoFormularioAdapter getEstadoTipoFormularioAdapterForCreate(UserContext userContext) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			EstadoTipoFormularioAdapter estadoTipoFormularioAdapter = new EstadoTipoFormularioAdapter();

			estadoTipoFormularioAdapter.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),1,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			log.debug(funcName + ": exit");
			return estadoTipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public EstadoTipoFormularioAdapter getEstadoTipoFormularioAdapterParam(UserContext userContext, EstadoTipoFormularioAdapter estadoTipoFormularioAdapter) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			estadoTipoFormularioAdapter.clearError();

			log.debug(funcName + ": exit");
			return estadoTipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}	
	}

	public EstadoTipoFormularioAdapter getEstadoTipoFormularioAdapterForUpdate(UserContext userContext, CommonKey commonKey) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession(); 

			EstadoTipoFormularioAdapter estadoTipoFormularioAdapter = new EstadoTipoFormularioAdapter();


			EstadoTipoFormulario estadoTipoFormulario = EstadoTipoFormulario.getById(commonKey.getId());
			estadoTipoFormularioAdapter.setEstadoTipoFormulario(
					(EstadoTipoFormularioVO) estadoTipoFormulario.toVO(1,false));


			// Seteo la lista para combo, valores, etc
			estadoTipoFormularioAdapter.setListTipoFormulario(
					ListUtilBean.toVO(TipoFormulario.getListActivos(),1,
							false, new TipoFormularioVO(-1, StringUtil.SELECT_OPCION_SELECCIONAR)));

			log.debug(funcName + ": exit");
			return estadoTipoFormularioAdapter;
		} catch (Exception e) {
			log.error("Service Error: ",  e);
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public EstadoTipoFormularioVO createEstadoTipoFormulario(UserContext userContext, EstadoTipoFormularioVO estadoTipoFormularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			estadoTipoFormularioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			EstadoTipoFormulario estadoTipoFormulario = new EstadoTipoFormulario();

			TipoFormulario tipoFormulario = TipoFormulario.getByIdNull(estadoTipoFormularioVO.getTipoFormulario().getId());
			estadoTipoFormulario.setTipoFormulario(tipoFormulario);
			estadoTipoFormulario.setDescripcion(estadoTipoFormularioVO.getDescripcion());

			estadoTipoFormulario.setEstado(Estado.ACTIVO.getId());

			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			estadoTipoFormulario = FrmFormularioManager.getInstance().createEstadoTipoFormulario(estadoTipoFormulario);

			if (estadoTipoFormulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				estadoTipoFormularioVO =  (EstadoTipoFormularioVO) estadoTipoFormulario.toVO(0,false);
			}
			estadoTipoFormulario.passErrorMessages(estadoTipoFormularioVO);

			log.debug(funcName + ": exit");
			return estadoTipoFormularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public EstadoTipoFormularioVO updateEstadoTipoFormulario(UserContext userContext, EstadoTipoFormularioVO estadoTipoFormularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			estadoTipoFormularioVO.clearErrorMessages();

			// Copiado de propiadades de VO al BO
			EstadoTipoFormulario estadoTipoFormulario = EstadoTipoFormulario.getById(estadoTipoFormularioVO.getId());

			if(!estadoTipoFormularioVO.validateVersion(estadoTipoFormulario.getFechaUltMdf())) return estadoTipoFormularioVO;

			TipoFormulario tipoFormulario = TipoFormulario.getByIdNull(estadoTipoFormularioVO.getTipoFormulario().getId());
			estadoTipoFormulario.setTipoFormulario(tipoFormulario);
			estadoTipoFormulario.setDescripcion(estadoTipoFormularioVO.getDescripcion());

			estadoTipoFormulario.setEstado(Estado.ACTIVO.getId());
			// Aqui la creacion esta delegada en el manager, pero puede corresponder a un Bean contenedor
			estadoTipoFormulario = FrmFormularioManager.getInstance().updateEstadoTipoFormulario(estadoTipoFormulario);

			if (estadoTipoFormulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				estadoTipoFormularioVO =  (EstadoTipoFormularioVO) estadoTipoFormulario.toVO(0,false);
			}
			estadoTipoFormulario.passErrorMessages(estadoTipoFormularioVO);

			log.debug(funcName + ": exit");
			return estadoTipoFormularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public EstadoTipoFormularioVO deleteEstadoTipoFormulario(UserContext userContext, EstadoTipoFormularioVO estadoTipoFormularioVO) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();

		Session session = null;
		Transaction tx = null;

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();
			estadoTipoFormularioVO.clearErrorMessages();

			// Se recupera el Bean dado su id
			EstadoTipoFormulario estadoTipoFormulario = EstadoTipoFormulario.getById(estadoTipoFormularioVO.getId());

			// Se le delega al Manager el borrado, pero puse ser responsabilidad de otro bean
			estadoTipoFormulario = FrmFormularioManager.getInstance().deleteEstadoTipoFormulario(estadoTipoFormulario);

			if (estadoTipoFormulario.hasError()) {
				tx.rollback();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.rollback");}
			} else {
				tx.commit();
				if(log.isDebugEnabled()){log.debug(funcName + ": tx.commit");}
				estadoTipoFormularioVO =  (EstadoTipoFormularioVO) estadoTipoFormulario.toVO(0,false);
			}
			estadoTipoFormulario.passErrorMessages(estadoTipoFormularioVO);

			log.debug(funcName + ": exit");
			return estadoTipoFormularioVO;
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			try { tx.rollback(); } catch (Exception ee) {}
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

}