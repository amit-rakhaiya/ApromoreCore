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

package org.apromore.portal.custom.gui.plugin;

import org.apromore.model.ProcessSummaryType;
import org.apromore.model.VersionSummaryType;
import org.apromore.plugin.portal.MainControllerInterface;
import org.apromore.plugin.property.RequestParameterType;
import org.apromore.portal.custom.gui.tab.TabItemExecutor;
import org.apromore.portal.custom.gui.tab.impl.ProcessSummaryRowValue;
import org.apromore.portal.custom.gui.tab.impl.TabItem;

import java.util.HashSet;

/**
 * Created by Raffaele Conforti (conforti.raffaele@gmail.com) on 2/05/2016.
 */
public class ProcessTabItemExecutor implements TabItemExecutor {

    private MainControllerInterface mainControllerInterface;

    public ProcessTabItemExecutor(MainControllerInterface mainControllerInterface) {
        this.mainControllerInterface = mainControllerInterface;
    }

    @Override
    public void execute(TabItem listItem) {
        ProcessSummaryType pst = createProcessSummaryType((ProcessSummaryRowValue) listItem.getTabRowValue());
        VersionSummaryType vst = createVersionSummaryType((ProcessSummaryRowValue) listItem.getTabRowValue());
        try {
            mainControllerInterface.editProcess(pst, vst, pst.getOriginalNativeType(), null, "false", new HashSet<RequestParameterType<?>>());
        } catch (InterruptedException e) {
            System.out.println(pst);
            System.out.println(vst);
            e.printStackTrace();
        }
    }

    protected ProcessSummaryType createProcessSummaryType(ProcessSummaryRowValue rowValue) {
        return rowValue.getProcessSummaryType();
    }

    protected VersionSummaryType createVersionSummaryType(ProcessSummaryRowValue rowValue) {
        return rowValue.getVersionSummaryType();
    }
}
