
package ar.gov.rosario.gait.apsv;

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
 *         &lt;element name="noAptitudes" type="{http://www.rosario.gov.ar/Apsv/schema}NoAptitud" maxOccurs="unbounded" minOccurs="0"/>
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
    "noAptitudes",
    "responseInfo"
})
@XmlRootElement(name = "obtenerNoAptitudResponse")
public class ObtenerNoAptitudResponse {

    protected List<NoAptitud> noAptitudes;
    @XmlElement(required = true)
    protected ResponseInfo responseInfo;

    /**
     * Gets the value of the noAptitudes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the noAptitudes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNoAptitudes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NoAptitud }
     * 
     * 
     */
    public List<NoAptitud> getNoAptitudes() {
        if (noAptitudes == null) {
            noAptitudes = new ArrayList<NoAptitud>();
        }
        return this.noAptitudes;
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
