package com.oberasoftware.home.zwave.threading;

import com.oberasoftware.home.zwave.api.events.ZWaveEvent;
import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;

/**
 * @author renarj
 */
public class MessageReceivedEvent implements ZWaveEvent {

    private ZWaveRawMessage receivedMessage;

    public MessageReceivedEvent(ZWaveRawMessage receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    public ZWaveRawMessage getReceivedMessage() {
        return receivedMessage;
    }

    @Override
    public String toString() {
        return "MessageReceivedEvent{" +
                "receivedMessage=" + receivedMessage +
                '}';
    }
}
