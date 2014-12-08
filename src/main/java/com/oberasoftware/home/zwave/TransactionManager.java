package com.oberasoftware.home.zwave;

import com.oberasoftware.home.zwave.exceptions.HomeAutomationException;
import com.oberasoftware.home.zwave.api.ZWaveAction;
import com.oberasoftware.home.zwave.api.events.controller.ControllerEvent;

/**
 * @author renarj
 */
public interface TransactionManager {
    int startAction(ZWaveAction action) throws HomeAutomationException;

    void completeTransaction(ControllerEvent controllerEvent) throws HomeAutomationException;

    void cancelTransaction() throws HomeAutomationException;
}
