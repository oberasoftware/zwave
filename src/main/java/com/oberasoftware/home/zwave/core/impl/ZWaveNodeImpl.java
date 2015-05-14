package com.oberasoftware.home.zwave.core.impl;

import com.oberasoftware.home.zwave.api.events.controller.NodeIdentifyEvent;
import com.oberasoftware.home.zwave.api.events.devices.ManufactorInfoEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.core.NodeAvailability;
import com.oberasoftware.home.zwave.core.NodeStatus;
import com.oberasoftware.home.zwave.core.ZWaveNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author renarj
 */
public class ZWaveNodeImpl implements ZWaveNode {

    private final int nodeId;
    private NodeStatus nodeStatus = NodeStatus.UNKNOWN;
    private NodeAvailability availability = NodeAvailability.UNKNOWN;

    private Optional<NodeIdentifyEvent> optionalNodeInformation = Optional.empty();
    private Optional<ManufactorInfoEvent> optionalManufacturerInformation = Optional.empty();
    private Set<CommandClass> commandClasses = new HashSet<>();
    private List<Integer> endpoints = new ArrayList<>();
    private Map<String, Object> nodeProperties = new HashMap<>();

    public ZWaveNodeImpl(int nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public NodeAvailability getAvailability() {
        return availability;
    }

    public ZWaveNodeImpl setAvailability(NodeAvailability availability) {
        this.availability = availability;
        return this;
    }

    @Override
    public NodeStatus getNodeStatus() {
        return nodeStatus;
    }

    public ZWaveNodeImpl setNodeStatus(NodeStatus nodeStatus) {
        this.nodeStatus = nodeStatus;
        return this;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public Optional<NodeIdentifyEvent> getNodeInformation() {
        return optionalNodeInformation;
    }

    public ZWaveNodeImpl setNodeInformation(NodeIdentifyEvent nodeInformation) {
        this.optionalNodeInformation = Optional.ofNullable(nodeInformation);
        return this;
    }

    @Override
    public Optional<ManufactorInfoEvent> getManufactorInfoEvent() {
        return optionalManufacturerInformation;
    }

    public ZWaveNodeImpl setManufacturerInformation(ManufactorInfoEvent manufacturerInformation) {
        this.optionalManufacturerInformation = Optional.ofNullable(manufacturerInformation);
        return this;
    }

    @Override
    public Set<CommandClass> getCommandClasses() {
        return commandClasses;
    }

    public ZWaveNodeImpl addCommandClass(CommandClass commandClass) {
        commandClasses.add(commandClass);
        return this;
    }

    public ZWaveNodeImpl setCommandClasses(Set<CommandClass> commandClasses) {
        this.commandClasses = commandClasses;
        return this;
    }

    @Override
    public List<Integer> getEndpoints() {
        return endpoints;
    }

    public ZWaveNodeImpl addEndpoint(int endpointId) {
        this.endpoints.add(endpointId);
        return this;
    }

    public ZWaveNodeImpl setEndpoints(List<Integer> endpoints) {
        this.endpoints = endpoints;
        return this;
    }

    @Override
    public Map<String, Object> getNodeProperties() {
        return nodeProperties;
    }

    @Override
    public Object getProperty(String key) {
        return this.nodeProperties.get(key);
    }

    public void setNodeProperties(Map<String, Object> nodeProperties) {
        this.nodeProperties = nodeProperties;
    }

    public void addProperty(String key, Object value) {
        this.nodeProperties.put(key, value);
    }

    @Override
    public ZWaveNodeImpl cloneNode() {
        ZWaveNodeImpl clone = new ZWaveNodeImpl(nodeId);
        clone.setAvailability(availability);
        clone.setNodeStatus(nodeStatus);
        clone.optionalManufacturerInformation = optionalManufacturerInformation;
        clone.optionalNodeInformation = optionalNodeInformation;
        clone.setCommandClasses(commandClasses);
        clone.setEndpoints(endpoints);
        clone.setNodeProperties(nodeProperties);

        return clone;
    }

    @Override
    public String toString() {
        return "ZWaveNodeImpl{" +
                "nodeId=" + nodeId +
                ", endpoints=" + endpoints +
                ", nodeStatus=" + nodeStatus +
                ", availability=" + availability +
                ", nodeInformation=" + (optionalNodeInformation.isPresent() ? optionalNodeInformation.get().toString() : "") +
                ", manufacturerInformation=" + (optionalManufacturerInformation.isPresent() ? optionalManufacturerInformation.get().toString() : "") +
                '}';
    }
}
