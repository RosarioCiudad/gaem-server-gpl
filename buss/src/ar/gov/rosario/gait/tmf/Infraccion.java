
package ar.gov.rosario.gait.tmf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Infraccion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Infraccion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codInfraccion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Infraccion", propOrder = {
    "codInfraccion"
})
public class Infraccion {

    @XmlElement(required = true)
    protected String codInfraccion;

    /**
     * Gets the value of the codInfraccion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodInfraccion() {
        return codInfraccion;
    }

    /**
     * Sets the value of the codInfraccion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodInfraccion(String value) {
        this.codInfraccion = value;
    }

}
