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
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for usuarioDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="usuarioDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actor" type="{http://ws.admusu.santafeciudad.gov.ar/}actorDto" minOccurs="0"/>
 *         &lt;element name="cambiaClave" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="cuentaBloqueada" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="cuentaExpira" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="cuentaHabilitada" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaAlta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaExpira" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaUltAcceso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaUltCambioClave" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="grupos" type="{http://ws.admusu.santafeciudad.gov.ar/}grupoUsuarioDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idUsuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="palabraClave" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="predefinido" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="rolAdministrador" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "usuarioDto", propOrder = {
    "actor",
    "cambiaClave",
    "cuentaBloqueada",
    "cuentaExpira",
    "cuentaHabilitada",
    "email",
    "fechaAlta",
    "fechaExpira",
    "fechaUltAcceso",
    "fechaUltCambioClave",
    "grupos",
    "id",
    "idUsuario",
    "palabraClave",
    "predefinido",
    "rolAdministrador",
    "version"
})
public class UsuarioDto {

    protected ActorDto actor;
    protected boolean cambiaClave;
    protected boolean cuentaBloqueada;
    protected boolean cuentaExpira;
    protected boolean cuentaHabilitada;
    protected String email;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaAlta;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaExpira;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaUltAcceso;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaUltCambioClave;
    @XmlElement(nillable = true)
    protected List<GrupoUsuarioDto> grupos;
    protected Long id;
    protected String idUsuario;
    protected String palabraClave;
    protected boolean predefinido;
    protected boolean rolAdministrador;
    protected int version;

    /**
     * Gets the value of the actor property.
     * 
     * @return
     *     possible object is
     *     {@link ActorDto }
     *     
     */
    public ActorDto getActor() {
        return actor;
    }

    /**
     * Sets the value of the actor property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActorDto }
     *     
     */
    public void setActor(ActorDto value) {
        this.actor = value;
    }

    /**
     * Gets the value of the cambiaClave property.
     * 
     */
    public boolean isCambiaClave() {
        return cambiaClave;
    }

    /**
     * Sets the value of the cambiaClave property.
     * 
     */
    public void setCambiaClave(boolean value) {
        this.cambiaClave = value;
    }

    /**
     * Gets the value of the cuentaBloqueada property.
     * 
     */
    public boolean isCuentaBloqueada() {
        return cuentaBloqueada;
    }

    /**
     * Sets the value of the cuentaBloqueada property.
     * 
     */
    public void setCuentaBloqueada(boolean value) {
        this.cuentaBloqueada = value;
    }

    /**
     * Gets the value of the cuentaExpira property.
     * 
     */
    public boolean isCuentaExpira() {
        return cuentaExpira;
    }

    /**
     * Sets the value of the cuentaExpira property.
     * 
     */
    public void setCuentaExpira(boolean value) {
        this.cuentaExpira = value;
    }

    /**
     * Gets the value of the cuentaHabilitada property.
     * 
     */
    public boolean isCuentaHabilitada() {
        return cuentaHabilitada;
    }

    /**
     * Sets the value of the cuentaHabilitada property.
     * 
     */
    public void setCuentaHabilitada(boolean value) {
        this.cuentaHabilitada = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the fechaAlta property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaAlta() {
        return fechaAlta;
    }

    /**
     * Sets the value of the fechaAlta property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaAlta(XMLGregorianCalendar value) {
        this.fechaAlta = value;
    }

    /**
     * Gets the value of the fechaExpira property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaExpira() {
        return fechaExpira;
    }

    /**
     * Sets the value of the fechaExpira property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaExpira(XMLGregorianCalendar value) {
        this.fechaExpira = value;
    }

    /**
     * Gets the value of the fechaUltAcceso property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaUltAcceso() {
        return fechaUltAcceso;
    }

    /**
     * Sets the value of the fechaUltAcceso property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaUltAcceso(XMLGregorianCalendar value) {
        this.fechaUltAcceso = value;
    }

    /**
     * Gets the value of the fechaUltCambioClave property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaUltCambioClave() {
        return fechaUltCambioClave;
    }

    /**
     * Sets the value of the fechaUltCambioClave property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaUltCambioClave(XMLGregorianCalendar value) {
        this.fechaUltCambioClave = value;
    }

    /**
     * Gets the value of the grupos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the grupos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGrupos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GrupoUsuarioDto }
     * 
     * 
     */
    public List<GrupoUsuarioDto> getGrupos() {
        if (grupos == null) {
            grupos = new ArrayList<GrupoUsuarioDto>();
        }
        return this.grupos;
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
     * Gets the value of the idUsuario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdUsuario() {
        return idUsuario;
    }

    /**
     * Sets the value of the idUsuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdUsuario(String value) {
        this.idUsuario = value;
    }

    /**
     * Gets the value of the palabraClave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPalabraClave() {
        return palabraClave;
    }

    /**
     * Sets the value of the palabraClave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPalabraClave(String value) {
        this.palabraClave = value;
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

}
