package com.oberasoftware.home.zwave.core.impl;

import com.oberasoftware.home.zwave.api.events.controller.NodeInformationEvent;
import com.oberasoftware.home.zwave.api.events.devices.ManufactorInfoEvent;
import com.oberasoftware.home.zwave.core.NodeAvailability;
import com.oberasoftware.home.zwave.core.NodeStatus;
import com.oberasoftware.home.zwave.core.ZWaveNode;

import java.util.Optional;

/**
 * @author renarj
 */
public class ZWaveNodeImpl implements ZWaveNode {

    private final int nodeId;
    private final NodeStatus nodeStatus;
    private final NodeAvailability availability;

    private final Optional<NodeInformationEvent> optionalNodeInformation;
    private final Optional<ManufactorInfoEvent> optionalManufacturerInformation;

    public ZWaveNodeImpl(int nodeId, NodeStatus nodeStatus, NodeAvailability availability, Optional<NodeInformationEvent> optionalNodeInformation, Optional<ManufactorInfoEvent> optionalManufacturerInformation) {
        this.nodeId = nodeId;
        this.nodeStatus = nodeStatus;
        this.availability = availability;
        this.optionalNodeInformation = optionalNodeInformation;
        this.optionalManufacturerInformation = optionalManufacturerInformation;
    }

    @Override
    public NodeAvailability getAvailability() {
        return availability;
    }

    @Override
    public NodeStatus getNodeStatus() {
        return nodeStatus;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public Optional<NodeInformationEvent> getNodeInformation() {
        return optionalNodeInformation;
    }

    @Override
    public Optional<ManufactorInfoEvent> getManufactorInfoEvent() {
        return optionalManufacturerInformation;
    }

    @Override
    public String toString() {
        return "ZWaveNodeImpl{" +
                "nodeId=" + nodeId +
                ", nodeStatus=" + nodeStatus +
                ", availability=" + availability +
                ", nodeInformation=" + (optionalNodeInformation.isPresent() ? optionalNodeInformation.toString() : "") +
                ", optionalManufacturerInformation=" + (optionalManufacturerInformation.isPresent() ? optionalManufacturerInformation.isPresent() : "") +
                '}';
    }
}
