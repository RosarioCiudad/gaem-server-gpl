
package ar.gov.rosario.gait.simgei.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for direccionDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="direccionDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="areaUrbana" type="{http://ws.admusu.santafeciudad.gov.ar/}areaUrbanaDto" minOccurs="0"/>
 *         &lt;element name="departamento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="edificio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="letra" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="local" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="manzana" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monoblock" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nomenclador" type="{http://ws.admusu.santafeciudad.gov.ar/}nomencladorDto" minOccurs="0"/>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="observaciones" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="padron" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="piso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sector" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="secuencia" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="textoDomicilio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoPortal" type="{http://ws.admusu.santafeciudad.gov.ar/}tipoPortal" minOccurs="0"/>
 *         &lt;element name="tipoValidacion" type="{http://ws.admusu.santafeciudad.gov.ar/}tipoValidacionDireccion" minOccurs="0"/>
 *         &lt;element name="torre" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="vivienda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zona" type="{http://www.w3.org/2001/XMLSchema}short" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "direccionDto", propOrder = {
    "areaUrbana",
    "departamento",
    "edificio",
    "id",
    "letra",
    "local",
    "manzana",
    "monoblock",
    "nomenclador",
    "numero",
    "observaciones",
    "padron",
    "piso",
    "sector",
    "secuencia",
    "textoDomicilio",
    "tipoPortal",
    "tipoValidacion",
    "torre",
    "version",
    "vivienda",
    "zona"
})
public class DireccionDto {

    protected AreaUrbanaDto areaUrbana;
    protected String departamento;
    protected String edificio;
    protected Long id;
    protected String letra;
    protected String local;
    protected String manzana;
    protected String monoblock;
    protected NomencladorDto nomenclador;
    protected Integer numero;
    protected String observaciones;
    protected String padron;
    protected String piso;
    protected String sector;
    protected int secuencia;
    protected String textoDomicilio;
    protected TipoPortal tipoPortal;
    protected TipoValidacionDireccion tipoValidacion;
    protected String torre;
    protected int version;
    protected String vivienda;
    protected Short zona;

    /**
     * Gets the value of the areaUrbana property.
     * 
     * @return
     *     possible object is
     *     {@link AreaUrbanaDto }
     *     
     */
    public AreaUrbanaDto getAreaUrbana() {
        return areaUrbana;
    }

    /**
     * Sets the value of the areaUrbana property.
     * 
     * @param value
     *     allowed object is
     *     {@link AreaUrbanaDto }
     *     
     */
    public void setAreaUrbana(AreaUrbanaDto value) {
        this.areaUrbana = value;
    }

    /**
     * Gets the value of the departamento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Sets the value of the departamento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepartamento(String value) {
        this.departamento = value;
    }

    /**
     * Gets the value of the edificio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEdificio() {
        return edificio;
    }

    /**
     * Sets the value of the edificio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEdificio(String value) {
        this.edificio = value;
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
     * Gets the value of the letra property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLetra() {
        return letra;
    }

    /**
     * Sets the value of the letra property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLetra(String value) {
        this.letra = value;
    }

    /**
     * Gets the value of the local property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocal() {
        return local;
    }

    /**
     * Sets the value of the local property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocal(String value) {
        this.local = value;
    }

    /**
     * Gets the value of the manzana property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManzana() {
        return manzana;
    }

    /**
     * Sets the value of the manzana property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManzana(String value) {
        this.manzana = value;
    }

    /**
     * Gets the value of the monoblock property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonoblock() {
        return monoblock;
    }

    /**
     * Sets the value of the monoblock property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonoblock(String value) {
        this.monoblock = value;
    }

    /**
     * Gets the value of the nomenclador property.
     * 
     * @return
     *     possible object is
     *     {@link NomencladorDto }
     *     
     */
    public NomencladorDto getNomenclador() {
        return nomenclador;
    }

    /**
     * Sets the value of the nomenclador property.
     * 
     * @param value
     *     allowed object is
     *     {@link NomencladorDto }
     *     
     */
    public void setNomenclador(NomencladorDto value) {
        this.nomenclador = value;
    }

    /**
     * Gets the value of the numero property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumero(Integer value) {
        this.numero = value;
    }

    /**
     * Gets the value of the observaciones property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Sets the value of the observaciones property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservaciones(String value) {
        this.observaciones = value;
    }

    /**
     * Gets the value of the padron property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPadron() {
        return padron;
    }

    /**
     * Sets the value of the padron property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPadron(String value) {
        this.padron = value;
    }

    /**
     * Gets the value of the piso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPiso() {
        return piso;
    }

    /**
     * Sets the value of the piso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPiso(String value) {
        this.piso = value;
    }

    /**
     * Gets the value of the sector property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSector() {
        return sector;
    }

    /**
     * Sets the value of the sector property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSector(String value) {
        this.sector = value;
    }

    /**
     * Gets the value of the secuencia property.
     * 
     */
    public int getSecuencia() {
        return secuencia;
    }

    /**
     * Sets the value of the secuencia property.
     * 
     */
    public void setSecuencia(int value) {
        this.secuencia = value;
    }

    /**
     * Gets the value of the textoDomicilio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextoDomicilio() {
        return textoDomicilio;
    }

    /**
     * Sets the value of the textoDomicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextoDomicilio(String value) {
        this.textoDomicilio = value;
    }

    /**
     * Gets the value of the tipoPortal property.
     * 
     * @return
     *     possible object is
     *     {@link TipoPortal }
     *     
     */
    public TipoPortal getTipoPortal() {
        return tipoPortal;
    }

    /**
     * Sets the value of the tipoPortal property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoPortal }
     *     
     */
    public void setTipoPortal(TipoPortal value) {
        this.tipoPortal = value;
    }

    /**
     * Gets the value of the tipoValidacion property.
     * 
     * @return
     *     possible object is
     *     {@link TipoValidacionDireccion }
     *     
     */
    public TipoValidacionDireccion getTipoValidacion() {
        return tipoValidacion;
    }

    /**
     * Sets the value of the tipoValidacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoValidacionDireccion }
     *     
     */
    public void setTipoValidacion(TipoValidacionDireccion value) {
        this.tipoValidacion = value;
    }

    /**
     * Gets the value of the torre property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTorre() {
        return torre;
    }

    /**
     * Sets the value of the torre property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTorre(String value) {
        this.torre = value;
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

    /**
     * Gets the value of the vivienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVivienda() {
        return vivienda;
    }

    /**
     * Sets the value of the vivienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVivienda(String value) {
        this.vivienda = value;
    }

    /**
     * Gets the value of the zona property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getZona() {
        return zona;
    }

    /**
     * Sets the value of the zona property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setZona(Short value) {
        this.zona = value;
    }

}
