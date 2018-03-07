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

package org.apromore.similaritysearch.common;

import org.apromore.similaritysearch.graph.Vertex;

public class VertexPair {
    Vertex left;
    Vertex right;
    boolean visited = false;
    double weight;
    public double ed;
    public double sem;
    public double syn;
    public double struct;
    public double parent;


    public VertexPair(Vertex left, Vertex right) {
        this.left = left;
        this.right = right;
    }

    public VertexPair(Vertex left, Vertex right, double weight) {
        this.left = left;
        this.right = right;
        this.weight = weight;
    }

    public VertexPair(Vertex first, Vertex second, double weight, double ed, double sem, double syn, double struct, double parent) {
        left = first;
        right = second;
        this.weight = weight;
        this.ed = ed;
        this.sem = sem;
        this.syn = syn;
        this.struct = struct;
        this.parent = parent;
    }


    public Vertex getLeft() {
        return left;
    }

    public Vertex getRight() {
        return right;
    }

    public double getWeight() {
        return weight;
    }

}
