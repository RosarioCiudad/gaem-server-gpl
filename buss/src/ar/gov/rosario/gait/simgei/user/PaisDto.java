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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for paisDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="paisDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cuitPF" type="{http://ws.admusu.santafeciudad.gov.ar/}cuit" minOccurs="0"/>
 *         &lt;element name="cuitPJ" type="{http://ws.admusu.santafeciudad.gov.ar/}cuit" minOccurs="0"/>
 *         &lt;element name="descripcion" type="{http://ws.admusu.santafeciudad.gov.ar/}descripcion" minOccurs="0"/>
 *         &lt;element name="gentilicio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="provincias" type="{http://ws.admusu.santafeciudad.gov.ar/}provinciaEstadoDto" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "paisDto", propOrder = {
    "codigo",
    "cuitPF",
    "cuitPJ",
    "descripcion",
    "gentilicio",
    "id",
    "provincias",
    "version"
})
public class PaisDto {

    protected String codigo;
    protected Cuit cuitPF;
    protected Cuit cuitPJ;
    protected Descripcion descripcion;
    protected String gentilicio;
    protected Long id;
    @XmlElement(nillable = true)
    protected List<ProvinciaEstadoDto> provincias;
    protected int version;

    /**
     * Gets the value of the codigo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Sets the value of the codigo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Gets the value of the cuitPF property.
     * 
     * @return
     *     possible object is
     *     {@link Cuit }
     *     
     */
    public Cuit getCuitPF() {
        return cuitPF;
    }

    /**
     * Sets the value of the cuitPF property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cuit }
     *     
     */
    public void setCuitPF(Cuit value) {
        this.cuitPF = value;
    }

    /**
     * Gets the value of the cuitPJ property.
     * 
     * @return
     *     possible object is
     *     {@link Cuit }
     *     
     */
    public Cuit getCuitPJ() {
        return cuitPJ;
    }

    /**
     * Sets the value of the cuitPJ property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cuit }
     *     
     */
    public void setCuitPJ(Cuit value) {
        this.cuitPJ = value;
    }

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link Descripcion }
     *     
     */
    public Descripcion getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Descripcion }
     *     
     */
    public void setDescripcion(Descripcion value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the gentilicio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGentilicio() {
        return gentilicio;
    }

    /**
     * Sets the value of the gentilicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGentilicio(String value) {
        this.gentilicio = value;
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
     * Gets the value of the provincias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the provincias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProvincias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProvinciaEstadoDto }
     * 
     * 
     */
    public List<ProvinciaEstadoDto> getProvincias() {
        if (provincias == null) {
            provincias = new ArrayList<ProvinciaEstadoDto>();
        }
        return this.provincias;
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
