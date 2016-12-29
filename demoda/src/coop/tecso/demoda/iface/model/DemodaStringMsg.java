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

import coop.tecso.demoda.iface.error.DemodaError;

/**
 * Representa la instancia de un Mensage de error con su numero, clave y parametros si los tuviese.
 * Dificilmente usted tenga que instaciar esta clase.
 * <p>Para manejar los errores utilizar los metodos 
 * del Common (addRecovreableError y addNonRecoverableError, etc...)
*/
public class DemodaStringMsg {
	private long number;
	private String key;
	private Object[] params = null;
	private String rawkey;

	public DemodaStringMsg() {
		this.number = 0;
		this.key = "";
		this.params = new Object[0];
		this.rawkey = "";
	}
	
	public DemodaStringMsg(String errorConst) {
		this.number = 0;
		this.key = DemodaError.errorKey(errorConst);
		this.params = new Object[0];
		this.rawkey = errorConst;
	}

	public DemodaStringMsg(String errorConst, Object[] params) {
		this.number = 0;
		this.key = DemodaError.errorKey(errorConst);
		this.params = params;
		this.rawkey = errorConst;
	}

	public DemodaStringMsg(long number, String key) {
		this.number = number;
		this.key = key;
		this.params = new Object[0];
		this.rawkey = key;
	}

	public DemodaStringMsg(long number, String key, Object[] params) {
		this.number = number;
		this.key = key;
		this.params = params;
		this.rawkey = key;
	}
	
	public boolean isValue() {
		boolean isValue = false;
		
		if (this.key.startsWith("&")) {
			isValue = true;
		}
		
		return isValue;
	}
	
	public boolean isKey() {
		return !this.isValue();
	}

	public long number() { return number; }
	public String key() { return key; }
	public Object[] params() { return params; }
	public String rawkey() { return rawkey; }

}

