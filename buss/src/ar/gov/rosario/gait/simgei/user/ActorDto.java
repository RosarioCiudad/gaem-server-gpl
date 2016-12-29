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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for actorDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="actorDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actividadesEconomicas" type="{http://ws.admusu.santafeciudad.gov.ar/}actividadEconomicaActorDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="actorUnificado" type="{http://ws.admusu.santafeciudad.gov.ar/}actorDto" minOccurs="0"/>
 *         &lt;element name="apellido" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="apellidoMaterno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="avatar" type="{http://ws.admusu.santafeciudad.gov.ar/}tempResourceRef" minOccurs="0"/>
 *         &lt;element name="circunscripcionRPC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clasificacion" type="{http://ws.admusu.santafeciudad.gov.ar/}clasificacionSectorialDto" minOccurs="0"/>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="condicionesIVA" type="{http://ws.admusu.santafeciudad.gov.ar/}ivaActorDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contactos" type="{http://ws.admusu.santafeciudad.gov.ar/}contactoActorDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="convenioMultilateral" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="copiaNumeroDocumento" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="cuit" type="{http://ws.admusu.santafeciudad.gov.ar/}cuit" minOccurs="0"/>
 *         &lt;element name="direccionesLocales" type="{http://ws.admusu.santafeciudad.gov.ar/}direccionActorDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="direccionesNoLocales" type="{http://ws.admusu.santafeciudad.gov.ar/}direccionActorDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="documento" type="{http://ws.admusu.santafeciudad.gov.ar/}documentoIdentidad" minOccurs="0"/>
 *         &lt;element name="donanteOrganos" type="{http://ws.admusu.santafeciudad.gov.ar/}siNoInformado" minOccurs="0"/>
 *         &lt;element name="duracion" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="emailPrincipal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="esUTE" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="estadoCivil" type="{http://ws.admusu.santafeciudad.gov.ar/}estadoCivil" minOccurs="0"/>
 *         &lt;element name="factorSanguineo" type="{http://ws.admusu.santafeciudad.gov.ar/}factorSanguineo" minOccurs="0"/>
 *         &lt;element name="fechaActualizacionAvatar" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaAlta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaBaja" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaInicio" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaNacimiento" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaRPC" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaUnificacion" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="firma" type="{http://ws.admusu.santafeciudad.gov.ar/}tempResourceRef" minOccurs="0"/>
 *         &lt;element name="folioRPC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grupoSanguineo" type="{http://ws.admusu.santafeciudad.gov.ar/}grupoSanguineo" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idDocumentoSidom" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="incidencias" type="{http://ws.admusu.santafeciudad.gov.ar/}incidenciaActorDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="integrantesOrganismo" type="{http://ws.admusu.santafeciudad.gov.ar/}integraOrganismoDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="integrantesPJ" type="{http://ws.admusu.santafeciudad.gov.ar/}integraPersonaJuridicaDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="integrantesUTE" type="{http://ws.admusu.santafeciudad.gov.ar/}integraUTEDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="libroRPC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="lugarTrabajo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="movilPrincipal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nacionalidad" type="{http://ws.admusu.santafeciudad.gov.ar/}paisDto" minOccurs="0"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreFantasia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreOrganismo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroIB" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroRPC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ocupacion" type="{http://ws.admusu.santafeciudad.gov.ar/}ocupacionDto" minOccurs="0"/>
 *         &lt;element name="origen" type="{http://ws.admusu.santafeciudad.gov.ar/}origenActor" minOccurs="0"/>
 *         &lt;element name="pais" type="{http://ws.admusu.santafeciudad.gov.ar/}paisDto" minOccurs="0"/>
 *         &lt;element name="paisDocumento" type="{http://ws.admusu.santafeciudad.gov.ar/}paisDto" minOccurs="0"/>
 *         &lt;element name="provinciaDocumento" type="{http://ws.admusu.santafeciudad.gov.ar/}provinciaEstadoDto" minOccurs="0"/>
 *         &lt;element name="razonSocial" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sexo" type="{http://ws.admusu.santafeciudad.gov.ar/}sexo" minOccurs="0"/>
 *         &lt;element name="sigla" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefonoPrincipal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipo" type="{http://ws.admusu.santafeciudad.gov.ar/}tipoActor" minOccurs="0"/>
 *         &lt;element name="tipoPersonaJuridica" type="{http://ws.admusu.santafeciudad.gov.ar/}tipoPersonaJuridicaDto" minOccurs="0"/>
 *         &lt;element name="tipoValidacion" type="{http://ws.admusu.santafeciudad.gov.ar/}tipoValidacionActor" minOccurs="0"/>
 *         &lt;element name="tomoRPC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "actorDto", propOrder = {
    "actividadesEconomicas",
    "actorUnificado",
    "apellido",
    "apellidoMaterno",
    "avatar",
    "circunscripcionRPC",
    "clasificacion",
    "codigo",
    "condicionesIVA",
    "contactos",
    "convenioMultilateral",
    "copiaNumeroDocumento",
    "cuit",
    "direccionesLocales",
    "direccionesNoLocales",
    "documento",
    "donanteOrganos",
    "duracion",
    "emailPrincipal",
    "esUTE",
    "estadoCivil",
    "factorSanguineo",
    "fechaActualizacionAvatar",
    "fechaAlta",
    "fechaBaja",
    "fechaInicio",
    "fechaNacimiento",
    "fechaRPC",
    "fechaUnificacion",
    "firma",
    "folioRPC",
    "grupoSanguineo",
    "id",
    "idDocumentoSidom",
    "incidencias",
    "integrantesOrganismo",
    "integrantesPJ",
    "integrantesUTE",
    "libroRPC",
    "lugarTrabajo",
    "movilPrincipal",
    "nacionalidad",
    "nombre",
    "nombreFantasia",
    "nombreOrganismo",
    "numeroIB",
    "numeroRPC",
    "observaciones",
    "ocupacion",
    "origen",
    "pais",
    "paisDocumento",
    "provinciaDocumento",
    "razonSocial",
    "sexo",
    "sigla",
    "telefonoPrincipal",
    "tipo",
    "tipoPersonaJuridica",
    "tipoValidacion",
    "tomoRPC",
    "version"
})
@XmlSeeAlso({
    PersonaFisicaDto.class
})
public class ActorDto {

    @XmlElement(nillable = true)
    protected List<ActividadEconomicaActorDto> actividadesEconomicas;
    protected ActorDto actorUnificado;
    protected String apellido;
    protected String apellidoMaterno;
    protected TempResourceRef avatar;
    protected String circunscripcionRPC;
    protected ClasificacionSectorialDto clasificacion;
    protected int codigo;
    @XmlElement(nillable = true)
    protected List<IvaActorDto> condicionesIVA;
    @XmlElement(nillable = true)
    protected List<ContactoActorDto> contactos;
    protected Boolean convenioMultilateral;
    protected Integer copiaNumeroDocumento;
    protected Cuit cuit;
    @XmlElement(nillable = true)
    protected List<DireccionActorDto> direccionesLocales;
    @XmlElement(nillable = true)
    protected List<DireccionActorDto> direccionesNoLocales;
    protected DocumentoIdentidad documento;
    protected SiNoInformado donanteOrganos;
    protected Short duracion;
    protected String emailPrincipal;
    protected Boolean esUTE;
    protected EstadoCivil estadoCivil;
    protected FactorSanguineo factorSanguineo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaActualizacionAvatar;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaAlta;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaBaja;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaInicio;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaNacimiento;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaRPC;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaUnificacion;
    protected TempResourceRef firma;
    protected String folioRPC;
    protected GrupoSanguineo grupoSanguineo;
    protected Long id;
    protected Integer idDocumentoSidom;
    @XmlElement(nillable = true)
    protected List<IncidenciaActorDto> incidencias;
    @XmlElement(nillable = true)
    protected List<IntegraOrganismoDto> integrantesOrganismo;
    @XmlElement(nillable = true)
    protected List<IntegraPersonaJuridicaDto> integrantesPJ;
    @XmlElement(nillable = true)
    protected List<IntegraUTEDto> integrantesUTE;
    protected String libroRPC;
    protected String lugarTrabajo;
    protected String movilPrincipal;
    protected PaisDto nacionalidad;
    protected String nombre;
    protected String nombreFantasia;
    protected String nombreOrganismo;
    protected String numeroIB;
    protected String numeroRPC;
    protected String observaciones;
    protected OcupacionDto ocupacion;
    protected OrigenActor origen;
    protected PaisDto pais;
    protected PaisDto paisDocumento;
    protected ProvinciaEstadoDto provinciaDocumento;
    protected String razonSocial;
    protected Sexo sexo;
    protected String sigla;
    protected String telefonoPrincipal;
    protected TipoActor tipo;
    protected TipoPersonaJuridicaDto tipoPersonaJuridica;
    protected TipoValidacionActor tipoValidacion;
    protected String tomoRPC;
    protected int version;

    /**
     * Gets the value of the actividadesEconomicas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the actividadesEconomicas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActividadesEconomicas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActividadEconomicaActorDto }
     * 
     * 
     */
    public List<ActividadEconomicaActorDto> getActividadesEconomicas() {
        if (actividadesEconomicas == null) {
            actividadesEconomicas = new ArrayList<ActividadEconomicaActorDto>();
        }
        return this.actividadesEconomicas;
    }

    /**
     * Gets the value of the actorUnificado property.
     * 
     * @return
     *     possible object is
     *     {@link ActorDto }
     *     
     */
    public ActorDto getActorUnificado() {
        return actorUnificado;
    }

    /**
     * Sets the value of the actorUnificado property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActorDto }
     *     
     */
    public void setActorUnificado(ActorDto value) {
        this.actorUnificado = value;
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
     * Gets the value of the avatar property.
     * 
     * @return
     *     possible object is
     *     {@link TempResourceRef }
     *     
     */
    public TempResourceRef getAvatar() {
        return avatar;
    }

    /**
     * Sets the value of the avatar property.
     * 
     * @param value
     *     allowed object is
     *     {@link TempResourceRef }
     *     
     */
    public void setAvatar(TempResourceRef value) {
        this.avatar = value;
    }

    /**
     * Gets the value of the circunscripcionRPC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCircunscripcionRPC() {
        return circunscripcionRPC;
    }

    /**
     * Sets the value of the circunscripcionRPC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCircunscripcionRPC(String value) {
        this.circunscripcionRPC = value;
    }

    /**
     * Gets the value of the clasificacion property.
     * 
     * @return
     *     possible object is
     *     {@link ClasificacionSectorialDto }
     *     
     */
    public ClasificacionSectorialDto getClasificacion() {
        return clasificacion;
    }

    /**
     * Sets the value of the clasificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClasificacionSectorialDto }
     *     
     */
    public void setClasificacion(ClasificacionSectorialDto value) {
        this.clasificacion = value;
    }

    /**
     * Gets the value of the codigo property.
     * 
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Sets the value of the codigo property.
     * 
     */
    public void setCodigo(int value) {
        this.codigo = value;
    }

    /**
     * Gets the value of the condicionesIVA property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the condicionesIVA property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCondicionesIVA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IvaActorDto }
     * 
     * 
     */
    public List<IvaActorDto> getCondicionesIVA() {
        if (condicionesIVA == null) {
            condicionesIVA = new ArrayList<IvaActorDto>();
        }
        return this.condicionesIVA;
    }

    /**
     * Gets the value of the contactos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactoActorDto }
     * 
     * 
     */
    public List<ContactoActorDto> getContactos() {
        if (contactos == null) {
            contactos = new ArrayList<ContactoActorDto>();
        }
        return this.contactos;
    }

    /**
     * Gets the value of the convenioMultilateral property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isConvenioMultilateral() {
        return convenioMultilateral;
    }

    /**
     * Sets the value of the convenioMultilateral property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setConvenioMultilateral(Boolean value) {
        this.convenioMultilateral = value;
    }

    /**
     * Gets the value of the copiaNumeroDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCopiaNumeroDocumento() {
        return copiaNumeroDocumento;
    }

    /**
     * Sets the value of the copiaNumeroDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCopiaNumeroDocumento(Integer value) {
        this.copiaNumeroDocumento = value;
    }

    /**
     * Gets the value of the cuit property.
     * 
     * @return
     *     possible object is
     *     {@link Cuit }
     *     
     */
    public Cuit getCuit() {
        return cuit;
    }

    /**
     * Sets the value of the cuit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Cuit }
     *     
     */
    public void setCuit(Cuit value) {
        this.cuit = value;
    }

    /**
     * Gets the value of the direccionesLocales property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the direccionesLocales property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDireccionesLocales().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DireccionActorDto }
     * 
     * 
     */
    public List<DireccionActorDto> getDireccionesLocales() {
        if (direccionesLocales == null) {
            direccionesLocales = new ArrayList<DireccionActorDto>();
        }
        return this.direccionesLocales;
    }

    /**
     * Gets the value of the direccionesNoLocales property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the direccionesNoLocales property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDireccionesNoLocales().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DireccionActorDto }
     * 
     * 
     */
    public List<DireccionActorDto> getDireccionesNoLocales() {
        if (direccionesNoLocales == null) {
            direccionesNoLocales = new ArrayList<DireccionActorDto>();
        }
        return this.direccionesNoLocales;
    }

    /**
     * Gets the value of the documento property.
     * 
     * @return
     *     possible object is
     *     {@link DocumentoIdentidad }
     *     
     */
    public DocumentoIdentidad getDocumento() {
        return documento;
    }

    /**
     * Sets the value of the documento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentoIdentidad }
     *     
     */
    public void setDocumento(DocumentoIdentidad value) {
        this.documento = value;
    }

    /**
     * Gets the value of the donanteOrganos property.
     * 
     * @return
     *     possible object is
     *     {@link SiNoInformado }
     *     
     */
    public SiNoInformado getDonanteOrganos() {
        return donanteOrganos;
    }

    /**
     * Sets the value of the donanteOrganos property.
     * 
     * @param value
     *     allowed object is
     *     {@link SiNoInformado }
     *     
     */
    public void setDonanteOrganos(SiNoInformado value) {
        this.donanteOrganos = value;
    }

    /**
     * Gets the value of the duracion property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getDuracion() {
        return duracion;
    }

    /**
     * Sets the value of the duracion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setDuracion(Short value) {
        this.duracion = value;
    }

    /**
     * Gets the value of the emailPrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailPrincipal() {
        return emailPrincipal;
    }

    /**
     * Sets the value of the emailPrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailPrincipal(String value) {
        this.emailPrincipal = value;
    }

    /**
     * Gets the value of the esUTE property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEsUTE() {
        return esUTE;
    }

    /**
     * Sets the value of the esUTE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEsUTE(Boolean value) {
        this.esUTE = value;
    }

    /**
     * Gets the value of the estadoCivil property.
     * 
     * @return
     *     possible object is
     *     {@link EstadoCivil }
     *     
     */
    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    /**
     * Sets the value of the estadoCivil property.
     * 
     * @param value
     *     allowed object is
     *     {@link EstadoCivil }
     *     
     */
    public void setEstadoCivil(EstadoCivil value) {
        this.estadoCivil = value;
    }

    /**
     * Gets the value of the factorSanguineo property.
     * 
     * @return
     *     possible object is
     *     {@link FactorSanguineo }
     *     
     */
    public FactorSanguineo getFactorSanguineo() {
        return factorSanguineo;
    }

    /**
     * Sets the value of the factorSanguineo property.
     * 
     * @param value
     *     allowed object is
     *     {@link FactorSanguineo }
     *     
     */
    public void setFactorSanguineo(FactorSanguineo value) {
        this.factorSanguineo = value;
    }

    /**
     * Gets the value of the fechaActualizacionAvatar property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaActualizacionAvatar() {
        return fechaActualizacionAvatar;
    }

    /**
     * Sets the value of the fechaActualizacionAvatar property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaActualizacionAvatar(XMLGregorianCalendar value) {
        this.fechaActualizacionAvatar = value;
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
     * Gets the value of the fechaBaja property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaBaja() {
        return fechaBaja;
    }

    /**
     * Sets the value of the fechaBaja property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaBaja(XMLGregorianCalendar value) {
        this.fechaBaja = value;
    }

    /**
     * Gets the value of the fechaInicio property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Sets the value of the fechaInicio property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaInicio(XMLGregorianCalendar value) {
        this.fechaInicio = value;
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
     * Gets the value of the fechaRPC property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaRPC() {
        return fechaRPC;
    }

    /**
     * Sets the value of the fechaRPC property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaRPC(XMLGregorianCalendar value) {
        this.fechaRPC = value;
    }

    /**
     * Gets the value of the fechaUnificacion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaUnificacion() {
        return fechaUnificacion;
    }

    /**
     * Sets the value of the fechaUnificacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaUnificacion(XMLGregorianCalendar value) {
        this.fechaUnificacion = value;
    }

    /**
     * Gets the value of the firma property.
     * 
     * @return
     *     possible object is
     *     {@link TempResourceRef }
     *     
     */
    public TempResourceRef getFirma() {
        return firma;
    }

    /**
     * Sets the value of the firma property.
     * 
     * @param value
     *     allowed object is
     *     {@link TempResourceRef }
     *     
     */
    public void setFirma(TempResourceRef value) {
        this.firma = value;
    }

    /**
     * Gets the value of the folioRPC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioRPC() {
        return folioRPC;
    }

    /**
     * Sets the value of the folioRPC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioRPC(String value) {
        this.folioRPC = value;
    }

    /**
     * Gets the value of the grupoSanguineo property.
     * 
     * @return
     *     possible object is
     *     {@link GrupoSanguineo }
     *     
     */
    public GrupoSanguineo getGrupoSanguineo() {
        return grupoSanguineo;
    }

    /**
     * Sets the value of the grupoSanguineo property.
     * 
     * @param value
     *     allowed object is
     *     {@link GrupoSanguineo }
     *     
     */
    public void setGrupoSanguineo(GrupoSanguineo value) {
        this.grupoSanguineo = value;
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
     * Gets the value of the idDocumentoSidom property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdDocumentoSidom() {
        return idDocumentoSidom;
    }

    /**
     * Sets the value of the idDocumentoSidom property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdDocumentoSidom(Integer value) {
        this.idDocumentoSidom = value;
    }

    /**
     * Gets the value of the incidencias property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the incidencias property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIncidencias().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IncidenciaActorDto }
     * 
     * 
     */
    public List<IncidenciaActorDto> getIncidencias() {
        if (incidencias == null) {
            incidencias = new ArrayList<IncidenciaActorDto>();
        }
        return this.incidencias;
    }

    /**
     * Gets the value of the integrantesOrganismo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the integrantesOrganismo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIntegrantesOrganismo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IntegraOrganismoDto }
     * 
     * 
     */
    public List<IntegraOrganismoDto> getIntegrantesOrganismo() {
        if (integrantesOrganismo == null) {
            integrantesOrganismo = new ArrayList<IntegraOrganismoDto>();
        }
        return this.integrantesOrganismo;
    }

    /**
     * Gets the value of the integrantesPJ property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the integrantesPJ property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIntegrantesPJ().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IntegraPersonaJuridicaDto }
     * 
     * 
     */
    public List<IntegraPersonaJuridicaDto> getIntegrantesPJ() {
        if (integrantesPJ == null) {
            integrantesPJ = new ArrayList<IntegraPersonaJuridicaDto>();
        }
        return this.integrantesPJ;
    }

    /**
     * Gets the value of the integrantesUTE property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the integrantesUTE property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIntegrantesUTE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IntegraUTEDto }
     * 
     * 
     */
    public List<IntegraUTEDto> getIntegrantesUTE() {
        if (integrantesUTE == null) {
            integrantesUTE = new ArrayList<IntegraUTEDto>();
        }
        return this.integrantesUTE;
    }

    /**
     * Gets the value of the libroRPC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLibroRPC() {
        return libroRPC;
    }

    /**
     * Sets the value of the libroRPC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLibroRPC(String value) {
        this.libroRPC = value;
    }

    /**
     * Gets the value of the lugarTrabajo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLugarTrabajo() {
        return lugarTrabajo;
    }

    /**
     * Sets the value of the lugarTrabajo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLugarTrabajo(String value) {
        this.lugarTrabajo = value;
    }

    /**
     * Gets the value of the movilPrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMovilPrincipal() {
        return movilPrincipal;
    }

    /**
     * Sets the value of the movilPrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMovilPrincipal(String value) {
        this.movilPrincipal = value;
    }

    /**
     * Gets the value of the nacionalidad property.
     * 
     * @return
     *     possible object is
     *     {@link PaisDto }
     *     
     */
    public PaisDto getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Sets the value of the nacionalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaisDto }
     *     
     */
    public void setNacionalidad(PaisDto value) {
        this.nacionalidad = value;
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
     * Gets the value of the nombreFantasia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreFantasia() {
        return nombreFantasia;
    }

    /**
     * Sets the value of the nombreFantasia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreFantasia(String value) {
        this.nombreFantasia = value;
    }

    /**
     * Gets the value of the nombreOrganismo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreOrganismo() {
        return nombreOrganismo;
    }

    /**
     * Sets the value of the nombreOrganismo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreOrganismo(String value) {
        this.nombreOrganismo = value;
    }

    /**
     * Gets the value of the numeroIB property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroIB() {
        return numeroIB;
    }

    /**
     * Sets the value of the numeroIB property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroIB(String value) {
        this.numeroIB = value;
    }

    /**
     * Gets the value of the numeroRPC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroRPC() {
        return numeroRPC;
    }

    /**
     * Sets the value of the numeroRPC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroRPC(String value) {
        this.numeroRPC = value;
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
     * Gets the value of the ocupacion property.
     * 
     * @return
     *     possible object is
     *     {@link OcupacionDto }
     *     
     */
    public OcupacionDto getOcupacion() {
        return ocupacion;
    }

    /**
     * Sets the value of the ocupacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link OcupacionDto }
     *     
     */
    public void setOcupacion(OcupacionDto value) {
        this.ocupacion = value;
    }

    /**
     * Gets the value of the origen property.
     * 
     * @return
     *     possible object is
     *     {@link OrigenActor }
     *     
     */
    public OrigenActor getOrigen() {
        return origen;
    }

    /**
     * Sets the value of the origen property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrigenActor }
     *     
     */
    public void setOrigen(OrigenActor value) {
        this.origen = value;
    }

    /**
     * Gets the value of the pais property.
     * 
     * @return
     *     possible object is
     *     {@link PaisDto }
     *     
     */
    public PaisDto getPais() {
        return pais;
    }

    /**
     * Sets the value of the pais property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaisDto }
     *     
     */
    public void setPais(PaisDto value) {
        this.pais = value;
    }

    /**
     * Gets the value of the paisDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link PaisDto }
     *     
     */
    public PaisDto getPaisDocumento() {
        return paisDocumento;
    }

    /**
     * Sets the value of the paisDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaisDto }
     *     
     */
    public void setPaisDocumento(PaisDto value) {
        this.paisDocumento = value;
    }

    /**
     * Gets the value of the provinciaDocumento property.
     * 
     * @return
     *     possible object is
     *     {@link ProvinciaEstadoDto }
     *     
     */
    public ProvinciaEstadoDto getProvinciaDocumento() {
        return provinciaDocumento;
    }

    /**
     * Sets the value of the provinciaDocumento property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvinciaEstadoDto }
     *     
     */
    public void setProvinciaDocumento(ProvinciaEstadoDto value) {
        this.provinciaDocumento = value;
    }

    /**
     * Gets the value of the razonSocial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Sets the value of the razonSocial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRazonSocial(String value) {
        this.razonSocial = value;
    }

    /**
     * Gets the value of the sexo property.
     * 
     * @return
     *     possible object is
     *     {@link Sexo }
     *     
     */
    public Sexo getSexo() {
        return sexo;
    }

    /**
     * Sets the value of the sexo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sexo }
     *     
     */
    public void setSexo(Sexo value) {
        this.sexo = value;
    }

    /**
     * Gets the value of the sigla property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSigla() {
        return sigla;
    }

    /**
     * Sets the value of the sigla property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSigla(String value) {
        this.sigla = value;
    }

    /**
     * Gets the value of the telefonoPrincipal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefonoPrincipal() {
        return telefonoPrincipal;
    }

    /**
     * Sets the value of the telefonoPrincipal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefonoPrincipal(String value) {
        this.telefonoPrincipal = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link TipoActor }
     *     
     */
    public TipoActor getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoActor }
     *     
     */
    public void setTipo(TipoActor value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the tipoPersonaJuridica property.
     * 
     * @return
     *     possible object is
     *     {@link TipoPersonaJuridicaDto }
     *     
     */
    public TipoPersonaJuridicaDto getTipoPersonaJuridica() {
        return tipoPersonaJuridica;
    }

    /**
     * Sets the value of the tipoPersonaJuridica property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoPersonaJuridicaDto }
     *     
     */
    public void setTipoPersonaJuridica(TipoPersonaJuridicaDto value) {
        this.tipoPersonaJuridica = value;
    }

    /**
     * Gets the value of the tipoValidacion property.
     * 
     * @return
     *     possible object is
     *     {@link TipoValidacionActor }
     *     
     */
    public TipoValidacionActor getTipoValidacion() {
        return tipoValidacion;
    }

    /**
     * Sets the value of the tipoValidacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoValidacionActor }
     *     
     */
    public void setTipoValidacion(TipoValidacionActor value) {
        this.tipoValidacion = value;
    }

    /**
     * Gets the value of the tomoRPC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTomoRPC() {
        return tomoRPC;
    }

    /**
     * Sets the value of the tomoRPC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTomoRPC(String value) {
        this.tomoRPC = value;
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
