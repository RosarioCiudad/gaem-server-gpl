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
package ar.gov.rosario.gait.frm.buss.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampo;
import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampoValor;
import ar.gov.rosario.gait.apm.buss.bean.AplPerfilSeccionCampoValorOpcion;
import ar.gov.rosario.gait.frm.buss.dao.FrmDAOFactory;
import coop.tecso.demoda.buss.bean.BaseBO;

@Entity
@Table(name="for_formularioDetalle")
public class FormularioDetalle extends BaseBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)  
	@JoinColumn(name = "idFormulario") 
	private Formulario formulario;
	
	@ManyToOne(optional = false)  
	@JoinColumn(name="idTipoFormularioDefSeccionCampo")
	private AplPerfilSeccionCampo tipoFormularioDefSeccionCampo;

	@ManyToOne
	@JoinColumn(name="idTipoFormularioDefSeccionCampoValor")
	private AplPerfilSeccionCampoValor tipoFormularioDefSeccionCampoValor;

	@ManyToOne  
	@JoinColumn(name="idTipoFormularioDefSeccionCampoValorOpcion")
	private AplPerfilSeccionCampoValorOpcion tipoFormularioDefSeccionCampoValorOpcion;

	@Column
	private byte[] imagen;

	@Column
	private String valor;

	// Constructores
	public FormularioDetalle() {
		super();
	}

	// Metodos de Clase
	public static FormularioDetalle getById(Long id) {
		return (FormularioDetalle) FrmDAOFactory.getFormularioDetalleDAO().getById(id);
	}
	
	public static FormularioDetalle getByIdNull(Long id) {
		return (FormularioDetalle) FrmDAOFactory.getFormularioDetalleDAO().getByIdNull(id);
	}
	
	public static List<FormularioDetalle> getList() {
		return FrmDAOFactory.getFormularioDetalleDAO().getList();
	}
	
	public static List<FormularioDetalle> getListActivos() {			
		return FrmDAOFactory.getFormularioDetalleDAO().getListActiva();
	}
	
	// Getters y Setters
	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	public AplPerfilSeccionCampo getTipoFormularioDefSeccionCampo() {
		return tipoFormularioDefSeccionCampo;
	}

	public void setTipoFormularioDefSeccionCampo(
			AplPerfilSeccionCampo tipoFormularioDefSeccionCampo) {
		this.tipoFormularioDefSeccionCampo = tipoFormularioDefSeccionCampo;
	}

	public AplPerfilSeccionCampoValor getTipoFormularioDefSeccionCampoValor() {
		return tipoFormularioDefSeccionCampoValor;
	}

	public void setTipoFormularioDefSeccionCampoValor(
			AplPerfilSeccionCampoValor tipoFormularioDefSeccionCampoValor) {
		this.tipoFormularioDefSeccionCampoValor = tipoFormularioDefSeccionCampoValor;
	}

	public AplPerfilSeccionCampoValorOpcion getTipoFormularioDefSeccionCampoValorOpcion() {
		return tipoFormularioDefSeccionCampoValorOpcion;
	}

	public void setTipoFormularioDefSeccionCampoValorOpcion(
			AplPerfilSeccionCampoValorOpcion tipoFormularioDefSeccionCampoValorOpcion) {
		this.tipoFormularioDefSeccionCampoValorOpcion = tipoFormularioDefSeccionCampoValorOpcion;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
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
		// Validaciones de Negocio
		return true;
	}
	
	private boolean validate() throws Exception {
		if (hasError()) {
			return false;
		}
		// Validaciones de Negocio
		return true;
	}
}