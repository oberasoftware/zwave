package com.oberasoftware.home.zwave.api.events.controller;

import com.oberasoftware.home.zwave.api.events.ZWaveEvent;

/**
 * @author renarj
 */
public interface ControllerEvent extends ZWaveEvent {
    boolean isTransactionCompleted();
}
