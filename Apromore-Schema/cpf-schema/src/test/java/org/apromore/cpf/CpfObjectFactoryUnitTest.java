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

package org.apromore.cpf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

/**
 * Test suite for {@link CpfObjectFactory}.
 */
public class CpfObjectFactoryUnitTest {

    /**
     * Test the equality and hashcode methods of {@link CancellationRefType}s.
     */
    @Test
    public void testCancellationRef() {
        CancellationRefType a = CpfObjectFactory.getInstance().createCancellationRefType();
        CancellationRefType b = CpfObjectFactory.getInstance().createCancellationRefType();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        a.setRefId("a");
        assertFalse(a.equals(b));
        assertFalse(a.hashCode() == b.hashCode());

        b.setRefId("b");
        assertFalse(a.equals(b));
        assertFalse(a.hashCode() == b.hashCode());

        b.setRefId("a");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
