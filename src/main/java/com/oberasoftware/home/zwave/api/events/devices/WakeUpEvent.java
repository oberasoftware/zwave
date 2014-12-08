package com.oberasoftware.home.zwave.api.events.devices;

/**
 * @author renarj
 */
public abstract class WakeUpEvent implements DeviceEvent {

    private final int nodeId;

    public WakeUpEvent(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public String toString() {
        return "WakeUpEvent{" +
                "nodeId=" + nodeId +
                '}';
    }
}
