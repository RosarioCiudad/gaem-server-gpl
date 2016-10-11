
package ar.gov.rosario.gait.tmf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Vehiculo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Vehiculo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dominio" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoVehiculo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nroMotor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="marca" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tonalidad" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="remitido" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="dominioCopia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dominioNulo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="idMarca" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="licencia" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="chasis" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="linea" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="interno" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Vehiculo", propOrder = {
    "dominio",
    "tipoVehiculo",
    "nroMotor",
    "marca",
    "tonalidad",
    "remitido",
    "dominioCopia",
    "dominioNulo",
    "idMarca",
    "licencia",
    "chasis",
    "linea",
    "interno"
})
public class Vehiculo {

    @XmlElement(required = true, nillable = true)
    protected String dominio;
    protected int tipoVehiculo;
    @XmlElement(required = true, nillable = true)
    protected String nroMotor;
    @XmlElement(required = true, nillable = true)
    protected String marca;
    @XmlElement(required = true, nillable = true)
    protected String tonalidad;
    @XmlElement(required = true, type = Boolean.class, nillable = true)
    protected Boolean remitido;
    @XmlElement(required = true, nillable = true)
    protected String dominioCopia;
    protected boolean dominioNulo;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer idMarca;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer licencia;
    @XmlElement(required = true, nillable = true)
    protected String chasis;
    @XmlElement(required = true, nillable = true)
    protected String linea;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer interno;

    /**
     * Gets the value of the dominio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDominio() {
        return dominio;
    }

    /**
     * Sets the value of the dominio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDominio(String value) {
        this.dominio = value;
    }

    /**
     * Gets the value of the tipoVehiculo property.
     * 
     */
    public int getTipoVehiculo() {
        return tipoVehiculo;
    }

    /**
     * Sets the value of the tipoVehiculo property.
     * 
     */
    public void setTipoVehiculo(int value) {
        this.tipoVehiculo = value;
    }

    /**
     * Gets the value of the nroMotor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNroMotor() {
        return nroMotor;
    }

    /**
     * Sets the value of the nroMotor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNroMotor(String value) {
        this.nroMotor = value;
    }

    /**
     * Gets the value of the marca property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Sets the value of the marca property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarca(String value) {
        this.marca = value;
    }

    /**
     * Gets the value of the tonalidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTonalidad() {
        return tonalidad;
    }

    /**
     * Sets the value of the tonalidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTonalidad(String value) {
        this.tonalidad = value;
    }

    /**
     * Gets the value of the remitido property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRemitido() {
        return remitido;
    }

    /**
     * Sets the value of the remitido property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRemitido(Boolean value) {
        this.remitido = value;
    }

    /**
     * Gets the value of the dominioCopia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDominioCopia() {
        return dominioCopia;
    }

    /**
     * Sets the value of the dominioCopia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDominioCopia(String value) {
        this.dominioCopia = value;
    }

    /**
     * Gets the value of the dominioNulo property.
     * 
     */
    public boolean isDominioNulo() {
        return dominioNulo;
    }

    /**
     * Sets the value of the dominioNulo property.
     * 
     */
    public void setDominioNulo(boolean value) {
        this.dominioNulo = value;
    }

    /**
     * Gets the value of the idMarca property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdMarca() {
        return idMarca;
    }

    /**
     * Sets the value of the idMarca property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdMarca(Integer value) {
        this.idMarca = value;
    }

    /**
     * Gets the value of the licencia property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLicencia() {
        return licencia;
    }

    /**
     * Sets the value of the licencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLicencia(Integer value) {
        this.licencia = value;
    }

    /**
     * Gets the value of the chasis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChasis() {
        return chasis;
    }

    /**
     * Sets the value of the chasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChasis(String value) {
        this.chasis = value;
    }

    /**
     * Gets the value of the linea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinea() {
        return linea;
    }

    /**
     * Sets the value of the linea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinea(String value) {
        this.linea = value;
    }

    /**
     * Gets the value of the interno property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInterno() {
        return interno;
    }

    /**
     * Sets the value of the interno property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInterno(Integer value) {
        this.interno = value;
    }

}
