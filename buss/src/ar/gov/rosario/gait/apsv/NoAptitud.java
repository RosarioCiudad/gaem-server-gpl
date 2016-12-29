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

package ar.gov.rosario.gait.apsv;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for NoAptitud complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NoAptitud">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="licNaSec" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fechaDeclaracion" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="fechaCarga" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="usuarioCarga" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="centro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="medico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="matricula" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="declaracion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tiempoDeRevision" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="patologias" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="solicitudes" type="{http://www.rosario.gov.ar/Apsv/schema}Solicitud" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="tiposLicencia" type="{http://www.rosario.gov.ar/Apsv/schema}ClaseLicencia" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NoAptitud", propOrder = {
    "licNaSec",
    "fechaDeclaracion",
    "fechaCarga",
    "usuarioCarga",
    "centro",
    "medico",
    "matricula",
    "declaracion",
    "observaciones",
    "tiempoDeRevision",
    "patologias",
    "solicitudes",
    "tiposLicencia"
})
public class NoAptitud {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer licNaSec;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaDeclaracion;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaCarga;
    @XmlElement(required = true, nillable = true)
    protected String usuarioCarga;
    @XmlElement(required = true, nillable = true)
    protected String centro;
    @XmlElement(required = true, nillable = true)
    protected String medico;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer matricula;
    @XmlElement(required = true, nillable = true)
    protected String declaracion;
    @XmlElement(required = true, nillable = true)
    protected String observaciones;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer tiempoDeRevision;
    @XmlElement(required = true, nillable = true)
    protected String patologias;
    protected List<Solicitud> solicitudes;
    protected List<ClaseLicencia> tiposLicencia;

    /**
     * Gets the value of the licNaSec property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLicNaSec() {
        return licNaSec;
    }

    /**
     * Sets the value of the licNaSec property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLicNaSec(Integer value) {
        this.licNaSec = value;
    }

    /**
     * Gets the value of the fechaDeclaracion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaDeclaracion() {
        return fechaDeclaracion;
    }

    /**
     * Sets the value of the fechaDeclaracion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaDeclaracion(XMLGregorianCalendar value) {
        this.fechaDeclaracion = value;
    }

    /**
     * Gets the value of the fechaCarga property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaCarga() {
        return fechaCarga;
    }

    /**
     * Sets the value of the fechaCarga property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaCarga(XMLGregorianCalendar value) {
        this.fechaCarga = value;
    }

    /**
     * Gets the value of the usuarioCarga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuarioCarga() {
        return usuarioCarga;
    }

    /**
     * Sets the value of the usuarioCarga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuarioCarga(String value) {
        this.usuarioCarga = value;
    }

    /**
     * Gets the value of the centro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCentro() {
        return centro;
    }

    /**
     * Sets the value of the centro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCentro(String value) {
        this.centro = value;
    }

    /**
     * Gets the value of the medico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedico() {
        return medico;
    }

    /**
     * Sets the value of the medico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedico(String value) {
        this.medico = value;
    }

    /**
     * Gets the value of the matricula property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMatricula() {
        return matricula;
    }

    /**
     * Sets the value of the matricula property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMatricula(Integer value) {
        this.matricula = value;
    }

    /**
     * Gets the value of the declaracion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeclaracion() {
        return declaracion;
    }

    /**
     * Sets the value of the declaracion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeclaracion(String value) {
        this.declaracion = value;
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
     * Gets the value of the tiempoDeRevision property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTiempoDeRevision() {
        return tiempoDeRevision;
    }

    /**
     * Sets the value of the tiempoDeRevision property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTiempoDeRevision(Integer value) {
        this.tiempoDeRevision = value;
    }

    /**
     * Gets the value of the patologias property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatologias() {
        return patologias;
    }

    /**
     * Sets the value of the patologias property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatologias(String value) {
        this.patologias = value;
    }

    /**
     * Gets the value of the solicitudes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the solicitudes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSolicitudes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Solicitud }
     * 
     * 
     */
    public List<Solicitud> getSolicitudes() {
        if (solicitudes == null) {
            solicitudes = new ArrayList<Solicitud>();
        }
        return this.solicitudes;
    }

    /**
     * Gets the value of the tiposLicencia property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tiposLicencia property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTiposLicencia().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClaseLicencia }
     * 
     * 
     */
    public List<ClaseLicencia> getTiposLicencia() {
        if (tiposLicencia == null) {
            tiposLicencia = new ArrayList<ClaseLicencia>();
        }
        return this.tiposLicencia;
    }

}
