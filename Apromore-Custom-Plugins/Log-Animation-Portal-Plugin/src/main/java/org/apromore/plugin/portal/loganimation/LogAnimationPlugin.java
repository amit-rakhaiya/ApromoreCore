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

package org.apromore.plugin.portal.loganimation;

// Java 2 Standard Edition
import java.util.*;

// Java 2 Enterprise Edition
import javax.inject.Inject;

// Third party packages
import org.apromore.model.*;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

// Local packages
import org.apromore.helper.Version;
import org.apromore.plugin.portal.DefaultPortalPlugin;
import org.apromore.plugin.portal.PortalContext;
import org.apromore.plugin.property.RequestParameterType;
import org.apromore.portal.common.UserSessionManager;
import org.apromore.portal.dialogController.MainController;
import org.apromore.portal.dialogController.dto.SignavioSession;
import org.apromore.service.EventLogService;
import org.apromore.service.loganimation.LogAnimationService;
import org.deckfour.xes.model.XLog;

public class LogAnimationPlugin extends DefaultPortalPlugin implements LogAnimationPluginInterface {

    private String label = ""; //initialized in Spring beans
    private String groupLabel = ""; //initialized in Spring beans

    @Inject private EventLogService eventLogService;
    @Inject private LogAnimationService logAnimationService;

    @Override
    public String getLabel(Locale locale) {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getGroupLabel(Locale locale) {
        return groupLabel;
    }

    public void setGroupLabel(String groupLabel) {
        this.groupLabel = groupLabel;
    }

    @Override
    public void execute(PortalContext portalContext) {

        List<Object[]> processes = new ArrayList<>();
        List<LogSummaryType> logSummaries = new ArrayList<>();

        Map<SummaryType, List<VersionSummaryType>> elements = portalContext.getSelection().getSelectedProcessModelVersions();
        for (SummaryType summary: elements.keySet()) {
            if (summary instanceof ProcessSummaryType) {
                for (VersionSummaryType version: elements.get(summary)) {
                    Object[] pair = { (ProcessSummaryType) summary, version };
                    processes.add(pair);
                }
            }
            else if (summary instanceof LogSummaryType) {
                logSummaries.add((LogSummaryType) summary);
            }
        }

        if (processes.size() != 1 || logSummaries.size() < 1) {
            Messagebox.show("Select exactly one BPMN model and at least one log", "Attention", Messagebox.OK, Messagebox.ERROR);
            return;
        }

        ProcessSummaryType process = (ProcessSummaryType) processes.get(0)[0];
        VersionSummaryType vst = (VersionSummaryType) processes.get(0)[1];

        // Fetch the XLog representations of the logs
        List<LogAnimationService.Log> logs = new ArrayList<>();
        Iterator<String> colors = Arrays.asList("#0088FF", "#FF8800", "#88FF00").iterator();
        for (LogSummaryType logSummary: logSummaries) {
            LogAnimationService.Log log = new LogAnimationService.Log();
            log.fileName = logSummary.getName();
            log.xlog     = eventLogService.getXLog(logSummary.getId());
            log.color    = colors.hasNext() ? colors.next() : "red";
            logs.add(log);
        }
        
        String username = portalContext.getCurrentUser().getUsername();
        MainController mainC = (MainController)portalContext.getMainController();
        EditSessionType editSession1 = createEditSession(username, process, vst, process.getOriginalNativeType(), null /*annotation*/);
        Set<RequestParameterType<?>> requestParameterTypes = new HashSet<>();
        SignavioSession session = new SignavioSession(editSession1, null, mainC, process, vst, null, null, requestParameterTypes);
        session.put("logAnimationService", logAnimationService);
        session.put("logs", logs);

        String id = UUID.randomUUID().toString();
        UserSessionManager.setEditSession(id, session);
        Clients.evalJavaScript("window.open('../loganimation/animateLog.zul?id=" + id + "')");
    }

    private static EditSessionType createEditSession(final String username, final ProcessSummaryType process, final VersionSummaryType version, final String nativeType, final String annotation) {

        EditSessionType editSession = new EditSessionType();

        editSession.setDomain(process.getDomain());
        editSession.setNativeType(nativeType.equals("XPDL 2.2")?"BPMN 2.0":nativeType);
        editSession.setProcessId(process.getId());
        editSession.setProcessName(process.getName());
        editSession.setUsername(username);
        editSession.setPublicModel(process.isMakePublic());
        editSession.setOriginalBranchName(version.getName());
        editSession.setOriginalVersionNumber(version.getVersionNumber());
        editSession.setCurrentVersionNumber(version.getVersionNumber());
        editSession.setMaxVersionNumber(findMaxVersion(process));

        editSession.setCreationDate(version.getCreationDate());
        editSession.setLastUpdate(version.getLastUpdate());
        if (annotation == null) {
            editSession.setWithAnnotation(false);
        } else {
            editSession.setWithAnnotation(true);
            editSession.setAnnotation(annotation);
        }

        return editSession;
    }

    /* From a list of version summary types find the max version number. */
    private static String findMaxVersion(ProcessSummaryType process) {
        Version versionNum;
        Version max = new Version(0, 0);
        for (VersionSummaryType version : process.getVersionSummaries()) {
            versionNum = new Version(version.getVersionNumber());
            if (versionNum.compareTo(max) > 0) {
                max = versionNum;
            }
        }
        return max.toString();
    }
    
    /**
     * @param json
     * @return the <var>json</var> escaped so that it can be quoted in Javascript.
     *     Specifically, it replaces apostrophes with \\u0027 and removes embedded newlines and leading and trailing whitespace.
     */
    private static String escapeQuotedJavascript(String json) {
        return json.replace("\n", " ").replace("'", "\\u0027").trim();
    }
    
    @Override
    /**
     * The log will be replayed on the process model represented by bpmn param.
     * However, the model displayed in the animation is different from the replayed model
     * For example: if it's a graph, all XOR gateways should be removed to show it as a graph
     * Therefore,  BPMNUpdater is used to create an updated BPMN based on the original bpmn
     * But then it requires the replay result to be also updated to make it relevant for 
     * the displayed BPMN only because it is created for the original BPMN. That's why 
     * AnimationUpdater is used to create an updated animation data from the created animation data
     */
    public void execute(PortalContext portalContext, String bpmn, String layout, XLog eventlog, boolean maintain_gateways) {
        try {
            List<LogAnimationService.Log> logs = new ArrayList<>();
            Iterator<String> colors = Arrays.asList("#0088FF", "#FF8800", "#88FF00").iterator();
            LogAnimationService.Log log = new LogAnimationService.Log();
            log.fileName = "Dummy";
            log.xlog     = eventlog;
            log.color    = colors.hasNext() ? colors.next() : "red";
            logs.add(log);

            String username = portalContext.getCurrentUser().getUsername();

            ProcessSummaryType processSummaryType = new ProcessSummaryType();
            processSummaryType.setDomain("Log-Visualizer");
            processSummaryType.setName("Log-Visualizer-Model");
            processSummaryType.setId(1);
            processSummaryType.setMakePublic(true);

            VersionSummaryType versionSummaryType = new VersionSummaryType();
            versionSummaryType.setName("Log-Visualizer-Model");
            versionSummaryType.setVersionNumber("1.0");

            EditSessionType editSession = createEditSession(username, processSummaryType, versionSummaryType, "BPMN 2.0", null);
            Set<RequestParameterType<?>> requestParameterTypes = new HashSet<>();
            SignavioSession session = new SignavioSession(editSession, null, null, processSummaryType, versionSummaryType, null, null, requestParameterTypes);

            //Bruce: Do not escape here because it will make the name containing quotes in BPMN different from the name in layout  
            //String updatedBPMN = escapeQuotedJavascript(bpmn);
            String updatedBPMN = bpmn;

            BPMNUpdater bpmnUpdater = new BPMNUpdater();
            updatedBPMN = bpmnUpdater.getUpdatedBPMN(updatedBPMN, layout, !maintain_gateways);

            System.out.println("Final BPMN");
//            System.out.println(jsonDataEscape);
            session.put("bpmnXML", updatedBPMN);

            if (logAnimationService != null) {  // logAnimationService is null if invoked from the editor toobar
                String animationData = logAnimationService.createAnimation(bpmn, logs);

                AnimationUpdater animationUpdater = new AnimationUpdater();
                animationData = animationUpdater.updateAnimationData(animationData, bpmnUpdater.getRemovedFlowIDs());

                session.put("animationData", escapeQuotedJavascript(animationData));
                System.out.println("ANIMATIONDATA");
//                System.out.println(escapeQuotedJavascript(animationData));
            }

            session.put("logs", logs);

            String id = UUID.randomUUID().toString();
            UserSessionManager.setEditSession(id, session);
            //Clients.evalJavaScript("window.open('../loganimation/animateLog.zul?id=" + id + "')");
            Clients.evalJavaScript("window.open('/loganimation/animateLog.zul?id=" + id + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
