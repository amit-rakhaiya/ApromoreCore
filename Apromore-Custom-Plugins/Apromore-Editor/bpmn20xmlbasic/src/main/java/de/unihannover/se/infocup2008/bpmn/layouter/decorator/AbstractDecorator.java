package de.unihannover.se.infocup2008.bpmn.layouter.decorator;

/*-
 * #%L
 * Signavio Core Components
 * %%
 * Copyright (C) 2006 - 2020 Philipp Berger, Martin Czuchra, Gero Decker,
 * Ole Eckermann, Lutz Gericke,
 * Alexander Hold, Alexander Koglin, Oliver Kopp, Stefan Krumnow,
 * Matthias Kunze, Philipp Maschke, Falko Menge, Christoph Neijenhuis,
 * Hagen Overdick, Zhen Peng, Nicolas Peters, Kerstin Pfitzner, Daniel Polak,
 * Steffen Ryll, Kai Schlichting, Jan-Felix Schwarz, Daniel Taschik,
 * Willi Tscheschner, Björn Wagner, Sven Wagner-Boysen, Matthias Weidlich
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 * 
 * 
 * 
 * Ext JS (http://extjs.com/) is used under the terms of the Open Source LGPL 3.0
 * license.
 * The license and the source files can be found in our SVN repository at:
 * http://oryx-editor.googlecode.com/.
 * #L%
 */

import de.hpi.layouting.model.LayoutingBounds;

/**
 * AbstractDecorator implements just delegation for all methods.
 *
 * @author Team Royal Fawn
 */
public abstract class AbstractDecorator implements LayoutingBounds {


    private LayoutingBounds target;

    /**
     * @param target
     */
    protected AbstractDecorator(LayoutingBounds target) {
        super();
        this.target = target;
    }

    /**
     * @return
     * @see de.hpi.layouting.model.LayoutingBounds#getHeight()
     */
    public double getHeight() {
        return target.getHeight();
    }


    /**
     * @return
     * @see de.hpi.layouting.model.LayoutingBounds#getWidth()
     */
    public double getWidth() {
        return target.getWidth();
    }


    /**
     * @return
     * @see de.hpi.layouting.model.LayoutingBounds#getX()
     */
    public double getX() {
        return target.getX();
    }


    /**
     * @return
     * @see de.hpi.layouting.model.LayoutingBounds#getX2()
     */
    public double getX2() {
        return getX() + getWidth();
    }


    /**
     * @return
     * @see de.hpi.layouting.model.LayoutingBounds#getY()
     */
    public double getY() {
        return target.getY();
    }


    /**
     * @return
     * @see de.hpi.layouting.model.LayoutingBounds#getY2()
     */
    public double getY2() {
        return getY() + getHeight();
    }


    @Override
    public String toString() {
        String out = " x=" + getX();
        out += " y=" + getY();
        out += " width=" + getWidth();
        out += " height=" + getHeight();
        return out;
    }

}
