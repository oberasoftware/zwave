package com.oberasoftware.home.zwave.api.events.devices;

/**
 * @author renarj
 */
public class BasicEvent implements DeviceEvent {
    private final int nodeId;
    private final int value;

    public BasicEvent(int nodeId, int value) {
        this.nodeId = nodeId;
        this.value = value;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "BasicEvent{" +
                "nodeId=" + nodeId +
                ", value=" + value +
                '}';
    }
}
