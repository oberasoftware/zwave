package com.oberasoftware.home.zwave.core;

import com.oberasoftware.home.zwave.api.events.controller.NodeInformationEvent;
import com.oberasoftware.home.zwave.api.events.devices.ManufactorInfoEvent;

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

    Optional<NodeInformationEvent> getNodeInformation();

    Optional<ManufactorInfoEvent> getManufactorInfoEvent();
}
