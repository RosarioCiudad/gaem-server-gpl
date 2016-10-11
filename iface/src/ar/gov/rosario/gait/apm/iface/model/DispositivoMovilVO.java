package ar.gov.rosario.gait.apm.iface.model;

import ar.gov.rosario.gait.base.iface.model.GaitBussImageModel;
import ar.gov.rosario.gait.def.iface.model.AreaVO;
import coop.tecso.demoda.iface.model.SiNo;

public class DispositivoMovilVO extends GaitBussImageModel {

	private static final long serialVersionUID = 1L;

	public static final String NAME = "dispositivoMovilVO";

	private String descripcion;

	private String numeroSerie;

	private String numeroLinea;

	private String numeroIMEI;

	private String emailAddress;

	private AreaVO area = new AreaVO();

	private SiNo forzarActualizacion = SiNo.OpcionTodo;

	// Constructores
	public DispositivoMovilVO() {
		super();
	}

	// Constructor para utilizar este VO en un combo como valor SELECCIONAR, TODOS, etc.
	public DispositivoMovilVO(int id, String desc) {
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

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public String getNumeroLinea() {
		return numeroLinea;
	}

	public void setNumeroLinea(String numeroLinea) {
		this.numeroLinea = numeroLinea;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public AreaVO getArea() {
		return area;
	}

	public void setArea(AreaVO area) {
		this.area = area;
	}

	public SiNo getForzarActualizacion() {
		return forzarActualizacion;
	}

	public void setForzarActualizacion(SiNo forzarActualizacion) {
		this.forzarActualizacion = forzarActualizacion;
	}

	public String getNumeroIMEI() {
		return numeroIMEI;
	}

	public void setNumeroIMEI(String numeroIMEI) {
		this.numeroIMEI = numeroIMEI;
	}
}