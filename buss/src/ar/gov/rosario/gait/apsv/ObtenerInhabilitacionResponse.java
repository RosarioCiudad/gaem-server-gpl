
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
 *         &lt;element name="inhabilitado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="responseInfo" type="{http://www.rosario.gov.ar/Apsv/schema}ResponseInfo"/>
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
    "inhabilitado",
    "responseInfo"
})
@XmlRootElement(name = "obtenerInhabilitacionResponse")
public class ObtenerInhabilitacionResponse {

    @XmlElement(required = true)
    protected String inhabilitado;
    @XmlElement(required = true)
    protected ResponseInfo responseInfo;

    /**
     * Gets the value of the inhabilitado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInhabilitado() {
        return inhabilitado;
    }

    /**
     * Sets the value of the inhabilitado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInhabilitado(String value) {
        this.inhabilitado = value;
    }

    /**
     * Gets the value of the responseInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseInfo }
     *     
     */
    public ResponseInfo getResponseInfo() {
        return responseInfo;
    }

    /**
     * Sets the value of the responseInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseInfo }
     *     
     */
    public void setResponseInfo(ResponseInfo value) {
        this.responseInfo = value;
    }

}
