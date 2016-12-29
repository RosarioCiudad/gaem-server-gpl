/*******************************************************************************
 * Copyright (c) 2016 Municipalidad de Rosario, Coop. de Trabajo Tecso Ltda.
 *
 * This file is part of GAEM.
 *
 * GAEM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * GAEM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAEM.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package ar.gov.rosario.gait.simgei.user;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for grupoDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="grupoDto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="grupoExterno" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="grupoHabilitado" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="idGrupo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="menuNodos" type="{http://ws.admusu.santafeciudad.gov.ar/}grupoMenuNodoDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="predefinido" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="usuarios" type="{http://ws.admusu.santafeciudad.gov.ar/}grupoUsuarioDto" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "grupoDto", propOrder = {
    "descripcion",
    "grupoExterno",
    "grupoHabilitado",
    "id",
    "idGrupo",
    "menuNodos",
    "predefinido",
    "usuarios",
    "version"
})
public class GrupoDto {

    protected String descripcion;
    protected boolean grupoExterno;
    protected boolean grupoHabilitado;
    protected Long id;
    protected String idGrupo;
    @XmlElement(nillable = true)
    protected List<GrupoMenuNodoDto> menuNodos;
    protected boolean predefinido;
    @XmlElement(nillable = true)
    protected List<GrupoUsuarioDto> usuarios;
    protected int version;

    /**
     * Gets the value of the descripcion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the value of the descripcion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Gets the value of the grupoExterno property.
     * 
     */
    public boolean isGrupoExterno() {
        return grupoExterno;
    }

    /**
     * Sets the value of the grupoExterno property.
     * 
     */
    public void setGrupoExterno(boolean value) {
        this.grupoExterno = value;
    }

    /**
     * Gets the value of the grupoHabilitado property.
     * 
     */
    public boolean isGrupoHabilitado() {
        return grupoHabilitado;
    }

    /**
     * Sets the value of the grupoHabilitado property.
     * 
     */
    public void setGrupoHabilitado(boolean value) {
        this.grupoHabilitado = value;
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
     * Gets the value of the idGrupo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdGrupo() {
        return idGrupo;
    }

    /**
     * Sets the value of the idGrupo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdGrupo(String value) {
        this.idGrupo = value;
    }

    /**
     * Gets the value of the menuNodos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the menuNodos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMenuNodos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GrupoMenuNodoDto }
     * 
     * 
     */
    public List<GrupoMenuNodoDto> getMenuNodos() {
        if (menuNodos == null) {
            menuNodos = new ArrayList<GrupoMenuNodoDto>();
        }
        return this.menuNodos;
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
     * Gets the value of the usuarios property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the usuarios property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUsuarios().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GrupoUsuarioDto }
     * 
     * 
     */
    public List<GrupoUsuarioDto> getUsuarios() {
        if (usuarios == null) {
            usuarios = new ArrayList<GrupoUsuarioDto>();
        }
        return this.usuarios;
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
