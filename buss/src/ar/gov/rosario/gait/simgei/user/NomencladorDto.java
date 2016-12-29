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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nomencladorDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nomencladorDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="altura" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="baja" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="codigo" type="{http://ws.admusu.santafeciudad.gov.ar/}cantidad2" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idCalle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="letra" type="{http://www.w3.org/2001/XMLSchema}unsignedShort"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreCalle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreLargo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="zonas" type="{http://ws.admusu.santafeciudad.gov.ar/}nomencladorZonaVerificacionDto" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nomencladorDto", propOrder = {
    "altura",
    "baja",
    "codigo",
    "id",
    "idCalle",
    "letra",
    "nombre",
    "nombreCalle",
    "nombreLargo",
    "observaciones",
    "version",
    "zonas"
})
public class NomencladorDto {

    protected int altura;
    protected boolean baja;
    protected Cantidad2 codigo;
    protected Long id;
    protected String idCalle;
    @XmlSchemaType(name = "unsignedShort")
    protected int letra;
    protected String nombre;
    protected String nombreCalle;
    protected String nombreLargo;
    protected String observaciones;
    protected int version;
    @XmlElement(nillable = true)
    protected List<NomencladorZonaVerificacionDto> zonas;

    /**
     * Gets the value of the altura property.
     * 
     */
    public int getAltura() {
        return altura;
    }

    /**
     * Sets the value of the altura property.
     * 
     */
    public void setAltura(int value) {
        this.altura = value;
    }

    /**
     * Gets the value of the baja property.
     * 
     */
    public boolean isBaja() {
        return baja;
    }

    /**
     * Sets the value of the baja property.
     * 
     */
    public void setBaja(boolean value) {
        this.baja = value;
    }

    /**
     * Gets the value of the codigo property.
     * 
     * @return
     *     possible object is
     *     {@link Cantidad2 }
     *     
     */
    public Cantidad2 getCodigo() {
        return codigo;
    }

    /**
     * Sets the value of the codigo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cantidad2 }
     *     
     */
    public void setCodigo(Cantidad2 value) {
        this.codigo = value;
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
     * Gets the value of the idCalle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCalle() {
        return idCalle;
    }

    /**
     * Sets the value of the idCalle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCalle(String value) {
        this.idCalle = value;
    }

    /**
     * Gets the value of the letra property.
     * 
     */
    public int getLetra() {
        return letra;
    }

    /**
     * Sets the value of the letra property.
     * 
     */
    public void setLetra(int value) {
        this.letra = value;
    }

    /**
     * Gets the value of the nombre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets the value of the nombre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
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
     * Gets the value of the nombreLargo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreLargo() {
        return nombreLargo;
    }

    /**
     * Sets the value of the nombreLargo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreLargo(String value) {
        this.nombreLargo = value;
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
     * Gets the value of the zonas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the zonas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZonas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NomencladorZonaVerificacionDto }
     * 
     * 
     */
    public List<NomencladorZonaVerificacionDto> getZonas() {
        if (zonas == null) {
            zonas = new ArrayList<NomencladorZonaVerificacionDto>();
        }
        return this.zonas;
    }

}
