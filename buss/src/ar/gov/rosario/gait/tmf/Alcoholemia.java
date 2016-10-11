
package ar.gov.rosario.gait.tmf;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Alcoholemia complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Alcoholemia">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idAlcoholimetro" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="medicion" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="nroMedicion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idMedico" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="nroEvaluacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codCalleFuga" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idSentidoCirculacion" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="inspectoresFuga" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="policiasFuga" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idSeccionalPolicial" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Alcoholemia", propOrder = {
    "idAlcoholimetro",
    "medicion",
    "nroMedicion",
    "idMedico",
    "nroEvaluacion",
    "codCalleFuga",
    "idSentidoCirculacion",
    "inspectoresFuga",
    "policiasFuga",
    "idSeccionalPolicial"
})
public class Alcoholemia {

    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer idAlcoholimetro;
    @XmlElement(required = true, nillable = true)
    protected BigDecimal medicion;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer nroMedicion;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer idMedico;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer nroEvaluacion;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer codCalleFuga;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer idSentidoCirculacion;
    @XmlElement(required = true, nillable = true)
    protected String inspectoresFuga;
    @XmlElement(required = true, nillable = true)
    protected String policiasFuga;
    @XmlElement(required = true, type = Integer.class, nillable = true)
    protected Integer idSeccionalPolicial;

    /**
     * Gets the value of the idAlcoholimetro property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdAlcoholimetro() {
        return idAlcoholimetro;
    }

    /**
     * Sets the value of the idAlcoholimetro property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdAlcoholimetro(Integer value) {
        this.idAlcoholimetro = value;
    }

    /**
     * Gets the value of the medicion property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMedicion() {
        return medicion;
    }

    /**
     * Sets the value of the medicion property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMedicion(BigDecimal value) {
        this.medicion = value;
    }

    /**
     * Gets the value of the nroMedicion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNroMedicion() {
        return nroMedicion;
    }

    /**
     * Sets the value of the nroMedicion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNroMedicion(Integer value) {
        this.nroMedicion = value;
    }

    /**
     * Gets the value of the idMedico property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdMedico() {
        return idMedico;
    }

    /**
     * Sets the value of the idMedico property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdMedico(Integer value) {
        this.idMedico = value;
    }

    /**
     * Gets the value of the nroEvaluacion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNroEvaluacion() {
        return nroEvaluacion;
    }

    /**
     * Sets the value of the nroEvaluacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNroEvaluacion(Integer value) {
        this.nroEvaluacion = value;
    }

    /**
     * Gets the value of the codCalleFuga property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodCalleFuga() {
        return codCalleFuga;
    }

    /**
     * Sets the value of the codCalleFuga property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodCalleFuga(Integer value) {
        this.codCalleFuga = value;
    }

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
     * Gets the value of the inspectoresFuga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInspectoresFuga() {
        return inspectoresFuga;
    }

    /**
     * Sets the value of the inspectoresFuga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInspectoresFuga(String value) {
        this.inspectoresFuga = value;
    }

    /**
     * Gets the value of the policiasFuga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoliciasFuga() {
        return policiasFuga;
    }

    /**
     * Sets the value of the policiasFuga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoliciasFuga(String value) {
        this.policiasFuga = value;
    }

    /**
     * Gets the value of the idSeccionalPolicial property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdSeccionalPolicial() {
        return idSeccionalPolicial;
    }

    /**
     * Sets the value of the idSeccionalPolicial property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdSeccionalPolicial(Integer value) {
        this.idSeccionalPolicial = value;
    }

}
