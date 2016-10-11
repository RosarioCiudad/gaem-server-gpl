package ar.gov.rosario.gait.apm.iface.model;

import java.util.Date;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import coop.tecso.demoda.iface.helper.DateUtil;

public class AplicacionBinarioVersionVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "aplicacionBinarioVersionVO";
	
	private AplicacionVO aplicacion = new AplicacionVO();
	private AplicacionTipoBinarioVO aplicacionTipoBinario = new AplicacionTipoBinarioVO();
	private String descripcion;
	private String ubicacion;
	private Date fecha = new Date();
	
	private String fileName = "";
	private byte[] fileData;
	
	// Constructores
	public AplicacionBinarioVersionVO() {
		super();
	}

	// Getters y Setters
	public AplicacionVO getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(AplicacionVO aplicacion) {
		this.aplicacion = aplicacion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public AplicacionTipoBinarioVO getAplicacionTipoBinario() {
		return aplicacionTipoBinario;
	}

	public void setAplicacionTipoBinario(AplicacionTipoBinarioVO aplicacionTipoBinario) {
		this.aplicacionTipoBinario = aplicacionTipoBinario;
	}

	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public String getFechaView() {
		return DateUtil.formatDate(fecha, DateUtil.ddSMMSYYYY_MASK);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}
}