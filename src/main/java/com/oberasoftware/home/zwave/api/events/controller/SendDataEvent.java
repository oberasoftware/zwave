package com.oberasoftware.home.zwave.api.events.controller;

/**
 * @author renarj
 */
public class SendDataEvent extends SendDataStateEvent {
    private int nodeId;
    private int callbackId;

    public SendDataEvent(int nodeId, int callbackId, SEND_STATE sendState) {
        super(sendState);
        this.nodeId = nodeId;
        this.callbackId = callbackId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public int getCallbackId() {
        return callbackId;
    }

    @Override
    public boolean isTransactionCompleted() {
        return getState() == SEND_STATE.SUCCESS;
    }

    @Override
    public String toString() {
        return "SendDataEvent{" +
                "nodeId=" + nodeId +
                ", callbackId=" + callbackId +
                ", sendState=" + getState() +
                '}';
    }
}
