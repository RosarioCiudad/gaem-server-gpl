
package ar.gov.rosario.gait.simgei.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for direccionActorDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="direccionActorDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="activo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="calle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="departamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="direccion" type="{http://ws.admusu.santafeciudad.gov.ar/}direccionDto" minOccurs="0"/>
 *         &lt;element name="distrito" type="{http://ws.admusu.santafeciudad.gov.ar/}distritoDto" minOccurs="0"/>
 *         &lt;element name="domicilioOriginal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="edificio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaActivo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="item" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="manzana" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monoblock" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroPortal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="piso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secuencia" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="tipo" type="{http://ws.admusu.santafeciudad.gov.ar/}tipoDomicilio" minOccurs="0"/>
 *         &lt;element name="tipoDireccion" type="{http://ws.admusu.santafeciudad.gov.ar/}tipoDireccionActor" minOccurs="0"/>
 *         &lt;element name="torre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="vivienda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "direccionActorDto", propOrder = {
    "activo",
    "calle",
    "departamento",
    "direccion",
    "distrito",
    "domicilioOriginal",
    "edificio",
    "fechaActivo",
    "id",
    "item",
    "manzana",
    "monoblock",
    "numeroPortal",
    "observaciones",
    "piso",
    "secuencia",
    "tipo",
    "tipoDireccion",
    "torre",
    "version",
    "vivienda"
})
public class DireccionActorDto {

    protected boolean activo;
    protected String calle;
    protected String departamento;
    protected DireccionDto direccion;
    protected DistritoDto distrito;
    protected String domicilioOriginal;
    protected String edificio;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaActivo;
    protected Long id;
    protected short item;
    protected String manzana;
    protected String monoblock;
    protected String numeroPortal;
    protected String observaciones;
    protected String piso;
    protected short secuencia;
    protected TipoDomicilio tipo;
    protected TipoDireccionActor tipoDireccion;
    protected String torre;
    protected int version;
    protected String vivienda;

    /**
     * Gets the value of the activo property.
     * 
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Sets the value of the activo property.
     * 
     */
    public void setActivo(boolean value) {
        this.activo = value;
    }

    /**
     * Gets the value of the calle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Sets the value of the calle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalle(String value) {
        this.calle = value;
    }

    /**
     * Gets the value of the departamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Sets the value of the departamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartamento(String value) {
        this.departamento = value;
    }

    /**
     * Gets the value of the direccion property.
     * 
     * @return
     *     possible object is
     *     {@link DireccionDto }
     *     
     */
    public DireccionDto getDireccion() {
        return direccion;
    }

    /**
     * Sets the value of the direccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link DireccionDto }
     *     
     */
    public void setDireccion(DireccionDto value) {
        this.direccion = value;
    }

    /**
     * Gets the value of the distrito property.
     * 
     * @return
     *     possible object is
     *     {@link DistritoDto }
     *     
     */
    public DistritoDto getDistrito() {
        return distrito;
    }

    /**
     * Sets the value of the distrito property.
     * 
     * @param value
     *     allowed object is
     *     {@link DistritoDto }
     *     
     */
    public void setDistrito(DistritoDto value) {
        this.distrito = value;
    }

    /**
     * Gets the value of the domicilioOriginal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomicilioOriginal() {
        return domicilioOriginal;
    }

    /**
     * Sets the value of the domicilioOriginal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomicilioOriginal(String value) {
        this.domicilioOriginal = value;
    }

    /**
     * Gets the value of the edificio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * Sets the value of the edificio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEdificio(String value) {
        this.edificio = value;
    }

    /**
     * Gets the value of the fechaActivo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaActivo() {
        return fechaActivo;
    }

    /**
     * Sets the value of the fechaActivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaActivo(XMLGregorianCalendar value) {
        this.fechaActivo = value;
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
     * Gets the value of the manzana property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManzana() {
        return manzana;
    }

    /**
     * Sets the value of the manzana property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManzana(String value) {
        this.manzana = value;
    }

    /**
     * Gets the value of the monoblock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonoblock() {
        return monoblock;
    }

    /**
     * Sets the value of the monoblock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonoblock(String value) {
        this.monoblock = value;
    }

    /**
     * Gets the value of the numeroPortal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroPortal() {
        return numeroPortal;
    }

    /**
     * Sets the value of the numeroPortal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroPortal(String value) {
        this.numeroPortal = value;
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
     * Gets the value of the piso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiso() {
        return piso;
    }

    /**
     * Sets the value of the piso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiso(String value) {
        this.piso = value;
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
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDomicilio }
     *     
     */
    public TipoDomicilio getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDomicilio }
     *     
     */
    public void setTipo(TipoDomicilio value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the tipoDireccion property.
     * 
     * @return
     *     possible object is
     *     {@link TipoDireccionActor }
     *     
     */
    public TipoDireccionActor getTipoDireccion() {
        return tipoDireccion;
    }

    /**
     * Sets the value of the tipoDireccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoDireccionActor }
     *     
     */
    public void setTipoDireccion(TipoDireccionActor value) {
        this.tipoDireccion = value;
    }

    /**
     * Gets the value of the torre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTorre() {
        return torre;
    }

    /**
     * Sets the value of the torre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTorre(String value) {
        this.torre = value;
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
     * Gets the value of the vivienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVivienda() {
        return vivienda;
    }

    /**
     * Sets the value of the vivienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVivienda(String value) {
        this.vivienda = value;
    }

}
