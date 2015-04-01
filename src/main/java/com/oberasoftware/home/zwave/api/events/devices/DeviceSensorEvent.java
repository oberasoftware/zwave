package com.oberasoftware.home.zwave.api.events.devices;

import com.oberasoftware.home.zwave.converter.events.SensorType;

import java.math.BigDecimal;

/**
 * @author renarj
 */
public class DeviceSensorEvent implements DeviceEvent {
    private final int nodeId;
    private final int endpointId;

    private final SensorType sensorType;

    private final BigDecimal value;

    public DeviceSensorEvent(int nodeId, int endpointId, SensorType sensorType, BigDecimal value) {
        this.nodeId = nodeId;
        this.endpointId = endpointId;
        this.sensorType = sensorType;
        this.value = value;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    public int getEndpointId() {
        return endpointId;
    }

    public SensorType getSensorType() {
        return sensorType;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean containsValue() {
        return true;
    }

    @Override
    public String valueAsString() {
        return value.toString();
    }

    @Override
    public int valueAsInt() {
        return value.intValue();
    }

    @Override
    public String toString() {
        return "DeviceSensorEvent{" +
                "nodeId=" + nodeId +
                ", endpointId=" + endpointId +
                ", sensorType=" + sensorType +
                ", value=" + value +
                '}';
    }
}
