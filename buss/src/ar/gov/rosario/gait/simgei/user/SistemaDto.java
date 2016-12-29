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
 * <p>Java class for sistemaDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sistemaDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="enMantenimiento" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="menuNodos" type="{http://ws.admusu.santafeciudad.gov.ar/}menuNodoDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="msjMantenimiento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreSistema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroSistema" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="predefinido" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="rolAdministrador" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="versionSistema" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sistemaDto", propOrder = {
    "enMantenimiento",
    "id",
    "menuNodos",
    "msjMantenimiento",
    "nombreSistema",
    "numeroSistema",
    "predefinido",
    "rolAdministrador",
    "version",
    "versionSistema"
})
public class SistemaDto {

    protected boolean enMantenimiento;
    protected Long id;
    @XmlElement(nillable = true)
    protected List<MenuNodoDto> menuNodos;
    protected String msjMantenimiento;
    protected String nombreSistema;
    protected int numeroSistema;
    protected boolean predefinido;
    protected boolean rolAdministrador;
    protected int version;
    protected String versionSistema;

    /**
     * Gets the value of the enMantenimiento property.
     * 
     */
    public boolean isEnMantenimiento() {
        return enMantenimiento;
    }

    /**
     * Sets the value of the enMantenimiento property.
     * 
     */
    public void setEnMantenimiento(boolean value) {
        this.enMantenimiento = value;
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
     * Gets the value of the menuNodos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the menuNodos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMenuNodos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MenuNodoDto }
     * 
     * 
     */
    public List<MenuNodoDto> getMenuNodos() {
        if (menuNodos == null) {
            menuNodos = new ArrayList<MenuNodoDto>();
        }
        return this.menuNodos;
    }

    /**
     * Gets the value of the msjMantenimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsjMantenimiento() {
        return msjMantenimiento;
    }

    /**
     * Sets the value of the msjMantenimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsjMantenimiento(String value) {
        this.msjMantenimiento = value;
    }

    /**
     * Gets the value of the nombreSistema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreSistema() {
        return nombreSistema;
    }

    /**
     * Sets the value of the nombreSistema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreSistema(String value) {
        this.nombreSistema = value;
    }

    /**
     * Gets the value of the numeroSistema property.
     * 
     */
    public int getNumeroSistema() {
        return numeroSistema;
    }

    /**
     * Sets the value of the numeroSistema property.
     * 
     */
    public void setNumeroSistema(int value) {
        this.numeroSistema = value;
    }

    /**
     * Gets the value of the predefinido property.
     * 
     */
    public boolean isPredefinido() {
        return predefinido;
    }

    /**
     * Sets the value of the predefinido property.
     * 
     */
    public void setPredefinido(boolean value) {
        this.predefinido = value;
    }

    /**
     * Gets the value of the rolAdministrador property.
     * 
     */
    public boolean isRolAdministrador() {
        return rolAdministrador;
    }

    /**
     * Sets the value of the rolAdministrador property.
     * 
     */
    public void setRolAdministrador(boolean value) {
        this.rolAdministrador = value;
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
     * Gets the value of the versionSistema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersionSistema() {
        return versionSistema;
    }

    /**
     * Sets the value of the versionSistema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersionSistema(String value) {
        this.versionSistema = value;
    }

}
