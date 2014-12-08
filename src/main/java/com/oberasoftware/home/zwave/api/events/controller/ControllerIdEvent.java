package com.oberasoftware.home.zwave.api.events.controller;

/**
 * @author renarj
 */
public class ControllerIdEvent implements ControllerEvent {
    private final int homeId;
    private final int controllerId;

    public ControllerIdEvent(int homeId, int controllerId) {
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
