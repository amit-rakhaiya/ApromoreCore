/*
 * This file is part of "Apromore".
 *
 * Copyright (C) 2019 - 2020 The University of Melbourne.
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

/**
 * Annotation Format XML schema.
 *
 * @author <a href="mailto:simon.raboczi@uqconnect.edu.au">Simon Raboczi</a>
 */

@javax.xml.bind.annotation.XmlSchema(
    namespace = "http://www.apromore.org/ANF",
    xmlns = {
        @javax.xml.bind.annotation.XmlNs(prefix = "anf",    namespaceURI = "http://www.apromore.org/ANF"),
        @javax.xml.bind.annotation.XmlNs(prefix = "xsi",    namespaceURI = "http://www.w3.org/2001/XMLSchema-instance")
    },
    elementFormDefault = javax.xml.bind.annotation.XmlNsForm.UNQUALIFIED)

package org.apromore.anf;
