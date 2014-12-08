package com.oberasoftware.home.zwave.exceptions;

/**
 * @author renarj
 */
public class RuntimeAutomationException extends RuntimeException {
    public RuntimeAutomationException(String message, Throwable e) {
        super(message, e);
    }
}
