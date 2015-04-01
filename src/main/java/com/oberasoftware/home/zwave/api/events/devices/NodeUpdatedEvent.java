package com.oberasoftware.home.zwave.api.events.devices;

import com.oberasoftware.home.zwave.core.ZWaveNode;

/**
 * @author renarj
 */
public class NodeUpdatedEvent implements DeviceEvent {
    private final int nodeId;
    private final ZWaveNode node;

    public NodeUpdatedEvent(int nodeId, ZWaveNode node) {
        this.nodeId = nodeId;
        this.node = node;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    public ZWaveNode getNode() {
        return node;
    }

    @Override
    public String toString() {
        return "NodeUpdatedEvent{" +
                "nodeId=" + nodeId +
                ", node=" + node +
                '}';
    }
}
