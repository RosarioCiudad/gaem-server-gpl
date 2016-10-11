
package ar.gov.rosario.gait.tmf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Posicion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Posicion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="latitud" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="longitud" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="precision" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="fechaHoraMedicion" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="origen" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Posicion", propOrder = {
    "latitud",
    "longitud",
    "precision",
    "fechaHoraMedicion",
    "origen"
})
public class Posicion {

    protected double latitud;
    protected double longitud;
    protected double precision;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaHoraMedicion;
    protected int origen;

    /**
     * Gets the value of the latitud property.
     * 
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * Sets the value of the latitud property.
     * 
     */
    public void setLatitud(double value) {
        this.latitud = value;
    }

    /**
     * Gets the value of the longitud property.
     * 
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * Sets the value of the longitud property.
     * 
     */
    public void setLongitud(double value) {
        this.longitud = value;
    }

    /**
     * Gets the value of the precision property.
     * 
     */
    public double getPrecision() {
        return precision;
    }

    /**
     * Sets the value of the precision property.
     * 
     */
    public void setPrecision(double value) {
        this.precision = value;
    }

    /**
     * Gets the value of the fechaHoraMedicion property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaHoraMedicion() {
        return fechaHoraMedicion;
    }

    /**
     * Sets the value of the fechaHoraMedicion property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaHoraMedicion(XMLGregorianCalendar value) {
        this.fechaHoraMedicion = value;
    }

    /**
     * Gets the value of the origen property.
     * 
     */
    public int getOrigen() {
        return origen;
    }

    /**
     * Sets the value of the origen property.
     * 
     */
    public void setOrigen(int value) {
        this.origen = value;
    }

}
