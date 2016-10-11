
package ar.gov.rosario.gait.simgei.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for distritoDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="distritoDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codigo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="codigoPostal" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *         &lt;element name="departamento" type="{http://ws.admusu.santafeciudad.gov.ar/}departamentoDto" minOccurs="0"/>
 *         &lt;element name="descripcion" type="{http://ws.admusu.santafeciudad.gov.ar/}descripcion" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="local" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="provincia" type="{http://ws.admusu.santafeciudad.gov.ar/}provinciaEstadoDto" minOccurs="0"/>
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
@XmlType(name = "distritoDto", propOrder = {
    "codigo",
    "codigoPostal",
    "departamento",
    "descripcion",
    "id",
    "local",
    "provincia",
    "version"
})
public class DistritoDto {

    protected int codigo;
    protected Short codigoPostal;
    protected DepartamentoDto departamento;
    protected Descripcion descripcion;
    protected Long id;
    protected boolean local;
    protected ProvinciaEstadoDto provincia;
    protected int version;

    /**
     * Gets the value of the codigo property.
     * 
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Sets the value of the codigo property.
     * 
     */
    public void setCodigo(int value) {
        this.codigo = value;
    }

    /**
     * Gets the value of the codigoPostal property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Sets the value of the codigoPostal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setCodigoPostal(Short value) {
        this.codigoPostal = value;
    }

    /**
     * Gets the value of the departamento property.
     * 
     * @return
     *     possible object is
     *     {@link DepartamentoDto }
     *     
     */
    public DepartamentoDto getDepartamento() {
        return departamento;
    }

    /**
     * Sets the value of the departamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepartamentoDto }
     *     
     */
    public void setDepartamento(DepartamentoDto value) {
        this.departamento = value;
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
     * Gets the value of the local property.
     * 
     */
    public boolean isLocal() {
        return local;
    }

    /**
     * Sets the value of the local property.
     * 
     */
    public void setLocal(boolean value) {
        this.local = value;
    }

    /**
     * Gets the value of the provincia property.
     * 
     * @return
     *     possible object is
     *     {@link ProvinciaEstadoDto }
     *     
     */
    public ProvinciaEstadoDto getProvincia() {
        return provincia;
    }

    /**
     * Sets the value of the provincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProvinciaEstadoDto }
     *     
     */
    public void setProvincia(ProvinciaEstadoDto value) {
        this.provincia = value;
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
