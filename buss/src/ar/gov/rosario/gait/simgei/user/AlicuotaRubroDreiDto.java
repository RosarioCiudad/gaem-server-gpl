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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for alicuotaRubroDreiDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="alicuotaRubroDreiDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="alicuota" type="{http://ws.admusu.santafeciudad.gov.ar/}cantidad8" minOccurs="0"/>
 *         &lt;element name="derechoMinimo" type="{http://ws.admusu.santafeciudad.gov.ar/}cantidad8" minOccurs="0"/>
 *         &lt;element name="fechaVigencia" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rubro" type="{http://ws.admusu.santafeciudad.gov.ar/}rubroDreiDto" minOccurs="0"/>
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
@XmlType(name = "alicuotaRubroDreiDto", propOrder = {
    "alicuota",
    "derechoMinimo",
    "fechaVigencia",
    "id",
    "item",
    "observaciones",
    "rubro",
    "secuencia",
    "version"
})
public class AlicuotaRubroDreiDto {

    protected Cantidad8 alicuota;
    protected Cantidad8 derechoMinimo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaVigencia;
    protected Long id;
    protected short item;
    protected String observaciones;
    protected RubroDreiDto rubro;
    protected short secuencia;
    protected int version;

    /**
     * Gets the value of the alicuota property.
     * 
     * @return
     *     possible object is
     *     {@link Cantidad8 }
     *     
     */
    public Cantidad8 getAlicuota() {
        return alicuota;
    }

    /**
     * Sets the value of the alicuota property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cantidad8 }
     *     
     */
    public void setAlicuota(Cantidad8 value) {
        this.alicuota = value;
    }

    /**
     * Gets the value of the derechoMinimo property.
     * 
     * @return
     *     possible object is
     *     {@link Cantidad8 }
     *     
     */
    public Cantidad8 getDerechoMinimo() {
        return derechoMinimo;
    }

    /**
     * Sets the value of the derechoMinimo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cantidad8 }
     *     
     */
    public void setDerechoMinimo(Cantidad8 value) {
        this.derechoMinimo = value;
    }

    /**
     * Gets the value of the fechaVigencia property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaVigencia() {
        return fechaVigencia;
    }

    /**
     * Sets the value of the fechaVigencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaVigencia(XMLGregorianCalendar value) {
        this.fechaVigencia = value;
    }

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
     * Gets the value of the observaciones property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Sets the value of the observaciones property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservaciones(String value) {
        this.observaciones = value;
    }

    /**
     * Gets the value of the rubro property.
     * 
     * @return
     *     possible object is
     *     {@link RubroDreiDto }
     *     
     */
    public RubroDreiDto getRubro() {
        return rubro;
    }

    /**
     * Sets the value of the rubro property.
     * 
     * @param value
     *     allowed object is
     *     {@link RubroDreiDto }
     *     
     */
    public void setRubro(RubroDreiDto value) {
        this.rubro = value;
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
