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
 * <p>Java class for integraUTEDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="integraUTEDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="integrante" type="{http://ws.admusu.santafeciudad.gov.ar/}actorDto" minOccurs="0"/>
 *         &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="porcentaje" type="{http://ws.admusu.santafeciudad.gov.ar/}cantidad2" minOccurs="0"/>
 *         &lt;element name="secuencia" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "integraUTEDto", propOrder = {
    "id",
    "integrante",
    "item",
    "porcentaje",
    "secuencia",
    "version"
})
public class IntegraUTEDto {

    protected Long id;
    protected ActorDto integrante;
    protected short item;
    protected Cantidad2 porcentaje;
    protected short secuencia;
    protected int version;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the integrante property.
     * 
     * @return
     *     possible object is
     *     {@link ActorDto }
     *     
     */
    public ActorDto getIntegrante() {
        return integrante;
    }

    /**
     * Sets the value of the integrante property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActorDto }
     *     
     */
    public void setIntegrante(ActorDto value) {
        this.integrante = value;
    }

    /**
     * Gets the value of the item property.
     * 
     */
    public short getItem() {
        return item;
    }

    /**
     * Sets the value of the item property.
     * 
     */
    public void setItem(short value) {
        this.item = value;
    }

    /**
     * Gets the value of the porcentaje property.
     * 
     * @return
     *     possible object is
     *     {@link Cantidad2 }
     *     
     */
    public Cantidad2 getPorcentaje() {
        return porcentaje;
    }

    /**
     * Sets the value of the porcentaje property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cantidad2 }
     *     
     */
    public void setPorcentaje(Cantidad2 value) {
        this.porcentaje = value;
    }

    /**
     * Gets the value of the secuencia property.
     * 
     */
    public short getSecuencia() {
        return secuencia;
    }

    /**
     * Sets the value of the secuencia property.
     * 
     */
    public void setSecuencia(short value) {
        this.secuencia = value;
    }

    /**
     * Gets the value of the version property.
     * 
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     */
    public void setVersion(int value) {
        this.version = value;
    }

}
