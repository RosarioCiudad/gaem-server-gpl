package ar.gov.rosario.gait.def.iface.model;

import ar.gov.rosario.gait.apm.iface.model.AplicacionPerfilVO;
import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;

/**
 * Value Object de Direccion Apl Perfil
 * @author tecso
 *
 */
public class DireccionAplicacionPerfilVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "direccionAplicacionPerfilVO";
	
	private DireccionVO direccion = new DireccionVO();
	
	
	private AplicacionPerfilVO aplicacionPerfil = new AplicacionPerfilVO(); 
	
	// Buss Flags
	
	
	// View Constants
	
	
	// Constructores
	public DireccionAplicacionPerfilVO() {
		super();
	}
	
	// Constructor para utilizar este VO en un combo como valor SELECCIONAR, TODOS, etc.
	public DireccionAplicacionPerfilVO(int id, String desc) {
		super();
		setId(new Long(id));
		
	}

	
	
	// Getters y Setters

	public DireccionVO getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionVO direccion) {
		this.direccion = direccion;
	}

	public AplicacionPerfilVO getAplicacionPerfil() {
		return aplicacionPerfil;
	}

	public void setAplicacionPerfil(AplicacionPerfilVO aplicacionPerfil) {
		this.aplicacionPerfil = aplicacionPerfil;
	}
	

	
	// Buss flags getters y setters
	
	
	// View flags getters
	
	
	// View getters
}
