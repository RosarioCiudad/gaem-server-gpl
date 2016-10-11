
package ar.gov.rosario.gait.tmf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Infractor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Infractor">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codDoc" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nroDoc" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nombres" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sexo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="apellido" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="direccion" type="{http://www.rosario.gov.ar/Tribunal/schema}Direccion"/>
 *         &lt;element name="licenciaClase" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Infractor", propOrder = {
    "codDoc",
    "nroDoc",
    "nombres",
    "sexo",
    "apellido",
    "direccion",
    "licenciaClase"
})
public class Infractor {

    protected int codDoc;
    protected int nroDoc;
    @XmlElement(required = true)
    protected String nombres;
    @XmlElement(required = true)
    protected String sexo;
    @XmlElement(required = true)
    protected String apellido;
    @XmlElement(required = true, nillable = true)
    protected Direccion direccion;
    @XmlElement(required = true)
    protected String licenciaClase;

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
     * Gets the value of the licenciaClase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLicenciaClase() {
        return licenciaClase;
    }

    /**
     * Sets the value of the licenciaClase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLicenciaClase(String value) {
        this.licenciaClase = value;
    }

}
