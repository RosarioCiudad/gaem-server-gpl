package ar.gov.rosario.gait.frm.buss.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.bean.AplicacionPerfil;
import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.apm.buss.bean.UsuarioApm;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.def.buss.bean.Area;
import ar.gov.rosario.gait.frm.buss.dao.FrmDAOFactory;
import ar.gov.rosario.gait.frm.iface.util.FrmError;
import coop.tecso.demoda.buss.bean.BaseBO;
import coop.tecso.demoda.iface.model.Estado;

@Entity
@Table(name="for_formulario")
public class Formulario extends BaseBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idTipoFormularioDef")
	private AplicacionPerfil tipoFormularioDef;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idEstadoTipoFormulario")
	private EstadoTipoFormulario estadoTipoFormulario;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idTipoFormulario")
	private TipoFormulario tipoFormulario;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idDispositivoMovil")
	private DispositivoMovil dispositivoMovil;

	@ManyToOne(optional = false)  
	@JoinColumn(name="idUsuarioApmCierre")
	private UsuarioApm usuarioCierre;

	@ManyToOne()  
	@JoinColumn(name="idMotivoAnulacionTipoFormulario")
	private MotivoAnulacionTipoFormulario motivoAnulacionTipoFormulario;

	@ManyToOne()  
	@JoinColumn(name="idMotivoCierreTipoFormulario")
	private MotivoCierreTipoFormulario motivoCierreTipoFormulario;

	@ManyToOne()  
	@JoinColumn(name="idTalonario")
	private Talonario talonario;

	@ManyToOne()  
	@JoinColumn(name="idArea")
	private Area area;

	@Column
	private String observacion;

	@Column
	private String codigoRespuesta;

	@Column
	private Date fechaInicio;

	@Column
	private String numero;

	@Column
	private String domicilio;

	@Column
	private Date fechaCierre;

	@Column
	private Date fechaTransmision;

	@Column
	private Integer cantidadImpresiones;

	@Column
	private Double latitud;

	@Column
	private Double longitud;

	@Column
	private Double precision;

	@Column
	private Date fechaMedicion;

	@Column
	private Integer origen;

	@Column
	private byte[] firmaDigital;

	@Column
	private byte[] formularioDigital;

	@Column
	private Integer numeroInspector;

	@Column
	private Date fechaImpresion;

	@Column
	private Integer retieneLicencia;
	
	@Column
	private String resumen;

	@OneToMany()
	@JoinColumn(name="idFormulario")
	private List<FormularioDetalle> listFormularioDetalle;

	// Metodos de Clase
	public static Formulario getById(Long id) {
		return (Formulario) FrmDAOFactory.getFormularioDAO().getById(id);
	}

	public static Formulario getByIdNull(Long id) {
		return (Formulario) FrmDAOFactory.getFormularioDAO().getByIdNull(id);
	}

	public static List<Formulario> getList() {
		return FrmDAOFactory.getFormularioDAO().getList();
	}

	public static List<Formulario> getListActivos() {			
		return FrmDAOFactory.getFormularioDAO().getListActiva();
	}

	public List<FormularioDetalle> getFormularioDetalleByCodigo(String codigo) {
		return FrmDAOFactory.getFormularioDetalleDAO().getByFormularioAndCodigo(this, codigo);
	}

	public List<FormularioDetalle> getListFormularioDetalleByCodigo(String codigo) {
		return FrmDAOFactory.getFormularioDetalleDAO().getListByFormularioAndCodigo(this, codigo);
	}

	// Constructores
	public Formulario(){
		super();
		// Seteo de valores default			
	}

	public Formulario(Long id){
		super();
		setId(id);
	}

	// Getters y Setters
	public EstadoTipoFormulario getEstadoFormulario() {
		return estadoTipoFormulario;
	}

	public AplicacionPerfil getTipoFormularioDef() {
		return tipoFormularioDef;
	}

	public void setTipoFormularioDef(AplicacionPerfil tipoFormularioDef) {
		this.tipoFormularioDef = tipoFormularioDef;
	}

	public void setEstadoFormulario(EstadoTipoFormulario estadoTipoFormulario) {
		this.estadoTipoFormulario = estadoTipoFormulario;
	}

	public DispositivoMovil getDispositivoMovil() {
		return dispositivoMovil;
	}

	public void setDispositivoMovil(DispositivoMovil dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}

	public UsuarioApm getUsuarioCierre() {
		return usuarioCierre;
	}

	public void setUsuarioCierre(UsuarioApm usuarioCierre) {
		this.usuarioCierre = usuarioCierre;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public Date getFechaTransmision() {
		return fechaTransmision;
	}

	public void setFechaTransmision(Date fechaTransmision) {
		this.fechaTransmision = fechaTransmision;
	}

	public Integer getCantidadImpresiones() {
		return cantidadImpresiones;
	}

	public void setCantidadImpresiones(Integer cantidadImpresiones) {
		this.cantidadImpresiones = cantidadImpresiones;
	}

	public List<FormularioDetalle> getListFormularioDetalle() {
		return listFormularioDetalle;
	}

	public void setListFormularioDetalle(List<FormularioDetalle> listFormularioDetalle) {
		this.listFormularioDetalle = listFormularioDetalle;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getPrecision() {
		return precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}

	public Date getFechaMedicion() {
		return fechaMedicion;
	}

	public void setFechaMedicion(Date fechaMedicion) {
		this.fechaMedicion = fechaMedicion;
	}

	public Integer getOrigen() {
		return origen;
	}

	public void setOrigen(Integer origen) {
		this.origen = origen;
	}

	public byte[] getFirmaDigital() {
		return firmaDigital;
	}

	public void setFirmaDigital(byte[] firmaDigital) {
		this.firmaDigital = firmaDigital;
	}

	public EstadoTipoFormulario getEstadoTipoFormulario() {
		return estadoTipoFormulario;
	}

	public void setEstadoTipoFormulario(EstadoTipoFormulario estadoTipoFormulario) {
		this.estadoTipoFormulario = estadoTipoFormulario;
	}

	public TipoFormulario getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormulario tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	public MotivoAnulacionTipoFormulario getMotivoAnulacionTipoFormulario() {
		return motivoAnulacionTipoFormulario;
	}

	public void setMotivoAnulacionTipoFormulario(
			MotivoAnulacionTipoFormulario motivoAnulacionTipoFormulario) {
		this.motivoAnulacionTipoFormulario = motivoAnulacionTipoFormulario;
	}

	public MotivoCierreTipoFormulario getMotivoCierreTipoFormulario() {
		return motivoCierreTipoFormulario;
	}

	public void setMotivoCierreTipoFormulario(
			MotivoCierreTipoFormulario motivoCierreTipoFormulario) {
		this.motivoCierreTipoFormulario = motivoCierreTipoFormulario;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public byte[] getFormularioDigital() {
		return formularioDigital;
	}

	public void setFormularioDigital(byte[] formularioDigital) {
		this.formularioDigital = formularioDigital;
	}

	public Integer getNumeroInspector() {
		return numeroInspector;
	}

	public void setNumeroInspector(Integer numeroInspector) {
		this.numeroInspector = numeroInspector;
	}

	public Talonario getTalonario() {
		return talonario;
	}

	public void setTalonario(Talonario talonario) {
		this.talonario = talonario;
	}

	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}

	public void setCodigoRespuesta(String codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}

	public Date getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(Date fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Integer getRetieneLicencia() {
		return retieneLicencia;
	}

	public void setRetieneLicencia(Integer retieneLicencia) {
		this.retieneLicencia = retieneLicencia;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
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

		if (GenericDAO.hasReference(this, FormularioDetalle.class, "formulario")) {
			addRecoverableError(BaseError.MSG_ELIMINAR_REGISTROS_ASOCIADOS,
					FrmError.FORMULARIO_LABEL, FrmError.FORMULARIODETALLE_LABEL);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}
	
	private boolean validate() throws Exception {
		//	Validaciones        
		if(null == getFechaInicio()){
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, FrmError.FORMULARIO_FECHAINICIO );
		}

		if(null == getFechaCierre()){
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, FrmError.FORMULARIO_FECHACIERRE );
		}

		if (hasError()) {
			return false;
		}

		// Validaciones de unique
		//		UniqueMap uniqueMap = new UniqueMap();
		//		uniqueMap.addString("cod${Bean}");
		//		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
		//			addRecoverableError(BaseError.MSG_CAMPO_UNICO, FrmError.${BEAN}_COD${BEAN});			
		//		}

		return true;
	}

	// Metodos de negocio

	/**
	 * Activa el Formulario. Previamente valida la activacion. 
	 *
	 */
	public void activar(){
		if(!this.validateActivar()){
			return;
		}
		this.setEstado(Estado.ACTIVO.getId());
		FrmDAOFactory.getFormularioDAO().update(this);
	}

	/**
	 * Desactiva el Formulario. Previamente valida la desactivacion. 
	 *
	 */
	public void desactivar(){
		if(!this.validateDesactivar()){
			return;
		}
		this.setEstado(Estado.INACTIVO.getId());
		FrmDAOFactory.getFormularioDAO().update(this);
	}

	/**
	 * Valida la activacion del Formulario
	 * @return boolean
	 */
	private boolean validateActivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}

	/**
	 * Valida la desactivacion del Formulario
	 * @return boolean
	 */
	private boolean validateDesactivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}
}