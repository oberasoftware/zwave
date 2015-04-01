package com.oberasoftware.home.zwave.api.events.devices;

/**
 * @author renarj
 */
public class BatteryEvent implements DeviceEvent {
    private final int nodeId;
    private final int batteryLevel;

    public BatteryEvent(int nodeId, int batteryLevel) {
        this.nodeId = nodeId;
        this.batteryLevel = batteryLevel;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    @Override
    public boolean containsValue() {
        return true;
    }

    @Override
    public String valueAsString() {
        return Integer.toString(getBatteryLevel());
    }

    @Override
    public int valueAsInt() {
        return getBatteryLevel();
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public String toString() {
        return "BatteryEvent{" +
                "batteryLevel=" + batteryLevel +
                ", nodeId=" + nodeId +
                '}';
    }
}
