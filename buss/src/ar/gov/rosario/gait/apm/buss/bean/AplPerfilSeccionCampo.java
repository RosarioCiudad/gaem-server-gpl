package ar.gov.rosario.gait.apm.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.buss.dao.UniqueMap;
import coop.tecso.demoda.iface.model.Estado;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "apm_aplPerfilSeccionCampo")
public class AplPerfilSeccionCampo extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
    @JoinColumn(name = "idAplicacionPerfilSeccion")
	private AplicacionPerfilSeccion aplicacionPerfilSeccion;

	@ManyToOne(optional = false)
    @JoinColumn(name = "idCampo")
	private Campo campo;

	@Column(nullable = false)
	private Integer orden;

	@Column
	private Integer soloLectura;
	
	@OneToMany()
	@JoinColumn(name="idAplPerfilSeccionCampo")
	@Where(clause="estado = 1")
	@OrderBy("orden")
	private List<AplPerfilSeccionCampoValor> listAplPerfilSeccionCampoValor;


	// Metodos de Clase
	public static AplPerfilSeccionCampo getById(Long id) {
		return (AplPerfilSeccionCampo) ApmDAOFactory.getAplPerfilSeccionCampoDAO().getById(id);
	}
	
	public static AplPerfilSeccionCampo getByIdNull(Long id) {
		return (AplPerfilSeccionCampo) ApmDAOFactory.getAplPerfilSeccionCampoDAO().getByIdNull(id);
	}
	
	public static List<AplPerfilSeccionCampo> getList() {
		return ApmDAOFactory.getAplPerfilSeccionCampoDAO().getList();
	}
	
	public static List<AplPerfilSeccionCampo> getListActivos() {
		return ApmDAOFactory.getAplPerfilSeccionCampoDAO().getListActiva();
	}


	// Getters y Setters
	public AplicacionPerfilSeccion getAplicacionPerfilSeccion() {
		return aplicacionPerfilSeccion;
	}

	public void setAplicacionPerfilSeccion(AplicacionPerfilSeccion aplicacionPerfilSeccion) {
		this.aplicacionPerfilSeccion = aplicacionPerfilSeccion;
	}

	public Campo getCampo() {
		return campo;
	}

	public void setCampo(Campo campo) {
		this.campo = campo;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getSoloLectura() {
		return soloLectura;
	}

	public void setSoloLectura(Integer soloLectura) {
		this.soloLectura = soloLectura;
	}

	public List<AplPerfilSeccionCampoValor> getListAplPerfilSeccionCampoValor() {
		return listAplPerfilSeccionCampoValor;
	}

	public void setListAplPerfilSeccionCampoValor(List<AplPerfilSeccionCampoValor> listAplPerfilSeccionCampoValor) {
		this.listAplPerfilSeccionCampoValor = listAplPerfilSeccionCampoValor;
	}

	
	// Validaciones
	public boolean validateCreate() throws Exception {
		// limpiamos la lista de errores
		clearError();

		if (!this.validate()) {
			return false;
		}
		
		UniqueMap uniqueMap = new UniqueMap();
		uniqueMap.addInteger("orden");
		uniqueMap.addEntity("aplicacionPerfilSeccion");
		uniqueMap.addLong("estado", 1L);
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.APLICACIONPERFIL_ORDEN);			
		}
		
		if (this.hasError()) {
			return false;
		}

		// Validaciones de Negocio

		return true;
	}


	public boolean validateUpdate() throws Exception {
		// limpiamos la lista de errores
		clearError();
		
		UniqueMap uniqueMap = new UniqueMap();
		uniqueMap.addInteger("orden");
		uniqueMap.addEntity("aplicacionPerfilSeccion");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.APLICACIONPERFIL_ORDEN);			
		}
		

		if (!this.validate()) {
			return false;
		}

		// Validaciones de Negocio

		return true;
	}

	public boolean validateDelete() {
		// limpiamos la lista de errores
		clearError();

		if (GenericDAO.hasReferenceActivo(this, AplPerfilSeccionCampoValor.class, "aplPerfilSeccionCampo")) {
			addRecoverableError(BaseError.MSG_ELIMINAR_REGISTROS_ASOCIADOS,
					ApmError.APLPERFILSECCIONCAMPO_LABEL, ApmError.APLPERFILSECCIONCAMPOVALOR_LABEL);
		}
		
		
		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {
		// Validaciones
		if (null == getOrden()) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.APLICACIONPERFIL_ORDEN);
		}
		
		if(null == getCampo()){
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.CAMPO_LABEL);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}
}