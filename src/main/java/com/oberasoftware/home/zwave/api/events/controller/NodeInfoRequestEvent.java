package com.oberasoftware.home.zwave.api.events.controller;

/**
 * @author renarj
 */
public class NodeInfoRequestEvent implements TransactionEvent {
    @Override
    public boolean isTransactionCompleted() {
        return false;
    }
}
