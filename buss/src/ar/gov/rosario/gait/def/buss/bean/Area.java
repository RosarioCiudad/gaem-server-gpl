package ar.gov.rosario.gait.def.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.apm.buss.bean.Impresora;
import ar.gov.rosario.gait.apm.buss.bean.PerfilAcceso;
import ar.gov.rosario.gait.apm.buss.bean.TelefonoPanico;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.def.buss.dao.DefDAOFactory;
import ar.gov.rosario.gait.def.iface.util.DefError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.helper.StringUtil;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "def_area")
public class Area extends VersionableBO  {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idDireccion")
	private Direccion direccion;

	@OneToMany()
	@JoinColumn(name="idArea")
	@Where(clause = "estado = 1")
	@OrderBy("descripcion")
	private List<PerfilAcceso> listPerfilAcceso;

	@OneToMany()
	@JoinColumn(name="idArea")
	@Where(clause = "estado = 1")
	@OrderBy("descripcion")
	private List<Impresora> listImpresora;

	@OneToMany()
	@JoinColumn(name="idArea")
	@Where(clause = "estado = 1")
	@OrderBy("descripcion")
	private List<DispositivoMovil> listDispositivoMovil;

	@OneToMany()
	@JoinColumn(name="idArea")
	@Where(clause = "estado = 1")
	@OrderBy("descripcion")
	private List<TelefonoPanico> listTelefonoPanico;

	@Column
	private String descripcion;

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	// Metodos de Clase
	public static Area getById(Long id) {
		return (Area) DefDAOFactory.getAreaDAO().getById(id);
	}

	public static Area getByIdNull(Long id) {
		return (Area) DefDAOFactory.getAreaDAO().getByIdNull(id);
	}

	public static List<Area> getList() {
		return DefDAOFactory.getAreaDAO().getList();
	}

	public static List<Area> getListActivos() {
		return DefDAOFactory.getAreaDAO().getListActiva();
	}

	public List<TelefonoPanico> getListTelefonoPanico() {
		return listTelefonoPanico;
	}

	public void setListTelefonoPanico(List<TelefonoPanico> listTelefonoPanico) {
		this.listTelefonoPanico = listTelefonoPanico;
	}

	/**
	 * @return the listImpresora
	 */
	public List<Impresora> getListImpresora() {
		return listImpresora;
	}

	/**
	 * @param listImpresora the listImpresora to set
	 */
	public void setListImpresora(List<Impresora> listImpresora) {
		this.listImpresora = listImpresora;
	}

	/**
	 * @return the listDispositivoMovil
	 */
	public List<DispositivoMovil> getListDispositivoMovil() {
		return listDispositivoMovil;
	}

	/**
	 * @param listDispositivoMovil the listDispositivoMovil to set
	 */
	public void setListDispositivoMovil(List<DispositivoMovil> listDispositivoMovil) {
		this.listDispositivoMovil = listDispositivoMovil;
	}

	/**
	 * @return the listPerfilAcceso
	 */
	public List<PerfilAcceso> getListPerfilAcceso() {
		return listPerfilAcceso;
	}

	/**
	 * @param listPerfilAcceso the listPerfilAcceso to set
	 */
	public void setListPerfilAcceso(List<PerfilAcceso> listPerfilAcceso) {
		this.listPerfilAcceso = listPerfilAcceso;
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

		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {

		// Validaciones

		if (StringUtil.isNullOrEmpty(getDescripcion())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					DefError.AREA_DESAREA);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}
}