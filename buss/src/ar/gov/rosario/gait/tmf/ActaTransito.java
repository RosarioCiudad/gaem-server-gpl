
package ar.gov.rosario.gait.tmf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ActaTransito complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ActaTransito">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoActa" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nroActa" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="serie" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaActa" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="horaActa" type="{http://www.w3.org/2001/XMLSchema}time"/>
 *         &lt;element name="vehiculo" type="{http://www.rosario.gov.ar/Tribunal/schema}Vehiculo"/>
 *         &lt;element name="infractor" type="{http://www.rosario.gov.ar/Tribunal/schema}Infractor"/>
 *         &lt;element name="lugarInfraccion" type="{http://www.rosario.gov.ar/Tribunal/schema}LugarInfraccion"/>
 *         &lt;element name="inspector" type="{http://www.rosario.gov.ar/Tribunal/schema}Inspector"/>
 *         &lt;element name="alcoholemia" type="{http://www.rosario.gov.ar/Tribunal/schema}Alcoholemia"/>
 *         &lt;element name="nroPlanilla" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="actaDigital" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="observacion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="licenciaRetenida" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="imagenes" type="{http://www.rosario.gov.ar/Tribunal/schema}Imagen" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="infracciones" type="{http://www.rosario.gov.ar/Tribunal/schema}Infraccion" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="participes" type="{http://www.rosario.gov.ar/Tribunal/schema}ActaParticipe" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="codMotivoAnulacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idResultadoFirma" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="serieCaptor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaHoraCierre" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="idFormulario" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nroActaSecuestro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rehusaDescender" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActaTransito", propOrder = {
    "tipoActa",
    "nroActa",
    "serie",
    "fechaActa",
    "horaActa",
    "vehiculo",
    "infractor",
    "lugarInfraccion",
    "inspector",
    "alcoholemia",
    "nroPlanilla",
    "actaDigital",
    "observacion",
    "licenciaRetenida",
    "imagenes",
    "infracciones",
    "participes",
    "codMotivoAnulacion",
    "idResultadoFirma",
    "serieCaptor",
    "fechaHoraCierre",
    "idFormulario",
    "nroActaSecuestro",
    "rehusaDescender",
    "estado"
})
public class ActaTransito {

    protected int tipoActa;
    protected int nroActa;
    @XmlElement(required = true)
    protected String serie;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaActa;
    @XmlElement(required = true)
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar horaActa;
    @XmlElement(required = true, nillable = true)
    protected Vehiculo vehiculo;
    @XmlElement(required = true, nillable = true)
    protected Infractor infractor;
    @XmlElement(required = true, nillable = true)
    protected LugarInfraccion lugarInfraccion;
    @XmlElement(required = true)
    protected Inspector inspector;
    @XmlElement(required = true, nillable = true)
    protected Alcoholemia alcoholemia;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer nroPlanilla;
    @XmlElement(required = true, nillable = true)
    protected byte[] actaDigital;
    @XmlElement(required = true, nillable = true)
    protected String observacion;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean licenciaRetenida;
    protected List<Imagen> imagenes;
    protected List<Infraccion> infracciones;
    protected List<ActaParticipe> participes;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer codMotivoAnulacion;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer idResultadoFirma;
    @XmlElement(required = true, nillable = true)
    protected String serieCaptor;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaHoraCierre;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer idFormulario;
    @XmlElement(required = true, nillable = true)
    protected String nroActaSecuestro;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean rehusaDescender;
    @XmlElement(required = true)
    protected String estado;

    /**
     * Gets the value of the tipoActa property.
     * 
     */
    public int getTipoActa() {
        return tipoActa;
    }

    /**
     * Sets the value of the tipoActa property.
     * 
     */
    public void setTipoActa(int value) {
        this.tipoActa = value;
    }

    /**
     * Gets the value of the nroActa property.
     * 
     */
    public int getNroActa() {
        return nroActa;
    }

    /**
     * Sets the value of the nroActa property.
     * 
     */
    public void setNroActa(int value) {
        this.nroActa = value;
    }

    /**
     * Gets the value of the serie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerie() {
        return serie;
    }

    /**
     * Sets the value of the serie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerie(String value) {
        this.serie = value;
    }

    /**
     * Gets the value of the fechaActa property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaActa() {
        return fechaActa;
    }

    /**
     * Sets the value of the fechaActa property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaActa(XMLGregorianCalendar value) {
        this.fechaActa = value;
    }

    /**
     * Gets the value of the horaActa property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getHoraActa() {
        return horaActa;
    }

    /**
     * Sets the value of the horaActa property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setHoraActa(XMLGregorianCalendar value) {
        this.horaActa = value;
    }

    /**
     * Gets the value of the vehiculo property.
     * 
     * @return
     *     possible object is
     *     {@link Vehiculo }
     *     
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * Sets the value of the vehiculo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Vehiculo }
     *     
     */
    public void setVehiculo(Vehiculo value) {
        this.vehiculo = value;
    }

    /**
     * Gets the value of the infractor property.
     * 
     * @return
     *     possible object is
     *     {@link Infractor }
     *     
     */
    public Infractor getInfractor() {
        return infractor;
    }

    /**
     * Sets the value of the infractor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Infractor }
     *     
     */
    public void setInfractor(Infractor value) {
        this.infractor = value;
    }

    /**
     * Gets the value of the lugarInfraccion property.
     * 
     * @return
     *     possible object is
     *     {@link LugarInfraccion }
     *     
     */
    public LugarInfraccion getLugarInfraccion() {
        return lugarInfraccion;
    }

    /**
     * Sets the value of the lugarInfraccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link LugarInfraccion }
     *     
     */
    public void setLugarInfraccion(LugarInfraccion value) {
        this.lugarInfraccion = value;
    }

    /**
     * Gets the value of the inspector property.
     * 
     * @return
     *     possible object is
     *     {@link Inspector }
     *     
     */
    public Inspector getInspector() {
        return inspector;
    }

    /**
     * Sets the value of the inspector property.
     * 
     * @param value
     *     allowed object is
     *     {@link Inspector }
     *     
     */
    public void setInspector(Inspector value) {
        this.inspector = value;
    }

    /**
     * Gets the value of the alcoholemia property.
     * 
     * @return
     *     possible object is
     *     {@link Alcoholemia }
     *     
     */
    public Alcoholemia getAlcoholemia() {
        return alcoholemia;
    }

    /**
     * Sets the value of the alcoholemia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Alcoholemia }
     *     
     */
    public void setAlcoholemia(Alcoholemia value) {
        this.alcoholemia = value;
    }

    /**
     * Gets the value of the nroPlanilla property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNroPlanilla() {
        return nroPlanilla;
    }

    /**
     * Sets the value of the nroPlanilla property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNroPlanilla(Integer value) {
        this.nroPlanilla = value;
    }

    /**
     * Gets the value of the actaDigital property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getActaDigital() {
        return actaDigital;
    }

    /**
     * Sets the value of the actaDigital property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setActaDigital(byte[] value) {
        this.actaDigital = value;
    }

    /**
     * Gets the value of the observacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Sets the value of the observacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservacion(String value) {
        this.observacion = value;
    }

    /**
     * Gets the value of the licenciaRetenida property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLicenciaRetenida() {
        return licenciaRetenida;
    }

    /**
     * Sets the value of the licenciaRetenida property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLicenciaRetenida(Boolean value) {
        this.licenciaRetenida = value;
    }

    /**
     * Gets the value of the imagenes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the imagenes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImagenes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Imagen }
     * 
     * 
     */
    public List<Imagen> getImagenes() {
        if (imagenes == null) {
            imagenes = new ArrayList<Imagen>();
        }
        return this.imagenes;
    }

    /**
     * Gets the value of the infracciones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infracciones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfracciones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Infraccion }
     * 
     * 
     */
    public List<Infraccion> getInfracciones() {
        if (infracciones == null) {
            infracciones = new ArrayList<Infraccion>();
        }
        return this.infracciones;
    }

    /**
     * Gets the value of the participes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the participes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParticipes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ActaParticipe }
     * 
     * 
     */
    public List<ActaParticipe> getParticipes() {
        if (participes == null) {
            participes = new ArrayList<ActaParticipe>();
        }
        return this.participes;
    }

    /**
     * Gets the value of the codMotivoAnulacion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodMotivoAnulacion() {
        return codMotivoAnulacion;
    }

    /**
     * Sets the value of the codMotivoAnulacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodMotivoAnulacion(Integer value) {
        this.codMotivoAnulacion = value;
    }

    /**
     * Gets the value of the idResultadoFirma property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdResultadoFirma() {
        return idResultadoFirma;
    }

    /**
     * Sets the value of the idResultadoFirma property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdResultadoFirma(Integer value) {
        this.idResultadoFirma = value;
    }

    /**
     * Gets the value of the serieCaptor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerieCaptor() {
        return serieCaptor;
    }

    /**
     * Sets the value of the serieCaptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerieCaptor(String value) {
        this.serieCaptor = value;
    }

    /**
     * Gets the value of the fechaHoraCierre property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    /**
     * Sets the value of the fechaHoraCierre property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaHoraCierre(XMLGregorianCalendar value) {
        this.fechaHoraCierre = value;
    }

    /**
     * Gets the value of the idFormulario property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdFormulario() {
        return idFormulario;
    }

    /**
     * Sets the value of the idFormulario property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdFormulario(Integer value) {
        this.idFormulario = value;
    }

    /**
     * Gets the value of the nroActaSecuestro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroActaSecuestro() {
        return nroActaSecuestro;
    }

    /**
     * Sets the value of the nroActaSecuestro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroActaSecuestro(String value) {
        this.nroActaSecuestro = value;
    }

    /**
     * Gets the value of the rehusaDescender property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRehusaDescender() {
        return rehusaDescender;
    }

    /**
     * Sets the value of the rehusaDescender property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRehusaDescender(Boolean value) {
        this.rehusaDescender = value;
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

}
