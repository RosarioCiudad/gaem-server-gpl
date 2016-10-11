
package ar.gov.rosario.gait.tmf;

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
 *         &lt;element name="requestInfo" type="{http://www.rosario.gov.ar/Tribunal/schema}RequestInfo"/>
 *         &lt;element name="actaTransito" type="{http://www.rosario.gov.ar/Tribunal/schema}ActaTransito"/>
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
    "actaTransito"
})
@XmlRootElement(name = "registrarActaTransitoRequest")
public class RegistrarActaTransitoRequest {

    @XmlElement(required = true)
    protected RequestInfo requestInfo;
    @XmlElement(required = true)
    protected ActaTransito actaTransito;

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
     * Gets the value of the actaTransito property.
     * 
     * @return
     *     possible object is
     *     {@link ActaTransito }
     *     
     */
    public ActaTransito getActaTransito() {
        return actaTransito;
    }

    /**
     * Sets the value of the actaTransito property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActaTransito }
     *     
     */
    public void setActaTransito(ActaTransito value) {
        this.actaTransito = value;
    }

}
