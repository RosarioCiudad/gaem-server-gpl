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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import ar.gov.rosario.gait.apm.buss.dao.ApmDAOFactory;
import ar.gov.rosario.gait.apm.iface.util.ApmError;
import ar.gov.rosario.gait.base.buss.dao.GenericDAO;
import ar.gov.rosario.gait.base.iface.util.BaseError;
import ar.gov.rosario.gait.frm.buss.bean.TipoFormulario;
import coop.tecso.demoda.buss.bean.VersionableBO;
import coop.tecso.demoda.iface.helper.StringUtil;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "apm_aplicacionPerfil")
public class AplicacionPerfil extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idAplicacion")
	private Aplicacion aplicacion;

	@ManyToOne(optional = false)
	@JoinColumn(name = "idTipoFormulario")
	private TipoFormulario tipoFormulario;
	
	@Column
	private String descripcion;

	@OneToMany()
	@JoinColumn(name="idAplicacionPerfil")
	@Where(clause="estado = 1")
	@OrderBy("orden")
	private List<AplicacionPerfilSeccion> listAplicacionPerfilSeccion;

	@OneToMany()
	@JoinColumn(name="idAplicacionPerfil")
	@Where(clause="estado = 1")
	@OrderBy("codigo")
	private List<AplicacionPerfilParametro> listAplicacionPerfilParametro;

	// Metodos de Clase
	public static AplicacionPerfil getById(Long id) {
		return (AplicacionPerfil) ApmDAOFactory.getAplicacionPerfilDAO().getById(id);
	}

	public static AplicacionPerfil getByIdNull(Long id) {
		return (AplicacionPerfil) ApmDAOFactory.getAplicacionPerfilDAO().getByIdNull(id);
	}

	public static List<AplicacionPerfil> getList() {
		return ApmDAOFactory.getAplicacionPerfilDAO().getList();
	}

	public static List<AplicacionPerfil> getListActivos() {
		return ApmDAOFactory.getAplicacionPerfilDAO().getListActiva();
	}

	// Getters y Setters
	public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<AplicacionPerfilSeccion> getListApPerfilSeccion() {
		return listAplicacionPerfilSeccion;
	}

	public void setListApPerfilSeccionList(List<AplicacionPerfilSeccion> listAplicacionPerfilSeccion) {
		this.listAplicacionPerfilSeccion = listAplicacionPerfilSeccion;
	}
	
	public List<AplicacionPerfilSeccion> getListAplicacionPerfilSeccion() {
		return listAplicacionPerfilSeccion;
	}

	public void setListAplicacionPerfilSeccion(List<AplicacionPerfilSeccion> listAplicacionPerfilSeccion) {
		this.listAplicacionPerfilSeccion = listAplicacionPerfilSeccion;
	}

	public List<AplicacionPerfilParametro> getListAplicacionPerfilParametro() {
		return listAplicacionPerfilParametro;
	}

	public void setListAplicacionPerfilParametro(List<AplicacionPerfilParametro> listAplicacionPerfilParametro) {
		this.listAplicacionPerfilParametro = listAplicacionPerfilParametro;
	}

	public TipoFormulario getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(TipoFormulario tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
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

		if (GenericDAO.hasReferenceActivo(this, AplicacionPerfilSeccion.class, "aplicacionPerfil")) {
			addRecoverableError(BaseError.MSG_ELIMINAR_REGISTROS_ASOCIADOS,
					ApmError.APLICACIONPERFIL_LABEL, ApmError.APLICACIONPERFILSECCION_LABEL);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}

	private boolean validate() throws Exception {
		// Validaciones
		if (StringUtil.isNullOrEmpty(getDescripcion())) {
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO, ApmError.APLICACIONPERFIL_DESCRIPCION);
		}
		
		if(getTipoFormulario() == null){
			addRecoverableError(BaseError.MSG_CAMPO_REQUERIDO,
					ApmError.APLICACIONPERFIL_TIPOFORMULARIO);
		}

		if (hasError()) {
			return false;
		}

		return true;
	}
}