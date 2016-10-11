package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class AplicacionParametroVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionParametroVO";

	private String codigo;
	private String descripcion;
	private AplicacionVO aplicacion = new AplicacionVO();
	private String valor;
	
	
	// Constructores
	public AplicacionParametroVO() {
		super();
	}
	
	// Constructor para utilizar este VO en un combo como valor SELECCIONAR, TODOS, etc.
	public AplicacionParametroVO(int id, String desc) {
		super();
		setId(new Long(id));
		setDescripcion(desc);
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public AplicacionVO getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionVO aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}


}