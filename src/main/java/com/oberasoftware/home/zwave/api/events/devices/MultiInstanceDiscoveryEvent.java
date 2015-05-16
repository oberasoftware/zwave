package com.oberasoftware.home.zwave.api.events.devices;

import java.util.List;

/**
 * @author renarj
 */
public class MultiInstanceDiscoveryEvent implements DeviceEvent {

    private final int nodeId;
    private final List<Integer> endpoints;

    public MultiInstanceDiscoveryEvent(int nodeId, List<Integer> endpoints) {
        this.nodeId = nodeId;
        this.endpoints = endpoints;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    public List<Integer> getEndpoints() {
        return endpoints;
    }
}
