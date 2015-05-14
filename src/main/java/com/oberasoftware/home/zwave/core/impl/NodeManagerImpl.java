package com.oberasoftware.home.zwave.core.impl;

import com.oberasoftware.base.event.EventBus;
import com.oberasoftware.home.zwave.api.events.controller.NodeIdentifyEvent;
import com.oberasoftware.home.zwave.api.events.devices.ManufactorInfoEvent;
import com.oberasoftware.home.zwave.api.events.devices.NodeRegisteredEvent;
import com.oberasoftware.home.zwave.api.events.devices.NodeUpdatedEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;
import com.oberasoftware.home.zwave.core.NodeAvailability;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.core.NodeStatus;
import com.oberasoftware.home.zwave.core.ZWaveNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author renarj
 */
@Component
public class NodeManagerImpl implements NodeManager {
    private Map<Integer, ZWaveNode> nodeMap = new ConcurrentHashMap<>();

    @Autowired
    private EventBus eventBus;

    @Override
    public void registerNode(int nodeId) {
        registerNode(new ZWaveNodeImpl(nodeId));
    }

    @Override
    public void registerNode(ZWaveNode node) {
        nodeMap.putIfAbsent(node.getNodeId(), node);

        eventBus.publish(new NodeRegisteredEvent(node.getNodeId(), node));
    }

    @Override
    public void registerCommandClass(int nodeId, CommandClass commandClass) {
        registerCommandClasses(nodeId, newArrayList(commandClass));
    }

    @Override
    public void registerCommandClasses(int nodeId, List<CommandClass> commandClasses) {
        ZWaveNode node = getNode(nodeId);

        ZWaveNodeImpl newNode = node.cloneNode();
        commandClasses.forEach(newNode::addCommandClass);

        replaceOrSetNode(newNode);
    }

    @Override
    public void registerEndpoints(int nodeId, List<Integer> endpointIds) {
        ZWaveNode node = getNode(nodeId);

        ZWaveNodeImpl newNode = node.cloneNode();
        endpointIds.forEach(newNode::addEndpoint);

        replaceOrSetNode(newNode);
    }

    @Override
    public void setNodeProperty(int nodeId, String key, Object value) {
        ZWaveNode node = getNode(nodeId);

        ZWaveNodeImpl newNode = node.cloneNode();
        newNode.addProperty(key, value);

        replaceOrSetNode(newNode);
    }

    @Override
    public boolean haveNodeMinimalStatus(NodeStatus nodeStatus) {
        return nodeMap.values().stream().allMatch(v -> v.getNodeStatus().hasMinimalStatus(nodeStatus));
    }

    @Override
    public ZWaveNode setNodeAvailability(int nodeId, NodeAvailability availability) {
        ZWaveNode node = getNode(nodeId);

        return replaceOrSetNode(node.cloneNode().setAvailability(availability));
    }

    @Override
    public boolean isBatteryDevice(int nodeId) {
        ZWaveNode node = getNode(nodeId);

        return node != null && node.getNodeInformation().isPresent() && !node.getNodeInformation().get().isListening();
    }

    @Override
    public ZWaveNode setNodeStatus(int nodeId, NodeStatus nodeStatus) {
        ZWaveNode node = getNode(nodeId);

        return replaceOrSetNode(node.cloneNode().setNodeStatus(nodeStatus));
    }

    @Override
    public List<ZWaveNode> getNodes() {
        return newArrayList(nodeMap.values());
    }

    @Override
    public void setNodeInformation(int nodeId, NodeIdentifyEvent nodeIdentifyEvent) {
        ZWaveNode node = getNode(nodeId);

        replaceOrSetNode(new ZWaveNodeImpl(node.getNodeId()).setNodeInformation(nodeIdentifyEvent));
    }

    @Override
    public void setNodeInformation(int nodeId, ManufactorInfoEvent manufactorInfoEvent) {
        ZWaveNode node = getNode(nodeId);

        replaceOrSetNode(new ZWaveNodeImpl(node.getNodeId()).setManufacturerInformation(manufactorInfoEvent));
    }

    private ZWaveNode replaceOrSetNode(ZWaveNode node) {
        int nodeId = node.getNodeId();
        nodeMap.put(nodeId, node);

        eventBus.publish(new NodeUpdatedEvent(node.getNodeId(), node));

        return node;
    }

    @Override
    public ZWaveNode getNode(int nodeId) {
        return nodeMap.get(nodeId);
    }
}
