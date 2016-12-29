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
package ar.gov.rosario.gait.def.buss.dao;

public class DefDAOFactory {

    private static final DefDAOFactory INSTANCE = new DefDAOFactory();
    
    private ParametroDAO			parametroDAO;
    private DireccionDAO			direccionDAO;
    private AreaDAO					areaDAO;
    private DireccionAplicacionPerfilDAO direccionAplicacionPerfilDAO;
    private AreaAplicacionPerfilDAO					areaAplicacionPerfilDAO;
   
    private DefDAOFactory() {
        super();  
        this.parametroDAO				= new ParametroDAO();
        this.direccionDAO				= new DireccionDAO();
        this.areaDAO					= new AreaDAO();
        this.direccionAplicacionPerfilDAO = new DireccionAplicacionPerfilDAO();
        this.areaAplicacionPerfilDAO					= new AreaAplicacionPerfilDAO();
    }

	public static ParametroDAO getParametroDAO() {
		return INSTANCE.parametroDAO;
	}
	
	public static DireccionDAO getDireccionDAO() {
		return INSTANCE.direccionDAO;
	}

	public static AreaDAO getAreaDAO() {
		return INSTANCE.areaDAO;
	}
	public static DireccionAplicacionPerfilDAO getDireccionAplicacionPerfilDAO() {
		return INSTANCE.direccionAplicacionPerfilDAO;
	}
	public static AreaAplicacionPerfilDAO getAreaAplicacionPerfilDAO() {
		return INSTANCE.areaAplicacionPerfilDAO;
	}
}
