package com.oberasoftware.home.zwave.core;


import com.oberasoftware.home.zwave.api.events.controller.NodeIdentifyEvent;
import com.oberasoftware.home.zwave.api.events.devices.ManufactorInfoEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;

import java.util.List;

/**
 * @author renarj
 */
public interface NodeManager {
    void registerNode(int nodeId);

    void registerNode(ZWaveNode node);

    void registerCommandClass(int nodeId, CommandClass commandClass);

    void registerCommandClasses(int nodeId, List<CommandClass> commandClasses);

    void registerEndpoints(int nodeId, List<Integer> endpointIds);

    void setNodeProperty(int nodeId, String key, Object value);

    /**
     * This indicates if all nodes in the network have reached the minimal status specified
     * @param nodeStatus The minimal status that all nodes should have achieved
     * @return True if all nodes have achieved this minimal status
     */
    boolean haveNodeMinimalStatus(NodeStatus nodeStatus);

    ZWaveNode setNodeStatus(int nodeId, NodeStatus nodeStatus);

    ZWaveNode setNodeAvailability(int nodeId, NodeAvailability availability);

    boolean isBatteryDevice(int nodeId);

    ZWaveNode getNode(int nodeId);

    void setNodeInformation(int nodeId, NodeIdentifyEvent nodeIdentifyEvent);

    void setNodeInformation(int nodeId, ManufactorInfoEvent manufactorInfoEvent);

    List<ZWaveNode> getNodes();
}
