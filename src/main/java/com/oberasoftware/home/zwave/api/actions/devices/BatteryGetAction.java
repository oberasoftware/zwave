package com.oberasoftware.home.zwave.api.actions.devices;

import com.oberasoftware.home.zwave.api.ZWaveDeviceAction;

/**
 * @author renarj
 */
public class BatteryGetAction implements ZWaveDeviceAction {
    private final int nodeId;

    public BatteryGetAction(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public String toString() {
        return "BatteryGetAction{" +
                "nodeId=" + nodeId +
                '}';
    }
}
