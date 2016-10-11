package ar.gov.rosario.gait.def.iface.model;

import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
import ar.gov.rosario.gait.apm.iface.model.PerfilAccesoVO;
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

/**
 * Value Object de Direccion Apl Perfil
 * @author tecso
 *
 */
public class AreaAplicacionPerfilVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "areaAplicacionPerfilVO";
	
	private AreaVO area = new AreaVO();	
	private AplicacionPerfilVO aplicacionPerfil = new AplicacionPerfilVO(); 	
	private PerfilAccesoVO perfilAcceso = new PerfilAccesoVO(); 
	
	// Constructores
	public AreaAplicacionPerfilVO() {
		super();
	}
	
	// Constructor para utilizar este VO en un combo como valor SELECCIONAR, TODOS, etc.
	public AreaAplicacionPerfilVO(int id, String desc) {
		super();
		setId(new Long(id));
	}
	
	// Getters y Setters
	public AplicacionPerfilVO getAplicacionPerfil() {
		return aplicacionPerfil;
	}

	public void setAplicacionPerfil(AplicacionPerfilVO aplicacionPerfil) {
		this.aplicacionPerfil = aplicacionPerfil;
	}

	public PerfilAccesoVO getPerfilAcceso() {
		return perfilAcceso;
	}

	public void setPerfilAcceso(PerfilAccesoVO perfilAcceso) {
		this.perfilAcceso = perfilAcceso;
	}

	public AreaVO getArea() {
		return area;
	}

	public void setArea(AreaVO area) {
		this.area = area;
	}
}