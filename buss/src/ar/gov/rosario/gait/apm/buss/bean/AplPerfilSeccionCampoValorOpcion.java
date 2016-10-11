package ar.gov.rosario.gait.apm.buss.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.buss.dao.UniqueMap;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "apm_aplPerfilSeccionCampoValorOpcion")
public class AplPerfilSeccionCampoValorOpcion extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
    @JoinColumn(name = "idAplPerfilSeccionCampoValor")
	private AplPerfilSeccionCampoValor aplPerfilSeccionCampoValor;

	@ManyToOne(optional = false)  
    @JoinColumn(name = "idCampoValorOpcion")
	private CampoValorOpcion  campoValorOpcion;

	@Column(nullable = false)
	private Integer orden;


	// Metodos de Clase
	public static AplPerfilSeccionCampoValorOpcion getById(Long id) {
		return (AplPerfilSeccionCampoValorOpcion) ApmDAOFactory.getAplPerfilSeccionCampoValorOpcionDAO().getById(id);
	}

	public static AplPerfilSeccionCampoValorOpcion getByIdNull(Long id) {
		return (AplPerfilSeccionCampoValorOpcion) ApmDAOFactory.getAplPerfilSeccionCampoValorOpcionDAO().getByIdNull(id);
	}

	public static List<AplPerfilSeccionCampoValorOpcion> getList() {
		return (ArrayList<AplPerfilSeccionCampoValorOpcion>) ApmDAOFactory.getAplPerfilSeccionCampoValorOpcionDAO().getList();
	}

	public static List<AplPerfilSeccionCampoValorOpcion> getListActivos() {
		return ApmDAOFactory.getAplPerfilSeccionCampoValorOpcionDAO().getListActiva();
	}


	// Getters y setters
	public AplPerfilSeccionCampoValor getAplPerfilSeccionCampoValor() {
		return aplPerfilSeccionCampoValor;
	}

	public CampoValorOpcion getCampoValorOpcion() {
		return campoValorOpcion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setAplPerfilSeccionCampoValor(AplPerfilSeccionCampoValor aplPerfilSeccionCampoValor) {
		this.aplPerfilSeccionCampoValor = aplPerfilSeccionCampoValor;
	}

	public void setCampoValorOpcion(CampoValorOpcion campoValorOpcion) {
		this.campoValorOpcion = campoValorOpcion;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
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
		uniqueMap.addEntity("aplPerfilSeccionCampoValor");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.APLICACIONPERFIL_ORDEN);			
		}
		
		if (hasError()) {
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
		
		UniqueMap uniqueMap = new UniqueMap();
		uniqueMap.addInteger("orden");
		uniqueMap.addEntity("aplPerfilSeccionCampo");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.APLICACIONPERFIL_ORDEN);			
		}

		// Validaciones de Negocio
		if (hasError()) {
			return false;
		}

		return true;
	}

	public boolean validateDelete() {
		// limpiamos la lista de errores
		clearError();

		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {

		// Validaciones
		if (null == getOrden()) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					ApmError.APLICACIONPERFIL_ORDEN);
		}



		if (hasError()) {
			return false;
		}



		return true;
	}

	
}