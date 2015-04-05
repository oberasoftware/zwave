package com.oberasoftware.home.zwave.threading;

import com.oberasoftware.home.zwave.api.events.ZWaveEvent;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;

/**
 * @author renarj
 */
public class ActionConvertedEvent implements ZWaveEvent {
    private ZWaveRawMessage rawMessage;

    public ActionConvertedEvent(ZWaveRawMessage rawMessage) {
        this.rawMessage = rawMessage;
    }

    public ZWaveRawMessage getRawMessage() {
        return rawMessage;
    }

    @Override
    public String toString() {
        return "ActionConvertedEvent{" +
                "rawMessage=" + rawMessage +
                '}';
    }
}
