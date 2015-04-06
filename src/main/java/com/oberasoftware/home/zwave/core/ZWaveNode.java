package com.oberasoftware.home.zwave.core;

import com.oberasoftware.home.zwave.api.events.controller.NodeIdentifyEvent;
import com.oberasoftware.home.zwave.api.events.devices.ManufactorInfoEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;

import java.util.List;
import java.util.Optional;

/**
 * @author renarj
 */
public interface ZWaveNode {
    int getNodeId();

    default NodeStatus getNodeStatus() {
        return NodeStatus.INITIALIZING;
    }

    default NodeAvailability getAvailability() {
        return NodeAvailability.UNKNOWN;
    }

    Optional<NodeIdentifyEvent> getNodeInformation();

    Optional<ManufactorInfoEvent> getManufactorInfoEvent();

    List<CommandClass> getCommandClasses();

    List<Integer> getEndpoints();
}
