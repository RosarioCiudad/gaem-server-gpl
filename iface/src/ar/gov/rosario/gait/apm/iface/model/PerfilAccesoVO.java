package ar.gov.rosario.gait.apm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import ar.gov.rosario.gait.def.iface.model.AreaAplicacionPerfilVO;
import ar.gov.rosario.gait.def.iface.model.AreaVO;

public class PerfilAccesoVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "perfilAccesoVO";

	private AreaVO area = new AreaVO();
	private List<AreaAplicacionPerfilVO> listAreaAplicacionPerfil = new ArrayList<>();
	private List<PerfilAccesoAplicacionVO> listPerfilAccesoAplicacion = new ArrayList<>();
	
	private String descripcion;
	
	// Constructores
	public PerfilAccesoVO() {
		super();
	}
	
	// Constructor 
	public PerfilAccesoVO(int id, String desc) {
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

	public List<AreaAplicacionPerfilVO> getListAreaAplicacionPerfil() {
		return listAreaAplicacionPerfil;
	}

	public void setListAreaAplicacionPerfil(List<AreaAplicacionPerfilVO> listAreaAplicacionPerfil) {
		this.listAreaAplicacionPerfil = listAreaAplicacionPerfil;
	}

	public List<PerfilAccesoAplicacionVO> getListPerfilAccesoAplicacion() {
		return listPerfilAccesoAplicacion;
	}

	public void setListPerfilAccesoAplicacion(List<PerfilAccesoAplicacionVO> listPerfilAccesoAplicacion) {
		this.listPerfilAccesoAplicacion = listPerfilAccesoAplicacion;
	}
}