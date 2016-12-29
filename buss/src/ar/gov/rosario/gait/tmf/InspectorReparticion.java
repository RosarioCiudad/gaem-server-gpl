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

package ar.gov.rosario.gait.tmf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InspectorReparticion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InspectorReparticion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codReparticion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nombreReparticion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nroInspector" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="apellidoInspector" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nombreInspector" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InspectorReparticion", propOrder = {
    "tipo",
    "codReparticion",
    "nombreReparticion",
    "nroInspector",
    "apellidoInspector",
    "nombreInspector"
})
public class InspectorReparticion {

    @XmlElement(required = true)
    protected String tipo;
    protected int codReparticion;
    @XmlElement(required = true)
    protected String nombreReparticion;
    protected int nroInspector;
    @XmlElement(required = true)
    protected String apellidoInspector;
    @XmlElement(required = true)
    protected String nombreInspector;

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the codReparticion property.
     * 
     */
    public int getCodReparticion() {
        return codReparticion;
    }

    /**
     * Sets the value of the codReparticion property.
     * 
     */
    public void setCodReparticion(int value) {
        this.codReparticion = value;
    }

    /**
     * Gets the value of the nombreReparticion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreReparticion() {
        return nombreReparticion;
    }

    /**
     * Sets the value of the nombreReparticion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreReparticion(String value) {
        this.nombreReparticion = value;
    }

    /**
     * Gets the value of the nroInspector property.
     * 
     */
    public int getNroInspector() {
        return nroInspector;
    }

    /**
     * Sets the value of the nroInspector property.
     * 
     */
    public void setNroInspector(int value) {
        this.nroInspector = value;
    }

    /**
     * Gets the value of the apellidoInspector property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellidoInspector() {
        return apellidoInspector;
    }

    /**
     * Sets the value of the apellidoInspector property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoInspector(String value) {
        this.apellidoInspector = value;
    }

    /**
     * Gets the value of the nombreInspector property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreInspector() {
        return nombreInspector;
    }

    /**
     * Sets the value of the nombreInspector property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreInspector(String value) {
        this.nombreInspector = value;
    }

}
