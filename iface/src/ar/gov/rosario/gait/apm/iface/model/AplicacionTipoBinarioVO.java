package ar.gov.rosario.gait.apm.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

public class AplicacionTipoBinarioVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionTipoBinarioVO";
	
	private String descripcion;
	private List<AplicacionBinarioVersionVO> listAplicacionBinarioVersion = new ArrayList<AplicacionBinarioVersionVO>(); 

	// Constructores
	public AplicacionTipoBinarioVO() {
		super();
	}

	public AplicacionTipoBinarioVO(int id, String desc) {
		super();
		setId(new Long(id));
		setDescripcion(desc);
	}
	
	// Getters y setters
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<AplicacionBinarioVersionVO> getListAplicacionBinarioVersion() {
		return listAplicacionBinarioVersion;
	}

	public void setListAplicacionBinarioVersion(
			List<AplicacionBinarioVersionVO> listAplicacionBinarioVersion) {
		this.listAplicacionBinarioVersion = listAplicacionBinarioVersion;
	}


}