package com.oberasoftware.home.zwave.core.impl;

import com.oberasoftware.base.event.EventBus;
import com.oberasoftware.home.zwave.api.events.controller.NodeIdentifyEvent;
import com.oberasoftware.home.zwave.api.events.devices.ManufactorInfoEvent;
import com.oberasoftware.home.zwave.api.events.devices.NodeRegisteredEvent;
import com.oberasoftware.home.zwave.api.events.devices.NodeUpdatedEvent;
import com.oberasoftware.home.zwave.core.NodeAvailability;
import com.oberasoftware.home.zwave.core.NodeManager;
import com.oberasoftware.home.zwave.core.NodeStatus;
import com.oberasoftware.home.zwave.core.ZWaveNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        registerNode(new ZWaveNodeImpl(nodeId, NodeStatus.UNKNOWN, NodeAvailability.UNKNOWN, Optional.empty(), Optional.empty()));
    }

    @Override
    public void registerNode(ZWaveNode node) {
        nodeMap.putIfAbsent(node.getNodeId(), node);

        eventBus.publish(new NodeRegisteredEvent(node.getNodeId(), node));
    }

    @Override
    public boolean haveNodeMinimalStatus(NodeStatus nodeStatus) {
        return nodeMap.values().stream().allMatch(v -> v.getNodeStatus().hasMinimalStatus(nodeStatus));
    }

    @Override
    public ZWaveNode setNodeAvailability(int nodeId, NodeAvailability availability) {
        ZWaveNode node = getNode(nodeId);

        return replaceOrSetNode(new ZWaveNodeImpl(node.getNodeId(), node.getNodeStatus(), availability, node.getNodeInformation(), node.getManufactorInfoEvent()));
    }

    @Override
    public boolean isBatteryDevice(int nodeId) {
        ZWaveNode node = getNode(nodeId);

        return node != null && node.getNodeInformation().isPresent() && !node.getNodeInformation().get().isListening();
    }

    @Override
    public ZWaveNode setNodeStatus(int nodeId, NodeStatus nodeStatus) {
        ZWaveNode node = getNode(nodeId);

        return replaceOrSetNode(new ZWaveNodeImpl(node.getNodeId(), nodeStatus, node.getAvailability(), node.getNodeInformation(), node.getManufactorInfoEvent()));
    }

    @Override
    public List<ZWaveNode> getNodes() {
        return newArrayList(nodeMap.values());
    }

    @Override
    public void setNodeInformation(int nodeId, NodeIdentifyEvent nodeIdentifyEvent) {
        ZWaveNode node = getNode(nodeId);

        replaceOrSetNode(new ZWaveNodeImpl(node.getNodeId(), node.getNodeStatus(), node.getAvailability(), Optional.of(nodeIdentifyEvent), node.getManufactorInfoEvent()));
    }

    @Override
    public void setNodeInformation(int nodeId, ManufactorInfoEvent manufactorInfoEvent) {
        ZWaveNode node = getNode(nodeId);

        replaceOrSetNode(new ZWaveNodeImpl(node.getNodeId(), node.getNodeStatus(), node.getAvailability(), node.getNodeInformation(), Optional.of(manufactorInfoEvent)));
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
