package com.oberasoftware.home.zwave.core.impl;

import com.oberasoftware.home.zwave.api.events.controller.NodeIdentifyEvent;
import com.oberasoftware.home.zwave.api.events.devices.ManufactorInfoEvent;
import com.oberasoftware.home.zwave.core.NodeAvailability;
import com.oberasoftware.home.zwave.core.NodeStatus;
import com.oberasoftware.home.zwave.core.ZWaveNode;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author renarj
 */
public class ZWaveNodeImpl implements ZWaveNode {

    private final int nodeId;
    private final NodeStatus nodeStatus;
    private final NodeAvailability availability;

    private final Optional<NodeIdentifyEvent> optionalNodeInformation;
    private final Optional<ManufactorInfoEvent> optionalManufacturerInformation;
    private final List<CommandClass> commandClasses = new ArrayList<>();

    public ZWaveNodeImpl(int nodeId, NodeStatus nodeStatus, NodeAvailability availability, Optional<NodeIdentifyEvent> optionalNodeInformation, Optional<ManufactorInfoEvent> optionalManufacturerInformation) {
        this.nodeId = nodeId;
        this.nodeStatus = nodeStatus;
        this.availability = availability;
        this.optionalNodeInformation = optionalNodeInformation;
        this.optionalManufacturerInformation = optionalManufacturerInformation;
    }

    public void addCommandClass(CommandClass commandClass) {
        commandClasses.add(commandClass);
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
    public Optional<NodeIdentifyEvent> getNodeInformation() {
        return optionalNodeInformation;
    }

    @Override
    public Optional<ManufactorInfoEvent> getManufactorInfoEvent() {
        return optionalManufacturerInformation;
    }

    @Override
    public List<CommandClass> getCommandClasses() {
        return commandClasses;
    }

    @Override
    public List<Integer> getEndpoints() {
        return null;
    }

    @Override
    public String toString() {
        return "ZWaveNodeImpl{" +
                "nodeId=" + nodeId +
                ", nodeStatus=" + nodeStatus +
                ", availability=" + availability +
                ", nodeInformation=" + (optionalNodeInformation.isPresent() ? optionalNodeInformation.get().toString() : "") +
                ", manufacturerInformation=" + (optionalManufacturerInformation.isPresent() ? optionalManufacturerInformation.get().toString() : "") +
                '}';
    }
}
