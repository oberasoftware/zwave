package com.oberasoftware.home.zwave.api;

import com.oberasoftware.home.zwave.exceptions.ZWaveException;
import com.oberasoftware.home.zwave.api.messages.ZWaveRawMessage;

/**
 * @author renarj
 */
public interface ControllerConnector {
    void connect() throws ZWaveException;

    void close() throws ZWaveException;

    void send(ZWaveRawMessage rawMessage) throws ZWaveException;

    void completeTransaction();

    boolean isConnected();
}
