/*
 * This file is part of "Apromore".
 *
 * Copyright (C) 2017 - 2018 Adriano Augusto.
 * Copyright (C) 2017 Queensland University of Technology.
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

package au.edu.qut.processmining.miners.splitminer.ui.miner;

import au.edu.qut.processmining.miners.splitminer.ui.dfgp.DFGPUI;
import au.edu.qut.processmining.miners.splitminer.ui.dfgp.DFGPUIResult;
import org.deckfour.uitopia.api.event.TaskListener;
import org.processmining.contexts.uitopia.UIPluginContext;

import java.util.concurrent.CancellationException;

/**
 * Created by Adriano on 29/02/2016.
 */
public class SplitMinerUI {

    public SplitMinerUIResult showGUI(UIPluginContext context, String title) {

        DFGPUIResult dfgpUIResult = (new DFGPUI()).showGUI(context, "");

        SplitMinerSettings SplitMinerSettings = new SplitMinerSettings();
        TaskListener.InteractionResult GUI = context.showWizard(title, true, true, SplitMinerSettings);

        if( GUI == TaskListener.InteractionResult.CANCEL ) {
            context.getFutureResult(0).cancel(true);
            throw new CancellationException("The wizard has been cancelled.");
        }

        SplitMinerUIResult smUIResult = SplitMinerSettings.getSelections();

//      setup params for DFG+
        smUIResult.setFilterType(dfgpUIResult.getFilterType());
        smUIResult.setParallelismsThreshold(dfgpUIResult.getParallelismsThreshold());
        smUIResult.setPercentileFrequencyThreshold(dfgpUIResult.getPercentileFrequencyThreshold());
        smUIResult.setParallelismsFirst(dfgpUIResult.isParallelismsFirst());

        return smUIResult;
    }

}
