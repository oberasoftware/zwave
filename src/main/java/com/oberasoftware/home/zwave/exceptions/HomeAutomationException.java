package com.oberasoftware.home.zwave.exceptions;

/**
 * @author renarj
 */
public class HomeAutomationException extends Exception {
    public HomeAutomationException(String message, Throwable e) {
        super(message, e);
    }

    public HomeAutomationException(String message) {
        super(message);
    }
}
