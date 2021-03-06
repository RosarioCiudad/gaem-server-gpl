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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.helper.StringUtil;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "apm_campoValorOpcion")
public class CampoValorOpcion extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
	@JoinColumn(name = "idCampoValor")
	private CampoValor campoValor;

	@Column(nullable = false)
	private String etiqueta;

	@Column(nullable = false)
	private int obligatorio;

	@Column(nullable = false)
	private int tratamiento;

	@Column
	private String valorDefault;
	
	@Column
	private String codigo;

	@Column
	private String tablaBusqueda;

	@Column
	private int soloLectura;
	
	@Column
	private String mascaraVisual;

	public CampoValor getCampoValor() {
		return campoValor;
	}


	public String getEtiqueta() {
		return etiqueta;
	}

	public int getSoloLectura() {
		return soloLectura;
	}

	public String getTablaBusqueda() {
		return tablaBusqueda;
	}

	public int getTratamiento() {
		return tratamiento;
	}

	public String getValorDefault() {
		return valorDefault;
	}

	public int isObligatorio() {
		return obligatorio;
	}

	public void setCampoValor(CampoValor campoValor) {
		this.campoValor = campoValor;
	}


	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public void setObligatorio(int obligatorio) {
		this.obligatorio = obligatorio;
	}

	public void setSoloLectura(int soloLectura) {
		this.soloLectura = soloLectura;
	}

	public void setTablaBusqueda(String tablaBusqueda) {
		this.tablaBusqueda = tablaBusqueda;
	}

	public void setTratamiento(int tratamiento) {
		this.tratamiento = tratamiento;
	}

	public void setValorDefault(String valorDefault) {
		this.valorDefault = valorDefault;
	}

	public String getMascaraVisual() {
		return mascaraVisual;
	}


	public void setMascaraVisual(String mascaraVisual) {
		this.mascaraVisual = mascaraVisual;
	}

	public int getObligatorio() {
		return obligatorio;
	}

	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	// Metodos de Clase
	public static CampoValorOpcion getById(Long id) {
		return (CampoValorOpcion) ApmDAOFactory.getCampoValorOpcionDAO().getById(id);
	}

	public static CampoValorOpcion getByIdNull(Long id) {
		return (CampoValorOpcion) ApmDAOFactory.getCampoValorOpcionDAO().getByIdNull(id);
	}

	public static List<CampoValorOpcion> getList() {
		return ApmDAOFactory.getCampoValorOpcionDAO().getList();
	}

	public static List<CampoValorOpcion> getListActivos() {			
		return  ApmDAOFactory.getCampoValorOpcionDAO().getListActiva();
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
		if (StringUtil.isNullOrEmpty(getEtiqueta())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					ApmError.CAMPO_ETIQUETA);
		}



		if (hasError()) {
			return false;
		}



		return true;
	}


}