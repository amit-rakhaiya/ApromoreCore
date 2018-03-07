/*
 * Copyright © 2009-2018 The Apromore Initiative.
 *
 * This file is part of "Apromore".
 *
 * "Apromore" is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * "Apromore" is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.
 * If not, see <http://www.gnu.org/licenses/lgpl-3.0.html>.
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.15 at 11:16:00 AM EST 
//

package epml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for typeMove complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="typeMove">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="position" type="{http://www.epml.de}typeMove2" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="line" type="{http://www.epml.de}typeLine" minOccurs="0"/>
 *         &lt;element name="font" type="{http://www.epml.de}typeFont" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "typeMove", propOrder = { "position", "line", "font" })
public class TypeMove {

	protected List<TypeMove2> position;
	protected TypeLine line;
	protected TypeFont font;

	/**
	 * Gets the value of the position property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the position property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getPosition().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link TypeMove2 }
	 * 
	 * 
	 */
	public List<TypeMove2> getPosition() {
		if (position == null) {
			position = new ArrayList<TypeMove2>();
		}
		return this.position;
	}

	/**
	 * Gets the value of the line property.
	 * 
	 * @return possible object is {@link TypeLine }
	 * 
	 */
	public TypeLine getLine() {
		return line;
	}

	/**
	 * Sets the value of the line property.
	 * 
	 * @param value
	 *            allowed object is {@link TypeLine }
	 * 
	 */
	public void setLine(TypeLine value) {
		this.line = value;
	}

	/**
	 * Gets the value of the font property.
	 * 
	 * @return possible object is {@link TypeFont }
	 * 
	 */
	public TypeFont getFont() {
		return font;
	}

	/**
	 * Sets the value of the font property.
	 * 
	 * @param value
	 *            allowed object is {@link TypeFont }
	 * 
	 */
	public void setFont(TypeFont value) {
		this.font = value;
	}

}
