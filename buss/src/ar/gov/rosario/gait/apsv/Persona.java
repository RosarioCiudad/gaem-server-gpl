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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Persona complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Persona">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codDoc" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nroDoc" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sexo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nombres" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="apellido" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="apellidoMaterno" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idDocumentoExtendidoPor" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="documentoExtendidoPor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaNacimiento" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="idPaisNacimiento" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="paisNacimiento" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nacionalidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idNacionalidad" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="grupoSanguineo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="factorRh" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="donante" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="telefono" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="celular" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="direccion" type="{http://www.rosario.gov.ar/Apsv/schema}Direccion"/>
 *         &lt;element name="idPersona" type="{http://www.rosario.gov.ar/Apsv/schema}PersonaId"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Persona", propOrder = {
    "codDoc",
    "nroDoc",
    "sexo",
    "nombres",
    "apellido",
    "apellidoMaterno",
    "idDocumentoExtendidoPor",
    "documentoExtendidoPor",
    "fechaNacimiento",
    "idPaisNacimiento",
    "paisNacimiento",
    "nacionalidad",
    "idNacionalidad",
    "grupoSanguineo",
    "factorRh",
    "donante",
    "telefono",
    "celular",
    "direccion",
    "idPersona"
})
public class Persona {

    protected int codDoc;
    protected int nroDoc;
    @XmlElement(required = true)
    protected String sexo;
    @XmlElement(required = true)
    protected String nombres;
    @XmlElement(required = true)
    protected String apellido;
    @XmlElement(required = true)
    protected String apellidoMaterno;
    protected int idDocumentoExtendidoPor;
    @XmlElement(required = true)
    protected String documentoExtendidoPor;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaNacimiento;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer idPaisNacimiento;
    @XmlElement(required = true)
    protected String paisNacimiento;
    @XmlElement(required = true)
    protected String nacionalidad;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer idNacionalidad;
    @XmlElement(required = true)
    protected String grupoSanguineo;
    @XmlElement(required = true)
    protected String factorRh;
    protected boolean donante;
    @XmlElement(required = true)
    protected String telefono;
    @XmlElement(required = true)
    protected String celular;
    @XmlElement(required = true)
    protected Direccion direccion;
    @XmlElement(required = true)
    protected PersonaId idPersona;

    /**
     * Gets the value of the codDoc property.
     * 
     */
    public int getCodDoc() {
        return codDoc;
    }

    /**
     * Sets the value of the codDoc property.
     * 
     */
    public void setCodDoc(int value) {
        this.codDoc = value;
    }

    /**
     * Gets the value of the nroDoc property.
     * 
     */
    public int getNroDoc() {
        return nroDoc;
    }

    /**
     * Sets the value of the nroDoc property.
     * 
     */
    public void setNroDoc(int value) {
        this.nroDoc = value;
    }

    /**
     * Gets the value of the sexo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Sets the value of the sexo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSexo(String value) {
        this.sexo = value;
    }

    /**
     * Gets the value of the nombres property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Sets the value of the nombres property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombres(String value) {
        this.nombres = value;
    }

    /**
     * Gets the value of the apellido property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Sets the value of the apellido property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellido(String value) {
        this.apellido = value;
    }

    /**
     * Gets the value of the apellidoMaterno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Sets the value of the apellidoMaterno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApellidoMaterno(String value) {
        this.apellidoMaterno = value;
    }

    /**
     * Gets the value of the idDocumentoExtendidoPor property.
     * 
     */
    public int getIdDocumentoExtendidoPor() {
        return idDocumentoExtendidoPor;
    }

    /**
     * Sets the value of the idDocumentoExtendidoPor property.
     * 
     */
    public void setIdDocumentoExtendidoPor(int value) {
        this.idDocumentoExtendidoPor = value;
    }

    /**
     * Gets the value of the documentoExtendidoPor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentoExtendidoPor() {
        return documentoExtendidoPor;
    }

    /**
     * Sets the value of the documentoExtendidoPor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentoExtendidoPor(String value) {
        this.documentoExtendidoPor = value;
    }

    /**
     * Gets the value of the fechaNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Sets the value of the fechaNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaNacimiento(XMLGregorianCalendar value) {
        this.fechaNacimiento = value;
    }

    /**
     * Gets the value of the idPaisNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdPaisNacimiento() {
        return idPaisNacimiento;
    }

    /**
     * Sets the value of the idPaisNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdPaisNacimiento(Integer value) {
        this.idPaisNacimiento = value;
    }

    /**
     * Gets the value of the paisNacimiento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaisNacimiento() {
        return paisNacimiento;
    }

    /**
     * Sets the value of the paisNacimiento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaisNacimiento(String value) {
        this.paisNacimiento = value;
    }

    /**
     * Gets the value of the nacionalidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Sets the value of the nacionalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNacionalidad(String value) {
        this.nacionalidad = value;
    }

    /**
     * Gets the value of the idNacionalidad property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdNacionalidad() {
        return idNacionalidad;
    }

    /**
     * Sets the value of the idNacionalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdNacionalidad(Integer value) {
        this.idNacionalidad = value;
    }

    /**
     * Gets the value of the grupoSanguineo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    /**
     * Sets the value of the grupoSanguineo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrupoSanguineo(String value) {
        this.grupoSanguineo = value;
    }

    /**
     * Gets the value of the factorRh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFactorRh() {
        return factorRh;
    }

    /**
     * Sets the value of the factorRh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFactorRh(String value) {
        this.factorRh = value;
    }

    /**
     * Gets the value of the donante property.
     * 
     */
    public boolean isDonante() {
        return donante;
    }

    /**
     * Sets the value of the donante property.
     * 
     */
    public void setDonante(boolean value) {
        this.donante = value;
    }

    /**
     * Gets the value of the telefono property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the value of the telefono property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefono(String value) {
        this.telefono = value;
    }

    /**
     * Gets the value of the celular property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCelular() {
        return celular;
    }

    /**
     * Sets the value of the celular property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCelular(String value) {
        this.celular = value;
    }

    /**
     * Gets the value of the direccion property.
     * 
     * @return
     *     possible object is
     *     {@link Direccion }
     *     
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Sets the value of the direccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Direccion }
     *     
     */
    public void setDireccion(Direccion value) {
        this.direccion = value;
    }

    /**
     * Gets the value of the idPersona property.
     * 
     * @return
     *     possible object is
     *     {@link PersonaId }
     *     
     */
    public PersonaId getIdPersona() {
        return idPersona;
    }

    /**
     * Sets the value of the idPersona property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonaId }
     *     
     */
    public void setIdPersona(PersonaId value) {
        this.idPersona = value;
    }

}
