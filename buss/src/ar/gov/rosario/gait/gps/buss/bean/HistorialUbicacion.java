package ar.gov.rosario.gait.gps.buss.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.apm.buss.bean.UsuarioApm;
import ar.gov.rosario.gait.def.buss.bean.Area;
import ar.gov.rosario.gait.gps.buss.dao.GpsDAOFactory;
import coop.tecso.demoda.buss.bean.BaseBO;
import coop.tecso.demoda.iface.model.Estado;


/**
 * Bean correspondiente a HistorialUbicacion - 
 * Captura de ubicaciones enviadas desde dispositivo movil
 * 
 * @author tecso.coop
 */
@Entity
@Table(name = "gps_historialUbicacion")
public class HistorialUbicacion extends BaseBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idDispositivoMovil")
	private DispositivoMovil dispositivoMovil;

	@ManyToOne(optional = false)  
	@JoinColumn(name="idUsuarioApm")
	private UsuarioApm usuarioApm;

	@ManyToOne(optional = false)  
	@JoinColumn(name="idArea")
	private Area area;

	@Column(nullable = false)
	private Date fechaPosicion;

	@Column(nullable = false)
	private Date fechaUbicacion;

	@Column(nullable = false)
	private double latitud;

	@Column(nullable = false)
	private double longitud;

	@Column(nullable = false)
	private double precision;

	@Column(nullable = false)
	private String origen;

	@Column(nullable = false)
	private double velocidad;

	@Column(nullable = false)
	private double radio;

	@Column(nullable = false)
	private double altitud;
	
	@Column
	private double nivelBateria;

	@Column
	private double nivelSenial;
	
	// Constructores
	public HistorialUbicacion(){
		super();
	}

	public HistorialUbicacion(Long id){
		super();
		setId(id);
	}

	// Metodos de Clase
	public static HistorialUbicacion getById(Long id) {
		return (HistorialUbicacion) GpsDAOFactory.getHistorialUbicacionDAO().getById(id);
	}

	public static HistorialUbicacion getByIdNull(Long id) {
		return (HistorialUbicacion) GpsDAOFactory.getHistorialUbicacionDAO().getByIdNull(id);
	}

	public static List<HistorialUbicacion> getList() {
		return  GpsDAOFactory.getHistorialUbicacionDAO().getList();
	}

	public static List<HistorialUbicacion> getListActivos() {			
		return  GpsDAOFactory.getHistorialUbicacionDAO().getListActiva();
	}

	// Getters y setters
	public DispositivoMovil getDispositivoMovil() {
		return dispositivoMovil;
	}

	public void setDispositivoMovil(DispositivoMovil dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}

	public UsuarioApm getUsuarioApm() {
		return usuarioApm;
	}

	public void setUsuarioApm(UsuarioApm usuarioApm) {
		this.usuarioApm = usuarioApm;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Date getFechaPosicion() {
		return fechaPosicion;
	}

	public void setFechaPosicion(Date fechaPosicion) {
		this.fechaPosicion = fechaPosicion;
	}

	public Date getFechaUbicacion() {
		return fechaUbicacion;
	}

	public void setFechaUbicacion(Date fechaUbicacion) {
		this.fechaUbicacion = fechaUbicacion;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getPrecision() {
		return precision;
	}

	public void setPrecision(double precision) {
		this.precision = precision;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	public double getRadio() {
		return radio;
	}

	public void setRadio(double radio) {
		this.radio = radio;
	}

	public double getAltitud() {
		return altitud;
	}

	public void setAltitud(double altitud) {
		this.altitud = altitud;
	}
	
	public double getNivelBateria() {
		return nivelBateria;
	}

	public void setNivelBateria(double nivelBateria) {
		this.nivelBateria = nivelBateria;
	}

	public double getNivelSenial() {
		return nivelSenial;
	}

	public void setNivelSenial(double nivelSenial) {
		this.nivelSenial = nivelSenial;
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
		//limpiamos la lista de errores
		clearError();

		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {

		//	Validaciones 
		/*
		if (StringUtil.isNullOrEmpty(getCodHistorialUbicacion())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, GpsError.${BEAN}_COD${BEAN} );
		}

		if (StringUtil.isNullOrEmpty(getDesHistorialUbicacion())){
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, GpsError.${BEAN}_DES${BEAN});
		}

		if (hasError()) {
			return false;
		}

		// Validaciones de unique
		UniqueMap uniqueMap = new UniqueMap();
		uniqueMap.addString("codHistorialUbicacion");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, GpsError.${BEAN}_COD${BEAN});			
		}*/

		return true;
	}

	// Metodos de negocio

	/**
	 * Activa el HistorialUbicacion. Previamente valida la activacion. 
	 *
	 */
	public void activar(){
		if(!this.validateActivar()){
			return;
		}
		this.setEstado(Estado.ACTIVO.getId());
		GpsDAOFactory.getHistorialUbicacionDAO().update(this);
	}

	/**
	 * Desactiva el HistorialUbicacion. Previamente valida la desactivacion. 
	 *
	 */
	public void desactivar(){
		if(!this.validateDesactivar()){
			return;
		}
		this.setEstado(Estado.INACTIVO.getId());
		GpsDAOFactory.getHistorialUbicacionDAO().update(this);
	}

	/**
	 * Valida la activacion del HistorialUbicacion
	 * @return boolean
	 */
	private boolean validateActivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}

	/**
	 * Valida la desactivacion del HistorialUbicacion
	 * @return boolean
	 */
	private boolean validateDesactivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}
}