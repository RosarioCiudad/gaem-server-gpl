
package ar.gov.rosario.gait.simgei.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for menuNodoDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="menuNodoDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="menuSeccionPadre" type="{http://ws.admusu.santafeciudad.gov.ar/}menuSeccionDto" minOccurs="0"/>
 *         &lt;element name="orden" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="predefinido" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="sistema" type="{http://ws.admusu.santafeciudad.gov.ar/}sistemaDto" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "menuNodoDto", propOrder = {
    "id",
    "menuSeccionPadre",
    "orden",
    "predefinido",
    "sistema",
    "version"
})
@XmlSeeAlso({
    MenuSeccionDto.class
})
public abstract class MenuNodoDto {

    protected Long id;
    protected MenuSeccionDto menuSeccionPadre;
    protected int orden;
    protected boolean predefinido;
    protected SistemaDto sistema;
    protected int version;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setId(Long value) {
        this.id = value;
    }

    /**
     * Gets the value of the menuSeccionPadre property.
     * 
     * @return
     *     possible object is
     *     {@link MenuSeccionDto }
     *     
     */
    public MenuSeccionDto getMenuSeccionPadre() {
        return menuSeccionPadre;
    }

    /**
     * Sets the value of the menuSeccionPadre property.
     * 
     * @param value
     *     allowed object is
     *     {@link MenuSeccionDto }
     *     
     */
    public void setMenuSeccionPadre(MenuSeccionDto value) {
        this.menuSeccionPadre = value;
    }

    /**
     * Gets the value of the orden property.
     * 
     */
    public int getOrden() {
        return orden;
    }

    /**
     * Sets the value of the orden property.
     * 
     */
    public void setOrden(int value) {
        this.orden = value;
    }

    /**
     * Gets the value of the predefinido property.
     * 
     */
    public boolean isPredefinido() {
        return predefinido;
    }

    /**
     * Sets the value of the predefinido property.
     * 
     */
    public void setPredefinido(boolean value) {
        this.predefinido = value;
    }

    /**
     * Gets the value of the sistema property.
     * 
     * @return
     *     possible object is
     *     {@link SistemaDto }
     *     
     */
    public SistemaDto getSistema() {
        return sistema;
    }

    /**
     * Sets the value of the sistema property.
     * 
     * @param value
     *     allowed object is
     *     {@link SistemaDto }
     *     
     */
    public void setSistema(SistemaDto value) {
        this.sistema = value;
    }

    /**
     * Gets the value of the version property.
     * 
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     */
    public void setVersion(int value) {
        this.version = value;
    }

}
