package com.oberasoftware.home.zwave.api.events.devices;

import com.oberasoftware.home.zwave.api.events.controller.TransactionEvent;
import com.oberasoftware.home.zwave.api.messages.types.MeterScale;
import com.oberasoftware.home.zwave.api.messages.types.MeterType;

import java.math.BigDecimal;

/**
 * @author renarj
 */
public class MeterEvent implements TransactionEvent, DeviceEvent {
    private final int nodeId;
    private final int endpointId;
    private final BigDecimal value;
    private final MeterType meterType;
    private final MeterScale scale;

    public MeterEvent(int nodeId, int endpointId, BigDecimal value, MeterType meterType, MeterScale scale) {
        this.nodeId = nodeId;
        this.endpointId = endpointId;
        this.value = value;
        this.meterType = meterType;
        this.scale = scale;
    }

    @Override
    public boolean isTransactionCompleted() {
        return true;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    public int getEndpointId() {
        return endpointId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public MeterType getMeterType() {
        return meterType;
    }

    public MeterScale getScale() {
        return scale;
    }

    @Override
    public String toString() {
        return "MeterEvent{" +
                "nodeId=" + nodeId +
                ", endpointId=" + endpointId +
                ", value=" + value +
                ", meterType=" + meterType +
                ", scale=" + scale +
                '}';
    }
}
