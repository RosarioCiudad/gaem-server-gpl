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

package ar.gov.rosario.gait.simgei.user;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
//CDPA_PIP
//import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "UsuarioWebService", targetNamespace = "http://ws.admusu.santafeciudad.gov.ar/")
//CDPA_PIP
//@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface UsuarioWebService {


    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    public String changePassword(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0,
        @WebParam(name = "arg1", partName = "arg1")
        String arg1,
        @WebParam(name = "arg2", partName = "arg2")
        String arg2);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    public String forgetPasswordExterno(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0,
        @WebParam(name = "arg1", partName = "arg1")
        String arg1);

    /**
     * 
     * @param usuario
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    public String forgetPasswordInterno(
        @WebParam(name = "usuario", partName = "usuario")
        String usuario);

    /**
     * 
     * @param usuario
     * @return
     *     returns ar.gov.rosario.gait.simgei.user.StringArray
     */
    @WebMethod
    @WebResult(partName = "return")
    public StringArray getGrupoUsuariosXUsuario(
        @WebParam(name = "usuario", partName = "usuario")
        String usuario);

    /**
     * 
     * @param usuario
     * @return
     *     returns ar.gov.rosario.gait.simgei.user.StringArray
     */
    @WebMethod
    @WebResult(partName = "return")
    public StringArray getSistemasXUsuario(
        @WebParam(name = "usuario", partName = "usuario")
        String usuario);

    /**
     * 
     * @param id
     * @return
     *     returns ar.gov.rosario.gait.simgei.user.UsuarioDto
     */
    @WebMethod
    @WebResult(partName = "return")
    public UsuarioDto getUsuarioById(
        @WebParam(name = "id", partName = "id")
        String id);

    /**
     * 
     * @param id
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    public String getUsuarioCambiaClave(
        @WebParam(name = "id", partName = "id")
        String id);

    /**
     * 
     * @param arg2
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(partName = "return")
    public String validaAccesoExterno(
        @WebParam(name = "arg0", partName = "arg0")
        String arg0,
        @WebParam(name = "arg1", partName = "arg1")
        String arg1,
        @WebParam(name = "arg2", partName = "arg2")
        String arg2);

}
