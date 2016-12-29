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
 * <p>Java class for nomencladorZonaVerificacionDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nomencladorZonaVerificacionDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="desde" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="hasta" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="nomenclador" type="{http://ws.admusu.santafeciudad.gov.ar/}nomencladorDto" minOccurs="0"/>
 *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secuencia" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="zona" type="{http://ws.admusu.santafeciudad.gov.ar/}zonaVerificacionDto" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nomencladorZonaVerificacionDto", propOrder = {
    "desde",
    "hasta",
    "id",
    "item",
    "nomenclador",
    "observaciones",
    "secuencia",
    "version",
    "zona"
})
public class NomencladorZonaVerificacionDto {

    protected int desde;
    protected int hasta;
    protected Long id;
    protected short item;
    protected NomencladorDto nomenclador;
    protected String observaciones;
    protected short secuencia;
    protected int version;
    protected ZonaVerificacionDto zona;

    /**
     * Gets the value of the desde property.
     * 
     */
    public int getDesde() {
        return desde;
    }

    /**
     * Sets the value of the desde property.
     * 
     */
    public void setDesde(int value) {
        this.desde = value;
    }

    /**
     * Gets the value of the hasta property.
     * 
     */
    public int getHasta() {
        return hasta;
    }

    /**
     * Sets the value of the hasta property.
     * 
     */
    public void setHasta(int value) {
        this.hasta = value;
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
     * Gets the value of the nomenclador property.
     * 
     * @return
     *     possible object is
     *     {@link NomencladorDto }
     *     
     */
    public NomencladorDto getNomenclador() {
        return nomenclador;
    }

    /**
     * Sets the value of the nomenclador property.
     * 
     * @param value
     *     allowed object is
     *     {@link NomencladorDto }
     *     
     */
    public void setNomenclador(NomencladorDto value) {
        this.nomenclador = value;
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

    /**
     * Gets the value of the zona property.
     * 
     * @return
     *     possible object is
     *     {@link ZonaVerificacionDto }
     *     
     */
    public ZonaVerificacionDto getZona() {
        return zona;
    }

    /**
     * Sets the value of the zona property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZonaVerificacionDto }
     *     
     */
    public void setZona(ZonaVerificacionDto value) {
        this.zona = value;
    }

}
