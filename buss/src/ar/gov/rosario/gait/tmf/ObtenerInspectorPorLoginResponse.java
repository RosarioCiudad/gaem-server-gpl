
package ar.gov.rosario.gait.tmf;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="responseInfo" type="{http://www.rosario.gov.ar/Tribunal/schema}ResponseInfo"/>
 *         &lt;element name="login" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="reparticiones" type="{http://www.rosario.gov.ar/Tribunal/schema}InspectorReparticion" maxOccurs="2"/>
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
    "responseInfo",
    "login",
    "reparticiones"
})
@XmlRootElement(name = "obtenerInspectorPorLoginResponse")
public class ObtenerInspectorPorLoginResponse {

    @XmlElement(required = true)
    protected ResponseInfo responseInfo;
    @XmlElement(required = true)
    protected String login;
    @XmlElement(required = true)
    protected List<InspectorReparticion> reparticiones;

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

    /**
     * Gets the value of the login property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the value of the login property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogin(String value) {
        this.login = value;
    }

    /**
     * Gets the value of the reparticiones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reparticiones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReparticiones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InspectorReparticion }
     * 
     * 
     */
    public List<InspectorReparticion> getReparticiones() {
        if (reparticiones == null) {
            reparticiones = new ArrayList<InspectorReparticion>();
        }
        return this.reparticiones;
    }

}
