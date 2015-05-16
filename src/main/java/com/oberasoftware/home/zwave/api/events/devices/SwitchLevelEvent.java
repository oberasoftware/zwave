package com.oberasoftware.home.zwave.api.events.devices;

import com.oberasoftware.home.zwave.api.events.controller.TransactionEvent;

/**
 * @author renarj
 */
public class SwitchLevelEvent implements DeviceEvent, TransactionEvent {
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
    public boolean isTransactionCompleted() {
        return true;
    }

    @Override
    public String toString() {
        return "SwitchLevelEvent{" +
                "nodeId=" + nodeId +
                ", value=" + value +
                '}';
    }
}
