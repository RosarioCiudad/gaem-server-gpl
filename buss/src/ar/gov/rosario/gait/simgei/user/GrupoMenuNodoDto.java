
package ar.gov.rosario.gait.simgei.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for grupoMenuNodoDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="grupoMenuNodoDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="alta" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="atributos" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="baja" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="grupo" type="{http://ws.admusu.santafeciudad.gov.ar/}grupoDto" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="menuNodo" type="{http://ws.admusu.santafeciudad.gov.ar/}menuNodoDto" minOccurs="0"/>
 *         &lt;element name="modificacion" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="predefinido" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
@XmlType(name = "grupoMenuNodoDto", propOrder = {
    "alta",
    "atributos",
    "baja",
    "grupo",
    "id",
    "menuNodo",
    "modificacion",
    "predefinido",
    "version"
})
public class GrupoMenuNodoDto {

    protected boolean alta;
    protected String atributos;
    protected boolean baja;
    protected GrupoDto grupo;
    protected Long id;
    protected MenuNodoDto menuNodo;
    protected boolean modificacion;
    protected boolean predefinido;
    protected int version;

    /**
     * Gets the value of the alta property.
     * 
     */
    public boolean isAlta() {
        return alta;
    }

    /**
     * Sets the value of the alta property.
     * 
     */
    public void setAlta(boolean value) {
        this.alta = value;
    }

    /**
     * Gets the value of the atributos property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAtributos() {
        return atributos;
    }

    /**
     * Sets the value of the atributos property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAtributos(String value) {
        this.atributos = value;
    }

    /**
     * Gets the value of the baja property.
     * 
     */
    public boolean isBaja() {
        return baja;
    }

    /**
     * Sets the value of the baja property.
     * 
     */
    public void setBaja(boolean value) {
        this.baja = value;
    }

    /**
     * Gets the value of the grupo property.
     * 
     * @return
     *     possible object is
     *     {@link GrupoDto }
     *     
     */
    public GrupoDto getGrupo() {
        return grupo;
    }

    /**
     * Sets the value of the grupo property.
     * 
     * @param value
     *     allowed object is
     *     {@link GrupoDto }
     *     
     */
    public void setGrupo(GrupoDto value) {
        this.grupo = value;
    }

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
     * Gets the value of the menuNodo property.
     * 
     * @return
     *     possible object is
     *     {@link MenuNodoDto }
     *     
     */
    public MenuNodoDto getMenuNodo() {
        return menuNodo;
    }

    /**
     * Sets the value of the menuNodo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MenuNodoDto }
     *     
     */
    public void setMenuNodo(MenuNodoDto value) {
        this.menuNodo = value;
    }

    /**
     * Gets the value of the modificacion property.
     * 
     */
    public boolean isModificacion() {
        return modificacion;
    }

    /**
     * Sets the value of the modificacion property.
     * 
     */
    public void setModificacion(boolean value) {
        this.modificacion = value;
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
