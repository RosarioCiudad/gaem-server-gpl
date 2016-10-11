
package ar.gov.rosario.gait.apsv;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestInfo" type="{http://www.rosario.gov.ar/Apsv/schema}RequestInfo"/>
 *         &lt;element name="idPersona" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="claseLicencia" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "requestInfo",
    "idPersona",
    "claseLicencia"
})
@XmlRootElement(name = "obtenerLicenciaRequest")
public class ObtenerLicenciaRequest {

    @XmlElement(required = true)
    protected RequestInfo requestInfo;
    protected int idPersona;
    @XmlElement(required = true, nillable = true)
    protected String claseLicencia;

    /**
     * Gets the value of the requestInfo property.
     * 
     * @return
     *     possible object is
     *     {@link RequestInfo }
     *     
     */
    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    /**
     * Sets the value of the requestInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestInfo }
     *     
     */
    public void setRequestInfo(RequestInfo value) {
        this.requestInfo = value;
    }

    /**
     * Gets the value of the idPersona property.
     * 
     */
    public int getIdPersona() {
        return idPersona;
    }

    /**
     * Sets the value of the idPersona property.
     * 
     */
    public void setIdPersona(int value) {
        this.idPersona = value;
    }

    /**
     * Gets the value of the claseLicencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaseLicencia() {
        return claseLicencia;
    }

    /**
     * Sets the value of the claseLicencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaseLicencia(String value) {
        this.claseLicencia = value;
    }

}
