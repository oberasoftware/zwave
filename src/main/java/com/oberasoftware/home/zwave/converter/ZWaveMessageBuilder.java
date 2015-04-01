package com.oberasoftware.home.zwave.converter;

import com.oberasoftware.home.zwave.messages.ZWaveRawMessage;
import com.oberasoftware.home.zwave.messages.types.CommandClass;
import com.oberasoftware.home.zwave.messages.types.ControllerMessageType;
import com.oberasoftware.home.zwave.messages.types.MessageType;

/**
 * @author renarj
 */
public class ZWaveMessageBuilder {

    private byte[] buffer;


    public static ZWaveMessageBuilder create(ControllerMessageType controllerMessageType, MessageType messageType) {
        return new ZWaveMessageBuilder();
    }

    public static ZWaveMessageBuilder create(ControllerMessageType controllerMessageType, MessageType messageType, int nodeId) {
        return new ZWaveMessageBuilder();
    }


    public ZWaveMessageBuilder node(int nodeId) {
        return this;
    }

    public ZWaveMessageBuilder callback(int callbackId) {
        return this;
    }

    public ZWaveMessageBuilder addByte(byte b) {
        return this;
    }

    public ZWaveMessageBuilder addInt(int i) {
        return this;
    }

    public ZWaveMessageBuilder addCommandClass(CommandClass commandClass) {
        return this;
    }

    public ZWaveRawMessage construct() {
        return null;
    }
}
