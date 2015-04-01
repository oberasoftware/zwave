package com.oberasoftware.home.zwave.api.actions.devices;

import com.oberasoftware.home.zwave.api.ZWaveDeviceAction;
import com.oberasoftware.home.zwave.messages.types.CommandClass;

/**
 * @author renarj
 */
public class DeviceVersionAction implements ZWaveDeviceAction {
    private final int nodeId;
    private final CommandClass commandClass;

    public DeviceVersionAction(int nodeId, CommandClass commandClass) {
        this.nodeId = nodeId;
        this.commandClass = commandClass;
    }

    public CommandClass getCommandClass() {
        return commandClass;
    }

    @Override
    public int getNodeId() {
        return nodeId;
    }

    @Override
    public String toString() {
        return "DeviceVersionAction{" +
                "nodeId=" + nodeId +
                ", commandClass=" + commandClass +
                '}';
    }
}
