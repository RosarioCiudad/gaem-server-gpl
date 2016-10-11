package ar.gov.rosario.gait.apm.buss.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

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
@Table(name = "apm_aplicacion")
public class Aplicacion extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@Column
	private String codigo;
	
	@Column
	private String descripcion;
	
	@Column(name="package")
	private String packageName;
	
	@Column
	private String className;
	
	@OneToMany()
	@JoinColumn(name="idAplicacion")
	@Where(clause = "estado = 1")
	private List<AplicacionPerfil> listAplicacionPerfil;
	
	@OneToMany()
	@JoinColumn(name="idAplicacion")
	@Where(clause = "estado = 1")
	private List<AplicacionTabla> listAplicacionTabla;
	
	@OneToMany()
	@JoinColumn(name="idAplicacion")
	@Where(clause = "estado = 1")
	@OrderBy("fecha DESC")
	private List<AplicacionBinarioVersion> listAplicacionBinarioVersion;

	@OneToMany()
	@JoinColumn(name="idAplicacion")
	@Where(clause = "estado = 1")
	private List<AplicacionParametro> listAplicacionParametro;
	
	// Metodos de Clase
	public static Aplicacion getById(Long id) {
		return (Aplicacion) ApmDAOFactory.getAplicacionDAO().getById(id);
	}
	
	public static Aplicacion getByIdNull(Long id) {
		return (Aplicacion) ApmDAOFactory.getAplicacionDAO().getByIdNull(id);
	}
	
	public static List<Aplicacion> getList() {
		return (ArrayList<Aplicacion>) ApmDAOFactory.getAplicacionDAO().getList();
	}
	
	public static List<Aplicacion> getListActivos() {			
		return ApmDAOFactory.getAplicacionDAO().getListActiva();
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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
					ApmError.APLICACION_DESCRIPCION);
		}

	
		if (StringUtil.isNullOrEmpty(getCodigo())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					ApmError.APLICACION_CODIGO);
		}


		if (hasError()) {
			return false;
		}



		return true;
	}

	/**
	 * @return the listAplicacionPerfil
	 */
	public List<AplicacionPerfil> getListAplicacionPerfil() {
		return listAplicacionPerfil;
	}

	/**
	 * @param listAplicacionPerfil the listAplicacionPerfil to set
	 */
	public void setListAplicacionPerfil(List<AplicacionPerfil> listAplicacionPerfil) {
		this.listAplicacionPerfil = listAplicacionPerfil;
	}

	/**
	 * @return the listAplicacionTabla
	 */
	public List<AplicacionTabla> getListAplicacionTabla() {
		return listAplicacionTabla;
	}

	/**
	 * @param listAplicacionTabla the listAplicacionTabla to set
	 */
	public void setListAplicacionTabla(List<AplicacionTabla> listAplicacionTabla) {
		this.listAplicacionTabla = listAplicacionTabla;
	}

	/**
	 * @return the listAplicacionBinarioVersion
	 */
	public List<AplicacionBinarioVersion> getListAplicacionBinarioVersion() {
		return listAplicacionBinarioVersion;
	}

	/**
	 * @param listAplicacionBinarioVersion the listAplicacionBinarioVersion to set
	 */
	public void setListAplicacionBinarioVersion(
			List<AplicacionBinarioVersion> listAplicacionBinarioVersion) {
		this.listAplicacionBinarioVersion = listAplicacionBinarioVersion;
	}

	/**
	 * @return the listAplicacionParametro
	 */
	public List<AplicacionParametro> getListAplicacionParametro() {
		return listAplicacionParametro;
	}

	/**
	 * @param listAplicacionParametro the listAplicacionParametro to set
	 */
	public void setListAplicacionParametro(
			List<AplicacionParametro> listAplicacionParametro) {
		this.listAplicacionParametro = listAplicacionParametro;
	}

}