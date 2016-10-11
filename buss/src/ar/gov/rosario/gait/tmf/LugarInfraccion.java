
package ar.gov.rosario.gait.tmf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LugarInfraccion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LugarInfraccion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idSentidoCirculacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="direccion" type="{http://www.rosario.gov.ar/Tribunal/schema}Direccion"/>
 *         &lt;element name="posicion" type="{http://www.rosario.gov.ar/Tribunal/schema}Posicion"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LugarInfraccion", propOrder = {
    "idSentidoCirculacion",
    "descripcion",
    "direccion",
    "posicion"
})
public class LugarInfraccion {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer idSentidoCirculacion;
    @XmlElement(required = true)
    protected String descripcion;
    @XmlElement(required = true)
    protected Direccion direccion;
    @XmlElement(required = true)
    protected Posicion posicion;

    /**
     * Gets the value of the idSentidoCirculacion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdSentidoCirculacion() {
        return idSentidoCirculacion;
    }

    /**
     * Sets the value of the idSentidoCirculacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdSentidoCirculacion(Integer value) {
        this.idSentidoCirculacion = value;
    }

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
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
     * Gets the value of the posicion property.
     * 
     * @return
     *     possible object is
     *     {@link Posicion }
     *     
     */
    public Posicion getPosicion() {
        return posicion;
    }

    /**
     * Sets the value of the posicion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Posicion }
     *     
     */
    public void setPosicion(Posicion value) {
        this.posicion = value;
    }

}
