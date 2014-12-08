package com.oberasoftware.home.zwave.exceptions;

/**
 * @author renarj
 */
public class ZWaveException extends HomeAutomationException {
    public ZWaveException(String message, Throwable e) {
        super(message, e);
    }

    public ZWaveException(String message) {
        super(message);
    }
}
