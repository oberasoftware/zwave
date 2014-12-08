package com.oberasoftware.home.zwave.api.events.devices;

import com.oberasoftware.home.zwave.api.events.ZWaveEvent;

/**
 * @author renarj
 */
public interface DeviceEvent extends ZWaveEvent {
    int getNodeId();

    default boolean isTriggered() {
        return false;
    }

    default boolean containsValue() {
        return false;
    }

    default String valueAsString() {
        return null;
    }

    default int valueAsInt() {
        return -1;
    }
}
