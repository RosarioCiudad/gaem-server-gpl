/*******************************************************************************
 * Copyright (c) 2016 Municipalidad de Rosario, Coop. de Trabajo Tecso Ltda.
 *
 * This file is part of GAEM.
 *
 * GAEM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * GAEM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAEM.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package ar.gov.rosario.gait.apm.buss.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.def.buss.bean.Area;
import coop.tecso.demoda.buss.bean.BaseBO;
import coop.tecso.demoda.iface.helper.DateUtil;
import coop.tecso.demoda.iface.model.Estado;

@Entity
@Table(name="apm_panico")
public class Panico extends BaseBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idDispositivoMovil")
	private DispositivoMovil dispositivoMovil;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idArea")
	private Area area;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idEstadoPanico")
	private EstadoPanico estadoPanico;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idUsuarioPanico")
	private UsuarioApm usuarioPanico;

	@Column(nullable = false)
	private Date fechaPosicion;

	@Column(nullable = false)
	private Date fechaPanico;

	@Column(nullable = false)
	private Date fechaAtencion;

	@Column(nullable = false)
	private Double latitud;

	@Column(nullable = false)
	private Double longitud;

	@Column(nullable = false)
	private String origen;

	@Column(nullable = false)
	private Double precision;

	@Column
	private String observacion;	
	
	@Column(nullable = false)
	private Date fechaRecepcion;

	@OneToMany()
	@JoinColumn(name="idPanico")
	private List<HisEstPan> listHisEstPan;

	// Constructores
	public Panico() {
		super();
	}

	// Metodos de Clase
	public static Panico getById(Long id) {
		return (Panico) ApmDAOFactory.getPanicoDAO().getById(id);
	}

	public static Panico getByIdNull(Long id) {
		return (Panico) ApmDAOFactory.getPanicoDAO().getByIdNull(id);
	}

	public static List<Panico> getList() {
		return ApmDAOFactory.getPanicoDAO().getList();
	}

	public static List<Panico> getListActivos() {			
		return ApmDAOFactory.getPanicoDAO().getListActiva();
	}
	
	// Getters y Setters
	public DispositivoMovil getDispositivoMovil() {
		return dispositivoMovil;
	}

	public void setDispositivoMovil(DispositivoMovil dispositivoMovil) {
		this.dispositivoMovil = dispositivoMovil;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public EstadoPanico getEstadoPanico() {
		return estadoPanico;
	}

	public void setEstadoPanico(EstadoPanico estadoPanico) {
		this.estadoPanico = estadoPanico;
	}

	public UsuarioApm getUsuarioPanico() {
		return usuarioPanico;
	}

	public void setUsuarioPanico(UsuarioApm usuarioPanico) {
		this.usuarioPanico = usuarioPanico;
	}

	public Date getFechaPosicion() {
		return fechaPosicion;
	}

	public void setFechaPosicion(Date fechaPosicion) {
		this.fechaPosicion = fechaPosicion;
	}

	public Date getFechaPanico() {
		return fechaPanico;
	}

	public void setFechaPanico(Date fechaPanico) {
		this.fechaPanico = fechaPanico;
	}

	public Date getFechaAtencion() {
		return fechaAtencion;
	}

	public void setFechaAtencion(Date fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
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

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public Double getPrecision() {
		return precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}

	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}



	public List<HisEstPan> getListHisEstPan() {
		return listHisEstPan;
	}

	public void setListHisEstPan(List<HisEstPan> listHisEstPan) {
		this.listHisEstPan = listHisEstPan;
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

//		if (GenericDAO.hasReference(this, HisEstPan.class, "panico")) {
//			addRecoverableError(BaseError.MSG_ELIMINAR_REGISTROS_ASOCIADOS,
//					ApmError.${BEAN}_LABEL, ApmError.${BEAN_RELACIONADO}_LABEL );
//		}

		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {

		//	Validaciones        
//		if (StringUtil.isNullOrEmpty(getCodPanico())) {
//			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.${BEAN}_COD${BEAN} );
//		}
//
//		if (StringUtil.isNullOrEmpty(getDesPanico())){
//			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.${BEAN}_DES${BEAN});
//		}

		if (hasError()) {
			return false;
		}

		// Validaciones de unique
//		UniqueMap uniqueMap = new UniqueMap();
//		uniqueMap.addString("codPanico");
//		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
//			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.${BEAN}_COD${BEAN});			
//		}

		return true;
	}

	// Metodos de negocio

	/**
	 * Activa el Panico. Previamente valida la activacion. 
	 *
	 */
	public void activar(){
		if(!this.validateActivar()){
			return;
		}
		this.setEstado(Estado.ACTIVO.getId());
		ApmDAOFactory.getPanicoDAO().update(this);
	}

	/**
	 * Desactiva el Panico. Previamente valida la desactivacion. 
	 *
	 */
	public void desactivar(){
		if(!this.validateDesactivar()){
			return;
		}
		this.setEstado(Estado.INACTIVO.getId());
		ApmDAOFactory.getPanicoDAO().update(this);
	}

	/**
	 * Valida la activacion del Panico
	 * @return boolean
	 */
	private boolean validateActivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}

	/**
	 * Valida la desactivacion del Panico
	 * @return boolean
	 */
	private boolean validateDesactivar(){
		//limpiamos la lista de errores
		clearError();

		//Validaciones 
		return true;
	}
}