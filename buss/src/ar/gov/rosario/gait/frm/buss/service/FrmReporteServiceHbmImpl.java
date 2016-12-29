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
package ar.gov.rosario.gait.frm.buss.service;

/**
 * Implementacion de servicios del submodulo Reporte del modulo Frm
 * @author tecso
 */

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

import ar.gov.rosario.gait.apm.buss.bean.AplicacionPerfil;
import ar.gov.rosario.gait.apm.buss.bean.Campo;
import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.def.buss.bean.Area;
import ar.gov.rosario.gait.def.buss.dao.DefDAOFactory;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import ar.gov.rosario.gait.frm.buss.bean.Formulario;
import ar.gov.rosario.gait.frm.buss.dao.FrmDAOFactory;
import ar.gov.rosario.gait.frm.iface.model.ReporteFormularioSearchPage;
import ar.gov.rosario.gait.frm.iface.service.IFrmReporteService;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import coop.tecso.demoda.buss.helper.ListUtilBean;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.SiNo;
import coop.tecso.demoda.iface.model.UserContext;
import coop.tecso.demoda.iface.model.ValueVO;

public class FrmReporteServiceHbmImpl implements IFrmReporteService {
	private Logger log = Logger.getLogger(FrmReporteServiceHbmImpl.class);

	// ---> ABM ReporteFormulario 	
	public ReporteFormularioSearchPage getReporteFormularioSearchPageInit(UserContext userContext) 
			throws DemodaServiceException {		
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			ReporteFormularioSearchPage reporteFormularioSearchPage = new ReporteFormularioSearchPage();

			List<AreaVO> listArea;
			List<AplicacionPerfilVO> listAplicacionPerfil;
			// Seteo de banderas
			if (userContext.getEsDGI()) {
				// Listado de Areas
				listArea = ListUtilBean.toVO(Area.getListActivos(), 0, false, 
						new AreaVO(-1, StringUtil.SELECT_OPCION_TODAS));
				// Listado de Formularios
				listAplicacionPerfil = ListUtilBean.toVO(
						AplicacionPerfil.getListActivos(), 0, false, 
						new AplicacionPerfilVO(-1, StringUtil.SELECT_OPCION_TODOS));
			} else {
				// Area
				Area area = Area.getById(userContext.getIdArea());
				listArea = new ArrayList<>();
				listArea.add((AreaVO) area.toVO(0,false));
				// Listado de Formularios
				listAplicacionPerfil = ListUtilBean.toVO(DefDAOFactory.getDireccionAplicacionPerfilDAO().
						getListAplicacionPerfilByDireccion(area.getDireccion()), 0, false);
			}

			reporteFormularioSearchPage.setListArea(listArea);
			reporteFormularioSearchPage.setListAplicacionPerfil(listAplicacionPerfil);

			List<SiNo> listSiNo = new ArrayList<>();
			listSiNo.add(SiNo.OpcionTodo);
			listSiNo.add(SiNo.SI);
			listSiNo.add(SiNo.NO);
			reporteFormularioSearchPage.setListSiNo(listSiNo);

			// Tipo de Reporte
			List<ValueVO> listTipoReporte = new ArrayList<>();
			listTipoReporte.add(new ValueVO(1, "Actas sin Imprimir"));
			listTipoReporte.add(new ValueVO(2, "Actas ya Impresas"));

			reporteFormularioSearchPage.setListTipoReporte(listTipoReporte);

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return reporteFormularioSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	public ReporteFormularioSearchPage getReporteFormularioSearchPageResult(UserContext userContext, ReporteFormularioSearchPage reporteFormularioSearchPage) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		if (log.isDebugEnabled()) log.debug(funcName + ": enter");

		try {
			DemodaUtil.setCurrentUserContext(userContext);
			GaitHibernateUtil.currentSession();

			reporteFormularioSearchPage.clearError();

			// Cantidad de Registros
			reporteFormularioSearchPage.setRecsByPage(10L);

			// Aqui obtiene lista de BOs
			List<Formulario> listReporteFormulario = FrmDAOFactory.getReporteFormularioDAO().getBySearchPage(reporteFormularioSearchPage);  

			//Aqui pasamos BO a VO
			reporteFormularioSearchPage.setListResult(ListUtilBean.toVO(listReporteFormulario,2,false));

			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return reporteFormularioSearchPage;
		} catch (Exception e) {
			log.error("ServiceError en: ", e);
			throw new DemodaServiceException(e); 
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}


	public ReporteFormularioSearchPage imprimirReporteFormulario(UserContext userContext,
			ReporteFormularioSearchPage reporteFormularioSearchPage) throws DemodaServiceException {
		String funcName = DemodaUtil.currentMethodName();
		Session session = null;
		Transaction tx = null; 

		if (log.isDebugEnabled()) log.debug(funcName + ": enter");
		try {
			DemodaUtil.setCurrentUserContext(userContext);
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();

			reporteFormularioSearchPage.clearError();

			List<Formulario> listFormulario = new ArrayList<>();

			// Busqueda
			reporteFormularioSearchPage.setPaged(false);
			listFormulario = FrmDAOFactory.getReporteFormularioDAO().getBySearchPage(reporteFormularioSearchPage);
			reporteFormularioSearchPage.setPaged(true);
			
			// HEADER
			AplicacionPerfil tipoFormularioDef = 
					AplicacionPerfil.getByIdNull(reporteFormularioSearchPage.getFormulario().getTipoFormularioDef().getId());
			String tipoFormulario = tipoFormularioDef == null ? "" : tipoFormularioDef.getDescripcion();
			
			HtmlReportHelper header = new HtmlReportHelper();
			header.startTable("style='width:100%; border: 0px;font-size: 8pt;'");
			header.addRawHTML("<tr>");
			header.addRawHTML("<td rowspan='3'style='font-size: 12pt;'>");
			header.addRawHTML("<b>MUNICIPALIDAD DE ROSARIO - AGENCIA MUNICIPAL DE SEGURIDAD VIAL</b>");
			header.addRawHTML("</td>");
			header.addRawHTML(String.format("<td align='right'><b>Fecha: </b></td><td>%s</td>",
					DateUtil.formatDate(new Date(), DateUtil.ddSMMSYYYY_MASK)));
			header.addRawHTML("</tr>");
			header.addRawHTML("<tr>");
			header.addRawHTML(String.format("<td align='right'><b>Hora: </b></td><td>%s</td>", 
					DateUtil.formatDate(new Date(), DateUtil.HOUR_MINUTE_MASK)));
			header.addRawHTML("</tr>");
			header.addRawHTML("<tr>");
			header.addRawHTML(String.format("<td align='right'><b>Usuario: </b></td><td>%s</td>", userContext.getUserName()));
			header.addRawHTML("</tr>");
			// Filtros
			header.addRawHTML("<tr>");
			header.addRawHTML("<td colspan='3'>");
			header.addRawHTML("<table style='font-size: 10pt;border:0px dashed #000;width:100%;'>");
			
			header.addTableRow("",
					new String[]{ 
					String.format("<b>Fecha Cierre Desde: </b>%s",
							reporteFormularioSearchPage.getFechaCierreDesdeView()),
					String.format("<b>Fecha Cierre Hasta: </b>%s",
							reporteFormularioSearchPage.getFechaCierreHastaView()),
					String.format("<b>Fecha Impresion Desde: </b>%s",
							reporteFormularioSearchPage.getFechaImpresionDesdeView()),							
					String.format("<b>Fecha Impresion Hasta: </b>%s",
							reporteFormularioSearchPage.getFechaImpresionHastaView())							
							});			

			header.addRawHTML("<tr><td colspan='4'><table style='font-size: 10pt;width:100%;'>");

			
			Area areaDef = Area.getByIdNull(reporteFormularioSearchPage.getFormulario().getArea().getId());
			String area = areaDef == null ? "Todas" : areaDef.getDescripcion();

			String tipoReporte = "Todos";
			for(ValueVO itemTipoReporte: reporteFormularioSearchPage.getListTipoReporte()){
				if(itemTipoReporte.getId().equals(reporteFormularioSearchPage.getTipoReporte())){
					tipoReporte = itemTipoReporte.getDescripcion();
				}
			}

			String usuarioApm2 = reporteFormularioSearchPage.getFormulario().getUsuarioCierre().getUsername();
			String usuarioApm = usuarioApm2 == null ? "" : usuarioApm2;

			String tipoForm = tipoFormulario.equals("") ? "Todos" : tipoFormulario;
				
			header.addTableRow("style='width:33%;'",
					new String[]{ 
					String.format("<b>Area: </b>%s", area),
					String.format("<b>Tipo de Formulario: </b>%s", tipoForm),
					String.format("<b>Inspector: </b>%s", usuarioApm)
							});
			header.addRawHTML("</table></td></tr>");

			header.addRawHTML("<tr><td colspan='4'><table style='font-size: 10pt;width:100%;'>");
			header.addTableRow("style='width:33%;'",
					new String[]{ 
					String.format("<b>Tipo de Reporte: </b>%s", tipoReporte),
					String.format("<b>Numero: </b>%s", reporteFormularioSearchPage.getFormulario().getNumero()),							
					String.format("<b>Retiene Licencia: </b>%s",
							reporteFormularioSearchPage.getFormulario().getRetieneLicencia().getValue())							
							});			
			header.addRawHTML("</table></td></tr>");

			header.addRawHTML("</table></td></tr>");
			//End Filtros
			
			tipoFormulario = tipoFormulario.equals("")? "":tipoFormulario.toUpperCase() +" - ";
			
			header.addTableRow("style='font-size: 10pt;'",
					new String[]{String.format("<p><b>%s LISTADO DE ACTAS DE COMPROBACIÓN</b></p>",tipoFormulario)});
			header.endTable();
			
			// BODY
			HtmlReportHelper report = new HtmlReportHelper();
			// Inspector
			Integer numeroInsPectorRef = 0;
			String nombreInspector = "";
			for (Formulario formulario : listFormulario) {
				if(!numeroInsPectorRef.equals(formulario.getNumeroInspector())){
					// Cambio de Pagina
					if(numeroInsPectorRef != 0){
						// Footer
						report.endTable();
						report.startNewPage();
					}
					numeroInsPectorRef = formulario.getNumeroInspector();
					nombreInspector = formulario.getUsuarioCierre().getNombre();

					// HEADER
					report.addRawHTML(header.out());
					report.startTable("style='width:100%; border: 0px;'");
					report.addTableRow("style='font-size: 10pt; border: 0px;'",
							new String[]{
							String.format("<b>AREA: </b>%s", formulario.getArea().getDescripcion()),
							String.format("<b>INSPECTOR: </b>%s", nombreInspector),
							String.format("<b>CÓDIGO INSPECTOR: </b>%s", numeroInsPectorRef)
					});
					report.endTable();

					report.startTable("style='repeat-header: yes;repeat-footer:yes;width:100%;border: 0px solid black;border-collapse: collapse;'");
					report.addTableHeader("style='font-size: 8pt;border: 1px solid black;'",
							// Columns
							new String[]{"Acta","Fecha","Hora","Lugar","Dominio","Infracción","Documento","Conductor","RL","F"});
					
					// Footer
					report.addRawHTML("<tfoot style='width:100%;border: 0px;!important'>");
					String extra = "style='font-size: 8pt;' colspan='10'";
					report.addTableRow(extra, new String[]{""});
					report.addTableRow(extra, new String[]{"<b>Referencia Firma Imputado (F): </b>"});
					report.addTableRow(extra ,new String[]{"	(1) NO ESTÁ PRESENTE"});
					report.addTableRow(extra ,new String[]{"	(2) SE NIEGA A FIRMAR"});
					report.addTableRow(extra ,new String[]{"	(3) FIRMADA"});
					report.addTableRow(extra, new String[]{"    (A) ANULADA"});
					report.addRawHTML("<tr>");
					report.addRawHTML(String.format("<td colspan='6'></td>"));
					report.addRawHTML(String.format("<td style='font-size: 8pt;' align='center' colspan='4'>%s</td>",
							"<b>.........................................................</b>"));
					report.addRawHTML("</tr>");
					report.addRawHTML("<tr>");
					report.addRawHTML(String.format("<td colspan='6'></td>"));
					report.addRawHTML(String.format("<td style='font-size: 8pt;' align='center' colspan='4'>" +
							"<b>Firma Inspector Cod. %s</b></td>", numeroInsPectorRef));
					report.addRawHTML("</tr>");
					report.addRawHTML("<tr>");
					report.addRawHTML(String.format("<td colspan='6'></td>"));
					report.addRawHTML(String.format("<td style='font-size: 8pt;' align='center' colspan='4'>" +
							"<b>%s</b></td>", nombreInspector));
					report.addRawHTML("</tr>");
					report.addRawHTML("</tfoot>");
				}
				
				Formatter formatter = new Formatter(formulario);

				List<String> values = new ArrayList<>();
				values.add(formulario.getNumero());
				values.add(DateUtil.formatDate(formulario.getFechaCierre(), DateUtil.ddSMMSYYYY_MASK));
				values.add(DateUtil.formatDate(formulario.getFechaCierre(), DateUtil.HOUR_MINUTE_MASK));
				if(StringUtil.isNullOrEmpty(formulario.getResumen())){
					values.add(formatter.getString(Campo.COD_LUGAR_INFRACCION));
					values.add(formatter.getString(Campo.COD_DOMINIO));
					values.add(formatter.getString(Campo.COD_NORMA_INFRACCION));
					values.add(formatter.getString(Campo.COD_TIPO_DOCUMENTO_CONDUCTOR)
							+" "+formatter.getString(Campo.COD_NRO_DOCUMENTO_CONDUCTOR));
					values.add(formatter.getString(Campo.COD_APELLIDO_CONDUCTOR)
							+" "+formatter.getString(Campo.COD_NOMBRE_CONDUCTOR));
				}else{
					String[] data = formulario.getResumen().split("\\|");
					values.add(data[0]); // LUGAR_INFRACCION
					values.add(data[1]); // DOMINIO
					values.add(data[2]); // INFRACCION
					values.add(data[3]); // DOCUMENTO_CONDUCTOR
					values.add(data[4]); // NOMBRE_CONDUCTOR
				}
				if(formulario.getRetieneLicencia() == null){
					values.add("N");
				}else{
					values.add(formulario.getRetieneLicencia().equals(1)?"S":"N");
				}
				if(formulario.getMotivoCierreTipoFormulario() != null){
					values.add(formulario.getMotivoCierreTipoFormulario().getId().toString());
				}else{
					values.add("A");
				}

				String[] columns = new String[values.size()];
				values.toArray(columns);
				// Data
				report.addTableRow(
						// Estilo
						"style='font-size: 7pt; border: 0.5px gray solid;'", columns);
				formulario.setFechaImpresion(new Date());

				FrmDAOFactory.getFormularioDAO().update(formulario);
			}
			
			// Close Table
			if(numeroInsPectorRef != 0){
				report.endTable();
			}
			
			if(listFormulario.isEmpty()){
				report.addRawHTML(header.out());
			}


			String fileName = "Reporte_"+UUID.randomUUID().toString().toUpperCase()+".pdf"; 
			File pdfFile = new File("/tmp" + File.separator + fileName);
			//--
			reporteFormularioSearchPage.getReport().setReportFileName(pdfFile.getAbsolutePath());
			Document document = new Document(PageSize.A4.rotate());

			PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
			pdfWriter.setPageEvent(new HeaderFooter());

			document.open();
			document.addAuthor("Municipalidad de Rosario");
			document.addCreator(userContext.getUserName());
			document.addSubject("Reporte de Actas");
			document.addCreationDate();
			document.addTitle("Reporte de Actas");
			log.debug("aklsdjklasjd: " + report.out());
			XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
			worker.parseXHtml(pdfWriter, document, new StringReader(report.out()));

			document.close();

			tx.commit();
			if (log.isDebugEnabled()) log.debug(funcName + ": exit");
			return reporteFormularioSearchPage;
		} catch (Exception e) {
			if(tx != null) tx.rollback();
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	/** Inner class to add a header and a footer. */
	static class HeaderFooter extends PdfPageEventHelper {

		public void onEndPage (PdfWriter writer, Document document) {
			Rectangle rect = writer.getBoxSize("art");
			//			switch(writer.getPageNumber() % 2) {
			//			case 0:
			//				ColumnText.showTextAligned(writer.getDirectContent(),
			//						Element.ALIGN_RIGHT, new Phrase("even header"),
			//						rect.getRight(), rect.getTop(), 0);
			//				break;
			//			case 1:
			//				ColumnText.showTextAligned(writer.getDirectContent(),
			//						Element.ALIGN_LEFT, new Phrase("odd header"),
			//						rect.getLeft(), rect.getTop(), 0);
			//				break;
			//			}
			//			ColumnText.showTextAligned(
			//					writer.getDirectContent(),
			//					Element.ALIGN_CENTER, new Phrase(String.format("page %d", writer.getPageNumber())),
			//					(rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);

			ColumnText.showTextAligned(writer.getDirectContent(),
					Element.ALIGN_RIGHT, 
					new Phrase(String.format(" Página: %d", writer.getPageNumber())), 800f, 15f, 0);
		}
	}

	// <--- ABM ReporteFormulario
}

