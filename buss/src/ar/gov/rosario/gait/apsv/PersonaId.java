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

package ar.gov.rosario.gait.apsv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PersonaId complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PersonaId">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idPersona" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idPersonaSec" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idPersonaSec2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaId", propOrder = {
    "idPersona",
    "idPersonaSec",
    "idPersonaSec2"
})
public class PersonaId {

    protected int idPersona;
    protected int idPersonaSec;
    protected int idPersonaSec2;

    /**
     * Gets the value of the idPersona property.
     * 
     */
    public int getIdPersona() {
        return idPersona;
    }

    /**
     * Sets the value of the idPersona property.
     * 
     */
    public void setIdPersona(int value) {
        this.idPersona = value;
    }

    /**
     * Gets the value of the idPersonaSec property.
     * 
     */
    public int getIdPersonaSec() {
        return idPersonaSec;
    }

    /**
     * Sets the value of the idPersonaSec property.
     * 
     */
    public void setIdPersonaSec(int value) {
        this.idPersonaSec = value;
    }

    /**
     * Gets the value of the idPersonaSec2 property.
     * 
     */
    public int getIdPersonaSec2() {
        return idPersonaSec2;
    }

    /**
     * Sets the value of the idPersonaSec2 property.
     * 
     */
    public void setIdPersonaSec2(int value) {
        this.idPersonaSec2 = value;
    }

}
