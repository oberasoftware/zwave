package com.oberasoftware.home.zwave.api.events.controller;

import com.oberasoftware.home.zwave.api.events.ZWaveEvent;

/**
 * @author renarj
 */
public interface TransactionEvent extends ZWaveEvent {
    boolean isTransactionCompleted();
}
