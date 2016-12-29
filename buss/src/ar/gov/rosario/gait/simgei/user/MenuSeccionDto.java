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
 * <p>Java class for menuSeccionDto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="menuSeccionDto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.admusu.santafeciudad.gov.ar/}menuNodoDto">
 *       &lt;sequence>
 *         &lt;element name="menuNodosHijos" type="{http://ws.admusu.santafeciudad.gov.ar/}menuNodoDto" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="texto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "menuSeccionDto", propOrder = {
    "menuNodosHijos",
    "texto"
})
public class MenuSeccionDto
    extends MenuNodoDto
{

    @XmlElement(nillable = true)
    protected List<MenuNodoDto> menuNodosHijos;
    protected String texto;

    /**
     * Gets the value of the menuNodosHijos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the menuNodosHijos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMenuNodosHijos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MenuNodoDto }
     * 
     * 
     */
    public List<MenuNodoDto> getMenuNodosHijos() {
        if (menuNodosHijos == null) {
            menuNodosHijos = new ArrayList<MenuNodoDto>();
        }
        return this.menuNodosHijos;
    }

    /**
     * Gets the value of the texto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Sets the value of the texto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTexto(String value) {
        this.texto = value;
    }

}
