package com.oberasoftware.home.zwave.api.events.devices;

/**
 * @author renarj
 */
public class ManufactorInfoEvent implements DeviceEvent {
    private final int nodeId;

    private final int manufactorId;
    private final int deviceType;
    private final int deviceId;

    public ManufactorInfoEvent(int nodeId, int manufactorId, int deviceType, int deviceId) {
        this.nodeId = nodeId;
        this.manufactorId = manufactorId;
        this.deviceType = deviceType;
        this.deviceId = deviceId;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    public int getManufactorId() {
        return manufactorId;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public int getDeviceId() {
        return deviceId;
    }

    @Override
    public String toString() {
        return "ManufactorInfoEvent{" +
                "nodeId=" + nodeId +
                ", manufactorId=" + manufactorId +
                ", deviceType=" + deviceType +
                ", deviceId=" + deviceId +
                '}';
    }
}
