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

package coop.tecso.demoda.iface.helper;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class CheckPerformanceFilter implements Filter {

	private static Logger log = Logger.getLogger((CheckPerformanceFilter.class));
	
	public void init(FilterConfig arg0) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        chain.doFilter(request, response);
        long stopTime = System.currentTimeMillis();
        
        String url = ((HttpServletRequest) request).getRequestURL().toString();
        if(url==null) url = "";
        String reqAttMethod = request.getParameter("method");
        if(reqAttMethod==null) reqAttMethod = "";
		String reqAttSelectedId = request.getParameter("selectedId");
		if(reqAttSelectedId==null) reqAttSelectedId = "";
		String act = request.getParameter("act");
		if(act==null) act = "";
		String info = "(CHEQUEO DE TIEMPOS DE RESPUESTA) Url: '"+url+"', Method: "+reqAttMethod+", Act: "+act+", SelectedId: "+reqAttSelectedId+", ";
       
        log.info(info+"Tiempo de ejecución: " + (stopTime - startTime) + " ms");
        //System.out.println("Time to execute request: " + (stopTime - startTime) + " milliseconds");

	}


}
