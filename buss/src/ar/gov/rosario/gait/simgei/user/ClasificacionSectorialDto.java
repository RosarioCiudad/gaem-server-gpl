
package ar.gov.rosario.gait.simgei.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for clasificacionSectorialDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="clasificacionSectorialDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actividad" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="descripcion" type="{http://ws.admusu.santafeciudad.gov.ar/}descripcion" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="sector" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="subsector" type="{http://www.w3.org/2001/XMLSchema}short"/>
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
@XmlType(name = "clasificacionSectorialDto", propOrder = {
    "actividad",
    "descripcion",
    "id",
    "sector",
    "subsector",
    "version"
})
public class ClasificacionSectorialDto {

    protected short actividad;
    protected Descripcion descripcion;
    protected Long id;
    protected short sector;
    protected short subsector;
    protected int version;

    /**
     * Gets the value of the actividad property.
     * 
     */
    public short getActividad() {
        return actividad;
    }

    /**
     * Sets the value of the actividad property.
     * 
     */
    public void setActividad(short value) {
        this.actividad = value;
    }

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link Descripcion }
     *     
     */
    public Descripcion getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Descripcion }
     *     
     */
    public void setDescripcion(Descripcion value) {
        this.descripcion = value;
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
     * Gets the value of the sector property.
     * 
     */
    public short getSector() {
        return sector;
    }

    /**
     * Sets the value of the sector property.
     * 
     */
    public void setSector(short value) {
        this.sector = value;
    }

    /**
     * Gets the value of the subsector property.
     * 
     */
    public short getSubsector() {
        return subsector;
    }

    /**
     * Sets the value of the subsector property.
     * 
     */
    public void setSubsector(short value) {
        this.subsector = value;
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
