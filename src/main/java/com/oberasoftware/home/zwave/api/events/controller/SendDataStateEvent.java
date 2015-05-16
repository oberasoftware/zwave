package com.oberasoftware.home.zwave.api.events.controller;

/**
 * @author renarj
 */
public class SendDataStateEvent implements TransactionEvent {
    private final SEND_STATE state;

    public SendDataStateEvent(SEND_STATE state) {
        this.state = state;
    }

    public SEND_STATE getState() {
        return state;
    }

    @Override
    public boolean isTransactionCompleted() {
        return false;
    }

    @Override
    public String toString() {
        return "SendDataStateEvent{" +
                "state=" + state +
                '}';
    }
}
