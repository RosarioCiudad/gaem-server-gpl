package ar.gov.rosario.gait.apm.buss.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.helper.StringUtil;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "apm_aplicacionBinarioVersion")
public class AplicacionBinarioVersion extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idAplicacion")
	private Aplicacion aplicacion;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idTipoVersion")
	private AplicacionTipoBinario aplicacionTipoBinario;

	@Column
	private String descripcion;

	@Column
	private Date fecha;

	@Column
	private String ubicacion;

	public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public AplicacionTipoBinario getAplicacionTipoBinario() {
		return aplicacionTipoBinario;
	}

	public void setAplicacionTipoBinario(AplicacionTipoBinario aplicacionTipoBinario) {
		this.aplicacionTipoBinario = aplicacionTipoBinario;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	// Metodos de Clase
	public static AplicacionBinarioVersion getById(Long id) {
		return (AplicacionBinarioVersion) ApmDAOFactory.getAplicacionBinarioVersionDAO().getById(id);
	}

	public static AplicacionBinarioVersion getByIdNull(Long id) {
		return (AplicacionBinarioVersion) ApmDAOFactory.getAplicacionBinarioVersionDAO().getByIdNull(id);
	}

	public static List<AplicacionBinarioVersion> getList() {
		return ApmDAOFactory.getAplicacionBinarioVersionDAO().getList();
	}

	public static List<AplicacionBinarioVersion> getListActivos() {			
		return ApmDAOFactory.getAplicacionBinarioVersionDAO().getListActiva();
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

	public boolean validateDelete() throws Exception{
		// limpiamos la lista de errores
		clearError();

		if (!this.validate()) {
			return false;
		}



		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {

		// Validaciones
		if (StringUtil.isNullOrEmpty(getDescripcion())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					ApmError.APLICACIONBINARIOVERSION_DESCRIPCION);
		}



		if (hasError()) {
			return false;
		}



		return true;
	}
}