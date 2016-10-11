package ar.gov.rosario.gait.def.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;


/**
 * Value Object del Parametro
 * @author tecso
 *
 */
public class ParametroVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "parametroVO";
	
	private String codParam;
	
	private String desParam;

	private String valor;
	
		
	// Constructores
	public ParametroVO() {
		super();
	}
	
	// Constructor para utilizar este VO en un combo como valor SELECCIONAR, TODOS, etc.
	public ParametroVO(int id, String desc) {
		super();
		setId(new Long(id));
		setDesParam(desc);
	}
	
	public String getCodParam() {
		return codParam;
	}

	public void setCodParam(String codParametro) {
		this.codParam = codParametro;
	}

	public String getDesParam() {
		return desParam;
	}

	public void setDesParam(String desParametro) {
		this.desParam = desParametro;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
