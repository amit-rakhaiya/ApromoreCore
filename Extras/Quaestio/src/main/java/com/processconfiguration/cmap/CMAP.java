/*******************************************************************************
 * Copyright © 2006-2011, www.processconfiguration.com
 *   
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 *      Marcello La Rosa - initial API and implementation, subsequent revisions
 *      Florian Gottschalk - individualizer for YAWL
 *      Possakorn Pitayarojanakul - integration with Configurator and Individualizer
 ******************************************************************************/
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.04.14 at 01:59:07 PM EST 
//

package com.processconfiguration.cmap;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="2">
 *         &lt;element name="c-epc" type="{http://www.processconfiguration.com/CMAP}c-epcType"/>
 *         &lt;element name="c-yawl" type="{http://www.processconfiguration.com/CMAP}c-yawlType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "cEpcOrCYawl", "CEpc", "CYawl" })
@XmlRootElement(name = "CMAP")
public class CMAP {

	@XmlElements({ @XmlElement(name = "c-epc", type = CEpcType.class),
			@XmlElement(name = "c-yawl", type = CYawlType.class) })
	protected List<Object> cEpcOrCYawl;
	protected CEpcType CEpc; // --- new parameter
	protected CYawlType CYawl; // --- new parameter

	/**
	 * Gets the value of the cEpcOrCYawl property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the cEpcOrCYawl property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getCEpcOrCYawl().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link CEpcType }
	 * {@link CYawlType }
	 * 
	 * 
	 */
	public List<Object> getCEpcOrCYawl() {
		if (cEpcOrCYawl == null) {
			cEpcOrCYawl = new ArrayList<Object>();
		}
		return this.cEpcOrCYawl;
	}

	// ----- add method -----
	public CEpcType getCEpc() {
		if (CEpc == null) {
			for (Object current : this.getCEpcOrCYawl()) {
				if (current instanceof CEpcType) {
					CEpc = (CEpcType) current;
					return (CEpcType) current;
				}
			}
		}
		return this.CEpc;
	}

	public CYawlType getCYawl() {
		if (CYawl == null) {
			for (Object current : this.getCEpcOrCYawl()) {
				if (current instanceof CYawlType) {
					CYawl = (CYawlType) current;
					return (CYawlType) current;
				}
			}
		}
		return this.CYawl;
	}

}
