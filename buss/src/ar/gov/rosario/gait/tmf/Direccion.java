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
 * <p>Java class for Direccion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Direccion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codCalle" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nombreCalle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="bis" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="letraCalle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="monoblock" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="piso" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="departamento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codPostalLocalidad" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="subPostalLocalidad" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nombreLocalidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codPais" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codCalleInterseccion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Direccion", propOrder = {
    "codCalle",
    "nombreCalle",
    "numero",
    "bis",
    "letraCalle",
    "monoblock",
    "piso",
    "departamento",
    "codPostalLocalidad",
    "subPostalLocalidad",
    "nombreLocalidad",
    "codPais",
    "codCalleInterseccion"
})
public class Direccion {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer codCalle;
    @XmlElement(required = true)
    protected String nombreCalle;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer numero;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean bis;
    @XmlElement(required = true, nillable = true)
    protected String letraCalle;
    @XmlElement(required = true, nillable = true)
    protected String monoblock;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer piso;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer departamento;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer codPostalLocalidad;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer subPostalLocalidad;
    @XmlElement(required = true, nillable = true)
    protected String nombreLocalidad;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer codPais;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer codCalleInterseccion;

    /**
     * Gets the value of the codCalle property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodCalle() {
        return codCalle;
    }

    /**
     * Sets the value of the codCalle property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodCalle(Integer value) {
        this.codCalle = value;
    }

    /**
     * Gets the value of the nombreCalle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreCalle() {
        return nombreCalle;
    }

    /**
     * Sets the value of the nombreCalle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreCalle(String value) {
        this.nombreCalle = value;
    }

    /**
     * Gets the value of the numero property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumero(Integer value) {
        this.numero = value;
    }

    /**
     * Gets the value of the bis property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBis() {
        return bis;
    }

    /**
     * Sets the value of the bis property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBis(Boolean value) {
        this.bis = value;
    }

    /**
     * Gets the value of the letraCalle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLetraCalle() {
        return letraCalle;
    }

    /**
     * Sets the value of the letraCalle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLetraCalle(String value) {
        this.letraCalle = value;
    }

    /**
     * Gets the value of the monoblock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonoblock() {
        return monoblock;
    }

    /**
     * Sets the value of the monoblock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonoblock(String value) {
        this.monoblock = value;
    }

    /**
     * Gets the value of the piso property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPiso() {
        return piso;
    }

    /**
     * Sets the value of the piso property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPiso(Integer value) {
        this.piso = value;
    }

    /**
     * Gets the value of the departamento property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDepartamento() {
        return departamento;
    }

    /**
     * Sets the value of the departamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDepartamento(Integer value) {
        this.departamento = value;
    }

    /**
     * Gets the value of the codPostalLocalidad property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodPostalLocalidad() {
        return codPostalLocalidad;
    }

    /**
     * Sets the value of the codPostalLocalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodPostalLocalidad(Integer value) {
        this.codPostalLocalidad = value;
    }

    /**
     * Gets the value of the subPostalLocalidad property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSubPostalLocalidad() {
        return subPostalLocalidad;
    }

    /**
     * Sets the value of the subPostalLocalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSubPostalLocalidad(Integer value) {
        this.subPostalLocalidad = value;
    }

    /**
     * Gets the value of the nombreLocalidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreLocalidad() {
        return nombreLocalidad;
    }

    /**
     * Sets the value of the nombreLocalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreLocalidad(String value) {
        this.nombreLocalidad = value;
    }

    /**
     * Gets the value of the codPais property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodPais() {
        return codPais;
    }

    /**
     * Sets the value of the codPais property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodPais(Integer value) {
        this.codPais = value;
    }

    /**
     * Gets the value of the codCalleInterseccion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodCalleInterseccion() {
        return codCalleInterseccion;
    }

    /**
     * Sets the value of the codCalleInterseccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodCalleInterseccion(Integer value) {
        this.codCalleInterseccion = value;
    }

}
