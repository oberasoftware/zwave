package com.oberasoftware.home.zwave.api.actions.devices;

import com.oberasoftware.home.zwave.api.ZWaveAction;

/**
 * @author renarj
 */
public class IdentifyNodeAction implements ZWaveAction {

    private int nodeId;

    public IdentifyNodeAction(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getNodeId() {
        return nodeId;
    }
}
