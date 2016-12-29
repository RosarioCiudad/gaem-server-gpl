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
package coop.tecso.demoda.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {
	private static List<Route> routes = Collections.synchronizedList(new ArrayList<Route>()); 
	
	public final String methods[];
	public final String pattern;
	public final Object data[];

	private Route(String httpmtd, String pattern, Object... data) {
		super();
		this.methods = httpmtd.split("\\|");
		this.pattern = pattern;
		
		this.data = data;
	}
	
	/**
	 * Add a route to posibles matches.
	 * @param httpMtd : GET, PUT, POST, can use a '|' character as separator for multiples matches.
	 * @param pattern : URL pattern. 
	 * @param klass : Class to instanciate.
	 * @param classMtd : method to invoke.
	 * @param params : extra parameters to pass when method is invoked.
	 */
	static public void bind(String httpMtd, String pattern, Object... data) {
		Route.routes.add(create(httpMtd, pattern, data));
	}

	static public Route create(String httpMtd, String pattern, Object... data) {
		return new Route(httpMtd, pattern, data);
	}

	static public void bind(Route[] routes) {
		for(Route route : routes) {
			Route.routes.add(route);
		}
	}
	
	/**
	 * Get first route that matches with method and uri
	 * @param method HTTP method to match
	 * @param uri URI to match
	 * @return route data that matches. Or null if no matches.
	 */
	static public Object[] get(String method, String uri) {
		for(Route route : routes) {
			// test if method matchs
			for(String m : route.methods) {
				if (method.equals(m)) {
					break;
				}
			}

			// test if uri pattern matchs
			if (uri.startsWith(route.pattern)) { //XXX by now only startsWith  
				return route.data;
			}
		}
		
		return null;
	}
	
}