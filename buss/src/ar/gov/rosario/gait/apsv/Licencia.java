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
 * <p>Java class for Licencia complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Licencia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numeroLicencia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="claseLicencia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tiempoInhabilitacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="fechaOtorgamiento" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="fechaVencimiento" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="fechaRevalida" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="fechaProximaRevalida" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="restricciones" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="inhabilitada" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="solicitudes" type="{http://www.rosario.gov.ar/Apsv/schema}Solicitud" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Licencia", propOrder = {
    "numeroLicencia",
    "claseLicencia",
    "estado",
    "tiempoInhabilitacion",
    "fechaOtorgamiento",
    "fechaVencimiento",
    "fechaRevalida",
    "fechaProximaRevalida",
    "observaciones",
    "restricciones",
    "inhabilitada",
    "solicitudes"
})
public class Licencia {

    @XmlElement(required = true)
    protected String numeroLicencia;
    @XmlElement(required = true)
    protected String claseLicencia;
    @XmlElement(required = true)
    protected String estado;
    protected int tiempoInhabilitacion;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaOtorgamiento;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaVencimiento;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaRevalida;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaProximaRevalida;
    @XmlElement(required = true)
    protected String observaciones;
    @XmlElement(required = true)
    protected String restricciones;
    protected boolean inhabilitada;
    protected List<Solicitud> solicitudes;

    /**
     * Gets the value of the numeroLicencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    /**
     * Sets the value of the numeroLicencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroLicencia(String value) {
        this.numeroLicencia = value;
    }

    /**
     * Gets the value of the claseLicencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaseLicencia() {
        return claseLicencia;
    }

    /**
     * Sets the value of the claseLicencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaseLicencia(String value) {
        this.claseLicencia = value;
    }

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Gets the value of the tiempoInhabilitacion property.
     * 
     */
    public int getTiempoInhabilitacion() {
        return tiempoInhabilitacion;
    }

    /**
     * Sets the value of the tiempoInhabilitacion property.
     * 
     */
    public void setTiempoInhabilitacion(int value) {
        this.tiempoInhabilitacion = value;
    }

    /**
     * Gets the value of the fechaOtorgamiento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaOtorgamiento() {
        return fechaOtorgamiento;
    }

    /**
     * Sets the value of the fechaOtorgamiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaOtorgamiento(XMLGregorianCalendar value) {
        this.fechaOtorgamiento = value;
    }

    /**
     * Gets the value of the fechaVencimiento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Sets the value of the fechaVencimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaVencimiento(XMLGregorianCalendar value) {
        this.fechaVencimiento = value;
    }

    /**
     * Gets the value of the fechaRevalida property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaRevalida() {
        return fechaRevalida;
    }

    /**
     * Sets the value of the fechaRevalida property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaRevalida(XMLGregorianCalendar value) {
        this.fechaRevalida = value;
    }

    /**
     * Gets the value of the fechaProximaRevalida property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaProximaRevalida() {
        return fechaProximaRevalida;
    }

    /**
     * Sets the value of the fechaProximaRevalida property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaProximaRevalida(XMLGregorianCalendar value) {
        this.fechaProximaRevalida = value;
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
     * Gets the value of the restricciones property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRestricciones() {
        return restricciones;
    }

    /**
     * Sets the value of the restricciones property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRestricciones(String value) {
        this.restricciones = value;
    }

    /**
     * Gets the value of the inhabilitada property.
     * 
     */
    public boolean isInhabilitada() {
        return inhabilitada;
    }

    /**
     * Sets the value of the inhabilitada property.
     * 
     */
    public void setInhabilitada(boolean value) {
        this.inhabilitada = value;
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

}
