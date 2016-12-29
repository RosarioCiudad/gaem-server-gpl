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
package coop.tecso.demoda.db;

import java.util.ArrayList;
import java.util.List;

public class QueryMakerBase implements QueryMaker {
	
	List<String> parts = new ArrayList<String>();
	List<Object> params = new ArrayList<Object>();
	
	@Override
	public QueryMaker add(String part, Object... values) {
		parts.add(part);
		for(Object v : values) {
			params.add(v);
		}
		
		return this;
	}

	@Override
	public QueryMaker addIf(boolean cond, String part, Object... values) {
		if (cond) {
			add(part, values);
		}
		
		return this;
	}

	@Override
	public QueryMaker addIfNotNull(String part, Object... values) {
		for(Object v : values) {
			if (v != null) {
				add(part, values);
				break;
			}
		}
		
		return this;
	}

	@Override
	public List<String> parts() {
		return new ArrayList<String>(parts);
	}

	@Override
	public List<Object> parameters() {
		return new ArrayList<Object>(params);
	}

	@Override
	public String queryString() {
		StringBuilder sb = new StringBuilder();
		for(String part : parts) {
			sb.append(part).append(" ");
		}
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Object value : params) {
			sb.append(", ").append(value);
		}
		
		return queryString() + (sb.length() > 0 ? "; --" + sb.substring(1) : "");
	}
	
}