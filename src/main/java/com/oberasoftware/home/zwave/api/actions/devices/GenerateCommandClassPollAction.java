package com.oberasoftware.home.zwave.api.actions.devices;

import com.oberasoftware.home.zwave.api.ZWaveDeviceAction;
import com.oberasoftware.home.zwave.api.ZWaveIntervalAction;
import com.oberasoftware.home.zwave.api.messages.types.CommandClass;

/**
 * @author renarj
 */
public class GenerateCommandClassPollAction implements ZWaveDeviceAction, ZWaveIntervalAction {
    private final int nodeId;
    private final int endpointId;
    private final CommandClass commandClass;

    public GenerateCommandClassPollAction(int nodeId, int endpointId, CommandClass commandClass) {
        this.nodeId = nodeId;
        this.endpointId = endpointId;
        this.commandClass = commandClass;
    }

    @Override
    public int getEndpointId() {
        return endpointId;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    public CommandClass getCommandClass() {
        return commandClass;
    }

    @Override
    public String toString() {
        return "GenerateCommandClassPollAction{" +
                "nodeId=" + nodeId +
                ", commandClass=" + commandClass +
                '}';
    }
}
