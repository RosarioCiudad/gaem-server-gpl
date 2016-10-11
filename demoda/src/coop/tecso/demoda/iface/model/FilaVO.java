
package coop.tecso.demoda.iface.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class FilaVO {

	private Logger log = Logger.getLogger(FilaVO.class);

	public static final String COLOR_BLANCO = "#ffffff";
	public static final String COLOR_GRIS = "#e0e0e0";
	public static final String COLOR_ROJO = "#100000";
	
	private String nombre = "";

	private String color = FilaVO.COLOR_BLANCO;
	
	private List<CeldaVO> listCelda = new ArrayList<CeldaVO>();

	public FilaVO(){
	}

	public FilaVO(String nombre){
		this.nombre = nombre;
	}

	// Getters Y Setters

	public List<CeldaVO> getListCelda() {
		return listCelda;
	}
	public void setListCelda(List<CeldaVO> listCelda) {
		this.listCelda = listCelda;
	}
	public void add(CeldaVO celda){
		this.listCelda.add(celda);
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void logearContenido(){
		log.debug("nombre: " + this.getNombre());
		for (CeldaVO celda : this.getListCelda()) {
			celda.logearContenido();
		}
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}
