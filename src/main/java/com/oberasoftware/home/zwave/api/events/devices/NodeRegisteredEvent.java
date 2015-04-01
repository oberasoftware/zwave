package com.oberasoftware.home.zwave.api.events.devices;

import com.oberasoftware.home.zwave.core.ZWaveNode;

/**
 * @author renarj
 */
public class NodeRegisteredEvent implements DeviceEvent {

    private final int nodeId;
    private final ZWaveNode node;

    public NodeRegisteredEvent(int nodeId, ZWaveNode node) {
        this.node = node;
        this.nodeId = nodeId;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    public ZWaveNode getNode() {
        return node;
    }
}
