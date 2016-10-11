
package ar.gov.rosario.gait.simgei.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for grupoUsuarioDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="grupoUsuarioDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cuentaHabilitada" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="grupo" type="{http://ws.admusu.santafeciudad.gov.ar/}grupoDto" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="motivoInhabilitacion" type="{http://ws.admusu.santafeciudad.gov.ar/}motivoInhabilitacionDto" minOccurs="0"/>
 *         &lt;element name="predefinido" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="usuario" type="{http://ws.admusu.santafeciudad.gov.ar/}usuarioDto" minOccurs="0"/>
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
@XmlType(name = "grupoUsuarioDto", propOrder = {
    "cuentaHabilitada",
    "grupo",
    "id",
    "motivoInhabilitacion",
    "predefinido",
    "usuario",
    "version"
})
public class GrupoUsuarioDto {

    protected boolean cuentaHabilitada;
    protected GrupoDto grupo;
    protected Long id;
    protected MotivoInhabilitacionDto motivoInhabilitacion;
    protected boolean predefinido;
    protected UsuarioDto usuario;
    protected int version;

    /**
     * Gets the value of the cuentaHabilitada property.
     * 
     */
    public boolean isCuentaHabilitada() {
        return cuentaHabilitada;
    }

    /**
     * Sets the value of the cuentaHabilitada property.
     * 
     */
    public void setCuentaHabilitada(boolean value) {
        this.cuentaHabilitada = value;
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
     * Gets the value of the motivoInhabilitacion property.
     * 
     * @return
     *     possible object is
     *     {@link MotivoInhabilitacionDto }
     *     
     */
    public MotivoInhabilitacionDto getMotivoInhabilitacion() {
        return motivoInhabilitacion;
    }

    /**
     * Sets the value of the motivoInhabilitacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link MotivoInhabilitacionDto }
     *     
     */
    public void setMotivoInhabilitacion(MotivoInhabilitacionDto value) {
        this.motivoInhabilitacion = value;
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
     * Gets the value of the usuario property.
     * 
     * @return
     *     possible object is
     *     {@link UsuarioDto }
     *     
     */
    public UsuarioDto getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link UsuarioDto }
     *     
     */
    public void setUsuario(UsuarioDto value) {
        this.usuario = value;
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
