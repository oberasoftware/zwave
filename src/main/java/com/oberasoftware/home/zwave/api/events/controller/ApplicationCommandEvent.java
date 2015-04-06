package com.oberasoftware.home.zwave.api.events.controller;

import com.oberasoftware.home.zwave.api.messages.types.CommandClass;

import static com.oberasoftware.home.zwave.api.messages.ZWaveRawMessage.bb2hex;

/**
 * @author renarj
 */
public class ApplicationCommandEvent implements ControllerEvent {

    private int nodeId;
    private int endpointId;
    private CommandClass commandClass;

    private byte[] payload;

    public ApplicationCommandEvent(int nodeId, int endpointId, CommandClass commandClass, byte[] payload) {
        this.nodeId = nodeId;
        this.endpointId = endpointId;
        this.commandClass = commandClass;
        this.payload = payload;
    }

    @Override
    public boolean isTransactionCompleted() {
        return false;
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getEndpointId() {
        return endpointId;
    }

    public CommandClass getCommandClass() {
        return commandClass;
    }

    public byte[] getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "ApplicationCommandEvent{" +
                "nodeId=" + nodeId +
                ", endpointId=" + endpointId +
                ", commandClass=" + commandClass.getLabel() +
                ", payload=" + bb2hex(payload) +
                '}';
    }
}
