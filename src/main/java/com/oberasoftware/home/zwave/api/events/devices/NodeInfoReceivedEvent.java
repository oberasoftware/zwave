package com.oberasoftware.home.zwave.api.events.devices;

import com.oberasoftware.home.zwave.api.events.controller.ControllerEvent;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;

import java.util.List;

/**
 * @author renarj
 */
public class NodeInfoReceivedEvent implements ControllerEvent {
    private final int nodeId;
    private final List<CommandClass> commandClasses;

    public NodeInfoReceivedEvent(int nodeId, List<CommandClass> commandClasses) {
        this.nodeId = nodeId;
        this.commandClasses = commandClasses;
    }

    public int getNodeId() {
        return nodeId;
    }

    public List<CommandClass> getCommandClasses() {
        return commandClasses;
    }

    @Override
    public boolean isTransactionCompleted() {
        return true;
    }

    @Override
    public String toString() {
        return "NodeInfoReceivedEvent{" +
                "nodeId=" + nodeId +
                ", commandClasses=" + commandClasses +
                '}';
    }
}
