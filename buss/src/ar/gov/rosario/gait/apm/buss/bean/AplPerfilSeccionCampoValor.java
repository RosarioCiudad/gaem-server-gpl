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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.buss.dao.UniqueMap;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "apm_aplPerfilSeccionCampoValor")
public class AplPerfilSeccionCampoValor extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
    @JoinColumn(name = "idAplPerfilSeccionCampo")
	private AplPerfilSeccionCampo aplPerfilSeccionCampo;

	@ManyToOne(optional = false)  
    @JoinColumn(name = "idCampoValor")
	private CampoValor campoValor;

	@Column(nullable = false)
	private Integer orden;
	
	@OneToMany()
	@JoinColumn(name="idAplPerfilSeccionCampoValor")
	@Where(clause="estado = 1")
	@OrderBy("orden")
	private List<AplPerfilSeccionCampoValorOpcion> listAplPerfilSeccionCampoValorOpcion;


	// Metodos de Clase
	public static AplPerfilSeccionCampoValor getById(Long id) {
		return (AplPerfilSeccionCampoValor) ApmDAOFactory.getAplPerfilSeccionCampoValorDAO().getById(id);
	}
	
	public static AplPerfilSeccionCampoValor getByIdNull(Long id) {
		return (AplPerfilSeccionCampoValor) ApmDAOFactory.getAplPerfilSeccionCampoValorDAO().getByIdNull(id);
	}
	
	public static List<AplPerfilSeccionCampoValor> getList() {
		return (ArrayList<AplPerfilSeccionCampoValor>) ApmDAOFactory.getAplPerfilSeccionCampoValorDAO().getList();
	}
	
	public static List<AplPerfilSeccionCampoValor> getListActivos() {
		return ApmDAOFactory.getAplPerfilSeccionCampoValorDAO().getListActiva();
	}


	// Getters Y Setters
	public AplPerfilSeccionCampo getAplPerfilSeccionCampo() {
		return aplPerfilSeccionCampo;
	}

	public void setAplPerfilSeccionCampo(AplPerfilSeccionCampo aplPerfilSeccionCampo) {
		this.aplPerfilSeccionCampo = aplPerfilSeccionCampo;
	}

	public CampoValor getCampoValor() {
		return campoValor;
	}

	public void setCampoValor(CampoValor campoValor) {
		this.campoValor = campoValor;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public List<AplPerfilSeccionCampoValorOpcion> getListAplPerfilSeccionCampoValorOpcion() {
		return listAplPerfilSeccionCampoValorOpcion;
	}

	public void setListAplPerfilSeccionCampoValorOpcion(List<AplPerfilSeccionCampoValorOpcion> listAplPerfilSeccionCampoValorOpcion) {
		this.listAplPerfilSeccionCampoValorOpcion = listAplPerfilSeccionCampoValorOpcion;
	}

	
	// Validaciones
	public boolean validateCreate() throws Exception {
		// limpiamos la lista de errores
		clearError();
		
		if (!this.validate()) {
			return false;
		}
		
		UniqueMap uniqueMap = new UniqueMap();
		uniqueMap.addInteger("orden");
		uniqueMap.addEntity("aplPerfilSeccionCampo");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.APLICACIONPERFIL_ORDEN);			
		}
		
		// Validaciones de Negocio
		if (hasError()) {
			return false;
		}

		return true;
	}


	public boolean validateUpdate() throws Exception {
		// limpiamos la lista de errores
		clearError();
		
		if (!this.validate()) {
			return false;
		}

		UniqueMap uniqueMap = new UniqueMap();
		uniqueMap.addInteger("orden");
		uniqueMap.addEntity("aplPerfilSeccionCampo");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.APLICACIONPERFIL_ORDEN);			
		}
		
		// Validaciones de Negocio
		if (hasError()) {
			return false;
		}

		return true;
	}

	public boolean validateDelete() {
		// limpiamos la lista de errores
		clearError();
		
		if (GenericDAO.hasReferenceActivo(this, AplPerfilSeccionCampoValorOpcion.class, "aplPerfilSeccionCampoValor")) {
			addRecoverableError(BaseError.MSG_ELIMINAR_REGISTROS_ASOCIADOS,
					ApmError.APLPERFILSECCIONCAMPOVALOR_LABEL, ApmError.APLPERFILSECCIONCAMPOVALOROPCION_LABEL);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {

		// Validaciones
		if (null == getOrden()) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.APLICACIONPERFIL_ORDEN);
		}

		if(null == getAplPerfilSeccionCampo()){
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.APLPERFILSECCIONCAMPO_LABEL);
		}
		

		if (hasError()) {
			return false;
		}

		return true;
	}
}