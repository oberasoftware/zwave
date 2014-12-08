package com.oberasoftware.home.zwave.api.events.devices;

/**
 * @author renarj
 */
public class PingEvent implements DeviceEvent {
    private final int nodeId;

    public PingEvent(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public String toString() {
        return "PingEvent{" +
                "nodeId=" + nodeId +
                '}';
    }
}
