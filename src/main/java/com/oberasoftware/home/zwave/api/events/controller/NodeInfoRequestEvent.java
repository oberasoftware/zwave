package com.oberasoftware.home.zwave.api.events.controller;

/**
 * @author renarj
 */
public class NodeInfoRequestEvent implements ControllerEvent {
    @Override
    public boolean isTransactionCompleted() {
        return false;
    }
}
