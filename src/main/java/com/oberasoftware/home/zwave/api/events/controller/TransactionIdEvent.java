package com.oberasoftware.home.zwave.api.events.controller;

/**
 * @author renarj
 */
public class TransactionIdEvent implements TransactionEvent {
    private final int homeId;
    private final int controllerId;

    public TransactionIdEvent(int homeId, int controllerId) {
        this.homeId = homeId;
        this.controllerId = controllerId;
    }

    public int getHomeId() {
        return homeId;
    }

    public int getControllerId() {
        return controllerId;
    }

    @Override
    public boolean isTransactionCompleted() {
        return true;
    }

    @Override
    public String toString() {
        return "ControllerIdEvent{" +
                "homeId=" + homeId +
                ", controllerId=" + controllerId +
                '}';
    }
}
