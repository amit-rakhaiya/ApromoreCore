/*
 * This file is part of "Apromore".
 *
 * Copyright (C) 2016 - 2017 Queensland University of Technology.
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

package org.apromore.portal.context;

import org.apache.commons.io.IOUtils;
import org.apromore.model.*;
import org.apromore.plugin.portal.*;
import org.apromore.portal.common.UserSessionManager;
import org.apromore.portal.dialogController.MainController;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the PortalContext that is use by portal plug-ins to communicate with the portal.
 */
public class PluginPortalContext implements PortalContext {

    /**
     * Implementation of the PortalUI communication interface
     */
    private final static class PortalUIImpl implements PortalUI {

        @Override
        public Component createComponent(ClassLoader bundleClassLoader, String uri, Component parent, Map<?, ?> arguments) throws IOException {
            InputStream is = bundleClassLoader.getResourceAsStream(uri);
            return Executions.createComponentsDirectly(IOUtils.toString(is, "UTF-8"), "zul", parent, arguments);
        }

    }

    private final MainController mainController;
    private final PortalUI portalUI;

    /**
     * Create a new PluginPortalContext
     *
     * @param mainController
     */
    public PluginPortalContext(MainController mainController) {
        this.mainController = mainController;
        this.portalUI = new PortalUIImpl();
    }

    @Override
    public PortalSelection getSelection() {
        return new PortalSelection() {
            @Override
            public Map<SummaryType, List<VersionSummaryType>> getSelectedProcessModelVersions() {
                return mainController.getSelectedElementsAndVersions();
            }

            @Override
            public Set<SummaryType> getSelectedProcessModels() {
                return mainController.getSelectedElements();
            }

        };
    }

    @Override
    public PortalUI getUI() {
        return new PortalUIImpl();
    }


    // Scratch area for methods required during porting

    @Override
    public void displayNewProcess(ProcessSummaryType process) {
        mainController.displayNewProcess(process);
    }

    /**
     * Bruce 17.05.2019: Do not use UserSessionManager as it does not work outside the portal ZK environment
     * Apromore has webapp bundles with its own ZK environment
     */
    @Override
    public FolderType getCurrentFolder() {
        //return UserSessionManager.getCurrentFolder();
    	Desktop desktop = mainController.getDesktop();
    	return (FolderType)desktop.getSession().getAttribute(UserSessionManager.CURRENT_FOLDER);
    }

    /**
     * Bruce 17.05.2019: Do not use UserSessionManager as it does not work outside the portal ZK environment
     * Apromore has webapp bundles with its own ZK environment
     */
    @Override
    public UserType getCurrentUser() {
        //return UserSessionManager.getCurrentUser();
    	Desktop desktop = mainController.getDesktop();
        Session session = desktop.getSession();
        UserType userType = (UserType) session.getAttribute(UserSessionManager.USER);
    	return userType;
    }
    
    /**
     * Get attributes stored in the user session
     * Created by Bruce 17.05.2019
     */
    @Override
    public Object getAttribute(String attribute) {
    	Desktop desktop = mainController.getDesktop();
    	return desktop.getSession().getAttribute(attribute);
    }

    @Override
    public MainController getMainController() {
        return mainController;
    }

    @Override
    public void refreshContent() {
        mainController.refresh();
    }

    @Override
    public void displaySimilarityClusters(final ClusterFilterType filter) {
        mainController.displaySimilarityClusters(filter);
    }
}
