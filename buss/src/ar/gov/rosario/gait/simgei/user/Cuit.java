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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cuit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cuit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigo1" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *         &lt;element name="codigo2" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codigo3" type="{http://www.w3.org/2001/XMLSchema}byte"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cuit", propOrder = {
    "codigo1",
    "codigo2",
    "codigo3"
})
public class Cuit {

    protected byte codigo1;
    protected int codigo2;
    protected byte codigo3;

    /**
     * Gets the value of the codigo1 property.
     * 
     */
    public byte getCodigo1() {
        return codigo1;
    }

    /**
     * Sets the value of the codigo1 property.
     * 
     */
    public void setCodigo1(byte value) {
        this.codigo1 = value;
    }

    /**
     * Gets the value of the codigo2 property.
     * 
     */
    public int getCodigo2() {
        return codigo2;
    }

    /**
     * Sets the value of the codigo2 property.
     * 
     */
    public void setCodigo2(int value) {
        this.codigo2 = value;
    }

    /**
     * Gets the value of the codigo3 property.
     * 
     */
    public byte getCodigo3() {
        return codigo3;
    }

    /**
     * Sets the value of the codigo3 property.
     * 
     */
    public void setCodigo3(byte value) {
        this.codigo3 = value;
    }

}
