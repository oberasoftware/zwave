package com.oberasoftware.home.zwave.api.events.devices;

/**
 * @author renarj
 */
public class SwitchEvent implements DeviceEvent {
    private final int nodeId;
    private final boolean on;
    private final int endpointId;

    public SwitchEvent(int nodeId, boolean on) {
        this(nodeId, 0, on);
    }

    public SwitchEvent(int nodeId, int endpointId, boolean on) {
        this.nodeId = nodeId;
        this.endpointId = endpointId;
        this.on = on;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    public boolean isOn() {
        return on;
    }

    public int getEndpointId() {
        return endpointId;
    }

    @Override
    public String toString() {
        return "SwitchEvent{" +
                "nodeId=" + nodeId +
                ", on=" + on +
                ", endpointId=" + endpointId +
                '}';
    }
}
