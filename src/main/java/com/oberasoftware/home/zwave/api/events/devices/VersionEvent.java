package com.oberasoftware.home.zwave.api.events.devices;

/**
 * @author renarj
 */
public class VersionEvent implements DeviceEvent {
    private final int nodeId;

    public VersionEvent(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public String toString() {
        return "VersionEvent{" +
                "nodeId=" + nodeId +
                '}';
    }
}
