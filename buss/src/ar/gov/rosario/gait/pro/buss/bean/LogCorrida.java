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
package ar.gov.rosario.gait.pro.buss.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.pro.buss.dao.ProDAOFactory;
import coop.tecso.demoda.buss.bean.BaseBO;
import coop.tecso.demoda.iface.model.Estado;


/**
 * Bean correspondiente a LogCorrida
 * 
 * @author tecso
 */
@Entity
@Table(name = "pro_logcorrida")
public class LogCorrida extends BaseBO {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional=false)
    @JoinColumn(name="idCorrida") 
	private Corrida corrida;
	
	@Column(name = "paso")
	private Long paso;
	
	@Column(name = "log")
	private String log;
	
	
	// Constructores
	public LogCorrida(){
		super();
	}
	
	/**
	 * Constructor Sobrecargado
	 *
	 * @param id
	 * @param corrida
	 * @param nivel
	 * @param log
	 */
	public LogCorrida(Corrida corrida, Long paso, String log) {
		this.corrida = corrida;
		this.paso = paso;
		this.log = log;
	}
	
	
	// Getters y setters
	public Corrida getCorrida() {
		return corrida;
	}

	public void setCorrida(Corrida corrida) {
		this.corrida = corrida;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public Long getPaso() {
		return paso;
	}

	public void setPaso(Long paso) {
		this.paso = paso;
	}

	
	// Metodos de Clase
	public static LogCorrida getById(Long id) {
		return (LogCorrida) ProDAOFactory.getLogCorridaDAO().getById(id);
	}
	
	public static LogCorrida getByIdNull(Long id) {
		return (LogCorrida) ProDAOFactory.getLogCorridaDAO().getByIdNull(id);
	}
	
	public static List<LogCorrida> getList() {
		return (ArrayList<LogCorrida>) ProDAOFactory.getLogCorridaDAO().getList();
	}
	
	public static List<LogCorrida> getListActivos() {			
		return ProDAOFactory.getLogCorridaDAO().getListActiva();
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
	
		if (hasError()) {
			return false;
		}
		
		// Validaciones de unique
	
		return true;
	}
	
	// Metodos de negocio
	
	/**
	 * Activa el LogCorrida. Previamente valida la activacion. 
	 *
	 */
	public void activar(){
		if(!this.validateActivar()){
			return;
		}
		this.setEstado(Estado.ACTIVO.getId());
		ProDAOFactory.getLogCorridaDAO().update(this);
	}

	/**
	 * Desactiva el LogCorrida. Previamente valida la desactivacion. 
	 *
	 */
	public void desactivar(){
		if(!this.validateDesactivar()){
			return;
		}
		this.setEstado(Estado.INACTIVO.getId());
		ProDAOFactory.getLogCorridaDAO().update(this);
	}
	
	/**
	 * Valida la activacion del LogCorrida
	 * @return boolean
	 */
	private boolean validateActivar(){
		//limpiamos la lista de errores
		clearError();
		
		//Validaciones 
		return true;
	}
	
	/**
	 * Valida la desactivacion del LogCorrida
	 * @return boolean
	 */
	private boolean validateDesactivar(){
		//limpiamos la lista de errores
		clearError();
		
		//Validaciones 
		return true;
	}
}
