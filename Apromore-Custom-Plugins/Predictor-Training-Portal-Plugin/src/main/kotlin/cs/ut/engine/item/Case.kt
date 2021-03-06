/*
 * This file is part of "Apromore".
 *
 * Copyright (C) 2018 - 2020 The University of Melbourne.
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

package cs.ut.engine.item

/**
 * Data class that used to hold information when parsing the log in CsvReader
 */
data class Case(val id: String) {

    val attributes = LinkedHashMap<String, MutableSet<String>>()
    val staticCols = mutableSetOf<String>()
    val dynamicCols = mutableSetOf<String>()

    val classifiedColumns = mutableMapOf<String, MutableSet<String>>()
}