package com.oberasoftware.home.zwave.api.events.devices;

/**
 * @author renarj
 */
public class WakeUpReceivedEvent extends WakeUpEvent {
    public WakeUpReceivedEvent(int nodeId) {
        super(nodeId);
    }
}
