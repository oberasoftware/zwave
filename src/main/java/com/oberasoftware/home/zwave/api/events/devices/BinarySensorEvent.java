package com.oberasoftware.home.zwave.api.events.devices;

/**
 * @author renarj
 */
public class BinarySensorEvent implements DeviceEvent {

    private final int nodeId;
    private final boolean triggered;

    public BinarySensorEvent(int nodeId, boolean triggered) {
        this.nodeId = nodeId;
        this.triggered = triggered;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public boolean containsValue() {
        return true;
    }

    @Override
    public boolean isTriggered() {
        return triggered;
    }

    @Override
    public String toString() {
        return "BinarySensorEvent{" +
                "nodeId=" + nodeId +
                ", triggered=" + triggered +
                '}';
    }
}
