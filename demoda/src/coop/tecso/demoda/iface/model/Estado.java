/*******************************************************************************
 * Copyright (c) 2016 Municipalidad de Rosario, Coop. de Trabajo Tecso Ltda.
 *
 * This file is part of GAEM.
 *
 * GAEM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * GAEM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAEM.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package coop.tecso.demoda.iface.model;

import java.util.ArrayList;
import java.util.List;

public enum Estado implements IDemodaEmun {

	CREADO(-1,"Creado"),
    INACTIVO(0, "Inactivo"),
    ACTIVO(1, "Activo"),
    //
    PROCESADO(2, "Procesado"),
	PROCESADO_ERROR(3, "Procesado con Error"),
	FORMULARIO_DUPLICADO(4, "Formulario Duplicado"),
	NUMERO_DUPLICADO(5, "Número Duplicado");

	private Integer id;
    private String value;

    private Estado(final Integer id, final String value) {
        this.id = id;
        this.value = value;
    }
    
    public Integer getId() {
		return id;
	}

    public String getValue() {
		return value;
	}

    public String toString() {
    	return "Estado: [" + id + ", " + value + "]";
	}
	
    public static Estado getById(Integer _id) {
	   Estado[] estados = Estado.values();
	   for (int i = 0; i < estados.length; i++) {
		   Estado est = estados[i];
  		   if (_id == null){
  			   if (est.id == null){
  				   return est;
  			   }  				   
  		   } else {
			   if (est.id.intValue() == _id.intValue()){
				   return est;
			   }
  		   }
		}
		   return null;
	}
    
    /**
     * Devuelve un Id valido para persistir, en un create o update.
     *
     * @return el numero del SiNo.
     */
    public Integer getBussId(){
 	    if (id < 0)
 	    	return null;
 	    else
 	    	return id;
    }
    
    public static List<Estado> getList(){
    	
    	List<Estado> listEstado = new ArrayList<Estado>();
 	   
 	   //Obtengo la lista de Estado
 	   Estado[] estados = Estado.values();
 		
 		// Agrego las enumeraciones cuyo id sea no nulo o mayor o igual que cero
        for (int i = 0; i < estados.length; i++) {
 	       	if (estados[i].getId() != null &&
 	       		estados[i].getId() >= 0)
 	       	listEstado.add(estados[i]);
 	   }
        return listEstado;
    }
    
    public static boolean getEsValido(Integer id){
 	   return (Estado.ACTIVO.id.equals(id)  || Estado.INACTIVO.id.equals(id) || Estado.CREADO.id.equals(id));
    }

    public boolean getEsActivo(){
 	   return this.id.equals(ACTIVO.getId());
    }
    
    public boolean getEsCreado(){
  	   return this.id.equals(CREADO.getId());
     }
}
