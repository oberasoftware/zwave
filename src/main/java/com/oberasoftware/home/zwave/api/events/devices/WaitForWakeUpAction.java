package com.oberasoftware.home.zwave.api.events.devices;

import com.oberasoftware.home.zwave.api.ZWaveDeviceAction;

/**
 * @author renarj
 */
public class WaitForWakeUpAction implements ZWaveDeviceAction {
    private final ZWaveDeviceAction deviceAction;
    private final int callbackId;

    public WaitForWakeUpAction(ZWaveDeviceAction deviceAction, int callbackId) {
        this.deviceAction = deviceAction;
        this.callbackId = callbackId;
    }

    public ZWaveDeviceAction getDeviceAction() {
        return deviceAction;
    }

    public int getCallbackId() {
        return callbackId;
    }

    public int getNodeId() {
        return deviceAction.getNodeId();
    }

    @Override
    public String toString() {
        return "WaitForWakeUpEvent{" +
                "deviceAction=" + deviceAction +
                '}';
    }
}
