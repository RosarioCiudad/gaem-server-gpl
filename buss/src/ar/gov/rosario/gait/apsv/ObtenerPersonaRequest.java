
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
 *         &lt;element name="codDoc" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nroDoc" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sexo" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "codDoc",
    "nroDoc",
    "sexo"
})
@XmlRootElement(name = "obtenerPersonaRequest")
public class ObtenerPersonaRequest {

    @XmlElement(required = true)
    protected RequestInfo requestInfo;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer codDoc;
    protected int nroDoc;
    @XmlElement(required = true, nillable = true)
    protected String sexo;

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
     * Gets the value of the codDoc property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodDoc() {
        return codDoc;
    }

    /**
     * Sets the value of the codDoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodDoc(Integer value) {
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

}
