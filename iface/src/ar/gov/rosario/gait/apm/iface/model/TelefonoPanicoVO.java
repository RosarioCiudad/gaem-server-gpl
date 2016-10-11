package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;

public class TelefonoPanicoVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "telefonoPanicoVO";

	private String descripcion;

	private String numero;

	private AreaVO area = new AreaVO();

	// Constructores
	public TelefonoPanicoVO() {
		super();
	}

	public TelefonoPanicoVO(int id, String desc) {
		super();
		setId(new Long(id));
		setDescripcion(desc);
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public AreaVO getArea() {
		return area;
	}

	public void setArea(AreaVO area) {
		this.area = area;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
}