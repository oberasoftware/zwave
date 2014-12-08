package com.oberasoftware.home.zwave.api.actions;

import com.oberasoftware.home.zwave.api.ZWaveDevice;
import com.oberasoftware.home.zwave.api.ZWaveDeviceAction;

/**
 * @author renarj
 */
public class SwitchAction implements ZWaveDeviceAction {

    public static final int MAX_LEVEL = 100;

    public enum STATE {
        ON,
        OFF
    }

    private final STATE desiredState;
    private final ZWaveDevice device;

    private final int level;

    public SwitchAction(ZWaveDevice device, STATE desiredState) {
        this(device, desiredState, MAX_LEVEL);
    }

    public SwitchAction(ZWaveDevice device, int level) {
        this(device, STATE.ON, level);
    }

    private SwitchAction(ZWaveDevice device, STATE desiredState, int level) {
        this.device = device;
        this.desiredState = desiredState;
        this.level = level;
    }

    @Override
    public int getNodeId() {
        return device.getNodeId();
    }

    public STATE getDesiredState() {
        return desiredState;
    }

    public ZWaveDevice getDevice() {
        return device;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return "SwitchAction{" +
                "desiredState=" + desiredState +
                ", device=" + device +
                '}';
    }
}
