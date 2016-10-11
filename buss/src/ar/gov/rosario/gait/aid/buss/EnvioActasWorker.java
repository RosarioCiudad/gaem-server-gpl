package ar.gov.rosario.gait.aid.buss;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampoValor;
import ar.gov.rosario.gait.apm.buss.bean.Campo;
import ar.gov.rosario.gait.base.buss.dao.GaitHibernateUtil;
import ar.gov.rosario.gait.base.iface.model.GaitParam;
import ar.gov.rosario.gait.frm.buss.bean.EstadoTipoFormulario;
import ar.gov.rosario.gait.frm.buss.bean.Formulario;
import ar.gov.rosario.gait.frm.buss.bean.FormularioDetalle;
import ar.gov.rosario.gait.frm.buss.dao.FrmDAOFactory;
import ar.gov.rosario.gait.sidom.data.Documento;
import ar.gov.rosario.gait.sidom.data.SidomWebServiceManager;
import ar.gov.rosario.gait.simgei.user.UsuarioDto;
import ar.gov.rosario.gait.simgei.user.UsuarioWebServiceManager;
import coop.tecso.adpcore.AdpRun;
import coop.tecso.adpcore.AdpRunDirEnum;
import coop.tecso.adpcore.AdpWorker;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.helper.DemodaUtil;
import coop.tecso.demoda.iface.helper.ListUtil;
import coop.tecso.demoda.iface.helper.NumberUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.Estado;
import coop.tecso.demoda.iface.model.Tratamiento;




public class EnvioActasWorker  implements AdpWorker {

	private Logger log = Logger.getLogger(EnvioActasWorker.class);

	private String PATH;
	private Map<String,Object> valores;
	private final String codComercio= "12";

	public void cancel(AdpRun adpRun) throws Exception {
	}

	public void reset(AdpRun adpRun) throws Exception {
	}

	public boolean validate(AdpRun adpRun) throws Exception {
		return true;
	}

	public void execute(AdpRun adpRun) throws Exception {
		String funcName = DemodaUtil.currentMethodName();
		log.debug(funcName + ": enter");
		log.debug("VOY A EJECUTAR EnvioActasWorker");

		Session session = null;
		Transaction tx = null; 
		try {

			// Lista de Actas Procesadas con error
			List<Long> listIdFormulario = FrmDAOFactory.getFormularioDAO().getListIdProcesadoByCodigo(codComercio);
			//List<Long> listIdFormulario = FrmDAOFactory.getFormularioDAO().g;
			session = GaitHibernateUtil.currentSession();
			tx = session.beginTransaction();

			String dirSalida = AdpRun.currentRun().getProcessDir(AdpRunDirEnum.SALIDA);
			log.debug("DIRECTORIO: "+dirSalida);
			String fileRechazados = dirSalida+"/files_";//+adpRun.getId(); //+"/"+"salida"+adpRun.getId()+".csv";
			File outputDir = new File(fileRechazados);
			if (!outputDir.exists()) {
				outputDir.mkdir();
			}
			log.debug("DIRECTORIO FINAL: "+fileRechazados+"/"+"salida"+adpRun.getId()+".csv");
			//inicializamos el archivo
			File outputFile = new File(fileRechazados+"/"+"salida"+adpRun.getId()+".csv");
			//File outputFile = new File(fileRechazados+"/"+"salida.csv"); ///mnt/gait/reportes/EnvioActas

			PrintWriter writer = new PrintWriter(outputFile);

			int proc = 0;
			int falla = 0;
			log.debug("Se procesaran "+listIdFormulario.size()+" formularios");
			for (Long idFormulario : listIdFormulario) {
				Formulario formulario = Formulario.getById(idFormulario);

				procesarFormulario(formulario, writer);
				try {
					byte[] formularioDigital = componerImagenForm(formulario);
					formulario.setFormularioDigital(formularioDigital);
				} catch (Exception e1) {
					log.error("EnvioActasWorker: Imagen Error: ",  e1);
				}

				try{
					procesarFormulario(formulario, writer);
					formulario.setEstado(Estado.PROCESADO.getId());
					proc++;
				}catch(Exception e){
					formulario.setEstado(Estado.PROCESADO_ERROR.getId());
					log.error("EnvioActasWorker: Service Error: ",  e);
					falla++;
				}
			}

			tx.commit();
			String msg;
			writer.close();
			if(proc == 0){
				msg = "No existen actas a Procesar";
				log.debug("No existen actas a Procesar");
			}else{
				log.debug("Procesadas "+proc+" actas");
				msg = String.format("Procesadas correctamente %s actas, Procesadas con erros %s actas",proc,falla);
			}

			adpRun.changeStateFinOk(msg);
			AdpRun.logRun(msg);
		} catch (Exception e) {
			log.error(funcName + ": Service Error: ",  e);
			adpRun.changeStateError("Hubo errores durante el procesamiento", e);
		}finally{
			GaitHibernateUtil.closeSession();
		}
		log.debug(funcName + ": exit");
	}

	private void procesarFormulario(Formulario formulario, PrintWriter writer) {
		valores = new HashMap<String, Object>();

		//agregamos los datos del formulario al mapa:
		valores.put("numero_acta",formulario.getNumero());
		valores.put("fecha_acta", DateUtil.formatDate(formulario.getFechaInicio(),  DateUtil.ddSMMSYYYY_MASK));//fecha acta //TODO es la fecha que hay que enviar???
		valores.put("hora_acta", DateUtil.formatDate(formulario.getFechaInicio(),  DateUtil.HOUR_MINUTE_MASK));//hora acta //TODO es la fecha que hay que enviar???
		if(formulario.getEstadoFormulario().getId() != null)
			valores.put("estado_acta_id", formulario.getEstadoFormulario().getId().toString());
		valores.put("observaciones", formulario.getObservacion());
		if(formulario.getOrigen() != null)
			valores.put("origen_acta", formulario.getOrigen().toString());
		valores.put("fecha_carga",  DateUtil.formatDate(formulario.getFechaCierre(),  DateUtil.ddSMMSYYYY_MASK));//TODO fecha_carga ??

		//agregamos datos de los detalles del formulario:
		for(FormularioDetalle detalle : formulario.getListFormularioDetalle()) {

			if(detalle.getTipoFormularioDefSeccionCampo() != null) {
				log.debug("entro a getTipoFormularioDefSeccionCampo(");
				if(StringUtil.isNullOrEmpty(detalle.getTipoFormularioDefSeccionCampo().getCampo().getCodigo())){
					continue;
				}
			}

			log.debug("campo.código: "+detalle.getTipoFormularioDefSeccionCampo().getCampo().getCodigo());
			switch(detalle.getTipoFormularioDefSeccionCampo().getCampo().getCodigo()){
			case "tipo_documento": 
				try{
					log.debug("entro a tipo_documento");
					valores.put(detalle.getTipoFormularioDefSeccionCampo().getCampo().getCodigo(), detalle.getTipoFormularioDefSeccionCampoValor().getCampoValor().getCodigo());
					valores.put("infractor_numero_documento",detalle.getValor());
				}catch(NullPointerException e){
					log.error("EnvioActasWorker: Service Error: ",  e);
					e.printStackTrace(); 
				}
				break;

			case "tipo_infractor": 
				try {
					log.debug("entro a tipo_infractor");
					detalle.getTipoFormularioDefSeccionCampoValor().getCampoValor().getCodigo();
					valores.put(detalle.getTipoFormularioDefSeccionCampo().getCampo().getCodigo(), detalle.getTipoFormularioDefSeccionCampoValor().getCampoValor().getCodigo());
				} catch (NullPointerException e) {
					log.error("EnvioActasWorker: Service Error: ",  e);
					e.printStackTrace();
				}
				break;

			case "drei_inscripto": 
				try {
					log.debug("entro a drei_inscripto");
					valores.put(detalle.getTipoFormularioDefSeccionCampo().getCampo().getCodigo(), detalle.getTipoFormularioDefSeccionCampoValor().getCampoValor().getCodigo());
				} catch (NullPointerException e) {}
				break;

			case "norma_infraccion": 
				try {
					log.debug("entro a norma_infraccion");
					//veo si ya hay insertada alguna infracción:
					if(valores.get(detalle.getTipoFormularioDefSeccionCampo().getCampo().getCodigo()) != null){
						String valor = (String) valores.get(detalle.getTipoFormularioDefSeccionCampo().getCampo().getCodigo());
						String codNorma = valor+","+getCodigoInfraccion(detalle.getValor());
						valores.put( detalle.getTipoFormularioDefSeccionCampo().getCampo().getCodigo(), codNorma );
					}
					//es la primer infracción que voy a insertar
					else{
						valores.put(detalle.getTipoFormularioDefSeccionCampo().getCampo().getCodigo(), getCodigoInfraccion(detalle.getValor())); 
					}
				} catch (NullPointerException e) {}
				break;

			case "imagen_anexo":
				@SuppressWarnings("unchecked")
				List<byte[]> listImagen = (List) valores.get("imagen_anexo");
				if(null == listImagen) listImagen = new ArrayList<>();
				listImagen.add(detalle.getImagen());
				valores.put("imagen_anexo", listImagen);
				break;
			default:
				log.debug("default");
				valores.put(detalle.getTipoFormularioDefSeccionCampo().getCampo().getCodigo(), detalle.getValor());
			}
		}

		//		log.debug("Valores del detalle: ");
		//		for(String key: valores.keySet()){
		//			log.debug("clave: "+key+" - valor: "+valores.get(key));
		//		}

		String csvLine = createLine(formulario);
		log.debug("LINEA: "+csvLine);
		writer.println(csvLine);

	}


	private String createLine(Formulario formulario) {
		StringBuilder sb = new StringBuilder();

		sb.append(getData("numero_acta")); 
		sb.append(getData("fecha_acta")); 
		sb.append(getData("hora_acta")); 
		sb.append(getData("padron_drei")); //padron_drei
		sb.append(getData("padron_catastro")); //
		sb.append(getData("persona_id")); // //TODO persona_id?
		sb.append(valores.get("infractor_apellido")+" "+(valores.get("infractor_nombre"))+"|"); //infractor_apenom

		//parseamos el domicilio del infractor:
		Map<String, String> domInfractor;
		domInfractor = formatDomicilio(getData("infractor_dom"));
		sb.append(getData("calle",domInfractor));
		sb.append("Santa Fe|"); //localidad

		sb.append(getData("tipo_documento")); //
		sb.append(getData("infractor_numero_documento")); //
		sb.append(getData("infractor_cuit")); //
		sb.append(formulario.getUsuarioCierre().getUsername()+"|"); //inspector_id
		sb.append(getData("estado_acta_id")); // //estado_acta_id
		sb.append(getData("observaciones")); // //observaciones
		sb.append(getData("tipo_infractor")); //

		//cargamos el domicilio de la infracción:
		Map<String, String> domInfraccion;
		domInfraccion = formatDomicilio(getData("lugar_infraccion"));
		sb.append(getData("altura",domInfraccion)); //lugar_infraccion_numero
		sb.append(getData("calle",domInfraccion));  //lugar_infraccion_calle
		sb.append(getData("piso",domInfraccion));  
		sb.append(getData("dpto",domInfraccion));  

		sb.append(getData("drei_inscripto")); 
		sb.append(getData("recibe_titular")); 

		sb.append(getData("calle",domInfractor));
		sb.append(getData("altura",domInfractor));
		sb.append(getData("piso",domInfractor));
		sb.append(getData("dpto",domInfractor));

		sb.append(getData("motivo_anulacion_id"));
		sb.append(getData("origen_acta")); //
		sb.append(getData("fecha_carga")); //TODO fecha_carga ??
		log.debug("Concateno la norma_infraccion: "+getData("norma_infraccion"));
		sb.append(getData("norma_infraccion"));
		if(formulario.getLatitud() != null && formulario.getLongitud() != null){
			sb.append(formulario.getLatitud().toString()+"|"); //latitud_labrado
			sb.append(formulario.getLongitud().toString()); //longitud_labrado
		}

		return sb.toString();
	}


	/**
	 * Recupera un dato del mapa de valores que fuimos llenando.
	 * @param key
	 * @return valor
	 */
	private String getData(String key) {
		if(valores.containsKey(key))
			return (valores.get(key)+"|");
		else{
			return "|";
		}
	}

	/**
	 * Recupera un dato del mapa pasado como parámetro.
	 * @param key
	 * @param mapa
	 * @return valor
	 */
	private String getData(String key, Map<String, String> mapa) {
		if(mapa.containsKey(key))
			return (mapa.get(key)+"|");
		else{
			return "|";
		}
	}


	/**
	 * Parsea el string correspondiente al domicilio, el mismo tiene la forma:
	 * codCalle|calle|altura|bis|letra|monoblock|piso|dpto|codPostal|codSubPostal|provincia|codPais|pais|esSantaFe
	 * Ejemplo: infracot_dom: 86550|SANTA FE|1500|false|||||2000|8|ROSARIO|1|ARGENTINA|true
	 * @param fecha
	 * @return
	 */
	public Map<String, String> formatDomicilio(String fecha){

		Map<String,String> result = new HashMap<>();
		String[] values = fecha.split("\\|");

		try{
			result.put("codCalle",values[0]);
			result.put("calle", values[1]);
			result.put("altura", values[2]);
			result.put("bis", values[3]);
			result.put("letra", values[4]);
			result.put("monoblock", values[5]);
			result.put("piso", values[6]);
			result.put("dpto", values[7]);
			result.put("codPostal", values[8]);
			result.put("codSubPostal",values[9]);
			result.put("provincia",values[10]);
			result.put("codPais",values[11]);
			result.put("pais",values[12]);
			result.put("esSantaFe",values[13]);
		}catch(ArrayIndexOutOfBoundsException e) {	}

		return result;
	}


	/**
	 * Parsea el campo "Normas Infringidas" y me devuleve solo el código de la infracción
	 * el String a parsear es de la forma codigo_infraccion|descripcion_infraccion.
	 * Ejemplo: 605-01-16/|ACELERAR A FONDO|ACELERAR A FONDO
	 * @param norma
	 * @return
	 */
	private String getCodigoInfraccion(String norma) {
		if(StringUtil.isNullOrEmpty(norma))
			return "";
		String[] valores = norma.split("\\|");
		if(valores.length < 1)
			return "";
		else return valores[0];
	}

	private byte[] componerImagenForm(Formulario formulario) throws Exception {
		log.debug("Entre a la funcion de componer la imagen");
		// **SECCION ANEXO** ############################################################################
		//		List<byte[]> images = new ArrayList<>();


		Map<String,byte[]> mapImage = new TreeMap<String, byte[]>();


		//Path header = Paths.get(GaitParam.getFileSharePath()+"/privado/escudo_gait.png");
		Path header = Paths.get(GaitParam.getFileSharePath()+"/privado/escudo_gait.png");
		// Header
		mapImage.put("1-HEADER", Files.readAllBytes(header));
		//		images.add(Files.readAllBytes(header));

		// Content
		//		images.add(formulario.getFormularioDigital());
		mapImage.put("2-ACTA",formulario.getFormularioDigital());
		@SuppressWarnings("unchecked")
		List<byte[]> extraImages =  (List<byte[]>) valores.get("imagen_anexo");
		// Extra
		if(null != extraImages)	{
			for (int i = 0; i < extraImages.size(); i++) {
				mapImage.put("3-FOTO"+i+1, extraImages.get(i));
			}
			//		images.addAll(extraImages);
		}
		// Width
		int width;
		BufferedImage form = ImageIO.read(new ByteArrayInputStream(formulario.getFormularioDigital()));
		width = form.getWidth();
		// Height
		double height = 10D;

		log.debug("Antes de empezar a llamar a servicio de la firma fase I");
		UsuarioWebServiceManager usuarioWebServiceManager = UsuarioWebServiceManager.getInstance();
		UsuarioDto usuarioDto = usuarioWebServiceManager.getUsuarioById(formulario.getUsuarioCierre().getUsername());
		Integer idDocumentoSidom =  usuarioDto.getActor().getIdDocumentoSidom();
		SidomWebServiceManager sidomWebServiceManager = SidomWebServiceManager.getInstance();
		String token = sidomWebServiceManager.login().getHash();
		List<Documento> listDocumento = sidomWebServiceManager.obtenerDocumento(token, idDocumentoSidom).getListDocumento();

		log.debug("Servicio de la firma Fase II");
		if(!ListUtil.isNullOrEmpty(listDocumento)){
			Documento documento = listDocumento.get(listDocumento.size() - 1);
			try {
				byte[] firma = sidomWebServiceManager.obtenerArchivoRestPorPID(documento.getPid());
				mapImage.put("4-FIRMA",firma);
				//				images.add(firma);
				//				graphics.drawImage(ImageIO.read(new ByteArrayInputStream(firma)), 10, (int) heightRef, width - 20, (int) height - 20, null);
			} catch (Exception e) {
				log.debug("Error en firma", e);
			}

		}	




		for (Map.Entry<String, byte[]> entry : mapImage.entrySet())
		{
			byte[] image = entry.getValue();
			if(image != null){
				BufferedImage buffImage = ImageIO.read(new ByteArrayInputStream(image));
				height += ((double)width/(double)buffImage.getWidth()) * buffImage.getHeight();
			}
		}

//		for (byte[] image : images) {
//			if(image != null){
//				BufferedImage buffImage = ImageIO.read(new ByteArrayInputStream(image));
//				height += ((double)width/(double)buffImage.getWidth()) * buffImage.getHeight();
//			}
//		}

		// Combine Images
		BufferedImage combined = new BufferedImage(width, (int)height, BufferedImage.TYPE_3BYTE_BGR);
		// paint both images, preserving the alpha channels
		Graphics graphics = combined.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, width, (int) height);

		double heightRef = 10D;

//		for (byte[] image : images) {
		for (Map.Entry<String, byte[]> entry : mapImage.entrySet())
		{
			String key = entry.getKey();
			byte[] image = entry.getValue();

			if(image != null){
				BufferedImage buffImage = ImageIO.read(new ByteArrayInputStream(image));
				height = ((double)width/(double)buffImage.getWidth()) * buffImage.getHeight();
				switch (key) {
				case "2-ACTA":
					graphics.drawImage(buffImage, 5, (int) heightRef, width, (int) height, null);
					heightRef+= 10;
					break;
				case "4-FIRMA":
					graphics.drawImage(buffImage, 40, (int) heightRef, (width - 90), (int) (height - 90), null);
					break;
				default:
					graphics.drawImage(buffImage, 10, (int) heightRef, width - 20, (int) height - 20, null);
					break;
				}
				heightRef += height;
			}
		}


		graphics.dispose();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// Save as new image
		ImageIO.write(combined, "jpeg", new MemoryCacheImageOutputStream(baos));
		//		imagenActa.setContenido(baos.toByteArray());
		//TODO:
//		ImageIO.write(combined, "jpeg", new File("/tmp/out22.jpeg"));
		// **FIN SECCION ANEXO** ############################################################################

		log.debug("EXITO");
		return baos.toByteArray();
	}
}
