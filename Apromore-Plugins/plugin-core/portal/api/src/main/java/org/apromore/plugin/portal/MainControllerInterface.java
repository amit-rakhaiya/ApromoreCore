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

package org.apromore.plugin.portal;

import org.apromore.model.EditSessionType;
import org.apromore.model.ProcessSummaryType;
import org.apromore.model.VersionSummaryType;
import org.apromore.plugin.property.RequestParameterType;
import org.zkoss.zk.ui.SuspendNotAllowedException;

import java.util.*;

public interface MainControllerInterface {

	// Edit process model in Signavio
    void editProcess(final ProcessSummaryType process, final VersionSummaryType version, final String nativeType, final String annotation,
                     final String readOnly, Set<RequestParameterType<?>> requestParameterTypes) throws InterruptedException;
    
    // Edit process model in BPMN.io
    void editProcess2(ProcessSummaryType process, VersionSummaryType version, String nativeType, String annotation,
			String readOnly, Set<RequestParameterType<?>> requestParameterTypes, boolean newProcess)
			throws InterruptedException;

    void saveModel(ProcessSummaryType process, VersionSummaryType version, EditSessionType editSession,
            boolean isNormalSave, String data) throws  InterruptedException;

	
}

