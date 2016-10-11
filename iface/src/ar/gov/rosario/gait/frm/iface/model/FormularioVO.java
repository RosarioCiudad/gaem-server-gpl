package ar.gov.rosario.gait.frm.iface.model;

import java.util.Date;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilVO;
import ar.gov.rosario.gait.apm.iface.model.UsuarioApmVO;
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.helper.StringUtil;
import coop.tecso.demoda.iface.model.SiNo;

public class FormularioVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "formularioVO";
	
	private AplicacionPerfilVO tipoFormularioDef = new AplicacionPerfilVO();

	private EstadoTipoFormularioVO estadoTipoFormulario = new EstadoTipoFormularioVO();

	private TipoFormularioVO tipoFormulario = new TipoFormularioVO();
	
	private DispositivoMovilVO dispositivoMovil = new DispositivoMovilVO();

	private UsuarioApmVO usuarioCierre = new UsuarioApmVO();
	
	private MotivoAnulacionTipoFormularioVO motivoAnulacionTipoFormulario = new MotivoAnulacionTipoFormularioVO();
	
	private MotivoCierreTipoFormularioVO motivoCierreTipoFormulario = new MotivoCierreTipoFormularioVO();
	
	private AreaVO area = new AreaVO();

	private String observacion = "";
	
	private String codigoRespuesta = "";

	private Date fechaInicio;

	private String numero;

	private String domicilio;

	private Date fechaCierre;

	private Date fechaTransmision;

	private Integer cantidadImpresiones;

	private Double latitud;
	
	private Double longitud;
	
	private Double precision;
	
	private Date fechaMedicion;
	
	private Integer origen;
	
	private String firmaDigital = "";
	
	private String formularioDigital = "";
	
	private Integer numeroInspector;
	
	private Integer reparticion;
	
	private Date fechaImpresion;
	
	private TalonarioVO talonario;
	
	private SiNo retieneLicencia = SiNo.OpcionTodo;
	
	private String resumen = "";

	private List<FormularioDetalleVO> listFormularioDetalle;
	
	// Getters y setters
	public AplicacionPerfilVO getTipoFormularioDef() {
		return tipoFormularioDef;
	}

	public void setTipoFormularioDef(AplicacionPerfilVO tipoFormularioDef) {
		this.tipoFormularioDef = tipoFormularioDef;
	}

	public EstadoTipoFormularioVO getEstadoTipoFormulario() {
		return estadoTipoFormulario;
	}

	public void setEstadoTipoFormulario(EstadoTipoFormularioVO estadoTipoFormulario) {
		this.estadoTipoFormulario = estadoTipoFormulario;
	}

	public TipoFormularioVO getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormularioVO tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	public DispositivoMovilVO getDispositivoMovil() {
		return dispositivoMovil;
	}

	public void setDispositivoMovil(DispositivoMovilVO dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}

	public UsuarioApmVO getUsuarioCierre() {
		return usuarioCierre;
	}

	public void setUsuarioCierre(UsuarioApmVO usuarioCierre) {
		this.usuarioCierre = usuarioCierre;
	}

	public MotivoAnulacionTipoFormularioVO getMotivoAnulacionTipoFormulario() {
		return motivoAnulacionTipoFormulario;
	}

	public void setMotivoAnulacionTipoFormulario(
			MotivoAnulacionTipoFormularioVO motivoAnulacionTipoFormulario) {
		this.motivoAnulacionTipoFormulario = motivoAnulacionTipoFormulario;
	}

	public MotivoCierreTipoFormularioVO getMotivoCierreTipoFormulario() {
		return motivoCierreTipoFormulario;
	}

	public void setMotivoCierreTipoFormulario(
			MotivoCierreTipoFormularioVO motivoCierreTipoFormulario) {
		this.motivoCierreTipoFormulario = motivoCierreTipoFormulario;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Date getFechaTransmision() {
		return fechaTransmision;
	}

	public void setFechaTransmision(Date fechaTrasmision) {
		this.fechaTransmision = fechaTrasmision;
	}

	public Integer getCantidadImpresiones() {
		return cantidadImpresiones;
	}

	public void setCantidadImpresiones(Integer cantidadImpresiones) {
		this.cantidadImpresiones = cantidadImpresiones;
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

	public String getFirmaDigital() {
		return firmaDigital;
	}

	public void setFirmaDigital(String firmaDigital) {
		this.firmaDigital = firmaDigital;
	}
	
	public String getFormularioDigital() {
		return formularioDigital;
	}

	public void setFormularioDigital(String formularioDigital) {
		this.formularioDigital = formularioDigital;
	}

	public List<FormularioDetalleVO> getListFormularioDetalle() {
		return listFormularioDetalle;
	}

	public void setListFormularioDetalle(
			List<FormularioDetalleVO> listFormularioDetalle) {
		this.listFormularioDetalle = listFormularioDetalle;
	}

	public Integer getNumeroInspector() {
		return numeroInspector;
	}

	public void setNumeroInspector(Integer numeroInspector) {
		this.numeroInspector = numeroInspector;
	}

	public Integer getReparticion() {
		return reparticion;
	}

	public void setReparticion(Integer reparticion) {
		this.reparticion = reparticion;
	}

	public TalonarioVO getTalonario() {
		return talonario;
	}

	public void setTalonario(TalonarioVO talonario) {
		this.talonario = talonario;
	}

	public String getCodigoRespuesta() {
		return codigoRespuesta;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
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
	
	public AreaVO getArea() {
		return area;
	}

	public void setArea(AreaVO area) {
		this.area = area;
	}

	public SiNo getRetieneLicencia() {
		return retieneLicencia;
	}

	public void setRetieneLicencia(SiNo retieneLicencia) {
		this.retieneLicencia = retieneLicencia;
	}

	// View Getters
	
	public String getHoraInicioView() {
		return DateUtil.formatDate(fechaInicio, DateUtil.HOUR_MINUTE_MASK);
	}
	
	public String getFechaImpresionView() {
		return DateUtil.formatDate(fechaImpresion, DateUtil.ddSMMSYYYY_MASK);
	}
	
	public String getFechaInicioView() {
		return DateUtil.formatDate(fechaInicio, DateUtil.ddSMMSYYYY_HH_MM_MASK);
	}
	
	public String getFechaCierreView() {
		return DateUtil.formatDate(fechaCierre, DateUtil.ddSMMSYYYY_HH_MM_MASK);
	}
	
	public String getHoraCierreView() {
		return DateUtil.formatDate(fechaCierre, DateUtil.HOUR_MINUTE_MASK);
	}
	
	public String getFechaTransmisionView() {
		return DateUtil.formatDate(fechaTransmision, DateUtil.ddSMMSYYYY_MASK);
	}
	
	public String getFechaMedicionView() {
		return DateUtil.formatDate(fechaMedicion, DateUtil.ddSMMSYYYY_MASK);
	}
	
	public String getNumeroInspectorView(){
		return StringUtil.formatInteger(numeroInspector);
	}
	
	public String getReparticionView(){
		return StringUtil.formatInteger(reparticion);
	}
}