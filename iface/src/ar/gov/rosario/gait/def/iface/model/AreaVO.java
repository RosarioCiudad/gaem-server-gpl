package ar.gov.rosario.gait.def.iface.model;

import java.util.ArrayList;
import java.util.List;

import ar.gov.rosario.gait.apm.iface.model.DispositivoMovilVO;
import ar.gov.rosario.gait.apm.iface.model.ImpresoraVO;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoVO;
import ar.gov.rosario.gait.apm.iface.model.TelefonoPanicoVO;
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

/**
 * Value Object del Area
 * @author tecso
 *
 */
public class AreaVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "areaVO";
	
	private DireccionVO direccion = new DireccionVO();
	
	private List<PerfilAccesoVO> listPerfilAcceso= new ArrayList<>();

	private List <DispositivoMovilVO> listDispositivoMovil = new ArrayList<>();
	
	private List <ImpresoraVO> listImpresora = new ArrayList<>();
	
	private List<AreaAplicacionPerfilVO> listAreaAplicacionPerfil = new ArrayList<>();
	
	private List<TelefonoPanicoVO> listTelefonoPanico = new ArrayList<>();
	
	private String descripcion;
	
	// Buss Flags
	
	
	// View Constants
	
	
	// Constructores
	public AreaVO() {
		super();
	}
	
	// Constructor para utilizar este VO en un combo como valor SELECCIONAR, TODOS, etc.
	public AreaVO(int id, String descripcion) {
		super();
		setId(Integer.valueOf(id).longValue());
		setDescripcion(descripcion);
	}
	
	// Getters y Setters
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public List <DispositivoMovilVO> getListDispositivoMovil() {
		return listDispositivoMovil;
	}

	public void setListDispositivoMovil(List <DispositivoMovilVO> listDispositivoMovil) {
		this.listDispositivoMovil = listDispositivoMovil;
	}

	public List <ImpresoraVO> getListImpresora() {
		return listImpresora;
	}

	public void setListImpresora(List <ImpresoraVO> listImpresora) {
		this.listImpresora = listImpresora;
	}

	public List<PerfilAccesoVO> getListPerfilAcceso() {
		return listPerfilAcceso;
	}

	public void setListPerfilAcceso(List<PerfilAccesoVO> listPerfilAcceso) {
		this.listPerfilAcceso = listPerfilAcceso;
	}

	public List<AreaAplicacionPerfilVO> getListAreaAplicacionPerfil() {
		return listAreaAplicacionPerfil;
	}

	public void setListAreaAplicacionPerfil(List<AreaAplicacionPerfilVO> listAreaAplicacionPerfil) {
		this.listAreaAplicacionPerfil = listAreaAplicacionPerfil;
	}

	public List<TelefonoPanicoVO> getListTelefonoPanico() {
		return listTelefonoPanico;
	}

	public void setListTelefonoPanico(List<TelefonoPanicoVO> listTelefonoPanico) {
		this.listTelefonoPanico = listTelefonoPanico;
	}
}