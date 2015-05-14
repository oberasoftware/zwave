package com.oberasoftware.home.zwave.api.actions.devices;

import com.oberasoftware.home.zwave.api.ZWaveDeviceAction;

/**
 * @author renarj
 */
public class SwitchBinaryGetAction implements ZWaveDeviceAction {
    private final int nodeId;
    private final int endpointId;

    public SwitchBinaryGetAction(int nodeId, int endpointId) {
        this.nodeId = nodeId;
        this.endpointId = endpointId;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public int getEndpointId() {
        return endpointId;
    }

    @Override
    public String toString() {
        return "BinarySwitchGetAction{" +
                "nodeId=" + nodeId +
                ", endpointId=" + endpointId +
                '}';
    }
}
