package ar.gov.rosario.gait.aid.buss.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import coop.tecso.demoda.buss.bean.VersionableBO;

@Entity
@Table(name="aid_lineatup")
public class LineaTUP extends VersionableBO {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private String codigo;

	@Column(nullable = false)
	private String descripcion;

	// Constructores
	public LineaTUP() {
		super();
	}

	// Getters y Setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}