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
package ar.gov.rosario.gait.tmf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
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
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import org.apache.log4j.Logger;

import ar.gov.rosario.gait.aid.iface.model.ReparticionVO;
import ar.gov.rosario.gait.aid.iface.model.UsuarioApmReparticionVO;
import ar.gov.rosario.gait.apm.buss.bean.Campo;
import ar.gov.rosario.gait.apm.buss.bean.UsuarioApm;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmVO;
import ar.gov.rosario.gait.base.iface.model.GaitParam;
import ar.gov.rosario.gait.frm.buss.bean.Formulario;
import ar.gov.rosario.gait.frm.buss.bean.FormularioDetalle;
import ar.gov.rosario.gait.frm.buss.bean.Talonario;
import ar.gov.rosario.gait.frm.buss.bean.TipoFormulario;
import ar.gov.rosario.gait.frm.iface.model.TipoFormularioVO;
import ar.gov.rosario.gait.sidom.data.Documento;
import ar.gov.rosario.gait.sidom.data.SidomWebServiceManager;
import ar.gov.rosario.gait.simgei.user.UsuarioDto;
import ar.gov.rosario.gait.simgei.user.UsuarioWebServiceManager;

import com.sun.xml.xwss.SecurityConfigurationFactory;
import com.sun.xml.xwss.XWSSecurityConfiguration;

import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.helper.ListUtil;
import coop.tecso.demoda.iface.helper.NumberUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.util.To;

public class TmfWebServiceManager {

	private static Logger log = Logger.getLogger(TmfWebServiceManager.class);

	private static final TmfWebServiceManager INSTANCE = new TmfWebServiceManager();

	private static final String paramUriRegistrarActaTMF = "uriRegistrarActaTMF";

	private URL wsdlLocation;
	
	private String configFilePath;
	
	private Map<String, Object> mapCods;
	private To to = new To();

	/**
	 * Constructor privado
	 */
	private TmfWebServiceManager() {
		try {
			// Security configuration file
			configFilePath = GaitParam.getFileSharePath() + File.separator 
							+ "conf" + File.separator + "gait_security_config.xml";
			// WSDL web-service location
			wsdlLocation = new URL(GaitParam.getString(paramUriRegistrarActaTMF));
		} catch (Exception e) {
			log.error("TmfWebServiceManager: **ERROR**", e);
		}
	}

	/**
	 * Devuelve unica instancia
	 */
	public static TmfWebServiceManager getInstance() {
		return INSTANCE;
	}
	

	/**
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public List<UsuarioApmReparticionVO> obtenerInspectorReparticion(String username) throws Exception{
		// 
		RequestInfo requestInfo = new RequestInfo();
		requestInfo.setUserId(username); 
		requestInfo.setOrganizationId("Captores");
		//
		ObtenerInspectorPorLoginRequest request = new ObtenerInspectorPorLoginRequest();
		request.setRequestInfo(requestInfo);
		request.setLogin(username);
		
		Tribunal tribunal = getTribunalService();
		ObtenerInspectorPorLoginResponse response = tribunal.obtenerInspectorPorLogin(request);

		UsuarioApmVO usuarioApm = (UsuarioApmVO) UsuarioApm.getByUserName(username).toVO(0,false);
		List<UsuarioApmReparticionVO> listReparticion = new ArrayList<>();
		for(InspectorReparticion reparticion : response.getReparticiones()){

			UsuarioApmReparticionVO usuarioApmReparticion = new UsuarioApmReparticionVO();
			//TODO:
			usuarioApmReparticion.setReparticion(
					new ReparticionVO(reparticion.getCodReparticion(), reparticion.getNombreReparticion()));
			usuarioApmReparticion.setNumeroInspector(reparticion.getNroInspector());
			//TODO:
			// TipoFormulario: T o C ---> Deberia ser 62 y 63
			String codigo;
			if(reparticion.getTipo().equals("T")){
				codigo = "62";
			}else{
				codigo = "63";
			}
			usuarioApmReparticion.setTipoFormulario((TipoFormularioVO) TipoFormulario.getByCodigo(codigo).toVO(0,false));
			usuarioApmReparticion.setUsuarioApm(usuarioApm);
			listReparticion.add(usuarioApmReparticion);
		}

		return listReparticion;
	}

	/**
	 * 
	 * @param formulario
	 * @return
	 * @throws Exception
	 */
	public boolean registrarActaTransito(Formulario formulario) throws Exception{
		mapCods = new HashMap<String, Object>();
		log.debug(" registrarActaTransito : enter");
		cargarValores(formulario.getListFormularioDetalle());

		// Cargar Acta de Transito
		ActaTransito actaTransito = new ActaTransito();
		actaTransito.setFechaHoraCierre(DateUtil.getXMLGCDate(formulario.getFechaCierre()));
		actaTransito.setIdFormulario(formulario.getTipoFormularioDef().getId().intValue()); 
		XMLGregorianCalendar fechaActa = DateUtil.getXMLGCDate(formulario.getFechaInicio());
		actaTransito.setFechaActa(fechaActa);
		actaTransito.setHoraActa(fechaActa);
		// Archivo firmado digitalmente con el certificado del inspector
		actaTransito.setActaDigital(formulario.getFirmaDigital()); 

		// Recreacion Digital
		Imagen imagenActa = new Imagen();
		imagenActa.setTipo(1); // 1- Acta
		imagenActa.setContenido(formulario.getFormularioDigital());
		actaTransito.getImagenes().add(imagenActa);

		/*
		 * Tipos de Acta:
		 * - 62 para infracciones de tránsito
		 * - 63 para infracciones de comercio
		 */
		actaTransito.setTipoActa(to.Integer(formulario.getTipoFormulario().getCodigo())); 
		actaTransito.setNroActa(to.Integer(formulario.getTalonario().getValor()));
		// Talonario
		Talonario talonario = formulario.getTalonario();
		actaTransito.setSerie(talonario.getSerie().getCodigo()); 
		actaTransito.setSerieCaptor(formulario.getDispositivoMovil().getNumeroSerie());

		// Cargar inspector
		Inspector inspector = new Inspector();
		inspector.setLogin(formulario.getUsuarioCierre().getUsername());
		inspector.setNumero(formulario.getNumeroInspector()); 
		actaTransito.setInspector(inspector);

		//**SECCION CONDUCTOR**
		Infractor infractor = new Infractor();
		
		Integer codDoc = NumberUtil.getInteger(mapCods.get(Campo.COD_TIPO_DOCUMENTO_CONDUCTOR));
		if(null != codDoc && codDoc > 0) infractor.setCodDoc(codDoc); //REQ
		
		Integer nroDoc = NumberUtil.getInteger(mapCods.get(Campo.COD_NRO_DOCUMENTO_CONDUCTOR));
		if(null != nroDoc) infractor.setNroDoc(nroDoc); //REQ
		
		infractor.setSexo(to.String(mapCods.get(Campo.COD_SEXO_CONDUCTOR))); // REQ "F" o "M"
		infractor.setNombres(to.String(mapCods.get(Campo.COD_NOMBRE_CONDUCTOR))); //REQ
		infractor.setApellido(to.String(mapCods.get(Campo.COD_APELLIDO_CONDUCTOR))); //REQ
		infractor.setLicenciaClase(to.String(mapCods.get(Campo.COD_LICENCIA_CONDUCTOR))); //REQ
		// Direccion
		Direccion direccionInfractor = new Direccion();
		direccionInfractor.setNombreCalle(to.String(mapCods.get("dc_calle"))); //REQ
		direccionInfractor.setCodCalle(NumberUtil.getInteger(mapCods.get("dc_codCalle"))); 
		direccionInfractor.setNumero(NumberUtil.getInteger(mapCods.get("dc_altura")));
		direccionInfractor.setBis(to.Boolean(mapCods.get("dc_bis"), false));
		direccionInfractor.setLetraCalle(to.String(mapCods.get("dc_letraCalle")));
		direccionInfractor.setMonoblock(to.String(mapCods.get("dc_mblk")));
		direccionInfractor.setPiso(NumberUtil.getInteger(mapCods.get("dc_piso")));
		direccionInfractor.setDepartamento(NumberUtil.getInteger(mapCods.get("dc_depto")));
		Integer codPostal = NumberUtil.getInteger(mapCods.get("dc_codPostal"));
		if(codPostal != null && codPostal > 0){
			direccionInfractor.setCodPostalLocalidad(NumberUtil.getInteger(mapCods.get("dc_codPostal")));
			direccionInfractor.setSubPostalLocalidad(NumberUtil.getInteger(mapCods.get("dc_subPostal")));
		}
		
		direccionInfractor.setNombreLocalidad(to.String(mapCods.get("dc_localidad")));
		direccionInfractor.setCodPais(NumberUtil.getInteger(mapCods.get("dc_codPais")));
		infractor.setDireccion(direccionInfractor);
		
		//
		if(infractor.getNroDoc() > 0 // Numero de documento
				&& infractor.getCodDoc() > 0
				&& !StringUtil.isNullOrEmpty(infractor.getSexo()) // Sexo
				&& !StringUtil.isNullOrEmpty(infractor.getLicenciaClase()) // Licencia Clase
				&& direccionInfractor.codPais != null // Pais
				&& (direccionInfractor.getCodPostalLocalidad()!=null ||  // Codigo Localidad
				   !StringUtil.isNullOrEmpty(direccionInfractor.getNombreLocalidad())) // Nombre Localidad
				&& (direccionInfractor.getCodCalle() != null || // Codigo Calle
						   !StringUtil.isNullOrEmpty(direccionInfractor.getNombreCalle())) // Nombre Calle
				&& direccionInfractor.getNumero() != null) // Altura
		{
			actaTransito.setInfractor(infractor);
		}

//		if(!StringUtil.isNullOrEmpty(infractor.getNombres()) 
//				&& !StringUtil.isNullOrEmpty(infractor.getApellido())){
//			actaTransito.setInfractor(infractor);
//		}

		//**SECCION VEHICULO**
		Vehiculo vehiculo = new Vehiculo();
		// Dominio
		vehiculo.setDominioNulo(Boolean.valueOf(to.String(mapCods.get("ve_dominioSinIdentificar"))));
		if(!vehiculo.dominioNulo){
			vehiculo.setDominio(to.String(mapCods.get("ve_dominio"))); 
			vehiculo.setDominioCopia(to.String(mapCods.get("ve_dominioCopia")));
		}
		Integer tipoVehiculo = NumberUtil.getInteger(mapCods.get(Campo.COD_TIPO_VEHICULO));
		if(null != tipoVehiculo) vehiculo.setTipoVehiculo(tipoVehiculo); //REQ
		
		vehiculo.setNroMotor(to.String(mapCods.get(Campo.COD_NRO_MOTOR)));
		vehiculo.setChasis(to.String(mapCods.get(Campo.COD_CHASIS)));
		if(mapCods.get("mv_marca") != null){
			vehiculo.setMarca(to.String(mapCods.get("mv_marca"))); //REQ
		}
		vehiculo.setTonalidad(null);
		vehiculo.setRemitido(to.Boolean(mapCods.get(Campo.COD_REMITIDO))); 
		try {
			vehiculo.setIdMarca(NumberUtil.getInteger(to.Integer(mapCods.get("mv_idMarca")))); 
		} catch (Exception e) {
			log.debug("Marca: "+ mapCods.get("mv_idMarca") + " ERR:"+e.getMessage());
		}
		vehiculo.setLicencia(NumberUtil.getInteger(mapCods.get(Campo.COD_LICENCIA_VEHICULO)));
		vehiculo.setLinea(to.String(mapCods.get(Campo.COD_LINEA_VEHICULO))); 
		vehiculo.setInterno(NumberUtil.getInteger(mapCods.get(Campo.COD_INTERNO_VEHICULO)));
		actaTransito.setVehiculo(vehiculo);

		//**SECCION LUGAR**
		// DireccionInfraccion
		Direccion dirInfraccion = new Direccion();
		dirInfraccion.setCodCalle(NumberUtil.getInteger(mapCods.get("li_codCalle"))); 
		dirInfraccion.setNombreCalle(to.String(mapCods.get("li_calle"))); //REQ
		try {
			dirInfraccion.setNumero(NumberUtil.getInteger(mapCods.get("li_altura"))); 
		} catch (Exception e) {
			log.debug("li_altura: "+ mapCods.get("li_altura") + " ERR:"+e.getMessage());
		}
		try {
			dirInfraccion.setBis(to.Boolean(mapCods.get("li_bis").toString())); 
		} catch (Exception e) {
			log.debug("li_altura: "+ mapCods.get("li_bis") + " ERR:"+e.getMessage());
		}
		dirInfraccion.setLetraCalle(to.String(mapCods.get("li_letra")));
		dirInfraccion.setCodCalleInterseccion(NumberUtil.getInteger(mapCods.get("li_codigoInterseccion")));
		dirInfraccion.setCodPostalLocalidad(2000);
		dirInfraccion.setSubPostalLocalidad(8);
		dirInfraccion.setCodPais(1);
		// LugarInfraccion
		// Posicion
		Posicion posicion = new Posicion();
		
		//TODO:
		if(formulario.getLatitud() != null){
			posicion.setLatitud(formulario.getLatitud());
			posicion.setLongitud(formulario.getLongitud());
			posicion.setPrecision(formulario.getPrecision());
			posicion.setFechaHoraMedicion(DateUtil.getXMLGCDate(formulario.getFechaMedicion()));
			posicion.setOrigen(formulario.getOrigen()); // 1-GPS|2-Antena	
		}else{
			posicion.setLatitud(0);
			posicion.setLongitud(0);
			posicion.setPrecision(0);
			posicion.setFechaHoraMedicion(DateUtil.getXMLGCDate(new Date()));
			posicion.setOrigen(2); // 1-GPS|2-Antena
		}
		LugarInfraccion lugarInfraccion = new LugarInfraccion();
		lugarInfraccion.setIdSentidoCirculacion(NumberUtil.getInteger(mapCods.get("li_sentido")));
		lugarInfraccion.setDireccion(dirInfraccion);
		lugarInfraccion.setPosicion(posicion);
		/* Formato LugarInfraccion.Descripcion 
		Formar una cadena con una de estas opciones según sea una dirección o una intersección: 
			1) Nombre abreviado + numero + bis + letra 
			2) Nombre abreviado + “ Y “ + Nombre abreviado 
		Máxima longitud 33 caracteres. */ 
		StringBuilder descripcion = new StringBuilder();
		if(dirInfraccion.getCodCalleInterseccion() == null){
			// Caso 1)
			descripcion.append(dirInfraccion.getNombreCalle());
			descripcion.append(" ");
			descripcion.append(dirInfraccion.getNumero());
			if(dirInfraccion.isBis()) descripcion.append(" bis ");
			if(!StringUtil.isNullOrEmpty(dirInfraccion.getLetraCalle())){
				descripcion.append(" ");
				descripcion.append(dirInfraccion.getLetraCalle());
			}
		}else{
			// Caso 2)
			descripcion.append(dirInfraccion.getNombreCalle());
			descripcion.append(" Y ");
			descripcion.append(mapCods.get("li_interseccion"));
		}
		if(descripcion.toString().length() <= 33){
			lugarInfraccion.setDescripcion(descripcion.toString());
		}else{
			lugarInfraccion.setDescripcion(descripcion.substring(0, 33));
		}
		actaTransito.setLugarInfraccion(lugarInfraccion);
		
		//**SECCION DESCRIPCION DEL HECHO**
		// Normas Infringidas
		List<String> listInfraccion = (List) mapCods.get(Campo.COD_NORMA_INFRACCION);
		if(null != listInfraccion){
			for (String codInfraccion : listInfraccion) {
				Infraccion infraccion = new Infraccion();
				infraccion.setCodInfraccion(codInfraccion);
				actaTransito.getInfracciones().add(infraccion);	
			}
		}
		actaTransito.setRehusaDescender(to.Boolean(mapCods.get(Campo.COD_REHUSA_DESCENDER)));
		actaTransito.setLicenciaRetenida(to.Boolean(mapCods.get(Campo.COD_RETIENE_LICENCIA)));
		actaTransito.setNroActaSecuestro(to.String(mapCods.get(Campo.COD_ACTA_SECUESTRO))); 
		actaTransito.setObservacion(to.String(mapCods.get(Campo.COD_OBSERVACION_HECHO)));

		// **SECCION ANEXO** ############################################################################
		List<byte[]> images = new ArrayList<>();
		
		Path header = Paths.get(GaitParam.getFileSharePath()+"/privado/escudo_gait.png");
		// Header
		images.add(Files.readAllBytes(header));
		// Content
		images.add(formulario.getFormularioDigital());
		@SuppressWarnings("unchecked")
		List<byte[]> extraImages =  (List<byte[]>) mapCods.get(Campo.COD_DOCUMENTACION_RESPALDATORIA);
		// Extra
		if(null != extraImages)	images.addAll(extraImages);
		// Width
		int width;
		BufferedImage form = ImageIO.read(new ByteArrayInputStream(formulario.getFormularioDigital()));
		width = form.getWidth();
		// Height
		double height = 10D;
		for (byte[] image : images) {
			if(image != null){
				BufferedImage buffImage = ImageIO.read(new ByteArrayInputStream(image));
				height += ((double)width/(double)buffImage.getWidth()) * buffImage.getHeight();
			}
		}
		// Combine Images
		BufferedImage combined = new BufferedImage(width, (int)height, BufferedImage.TYPE_3BYTE_BGR);
		// paint both images, preserving the alpha channels
		Graphics graphics = combined.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, (int) height);
		
		double heightRef = 10D;
		for (byte[] image : images) {
			if(image != null){
				BufferedImage buffImage = ImageIO.read(new ByteArrayInputStream(image));
				height = ((double)width/(double)buffImage.getWidth()) * buffImage.getHeight();
				if(width == buffImage.getWidth()){
					graphics.drawImage(buffImage, 5, (int) heightRef, width, (int) height, null);
					heightRef+= 10;
				}else{
					graphics.drawImage(buffImage, 10, (int) heightRef, width - 20, (int) height - 20, null);
				}
				heightRef += height;
			}
		}
		try {
			UsuarioWebServiceManager usuarioWebServiceManager = UsuarioWebServiceManager.getInstance();
			UsuarioDto usuarioDto = usuarioWebServiceManager.getUsuarioById(formulario.getUsuarioCierre().getUsername());
			
			Integer idDocumentoSidom = usuarioDto.getActor().getIdDocumentoSidom();
			SidomWebServiceManager sidomWebServiceManager = SidomWebServiceManager.getInstance();
			
			String token = sidomWebServiceManager.login().getHash();
			List<Documento> listDocumento = sidomWebServiceManager.obtenerDocumento(token, idDocumentoSidom).getListDocumento();
			if(!ListUtil.isNullOrEmpty(listDocumento)){
				Documento documento = listDocumento.get(listDocumento.size() - 1);
				
				byte[] firma = sidomWebServiceManager.obtenerArchivoRestPorPID(documento.getPid());
				graphics.drawImage(ImageIO.read(new ByteArrayInputStream(firma)), 10, (int) heightRef, width - 20, (int) height - 20, null);
				
//				File imageFile = new File("/tmp/image.png");
//				BufferedImage img = ImageIO.read(new ByteArrayInputStream(firma));
//				ImageIO.write(img, "png", imageFile);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		graphics.dispose();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// Save as new image
		ImageIO.write(combined, "jpeg", new MemoryCacheImageOutputStream(baos));
		imagenActa.setContenido(baos.toByteArray());
		//TODO:
		ImageIO.write(combined, "jpeg", new File("/tmp/out.jpeg"));
		// **FIN SECCION ANEXO** ############################################################################

		// Resultado Firma: Acta Cerrada OK
		if(formulario.getMotivoCierreTipoFormulario() != null){
			actaTransito.setIdResultadoFirma(formulario.getMotivoCierreTipoFormulario().getId().intValue());
			actaTransito.setEstado("CERRADA");
		}
		// Motivo Anulacion: Acta Anulada
		if(formulario.getMotivoAnulacionTipoFormulario() != null){
			actaTransito.setCodMotivoAnulacion(formulario.getMotivoAnulacionTipoFormulario().getId().intValue());
			actaTransito.setEstado("ANULADA");
			// Para el caso de anular actas: 
			// Nombres y apellido son requeridos 
			if(StringUtil.isNullOrEmpty(infractor.getApellido()) 
					&& StringUtil.isNullOrEmpty(infractor.getNombres())){
				// Si no están completos debemos omitir el objeto infractor en el mensaje.
				actaTransito.setInfractor(null);
			}
			// Si Dominio es nulo
			if(StringUtil.isNullOrEmpty(vehiculo.getDominio())){
				// Seteo DominioNulo = true, para saltar la validación.
				actaTransito.getVehiculo().setDominioNulo(true);
			}
			// Si numero de calle o el código son nulos
			if(null == dirInfraccion.getCodCalle()
					|| null == dirInfraccion.getNumero()){
				// No enviamos el objeto DomicilioInfraccion.
				actaTransito.setLugarInfraccion(null);
			}
		}

		// Cargar Alcoholemia
		Alcoholemia alc = new Alcoholemia();
		alc.setIdAlcoholimetro(null); //
		alc.setMedicion(null); //
		alc.setNroMedicion(null); //
		alc.setIdMedico(null); //
		alc.setNroEvaluacion(null); //
		alc.setCodCalleFuga(null); //
		alc.setIdSentidoCirculacion(null); //
		alc.setInspectoresFuga(null); //
		alc.setPoliciasFuga(null); //
		alc.setIdSeccionalPolicial(null); //
		actaTransito.setAlcoholemia(alc);

		// Cargar ActaParticipe //TODO ??
		ActaParticipe actaParticipe = new ActaParticipe();
		actaParticipe.setCodDoc(0);
		actaParticipe.setNroDoc(0);
		actaParticipe.setSexo(null);
		actaParticipe.setNombres(null);
		actaParticipe.setApellido(null);
		actaParticipe.setIdCaracter(0);

		RegistrarActaTransitoRequest request = new RegistrarActaTransitoRequest();
		RequestInfo rInfo = new RequestInfo();
		rInfo.setUserId("GAEM"); 
		rInfo.setOrganizationId("Captores");
		request.setRequestInfo(rInfo);
		request.setActaTransito(actaTransito);
		
		// Tribunal Port
		Tribunal tribunal = getTribunalService();
		RegistrarActaTransitoResponse resp = tribunal.registrarActaTransito(request);
		
		ResponseInfo responseInfo = resp.getResponseInfo();
		
		formulario.setObservacion(responseInfo.getMessage());
		formulario.setCodigoRespuesta(responseInfo.getCode());

		System.out.println(String.format("Code: %s - Message: %s ",
				responseInfo.getMessage(), responseInfo.getCode()));
		
		log.debug(" registrarActaTransito : exit");
		return resp.resultado;
	}

	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Tribunal getTribunalService() throws Exception{
		TribunalService tribunalService = new TribunalService(wsdlLocation);
		Tribunal tribunal = tribunalService.getTribunalSoap11();
		// XWebService Configuration
        XWSSecurityConfiguration config = 
        		SecurityConfigurationFactory.newXWSSecurityConfiguration(new FileInputStream(configFilePath));

         // Put the security configuration info
        ((BindingProvider)tribunal).getRequestContext().
            put(XWSSecurityConfiguration.MESSAGE_SECURITY_CONFIGURATION, config);
        
        return tribunal;
	}
	

	private void cargarValores(List<FormularioDetalle> listFormularioDetalle) {
		for (FormularioDetalle fv : listFormularioDetalle) {
			String codigo = fv.getTipoFormularioDefSeccionCampo().getCampo().getCodigo();
			log.debug(String.format("Campo: %s - Codigo: %s - Valor: %s",
					fv.getTipoFormularioDefSeccionCampo().getCampo().getEtiqueta(), codigo, fv.getValor()));
			if(StringUtil.isNullOrEmpty(codigo)){
				log.debug("**OMITE ANALISIS**");
				continue;
			}
			String[] values;
			switch (codigo) {
			case Campo.COD_TIPO_VEHICULO:
				// codTipoVehiculo|tipoVehiculo
				if(fv.getValor().contains("|")){
					values = fv.getValor().split("\\|");
					mapCods.put(Campo.COD_TIPO_VEHICULO, values[0]);
				}
				break;
			case Campo.COD_LINEA_VEHICULO:
				// codLinea|desLinea
				if(fv.getValor().contains("|")){
					values = fv.getValor().split("\\|");
					mapCods.put(Campo.COD_LINEA_VEHICULO, values[0]);
				}
				break;
			case Campo.COD_MARCA_VEHICULO:
				// idMarca|marca
				if(fv.getValor().contains("|")){
					values = fv.getValor().split("\\|");
					mapCods.put("mv_idMarca", values[0]);
					mapCods.put("mv_marca", values[1]);
				}
				break;
			case Campo.COD_DOMICILIO_CONDUCTOR:
				// codCalle|calle|altura|bis|letra|mblk|piso|dpto|codPostal|subPostal|localidad|codPais|pais|esRosario
			 	//0|ECHEVERRIA|228|false|||||0|0|SEGOVIA|1|ARGENTINA|false 
				values = fv.getValor().split("\\|");
				Integer codCalle = NumberUtil.getInteger(values[0]);
				if(codCalle != null && codCalle > 0){
					mapCods.put("dc_codCalle", values[0]);
				}
				mapCods.put("dc_calle", values[1]);
				mapCods.put("dc_altura", values[2]);
				mapCods.put("dc_bis", values[3]);
				mapCods.put("dc_letraCalle", values[4]);
				mapCods.put("dc_mblk", values[5]);
				mapCods.put("dc_piso", values[6]);
				mapCods.put("dc_depto", values[7]);
				mapCods.put("dc_codPostal", values[8]);
				mapCods.put("dc_subPostal", values[9]);
				mapCods.put("dc_localidad", values[10]);
				mapCods.put("dc_codPais", values[11]);
				mapCods.put("dc_pais", values[12]);
				mapCods.put("dc_esRosario", values[13]);
				break;
			case Campo.COD_LUGAR_INFRACCION:
				// codigoCalle|altura|bis|letra|codigoInterseccion|calle|interseccion|sentido
				values = fv.getValor().split("\\|");
				codCalle = NumberUtil.getInteger(values[0]);
				if(codCalle != null && codCalle > 0){
					mapCods.put("li_codCalle", codCalle);
				}
				mapCods.put("li_altura", values[1]);
				mapCods.put("li_bis", values[2]);
				mapCods.put("li_letra", values[3]);
				Integer codInterseccion = NumberUtil.getInteger(values[4]);
				if(codInterseccion != null && codInterseccion > 0){
					mapCods.put("li_codigoInterseccion", values[4]);
				}
				mapCods.put("li_calle", values[5]);
				mapCods.put("li_interseccion", values[6]);
				if(!values[7].equals("-1")){
					mapCods.put("li_sentido", values[7]);
				}
				break;
			case Campo.COD_TIPO_DOCUMENTO_CONDUCTOR:
				// codigo|descripcion
				if(fv.getValor().contains("|")){
					values = fv.getValor().split("\\|");
					mapCods.put(Campo.COD_TIPO_DOCUMENTO_CONDUCTOR, values[0]);
				}
				break;
			case Campo.COD_SEXO_CONDUCTOR:
				if(!StringUtil.isNullOrEmpty(fv.getValor())){
					String sexo = fv.getValor().charAt(0)+"";
					if(!sexo.equals("S")){
						mapCods.put(Campo.COD_SEXO_CONDUCTOR, sexo);
					}
				}
				break;	
			case Campo.COD_LICENCIA_CONDUCTOR:
				// codigo|descripcion
				if(fv.getValor().contains("|")){
					values = fv.getValor().split("\\|");
					mapCods.put(Campo.COD_LICENCIA_CONDUCTOR, values[1]);
				}
				break;
			case Campo.COD_DOMINIO:
				// dominio|copia|sinIdentificar
				if(fv.getValor().contains("|")){
					values = fv.getValor().split("\\|");
					mapCods.put("ve_dominio", values[0]);
					mapCods.put("ve_dominioCopia", values[1]);
					mapCods.put("ve_dominioSinIdentificar", values[2]);
				}
				break;
			case Campo.COD_NORMA_INFRACCION:
				// codigo|descripcion
				if(fv.getValor().contains("|")){
					values = fv.getValor().split("\\|");
					@SuppressWarnings("unchecked")
					List<String> listInfraccion = (List) mapCods.get(Campo.COD_NORMA_INFRACCION);
					if(null == listInfraccion) listInfraccion = new ArrayList<>();
					try {
						listInfraccion.add(values[0]);
					} catch (Exception e) {
						log.error(Campo.COD_NORMA_INFRACCION+" valor:"+fv.getValor(), e);
					}
					mapCods.put(Campo.COD_NORMA_INFRACCION, listInfraccion);
				}
				break;
			case Campo.COD_DOCUMENTACION_RESPALDATORIA:
				@SuppressWarnings("unchecked")
				List<byte[]> listImagen = (List) mapCods.get(Campo.COD_DOCUMENTACION_RESPALDATORIA);
				if(null == listImagen) listImagen = new ArrayList<>();
				listImagen.add(fv.getImagen());
				mapCods.put(Campo.COD_DOCUMENTACION_RESPALDATORIA, listImagen);
				break;
			default:
				mapCods.put(codigo, fv.getValor());
				break;
			}
		}
	}
}