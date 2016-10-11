package ar.gov.rosario.gait.frm.buss.service;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampo;
import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampoValor;
import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampoValorOpcion;
import ar.gov.rosario.gait.apm.buss.bean.AplicacionPerfil;
import ar.gov.rosario.gait.apm.buss.bean.Campo;
import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.apm.buss.bean.UsuarioApm;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.iface.model.GaitParam;
import ar.gov.rosario.gait.frm.buss.bean.EstadoTipoFormulario;
import ar.gov.rosario.gait.frm.buss.bean.Formulario;
import ar.gov.rosario.gait.frm.buss.bean.FormularioDetalle;
import ar.gov.rosario.gait.frm.buss.bean.MotivoAnulacionTipoFormulario;
import ar.gov.rosario.gait.frm.buss.bean.MotivoCierreTipoFormulario;
import ar.gov.rosario.gait.frm.buss.bean.Talonario;
import ar.gov.rosario.gait.frm.buss.bean.TipoFormulario;
import ar.gov.rosario.gait.frm.iface.model.FormularioDetalleVO;
import ar.gov.rosario.gait.frm.iface.model.FormularioVO;
import ar.gov.rosario.gait.sidom.data.Documento;
import ar.gov.rosario.gait.sidom.data.SidomWebServiceManager;
import ar.gov.rosario.gait.simgei.user.UsuarioDto;
import ar.gov.rosario.gait.simgei.user.UsuarioWebServiceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.graph.GraphAdapterBuilder;

import coop.tecso.demoda.http.Reply;
import coop.tecso.demoda.http.RestRequest;
import coop.tecso.demoda.http.Route;
import coop.tecso.demoda.iface.DemodaServiceException;
import coop.tecso.demoda.iface.helper.ListUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.SiNo;
import coop.tecso.demoda.util.Base64;
import coop.tecso.demoda.util.To;

public class FormularioResource {

	private Logger log = Logger.getLogger(FormularioResource.class);

	To to = new To();

	public Route[] routes() {
		return new Route[] {
				Route.create("POST", "/gait/api/formulario.json", this.getClass(), "saveFormulario")
		};
	}

	/**
	 * 
	 * 
	 */
	public synchronized Reply<Boolean> saveFormulario(RestRequest<String> req) throws Exception {
		log.info("saveFormulario: enter");
		try {
			// XML
			String xmlData = to.String(req.parameters.get("data"));

			log.debug(xmlData);

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
			DocumentBuilder builder = factory.newDocumentBuilder();  
			Document document = builder.parse(new InputSource(new StringReader(xmlData)));  
			// Root Element
			Element data = document.getDocumentElement();
			// JSON node
			Node jsonNode = data.getElementsByTagName("json").item(0).getFirstChild();
			// Signature node
			Node signNode = data.getElementsByTagName("signature").item(0).getFirstChild();

			String json = jsonNode.getNodeValue();
			String signature = signNode.getNodeValue();

			GsonBuilder gsonBuilder = new GsonBuilder()
			.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
				@Override
				public Date deserialize(JsonElement e, Type arg1, JsonDeserializationContext context) {
					return new Date(e.getAsJsonPrimitive().getAsLong());
				}
			});
			new GraphAdapterBuilder()
			.addType(FormularioVO.class)
			.addType(FormularioDetalleVO.class)
			.registerOn(gsonBuilder);

			Gson gson = gsonBuilder.create();
			FormularioVO formularioVO = gson.fromJson(json, FormularioVO.class);

			//TODO:
			formularioVO.setFirmaDigital(signature);

			Formulario formulario = new Formulario();
			copy(formularioVO, formulario);

			FrmFormularioManager.getInstance().saveFormulario(formulario);

			return new Reply<Boolean>(Boolean.TRUE);
		} catch (Exception e) {
			throw new DemodaServiceException(e);
		} finally {
			GaitHibernateUtil.closeSession();
		}
	}

	/**
	 * Copia los datos del formulario y la lista de formularioValor
	 * 
	 * @param fVO
	 * @param f
	 */
	private void copy(FormularioVO fVO, Formulario f) {
		f.setTipoFormularioDef(AplicacionPerfil.getById(fVO.getTipoFormularioDef().getId()));
		f.setEstadoTipoFormulario(EstadoTipoFormulario.getById(fVO.getEstadoTipoFormulario().getId()));
		f.setMotivoCierreTipoFormulario(MotivoCierreTipoFormulario.getByIdNull(fVO.getMotivoCierreTipoFormulario().getId()));
		f.setMotivoAnulacionTipoFormulario(MotivoAnulacionTipoFormulario.getByIdNull(fVO.getMotivoAnulacionTipoFormulario().getId()));
		f.setTipoFormulario(TipoFormulario.getById(fVO.getTipoFormulario().getId()));
		f.setUsuarioCierre(UsuarioApm.getById(fVO.getUsuarioCierre().getId()));
		DispositivoMovil dispositivoMovil = DispositivoMovil.getById(fVO.getDispositivoMovil().getId());
		f.setDispositivoMovil(dispositivoMovil);
		f.setArea(dispositivoMovil.getArea());
		f.setObservacion(fVO.getObservacion());
		f.setTalonario(Talonario.getByIdNull(fVO.getTalonario().getId()));
		f.setFechaInicio(fVO.getFechaInicio());
		f.setNumero(fVO.getNumero());
		f.setDomicilio(fVO.getDomicilio());
		f.setFechaCierre(fVO.getFechaCierre());
		f.setFechaTransmision(new Date());
		f.setCantidadImpresiones(fVO.getCantidadImpresiones());
		// Localizacion
		f.setLatitud(fVO.getLatitud());
		f.setLongitud(fVO.getLongitud());
		f.setPrecision(fVO.getPrecision());
		f.setFechaMedicion(fVO.getFechaMedicion());
		f.setOrigen(fVO.getOrigen());
		// Firma Digital
		if(!StringUtil.isNullOrEmpty(fVO.getFirmaDigital())){
			f.setFirmaDigital(Base64.decode(fVO.getFirmaDigital(), Base64.DEFAULT));
		}
		// Recreacion Digital
		if(!StringUtil.isNullOrEmpty(fVO.getFormularioDigital())){
			f.setFormularioDigital(Base64.decode(fVO.getFormularioDigital(), Base64.DEFAULT));
			//--
			//
			//			try {
			//				UsuarioWebServiceManager usuarioWebServiceManager = UsuarioWebServiceManager.getInstance();
			//				UsuarioDto usuarioDto = usuarioWebServiceManager.getUsuarioById(f.getUsuarioCierre().getUsername());
			//				
			//				Integer idDocumentoSidom = usuarioDto.getActor().getIdDocumentoSidom();
			//				SidomWebServiceManager sidomWebServiceManager = SidomWebServiceManager.getInstance();
			//				
			//				String token = sidomWebServiceManager.login().getHash();
			//				List<Documento> listDocumento = sidomWebServiceManager.obtenerDocumento(token, idDocumentoSidom).getListDocumento();
			//				if(!ListUtil.isNullOrEmpty(listDocumento)){
			//					Documento documento = listDocumento.get(listDocumento.size() - 1);
			//					
			//					byte[] image = sidomWebServiceManager.obtenerArchivoRestPorPID(documento.getPid());
			//					File imageFile = new File("/tmp/image.png");
			//					BufferedImage img = ImageIO.read(new ByteArrayInputStream(image));
			//					ImageIO.write(img, "png", imageFile);
			//				}
			//			} catch (Exception e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//			}
			//--


		}
		//
		//		f.setSerie(fVO.getSerie());
		f.setNumeroInspector(fVO.getNumeroInspector());

		// 
		Map<String,Object> data = new HashMap<>();
		List<FormularioDetalle> listFormularioDetalle = new ArrayList<>();
		for (FormularioDetalleVO fvVO : fVO.getListFormularioDetalle()) {
			FormularioDetalle fd = new FormularioDetalle();
			fd.setFormulario(f);
			AplPerfilSeccionCampo campoDef = AplPerfilSeccionCampo.getById(fvVO.getTipoFormularioDefSeccionCampo().getId());
			fd.setTipoFormularioDefSeccionCampo(campoDef);
			fd.setTipoFormularioDefSeccionCampoValor(AplPerfilSeccionCampoValor.getByIdNull(fvVO.getTipoFormularioDefSeccionCampoValor().getId()));
			fd.setTipoFormularioDefSeccionCampoValorOpcion(AplPerfilSeccionCampoValorOpcion.getByIdNull(fvVO.getTipoFormularioDefSeccionCampoValorOpcion().getId()));
			if(!StringUtil.isNullOrEmpty(fvVO.getImagen())) fd.setImagen(Base64.decode(fvVO.getImagen(), Base64.DEFAULT));
			fd.setValor(fvVO.getValor());

			//--
			String campo = campoDef.getCampo().getCodigo();
			if(!StringUtil.isNullOrEmpty(campo)){
				switch (campo) {
				case Campo.COD_RETIENE_LICENCIA:
					data.put(Campo.COD_RETIENE_LICENCIA, Formatter.formatDetalle(fd));
					break;
				case Campo.COD_LUGAR_INFRACCION:
					Map<String,Object> mDomicilio = (Map<String, Object>) Formatter.formatDetalle(fd);
					data.put(Campo.COD_LUGAR_INFRACCION, mDomicilio.get("descripcion"));
					break;
				case Campo.COD_TIPO_DOCUMENTO_CONDUCTOR:
					data.put(Campo.COD_TIPO_DOCUMENTO_CONDUCTOR, Formatter.formatDetalle(fd));
					break;
				case Campo.COD_NRO_DOCUMENTO_CONDUCTOR:
					data.put(Campo.COD_NRO_DOCUMENTO_CONDUCTOR, Formatter.formatDetalle(fd));
					break;
				case Campo.COD_APELLIDO_CONDUCTOR:
					data.put(Campo.COD_APELLIDO_CONDUCTOR, Formatter.formatDetalle(fd));
					break;
				case Campo.COD_NOMBRE_CONDUCTOR:
					data.put(Campo.COD_NOMBRE_CONDUCTOR, Formatter.formatDetalle(fd));
					break;
				case Campo.COD_DOMINIO:
					data.put(Campo.COD_DOMINIO, Formatter.formatDetalle(fd));
					break;
				case Campo.COD_NORMA_INFRACCION:
					String norma = (String) data.get(Campo.COD_NORMA_INFRACCION);
					if(norma == null){
						data.put(Campo.COD_NORMA_INFRACCION, Formatter.formatDetalle(fd));
					}else{
						data.put(Campo.COD_NORMA_INFRACCION, String.format("%s<br></br>%s",
								norma, Formatter.formatDetalle(fd)));
					}
					break;
				default:
					break;
				}
			}
			boolean retieneLicencia = to.Boolean(data.get(Campo.COD_RETIENE_LICENCIA), false);
			StringBuilder builder = new StringBuilder();
			builder.append(to.String(data.get(Campo.COD_LUGAR_INFRACCION),"-"));
			builder.append("|");
			builder.append(to.String(data.get(Campo.COD_DOMINIO),"-"));
			builder.append("|");
			builder.append(to.String(data.get(Campo.COD_NORMA_INFRACCION),"-"));
			builder.append("|");
			// Documento
			String numero = to.String(data.get(Campo.COD_NRO_DOCUMENTO_CONDUCTOR));
			if(StringUtil.isNullOrEmpty(numero)){
				builder.append("-");
			}else{
				builder.append(to.String(data.get(Campo.COD_TIPO_DOCUMENTO_CONDUCTOR),""));
				builder.append(" ");
				builder.append(numero);
			}
			builder.append("|");
			builder.append(to.String(data.get(Campo.COD_NOMBRE_CONDUCTOR),"-"));
			builder.append(" ");
			builder.append(to.String(data.get(Campo.COD_APELLIDO_CONDUCTOR),"-"));

			f.setRetieneLicencia(retieneLicencia?SiNo.SI.getId():SiNo.NO.getId());
			f.setResumen(builder.toString());
			//--

			listFormularioDetalle.add(fd);
		}

		f.setListFormularioDetalle(listFormularioDetalle);
	}
}