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
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import coop.tecso.demoda.buss.bean.VersionableBO;

@Entity
@Table(name="apm_hisEstPan")
public class HisEstPan extends VersionableBO {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional = false)  
	@JoinColumn(name = "idPanico")
	private Panico panico;
	
	@ManyToOne(optional = false)  
	@JoinColumn(name = "idEstadoPanico")
	private EstadoPanico estadoPanico;
	
	@Column
	private Date fecha;
	
	@Column
	private String logCambios;

	@Column
	private String observaciones;

	// Constructores
	public HisEstPan() {
		super();
	}

	// Getters y Setters
	public Panico getPanico() {
		return panico;
	}

	public void setPanico(Panico panico) {
		this.panico = panico;
	}

	public EstadoPanico getEstadoPanico() {
		return estadoPanico;
	}

	public void setEstadoPanico(EstadoPanico estadoPanico) {
		this.estadoPanico = estadoPanico;
	}

	public String getLogCambios() {
		return logCambios;
	}

	public void setLogCambios(String logCambios) {
		this.logCambios = logCambios;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	// Metodos de Clase
	public static EstadoPanico getById(Long id) {
		return (EstadoPanico) ApmDAOFactory.getEstadoPanicoDAO().getById(id);
	}

	public static EstadoPanico getByIdNull(Long id) {
		return (EstadoPanico) ApmDAOFactory.getEstadoPanicoDAO().getByIdNull(id);
	}

	public static List<EstadoPanico> getList() {
		return ApmDAOFactory.getEstadoPanicoDAO().getList();
	}

	public static List<EstadoPanico> getListActivos() {			
		return ApmDAOFactory.getEstadoPanicoDAO().getListActiva();
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

//		if (GenericDAO.hasReference(this, ${BeanRelacionado}.class, "estadoPanico")) {
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
//		if (StringUtil.isNullOrEmpty(getCodEstadoPanico())) {
//			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.${BEAN}_COD${BEAN} );
//		}
//
//		if (StringUtil.isNullOrEmpty(getDesEstadoPanico())){
//			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.${BEAN}_DES${BEAN});
//		}
		if (hasError()) {
			return false;
		}

		// Validaciones de unique
//		UniqueMap uniqueMap = new UniqueMap();
//		uniqueMap.addString("codEstadoPanico");
//		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
//			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.${BEAN}_COD${BEAN});			
//		}
		return true;
	}
//
//	// Metodos de negocio
//
//	/**
//	 * Activa el EstadoPanico. Previamente valida la activacion. 
//	 *
//	 */
//	public void activar(){
//		if(!this.validateActivar()){
//			return;
//		}
//		this.setEstado(Estado.ACTIVO.getId());
//		ApmDAOFactory.getEstadoPanicoDAO().update(this);
//	}
//
//	/**
//	 * Desactiva el EstadoPanico. Previamente valida la desactivacion. 
//	 *
//	 */
//	public void desactivar(){
//		if(!this.validateDesactivar()){
//			return;
//		}
//		this.setEstado(Estado.INACTIVO.getId());
//		ApmDAOFactory.getEstadoPanicoDAO().update(this);
//	}
//
//	/**
//	 * Valida la activacion del EstadoPanico
//	 * @return boolean
//	 */
//	private boolean validateActivar(){
//		//limpiamos la lista de errores
//		clearError();
//
//		//Validaciones 
//		return true;
//	}
//
//	/**
//	 * Valida la desactivacion del EstadoPanico
//	 * @return boolean
//	 */
//	private boolean validateDesactivar(){
//		//limpiamos la lista de errores
//		clearError();
//
//		//Validaciones 
//		return true;
//	}
}