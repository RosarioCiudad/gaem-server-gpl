package ar.gov.rosario.gait.not.buss.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.bean.Aplicacion;
import ar.gov.rosario.gait.apm.buss.bean.DispositivoMovil;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.not.buss.dao.NotDAOFactory;
import ar.gov.rosario.gait.not.iface.util.NotError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.helper.StringUtil;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "not_notificacion")
public class Notificacion extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idTipoNotificacion")
	private TipoNotificacion tipoNotificacion;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idEstadoNotificacion")
	private EstadoNotificacion estadoNotificacion;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idAplicacion")
	private Aplicacion aplicacion;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "idDispositivoMovil")
	private DispositivoMovil dispositivoMovil;

	@Column
	private String descripcionReducida;

	@Column
	private String descripcionAmpliada;

	@Column(nullable = true)
	private Date fechaEnvio;

	@Column(nullable = true)
	private Date fechaRecepcion;

	@Column(nullable = true)
	private Date fechaGeneracion;
	
	

	public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public String getDescripcionAmpliada() {
		return descripcionAmpliada;
	}

	public String getDescripcionReducida() {
		return descripcionReducida;
	}

	public EstadoNotificacion getEstadoNotificacion() {
		return estadoNotificacion;
	}


	public TipoNotificacion getTipoNotificacion() {
		return tipoNotificacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public void setDescripcionAmpliada(String descripcionAmpliada) {
		this.descripcionAmpliada = descripcionAmpliada;
	}

	public void setDescripcionReducida(String descripcionReducida) {
		this.descripcionReducida = descripcionReducida;
	}

	public void setEstadoNotificacion(EstadoNotificacion estadoNotificacion) {
		this.estadoNotificacion = estadoNotificacion;
	}



	public void setTipoNotificacion(TipoNotificacion tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}

	// Metodos de Clase
	public static Notificacion getById(Long id) {
		return (Notificacion) NotDAOFactory.getNotificacionDAO().getById(id);
	}

	@SuppressWarnings("unchecked")
	public static List<Notificacion> getList() {
		return (ArrayList<Notificacion>) NotDAOFactory.getNotificacionDAO().getList();
	}

	@SuppressWarnings("unchecked")
	public static List<Notificacion> getListActivos() {
		return NotDAOFactory.getNotificacionDAO().getListActiva();
	}
	
	public DispositivoMovil getDispositivoMovil() {
		return dispositivoMovil;
	}

	public void setDispositivoMovil(DispositivoMovil dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}

	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}

	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
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

	private boolean validate() throws Exception {

		// Validaciones
		if (StringUtil.isNullOrEmpty(getDescripcionReducida())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					NotError.NOTIFICACION_DESCRIPCIONREDUCIDA);
		}
		
		if (StringUtil.isNullOrEmpty(getDescripcionAmpliada())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					NotError.NOTIFICACION_DESCRIPCIONAMPLIADA);
		}


		if (tipoNotificacion == null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					NotError.NOTIFICACION_TIPONOTIFICACION);
		}

		if (aplicacion == null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					NotError.NOTIFICACION_APLICACION);
		}

		if (dispositivoMovil == null) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					NotError.NOTIFICACION_DISPOSITIVOMOVIL);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}





}