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
@Table(name = "apm_aplicacionPerfilSeccion")
public class AplicacionPerfilSeccion extends VersionableBO {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)  
    @JoinColumn(name = "idAplicacionPerfil")
	private AplicacionPerfil aplicacionPerfil; //Formulario

	@ManyToOne(optional = false)  
    @JoinColumn(name = "idSeccion")
	private Seccion seccion;
	
	@Column(nullable = false)
	private Integer orden;

	@Column
	private Integer noDesplegar;
	
	@Column
	private Integer opcional;
	
	@Column
	private Integer permiteImpresion;
	
	@OneToMany()
	@JoinColumn(name="idAplicacionPerfilSeccion")
	@Where(clause="estado = 1")
	@OrderBy("orden")
	private List<AplPerfilSeccionCampo> listAplPerfilSeccionCampo;
										

	// Getters y setters
	public AplicacionPerfil getAplicacionPerfil() {
		return aplicacionPerfil;
	}

	public void setAplicacionPerfil(AplicacionPerfil aplicacionPerfil) {
		this.aplicacionPerfil = aplicacionPerfil;
	}

	public Seccion getSeccion() {
		return seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getNoDesplegar() {
		return noDesplegar;
	}

	public void setNoDesplegar(Integer noDesplegar) {
		this.noDesplegar = noDesplegar;
	}

	public Integer getOpcional() {
		return opcional;
	}

	public void setOpcional(Integer opcional) {
		this.opcional = opcional;
	}
	
	public Integer getPermiteImpresion() {
		return permiteImpresion;
	}

	public void setPermiteImpresion(Integer permiteImpresion) {
		this.permiteImpresion = permiteImpresion;
	}


	// Metodos de Clase
	public static AplicacionPerfilSeccion getById(Long id) {
		return (AplicacionPerfilSeccion) ApmDAOFactory.getAplicacionPerfilSeccionDAO().getById(id);
	}

	public static AplicacionPerfilSeccion getByIdNull(Long id) {
		return (AplicacionPerfilSeccion) ApmDAOFactory.getAplicacionPerfilSeccionDAO().getByIdNull(id);
	}

	public static List<AplicacionPerfilSeccion> getList() {
		return (ArrayList<AplicacionPerfilSeccion>) ApmDAOFactory.getAplicacionPerfilSeccionDAO().getList();
	}

	public static List<AplicacionPerfilSeccion> getListActivos() {
		return (ArrayList<AplicacionPerfilSeccion>) ApmDAOFactory.getAplicacionPerfilSeccionDAO().getListActiva();
	}

	// Validaciones
	public boolean validateCreate() throws Exception {
		// limpiamos la lista de errores
		clearError();
		
		// Validaciones de Negocio
		if (!this.validate()) {
			return false;
		}
		
		UniqueMap uniqueMap = new UniqueMap();
		uniqueMap.addInteger("orden");
		uniqueMap.addEntity("aplicacionPerfil");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.APLICACIONPERFIL_ORDEN);			
		}

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
		uniqueMap.addEntity("aplicacionPerfil");
		if(!GenericDAO.checkIsUnique(this, uniqueMap)) {
			addRecoverableError(BaseError.MSG_CAMPO_UNICO, ApmError.APLICACIONPERFIL_ORDEN);			
		}
		
		if (hasError()) {
			return false;
		}
		
		// Validaciones de Negocio

		return true;
	}

	public boolean validateDelete() {
		// limpiamos la lista de errores
		clearError();

		if (GenericDAO.hasReferenceActivo(this, AplPerfilSeccionCampo.class, "aplicacionPerfilSeccion")) {
			addRecoverableError(BaseError.MSG_ELIMINAR_REGISTROS_ASOCIADOS,
					ApmError.APLICACIONPERFILSECCION_LABEL, ApmError.APLPERFILSECCIONCAMPO_LABEL);
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
		
		if (null == getSeccion()) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.APLICACIONPERFILSECCION_LABEL);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}

	/**
	 * @return the listAplPerfilSeccionCampo
	 */
	public List<AplPerfilSeccionCampo> getListAplPerfilSeccionCampo() {
		return listAplPerfilSeccionCampo;
	}

	/**
	 * @param listAplPerfilSeccionCampo the listAplPerfilSeccionCampo to set
	 */
	public void setListAplPerfilSeccionCampo(
			List<AplPerfilSeccionCampo> listAplPerfilSeccionCampo) {
		this.listAplPerfilSeccionCampo = listAplPerfilSeccionCampo;
	}

	
}