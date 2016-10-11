package ar.gov.rosario.gait.seg.buss.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import coop.tecso.demoda.buss.bean.VersionableBO;

/**
 * 
 * @author tecso.coop
 *
 */
@Entity
@Table(name = "apm_sucursal")
public class Sucursal extends VersionableBO {

	private static final long serialVersionUID = 1L;

	@Column(name="descripcion")
	private String descripcion;
		
	@Column(name="domicilio")
	private String domicilio;

	@Column(name="codigoPostal")
	private String codigoPostal;


	// Constructores
	public Sucursal() {
		super();
	}

	// Getters & Setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(); 
		builder.append(String.format("Entidad --> {%s} ", getClass().getSimpleName()));
		builder.append(String.format("ID: {%s}, ", getId()));
		builder.append(String.format("Descripcion: {%s}, ", getDescripcion()));
		builder.append(String.format("Domicilio: {%s}, ", getDomicilio()));
		builder.append(String.format("CodigoPostal: {%s}, ", getCodigoPostal()));
		builder.append("\n");
		return builder.toString();
	}

}