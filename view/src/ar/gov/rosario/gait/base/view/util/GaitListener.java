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
package ar.gov.rosario.gait.base.view.util; 

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ar.gov.rosario.gait.def.iface.service.DefServiceLocator;

public class GaitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
		try {
	    	System.out.println("INICIANDO GAIT...");
			DefServiceLocator.getConfiguracionService().initializeGait();
		} catch (Exception e) {
			System.out.println("**************************************");
			System.out.println("GAIT ERROR:");
			System.out.println("No se pudo Inicializar GAIT.");
			System.out.println("El comportamiento de la aplicacion es inesperado.");
			System.out.println("El error fue: " + e);
			e.printStackTrace();
			System.out.println("**************************************");
		}
    }

    public void contextDestroyed(ServletContextEvent event) {
		try {
	    	System.out.println("DESTRUYENDO GAIT...");
			DefServiceLocator.getConfiguracionService().destroyGait();
		} catch (Exception e) {
			System.out.println("**************************************");
			System.out.println("GAIT ERROR:");
			System.out.println("No se pudo Destruir GAIT.");
			System.out.println("El comportamiento de la aplicacion es inesperado.");
			System.out.println("El error fue: " + e);
			e.printStackTrace();
			System.out.println("**************************************");
		}
   }
}
