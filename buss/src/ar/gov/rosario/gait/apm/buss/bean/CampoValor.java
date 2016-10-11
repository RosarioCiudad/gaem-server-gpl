package ar.gov.rosario.gait.apm.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "apm_campoValor")
public class CampoValor extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idCampo")
	private Campo campo;

	@Column(nullable = false)
	private String etiqueta;

	@Column(nullable = false)
	private int obligatorio;

	@Column(nullable = false)
	private int tratamiento;

	@Column
	private String valorDefault;

	@Column
	private String tablaBusqueda;

	@Column
	private int soloLectura;
	
	@Column
	private String mascaraVisual;
	
	@Column
	private String codigo;

	@OneToMany()
	@JoinColumn(name="idCampoValor")
	@Where(clause="estado = 1")
	private List<CampoValorOpcion> listCampoValorOpcion;


	public Campo getCampo() {
		return campo;
	}

	public List<CampoValorOpcion> getListCampoValorOpcion() {
		return listCampoValorOpcion;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public int getSoloLectura() {
		return soloLectura;
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

	public int isObligatorio() {
		return obligatorio;
	}

	public void setCampo(Campo campo) {
		this.campo = campo;
	}

	public void setCampoValorOpcionList(List<CampoValorOpcion> listCampoValorOpcion) {
		this.listCampoValorOpcion = listCampoValorOpcion;
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

	public String getMascaraVisual() {
		return mascaraVisual;
	}

	public void setMascaraVisual(String mascaraVisual) {
		this.mascaraVisual = mascaraVisual;
	}

	public int getObligatorio() {
		return obligatorio;
	}

	public void setListCampoValorOpcion(List<CampoValorOpcion> listCampoValorOpcion) {
		this.listCampoValorOpcion = listCampoValorOpcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	// Metodos de Clase
	public static CampoValor getById(Long id) {
		return (CampoValor) ApmDAOFactory.getCampoValorDAO().getById(id);
	}

	public static CampoValor getByIdNull(Long id) {
		return (CampoValor) ApmDAOFactory.getCampoValorDAO().getByIdNull(id);
	}

	public static List<CampoValor> getList() {
		return ApmDAOFactory.getCampoValorDAO().getList();
	}

	public static List<CampoValor> getListActivos() {			
		return ApmDAOFactory.getCampoValorDAO().getListActiva();
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
		clearError();
		if (GenericDAO.hasReferenceActivo(this, CampoValor.class, "campo")) {
			addRecoverableError(BaseError.MSG_ELIMINAR_REGISTROS_ASOCIADOS,
					ApmError.CAMPO_LABEL, ApmError.CAMPOVALOROPCION_LABEL);
		}

		if (hasError()) {
			return false;
		}
		return true;
	}
	
	private boolean validate() throws Exception {
		// Validaciones
		if (StringUtil.isNullOrEmpty(getEtiqueta())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.CAMPO_ETIQUETA);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}
}