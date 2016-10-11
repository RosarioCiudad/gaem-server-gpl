
package ar.gov.rosario.gait.tmf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Solicitud complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Solicitud">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fecha" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="estadoFinal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idCentroEmision" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="centroEmision" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idSolicitud" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Solicitud", propOrder = {
    "fecha",
    "tipo",
    "estadoFinal",
    "idCentroEmision",
    "centroEmision",
    "idSolicitud"
})
public class Solicitud {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fecha;
    @XmlElement(required = true)
    protected String tipo;
    @XmlElement(required = true)
    protected String estadoFinal;
    protected int idCentroEmision;
    @XmlElement(required = true)
    protected String centroEmision;
    protected int idSolicitud;

    /**
     * Gets the value of the fecha property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFecha() {
        return fecha;
    }

    /**
     * Sets the value of the fecha property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFecha(XMLGregorianCalendar value) {
        this.fecha = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipo(String value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the estadoFinal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstadoFinal() {
        return estadoFinal;
    }

    /**
     * Sets the value of the estadoFinal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstadoFinal(String value) {
        this.estadoFinal = value;
    }

    /**
     * Gets the value of the idCentroEmision property.
     * 
     */
    public int getIdCentroEmision() {
        return idCentroEmision;
    }

    /**
     * Sets the value of the idCentroEmision property.
     * 
     */
    public void setIdCentroEmision(int value) {
        this.idCentroEmision = value;
    }

    /**
     * Gets the value of the centroEmision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCentroEmision() {
        return centroEmision;
    }

    /**
     * Sets the value of the centroEmision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCentroEmision(String value) {
        this.centroEmision = value;
    }

    /**
     * Gets the value of the idSolicitud property.
     * 
     */
    public int getIdSolicitud() {
        return idSolicitud;
    }

    /**
     * Sets the value of the idSolicitud property.
     * 
     */
    public void setIdSolicitud(int value) {
        this.idSolicitud = value;
    }

}
