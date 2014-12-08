package com.oberasoftware.home.zwave.api.actions.devices;

import com.oberasoftware.home.zwave.api.ZWaveAction;

/**
 * @author renarj
 */
public class RequestNodeInfoAction implements ZWaveAction {

    private final int nodeId;

    public RequestNodeInfoAction(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getNodeId() {
        return nodeId;
    }
}
