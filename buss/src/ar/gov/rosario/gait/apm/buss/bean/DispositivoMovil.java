package ar.gov.rosario.gait.apm.buss.bean;

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
import ar.gov.rosario.gait.def.buss.bean.Area;
import ar.gov.rosario.gait.def.iface.util.DefError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.buss.dao.UniqueMap;
import coop.tecso.demoda.iface.helper.StringUtil;

@Entity
@Table(name="apm_dispositivoMovil")
public class DispositivoMovil extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idArea")
	private Area area;

	@Column(nullable = false)
	private String descripcion;

	@Column
	private String numeroSerie;

	@Column(nullable = false)
	private String numeroLinea; 

	@Column(nullable = false)
	private String numeroIMEI;
	//

	@Column()
	private String emailAddress;

	@Column
	private Integer forzarActualizacion;

	// Constructores
	public DispositivoMovil() {
		super();
	}
	public DispositivoMovil(Long id){
		super();
		setId(id);
	}

	// Metodos de Clase
	public static DispositivoMovil getById(Long id) {
		return (DispositivoMovil) ApmDAOFactory.getDispositivoMovilDAO().getById(id);
	}

	public static DispositivoMovil getByIdNull(Long id) {
		return (DispositivoMovil) ApmDAOFactory.getDispositivoMovilDAO().getByIdNull(id);
	}

	public static List<DispositivoMovil> getList() {
		return ApmDAOFactory.getDispositivoMovilDAO().getList();
	}

	public static List<DispositivoMovil> getListActivos() {			
		return ApmDAOFactory.getDispositivoMovilDAO().getListActiva();
	}

	public static DispositivoMovil getByEmailAddress(String deviceID) {
		return ApmDAOFactory.getDispositivoMovilDAO().getByEmailAddress(deviceID);
	}

	public static DispositivoMovil getByNumeroIMEI(String deviceID) {
		return ApmDAOFactory.getDispositivoMovilDAO().getByNumeroIMEI(deviceID);
	}

	// Getters y Setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getNumeroLinea() {
		return numeroLinea;
	}

	public void setNumeroLinea(String numeroLinea) {
		this.numeroLinea = numeroLinea;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Integer getForzarActualizacion() {
		return forzarActualizacion;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getNumeroIMEI() {
		return numeroIMEI;
	}

	public void setNumeroIMEI(String numeroIMEI) {
		this.numeroIMEI = numeroIMEI;
	}

	public void setForzarActualizacion(Integer forzarActualizacion) {
		this.forzarActualizacion = forzarActualizacion;
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
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.DISPOSITIVOMOVIL_DESCRIPCION);
		}

		if (StringUtil.isNullOrEmpty(getNumeroIMEI())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.DISPOSITIVOMOVIL_IMEI);
		}

		if (getArea() == null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, DefError.AREA_LABEL);
		}

		if (hasError()) {
			return false;
		}

		// Validaciones de unique
		UniqueMap uniqueMap = new UniqueMap();
		//		uniqueMap.addString("emailAddress");
		//		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
		//			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.DISPOSITIVOMOVIL_EMAIL);			
		//		}

		uniqueMap = new UniqueMap();
		uniqueMap.addString("numeroLinea");
		uniqueMap.addInteger("estado");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.DISPOSITIVOMOVIL_NUMEROLINEA);			
		}

		uniqueMap = new UniqueMap();
		uniqueMap.addString("numeroIMEI");
		uniqueMap.addInteger("estado");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.DISPOSITIVOMOVIL_IMEI);			
		}

		if (hasError()) {
			return false;
		}

		return true;
	}


}