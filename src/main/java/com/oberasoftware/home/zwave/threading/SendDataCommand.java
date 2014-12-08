package com.oberasoftware.home.zwave.threading;

import com.oberasoftware.home.zwave.api.events.ZWaveEvent;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;

/**
 * @author renarj
 */
public class SendDataCommand implements ZWaveEvent {
    private ZWaveRawMessage rawMessage;

    public SendDataCommand(ZWaveRawMessage rawMessage) {
        this.rawMessage = rawMessage;
    }

    public ZWaveRawMessage getRawMessage() {
        return rawMessage;
    }
}
