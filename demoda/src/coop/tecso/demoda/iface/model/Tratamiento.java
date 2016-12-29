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

import coop.tecso.demoda.iface.helper.StringUtil;

public enum Tratamiento implements IDemodaEmun{
	TA(1, "Alfanúmerico"),
	TAM(2, "Alfanúmerico Multilinea"),
	TND(3, "Numérico Decimal"), 
	TNE(4, "Numérico Entero"), 
	TF(5, "Fecha"),
	TH(6, "Hora"),
	CHK(7, "CheckBox"),
	SBX(81,"SearchBox"),
	DOMI(70, "Domicilio Infracción"),
	DOMC(71, "Domicilio Conductor"),
	SO(72, ">>Sección Opcional>>"),
	SOC(73, ">>Sección Opcional Combo>>"),
	LD(74, ">>Lista Dinámica>>"),
	NA(76, "Opción Simple"),
	OP(75, "- Combo Opciones"),
	OP2(77,"- Combo Opciones vía DB"),
	OP3(78,"- Combo Opciones vía DB c/opción 'Otra'"),	
	//	LO(80, ">>Check Opciones>>"),
	PIC(79, "Adjunto Imagen"),
	DOM(80, "Dominio"),
	DOC(82, "Documento"),
	OpcionTodo(-1, StringUtil.SELECT_OPCION_TODOS);

	private Integer id;
	private String value;

	private Tratamiento(final Integer id,  final String value) {
		this.id = id;
		this.value = value;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public Integer getBussId() {
		return id > 0? id : null;
	}

	public static List<Tratamiento> getList(Tratamiento addEnu) {
		List<Tratamiento> listTratamiento = new ArrayList<>();
		//Obtengo la lista de SiNo
		Tratamiento[] tratamientos = Tratamiento.values();
		// Agrego las enumeraciones cuyo id sea no nulo o mayor que cero
		for (int i = 0; i < tratamientos.length; i++) {
			if (tratamientos[i].getId() != null &&
					tratamientos[i].getId() >= 0)
				listTratamiento.add(tratamientos[i]);
		}
		listTratamiento.add(0, addEnu);
		return listTratamiento;       
	}


	public static List<Tratamiento> getList() {
		List<Tratamiento> listTratamiento = new ArrayList<>();
		//Obtengo la lista de SiNo
		Tratamiento[] tratamientos = Tratamiento.values();
		// Agrego las enumeraciones cuyo id sea no nulo o mayor que cero
		for (int i = 0; i < tratamientos.length; i++) {
			if (tratamientos[i].getId() != null &&
					tratamientos[i].getId() >= 0)
				listTratamiento.add(tratamientos[i]);
		}
		return listTratamiento;       
	}

	public static Tratamiento getById(Integer id){
		Tratamiento[] values = Tratamiento.values();
		//
		for (Tratamiento tratamiento : values) {
			if(tratamiento.id.intValue() == id.intValue())
				return tratamiento;
		}
		return OpcionTodo;
	}
}