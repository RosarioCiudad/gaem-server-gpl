
package coop.tecso.demoda.buss.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Mapa de propiedades a verificar la unicidad de un Objeto en la Base de Datos.
 * Esta clase posee un Mapa interno donde se van cargando todas las propiedades
 * por las cuales verificar para realizar una prueba de unicidad invocando a 
 * checkIsUnique() de GenericAbastractDAO
*/
public class UniqueMap {
	private Map<String, Object> filters = new LinkedHashMap<>();
	
	static public String REFLECT_COOKIE = "__@reflect__";

    public UniqueMap() { 
	}

	/**
	 * Limpia el mapa interno de propiedades cargadas.
	*/
	public UniqueMap clean() {
		filters = new LinkedHashMap<>();
		return this;
	}

	/**
	 * Agrega una propiedad String para verificar unicidad con value.
	 * El chequeo de igualdad sera Case InSensitive (pej: fedel = FEDEL)
	*/
	public UniqueMap addString(String propName, String value) {
		filters.put("S" + propName, value);
		return this;
	}

	/**
	 * Agrega una propiedad String para verificar unicidad invocando por 
	 * reflection al metodo getter de propName del Objeto a chequear.
	 * El chequeo de igualdad sera Case InSensitive (pej: fedel = FEDEL)
	*/
	public UniqueMap addString(String propName) {
		filters.put("S" + propName, REFLECT_COOKIE);
		return this;
	}

	/**
	 * Agrega una propiedad String para verificar unicidad.
	 * El chequeo de igualdad sera Case Sensitive (pej: fedel != FEDEL)
	*/
	public UniqueMap addStringCase(String propName, String value) {
		filters.put("s" + propName, value);
		return this;
	}

	/**
	 * Agrega una propiedad String para verificar unicidad invocando por 
	 * reflection al metodo getter de propName del Objeto a chequear.
	 * El chequeo de igualdad sera Case Sensitive (pej: fedel != FEDEL)
	*/
	public UniqueMap addStringCase(String propName) {
		filters.put("s" + propName, REFLECT_COOKIE);
		return this;
	}

	/**
	 * Agrega una propiedad Long para verificar unicidad con value.
	*/
	public UniqueMap addLong(String propName, Long value) {
		filters.put("L" + propName, value);
		return this;
	}
	
	/**
	 * Agrega una propiedad date para verificar unicidad con value.
	*/
	public UniqueMap addDate(String propName, Date value) {
		filters.put("D" + propName, value);
		return this;
	}

	/**
	 * Agrega una propiedad Long para verificar unicidad invocando por 
	 * reflection al metodo getter de propName del Objeto a chequear.
	*/
	public UniqueMap addLong(String propName) {
		filters.put("L" + propName, REFLECT_COOKIE);
		return this;
	}

	/**
	 * Agrega una propiedad Integer para verificar unicidad invocando por 
	 * reflection al metodo getter de propName del Objeto a chequear.
	*/
	public UniqueMap addInteger(String propName) {
		filters.put("I" + propName, REFLECT_COOKIE);
		return this;
	}
	
	
	/**
	 * Agrega una propiedad cuyo tipo representa una entidad en Hibernate 
	 * para verificar unicidad con value.
	*/
	public UniqueMap addEntity(String propName, Object value) {
		filters.put("E" + propName, value);
		return this;
	}

	/**
	 * Agrega una propiedad cuyo tipo representa una entidad de Hibernate 
	 * para verificar unicidad invocando por reflection al metodo getter de 
	 * propName del Objeto a chequear.
	*/
	public UniqueMap addEntity(String propName) {
		filters.put("E" + propName, REFLECT_COOKIE);
		return this;
	}

	/**
	 * Retorna el mapa interno del filtros
	*/
	public Map<String, Object> getFilters() {
		return this.filters;
	}
}