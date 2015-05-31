package com.oberasoftware.home.zwave.api.events.devices;

import com.oberasoftware.home.zwave.api.events.controller.TransactionEvent;
import com.oberasoftware.home.zwave.api.messages.types.MeterScale;

import java.util.List;

/**
 * @author renarj
 */
public class MeterScalesEvent implements DeviceEvent, TransactionEvent {
    private final int nodeId;
    private final int endpointId;
    private final List<MeterScale> scales;

    public MeterScalesEvent(int nodeId, int endpointId, List<MeterScale> scales) {
        this.nodeId = nodeId;
        this.endpointId = endpointId;
        this.scales = scales;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public int getEndpointId() {
        return endpointId;
    }

    public List<MeterScale> getScales() {
        return scales;
    }

    @Override
    public boolean isTransactionCompleted() {
        return true;
    }

    @Override
    public String toString() {
        return "MeterScalesEvent{" +
                "nodeId=" + nodeId +
                ", endpointId=" + endpointId +
                ", scales=" + scales +
                '}';
    }
}
