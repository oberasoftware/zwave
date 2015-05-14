package com.oberasoftware.home.zwave.api.events.devices;

/**
 * @author renarj
 */
public class SwitchLevelEvent implements DeviceEvent {
    private final int nodeId;
    private final int value;

    public SwitchLevelEvent(int nodeId, int value) {
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
        return "SwitchLevelEvent{" +
                "nodeId=" + nodeId +
                ", value=" + value +
                '}';
    }
}
