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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import coop.tecso.demoda.buss.bean.VersionableBO;


/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "apm_aplicacionperfilparametro")
public class AplPerfilParametro extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false)
    @JoinColumn(name = "idAplicacionPerfil")
	private AplicacionPerfil aplicacionPerfil;

	@Column(name="codigo")
	private String codigo;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="valor")
	private String valor;

	// Constructores
	public AplPerfilParametro() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Getters y Setters
	public AplicacionPerfil getAplicacionPerfil() {
		return aplicacionPerfil;
	}

	public void setAplicacionPerfil(AplicacionPerfil aplicacionPerfil) {
		this.aplicacionPerfil = aplicacionPerfil;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(String.format("Entidad --> {%s} ", getClass().getSimpleName()));
		buffer.append(String.format("Aplicacion: {%s}, ", aplicacionPerfil.getDescripcion()));
		buffer.append(String.format("Codigo: {%s}, ", codigo));
		buffer.append(String.format("Descripcion: {%s}, ", descripcion));
		buffer.append(String.format("Valor: {%s}, ", valor));
		buffer.append("\n");
		return buffer.toString();
	}

}