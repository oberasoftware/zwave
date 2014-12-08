package com.oberasoftware.home.zwave.api.events.devices;

/**
 * @author renarj
 */
public class WakeUpNoMoreInformationEvent extends WakeUpEvent {

    public WakeUpNoMoreInformationEvent(int nodeId) {
        super(nodeId);
    }

    @Override
    public String toString() {
        return "WakeUpNoMoreInformationEvent{" + super.toString() + "}";
    }
}
