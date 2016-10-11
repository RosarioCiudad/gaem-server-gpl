package ar.gov.rosario.gait.apm.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.helper.StringUtil;

/**
 * 
 * @author tecso.coop
 * 
 */
@Entity
@Table(name = "apm_campo")
public class Campo extends VersionableBO {

	private static final long serialVersionUID = 1L;

	// Vehiculo
	public static final String COD_DOMINIO = "dominio";
	public static final String COD_TIPO_VEHICULO = "tipoVehiculo";
	public static final String COD_NRO_MOTOR = "numeroMotor";
	public static final String COD_CHASIS = "numeroChasis";
	public static final String COD_MARCA_VEHICULO = "marcaVehiculo";
	public static final String COD_TONALIDAD = "tonalidad";
	public static final String COD_REMITIDO = "remitido";
	public static final String COD_LINEA_VEHICULO = "linea";
	public static final String COD_LICENCIA_VEHICULO = "licencia";
	public static final String COD_INTERNO_VEHICULO = "interno";

	// Conductor
	public static final String COD_DOMICILIO_CONDUCTOR = "domicilioConductor";
	public static final String COD_TIPO_DOCUMENTO_CONDUCTOR = "tipoDocumentoConductor";
	public static final String COD_NRO_DOCUMENTO_CONDUCTOR = "nroDocumentoConductor";
	public static final String COD_SEXO_CONDUCTOR = "sexoConductor";
	public static final String COD_LICENCIA_CONDUCTOR = "licenciaConductor";
	public static final String COD_APELLIDO_CONDUCTOR = "apellidoConductor";
	public static final String COD_NOMBRE_CONDUCTOR = "nombreConductor";

	// Hecho
	public static final String COD_NORMA_INFRACCION = "normaInfraccion";
	public static final String COD_REHUSA_DESCENDER = "rehusaDescender";
	public static final String COD_ACTA_SECUESTRO = "actaSecuestro";
	public static final String COD_RETIENE_LICENCIA = "retieneLicencia";
	public static final String COD_OBSERVACION_HECHO = "observacionHecho";

	// Lugar Infraccion
	public static final String COD_LUGAR_INFRACCION = "lugarInfraccion";

	// Anexo
	public static final String COD_DOCUMENTACION_RESPALDATORIA = "docRespaldatoria";

	@Column(nullable = false)
	private String etiqueta;

	@Column(nullable = false)
	private Integer obligatorio;

	@Column(nullable = false)
	private Integer tratamiento;

	@Column
	private String valorDefault;

	@Column
	private Integer soloLectura;

	@Column
	private String tablaBusqueda;

	@Column
	private String mascaraVisual;

	@Column
	private String codigo;

	@OneToMany()
	@JoinColumn(name = "idCampo")
	@Where(clause = "estado = 1")
	private List<CampoValor> listCampoValor;



	public String getEtiqueta() {
		return etiqueta;
	}

	public String getTablaBusqueda() {
		return tablaBusqueda;
	}

	public int getTratamiento() {
		return tratamiento;
	}

	public String getValorDefault() {
		return valorDefault;
	}

	public int getObligatorio() {
		return obligatorio;
	}

	public int getSoloLectura() {
		return soloLectura;
	}

	public String getMascaraVisual() {
		return mascaraVisual;
	}

	public void setMascaraVisual(String mascaraVisual) {
		this.mascaraVisual = mascaraVisual;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public void setObligatorio(int obligatorio) {
		this.obligatorio = obligatorio;
	}

	public void setSoloLectura(int soloLectura) {
		this.soloLectura = soloLectura;
	}

	public void setTablaBusqueda(String tablaBusqueda) {
		this.tablaBusqueda = tablaBusqueda;
	}

	public void setTratamiento(int tratamiento) {
		this.tratamiento = tratamiento;
	}

	public void setValorDefault(String valorDefault) {
		this.valorDefault = valorDefault;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	} 

	/**
	 * @return the listCampoValor
	 */
	public List<CampoValor> getListCampoValor() {
		return listCampoValor;
	}

	/**
	 * @param listCampoValor
	 *            the listCampoValor to set
	 */
	public void setListCampoValor(List<CampoValor> listCampoValor) {
		this.listCampoValor = listCampoValor;
	}

	// Metodos de Clase
	public static Campo getById(Long id) {
		return (Campo) ApmDAOFactory.getCampoDAO().getById(id);
	}

	public static Campo getByIdNull(Long id) {
		return (Campo) ApmDAOFactory.getCampoDAO().getByIdNull(id);
	}

	public static List<Campo> getList() {
		return ApmDAOFactory.getCampoDAO().getList();
	}

	public static List<Campo> getListActivos() {
		return ApmDAOFactory.getCampoDAO().getListActiva();
	}

	// Validaciones
	public boolean validateCreate() throws Exception {
		// limpiamos la lista de errores
		clearError();

		if (!this.validate()) {
			return false;
		}

		// Validaciones de Negocio

		return true;
	}

	public boolean validateUpdate() throws Exception {
		// limpiamos la lista de errores
		clearError();

		if (!this.validate()) {
			return false;
		}

		// Validaciones de Negocio

		return true;
	}

	public boolean validateDelete() {
		// limpiamos la lista de errores
		clearError();

		if (GenericDAO.hasReferenceActivo(this, CampoValor.class, "campo")) {
			addRecoverableError(BaseError.MSG_ELIMINAR_REGISTROS_ASOCIADOS, ApmError.CAMPO_LABEL, ApmError.CAMPOVALOR_LABEL);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {

		// Validaciones
		if (StringUtil.isNullOrEmpty(getEtiqueta())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					ApmError.CAMPO_ETIQUETA);
		}

		if (hasError()) {
			return false;
		}

		return true;

	}



}