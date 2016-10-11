
package ar.gov.rosario.gait.simgei.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for codigoActividadDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="codigoActividadDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actividadPadre" type="{http://ws.admusu.santafeciudad.gov.ar/}codigoActividadDto" minOccurs="0"/>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="descripcion" type="{http://ws.admusu.santafeciudad.gov.ar/}descripcion" minOccurs="0"/>
 *         &lt;element name="fechaAlta" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="fechaBaja" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="manipulaAlimento" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="objetoCuantificable" type="{http://ws.admusu.santafeciudad.gov.ar/}objetoCuantificableDrei" minOccurs="0"/>
 *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rubro" type="{http://ws.admusu.santafeciudad.gov.ar/}rubroDreiDto" minOccurs="0"/>
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
@XmlType(name = "codigoActividadDto", propOrder = {
    "actividadPadre",
    "codigo",
    "descripcion",
    "fechaAlta",
    "fechaBaja",
    "id",
    "manipulaAlimento",
    "objetoCuantificable",
    "observaciones",
    "rubro",
    "version"
})
public class CodigoActividadDto {

    protected CodigoActividadDto actividadPadre;
    protected int codigo;
    protected Descripcion descripcion;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaAlta;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaBaja;
    protected Long id;
    protected boolean manipulaAlimento;
    protected ObjetoCuantificableDrei objetoCuantificable;
    protected String observaciones;
    protected RubroDreiDto rubro;
    protected int version;

    /**
     * Gets the value of the actividadPadre property.
     * 
     * @return
     *     possible object is
     *     {@link CodigoActividadDto }
     *     
     */
    public CodigoActividadDto getActividadPadre() {
        return actividadPadre;
    }

    /**
     * Sets the value of the actividadPadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodigoActividadDto }
     *     
     */
    public void setActividadPadre(CodigoActividadDto value) {
        this.actividadPadre = value;
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
     * Gets the value of the manipulaAlimento property.
     * 
     */
    public boolean isManipulaAlimento() {
        return manipulaAlimento;
    }

    /**
     * Sets the value of the manipulaAlimento property.
     * 
     */
    public void setManipulaAlimento(boolean value) {
        this.manipulaAlimento = value;
    }

    /**
     * Gets the value of the objetoCuantificable property.
     * 
     * @return
     *     possible object is
     *     {@link ObjetoCuantificableDrei }
     *     
     */
    public ObjetoCuantificableDrei getObjetoCuantificable() {
        return objetoCuantificable;
    }

    /**
     * Sets the value of the objetoCuantificable property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjetoCuantificableDrei }
     *     
     */
    public void setObjetoCuantificable(ObjetoCuantificableDrei value) {
        this.objetoCuantificable = value;
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
     * Gets the value of the rubro property.
     * 
     * @return
     *     possible object is
     *     {@link RubroDreiDto }
     *     
     */
    public RubroDreiDto getRubro() {
        return rubro;
    }

    /**
     * Sets the value of the rubro property.
     * 
     * @param value
     *     allowed object is
     *     {@link RubroDreiDto }
     *     
     */
    public void setRubro(RubroDreiDto value) {
        this.rubro = value;
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
